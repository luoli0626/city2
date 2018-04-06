
$(function(){
	
	window.setTimeout(function() {
		if( null != $('#formMessage').val() && "" != $('#formMessage').val() ){
			$.messager.alert( "提示" , $('#formMessage').val() );
		}
	}, 2);
	
});