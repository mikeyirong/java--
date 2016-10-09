package com.tvc.css.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Concurrent process pool
 * 
 * @author mclaren
 *
 */
public class Concurrents {
	public final static int INFINITI = -1;
	private final Executor executor;

	private Concurrents(int capacity) {
		super();
		this.executor = capacity == -1 ? Executors.newCachedThreadPool() : Executors.newFixedThreadPool(capacity);
	}

	public static Concurrents alloc(int capacity) {
		return new Concurrents(capacity);
	}

	public static Concurrents alloc() {
		return alloc(INFINITI);
	}

	public void assign(Runnable exe) {
		this.executor.execute(exe);
	}
}
