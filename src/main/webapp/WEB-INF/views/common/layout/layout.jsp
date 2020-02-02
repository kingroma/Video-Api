<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles"  uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<!-- https://startbootstrap.com/previews/sb-admin-2/ -->
<!-- https://www.w3schools.com/bootstrap/ -->
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title>Hello My World</title>

 	<link href="<c:url value='/resources/vendor/fontawesome-free/css/all.min.css'/>" rel="stylesheet" type="text/css">
	<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
	
	<!-- Custom styles for this template-->
	<link href="<c:url value='/resources/css/sb-admin-2.min.css'/>" rel="stylesheet">
	<link type="text/css" rel="stylesheet" href="<c:url value='/resources/css/common.css'/>" />
	
	<script src="<c:url value='/resources/vendor/jquery/jquery.min.js'/>"></script>
	<script src="<c:url value='/resources/vendor/bootstrap/js/bootstrap.bundle.min.js'/>"></script>
	<script src="<c:url value='/resources/vendor/jquery-easing/jquery.easing.min.js'/>"></script>
	
	<script type="text/javascript"> var contextPath = "<c:url value='/' />";</script>
	
	
	
	<!-- <script src="https://code.jquery.com/jquery-1.12.4.js"></script> -->
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<script src="<c:url value='/resources/js/autosize.min.js'/>"></script>
	<script src="<c:url value='/resources/js/common.js'/>"></script>
</head>
<body id="page-top">
	<div id="wrapper">
		<div id="">
			<tiles:insertAttribute name="header"/>
		</div>

		<tiles:insertAttribute name="leftMenu"/>
		
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<tiles:insertAttribute name="topMenu"/>
				
				<div class="container-fluid">
					<tiles:insertAttribute name="body"/>
				</div>
			</div>
		</div>
		
		<tiles:insertAttribute name="footer"/>
	</div>
	
	
	
	<script src="<c:url value='/resources/js/sb-admin-2.min.js'/>"></script>
	
	<script>
		$(document).ready(function(){
			var resultCd = '<c:out value="${resultCd}"/>';
			if ( resultCd == 'S'  ){
				alert('succ ');
			}else if ( resultCd == 'F' ){
				alert('fail ');
			}
		});
	</script>
});
</body>
</html>