<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<% pageContext.setAttribute("templateUrl", request.getContextPath()+"/resources/static/template2"); %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <base href="./">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <meta name="description" content="CoreUI - Open Source Bootstrap Admin Template">
    <meta name="author" content="Łukasz Holeczek">
    <meta name="keyword" content="Bootstrap,Admin,Template,Open,Source,jQuery,CSS,HTML,RWD,Dashboard">
    <title>Login</title>
 <!-- Main styles for this application-->
    <link href="${templateUrl}/css/style.css" rel="stylesheet">
    <!-- Global site tag (gtag.js) - Google Analytics-->
    
  </head>
  <body class="c-app flex-row align-items-center">
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-md-8">
          <div class="card-group">
            <div class="card p-4">
              <div class="card-body">
              	<!-- Check for login error -->
				<c:if test="${param.error != null}">
					<i class="text-danger">Invalid username and password.</i>
				</c:if>
				<c:if test="${param.logout != null}">
					<i class="text-success">You have been logged out.</i>
				</c:if>
                <h1>Login</h1>
                <p class="text-muted">Sign In to your account</p>
                <form:form class="user" action="${pageContext.request.contextPath}/login" method="POST">
                <div class="input-group mb-3">
                  <div class="input-group-prepend"><span class="input-group-text">
                      <svg class="c-icon">
                        <use xlink:href="${templateUrl}/node_modules/@coreui/icons/sprites/free.svg#cil-user"></use>
                      </svg></span></div>
                  <input class="form-control" type="text" name="username" placeholder="Username">
                </div>
                <div class="input-group mb-4">
                  <div class="input-group-prepend"><span class="input-group-text">
                      <svg class="c-icon">
                        <use xlink:href="${templateUrl}/node_modules/@coreui/icons/sprites/free.svg#cil-lock-locked"></use>
                      </svg></span></div>
                  <input class="form-control" type="password" name="password" placeholder="Password">
                </div>
                <div class="row">
                  <div class="col-6">
                    <button class="btn btn-primary px-4" type="submit">Login</button>
                  </div>
                  <div class="col-6 text-right">
                    <button class="btn btn-link px-0" type="button">Forgot password?</button>
                  </div>
                </div>
                </form:form>
              </div>
            </div>
            <div class="card text-white bg-primary py-5 d-md-down-none" style="width:44%">
              <div class="card-body text-center">
                <div>
                  <h2>${sitename }</h2>
                  <h4>Parking Application</h4>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- CoreUI and necessary plugins-->
    <script src="${templateUrl}/node_modules/@coreui/coreui/dist/js/coreui.bundle.min.js"></script>
    <!--[if IE]><!-->
    <script src="${templateUrl}/node_modules/@coreui/icons/js/svgxuse.min.js"></script>
    <!--<![endif]-->
  </body>
</html>