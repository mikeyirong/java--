with (webkit) {
	includeJs("lib/jquery.min.js");
	includeJs("lib/utils.js");

	$(document)
			.ready(
					function() {
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
									$("#gmtBeginID")[0].value = "06/01/2016";
									$("#gmtEndID")[0].value = "07/01/2016";
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
														var url = "http://fund.aliexpress.com/exportBill.do?action=ExportBillAjaxAction&event_submit_do_download=xxx&_csrf_token="
																+ token
																+ "&tsp="
																+ new Date()
																		.getTime();

														var oReq = new XMLHttpRequest();
														oReq.open("GET", url,
																true);
														oReq.responseType = "arraybuffer";

														oReq.onload = function(
																oEvent) {
															var R = oReq.response;
															var bin = new Int8Array(
																	R);
															log("Get "
																	+ bin.length);
															processDownloadData(bin);
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