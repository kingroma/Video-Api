<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form"      uri="http://www.springframework.org/tags/form" %> 


<link href="<c:url value='/resources/vendor/datatables/dataTables.bootstrap4.min.css'/>" rel="stylesheet">

<form:form commandName="myDaemonVO" name="frm1" id="frm1" method="POST" >
	<form:hidden path="daemonId"/>

	<div class="d-sm-flex align-items-center justify-content-between mb-4">
		<h1 class="h3 mb-0 text-gray-800 mr-auto">Daemon Log</h1>
		<a href="javascript:goAction('/common/mydaemonlog/search.do')" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm border">
			Search
		</a>
	</div>
	<div class="card shadow mb-4">
	  	<div class="card-header py-3">
	    	<h6 class="m-0 font-weight-bold text-primary">Daemon Log List</h6>
	  	</div>
	  	<div class="card-body">
	  		<div class='row  p-2'>
	  			<div class='col-sm-6 row p-0 mr-2' >
	  				<div class='col-sm-2 pt-2' >
	  					성공여부
	  				</div>
	  				<div class='col-sm-4' >
	  					<select id='srchSuccAt' name='srchSuccAt' class='form-control'>
	  						<option></option>
	  						<option value="Y" 
								<c:if test='${myDaemonVO.srchSuccAt == "Y" }'>selected="selected"</c:if>
	  							>Y</option>
	  						<option value="N"
	  							<c:if test='${myDaemonVO.srchSuccAt == "N" }'>selected="selected"</c:if>
	  							>N</option>
	  					</select>
	  				</div>
	  			</div>
	  			<div class='col-sm-6 row p-0 ml-3'  >
	  				<div class='col-sm-1  text-center' >
	  				</div>
	  				<div class='col-sm-5' >
	  					<input id="srchBgnde" name='srchBgnde' value='<c:out value="${myDaemonVO.srchBgnde }"/>' class="form-control" />
	  				</div>
	  				<div class='col-sm-1 text-center pt-2' >
	  					~
	  				</div>
	  				<div class='col-sm-5' >
	  					<input id="srchEndde" name='srchEndde' value='<c:out value="${myDaemonVO.srchEndde }"/>' class="form-control" />
	  				</div>
	  			</div>
	  		</div>
	    	<div class="table-responsive">
	      		<table class="table table-bordered table-hover" id="dataTable" width="100%" cellspacing="0">
	      			<colgroup>
	      				<col width='5%'/>
	      				<col width='20%'/>
	      				<col width='10%'/>
	      				<col width='23%'/>
	      				<col width='7%'/>
	      				
	      				<col />
	      			</colgroup>
	       			<thead>
	         			<tr>
				            <th class='text-center'>#</th>
				            <th class='text-center'>Daemon</th>
				            <th class='text-center'>Controller</th>
				            <th class='text-center'>데몬 시간</th>
				            <th class='text-center'>성공</th>
				            
				            <th class='text-center'>메세지</th>
	         			</tr>
	       			</thead>
	       			<tbody>
	         			<c:forEach items="${list }" var="r" varStatus="s">
	         				<tr>
	         					<td><c:out value="${r.rownum }"/></td>
	         					<td>[<c:out value="${r.daemonId }"/>]<c:out value="${r.daemonNm }"/></td>
	         					<td><c:out value="${r.controllerNm }"/></td>
	         					<td><c:out value="${r.daemonDe }"/></td>
	         					<td class='text-center'><c:out value="${r.succAt }"/></td>
	         					
	         					<td><c:out value="${r.message }"/></td>
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
	  
	  	setDatepicker('srchBgnde');
		setDatepicker('srchEndde');
	});
	
	function goModify(daemonId) {
		$('#daemonId').val(daemonId);
		goAction("/common/mydaemon/modify.do");	
	}
</script>

