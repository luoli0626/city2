function permiss(bnts,urlId) {
	$.ajax({  
        type: "POST",  
        dataType: "json",  
        url: "menuController.ftl?findFunction",  
        data: {urlId: urlId},  
        success: function (data) { 
        	var fids = $('a[fid]');
        	if(data.obj.length == 0) {
        		for (var i = 0; i < bnts.length; i++) {
        			$.each(fids, function(j, n){
                		$(n).css("display","none");
                	});
        			$("#"+bnts[i]).css("display","none");
        		}
        	}
        	$.each(bnts, function(i, n){
        		for (var i = 0; i < data.obj.length; i++) {
           			var id = data.obj[i];
           			if(id == n) {
           				break;
           			}
           			if((i == (data.obj.length -1))) {
           				$("#"+n).css("display","none");
           				var fidattr = $("a[fid='"+n+"']");
           				$.each(fidattr, function(j, n){
                    		$(n).css("display","none");
                    	});
           			}
        	   }
	         });
        },  
        error: function () {  
            $.messager.alert("消息", "错误！", "info");  
        }  
    }); 
}