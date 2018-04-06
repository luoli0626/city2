/**
 * 包含easyui的扩展和常用的方法
 * 
 * @author  
 */

var sy = $.extend({}, sy);/* 全局对象 */




$.parser.auto = false;
$(function() {
	$.messager.progress({
		text : '页面加载中....',
		interval : 100
	});
	$.parser.parse(window.document);
	window.setTimeout(function() {
		$.messager.progress('close');
		if (self != parent) {
			window.setTimeout(function() {
				parent.$.messager.progress('close');
			}, 500);
		}
	}, 1);
	$.parser.auto = true;
});

$.fn.panel.defaults.onBeforeDestroy = function() {/* tab关闭时回收内存 */
	var frame = $('iframe', this);
	try {
		if (frame.length > 0) {
			frame[0].contentWindow.document.write('');
			frame[0].contentWindow.close();
			frame.remove();
			if ($.browser.msie) {
				CollectGarbage();
			}
		} else {
			$(this).find('.combo-f').each(function() {
				var panel = $(this).data().combo.panel;
				panel.panel('destroy');
			});
		}
	} catch (e) {
	}
};

$.fn.panel.defaults.loadingMessage = '数据加载中，请稍候....';
$.fn.datagrid.defaults.loadMsg = '数据加载中，请稍候....';

var easyuiErrorFunction = function(XMLHttpRequest) {
	/* $.messager.progress('close'); */
	/*alert(XMLHttpRequest.responseText.split('<script')[0]); */
	$.messager.alert('错误', "操作失败，请刷新页面后重试！");
};
$.fn.datagrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.treegrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combogrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combobox.defaults.onLoadError = easyuiErrorFunction;
$.fn.form.defaults.onLoadError = easyuiErrorFunction;

var easyuiPanelOnMove = function(left, top) {/* 防止超出浏览器边界 */
	if (left < 0) {
		$(this).window('move', {
			left : 1
		});
	}
	if (top < 0) {
		$(this).window('move', {
			top : 1
		});
	}
};
$.fn.panel.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;

$.extend($.fn.validatebox.defaults.rules, {
	eqPassword : {/* 扩展验证两次密码 */
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '密码不一致！'
	}
});

$.extend($.fn.datagrid.defaults.editors, {
	combocheckboxtree : {
		init : function(container, options) {
			alert("options");
			var editor = $('<input/>').appendTo(container);
			options.multiple = true;
			editor.combotree(options);
			return editor;
		},
		destroy : function(target) {
			$(target).combotree('destroy');
		},
		getValue : function(target) {
			return $(target).combotree('getValues').join(',');
		},
		setValue : function(target, value) {
			$(target).combotree('setValues', sy.getList(value));
		},
		resize : function(target, width) {
			$(target).combotree('resize', width);
		}
	}
});
/**
 * 时间校验扩展
 * CloseTime 结束时间需要大于开始时间
 * TimeBeginer 某些开始时间必须是晚于系统时间
 */
$.extend($.fn.validatebox.defaults.rules,{ 
	CloseTime:{ 
		validator : function(value, param) {
			var btstr=$(param[0]).val();
			var beginTime=dateFormat(btstr);
			var endTime=dateFormat(value);
			return beginTime<endTime;
		},
		message : '结束时间必须要晚于开始时间!'
	},
	TimeBeginer:{
		validator :function(value, param){
			var nowTime=new Date();
			var beginTime=dateFormat(value);
			return nowTime<beginTime;
		},
		message : '开始时间不能早于当前时间!'
	},
	phone: {// 验证电话号码
		validator: function (value) {
		return /^((\d2,3)|(\d{3}\-))?(0\d2,3|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
		},
		message: '格式不正确,请使用下面格式:020-88888888'
	},
	mobile: {// 验证手机号码
		validator: function (value) {
			return /^(13|15|18)\d{9}$/i.test(value);
		},
	message: '手机号码格式不正确'
	},
	qq: {// 验证qq
		validator: function (value) {
			return /^\d{5,10}$/.test(value);
		},
	message: 'qq号码格式不正确'
	},
	multiple:{//混合验证
		validator:function(value,vtypes){
			var returnFlag = true;
			var opts = $.fn.validatebox.defaults;
			var returnFlag = true;
			var opts = $.fn.validatebox.defaults;
			for (var i = 0; i < vtypes.length; i++) {
				var methodinfo = /([a-zA-Z_]+)(.*)/.exec(vtypes[i]);
				var rule = opts.rules[methodinfo[1]];
				if (value && rule) {
					var parame = eval(methodinfo[2]);
					if (!rule["validator"](value, parame)) {
						returnFlag = false;
						this.message = rule.message;
						break;
					}
				}
			}
			return returnFlag;
		},
		message:"多重验证不正确"
	}
});
/**
 * 此函数专用处理(2012-05-05 12：23：22)格式的日期格式化
 * @param {} str
 * @return {}
 */
function dateFormat(str){
	str=str.replace(/:/g, "-");
	str=str.replace(/ /g, "-");
	str=str.trim();
	var da=str.split("-");
	var d=new Date(da[0],da[1]-1,da[2],da[3],da[4],da[5]);
	return d;
}
/**
 * 获得项目根路径
 * 
 * 使用方法：sy.bp();
 */
sy.bp = function() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos);
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
};
/**
 * 获取上下文路径
 */
sy.ctx = function(){
	var path = window.location.pathname;
	return path.substring(0, path.substr(1).indexOf('/')+1);
}
/**
 * 增加formatString功能
 * 
 * 使用方法：sy.fs('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
 */
