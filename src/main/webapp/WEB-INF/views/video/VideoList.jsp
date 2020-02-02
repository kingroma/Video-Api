<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form"      uri="http://www.springframework.org/tags/form" %> 


<link href="<c:url value='/resources/vendor/datatables/dataTables.bootstrap4.min.css'/>" rel="stylesheet">

<form:form commandName="videoVO" name="frm1" id="frm1" method="POST" >
	<form:hidden path="videoId"/>
	<div class="d-sm-flex align-items-center justify-content-between mb-4">
		<h1 class="h3 mb-0 text-gray-800">Video</h1>
		<a href="javascript:goAction('/video/regist.do')" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">
			Create
		</a>
	</div>
	<div class="card shadow mb-4">
	  	<div class="card-header py-3">
	    	<h6 class="m-0 font-weight-bold text-primary">Video List</h6>
	  	</div>
	  	<div class="card-body">
	    	<div class="table-responsive">
	      		<table class="table table-bordered table-hover" id="dataTable" width="100%" cellspacing="0">
	      			<colgroup>
	      				<col width='5%'/>
	      				<col width='30%'/>
	      				<col/>
	      				<col width='30%'/>
	      				<col width='10%'/>
	      				
	      			</colgroup>
	       			<thead>
	         			<tr>
				            <th class='text-center'>#</th>
				            <th class='text-center'>Video</th>
				            <th class='text-center'>Explain</th>
				            <th class='text-center'>Video Real Path</th>
				            <th class='text-center'>사용</th>
				            
	         			</tr>
	       			</thead>
	       			<tbody>
	         			<c:forEach items="${list }" var="r" varStatus="s">
	         				<tr onclick='goModify("<c:out value="${r.videoId }"/>")' style='cursor:pointer'>
	         					<td><c:out value="${r.rownum }"/></td>
	         					<td>[<c:out value="${r.videoSuperId} }"/> / <c:out value="${r.videoId }"/>]<c:out value="${r.videoNm }"/></td>
	         					<td><c:out value="${r.videoExplain }"/></td>
	         					<td><c:out value="${r.videoRealPath }"/></td>
	         					<td><c:out value="${r.useAt }"/></td>
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
	
	function goModify(videoId) {
		$('#videoId').val(videoId);
		goAction("/video/modify.do");	
	}
</script>

