<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form"      uri="http://www.springframework.org/tags/form" %> 


<link href="<c:url value='/resources/vendor/datatables/dataTables.bootstrap4.min.css'/>" rel="stylesheet">

<form:form commandName="myDaemonVO" name="frm1" id="frm1" method="POST" >
	<c:set var="registFlag" value="update"/>
	<c:if test="${ empty myDaemonVO.daemonId }"><c:set var="registFlag" value="insert"/></c:if>
	
	<form:hidden path="daemonId" />
	<form:hidden path="delAt" />
	
	<div class="d-flex align-items-center mb-4">
		<h1 class="h3 mb-0 text-gray-800 mr-auto">Daemon</h1>
		
		<a href="javascript:goAction('/common/mydaemon/list.do')" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm border">
			Go Back
		</a>
		<a href="javascript:fn_delete()" class="d-none d-sm-inline-block btn btn-sm btn-danger shadow-sm border">
			Delete
		</a>
		<a href="javascript:fn_save()" class="d-none d-sm-inline-block btn btn-sm btn-success shadow-sm border ">
			Save
		</a>
	</div>
	<div class="card shadow mb-4">
	  	<div class="card-header py-3">
	    	<h6 class="m-0 font-weight-bold text-primary">Daemon Regist</h6>
	  	</div>
	  	<div class="card-body">
	    	<div class="table-responsive">
	      		<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
	      			<colgroup>
	      				<col width='15%'/>
	      				<col width='35%'/>
	      				
	      				<col width='15%'/>
	      				<col/>
	      			</colgroup>
	       			<thead>
	         			<tr>
				            <th class='text-center' style='vertical-align:top'><div>Daemon ID</div></th>
				            <td>
				            	<c:out value="${myDaemonVO.daemonId }"/>
				            </td>
				            <th class='text-center' style='vertical-align:top'><div>Daemon NM</div></th>
				            <td>
				            	<form:input path="daemonNm" cssClass="form-control form-control-user"/>
				            </td>
	         			</tr>
	         			<tr>
				            <th class='text-center' style='vertical-align:top'><div>Controller NM</div></th>
				            <td>
				            	<form:input path="controllerNm" cssClass="form-control form-control-user"/>
				            </td>
				            <th class='text-center' style='vertical-align:top'><div>사용 여부</div></th>
				            <td>
				            	<select id='useAt' name='useAt' class='form-control form-control-user'>
				            		<option value='Y'
				            			<c:if test='${myDaemonVO.useAt == "Y" }'>
				            				selected="selected" 
				            			</c:if>
				            			>사용</option>
				            		<option value='N'
				            			<c:if test='${myDaemonVO.useAt == "N" }'>
				            				selected="selected" 
				            			</c:if>
				            			>미사용</option>
				            	</select>
				            </td>
	         			</tr>
	         			<tr>
				            <th class='text-center' style='vertical-align:top'><div>시작일시</div></th>
				            <td>
				            	<input id="bgnde" name='bgnde' value='<c:out value="${myDaemonVO.bgndeFormat }"/>' class="form-control form-control-user" />
				            </td>
				            <th class='text-center' style='vertical-align:top'><div>종료일시</div></th>
				            <td>
				            	<input id="endde" name='endde' value='<c:out value="${myDaemonVO.enddeFormat }"/>' class="form-control form-control-user" />
				            </td>
	         			</tr>
	         			<tr>
				            <th class='text-center' style='vertical-align:top'><div>반복/일회</div></th>
				            <td>
				            	<select id='intervalAt' name='intervalAt' class='form-control form-control-user' onchange="checkIntervalAt()">
				            		<option value='Y'
				            			<c:if test='${myDaemonVO.intervalAt == "Y" }'>
				            				selected="selected" 
				            			</c:if>
				            			>반복</option>
				            		<option value='N'
				            			<c:if test='${myDaemonVO.intervalAt == "N" }'>
				            				selected="selected" 
				            			</c:if>
				            			>일회</option>
				            	</select>
				            </td>
				            <th class='text-center' style='vertical-align:top'><div>시간</div></th>
				            <td>
				            	<form:input path="minute" cssClass="form-control form-control-user d-none" onkeyup=""/>
				            	
				            	<div id="minuteFakeDiv">
				            		<input type='text' id='minuteFake1' class='form-control form-control-user col-sm-6 d-inline' onkeyup="calcHourMinute()"/>
				            		<div class='col-sm-6 d-inline'>
			            				분 마다
			            			</div>
				            	</div>
				            	
				            	<div id="hourMinuteFakeDiv">
				            		<div>
				            			<input type='text' id='hourFake2' class='form-control form-control-user col-sm-6 d-inline' onkeyup="calcHourMinute()"/>
				            			<div class='col-sm-6 d-inline'> 
					            			시
					            		</div>
				            		</div>
				            		<div class='' style='padding:2px;'></div>
					            	<div>
					            		<input type='text' id='minuteFake2' class='form-control form-control-user col-sm-6 d-inline' onkeyup="calcHourMinute()"/>
					            		<div class='col-sm-6 d-inline'> 
					            			분 마다
					            		</div>
					            	</div>
				            	</div>
				            </td>
	         			</tr>
	         			<tr>
	         				<th class='text-center' style='vertical-align:top'><div>전달내용</div></th>
				            <td colspan="3">
				            	<textarea id='daemonParam' name='daemonParam' class='form-control'><c:out value="${myDaemonVO.daemonParam }"/></textarea>
				            </td>
	         			</tr>
	       			</thead>
	     		</table>
			</div>
		</div>
	</div>
	
