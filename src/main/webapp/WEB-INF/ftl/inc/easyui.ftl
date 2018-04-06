<script src="${static}/js/jquery-easyui-1.2.6/jquery-1.7.2.min.js" charset="UTF-8" type="text/javascript"></script>
<script src="${static}/js/jquery.cookie.js" charset="UTF-8" type="text/javascript"></script>
<link id="easyuiTheme" href="${static}/js/jquery-easyui-1.2.6/themes/default/easyui.css" rel="stylesheet" type="text/css" media="screen"/>
<script src="${static}/js/changeEasyuiTheme.js" charset="UTF-8" type="text/javascript"></script>
<link href="${static}/js/jquery-easyui-1.2.6/themes/icon.css" rel="stylesheet" type="text/css" media="screen"/>
<script src="${static}/js/jquery-easyui-1.2.6/jquery.easyui.min.js" charset="UTF-8" type="text/javascript"></script>
<script src="${static}/js/jquery-easyui-1.2.6/locale/easyui-lang-zh_CN.js" charset="UTF-8" type="text/javascript"></script>
<link href="${static}/css/baseCss.css" rel="stylesheet" type="text/css" media="screen"/>
<script src="${static}/js/syUtils.js" charset="UTF-8" type="text/javascript"></script>

<script type="text/javascript" charset="UTF-8">

   //对返回的数字格式的日期进行日期格式化
  Date.prototype.format = function(format) {
    var o = {
        "M+": this.getMonth() + 1, //month 
        "d+": this.getDate(), //day 
        "h+": this.getHours(), //hour 
        "m+": this.getMinutes(), //minute 
        "s+": this.getSeconds(), //second 
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter 
        "S": this.getMilliseconds() //millisecond 
    }
    if (/(y+)/.test(format)) 
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o) 
        if (new RegExp("(" + k + ")").test(format)) 
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
}
$(function(){
	$(window).keydown(function(e){
		if(e.keyCode==13){
			var a = $("a[iconCls='icon-search']");
			if(a.length>0){
				a[0].click();
			}
		}
	});
});
function rotate(id, angle, whence) {
      var p = document.getElementById(id);
      if (!whence) {
        p.angle = ((p.angle == undefined ? 0 : p.angle) + angle) % 360;
      }
      else {
        p.angle = angle;
      }
      if (p.angle >= 0) {
        var rotation = Math.PI * p.angle / 180;
      }
      else {
        var rotation = Math.PI * (360 + p.angle) / 180;
      }
      var costheta = Math.cos(rotation);
      var sintheta = Math.sin(rotation);
      if (document.all && !window.opera) {
        var canvas = document.createElement('img');
        canvas.src = p.src;
        canvas.height = p.height;
        canvas.width = p.width;
        canvas.style.filter = "progid:DXImageTransform.Microsoft.Matrix(M11=" + costheta + ",M12=" + (-sintheta) + ",M21=" + sintheta + ",M22=" + costheta + ",SizingMethod='auto expand')";
      }
      else {
        var canvas = document.createElement('canvas');
        if (!p.oImage) {
          canvas.oImage = new Image();
          canvas.oImage.src = p.src;
        }
        else {
          canvas.oImage = p.oImage;
        }
        //alert(canvas.width)
        canvas.style.width = canvas.width = Math.abs(costheta * canvas.oImage.width) + Math.abs(sintheta * canvas.oImage.height);
        canvas.style.height = canvas.height = Math.abs(costheta * canvas.oImage.height) + Math.abs(sintheta * canvas.oImage.width);
        var context = canvas.getContext('2d');
        context.save();
        if (rotation <= Math.PI / 2) {
          context.translate(sintheta * canvas.oImage.height, 0);
        }
        else if (rotation <= Math.PI) {
          context.translate(canvas.width, -costheta * canvas.oImage.height);
        }
        else if (rotation <= 1.5 * Math.PI) {
          context.translate(-costheta * canvas.oImage.width, canvas.height);
        }
        else {
          context.translate(0, -sintheta * canvas.oImage.width);
        }
        context.rotate(rotation);
        context.drawImage(canvas.oImage, 0, 0, canvas.oImage.width, canvas.oImage.height);
        context.restore();
      }
      canvas.id = p.id;
      canvas.angle = p.angle;
      p.parentNode.replaceChild(canvas, p);
    }

</script>