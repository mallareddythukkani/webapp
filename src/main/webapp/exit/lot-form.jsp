<jsp:include page="../common/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="card">
  <form:form action="${pageContext.request.contextPath}/exit/saveLot" cssClass="form-horizontal" method="post" modelAttribute="lot">
  <div class="card-header"><strong>LOT (Loss Of Ticket)</strong></div>
  <div class="card-body">
    
      <form:hidden path="lotId" />
	  <form:hidden path="locationId" />
	  <form:hidden path="vehicleTypeId" />
	  <form:hidden path="exitGateId" />
      <div class="form-group row">
        <label class="col-md-2 col-form-label" >Vehicle Number</label>
        <div class="col-md-3">
          <form:input path="vehicleNumber" cssClass="form-control" required="true" />
        </div>
      </div>
      <div class="form-group row">
        <label class="col-md-2 col-form-label" >LOT Amount</label>
        <div class="col-md-3">
          <input type="number" required="true" name="amountCollected" value='<c:out value="${lot.amountCollected}"></c:out>' readonly="readonly" class="form-control">
        </div>
      </div>
      <div class="form-group row">
        <label class="col-md-2 col-form-label" >Name</label>
        <div class="col-md-3">
          <form:input path="name" cssClass="form-control" />
        </div>
      </div>
      <div class="form-group row">
        <label class="col-md-2 col-form-label" >ID Proof</label>
        <div class="col-md-3">
          <form:input path="idProof" cssClass="form-control" />
        </div>
      </div>
      <div class="form-group row">
        <label class="col-md-2 col-form-label" >Mobile</label>
        <div class="col-md-3">
          <form:input path="mobile" cssClass="form-control" />
        </div>
      </div>
      <div class="form-group row">
        <label class="col-md-2 col-form-label" >Remarks</label>
        <div class="col-md-3">
          <form:input path="remarks" cssClass="form-control" />
        </div>
      </div>
    
  </div>
  <div class="card-footer">
    <button class="btn btn-sm btn-primary" type="submit"> Submit</button>
    <button class="btn btn-sm btn-danger" type="reset"> Reset</button>
  </div>
  </form:form>
</div>
                  
                  
                  
<%-- --%>
   
<jsp:include page="../common/footer.jsp" />