<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!-- 좌측 -->
<!-- Sidebar -->
<ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

	<!-- Sidebar - Brand -->
	<a class="sidebar-brand d-flex align-items-center justify-content-center" href="<c:url value='/common/dashboard/list.do'/>">
		<div class="sidebar-brand-icon rotate-n-15">
			<i class="fas fa-laugh-wink"></i>
		</div>
		<div class="sidebar-brand-text mx-3">My Deamon</div>
	</a>

	<!-- Divider -->
	<hr class="sidebar-divider my-0">

	<!-- Nav Item - Dashboard -->
	<li class="nav-item active">
		<a class="nav-link" href="<c:url value='/common/dashboard/list.do'/>">
			<i class="fas fa-fw fa-tachometer-alt"></i> <span>Dashboard</span>
		</a>
	</li>

	<!-- Divider -->
	<hr class="sidebar-divider">

	<!-- Heading -->
	<div class="sidebar-heading">Daemon</div>

	<!-- Nav Item - Tables -->
	<li class="nav-item">
		<a class="nav-link" href="<c:url value='/common/mydaemon/list.do'/>"> 
			<i class="fas fa-fw fa-table"></i> 
			<span>Daemon</span>
		</a>
	</li>
	
	<!-- Nav Item - Tables -->
	<li class="nav-item">
		<a class="nav-link" href="<c:url value='/common/mydaemonlog/list.do'/>"> 
			<i class="fas fa-fw fa-eye"></i> 
			<span>Daemon Log</span>
		</a>
	</li>

	<!-- Divider -->
	<hr class="sidebar-divider d-none d-md-block">

	<!-- Sidebar Toggler (Sidebar) -->
	<div class="text-center d-none d-md-inline">
		<button class="rounded-circle border-0" id="sidebarToggle"></button>
	</div>
</ul>
<!-- 좌측끝 -->