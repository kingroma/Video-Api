function getContextPath(){
	var pathLen = contextPath.length;
	if(contextPath.substring(pathLen-1, pathLen) == "/" ){
		return contextPath.substring(0, (contextPath.length-1));
	}else{
		return contextPath;
	}
}

function isString(value, init){
	var initval = (init == undefined || init == null || init == "undefined") ?"":init;
	return (value == undefined || value == null || value == "undefined")?initval:value.toString(); 
}


function goAction(path, formId, pageNo){
	var form = (isString(formId) == "")?"frm1":formId;
	$('#'+form).attr("action", getContextPath()+path).submit();
}


function setDatepicker(id){
	$('#' + id).datepicker({
		dateFormat: 'yy-mm-dd',
		buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif" //버튼 이미지 경로
		,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
        ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
        ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
        ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
        
	});
}

function goAjax(path, params, msgAt){
	var result = null;

	$.ajaxSettings.traditional = true;  // Array전송시 필요(jquery 1.4 이상)
	$.ajax({
		type: "POST", 
		url:  (getContextPath()+path),
		data: params,
		async: false, 
		cache: false,
		dataType: "json",
		success: function(data){
			result = data;
			if(msgAt != "N" && result != null && result.message != null && result.message != ""){
				// <br/>을 \n으로 치환
 	   			var re = /<br *\/?>/gi;
 	   			alert((result.message).replace(re, '\n')); 
			} 
		}, error:function (data, textStatus, jqXHR) {
			ajaxError(data, textStatus, jqXHR);
		}, beforeSend:function(){
			
	    }, complete:function(){ 
	    }  
	});
	return result;
}

function ajaxError(data, textStatus, jqXHR){
    if(textStatus == "parsererror"){ 
    	var pHeight = 200;
    	var pWidth  = 300;
    	var xtop    = (screen.height-pHeight)/2;
    	var yleft   = (screen.width-pWidth)/2;
    	var errWin = window.open("", "", "width="+pWidth+"px, height="+pHeight+"px, top="+xtop+"px, left="+yleft+"px, location=no, status=no, scrollbars=no"); 
    	errWin.document.write(data.responseText);
    }else{ 
	    alert(textStatus + " ===> " + jqXHR);		// Forbidden시 URL정보 및 권한 확인
    }
}

function getDuplPk(path, id, paramNames){
	var boo = false;
	if($("#"+id).val() == ""){
		
	} else {
		var params = {};
		params[id] = $("#"+id).val();
		
		var r = goAjax(path, params);
		if(r != null && r.resultCd == "S"){
			boo = true;
		}
	}
	return boo;
}