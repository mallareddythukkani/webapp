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
Map<Integer,Integer> paymentList = (Map<Integer,Integer>) request.getAttribute("paymentRes");
Map<Integer,Integer> lotList = (Map<Integer,Integer>) request.getAttribute("lotRes");
Map<String,Integer> exemptList = (Map<String,Integer>) request.getAttribute("exemptRes");
String rSubmit = (String) request.getAttribute("rSubmit");
%>
<div class="row">
  <div class="col-lg-12">
    <div class="card">
      <div class="card-header">
      <h4 class="card-title mb-0">Cashier Report</h4><br>
		<form:form action="${pageContext.request.contextPath}/report/cashierReport">
			<div class="row">
				<div class="col-lg-2">
					<input value="${ondate}" class="form-control" id="date-input" 
					type="date" name="ondate" placeholder="date" >
				</div>
				<div class="col-lg-3">
					<select class="form-control" name="cashier" id="cashier" required="true">
			          	<option value="">Select Cashier</option>
			          	<c:forEach var="tempRec" items="${cashierList}">
			          		<option value="${tempRec.userId }" <c:if test="${tempRec.userId == cashierId}"> selected </c:if>>${tempRec.name }</option>
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
      		<% if(paymentList != null || lotList != null || exemptList != null) { %>
                    <div class="row">
                    
                       <div class="col-md-12">
                           <table class="table table-bordered stats_tbl">
                                <thead>
                                    <tr class="table_head_row">
                                        <th width="50%">Collection</th>
                                        <th width="50%">Exemption</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>
                                            <table border="1" width="100%" class="stats_tbl">
                                                <tr>
                                                    <th width="25%" style="text-align:center">Amount</th>
                                                    <th width="25%" style="text-align:center">Count</th>
                                                    <th width="50%" style="text-align:center">Total</th>
                                                </tr>
                                                <tbody>
                                                <% 
                                                int grandTot = 0;
                                                int pmTotCnt = 0;
                                                int pmTotAmt = 0;
                                                if(paymentList != null) { %>
                                                    <tr style="background:#ddd;"><td colspan="3">Payment</td></tr>
                                                    <% 
                                                    
                                                    for (Map.Entry<Integer, Integer> entry : paymentList.entrySet()) { 
                                                    	int pmAmt = entry.getKey();
                                                    	int pmCnt = entry.getValue();
                                                    	int pmRowTot = pmAmt*pmCnt;
                                                    	pmTotCnt += pmCnt;
                                                    	pmTotAmt += pmRowTot;
                                                    %>
                                                        <tr>
                                                            <td align="right"><%=pmAmt %></td>
                                                            <td align="right"><%=pmCnt %></td>
                                                            <td align="right"><%=pmRowTot %></td>
                                                        </tr>
                                                     <% } //payment for block end%>
                                                    <tr><td colspan="2" align="right"><%=pmTotCnt %></td><td align="right"><%=pmTotAmt %></td></tr>
                                                     <% }  //payment if block end%> 
                                                     <% 
                                                     int lotTotCnt = 0;
                                                     int lotTotAmt = 0;
                                                     if(lotList != null) { %>          
                                                     <tr  style="background:#ddd;"><td colspan="3">LOT</td></tr>
                                                     <% 
                                                    
                                                    for (Map.Entry<Integer, Integer> entry : lotList.entrySet()) { 
                                                    	int lotAmt = entry.getKey();
                                                    	int lotCnt = entry.getValue();
                                                    	int lotRowTot = lotAmt*lotCnt;
                                                    	lotTotCnt += lotCnt;
                                                    	lotTotAmt += lotRowTot;
                                                    %>
                                                     	<tr>
                                                            <td align="right"><%=lotAmt %></td>
                                                            <td align="right"><%=lotCnt %></td>
                                                            <td align="right"><%=lotRowTot %></td>
                                                         </tr>
                                                    <% } //lot for block end%>
                                                    <tr><td colspan="2" align="right"><%=lotTotCnt %></td><td align="right"><%=lotTotAmt %></td></tr>
                                                    <% }  //lot if block end
                                                    grandTot = pmTotAmt+lotTotAmt;
                                                    %>  
                                                 	<tr><th colspan="2" align="right">GRAND TOTAL</th><td align="right"><b><%=grandTot %></b></td></tr>
                                                    
                                                </tbody>
                                            </table>

                                        </td>
                                        <td>
                                            <table border="1" width="100%" class="stats_tbl">
                                                <tr><th>Category</th><th align="center">Count</th></tr>
                                                     <% 
                                                    if(exemptList != null){
                                                    int exemptTotCnt = 0;
                                                    for (Map.Entry<String, Integer> entry : exemptList.entrySet()) { 
                                                    	String exempt_reason = entry.getKey();
                                                    	int exemptCnt = entry.getValue();
                                                    	exemptTotCnt += exemptCnt;
                                                    %>
                                                <tr>
                                                    <td><%=exempt_reason %></td>
                                                    <td align="right"><%=exemptCnt %></td>
                                                </tr>
                                                 <% } //exempt for block end%>
                                            <tr>
                                                <td>TOTAL</td>
                                                <td align="right"><%=exemptTotCnt %></td>
                                            </tr>
                                            <% } //exempt if block end %>
                                            </table>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                       </div>
                        <!-- end of if -->
                    </div>
      		<%} //end of if block
      		else if(rSubmit != null){
      			%>
      				<h6>No Records Found</h6>
      			<%
      		}
      		%>
      </div>
    </div>
  </div>
  <!-- /.col-->
</div>  
      <!-- End of Main Content -->
<jsp:include page="../common/footer.jsp" />