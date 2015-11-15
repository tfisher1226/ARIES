package org.aries.bank;

import javax.xml.ws.Endpoint;

import org.aries.bank.BankLoanQuoteImpl;


public class BankLoanQuoteMain {

	public static void main(String[] args) {
		try {
			//String url = "http://www.aries.org/bank/0.0.1";
			String url = "http://localhost:8080/bank/0.0.1";
			Object instance = new BankLoanQuoteImpl();
			launchAsWebService(url, instance);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void launchAsWebService(String url, Object instance) {
		//Bus bus = BusFactory.newInstance().createBus();
		//BusFactory.setThreadDefaultBus(bus);
		Endpoint.publish(url, instance);
	}

}
