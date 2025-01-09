package electricity_24_7.com.OnlineElectricitySystem.service;

import java.util.List;

import electricity_24_7.com.OnlineElectricitySystem.dao.PaymentHistoryDao;
import electricity_24_7.com.OnlineElectricitySystem.entity.PaymentHistory;

public class PaymentHistoryService {
	private final PaymentHistoryDao paymentDao = new PaymentHistoryDao();


    // Retrieve payment history for a specific customer
    public List<PaymentHistory> getHistory(String customerNumber) {
        return PaymentHistoryDao.getPaymentHistoryByCustomer(customerNumber);
    }


    // Process payment for the first unpaid bill
	public boolean processPayment(String customerNumber, double amount, String method) {
		// TODO Auto-generated method stub
		return false;
	}
}

