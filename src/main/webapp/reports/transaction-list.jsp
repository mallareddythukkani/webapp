<%@page import="com.parkingservice.webapp.model.ParkingTransaction"%>
<%@page import="com.parkingservice.webapp.model.TransactionData"%>
<%@page import="java.util.List"%>
<%@page import="com.parkingservice.webapp.util.Helper"%>
<jsp:include page="../common/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% int i = 1; ;
List<ParkingTransaction> res = (List<ParkingTransaction>) request.getAttribute("ptList");
%>

<div class="row">
  <div class="col-lg-12">
    <div class="card">
      <div class="card-header"> <h4 class="card-title mb-0">Transaction List</h4></div>
      <div class="card-body">
        <table class="table table-responsive-sm table-bordered table-striped table-sm">
          <thead>
            <tr>
              	<th width="40" align="left">S NO</th>
		        <th width="150" align="left">Transaction ID</th>
		        <th width="100" align="left">Trans Type</th>
		        <th width="200" align="left">Entry Time</th>
		        <th width="200" align="left">Exit Time</th>
		        <th width="100" align="left">Payment Type</th>
		        <th width="100" align="left">Amount</th>
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
        	  %>
        	  <tr>
	              <td><%=i++%></td>
	              <td><%=pt.getTransactionId() %></td>
	              <td><%=transType%></td>
	              <td><%=pt.getEntryTime() %></td>
	              <td><%=pt.getExitTime() %></td>
	              <td><%=pt.getPaymentMethodId() %></td>
	              <td><%=amount %></td>
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
<jsp:include page="../common/footer.jsp" />