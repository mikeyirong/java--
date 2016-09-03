package com.tvc.css;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.sun.javafx.application.PlatformImpl;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import static javafx.concurrent.Worker.State.FAILED;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
//import org.unesco.jisis.jisisutils.StringUtils;

/**
 * SwingFXWebView
 */
public class SwingFXWebView extends JPanel {

	private JFXPanel jfxPanel = null;
	private WebView webView;
	private WebEngine webEngine;
	private static final int PANEL_WIDTH_INT = 675;
	private static final int PANEL_HEIGHT_INT = 400;
	private JLabel lblStatus = new JLabel();
	private JProgressBar progressBar = new JProgressBar();

	/**
	 * There are some restrictions related to JFXPanel. As a Swing component, it
	 * should only be accessed from the event dispatch thread, except the
	 * setScene(javafx.scene.Scene) method, which can be called either on the
	 * event dispatch thread or on the JavaFX application thread.
	 */
	public SwingFXWebView() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Platform.setImplicitExit(false);
				initAndShowGUI();
			}
		});

	}

	private void initAndShowGUI() {
		jfxPanel = new JFXPanel();
		progressBar.setPreferredSize(new Dimension(150, 18));
		progressBar.setStringPainted(true);

		JPanel statusBar = new JPanel(new BorderLayout(5, 0));
		statusBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
		statusBar.add(lblStatus, BorderLayout.CENTER);
		statusBar.add(progressBar, BorderLayout.EAST);

		setLayout(new BorderLayout());
		add(jfxPanel, BorderLayout.CENTER);
		add(statusBar, BorderLayout.SOUTH);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				initFX();
			}
		});
	}

	private void initFX() {
		// This method is invoked on JavaFX thread
		Scene scene = createScene();
		jfxPanel.setScene(scene);
	}

	/**
	 * createScene
	 * 
	 * Note: The Key ISSUE is that Scene needs to be created and run on
	 * "FX user thread" and NOT on the AWT-EventQueue Thread
	 * 
	 */

	private Scene createScene() {

		// Set up the embedded browser:
		webView = new WebView();
		webView.setPrefSize(700, 500);
		Double widthDouble = new Integer(PANEL_WIDTH_INT).doubleValue();
		Double heightDouble = new Integer(PANEL_HEIGHT_INT).doubleValue();
		webView.setMinSize(widthDouble, heightDouble);
		webView.setPrefSize(widthDouble, heightDouble);
		webEngine = webView.getEngine();

		registerListeners();

		Scene scene = new Scene(webView);
		return (scene);
	}

	private void registerListeners() {

		webEngine.titleProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, final String newValue) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						updateUI();
					}
				});
			}
		});
		// handle popup windows
		webEngine.setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() { // todo
																					// should
																					// create
																					// a
																					// new
																					// tab.
			@Override
			public WebEngine call(PopupFeatures popupFeatures) {
				Stage popupStage = new Stage();
				final WebView popupWebView = new WebView();
				final Scene popupScene = new Scene(popupWebView);
				popupStage.setScene(popupScene);
				popupStage.setResizable(popupFeatures.isResizable());
				popupWebView.prefWidthProperty().bind(popupScene.widthProperty());
				popupWebView.prefHeightProperty().bind(popupScene.heightProperty());
				popupStage.show();

				return popupWebView.getEngine();
			}
		});

		webEngine.setOnStatusChanged(new EventHandler<WebEvent<String>>() {
			@Override
			public void handle(final WebEvent<String> event) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						lblStatus.setText(event.getData());
					}
				});
			}
		});

		webEngine.getLoadWorker().workDoneProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldValue,
					final Number newValue) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						progressBar.setValue(newValue.intValue());
					}
				});
			}
		});

		webEngine.getLoadWorker().exceptionProperty().addListener(new ChangeListener<Throwable>() {
			public void changed(ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) {
				if (webEngine.getLoadWorker().getState() == FAILED) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							JOptionPane.showMessageDialog(jfxPanel,
									(value != null) ? webEngine.getLocation() + "\n" + value.getMessage()
											: webEngine.getLocation() + "\nUnexpected error.",
									"Loading error...", JOptionPane.ERROR_MESSAGE);
						}
					});
				}
			}
		});

		webEngine.locationProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String oldLoc, String newLoc) {
				// check if the newLoc corresponds to a file you want to be
				// downloadable
				// and if so trigger some code and dialogs to handle the
				// download.
				if (newLoc.endsWith(".pdf")) {
					try {
						// final PDFViewer pdfViewer = new PDFViewer(true); //
						// todo try icepdf viewer instead...
						// pdfViewer.openFile(new URL(newLoc))

						// final IcePdfViewer icePdfViewer = new
						// IcePdfViewer(newLoc);
						return;
					} catch (Exception ex) {
						// just fail to open a bad pdf url silently - no action
						// required.
					}
				}
				String downloadableExtension = null; // todo I wonder how to
														// find out from WebView
														// which documents it
														// could not process so
														// that I could trigger
														// a save as for them?
				String[] downloadableExtensions = { ".doc", ".docx", ".pdf", ".xls", ".odt", ".zip", ".tgz", ".jar" };
				for (String ext : downloadableExtensions) {
					if (newLoc.endsWith(ext)) {
						downloadableExtension = ext;
						break;
					}
				}
				if (downloadableExtension != null) {
					// create a file save option for performing a download.
					FileChooser chooser = new FileChooser();
					chooser.setTitle("Save " + newLoc);
					chooser.getExtensionFilters()
							.add(new FileChooser.ExtensionFilter("Downloadable File", downloadableExtension));
					int filenameIdx = newLoc.lastIndexOf("/") + 1;
					if (filenameIdx != 0) {
						String fileName = newLoc.substring(filenameIdx);

						File saveFile = chooser.showSaveDialog(webView.getScene().getWindow());

						if (saveFile != null) {
							BufferedInputStream is = null;
							BufferedOutputStream os = null;
							try {
								is = new BufferedInputStream(new URL(newLoc).openStream());
								os = new BufferedOutputStream(new FileOutputStream(saveFile));
								int b = is.read();
								while (b != -1) {
									os.write(b);
									b = is.read();
								}
							} catch (FileNotFoundException e) {
								System.out.println("Unable to save file: " + e);
							} catch (MalformedURLException e) {
								System.out.println("Unable to save file: " + e);
							} catch (IOException e) {
								System.out.println("Unable to save file: " + e);
							} finally {
								try {
									if (is != null) {
										is.close();
									}
								} catch (IOException e) {
									/**
									 * no action required.
									 */
								}
								try {
									if (os != null) {
										os.close();
									}
								} catch (IOException e) {
									/**
									 * no action required.
									 */
								}
							}
						}
						// todo provide feedback on the save function and
						// provide a download list and download list lookup.
					}
				}

			}
		});

	}

	public static void open(File document) throws IOException {
		Desktop dt = Desktop.getDesktop();
		dt.open(document);
	}

	public void loadContent(final String htmlContent) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				PlatformImpl.runLater(new Runnable() {
					@Override
					public void run() {

						webEngine.loadContent(htmlContent);

					}
				});
			}
		});

	}

	public void executeScript(final java.lang.String script) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				PlatformImpl.runLater(new Runnable() {
					@Override
					public void run() {

						webEngine.executeScript(script);
					}
				});
			}
		});
	}

	public static void main(String[] args) {
		new SwingFXWebView();
	}
}
