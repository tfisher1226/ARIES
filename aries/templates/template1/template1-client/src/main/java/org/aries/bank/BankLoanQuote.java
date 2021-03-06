package org.aries.bank;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import org.aries.bank.BankLoanQuoteRequest;
import org.aries.bank.BankLoanQuoteResponse;

/**
 * Generated by Nam.
 *
 */
@WebService(name = "BankLoanQuote", targetNamespace = "http://www.aries.org/bank/0.0.1")
public interface BankLoanQuote {

	@WebMethod
	@WebResult(name = "result")
	public BankLoanQuoteResponse getLoanQuote(BankLoanQuoteRequest request);

}
