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
Map<Integer,Integer> paymentList = (Map<Integer,Integer>) request.getAttribute("paymentRes");
Map<Integer,Integer> lotList = (Map<Integer,Integer>) request.getAttribute("lotRes");
Map<String,Integer> exemptList = (Map<String,Integer>) request.getAttribute("exemptRes");
String rSubmit = (String) request.getAttribute("rSubmit");
//String location_id = (String) request.getSession(false).getAttribute("location_id");
String name = (String) request.getSession(false).getAttribute("name");
String siteName = (String) request.getSession(false).getAttribute("sitename");
pageContext.setAttribute("templateUrl", request.getContextPath()+"/resources/static/template2");
%>
<body>
<form>
<input type="hidden" id="redirectUrl" value="${pageContext.request.contextPath}/report/cashierReport">
</form>
<div align="left" style="width:180px;border:0px solid #000; margin-left:30px;">
    
      		<% if(paymentList != null || lotList != null || exemptList != null) { %>
      		<h3 align="center"><%=siteName %></h3>
		    <p align="center" style="border-bottom: 1px dashed #000; padding-bottom:5px; font-weight:bold;">Cashier Report for ${ondate } </p>
		    <br>
		    <p><small>Cashier: ${cashierName }
		    <c:if test="${from_time != '' }">
		    	<br> From Time: ${from_time}
		    </c:if>
		    <c:if test="${to_time != '' }">
		    	<br> To Time: ${to_time}
		    </c:if>
		    </small>
		    </p>
      		<table border="1" width="100%" class="table table-bordered stats_tbl">
                <thead>
                <tr><th colspan="3" align="center">Collection</th></tr>
                <tr>
                    <th width="25%" style="text-align:center">Amount</th>
                    <th width="25%" style="text-align:center">Count</th>
                    <th width="50%" style="text-align:center">Total</th>
                </tr>
                </thead>
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
                 	<tr><th colspan="2" align="right">TOTAL</th><td align="right"><b><%=grandTot %></b></td></tr>
                    
                </tbody>
            </table>
            <br>
      		<table border="1" width="100%" class="table table-bordered stats_tbl">
                <thead>
                <tr><th colspan="3" align="center">Exempted</th></tr>
                </thead>
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