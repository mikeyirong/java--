package com.tvc.css.api.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Platform;
import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;

public class FxWebkitFunctions {
	private final WebEngine engine;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public FxWebkitFunctions(WebEngine engine) {
		super();
		this.engine = engine;
	}

	public void shutdown() {
		Platform.exit();
	}

	public void log(String name) {
		logger.info(name);
	}

	public WebEngine getEngine() {
		return engine;
	}

	/**
	 * 包含外部JS
	 * 
	 * @param name
	 * @throws Exception
	 */
	public void includeJs(String url) throws Exception {
		String code = "";
		if (url.startsWith("http")) {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.connect();
			InputStream in = connection.getInputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int len = -1;
			byte[] buf = new byte[1024];
			do {
				len = in.read(buf);
				if (len != -1) {
					out.write(buf, 0, len);
				}
			} while (len != -1);
			code = new String(out.toByteArray());
		} else {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(url);
			byte[] buf = new byte[in.available()];
			in.read(buf);
			in.close();
			code = new String(buf);
		}

		engine.executeScript(code);
	}

	public void processDownloadData(JSObject R) {
		try {
			int length = (Integer) R.getMember("length");
			byte[] buffer = new byte[length];
			for (int i = 0; i < length; i++) {
				int item = (Integer) R.getMember("" + i);
				buffer[i] = (byte) item;
			}

			/**
			 * 读EXCEL
			 */
			ByteArrayInputStream in = new ByteArrayInputStream(buffer);
			HSSFWorkbook workbook = new HSSFWorkbook(in);
			HSSFSheet sheet = workbook.getSheetAt(0);
			logger.info("EXCEL总共" + sheet.getPhysicalNumberOfRows() + "行");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
