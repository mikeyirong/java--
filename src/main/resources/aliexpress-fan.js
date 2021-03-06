with (webkit) {
	includeJs("lib/jquery.min.js");
	includeJs("lib/utils.js");

	window.onerror = function(a, b, c) {
		log("出现错误：：：：" + a + "  =>  " + b + "   =>" + c);
	};
	$(document)
			.ready(
					function() {
						var start = "09/01/2016";
						var end = "10/01/2016";
						var startToend = start + "---" + end;
						/**
						 * 执行登录
						 */
						if (window.location.href
								.startsWith("https://login.aliexpress.com/")) {
							var doc = window.frames['alibaba-login-box'].document;
							$("#fm-login-id", doc).val(seller.getPrincipal());
							$("#fm-login-password", doc).val(
									seller.getCredentials());
							$("#fm-login-submit", doc).trigger("click");
						}

						else if (window.location.href
								.startsWith("http://fund.aliexpress.com/exportBill.htm")
								|| window.location.href
										.startsWith("https://fund.aliexpress.com/exportBill.htm")) {
							/**
							 * 执行抓取
							 */
							var trigger = $("#orderExportDownload");
							if (trigger.length == 0) {
								log("Matching............")
								$("#gmtBeginID").removeAttr("readonly");
								$("#gmtEndID").removeAttr("readonly");
								var createHidden = function(a, b) {
									var e = $(document.createElement("input"));
									e.attr("name", a).attr("value", b).attr(
											"type", "text").attr("id", a);
									return e;
								};
								var form = $("#orderExportForm");

								setTimeout(function() {
									$("#gmtBeginID")[0].value = start;
									$("#gmtEndID")[0].value = end;
									$("#orderExportSubmit").trigger("click");
								}, 2000);

							} else {
								log("==============Verify ========"
										+ runParm.ajaxUrl);
								var checking = function() {
									$
											.ajax({
												url : runParm.ajaxUrl,
												type : 'GET',
												success : function(d) {
													log("++>"
															+ JSON.stringify(d));
													if (d.status == 'running') {
														setTimeout(function() {
															checking();
														}, 2000);
													} else {
														var token = $("#orderExportSuccessForm")[0]._csrf_token.value;
														log("Token is " + token);
														var url = "https://fund.aliexpress.com/exportBill.do?action=ExportBillAjaxAction&event_submit_do_download=xxx&_csrf_token="
																+ token;
														log("url" + url);
														var oReq = new XMLHttpRequest();
														oReq.open("GET", url,
																true);
														oReq.onprogress = function(
																evt) {
															log("Loading "
																	+ evt.total
																	+ "  "
																	+ evt.loaded);
														};
														oReq.responseType = "arraybuffer";

														oReq.onload = function(
																oEvent) {
															var R = oReq.response;
															var bin = new Int8Array(
																	R);
															if (bin.length > 0) {
																processDownloadData(
																		bin,
																		startToend,
																		seller);
															}else{
																log("数据未取到------》");
															}
															shutdown();
														};

														oReq.send();
													}
												}
											});
								};

								checking();

							}
						} else {
							log("进入例外..." + window.location);
						}
					});

}