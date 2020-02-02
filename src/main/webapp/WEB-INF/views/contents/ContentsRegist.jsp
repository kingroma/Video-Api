<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form"      uri="http://www.springframework.org/tags/form" %> 


<link href="<c:url value='/resources/vendor/datatables/dataTables.bootstrap4.min.css'/>" rel="stylesheet">

<form:form commandName="contentsVO" name="frm1" id="frm1" method="POST" >
	<div class="d-flex align-items-center mb-4">
		<h1 class="h3 mb-0 text-gray-800 mr-auto">Contents</h1>
		
		<a href="javascript:goAction('/contents/list.do')" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm border">
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
	    	<h6 class="m-0 font-weight-bold text-primary">Contents Regist</h6>
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
				            <th class='text-center align-middle'>Contents Id</th>
				            <td colspan="3">
				            	<form:input path="contentsId" cssClass="form-control" />
				            </td>
	         			</tr>
	         			<tr>
				            <th class='text-center align-middle'>Contents Nm</th>
				            <td colspan="3">
				            	<form:input path="contentsNm" cssClass="form-control" />
				            </td>
				            
	         			</tr>
	         			<tr>
				            <th class='text-center align-middle'>사용여부</th>
				            <td>
					            <select id='useAt' name='useAt' class='form-control'>
					            	<option
					            		<c:if test="${contentsVO.useAt != null and contentsVO.useAt == 'Y' }">
					            			selected="selected"
					            		</c:if>
					            		>Y</option>
					            	<option
					            		<c:if test="${contentsVO.useAt != null and contentsVO.useAt == 'N' }">
					            			selected="selected"
					            		</c:if>
					            		>N</option>
					            </select>
				            </td>
				            
				            <th></th>
				            <td></td>
	         			</tr>
	         			
	         			<tr>
				            <th class='text-center align-middle'>Mapping Video</th>
				            <td id='videoSuperTd' colspan="3">
				            	<div class="d-flex">
				            		<input type="text" id="addVideoSuperId" class='form-control' style="width:300px;margin-right:10px" />
				            		<input type="text" id="addSortSn" class='form-control' style="width:100px;margin-right:10px" />
				            		
				            		<a href="javascript:fn_addVideo()" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm border">&nbsp;&nbsp;+&nbsp;&nbsp;</a>
				            	</div>
				            	<c:forEach items="${list}" var="r" varStatus="s">
					            	<div id='<c:out value="${r.videoSuperId }"/>' style="margin-top:3px;">
					            		<div class="d-flex">
											<input type="text" id="videoSuperIdArr" name="videoSuperIdArr" class='form-control' style="width:300px;margin-right:10px"  value="${r.videoSuperId }" />
											<input type="text" id="sortSnArr" name="sortSnArr" class='form-control' style="width:100px;margin-right:10px"  value="${r.sortSn }" />
											
											<a href="javascript:fn_deleteVideo('<c:out value="${r.videoSuperId }"/>')"
												class="d-none d-sm-inline-block btn btn-sm btn-danger shadow-sm border"
												>&nbsp;&nbsp;-&nbsp;&nbsp;</a>
										</div>
					            	</div>
				            	</c:forEach>
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
		var contentsId = $('#contentsId').val();
		var url = '/contents/insert.do';
		if ( contentsId != null && contentsId != '' ){
			url = '/contents/update.do';
		}
		goAction(url);
		
	}

	function fn_deleteVideo(id){
		$('#'+id).remove();
	}
	
	function fn_addVideo(){
		var td = $('#videoSuperTd');

		var div1 = $('<div></div>');
		var div2 = $('<div></div>');
		var inputVideoId = $('<input></input>');
		var inputSortSn = $('<input></input>');
		var aDelete = $('<a></a>');
		
		div1.attr("id",$('#addVideoSuperId').val()).css("margin-top","3px");
		
		aDelete.attr("href","javascript:fn_deleteVideo('" + $('#addVideoSuperId').val() + "')");
		aDelete.attr("class","d-none d-sm-inline-block btn btn-sm btn-danger shadow-sm border");
		aDelete.append("&nbsp;&nbsp;-&nbsp;&nbsp;");
		
		div2.attr("class","d-flex");
		
		inputVideoId.attr("type","text");
		inputVideoId.attr("id","videoSuperIdArr");
		inputVideoId.attr("name","videoSuperIdArr");
		inputVideoId.attr("class","form-control");
		inputVideoId.css("width","300px");
		inputVideoId.css("margin-right","10px");
		inputVideoId.val($('#addVideoSuperId').val());
		
		inputSortSn.attr("type","text");
		inputSortSn.attr("id","sortSnArr");
		inputSortSn.attr("name","sortSnArr");
		inputSortSn.attr("class","form-control");
		inputSortSn.css("width","100px");
		inputSortSn.css("margin-right","10px");
		inputSortSn.val($('#addSortSn').val());
		
		div2.append(inputVideoId);
		div2.append(inputSortSn);
		div2.append(aDelete);
		
		div1.append(div2);
		td.append(div1);
	}
	
	function fn_delete(){
		var url = '/contents/delete.do';
		goAction(url); 
	}
</script>

