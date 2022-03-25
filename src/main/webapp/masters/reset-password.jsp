<%@page import="com.parkingservice.webapp.model.User"%>
<jsp:include page="../common/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="card">
  <form:form action="${pageContext.request.contextPath}/user/updatePassword" cssClass="form-horizontal" 
  method="post" >
  <div class="card-header"><strong>${pageTitle }</strong></div>
  <% 
  User theUser = (User) request.getAttribute("user");
  
  %>
  <div class="card-body">
      <input type="hidden" name="userId" value="${user.userId }">
      <div class="form-group row">
        <label class="col-md-2 col-form-label" >Role</label>
        <div class="col-md-3">
          ${user.role.name }
        </div>
      </div>
      <div class="form-group row">
        <label class="col-md-2 col-form-label" >Name</label>
        <div class="col-md-3">
          ${user.name }
        </div>
      </div>
      <div class="form-group row">
        <label class="col-md-2 col-form-label" >Username</label>
        <div class="col-md-3">
          ${user.username }
        </div>
      </div>
      <div class="form-group row">
        <label class="col-md-2 col-form-label" >Password</label>
        <div class="col-md-3">
          <input type="password" class="form-control" name="password" required>
        </div>
      </div>
    
  </div>
  <div class="card-footer">
    <button class="btn btn-sm btn-primary" type="submit"> Submit</button>
  </div>
  </form:form>
</div>
 
<jsp:include page="../common/footer.jsp" />