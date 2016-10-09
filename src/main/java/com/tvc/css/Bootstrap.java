package com.tvc.css;

import com.tvc.css.concurrent.Concurrents;
import com.tvc.css.concurrent.MultiProcessMeta;

public class Bootstrap {

	/**
	 * 启动入口
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Concurrents concurrent = Concurrents.alloc();
		for (int i = 0; i < 13; i++) {
			concurrent.assign(new MultiProcessMeta(args[0], i + ""));
		}

	}
}
