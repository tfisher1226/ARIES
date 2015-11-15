package org.aries.client.jms;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.xml.ws.Response;

import org.aries.Handler;


/**
 * {@link Response} implementation. When the Runnable is executed, the
 * request is passed to Fiber and returns. When the Fiber finishes the execution, it sets
 * response in the {@link FutureTask}
 */
public class AsyncResponse<T> extends FutureTask<T> implements Response<T> {

    /** Gets invoked asynchronously to execute the task. */
    private final Callable<T> callable;
    
    /** Gets invoked at the completion of the task. */
    private final Handler<T> handler;
    
    
    public AsyncResponse(Callable<T> callable, Handler<T> handler) {
        super(callable);
        this.callable = callable;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            T response = callable.call();
            set(response, null);
        } catch (Throwable e) {
            set(null, new RuntimeException(e));
        }
    }

    public void set(final T value, final Throwable exception) {
//        // call the handler before we mark the future as 'done'
//        if (handler != null) {
//            try {
//                /**
//                 * {@link Response} object passed into the callback.
//                 * We need a separate {@link java.util.concurrent.Future} because we don't want {@link ResponseImpl}
//                 * to be marked as 'done' before the callback finishes execution.
//                 * (That would provide implicit synchronization between the application code
//                 * in the main thread and the callback code, and is compatible with the JAX-RI 2.0 FCS.
//                 */
//                class CallbackFuture<T> extends CompletedFuture<T> implements Response<T> {
//                    public CallbackFuture(T value, Throwable exception) {
//                        super(value, exception);
//                    }
//
//                    public Map<String, Object> getContext() {
//                        return AsyncResponse.this.getContext();
//                    }
//                }
//                Response<T> response = new CallbackFuture<T>(value, exception);
//
//            } catch (Throwable e) {
//                super.setException(e);
//                return;
//            }
//        }
        if (exception != null) {
            super.setException(exception);
        } else {
            super.set(value);
        }
    }

	@Override
	public Map<String, Object> getContext() {
		// TODO Auto-generated method stub
		return null;
	}
}
