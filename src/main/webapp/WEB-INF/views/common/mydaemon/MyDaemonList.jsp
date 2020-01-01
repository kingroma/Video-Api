<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form"      uri="http://www.springframework.org/tags/form" %> 


<link href="<c:url value='/resources/vendor/datatables/dataTables.bootstrap4.min.css'/>" rel="stylesheet">

<form:form commandName="myDaemonVO" name="frm1" id="frm1" method="POST" >
	<form:hidden path="daemonId"/>

	<div class="d-sm-flex align-items-center justify-content-between mb-4">
		<h1 class="h3 mb-0 text-gray-800">Daemon</h1>
		<a href="javascript:goAction('/common/mydaemon/regist.do')" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">
			Create
		</a>
	</div>
	<div class="card shadow mb-4">
	  	<div class="card-header py-3">
	    	<h6 class="m-0 font-weight-bold text-primary">Daemon List</h6>
	  	</div>
	  	<div class="card-body">
	    	<div class="table-responsive">
	      		<table class="table table-bordered table-hover" id="dataTable" width="100%" cellspacing="0">
	      			<colgroup>
	      				<col width='5%'/>
	      				<col width='20%'/>
	      				<col/>
	      				<col width='23%'/>
	      				<col width='10%'/>
	      				
	      				<col width='16%'/>
	      				<col width='10%'/>
	      			</colgroup>
	       			<thead>
	         			<tr>
				            <th class='text-center'>#</th>
				            <th class='text-center'>Daemon</th>
				            <th class='text-center'>Controller</th>
				            <th class='text-center'>시작 ~ 종료</th>
				            <th class='text-center'>시간</th>
				            
				            <th class='text-center'>반복/일회</th>
				            <th class='text-center'>사용</th>
	         			</tr>
	       			</thead>
	       			<tbody>
	         			<c:forEach items="${list }" var="r" varStatus="s">
	         				<tr onclick='goModify("<c:out value="${r.daemonId }"/>")' style='cursor:pointer'>
	         					<td><c:out value="${r.rownum }"/></td>
	         					<td>[<c:out value="${r.daemonId }"/>]<c:out value="${r.daemonNm }"/></td>
	         					<td><c:out value="${r.controllerNm }"/></td>
	         					<td><c:out value="${r.bgndeFormat }"/>&nbsp;~&nbsp;<c:out value="${r.enddeFormat }"/></td>
	         					<td><c:out value="${r.intervalAtNm }"/></td>
	         					
	         					<td>
	         						<c:choose>
	         							<c:when test="${r.intervalAt == 'Y' }">
	         								<c:out value="${r.minute }"/> 분 마다
	         							</c:when>
	         							<c:otherwise>
	         								<c:out value="${r.minuteEveryDayTime }"/>
	         							</c:otherwise>
	         						</c:choose>
	         					</td>
	         					<td><c:out value="${r.useAtNm }"/></td>
	         				</tr>
	         			</c:forEach>
	           
	       			</tbody>
	     		</table>
			</div>
		</div>
	</div>
</form:form>
<script src="<c:url value='/resources/vendor/datatables/jquery.dataTables.min.js'/>"></script>
<script src="<c:url value='/resources/vendor/datatables/dataTables.bootstrap4.min.js'/>"></script>

<script>
	$(document).ready(function() {
	  $('#dataTable').DataTable();
	});
	
	function goModify(daemonId) {
		$('#daemonId').val(daemonId);
		goAction("/common/mydaemon/modify.do");	
	}
</script>

