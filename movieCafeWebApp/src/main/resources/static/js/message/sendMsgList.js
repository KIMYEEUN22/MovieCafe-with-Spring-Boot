$(document).ready(function() {

	// 전체삭제 클릭시
	$('#allChecked').on('click',function(){
		//allChecked가 체크될시
		if($('#allChecked').is(':checked')){
			//removeCheckBox의 체크를 true로
			 $("input[name=removeCheckBox]").prop("checked",true);
		}else{
			//removeCheckBox의 체크를 false로
			 $("input[name=removeCheckBox]").prop("checked",false);
		}
		
		
	});
});

