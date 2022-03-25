<%@page import="com.parkingservice.webapp.model.LOT"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<title>LOT Receipt</title>
<% 
	pageContext.setAttribute("templateUrl", request.getContextPath()+"/resources/static/template2");
%>
<style type="text/css">
.color
{
    background-color:Red;
}
p {
    margin-block-end: 0em !important;
    margin-block-start: 0em !important;
    padding-bottom: 3px;
}
.time_head{
    width: 80px !important;
    display: inline-block;
}
@media print { .print_btn{ display: none;}}
</style>
</head>
<body>
<%
LOT lot = (LOT) request.getAttribute("lot_row");
%>
<form>
<input type="hidden" id="redirectUrl" value="${pageContext.request.contextPath}/exit/lot">
</form>
<div align="left" style="width:250px;border:0px solid #000;margin-left:10px">
    <p align="center">LOT Receipt</p>
    <p>-----------------------------------------</p>
    <p>Vehicle Type: ${vehicleType }</p>
    <p><div class="time_head">Vehicle No:</div> <%=lot.getVehicleNumber()%></p>
    <p><div class="time_head">Out Time:</div> <%=lot.getCreatedTime()%></p>
    <p style="font-size:18px;font-weight:700;">Amount : <%=lot.getAmountCollected()%> /-</p>
    <p>-----------------------------------------</p>
    <p align="center">Thank you, Visit again</p>
    <button class="print_btn" onclick="window.print()">Print</button>
    <a style="float:right; margin-right:20px;" class="print_btn" href="${pageContext.request.contextPath}/exit/screen">Back</a>
</div>
<script src="${templateUrl}/js/jquery-min.js"></script>
<script type="text/javascript" language="javascript">
    $(document).ready(function(){
    	window.print();
    	var delay = 3*1000; 
    	var exitScreenUrl = $("#redirectUrl").val();
    	//setTimeout(function(){ window.location = exitScreenUrl; }, delay);
    });
</script>
</body>
</html>