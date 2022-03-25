<jsp:include page="../common/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% int i = 0; %>
<div class="row">
  <div class="col-lg-12">
    <div class="card">
      <div class="card-header"><h4 class="card-title mb-0">LOT (Loss Of Ticket)</h4> 
      <a href="${pageContext.request.contextPath}/exit/addLot" class="btn btn-primary btn-sm" style="float:right;">ADD</a></div>
      <div class="card-body">
        <table class="table table-responsive-sm table-bordered table-striped table-sm">
                  <thead>
                    <tr>
                      <th>LOT ID</th>
                      <th>Vehicle Number</th>
                      <th>Name</th>
                      <th>Mobile</th>
                      <th>ID Proof</th>
                      <th>Amount</th>
                    </tr>
                  </thead>
              <tbody>
                  
					<!-- loop over and print our records -->
					<c:forEach var="tempRecord" items="${records}">
						<tr>
							<td>${tempRecord.lotId}</td>
							<td>${tempRecord.vehicleNumber}</td>
							<td>${tempRecord.name}</td>
							<td>${tempRecord.mobile}</td>
							<td>${tempRecord.idProof}</td>
							<td align="right">${tempRecord.amountCollected}</td>
						</tr>
						<% i++; %>
					</c:forEach>
					<%
			          if(i==0){
			        	  %>
			        	  <tr><td colspan="7" align="center">No Records Found</td></tr>
			        <% }%>
				</tbody>
			   <!-- /.container-fluid -->
			</table>
		  </div>
    </div>
  </div>
  <!-- /.col-->
</div>  
      <!-- End of Main Content -->
    
<jsp:include page="../common/footer.jsp" />