<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
Hello World
<link href="<c:url value='/resources/vendor/fontawesome-free/css/all.min.css'/>" rel="stylesheet" type="text/css">
	<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
	
	<!-- Custom styles for this template-->
	<link href="<c:url value='/resources/css/sb-admin-2.min.css'/>" rel="stylesheet">
	<link type="text/css" rel="stylesheet" href="<c:url value='/resources/css/common.css'/>" />
	
	<script src="<c:url value='/resources/vendor/jquery/jquery.min.js'/>"></script>
	<script src="<c:url value='/resources/vendor/bootstrap/js/bootstrap.bundle.min.js'/>"></script>
	<script src="<c:url value='/resources/vendor/jquery-easing/jquery.easing.min.js'/>"></script>
	
	<script type="text/javascript"> var contextPath = "<c:url value='/' />";</script>
	<script src="<c:url value='/resources/js/common.js'/>"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript"> var contextPath = "<c:url value='/' />";</script>
<script src="<c:url value='/resources/js/common.js'/>"></script>
<script src="<c:url value='/resources/vendor/datatables/jquery.dataTables.min.js'/>"></script>
<script src="<c:url value='/resources/vendor/datatables/dataTables.bootstrap4.min.js'/>"></script>
<form id='frm1' name='frm1' method="get">
	<a href="javascript:goAction('/hello')">GO</a>
</form>
</body>
</html>