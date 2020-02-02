<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form"      uri="http://www.springframework.org/tags/form" %> 


<link href="<c:url value='/resources/vendor/datatables/dataTables.bootstrap4.min.css'/>" rel="stylesheet">

<form:form commandName="videoSuperVO" name="frm1" id="frm1" method="POST" >
	<form:hidden path="delAt" />
	
	<div class="d-flex align-items-center mb-4">
		<h1 class="h3 mb-0 text-gray-800 mr-auto">Video Super</h1>
		
		<a href="javascript:goAction('/video/list.do')" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm border">
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
	    	<h6 class="m-0 font-weight-bold text-primary">Video Super Regist</h6>
	  	</div>
	  	<div class="card-body">
	    	<div class="table-responsive">
	      		<table class="table table-bordered table-hover" id="dataTable" width="100%" cellspacing="0">
	      			<colgroup>
	      				<col width='15%'/>
	      				<col width='35%'/>
	      				<col width='15%'/>
	      				<col />
	      			</colgroup>
	       			<thead>
	         			<tr>
				            <th class='text-center align-middle'>Super Video Id</th>
				            <td colspan="3">
				            	<form:input path="videoSuperId" cssClass="form-control" />
				            </td>
	         			</tr>
	         			<tr>
				            <th class='text-center align-middle'>Video Nm</th>
				            <td colspan="3">
				            	<form:input path="videoSuperNm" cssClass="form-control" />
				            </td>
				            
	         			</tr>
	         			<tr>
				            <th class='text-center align-top'>Video Explain</th>
				            <td colspan="3">
				            	<textarea class='form-control autosize' name="videoSuperExplain"><c:out value="${videoSuperVO.videoSuperExplain }"/></textarea>
				            </td>
	         			</tr>
	         			<tr>
				            <th class='text-center align-top'>Video Actor</th>
				            <td colspan="3">
				            	<textarea class='form-control autosize' name="videoSuperActor"><c:out value="${videoSuperVO.videoSuperActor }"/></textarea>
				            </td>
	         			</tr>
	         			
	         			<tr>
				            <th class='text-center align-top'>Video Genre</th>
				            <td colspan="3">
				            	<textarea class='form-control autosize' name="videoSuperGenre"><c:out value="${videoSuperVO.videoSuperGenre }"/></textarea>
				            </td>
	         			</tr>
	         			
	         			<tr>
				            <th class='text-center align-middle'>사용여부</th>
				            <td>
					            <select id='useAt' name='useAt' class='form-control'>
					            	<option
					            		<c:if test="${videoSuperVO.useAt != null and videoSuperVO.useAt == 'Y' }">
					            			selected="selected"
					            		</c:if>
					            		>Y</option>
					            	<option
					            		<c:if test="${videoSuperVO.useAt != null and videoSuperVO.useAt == 'N' }">
					            			selected="selected"
					            		</c:if>
					            		>N</option>
					            </select>
				            </td>
				            
				            <th></th>
				            <td></td>
	         			</tr>
	         			<tr>
				            <th class='text-center align-top'>Video Main Image</th>
				            <td colspan="3">
				            	<form:input path="videoSuperMainImage" cssClass="form-control" />
				            </td>
	         			</tr>
	       			</thead>
	     		</table>
			</div>
		</div>
	</div>
</form:form>
<script src="<c:url value='/resources/vendor/datatables/jquery.dataTables.min.js'/>"></script>
<script src="<c:url value='/resources/vendor/datatables/dataTables.bootstrap4.min.js'/>"></script>

<script>
	$(document).ready(function() {
	});

	function fn_save(){
		var videoSuperId = $('#videoSuperId').val();
		var url = '/videosuper/insert.do';
		if ( videoSuperId != null && videoSuperId != '' ){
			url = '/videosuper/update.do';
		}
		goAction(url); 
	}
	
	function fn_delete(){
		var url = '/videosuper/delete.do';
		goAction(url); 
	}
</script>
