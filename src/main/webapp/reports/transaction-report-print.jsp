<%@page import="com.parkingservice.webapp.model.ParkingTransaction"%>
<%@page import="com.parkingservice.webapp.model.TransactionData"%>
<%@page import="java.util.List"%>
<%@page import="com.parkingservice.webapp.util.Helper"%>
<%@page import="java.util.Map"%>
<html>
	<head>
		<style type="text/css">
		p {
		    margin-block-end: 0em !important;
		    margin-block-start: 0em !important;
		    padding-bottom: 3px;
		}
		.table-bordered, .table-bordered th, .table-bordered td {
		    border: 1px solid;
		    border-color: #000;
		    font-size: 12px;
		}
		table {
		    border-collapse: collapse;
		}
		body{ font-size: 12px;}
		@media print { .print_btn{ display: none;}}
		h3{margin-bottom: 10px;}
		</style>
	</head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<% int i = 1;
List<ParkingTransaction> res = (List<ParkingTransaction>) request.getAttribute("ptList");
String rSubmit = (String) request.getAttribute("rSubmit");
//String location_id = (String) request.getSession(false).getAttribute("location_id");
String name = (String) request.getSession(false).getAttribute("name");
String siteName = (String) request.getSession(false).getAttribute("sitename");
pageContext.setAttribute("templateUrl", request.getContextPath()+"/resources/static/template2");
%>
<body>
<form>
<input type="hidden" id="redirectUrl" value="${pageContext.request.contextPath}/report/transactionReport">
</form>
<div align="left" style="border:0px solid #000; margin-left:30px;">
    
      		<% if(res != null) { %>
      		<h3 align="center"><%=siteName %></h3>
		    <p align="center" style="border-bottom: 1px dashed #000; padding-bottom:5px; font-weight:bold;">Transaction Report for ${ondate } </p>
		    <br>
		    <p><small>
		    <c:if test="${from_time != '' }">
		    	<br> From Time: ${from_time}
		    </c:if>
		    <c:if test="${to_time != '' }">
		    	<br> To Time: ${to_time}
		    </c:if>
		    <c:if test="${vehicleTypeId == 1 }">
		    	<br> Vehicle Type: 4-Wheeler
		    </c:if>
		    <c:if test="${vehicleTypeId == 2 }">
		    	<br> Vehicle Type: 2-Wheeler
		    </c:if>
		    </small>
		    </p>
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
     
		    <br>

            <p><small>gen by ${name } <br>at: ${reportTime } </small></p>
		    <br><br>
		    <button class="print_btn" onclick="window.print()">Print</button>  
		    <a class="print_btn" style="margin-left:50px;" href="${pageContext.request.contextPath}/report/cashierReport">Back</a>
		    
<script src="${templateUrl}/js/jquery-min.js"></script>
<script type="text/javascript" language="javascript">
    $(document).ready(function(){
    	window.print();
    	var delay = 3*1000; 
    	var redirectUrl = $("#redirectUrl").val();
    	setTimeout(function(){ window.location = redirectUrl; }, delay);
    });
</script>
                        
            <%} //end of if block
      		else if(rSubmit != null){
      			%>
      				<h6>No Records Found</h6>
      			<%
      		}
      		%>
      		
</div>
</body>
</html>