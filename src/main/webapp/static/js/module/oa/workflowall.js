
/**
 * 一卡通数据展示
 * @param id
 * @param callback
 */
function showDetail(id, callback) {
	    var idx=id*1;
	    
	  // 打开对话框的时候读取请假内容
	    $.getJSON(ctx + '/oneCardController.ftl?detail&id=' + idx+"&timetemp="+new Date(), function(data) {
	        $.each(data, function(k, v) {
				   $('.showview-info td[name=' + k + ']').text(v);
	        });
	    });
	    
	    showviewDialog = $('#showviewDialog').show().dialog({
			modal : true,
            width: 400,
            height: 300,
			title : '数据详情'
	    });
}