package com.tvc.css.api.tools;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.tvc.css.api.aliexpress.financial.AliExpressSellerAccount;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

/**
 * Google webkit
 * 
 * @author Administrator
 *
 */
public class FxWebkit extends Application {
	static String url;

	static String analyzer;

	static File downLoadDirectory;

	static AliExpressSellerAccount seller;

	public static void fire() {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Scene scene = new Scene(new Browser(), 750, 500, Color.web("#666970"));
		stage.setScene(scene);
		stage.show();
	}

	class Browser extends Region {
		final WebView browser = new WebView();
		final WebEngine webEngine = browser.getEngine();
		private Logger logger = LoggerFactory.getLogger(getClass());

		public Browser() {
			// apply the styles
			getStyleClass().add("browser");
			IO.DeleteFolder(downLoadDirectory.getAbsolutePath());
			webEngine.setUserDataDirectory(downLoadDirectory);

			webEngine.locationProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (newValue.contains("action=ExportBillAjaxAction&event_submit_do_download")) {
						logger.info("Downloading " + newValue);
					}
				}
			});

			webEngine.documentProperty().addListener(new ChangeListener<Document>() {
				@Override
				public void changed(ObservableValue<? extends Document> prop, Document oldDoc, Document newDoc) {
					enableFirebug(webEngine);
				}
			});
			webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
				@Override
				public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
					if (newValue == State.SUCCEEDED) {
						try {
							JSObject window = (JSObject) webEngine.executeScript("window");
							window.setMember("webkit", new FxWebkitFunctions(webEngine));
							window.setMember("seller", seller);
							webEngine.executeScript(new String(IO.loadFromClasspath(analyzer)));
						} catch (Exception e) {
							logger.error("解析错误", e);
							Platform.exit();
						}
					}
				}
			});
			// load the web page
			webEngine.load(url);
			// add the web view to the scene
			getChildren().add(browser);

		}

		private void enableFirebug(final WebEngine engine) {
			engine.executeScript(
					"if (!document.getElementById('FirebugLite')){E = document['createElement' + 'NS'] && document.documentElement.namespaceURI;E = E ? document['createElement' + 'NS'](E, 'script') : document['createElement']('script');E['setAttribute']('id', 'FirebugLite');E['setAttribute']('src', 'https://getfirebug.com/' + 'firebug-lite.js' + '#startOpened');E['setAttribute']('FirebugLite', '4');(document['getElementsByTagName']('head')[0] || document['getElementsByTagName']('body')[0]).appendChild(E);E = new Image;E['setAttribute']('src', 'https://getfirebug.com/' + '#startOpened');}");
		}

		@Override
		protected void layoutChildren() {
			double w = getWidth();
			double h = getHeight();
			layoutInArea(browser, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
		}

		@Override
		protected double computePrefWidth(double height) {
			return 750;
		}

		@Override
		protected double computePrefHeight(double width) {
			return 500;
		}
	}
}
