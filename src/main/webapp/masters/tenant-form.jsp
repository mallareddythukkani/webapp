<jsp:include page="../common/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="col-md-offset-2 col-md-7">
 	<div class="panel panel-info">
	    
	     <div class="text-center">
                <h1 class="h4 text-gray-900 mb-4">Tenant</h1>
              </div>
	 <div class="panel-body">
     <form:form action="${pageContext.request.contextPath}/tenant/save" cssClass="form-horizontal"
      method="post" modelAttribute="tenant">

      <!-- need to associate this data with customer id -->
      <form:hidden path="tenantId" />

      <div class="form-group">
      
       <label for="column1" class="col-md-3 control-label">Tenant Name</label>
       <div class="col-md-9">
        <form:input path="name" cssClass="form-control" />
       </div>
      </div>
      <div class="form-group">
       <label for="column2" class="col-md-3 control-label">Location</label>
       <div class="col-md-9">
        <form:input path="location" cssClass="form-control" />
        <form:hidden path="locationId" />
       </div>
      </div>

      <div class="form-group">
       <label for="column3" class="col-md-3 control-label">Remarks</label>
       <div class="col-md-9">
        <form:input path="remarks" cssClass="form-control" />
       </div>
      </div>

      <div class="form-group">
       <!-- Button -->
       <div class="col-md-offset-3 col-md-9">
        <form:button class="btn btn-primary">Submit</form:button>
       </div>
      </div>

     </form:form>
    </div>
   </div>
   </div>
  
   
<jsp:include page="../common/footer.jsp" />