</form:form>
<%-- <script src="<c:url value='/resources/vendor/datatables/jquery.dataTables.min.js'/>"></script>
<script src="<c:url value='/resources/vendor/datatables/dataTables.bootstrap4.min.js'/>"></script> --%>

<script>
	$(document).ready(function() {
		setDatepicker('bgnde');
		setDatepicker('endde');
		
		checkIntervalAt();
		
		var minute = $('#minute');
		
		var min = parseInt(minute.val());
		
		var minuteFake1 = $('#minuteFake1');
		var hourFake2 = $('#hourFake2');
		var minuteFake2 = $('#minuteFake2');

		if ( minute.val() == '' ){
			min = 0 ;
		}
		
		minuteFake1.val(min);
		
		hourFake2.val(parseInt(parseInt(min)/60));
		minuteFake2.val(parseInt(parseInt(min)%60));	
		
		autosize(document.getElementById('daemonParam'));
	});
	
	
	function checkIntervalAt() { 
		var target = $('#intervalAt');
		
		var minuteFakeDiv = $('#minuteFakeDiv');
		var hourMinuteFakeDiv = $('#hourMinuteFakeDiv'); 
		
		minuteFakeDiv.css("display","none");
		hourMinuteFakeDiv.css("display","none");
		
		if ( target.val() == 'Y'){
			minuteFakeDiv.css("display","block");
		}else {
			hourMinuteFakeDiv.css("display","block");
		} 
	}
	
	function calcHourMinute(){
		var intervalAt = $('#intervalAt');
		var minute = $('#minute');
		
		var minuteFake1 = $('#minuteFake1');
		var hourFake2 = $('#hourFake2');
		var minuteFake2 = $('#minuteFake2');
		
		var minuteFake1Val = ( minuteFake1.val() == '' ? 0 : minuteFake1.val() ) ;
		var hourFake2Val = ( hourFake2.val() == '' ? 0 : hourFake2.val() ) ;
		var minuteFake2Val = ( minuteFake2.val() == '' ? 0 : minuteFake2.val() ) ;
		
		
		// 일회
		if ( intervalAt.val() == 'N' ){
			minuteFake1.val(parseInt(hourFake2Val) * 60 + parseInt(minuteFake2Val));
			
			minute.val(parseInt(hourFake2Val) * 60 + parseInt(minuteFake2Val));
		}
		
		// 반복
		else if ( intervalAt.val() == 'Y' ){
			hourFake2.val(parseInt(minuteFake1Val / 60));
			minuteFake2.val(parseInt(minuteFake1Val % 60));
			
			minute.val(parseInt(minuteFake1Val))
		}
	}
	
	function fn_delete(){
		$('#delAt').val('Y');
		$('#useAt').val('N');
		var msg = '삭제 ? ';
		
		if (confirm(msg)) {
			goAction('/common/mydaemon/delete.do'); 
		}
	}
	
	function fn_save(){
		$('#delAt').val('N');
		var msg = '저장 ? ';
		
		var registFlag = "${registFlag}";
		
		if ( !fn_vaild() ) {
			return ;
		}
		
		if (confirm(msg)) {
			if ( "insert" == registFlag ){
				goAction('/common/mydaemon/insert.do'); 
			}else{
				goAction('/common/mydaemon/update.do'); 
			}
        } 
	}
	
	function fn_vaild(){
		var ret = true ;  
		var arr = ['daemonNm','useAt','controllerNm','bgnde','endde','minute','intervalAt'];
		
		for ( var i = 0 ; i < arr.length ; i ++ ) {
			if ( $('#'+arr[i]).val() == undefined || $('#'+arr[i]).val() == '' ){
				alert(arr[i]+' is empty');
				ret = false ;
				break;
			}
		}
			
		return ret ; 
	}
</script>

