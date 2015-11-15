package org.aries.ui.event;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.ObserverMethod;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

public class AsyncEvent<T> {

	private static final AnnotationLiteral<Any> ANY = 
			new AnnotationLiteral<Any>() {
	};

	@Inject
	private BeanManager beanManager;

	private ExecutorService executorService;

	public AsyncEvent() {
	}

	@PostConstruct
	public void init() {
		executorService = Executors.newFixedThreadPool(5);
	}

	@PreDestroy
	public void shutdown() {
		try {
			executorService.shutdown();
			final boolean terminated = executorService.awaitTermination(10, TimeUnit.SECONDS);
			if (!terminated) {
				throw new RuntimeException("awaitTermination timeout");
			}
		} catch (final InterruptedException ie) {
			throw new RuntimeException(ie);
		}
	}

	public void fire(final T event) {
		fire(event, ANY);
	}

	public void fire(final T event, final Annotation... qualifiers) {
		final Set<ObserverMethod<? super T>> observers = beanManager.resolveObserverMethods(event, qualifiers);
		final Set<Callable<Void>> tasks = createCallablesForObservers(event, observers);
		invokeAll(tasks);
	}

	private Set<Callable<Void>> createCallablesForObservers(final T event, 
			final Set<ObserverMethod<? super T>> observers) {
		final Set<Callable<Void>> tasks = new HashSet<Callable<Void>>();
		for (final ObserverMethod<? super T> observer: observers) {
			final Callable<Void> callable = createCallable(event, observer);
			tasks.add(callable);
		}
		return tasks;
	}

	private Callable<Void> createCallable(final T event, 
			final ObserverMethod<? super T> observer) {
		final Callable<Void> callable = new Callable<Void>() {
			@Override
			public Void call() {
				observer.notify(event);
				return null;
			}
		};
		return callable;
	}

	private void invokeAll(final Set<Callable<Void>> tasks) {
		try {
			executorService.invokeAll(tasks);
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
