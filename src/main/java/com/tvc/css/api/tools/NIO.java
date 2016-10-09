package com.tvc.css.api.tools;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.zip.GZIPInputStream;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.DefaultMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

/**
 * 网络调用
 * 
 * @author mclaren
 *
 */
@SuppressWarnings("deprecation")
public abstract class NIO<I, O> {
	public abstract O communicate(I input) throws Exception;

	public static HttpGetIO asHttpGet() {
		return new HttpGetIO();
	}

	public static HttpPostIO asHttpPost() {
		return new HttpPostIO();
	}

	public static class HttpPostRequest {
		private final String url;
		private final Header[] headers;
		private final InputStream body;

		public HttpPostRequest(String url, Header[] headers, InputStream body) {
			super();
			this.url = url;
			this.headers = headers;
			this.body = body;
		}

		public String getUrl() {
			return url;
		}

		public Header[] getHeaders() {
			return headers;
		}

		public InputStream getBody() {
			return body;
		}
	}

	public static class HttpGetIO extends NIO<String, String> {
		@Override
		public String communicate(String url) throws Exception {
			try {
				HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));

				client.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
				client.getHttpConnectionManager().getParams().setSoTimeout(15000);
				DefaultMethodRetryHandler retryHandler = new DefaultMethodRetryHandler();
				retryHandler.setRetryCount(1);
				retryHandler.setRequestSentRetryEnabled(false);

				GetMethod get = new GetMethod(url);
				get.setMethodRetryHandler(retryHandler);

				int code = client.executeMethod(get);

				if (code == 302) {
					String directUrl = get.getResponseHeader("Location").getValue();

					return communicate(directUrl);
				} else {
					String encoding = get.getResponseCharSet();
					InputStream inputstream = get.getResponseBodyAsStream();
					Header header = get.getResponseHeader("Content-Encoding");
					if (header != null) {
						if (header.getValue().contains("gzip")) {
							inputstream = new GZIPInputStream(inputstream);
						}
					}
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					int i = 0;
					byte[] bytes = new byte[1024];

					do {
						i = inputstream.read(bytes);
						if (i != -1)
							out.write(bytes, 0, i);
					} while (i != -1);

					String s = new String(out.toByteArray(), encoding);
					return s;
				}
			} catch (

			SocketTimeoutException e) {
				return communicate(url);
			} catch (ConnectTimeoutException e) {
				return communicate(url);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static class HttpPostIO extends NIO<HttpPostRequest, String> {

		@Override
		public String communicate(HttpPostRequest input) throws Exception {
			try {
				HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));

				client.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
				client.getHttpConnectionManager().getParams().setSoTimeout(15000);
				DefaultMethodRetryHandler retryHandler = new DefaultMethodRetryHandler();
				retryHandler.setRetryCount(1);
				retryHandler.setRequestSentRetryEnabled(false);

				PostMethod post = new PostMethod(input.getUrl());

				post.setRequestBody(input.getBody());
				post.setMethodRetryHandler(retryHandler);

				for (Header header : input.getHeaders()) {
					post.addRequestHeader(header);
				}

				int code = client.executeMethod(post);

				if (code == 302) {
					String directUrl = post.getResponseHeader("Location").getValue();
					return NIO.asHttpGet().communicate(directUrl);
				} else {
					String encoding = post.getResponseCharSet();

					InputStream inputstream = post.getResponseBodyAsStream();
					Header header = post.getResponseHeader("Content-Encoding");
					if (header != null) {
						if (header.getValue().contains("gzip")) {
							inputstream = new GZIPInputStream(inputstream);
						}
					}
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					int i = 0;
					byte[] bytes = new byte[1024];

					do {
						i = inputstream.read(bytes);
						if (i != -1)
							out.write(bytes, 0, i);
					} while (i != -1);

					String s = new String(out.toByteArray(), encoding);
					return s;
				}
			} catch (SocketTimeoutException e) {
				return communicate(input);
			} catch (ConnectTimeoutException e) {
				return communicate(input);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

}