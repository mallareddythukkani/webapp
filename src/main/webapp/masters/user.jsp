<jsp:include page="../common/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<% int i = 0; %>
<div class="row">
  <div class="col-lg-12">
    <div class="card">
      <div class="card-header">
      <h4 class="card-title mb-0">User List</h4> 
      <a href="${pageContext.request.contextPath}/user/add" class="btn btn-primary btn-sm" style="float:right;">ADD</a></div>
      <div class="card-body">
        <table class="table table-responsive-sm table-bordered table-striped table-sm">
                  <thead>
                    <tr>
                      <th>S.No</th>
                      <th>Role</th>
                      <th>Name</th>
                      <th>Username</th>
                      <th>Actions</th>
                    </tr>
                  </thead>
              <tbody>
                  
					<!-- loop over and print our records -->
					<c:forEach var="tempRecord" items="${records}">
						<tr>
							<td><%=++i %></td>
							<td>${tempRecord.role.name}</td>
							<td>${tempRecord.name}</td>
							<td>${tempRecord.username}</td>
							<td>
								<a class="btn btn-sm btn-info"  href="${pageContext.request.contextPath}/user/edit/${tempRecord.userId}"><i class="fa fa-pencil"></i></a>
								<c:if test="${tempRecord.status == 1 }"> 
									<form:form action="${pageContext.request.contextPath}/user/deactivate/${tempRecord.userId}" method="POST" style="display:inline-block;">
										<button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to Deactivate?')"><i class="fa fa-trash-o"></i></button>
									</form:form>
								</c:if>
								<c:if test="${tempRecord.status != 1 }"> 
									<form:form action="${pageContext.request.contextPath}/user/activate/${tempRecord.userId}" method="POST" style="display:inline-block;">
										<button type="submit" class="btn btn-sm btn-success" onclick="return confirm('Are you sure you want to Activate?')"><i class="fa fa-check"></i></button>
									</form:form>
								</c:if>
								<a class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/user/resetPassword/${tempRecord.userId}"><i class="fa fa-key"></i></a>
							</td>
						</tr>
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