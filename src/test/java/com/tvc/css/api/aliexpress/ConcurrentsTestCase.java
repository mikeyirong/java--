package com.tvc.css.api.aliexpress;

import com.tvc.css.concurrent.Concurrents;
import com.tvc.css.concurrent.MultiProcessMeta;

import junit.framework.TestCase;

public class ConcurrentsTestCase extends TestCase {
	public void testConcurrent() throws Exception {
		Concurrents con = Concurrents.alloc();
		for (int i = 0; i < 1000; i++) {
			con.assign(new Runnable() {
				@Override
				public void run() {
					System.out.println("It works");
				}
			});
		}
	}

	public static void main(String[] args) throws Exception {
		Concurrents con = Concurrents.alloc(5);
		for (int i = 0; i < 1000; i++) {
			con.assign(new MultiProcessMeta("java", "-version"));
		}
	}
}
