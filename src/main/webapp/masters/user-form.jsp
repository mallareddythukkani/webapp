<jsp:include page="../common/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="card">
  <form:form action="${pageContext.request.contextPath}/user/save" cssClass="form-horizontal" 
  method="post" modelAttribute="user">
  <div class="card-header"><strong>${pageTitle }</strong></div>
  <div class="card-body">
      <form:hidden path="userId" />
      <form:hidden path="locationId" />
      <c:if test="${user.userId > 0 }"> <form:hidden path="password" /> </c:if>
      <div class="form-group row">
        <label class="col-md-2 col-form-label" >Role</label>
        <div class="col-md-3">
          <form:select path="role" cssClass="form-control" id="role" required="true">
          	<option value="">Select Role</option>
          	<c:forEach var="roleRec" items="${allowedRoles}">
          		<option value="${roleRec.roleId }" <c:if test="${roleRec.roleId == userRoleId}"> selected </c:if>>${roleRec.name }</option>
          	</c:forEach>
          </form:select>
        </div>
      </div>
      <div class="form-group row">
        <label class="col-md-2 col-form-label" >Name</label>
        <div class="col-md-3">
          <form:input cssClass="form-control" path="name" required="true" />
        </div>
      </div>
      <div class="form-group row">
        <label class="col-md-2 col-form-label" >Username</label>
        <div class="col-md-3">
          <form:input cssClass="form-control" path="username" required="true" />
        </div>
      </div>
      <c:if test="${user.userId == 0 }">
      <div class="form-group row">
        <label class="col-md-2 col-form-label" >Password</label>
        <div class="col-md-3">
          <input type="password" class="form-control" name="password" required>
        </div>
      </div>
      </c:if>
      <div id="exitGateBlock" class="form-group row <c:if test="${userRoleId!=5}"> d-none </c:if>">
        <label class="col-md-2 col-form-label" >Exit Gate</label>
        <div class="col-md-3">
          <select name="exit_gate" class="form-control">
          	<option value="">Select Exit Gate</option>
          	<c:forEach var="tempRec" items="${exitGateList}">
          		<option value="${tempRec.exitGateId }" <c:if test="${tempRec.exitGateId == userExitGateId}"> selected </c:if>>${tempRec.name }</option>
          	</c:forEach>
          </select>
        </div>
      </div>
    
  </div>
  <div class="card-footer">
    <button class="btn btn-sm btn-primary" type="submit"> Submit</button>
    <button class="btn btn-sm btn-danger" type="reset"> Reset</button>
  </div>
  </form:form>
</div>
 
<jsp:include page="../common/footer.jsp" />
<script type="text/javascript">
	$(document).on('change','#role',function(){
		var val = $(this).val();
		if(val==5)
			$('#exitGateBlock').removeClass('d-none');
		else
			$('#exitGateBlock').addClass('d-none');
	});
</script>