sy.fs = function(str) {
	for ( var i = 0; i < arguments.length - 1; i++) {
		str = str.replace("{" + i + "}", arguments[i + 1]);
	}
	return str;
};

/**
 * 增加命名空间功能
 * 
 * 使用方法：sy.ns('jQuery.bbb.ccc','jQuery.eee.fff');
 */
sy.ns = function() {
	var o = {}, d;
	for ( var i = 0; i < arguments.length; i++) {
		d = arguments[i].split(".");
		o = window[d[0]] = window[d[0]] || {};
		for ( var k = 0; k < d.slice(1).length; k++) {
			o = o[d[k + 1]] = o[d[k + 1]] || {};
		}
	}
	return o;
};

/**
 * 生成UUID
 */
sy.random4 = function() {

/*
	return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
	*/
	return Math.round((1 + Math.random()) * 10000);
};
sy.UUID = function() {
/*
	return (sy.random4() + sy.random4() + "-" + sy.random4() + "-" + sy.random4() + "-" + sy.random4() + "-" + sy.random4() + sy.random4() + sy.random4());
*/
	return (sy.random4()+""+sy.random4()+""+sy.random4()+""+sy.random4());

};
/**
 * 生成随机数字
 */
sy.randommath = function() {
	var i = Math.round((1 + Math.random()) * 1000);
	var str = i.toString();
	//alert(str);
	return str;
};
sy.COMMONID = function() {
	var str = (sy.randommath()+sy.randommath()+sy.randommath()+Math.round((1 + Math.random())));
	return (str);
};

/**
 * 获得URL参数
 */
sy.getUrlParam = function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
};

sy.getList = function(value) {
	if (value) {
		var values = [];
		var t = value.split(',');
		for ( var i = 0; i < t.length; i++) {
			values.push('' + t[i]);/* 避免他将ID当成数字 */
		}
		return values;
	} else {
		return [];
	}
};

sy.png = function() {
	var imgArr = document.getElementsByTagName("IMG");
	for ( var i = 0; i < imgArr.length; i++) {
		if (imgArr[i].src.toLowerCase().lastIndexOf(".png") != -1) {
			imgArr[i].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + imgArr[i].src + "', sizingMethod='auto')";
			imgArr[i].src = "images/blank.gif";
		}
		if (imgArr[i].currentStyle.backgroundImage.lastIndexOf(".png") != -1) {
			var img = imgArr[i].currentStyle.backgroundImage.substring(5, imgArr[i].currentStyle.backgroundImage.length - 2);
			imgArr[i].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + img + "', sizingMethod='crop')";
			imgArr[i].style.backgroundImage = "url('images/blank.gif')";
		}
	}
};
sy.bgPng = function(bgElements) {
	for ( var i = 0; i < bgElements.length; i++) {
		if (bgElements[i].currentStyle.backgroundImage.lastIndexOf(".png") != -1) {
			var img = bgElements[i].currentStyle.backgroundImage.substring(5, bgElements[i].currentStyle.backgroundImage.length - 2);
			bgElements[i].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + img + "', sizingMethod='crop')";
			bgElements[i].style.backgroundImage = "url('images/blank.gif')";
		}
	}
};

sy.isLessThanIe8 = function() {/* 判断浏览器是否是IE并且版本小于8 */
	return ($.browser.msie && $.browser.version < 8);
};

$.ajaxSetup({
	type : 'POST',
	error : function(XMLHttpRequest, textStatus, errorThrown) {/* 扩展AJAX出现错误的提示 */
		$.messager.progress('close');
		$.messager.alert('错误', XMLHttpRequest.responseText.split('<script')[0]);
	}
});
sy.ajaxSubmit = function(params){
	var contentType="application/x-www-form-urlencoded";
	if(params.contentType){
		contentType=params.contentType;
	}
	var dataType="json";
	if(params.dataType){
		dataType=params.dataType;
	}
	var type="post";
	if(params.type){
		type=params.type;
	}
	$.ajax({
		type : type,
		url : params.url,
		data : params.data,
		cache : false,
		contentType:contentType,
		dataType:dataType,
		success : function(resp) {
			if(params.success!=null){
					params.success(resp);
				}
		},
		error : function() {
			$.messager.alert("错误","操作失败,请刷新页面后重试");
			if(params.error!=null){
				params.error();	
			}
		},
		complete : function(XMLHttpRequest){
			var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头，sessionstatus
	         if(sessionstatus=="timeout"){   
	        	 //超时就处理  
	           window.location.replace(sy.ctx()+"/error/timeout.jsp");   
	         }
	         var permission=XMLHttpRequest.getResponseHeader("permission");
	         if(permission=="No"){
	        	 //没有权限处理
	        	 window.location.replace(sy.ctx()+"/error/noright.jsp"); 
	         }
		}
	});
}
sy.loadingStart=function(){
	$("<div class=\"datagrid-mask\"></div>").css({"z-index":"99999",display:"block",width:"100%",height:$(window).height()}).appendTo("body"); 
	$("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候....").appendTo("body").css({"z-index":"99999",display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
};
sy.loadingEnd=function(){
	$(".datagrid-mask").remove();
	$(".datagrid-mask-msg").remove();
};
jQuery.fn.serializeJson=function(){
	   var serializeObj={};
	   var array=this.serializeArray();
	   var str=this.serialize();
	   $(array).each(function(){
		   if(serializeObj[this.name]){
			   if($.isArray(serializeObj[this.name])){
				   serializeObj[this.name].push(this.value);
			   }else{
				   var temp = serializeObj[this.name]+","+this.value;
				   serializeObj[this.name]=temp;  
			   }
		   }else{
			   serializeObj[this.name]=this.value;
		   }
	   });
	    return serializeObj;
	  };