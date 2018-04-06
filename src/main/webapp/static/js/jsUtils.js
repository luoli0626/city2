
var datere;


/*通过code取得名字*/
	 $( function getajx(){
		  		var ura = window.location.href;
		  		var au =ura.split("/");
				sy.ajaxSubmit({
					url :au[0]+'/'+au[1]+'/'+au[2]+'/'+au[3] + '/useDevice/T_GB_6263json',
					cache : false,
					dataType : "json",
					success : function(data) {
						datere = data.obj;
					}
				});
	 });
	 
/*更多查询*/
	function moreCondition(){
		
		var flag =$("#moreCon").css('display');
		if("none" ==flag ){
			$("#moreCon").css("display","inline");
		}
		else{
			$("#moreCon").css("display","none");
		}
	}
	
	
	/*通过code取得名字*/
	 function getName(code){
		 if(typeof(datere) != "undefined"){
			 for(var d in datere){
				 if(datere[d].CODE == code){
					 return datere[d].NAME;
			 }
		}
	 }
		 return '';
	 }
	
