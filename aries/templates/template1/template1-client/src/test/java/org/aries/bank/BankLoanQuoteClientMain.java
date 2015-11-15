package org.aries.bank;

import org.aries.bank.BankLoanQuoteRequest;
import org.aries.bank.BankLoanQuoteResponse;


public class BankLoanQuoteClientMain {

	public static void main(String[] args) {
		BankLoanQuoteClientMain main = new BankLoanQuoteClientMain();
		main.executeGetLoanQuote();
	}

	public void executeGetLoanQuote() {
		try {
			BankLoanQuoteClient client = new BankLoanQuoteClient();
			BankLoanQuoteRequest request = new BankLoanQuoteRequest();
			request.setBorrowerName("Tom Fisher");
			request.setBorrowerSsn("8897761254");
			request.setCreditHistory(10);
			request.setCreditScore(800);
			request.setLoanAmount(300000d);
			request.setLoanDuration(30);
			BankLoanQuoteResponse response = client.getLoanQuote(request);
			System.out.println("Response: "+response.getLoanQuote());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
