package electricity_24_7.com.OnlineElectricitySystem.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
//import com.mysql.cj.Session;

import electricity_24_7.com.OnlineElectricitySystem.dao.BillDao;
import electricity_24_7.com.OnlineElectricitySystem.entity.Bill;
import electricity_24_7.com.OnlineElectricitySystem.entity.CustomerRegistration;
import electricity_24_7.com.OnlineElectricitySystem.entity.Meter;
import electricity_24_7.com.OnlineElectricitySystem.entity.PaymentHistory;
import electricity_24_7.com.OnlineElectricitySystem.util.HibernateUtil;

public class BillService {

    private final BillDao billDao = new BillDao();
    
    //Fetches the list of unpaid bills for a specific customer.
    public List<Bill> getUnpaidBills(String customerNumber) {
        return billDao.getOutstandingBills(customerNumber);
    }
    
    // Processes bill payment and updates the payment history.
    public void payBill(int billId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Fetch the bill by its ID
            Bill bill = session.get(Bill.class, billId);
            if (bill != null && "Unpaid".equalsIgnoreCase(bill.getPaymentStatus())) {
                // Update the bill status to "Paid"
                bill.setPaymentStatus("Paid");
                session.update(bill);

                // Create and save a payment history record
                PaymentHistory paymentHistory = new PaymentHistory();
                paymentHistory.setPaymentDate(LocalDate.now());
                paymentHistory.setAmountPaid(bill.getAmount());
                paymentHistory.setCustomer(bill.getCustomer());

                session.save(paymentHistory); // Save the payment history record

                transaction.commit();
                System.out.println("Bill payment recorded successfully, and payment history updated.");
            } else if (bill == null) {
                System.out.println("Invalid Bill ID. Please try again.");
            } else {
                System.out.println("Bill is already paid.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Inner class `BillDao` to handle database operations for bills.
    public class BillDao {
    	// Fetches a list of unpaid bills for a specific customer.
        public List<Bill> getOutstandingBills(String customerNumber) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                Query<Bill> query = session.createQuery(
                		"SELECT b FROM Bill b WHERE b.customer.customerNumber = :customerNumber AND b.paymentStatus = 'Unpaid'",
                    Bill.class
                );
                query.setParameter("customerNumber", customerNumber);
                return query.list();
            } catch (Exception e) {
            	// Handle any exceptions that occur while fetching outstanding bills
                e.printStackTrace();
                return new ArrayList<>();
            }
        }
          //Updates the payment status of a specific bill.
        public void updateBillStatus(int billId, String status) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                Transaction transaction = session.beginTransaction();
                Bill bill = session.get(Bill.class, billId);
                if (bill != null) {
                    bill.setPaymentStatus(status); // Update status to "Paid"
                    session.update(bill);
                }
                transaction.commit();
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }

}
