<%@page import="com.parkingservice.webapp.model.ParkingTransaction"%>
<%@page import="com.parkingservice.webapp.model.TransactionData"%>
<%@page import="com.parkingservice.webapp.util.Helper"%>
<jsp:include page="../common/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<main class="c-main">
          <div class="container-fluid">
            <div class="fade-in">
              <div class="card">
                <div class="card-body">
                  <div class="d-flex justify-content-between">
                    <div>
                      <h4 class="card-title mb-0">Payment Calculation</h4>
                    </div>
                     </div>
                  <div class="c-chart-wrapper" style="min-height:300px;margin-top:40px;">
                    <div>
                   		<%
            				ParkingTransaction pt = (ParkingTransaction) request.getAttribute("pt_row");
            				TransactionData td = pt.getTransactionData();
            				int parkingTariff = (Integer) request.getAttribute("amount");
            				String exit_time = (String) request.getAttribute("exit_time");
            				int daysBetween = Helper.getDaysBetween(pt.getEntryTime(), exit_time);
            				int overnightCharge = (Integer) request.getAttribute("overnight_charge");
            				boolean overnightVehicle = (daysBetween>=1)?true:false;
            				%>
                    	<form:form id="exitForm" method="POST" 
                    	action="${pageContext.request.contextPath}/exit/makePayment">
                    		<input name="transaction_id" value="<%=pt.getTransactionId()%>" type="hidden">
                    		<input name="exit_time" value="<%=exit_time %>" type="hidden">
                    		<div class="row">
                    			<div class="col-md-5">
                    				
	                    			<table>
										<tr><td>Transaction ID</td><td>:</td><td><%=pt.getTransactionId()%></td></tr>
										<tr><td>Transaction Type</td><td>:</td><td>${trans_type }</td></tr>
										<tr><td>Vehicle Type</td><td>:</td><td><%=pt.getVehicleType().getName()%></td></tr>
										<tr><td>Entry Time</td><td>:</td><td><%=pt.getEntryTime()%></td></tr>
									    <tr><td>Exit Time</td><td>:</td><td><%=exit_time%> (<%=request.getAttribute("duration") %>)</td></tr>
									    <%if(overnightVehicle){ 
									    	parkingTariff = daysBetween*overnightCharge;
									    	String overnight_amt = "("+daysBetween+"*"+overnightCharge+") "+parkingTariff;
									    	%>
									    <tr><th>Overnight Vehicle</th><td>:</td><td><%=overnight_amt %></td></tr>
									    <%}
									    else{
									    %>
									    <tr><td>Amount</td><td>:</td><td>${amount } Rs/-</td></tr>
									    <%} %>
									</table>
									<input class="amount" id="amount" name="amount" value="<%=parkingTariff %>" type="hidden">
								</div>
								<div class="col-md-7">
									<div class="payment_options">
										<label class="inline-radio">
											<input type="radio" class="exempt_payment" name="exempt_payment" value="0" tabindex="1" checked autofocus> Payment
										</label>
										<label class="inline-radio">
											<input type="radio" class="exempt_payment" name="exempt_payment" exmp_val="1" value="1" tabindex="1"> Movie
										</label>
										<label class="inline-radio">
											<input type="radio" class="exempt_payment" name="exempt_payment" exmp_val="2" value="1" tabindex="1"> Shopping
										</label>
										<label class="inline-radio">
											<input type="radio" class="exempt_payment" name="exempt_payment" exmp_val="3" value="1" tabindex="1"> Hotel
										</label>
										<label class="inline-radio">
											<input type="radio" class="exempt_payment" name="exempt_payment" exmp_val="4" value="1" tabindex="1"> Staff
										</label>
										<label class="inline-radio">
											<input type="radio" class="exempt_payment" name="exempt_payment" exmp_val="5" value="1" tabindex="1"> Others
										</label>
										<input type="hidden" name="exempt_reason" id="exempt_reason">
									</div>
									<div id="makePayment">
										<p>Payment Method:</p>
										<div class="payment_options">
											<label class="inline-radio">
												<input type="radio" class="payment_method" name="payment_method" value="1" checked tabindex="2"> Cash
											</label>
											<label class="inline-radio">
												<input type="radio" class="payment_method" name="payment_method" value="2" tabindex="2"> UPI/Wallet
											</label>
										</div>
										<p id="remarks2" class="remarks_block" hidden>Transaction No: <input type="text" id="transaction_num" name="transaction_num"  tabindex="6"></p>
									</div>
									<div id="exemptBlock" hidden>
										<div class="tenant_block" hidden>
											<p>
												<span>Select Shop</span>
												<select name="tenant" id="tenant" tabindex="8">
													<option value="">Select Shop</option>
													<!-- loop over and print our records -->
													<c:forEach var="tenant" items="${tenant_records}">
														<option value="${tenant.tenantId }">${tenant.name}</option>
													</c:forEach>
												</select>
											</p>
											<p>
												<span>Bill Number</span>
												<input type="text" name="bill_number" tabindex="8">
											</p>
											<p>
												<span>Bill Amount</span>
												<input type="number" name="bill_amount" tabindex="8">
											</p>
										</div>
									</div>
									<div id="submitBlock">
										<p>Remarks:<br>
										<textarea name="remarks" rows="1" cols="50" tabindex="10"></textarea>
										</p>
										<button id="submitPayment" class="btn btn-success" tabindex="10" <%if(parkingTariff==0){out.print("autofocus");} %>>Submit</button>
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
	$(".exempt_payment").change(function(){
		var val = $(this).val();
		//alert(val);
		var exmp_val = $(this).attr("exmp_val");
		if(val=="1"){
			$("#makePayment").attr("hidden","");
			$("#exemptBlock").removeAttr("hidden");
			$("#exempt_reason").val(exmp_val);
			if(exmp_val=="2")
				$(".tenant_block").removeAttr("hidden");
			else
				$(".tenant_block").attr("hidden","");
				
		}
		else{
			$("#exemptBlock").attr("hidden","");
			$("#makePayment").removeAttr("hidden");
		}
	});
	
	$(".payment_method").change(function(){
		//alert("hi");
		var payment_method = $(this).val();
		$(".remarks_block").attr("hidden","");
		$("#remarks"+payment_method).removeAttr("hidden");
	});
	
	$("#submitPayment").click(function(e){
		e.preventDefault();
		var exempt_payment = $(".exempt_payment:checked").val();
		//alert(exempt_payment);
		if(exempt_payment=="1"){
			var exempt_reason = $("#exempt_reason").val();
			if(exempt_reason=="2"){
				var tenant = $("#tenant").val();
				if(tenant==""){
					alert("Please Select Shop");
					return false;
				}
				
				
			}
		}
		else{
			var payment_method = $(".payment_method:checked").val();
			//alert("pm:"+payment_method);
			switch(payment_method){
				case "1": //cash
					$("#transaction_num").val("");
					break;
				case "2": //UPI/WALLET
					var transaction_num = $("#transaction_num").val();
					if(transaction_num==""){
						alert("Enter transaction number");
						return false;
					}
					break;
			}
		}
		$("#exitForm").submit();
		
	});
	
</script>