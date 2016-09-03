/*
 *  Visual Crawler Utils Library
 *  @author liuhao0927@163.com
 */
origin_height = 0;
sames = 0;
PERIOD = 1000;

_MAIN_ = function(fun) {
	if(window.location == 'about:blank') {
		return;
	}


	window.runno = window.runno || 0;
	if (window.runno == 0) {
		setTimeout(fun, 1000);
	}
	window.runno++;
}

has_next = function(callback) {
	var t = null;
	var proc = function() {
		var h = $(document).height() - $(window).height();
		if (origin_height == $(document).height()) {
			var last = $("#ui_search_load_more").length
			if (last == 0) {
				clearTimeout(t);
				sames = 0;
				callback();
				return;
			}
			sames++;

			/**
			 * Exit loop then matched 15 times
			 */
			if (sames == 15) {
				log("retrieved data with timeout(" + (15 * PERIOD) / 1000
						+ ") seconds");
				clearTimeout(t);
				sames = 0;
				callback();
				return;
			}
			has_next(callback);

		} else {
			sames = 0;
			origin_height = $(document).height();
			$(document).scrollTop(h);

			has_next(callback);
		}
	}
	t = setTimeout(proc, PERIOD);
}

String.prototype.startsWith = function(str) {
	var reg = new RegExp("^" + str);
	return reg.test(this);
}