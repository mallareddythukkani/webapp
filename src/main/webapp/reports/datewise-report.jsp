<%@page import="com.parkingservice.webapp.bean.DailyReport"%>
<jsp:include page="../common/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% int i = 1;
String rSubmit = (String) request.getAttribute("rSubmit");
%>
<style type="text/css">
thead tr{
 background-color:#3C4B64;
 color:#fff;
}

</style>
<div class="row">
  <div class="col-lg-12">
    <div class="card">
      <div class="card-header">
      <h4 class="card-title mb-0">Date Wise Report</h4>
		<form action="${pageContext.request.contextPath}/report/datewiseReport">
			<div class="row">
				<div class="col-lg-3">
					From Date
					<input value="${fromDate}" class="form-control" id="date-input" 
					type="date" name="fromdate" placeholder="date" >
				</div>
				<div class="col-lg-3">
					To Date
					<input value="${toDate}" class="form-control" id="date-input" 
					type="date" name="todate" placeholder="date" >
				</div>
				<div class="col-lg-2">
					<br>
					<button type="submit" name="rSubmit" value="1" class="btn btn-sm btn-primary">Submit</button>
					<button type="submit" name="rSubmit" value="2" class="btn btn-sm btn-danger">Print</button>
				</div>
			</div>
	
		</form>
		<%-- <a href="${pageContext.request.contextPath}/user/add" class="btn btn-primary btn-sm" style="float:right;">ADD</a> --%>
	  </div>
      <div class="card-body">
      <% if(rSubmit != null) { 

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
      %>
        <table class="table table-responsive-sm table-bordered table-striped table-sm">
                  <thead>
                    <tr>
                      <td rowspan="2"></td>
                      <td rowspan="2" valign="top">Vehicle In</td>
                      <td colspan="2" align="center">Collection</td>
                      <td colspan="2" align="center">LOT</td>
                      <td colspan="2" align="center">Overnight</td>
                      <td rowspan="2">Zero</td>
                      <td rowspan="2">Exempted</td>
                      <td rowspan="2">Not Settled</td>
                      <td rowspan="2">Settled Next Day</td>
                    </tr>
                    <tr>
                      <td>Amount</td>
                      <td>Count</td>
                      <td>Amount</td>
                      <td>Count</td>
                      <td>Amount</td>
                      <td>Count</td>
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
                		<td align="right"><%=dr4w.getZeroCnt() %></td>
                		<td align="right"><%=dr4w.getExemptedCnt() %></td>
                		<td align="right"><%=dr4w.getNotSettledCnt() %></td>
                		<td align="right"><%=dr4w.getSettledNextDayCnt() %></td>
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
                		<td align="right"><%=dr2w.getZeroCnt() %></td>
                		<td align="right"><%=dr2w.getExemptedCnt() %></td>
                		<td align="right"><%=dr2w.getNotSettledCnt() %></td>
                		<td align="right"><%=dr2w.getSettledNextDayCnt() %></td>
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
                		<td align="right"><%=drTot.getZeroCnt() %></td>
                		<td align="right"><%=drTot.getExemptedCnt() %></td>
                		<td align="right"><%=drTot.getNotSettledCnt() %></td>
                		<td align="right"><%=drTot.getSettledNextDayCnt() %></td>
                	</tr>  
					
				</tbody>
			   <!-- /.container-fluid -->
			</table>
	  <% } %>
	  </div>
    </div>
  </div>
  <!-- /.col-->
</div>  
      <!-- End of Main Content -->
 
    
<jsp:include page="../common/footer.jsp" />