<%@page import="com.parkingservice.webapp.bean.DailyReport"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% int i = 1;
DailyReport dr4w = (DailyReport) request.getAttribute("4wData");
DailyReport dr2w = (DailyReport) request.getAttribute("2wData");
DailyReport drTot = new DailyReport();
drTot.setVehicleCnt(dr4w.getVehicleCnt()+dr2w.getVehicleCnt());
drTot.setCollectionAmt(dr4w.getCollectionAmt()+dr2w.getCollectionAmt());
drTot.setCollectionCnt(dr4w.getCollectionCnt()+dr2w.getCollectionCnt());
drTot.setLotAmt(dr4w.getLotAmt()+dr2w.getLotAmt());
drTot.setLotCnt(dr4w.getLotCnt()+dr2w.getLotCnt());
drTot.setOvernightAmt(dr4w.getOvernightAmt()+dr2w.getOvernightAmt());
drTot.setOvernightCnt(dr4w.getOvernightCnt()+dr2w.getOvernightCnt());
drTot.setZeroCnt(dr4w.getZeroCnt()+dr2w.getZeroCnt());
drTot.setExemptedCnt(dr4w.getExemptedCnt()+dr2w.getExemptedCnt());
drTot.setNotSettledCnt(dr4w.getNotSettledCnt()+dr2w.getNotSettledCnt());
drTot.setSettledNextDayCnt(dr4w.getSettledNextDayCnt()+dr2w.getSettledNextDayCnt());
String siteName = (String) request.getSession(false).getAttribute("sitename");
pageContext.setAttribute("templateUrl", request.getContextPath()+"/resources/static/template2");

%>
<html>
	<head>
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
		    /*background: yellow;*/
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
<body>
<div align="left" style="width:180px;border:0px solid #000;margin-left:10px;">
    <h3 align="center"><%=siteName %></h3>
    <p align="center" style="border-bottom: 1px dashed #000; padding-bottom:5px; font-weight:bold;">Date Wise Report <br>${fromDate } to ${toDate } </p>
    <br>
        <form>
		<input type="hidden" id="redirectUrl" value="${pageContext.request.contextPath}/report/datewiseReport">
		</form>
		<table class="table table-responsive-sm table-bordered table-striped table-sm">
                  <thead>
                    <tr>
                      <td rowspan="2"></td>
                      <td rowspan="2" valign="top">V-In</td>
                      <td colspan="2" align="center">Collection</td>
                      <td colspan="2" align="center">LOT</td>
                      <td colspan="2" align="center">Overnight</td>
                    </tr>
                    <tr>
                      <td>Amt</td>
                      <td>Cnt</td>
                      <td>Amt</td>
                      <td>Cnt</td>
                      <td>Amt</td>
                      <td>Cnt</td>
                    </tr>
                  </thead>
              	<tbody>
                	<tr>
                		<td>4-W</td>
                		<td align="right"><%=dr4w.getVehicleCnt() %></td>
                		<td align="right"><%=dr4w.getCollectionAmt() %></td>
                		<td align="right"><%=dr4w.getCollectionCnt() %></td>
                		<td align="right"><%=dr4w.getLotAmt() %></td>
                		<td align="right"><%=dr4w.getLotCnt() %></td>
                		<td align="right"><%=dr4w.getOvernightAmt() %></td>
                		<td align="right"><%=dr4w.getOvernightCnt() %></td>
                	</tr>  
                	<tr>
                		<td>2-W</td>
                		<td align="right"><%=dr2w.getVehicleCnt() %></td>
                		<td align="right"><%=dr2w.getCollectionAmt() %></td>
                		<td align="right"><%=dr2w.getCollectionCnt() %></td>
                		<td align="right"><%=dr2w.getLotAmt() %></td>
                		<td align="right"><%=dr2w.getLotCnt() %></td>
                		<td align="right"><%=dr2w.getOvernightAmt() %></td>
                		<td align="right"><%=dr2w.getOvernightCnt() %></td>
                	</tr>
                	<tr>
                		<th>Total</th>
                		<td align="right"><%=drTot.getVehicleCnt() %></td>
                		<td align="right"><%=drTot.getCollectionAmt() %></td>
                		<td align="right"><%=drTot.getCollectionCnt() %></td>
                		<td align="right"><%=drTot.getLotAmt() %></td>
                		<td align="right"><%=drTot.getLotCnt() %></td>
                		<td align="right"><%=drTot.getOvernightAmt() %></td>
                		<td align="right"><%=drTot.getOvernightCnt() %></td>
                	</tr>  
					
				</tbody>
			   <!-- /.container-fluid -->
			</table>
<br>
		<table class="table table-responsive-sm table-bordered table-striped table-sm">
                  <thead>
                    <tr>
                      <td></td>
                      <td>Zero</td>
                      <td>Exmptd</td>
                      <td>Not-Stld</td>
                      <td>Stld-Nxt-Dy</td>
                    </tr>
                  </thead>
              	<tbody>
                	<tr>
                		<td>4-W</td>
                		<td align="right"><%=dr4w.getZeroCnt() %></td>
                		<td align="right"><%=dr4w.getExemptedCnt() %></td>
                		<td align="right"><%=dr4w.getNotSettledCnt() %></td>
                		<td align="right"><%=dr4w.getSettledNextDayCnt() %></td>
                	</tr>  
                	<tr>
                		<td>2-W</td>
                		<td align="right"><%=dr2w.getZeroCnt() %></td>
                		<td align="right"><%=dr2w.getExemptedCnt() %></td>
                		<td align="right"><%=dr2w.getNotSettledCnt() %></td>
                		<td align="right"><%=dr2w.getSettledNextDayCnt() %></td>
                	</tr>
                	<tr>
                		<th>Total</th>
                		<td align="right"><%=drTot.getZeroCnt() %></td>
                		<td align="right"><%=drTot.getExemptedCnt() %></td>
                		<td align="right"><%=drTot.getNotSettledCnt() %></td>
                		<td align="right"><%=drTot.getSettledNextDayCnt() %></td>
                	</tr>  
					
				</tbody>
			   <!-- /.container-fluid -->
			</table>
<br>
            <p><small>gen by ${name } <br>at: ${reportTime } </small></p>
		    <br><br>
		    <button class="print_btn" onclick="window.print()">Print</button>  
		    <a class="print_btn" style="margin-left:50px;" href="${pageContext.request.contextPath}/report/dailyReport">Back</a>
		    
<script src="${templateUrl}/js/jquery-min.js"></script>
<script type="text/javascript" language="javascript">
    $(document).ready(function(){
    	window.print();
    	var delay = 3*1000; 
    	var redirectUrl = $("#redirectUrl").val();
    	setTimeout(function(){ window.location = redirectUrl; }, delay);
    });
</script>
</div>
</body>
</html>