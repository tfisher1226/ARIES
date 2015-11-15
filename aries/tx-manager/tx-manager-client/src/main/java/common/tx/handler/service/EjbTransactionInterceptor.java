package common.tx.handler.service;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;


public class EjbTransactionInterceptor {

	@AroundInvoke
	public Object intercept(InvocationContext ctx) throws Exception {
		System.out.println("*** TransactionInterceptor intercepting " + ctx.getMethod().getName());
		try {
			
			
			return ctx.proceed();
		} finally {
			System.out.println("*** TransactionInterceptor exiting");
		}
	}
}

