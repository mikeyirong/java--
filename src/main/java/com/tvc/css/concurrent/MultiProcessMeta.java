package com.tvc.css.concurrent;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 多核进程描述符
 * 
 * @author mclaren
 *
 */
public class MultiProcessMeta implements Runnable {
	private final String[] command;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public MultiProcessMeta(String... command) {
		super();
		this.command = command;
	}

	public String[] getCommand() {
		return command;
	}

	@Override
	public void run() {
		logger.info("Enter : " + getCommand().length);
		Process process = null;
		try {
			process = new ProcessBuilder(getCommand()).start();
			logger.info("XXXXXXXXXXXXX");
			InputStream stdout = process.getInputStream();
			InputStream stderr = process.getErrorStream();
			dumpProcessOutput(process, stdout);
			dumpProcessOutput(process, stderr);
			int exitcode = process.waitFor();
			logger.info("process {} has been exit with code {}", process, exitcode);
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			}
			throw new RuntimeException(e);
		} finally {
			if (process != null) {
				process.destroy();
			}
		}
	}

	void dumpProcessOutput(Process process, InputStream in) throws Exception {
		logger.info("+++++");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = null;
		while ((line = reader.readLine()) != null) {
			logger.info("Process {} output: {}", process, line);
		}

		in.close();
	}
}
