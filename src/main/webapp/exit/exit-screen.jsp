<jsp:include page="../common/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<style type="text/css">
	.no_opacity{opacity:0;}
</style>
<main class="c-main">
          <div class="container-fluid">
            <div class="fade-in">
              <div class="card">
                <div class="card-body">
                  <div class="d-flex justify-content-between">
                    <div>
                      <h4 class="card-title mb-0">Exit Screen</h4>
                      <div class="small text-muted" id="toggleScanInput">show</div>
                    </div>
                     </div>
                  <div class="c-chart-wrapper" style="min-height:300px;margin-top:40px;">
                    <div>
                    	<form:form id="exitForm" method="POST" action="${pageContext.request.contextPath}/exit/exitFormSubmit">
                    		<div class="row">
                    			<div class="col-md-3"></div>
                    			<div class="col-md-4">
                    				<div class="form-group">
			                          <h3 align="center" style="background:#EBEDEF">SCAN QR CODE</h3>
			                          <div class="input-group no_opacity" id="scanInputBlock">
			                              <input class="form-control" name="trans_id" id="trans_id" type="text" autofocus>
			                          </div>
			                        </div>
			                        <div class="form-group">
			                        <h4 align="center">OR</h4>
			                          <label class="col-form-label" for="appendedInput">Enter Serial Number</label>
			                          <div class="input-group">
			                              <input class="form-control" name="serial_num" id="serial_num" type="text">
			                          </div>
			                        </div>
			                        <div class="form-actions">
			                          <button class="btn btn-primary" type="submit">Submit</button>
			                          <button class="btn btn-danger" type="button">Reset</button>
			                        </div>
                    			</div>
                    		</div>
                    	</form:form>
                    </div>
                 
                  </div>
                </div>
              </div>
              <!-- /.card-->
              
            </div>
          </div>
        </main>
           
<jsp:include page="../common/footer.jsp" />

<script type="text/javascript">
	$(document).on('input','#trans_id',function(){
		var val = $(this).val();
		//alert("value:"+val);
		$('#exitForm').submit();
	});
	$("#toggleScanInput").click(function(){
		$("#scanInputBlock").toggleClass("no_opacity");
	});
</script>