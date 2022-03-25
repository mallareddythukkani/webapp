<jsp:include page="../common/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% int i = 1; %>

 <div class="container-fluid">
	<div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Tenant</h6>
              <a href="${pageContext.request.contextPath}/tenant/add" class="btn btn-primary" style="float:right;">ADD</a>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered">
                  <thead>
                    <tr>
                      <th>#</th>
                      <th>Name</th>
                      <th>Location</th>
                      <th>Action</th>
                    </tr>
                  </thead>
              <tbody>
                  
					<!-- loop over and print our records -->
					<c:forEach var="tempRecord" items="${records}">
						
						<!-- construct an "update" link with customer id -->
						<c:url var="updateLink" value="/tenant/edit/${tempRecord.tenantId}">
							
						</c:url>
					 
						<!-- construct an "delete" link with customer id -->
						<c:url var="deleteLink" value="/tenant/delete">
							<c:param name="Id" value="${tempRecord.tenantId}" />
						</c:url>
		
						<tr>
							<td><%=i%></td>
							<td>${tempRecord.name}</td>
							<td>${tempRecord.location}</td>
							<td>
								<!-- display the update link --> <a href="${updateLink}">Edit</a>
								| <a href="${deleteLink}"
								onclick="if (!(confirm('Are you sure you want to delete this record?'))) return false">Delete</a>
							</td>
						</tr>
						<% i++; %>
					</c:forEach>
				</tbody>
			   <!-- /.container-fluid -->
			</table>
		  </div>
		</div>
	</div>
</div>

      <!-- End of Main Content -->
    
<jsp:include page="../common/footer.jsp" />