<%@page import="com.parkingservice.webapp.model.ParkingTransaction"%>
<%@page import="com.parkingservice.webapp.model.TransactionData"%>
<%@page import="java.util.List"%>
<%@page import="com.parkingservice.webapp.util.Helper"%>
<%@page import="java.util.Map"%>
<jsp:include page="../common/header.jsp" />
<style>
.stats_tbl td, .stats_tbl th{
	padding:1px !important;
}

thead tr{
 background-color:#3C4B64;
 color:#fff;
}
</style>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<% int i = 1;
List<ParkingTransaction> res = (List<ParkingTransaction>) request.getAttribute("ptList");
String rSubmit = (String) request.getAttribute("rSubmit");
%>
<div class="row">
  <div class="col-lg-12">
    <div class="card">
      <div class="card-header">
      <h4 class="card-title mb-0">${pageTitle}</h4><br>
		<form:form action="${pageContext.request.contextPath}/report/transactionReport">
			<div class="row">
				<div class="col-lg-2">
					<input value="${ondate}" class="form-control" id="date-input" 
					type="date" name="ondate" placeholder="date" >
				</div>
				<div class="col-lg-3">
					<select class="form-control" name="vehicleTypeId" id="vehicleTypeId">
			          	<option value="">Select Vehicle Type</option>
			          	<c:forEach var="tempRec" items="${vehicleTypeList}">
			          		<option value="${tempRec.vehicleTypeId }" <c:if test="${tempRec.vehicleTypeId == vehicleTypeId}"> selected </c:if>>${tempRec.name }</option>
			          	</c:forEach>
		          	</select>
				</div>
				<div class="col-lg-2">
					<input class="form-control" 
					type="time" name="from_time" value="${from_time }">
				</div>
				<div class="col-lg-2">
					<input class="form-control" 
					type="time" name="to_time"  value="${to_time }">
				</div>
				<div class="col-lg-3">
					<button type="submit" class="btn btn-sm btn-primary" name="rSubmit" value="1">Submit</button>
					<a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/report/cashierReport">Reset</a>
					<button type="submit" class="btn btn-sm btn-danger" name="rSubmit" value="2">Print</button>
				</div>
			</div>
	
		</form:form>
		<%-- <a href="${pageContext.request.contextPath}/user/add" class="btn btn-primary btn-sm" style="float:right;">ADD</a> --%>
	  </div>
	  <div class="card-body">
        <table class="table table-responsive-sm table-bordered table-striped table-sm">
          <thead>
            <tr>
              	<th width="40" align="left">S NO</th>
		        <th width="150" align="left">Transaction ID</th>
		        <th width="100" align="left">Vehicle Type</th>
		        <th width="100" align="left">Vehicle Number</th>
		        <th width="200" align="left">Entry Time</th>
		        <th width="200" align="left">Exit Time</th>
		        <th width="100" align="left">Amount</th>
		        <th width="150" align="left">Remarks</th>
            </tr>
          </thead>
          <tbody>
          <!-- loop over and print our records -->
		  <%-- <c:forEach var="ptRow" items="${ptList}">
            <tr>
              <td><%=i++%></td>
              <td>${ptRow.transactionId}</td>
              <td>${ptRow.transactionData.transTypeId}</td>
              <td>${ptRow.entryTime}</td>
              <td>${ptRow.exitTime}</td>
              <td>${ptRow.paymentMethodId}</td>
              <td>${ptRow.amount}</td>
            </tr>
          </c:forEach> --%>
          <% for(int j=0; j<res.size();j++){
        	  ParkingTransaction pt = res.get(j);
        	  String amount = (pt.getExempted()==1)?"0 /- (Exempted)":pt.getAmount()+" /-";
        	  int transTypeID = pt.getTransactionData().getTransTypeId();
        	  String transType = Helper.getTransTypeByID(transTypeID);
        	  String exitTime = (pt.getExitTime()==null)?"NA":pt.getExitTime();
        	  String remarks = (pt.getRemarks() != null)?pt.getRemarks():"";
        	  if(pt.getTransactionData().getRemarks3() != null)
        		  remarks = pt.getTransactionData().getRemarks3();
        	  %>
        	  <tr>
	              <td><%=i++%></td>
	              <td><%=pt.getTransactionId() %></td>
	              <td><%=pt.getVehicleType().getName() %></td>
	              <td><%=pt.getVehicleNumber()%></td>
	              <td><%=pt.getEntryTime() %></td>
	              <td><%=exitTime %></td>
	              <td><%=amount %></td>
	              <td><%=remarks %></td>
	            </tr>
          <% } %>
          <%
          if(res.size()==0){
        	  %>
        	  <tr><td colspan="7" align="center">No Transactions Found</td></tr>
        	  <%
          }
          %>
          </tbody>
        </table>
      </div>
   
      </div>
  </div>
  <!-- /.col-->
</div>  
      <!-- End of Main Content -->
<jsp:include page="../common/footer.jsp" />