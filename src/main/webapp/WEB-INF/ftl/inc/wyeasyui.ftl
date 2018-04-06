<script src="${static}/js/jquery-easyui-1.2.6/jquery-1.7.2.min.js" charset="UTF-8" type="text/javascript"></script>
<script src="${static}/js/jquery.cookie.js" charset="UTF-8" type="text/javascript"></script>
<link id="easyuiTheme" href="${static}/js/jquery-easyui-1.2.6/themes/default/easyui.css" rel="stylesheet" type="text/css" media="screen"/>
<script src="${static}/js/changeEasyuiTheme.js" charset="UTF-8" type="text/javascript"></script>
<link href="${static}/js/jquery-easyui-1.2.6/themes/icon.css" rel="stylesheet" type="text/css" media="screen"/>
<script src="${static}/js/jquery-easyui-1.2.6/jquery.easyui.min.js" charset="UTF-8" type="text/javascript"></script>
<script src="${static}/js/jquery-easyui-1.2.6/locale/easyui-lang-zh_CN.js" charset="UTF-8" type="text/javascript"></script>
<link href="${static}/css/baseCss.css" rel="stylesheet" type="text/css" media="screen"/>
<script src="${static}/js/syUtils.js" charset="UTF-8" type="text/javascript"></script>
<script src="${static}/js/permissions.js" charset="UTF-8" type="text/javascript"></script>
<link id="easyuiTheme" href="${static}/css/styles1.css" rel="stylesheet" type="text/css" media="screen"/>


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

</script>