<%
if (request.getSession(false).getAttribute("user_id") == null) {
		
%>
    <jsp:forward page="/login.jsp"/>
<%
	return;
	}
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<% 
	pageContext.setAttribute("templateUrl", request.getContextPath()+"/resources/static/template2");
int loggedInUserRole = (Integer) request.getSession(false).getAttribute("role_id");
String siteName = (String) request.getSession(false).getAttribute("sitename");
%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <base href="./">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <meta name="description" content="CoreUI - Open Source Bootstrap Admin Template">
    <meta name="author" content="Łukasz Holeczek">
    <meta name="keyword" content="Bootstrap,Admin,Template,Open,Source,jQuery,CSS,HTML,RWD,Dashboard">
    <title>${pageTitle}</title>
    <!-- Main styles for this application-->
    <link href="${templateUrl}/css/style.css" rel="stylesheet">
    <link href="${templateUrl}/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <style>
    	.btn i{color: #fff;}
    </style>
  </head>
  <body class="c-app">
    <div class="c-sidebar c-sidebar-dark c-sidebar-fixed c-sidebar-lg-show" id="sidebar">
      <div class="c-sidebar-brand d-lg-down-none">
       <%=siteName %>
      </div>
      <ul class="c-sidebar-nav">
        <li class="c-sidebar-nav-item"><a class="c-sidebar-nav-link" href="${pageContext.request.contextPath}/home">
            <svg class="c-sidebar-nav-icon">
              <use xlink:href="${templateUrl}/node_modules/@coreui/icons/sprites/free.svg#cil-speedometer"></use>
            </svg> Dashboard<span class="badge badge-info">NEW</span></a></li>
        
        <% if(loggedInUserRole==5){ //Cashier %>
        <li class="c-sidebar-nav-title">Exit Kiosk</li>
        <li class="c-sidebar-nav-item"><a class="c-sidebar-nav-link" href="${pageContext.request.contextPath}/exit/screen">
            <svg class="c-sidebar-nav-icon">
              <use xlink:href="${templateUrl}/node_modules/@coreui/icons/sprites/free.svg#cil-drop"></use>
            </svg> Exit Screen</a></li>
        <%-- <li class="c-sidebar-nav-item"><a class="c-sidebar-nav-link" href="${pageContext.request.contextPath}/report/transactionList">
            <svg class="c-sidebar-nav-icon">
              <use xlink:href="${templateUrl}/node_modules/@coreui/icons/sprites/free.svg#cil-pencil"></use>
            </svg> Transaction List</a></li> --%>
        <li class="c-sidebar-nav-item"><a class="c-sidebar-nav-link" href="${pageContext.request.contextPath}/exit/addLot">
            <svg class="c-sidebar-nav-icon">
              <use xlink:href="${templateUrl}/node_modules/@coreui/icons/sprites/free.svg#cil-drop"></use>
            </svg> LOT</a></li>
        <li class="c-sidebar-nav-item"><a class="c-sidebar-nav-link" href="${pageContext.request.contextPath}/exit/lot">
            <svg class="c-sidebar-nav-icon">
              <use xlink:href="${templateUrl}/node_modules/@coreui/icons/sprites/free.svg#cil-pencil"></use>
            </svg> LOT List</a></li>
        <%} %>
        
        <% if(loggedInUserRole<=4){ //Admin to SuperViser level %>
        <li class="c-sidebar-nav-title">Masters</li>
        <li class="c-sidebar-nav-item"><a class="c-sidebar-nav-link" href="${pageContext.request.contextPath}/user/list">
            <svg class="c-sidebar-nav-icon">
              <use xlink:href="${templateUrl}/node_modules/@coreui/icons/sprites/free.svg#cil-drop"></use>
            </svg> User</a></li>
        <% if(loggedInUserRole==4){ //Admin to SuperViser level %>
        <li class="c-sidebar-nav-item"><a class="c-sidebar-nav-link" href="${pageContext.request.contextPath}/tenant/list">
            <svg class="c-sidebar-nav-icon">
              <use xlink:href="${templateUrl}/node_modules/@coreui/icons/sprites/free.svg#cil-pencil"></use>
            </svg> Tenant</a></li>
        <%} %>
        <li class="c-sidebar-nav-item"><a class="c-sidebar-nav-link" href="${pageContext.request.contextPath}/tariff">
            <svg class="c-sidebar-nav-icon">
              <use xlink:href="${templateUrl}/node_modules/@coreui/icons/sprites/free.svg#cil-calculator"></use>
            </svg> Tariff</a></li>
        <%-- <li class="c-sidebar-nav-item"><a class="c-sidebar-nav-link" href="${pageContext.request.contextPath}/tariff">
            <svg class="c-sidebar-nav-icon">
              <use xlink:href="${templateUrl}/node_modules/@coreui/icons/sprites/free.svg#cil-drop"></use>
            </svg> Tariff</a></li> --%>
         <%} %>
         <% if(loggedInUserRole==2||loggedInUserRole==3||loggedInUserRole==4){ //BRANCH HEAD, MANAGER, SUPERVISER %>
        <li class="c-sidebar-nav-title">Reports</li>
        <li class="c-sidebar-nav-item"><a class="c-sidebar-nav-link" href="${pageContext.request.contextPath}/report/dailyReport">
            <svg class="c-sidebar-nav-icon">
              <use xlink:href="${templateUrl}/node_modules/@coreui/icons/sprites/free.svg#cil-chart-pie"></use>
            </svg> Daily Report</a></li>
        <li class="c-sidebar-nav-item"><a class="c-sidebar-nav-link" href="${pageContext.request.contextPath}/report/datewiseReport">
            <svg class="c-sidebar-nav-icon">
              <use xlink:href="${templateUrl}/node_modules/@coreui/icons/sprites/free.svg#cil-chart-pie"></use>
            </svg> Date Wise Report</a></li>
        <li class="c-sidebar-nav-item"><a class="c-sidebar-nav-link" href="${pageContext.request.contextPath}/report/cashierReport">
            <svg class="c-sidebar-nav-icon">
              <use xlink:href="${templateUrl}/node_modules/@coreui/icons/sprites/free.svg#cil-puzzle"></use>
            </svg> Cashier Report</a></li>
        <li class="c-sidebar-nav-item"><a class="c-sidebar-nav-link" href="${pageContext.request.contextPath}/report/transactionReport">
            <svg class="c-sidebar-nav-icon">
              <use xlink:href="${templateUrl}/node_modules/@coreui/icons/sprites/free.svg#cil-list-rich"></use>
            </svg> Transaction Report</a></li>
         <%} %>
      </ul>
      <button class="c-sidebar-minimizer c-class-toggler" type="button" data-target="_parent" data-class="c-sidebar-minimized"></button>
    </div>
    <div class="c-wrapper c-fixed-components">
      <header class="c-header c-header-light c-header-fixed c-header-with-subheader">
        <button class="c-header-toggler c-class-toggler d-lg-none mfe-auto" type="button" data-target="#sidebar" data-class="c-sidebar-show">
          <svg class="c-icon c-icon-lg">
            <use xlink:href="${templateUrl}/node_modules/@coreui/icons/sprites/free.svg#cil-menu"></use>
          </svg>
        </button><a class="c-header-brand d-lg-none" href="#">
          <b><%=siteName %></b>
          </a>
        <button class="c-header-toggler c-class-toggler mfs-3 d-md-down-none" type="button" data-target="#sidebar" data-class="c-sidebar-lg-show" responsive="true">
          <svg class="c-icon c-icon-lg">
            <use xlink:href="${templateUrl}/node_modules/@coreui/icons/sprites/free.svg#cil-menu"></use>
          </svg>
        </button>
        <ul class="c-header-nav d-md-down-none">
          <li class="c-header-nav-item px-3"><a class="c-header-nav-link" href="${pageContext.request.contextPath}/home">Dashboard</a></li>
          
        <% if(loggedInUserRole==5){ //Cashier %>
          <li class="c-header-nav-item px-3"><a class="c-header-nav-link" href="${pageContext.request.contextPath}/exit/screen">Exit Screen</a></li>
          <li class="c-header-nav-item px-3"><a class="c-header-nav-link" href="${pageContext.request.contextPath}/exit/addLot">LOT</a></li>
          <li class="c-header-nav-item px-3"><a class="c-header-nav-link" href="${pageContext.request.contextPath}/exit/lot">LOT List</a></li>
        <%} %>
        </ul>
        <ul class="c-header-nav ml-auto mr-4">
        	<%
        	
        	if(session.getAttribute("exit_gate_id")!=null){ %>
        	<li class="c-header-nav-item" style="margin-right:30px;">Exit Gate: <%=session.getAttribute("exit_gate_name") %></li>
          	<% } %>
          	<li class="c-header-nav-item dropdown">
	          <a class="c-header-nav-link" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
	              Welcome, <%=session.getAttribute("name") %>
	          </a>
	          <div class="dropdown-menu dropdown-menu-right pt-0">
	              <a class="dropdown-item">
	                <svg class="c-icon mr-2">
	                  <use xlink:href="${templateUrl}/node_modules/@coreui/icons/sprites/free.svg#cil-lock-locked"></use>
	                </svg> Change Password</a>
	                <form:form action="${pageContext.request.contextPath}/logout" method="POST">
	                <button class="dropdown-item" type="submit">
	                  <svg class="c-icon mr-2">
		              	<use xlink:href="${templateUrl}/node_modules/@coreui/icons/sprites/free.svg#cil-account-logout"></use>
		              </svg>
	                  Logout
	                </button>
	                </form:form>
	            </div>
	        </li>
        </ul>
      </header>
      <div class="c-body">
      <div class="container-fluid">
			<%=session.getAttribute("flashMessage") %>
		</div>
      <%
      session.setAttribute("flashMessage","");
      %>