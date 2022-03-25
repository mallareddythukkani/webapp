<%@page import="com.parkingservice.webapp.model.ParkingTransaction"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<title>Parking Receipt</title>
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
ParkingTransaction pt = (ParkingTransaction) request.getAttribute("pt_row");
int defaultPrint = (pt.getExempted()==0&&pt.getAmount()>0)?1:0;
String amountStr = (defaultPrint==1)?(pt.getAmount()+"/-"):"0/- (Exempted)";
%>
<form>
<input type="hidden" id="exitScreenUrl" value="${pageContext.request.contextPath}/exit/screen">
<input type="hidden" id="defaultPrint" value="<%=defaultPrint%>">
</form>
<div align="left" style="width:250px;border:0px solid #000;margin-left:10px">
    <p align="center">Parking Receipt</p>
    <p>-----------------------------------------</p>
    <p>Vehicle Type: <%=pt.getVehicleType().getName()%></p>
    <p><div class="time_head">In Time :</div> <%=pt.getEntryTime()%></p>
    <p><div class="time_head">Out Time:</div> <%=pt.getExitTime()%></p>
    <p style="font-size:18px;font-weight:700;">Amount : <%=amountStr%></p>
    <p>-----------------------------------------</p>
    <p align="center">Thank you, Visit again</p>
    <button class="print_btn" onclick="window.print()">Print</button>
    <a style="float:right; margin-right:20px;" class="print_btn" href="${pageContext.request.contextPath}/exit/screen">Back</a>
</div>
<script src="${templateUrl}/js/jquery-min.js"></script>
<script type="text/javascript" language="javascript">
    $(document).ready(function(){
    	var defaultPrint = $("#defaultPrint").val();
    	if(defaultPrint==1)
    		window.print();
    	var delay = 3*1000; 
    	var exitScreenUrl = $("#exitScreenUrl").val();
    	setTimeout(function(){ window.location = exitScreenUrl; }, delay);
    });
</script>
</body>
</html>