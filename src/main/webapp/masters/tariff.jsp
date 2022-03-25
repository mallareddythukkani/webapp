<%@page import="com.parkingservice.webapp.util.Helper"%>
<jsp:include page="../common/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<% int i = 0;
//List<ParkingTariff> pt_4w = (List<ParkingTariff>) request.getAttribute("pt_4w");
%>
<div class="row">
  <div class="col-lg-12">
    <div class="card">
      <div class="card-header">
      <h4 class="card-title mb-0">Tariff Details</h4> 
      </div>
      <div class="card-body">
      	
      	<div class="row">
      	<div class="col-md-6">
      		<p>
      			<span style="font-size:1.09375rem; font-weight:500; line-height:1.2;">4-Wheeler</span>
      			<a href="${pageContext.request.contextPath}/updateTariff/1" style="float:right">update</a>
      		</p>
      		<table style="width:100%" class="table table-responsive-sm table-bordered table-striped table-sm">
              <thead>
                <tr>
                  <th>Duration</th>
                  <th>Tariff (Rs/-)</th>
                </tr>
              </thead>
              <tbody>
                  <!-- loop over and print our records -->
					<c:forEach var="tempRecord" items="${pt_4w}">
						<tr>
							<td>${tempRecord.fromTime}m - ${tempRecord.toTime}m</td>
							<td>${tempRecord.tariff} /-</td>
						</tr>
						<c:if test="${tempRecord.repeatMinutes>0 }">
							<tr>
								<td>Every Additional ${tempRecord.repeatMinutes }m</td>
								<td>${tempRecord.repeatTariff} /-</td>
							</tr>
						</c:if>
					</c:forEach>	
				</tbody>
			   <!-- /.container-fluid -->
			</table>
			<p>LOT Amount : ${lot_4w } /-</p>
			<p>Overnight Amount : ${overnt_4w } /-</p>
      	</div>
        <div class="col-md-6">
        	<p>
      			<span style="font-size:1.09375rem; font-weight:500; line-height:1.2;">2-Wheeler</span>
      			<a href="${pageContext.request.contextPath}/updateTariff/2" style="float:right">update</a>
      		</p>
      		<table style="width:100%" class="table table-responsive-sm table-bordered table-striped table-sm">
              <thead>
                <tr>
                  <th>Duration</th>
                  <th>Tariff (Rs/-)</th>
                </tr>
              </thead>
              <tbody>
                  <!-- loop over and print our records -->
					<c:forEach var="tempRecord" items="${pt_2w}">
						<tr>
							<td>${tempRecord.fromTime}m - ${tempRecord.toTime}m</td>
							<td>${tempRecord.tariff} /-</td>
						</tr>
						<c:if test="${tempRecord.repeatMinutes>0 }">
							<tr>
								<td>Every Additional ${tempRecord.repeatMinutes }m</td>
								<td>${tempRecord.repeatTariff} /-</td>
							</tr>
						</c:if>
					</c:forEach>
					
				</tbody>
			   <!-- /.container-fluid -->
			</table>
			<p>LOT Amount : ${lot_2w } /-</p>
			<p>Overnight Amount : ${overnt_2w } /-</p>
      	</div>
      	</div>
		</div>
    </div>
  </div>
  <!-- /.col-->
</div>  
      <!-- End of Main Content -->
    
<jsp:include page="../common/footer.jsp" />