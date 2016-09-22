function UserDialog(h) {
	var e = 840;
	var l = 650;
	h = $.extend({}, {
		dialogWidth : e,
		dialogHeight : l,
		help : 0,
		status : 0,
		scroll : 0,
		center : 1
	}, h);
	var c = "dialogWidth=" + h.dialogWidth + "px;dialogHeight="
			+ h.dialogHeight + "px;help=" + h.help + ";status=" + h.status
			+ ";scroll=" + h.scroll + ";center=" + h.center;
	if (!h.isSingle) {
		h.isSingle = false;
	}
	var b = h.path + "/user/org/orgDialog.do?isSingle=" + h.isSingle+"&roleId= "+ h.roleId;
	b = b.getNewUrl();
	var g = new Array();
	if (h.ids && h.names) {
		var a = h.ids.split(",");
		var k = h.names.split(",");
		for ( var f = 0; f < a.length; f++) {
			var d = {
				id : a[f],
				name : k[f]
			};
			g.push(d);
		}
	} else {
		if (h.arguments) {
			g = h.arguments;
		}
	}
	var j = window.showModalDialog(b, g, c);
	if (h.callback) {
		if (j != undefined) {
			h.callback.call(this, j.orgId, j.orgName);
		}
	}
}

function OrgDeviceDialog(h) {
	var e = 840;
	var l = 650;
	h = $.extend({}, {
		dialogWidth : e,
		dialogHeight : l,
		help : 0,
		status : 0,
		scroll : 0,
		center : 1
	}, h);
	var c = "dialogWidth=" + h.dialogWidth + "px;dialogHeight="
			+ h.dialogHeight + "px;help=" + h.help + ";status=" + h.status
			+ ";scroll=" + h.scroll + ";center=" + h.center;
	if (!h.isSingle) {
		h.isSingle = false;
	}
	var b = h.path + "/user/org/orgDeviceDialog.do?isSingle=" + h.isSingle+"&status="+h.status;
	b = b.getNewUrl();
	var g = new Array();
	if (h.ids && h.names) {
		var a = h.ids.split(",");
		var k = h.names.split(",");
		for ( var f = 0; f < a.length; f++) {
			var d = {
				id : a[f],
				name : k[f]
			};
			g.push(d);
		}
	} else {
		if (h.arguments) {
			g = h.arguments;
		}
	}
	var j = window.showModalDialog(b, g, c);
	if (h.callback) {
		if (j != undefined) {
			h.callback.call(this, j.orgId, j.orgName);
		}
	}
}

function showDialog(url,width,height){
	// 如果打开的页面上有超链接 在页面<head></head>之间 加上<base target="_self"> 否则超链接会重新打开一个新窗口
	var leftPx = (screen.availWidth-width)/2;
	var topPx = (screen.availHeight-height)/2;
	var a = "dialogWidth=" + width + "px;dialogHeight="
	+ height + "px;dialogLeft="+leftPx+"px;dialogTop="+topPx+"px;help=0 ;status=0;scroll=0;center=1";
	window.showModalDialog(url,"", a);
}

function openDialog(url,width,height){
	var h = screen.availHeight - screen.availHeight*0.2;
	var w =  screen.availWidth - screen.availWidth*0.3;
	var a = "top="+(screen.availHeight-h)*0.5+",left="+(screen.availWidth-w)*0.5+",height="+h+",width="+w+",status=no,toolbar=no,menubar=no,location=no,resizable=0,scrollbars=1";
//	var leftPx = (screen.availWidth-width)/2;
//	var topPx = (screen.availHeight-height)/2;
//	var a = "width=" + width + "px,height="
//	+ height + "px,left="+leftPx+"px,top="+topPx+"px,status=0,toolbar=0,menubar=0,location=0,resizable=0,scrollbars=1";
	window.open(url, "",a,true);
}

String.prototype.getNewUrl = function() {
	var c = new Date().getTime();
	var b = this;
	if (b.indexOf("#") != -1) {
		var a = b.lastIndexOf("#", b.length - 1);
		b = b.substring(0, a);
	}
	while (b.endWith("#")) {
		b = b.substring(0, b.length - 1);
	}
	b = b.replace(/(\?|&)rand=\d*/g, "");
	if (b.indexOf("?") == -1) {
		b += "?rand=" + c;
	} else {
		b += "&rand=" + c;
	}
	return b;
};

String.prototype.endWith = function(c, b) {
	if (c == null || c == "" || this.length == 0 || c.length > this.length) {
		return false;
	}
	var a = this.substring(this.length - c.length);
	if (b == undefined || b) {
		return a == c;
	} else {
		return a.toLowerCase() == c.toLowerCase();
	}
};

