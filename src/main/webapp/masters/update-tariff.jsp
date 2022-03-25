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
      <h4 class="card-title mb-0">Update ${vehicleTypeName } Tariff Details</h4> 
      </div>
      <div class="card-body">
      	
      	<div class="row">
      	<div class="col-md-12">
      		<button type="button" id="add" class="btn btn-primary btn-sm" style="float:right;margin-bottom:5px;">Add Row</button>
      		<form:form action="${pageContext.request.contextPath}/submitTariff">
      		<input type="hidden" name="vehicleTypeId" value="${vehicleTypeId }">
      		<table id="tariffTable" style="width:100%" class="table table-responsive-sm table-bordered table-striped table-sm">
              <thead>
                <tr>
                  <th>Sl.No</th>
                  <th>From Time</th>
                  <th>To Time</th>
                  <th>Tariff (Rs/-)</th>
                  <th>Repeat Min</th>
                  <th>Repeat Tariff</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                  <!-- loop over and print our records -->
					<c:forEach var="tempRecord" items="${pt_list}">
						<%i++; %>
						<tr>
							<td><%=i %></td>
							<td>
								<input type="number" class="from_time" name="fromTime[]" value="${tempRecord.fromTime}">
							</td>
							<td>
								<input type="number" class="to_time" name="toTime[]" value="${tempRecord.toTime}">
							</td>
							<td>
								<input type="number" name="tariff[]" value="${tempRecord.tariff}">
							</td>
							<td>
								<input type="number" name="repeatMin[]" value="${tempRecord.repeatMinutes}">
							</td>
							<td>
								<input type="number" class="repeat_cost" name="repeatTariff[]" value="${tempRecord.repeatTariff}">
							</td>
							<td>
								<button class="btn btn-danger removerow">X</button>
							</td>
						</tr>
					</c:forEach>
					<%
					if(i==0){
					%>
						<tr>
							<td>1</td>
							<td>
								<input type="number" class="from_time" name="fromTime[]" value="0">
							</td>
							<td>
								<input type="number" class="to_time" name="toTime[]" value="">
							</td>
							<td>
								<input type="number" name="tariff[]" value="">
							</td>
							<td>
								<input type="number" name="repeatMin[]" value="">
							</td>
							<td>
								<input type="number" class="repeat_cost" name="repeatTariff[]" value="">
							</td>
							<td>
								<button class="btn btn-danger removerow">X</button>
							</td>
						</tr>
					<%
					}
					%>	
				</tbody>
			   <!-- /.container-fluid -->
			</table>
			<p><span style="width:120px; display:inline-block">LOT Amount</span> : <input type="number" name="lot" value="${lot}"></p>
			<p><span style="width:120px; display:inline-block">Overnight Amount</span> : <input type="number" name="overnt" value="${overnt }"></p>
			<p align="center"><button type="submit" id="btn_submit" class="btn btn-success">Submit</button></p>
			</form:form>
      	</div>
		</div>
    </div>
  </div>
  <!-- /.col-->
</div>  
      <!-- End of Main Content -->
    
<jsp:include page="../common/footer.jsp" />
<script type="text/javascript">
var click_count = 0;
$('#add, #btn_submit').click(function()
{
    click_count++;
    var this_id = $(this).attr('id');
    var ele = $('#tariffTable').find('tbody tr:last'); 
    var sl_no = ele.find('td:first').html();
    var from_time = ele.find('td:eq(1) input');
    var to_time = ele.find('td:eq(2) input');
    var cost = ele.find('td:eq(3) input');
    var repeat_minute = ele.find('td:eq(4) input');
    var repeat_cost = ele.find('td:eq(5) input');
    var ele_array = [from_time, to_time, cost, repeat_minute, repeat_cost];
    
    $.each(ele_array, function( index, value ) 
    {
        value.next('span').remove();   
        if(index<3)
        {
            if(value.val()=="")
            {
                value.after('<span class="span-error" style="color: red;"><br>Value is required</span>');
            }
        }
    });

    if(parseInt(from_time.val()) >= parseInt(to_time.val()))
    {
        to_time.after('<span class="span-error" style="color: red;"><br>To time can not be less than from time.</span>');
    }

    if(repeat_minute.val() || repeat_cost.val())
    {
        if(repeat_minute.val())
        {
            if(repeat_cost.val()=="")
            {
                repeat_cost.after('<span class="span-error" style="color: red;"><br>Please enter repeat cost.</span>');
            }
        }

        if(repeat_cost.val())
        {
            if(repeat_minute.val()=="")
            {
                repeat_minute.after('<span class="span-error" style="color: red;"><br>Please enter repeat minute.</span>');
            }
        }
    }

    if(click_count<=2)
    {
        ele.find('.span-error').remove();
        //from_time.val(0);
        //from_time.attr('readonly', true);
        return false
    }

    if(repeat_minute.val() && repeat_cost.val())
    {
        if(this_id=='add')
        {
            return false;
        }
        
    }
    if(repeat_minute.val()=='' && repeat_cost.val()== '')
    {
        if(this_id=='btn_submit')
        {
            return false;
        }
    }
    if($('.span-error').length){return false;}
   
    if(this_id=='add')
    {
        var ele_clone = ele.clone();
        //ele.find('input').attr('readonly',true);
        ele_clone.find('input').val('');
        ele_clone.find('td:first').html(parseInt(sl_no)+1);
        ele_clone.find('td:eq(1) input').val(parseInt(to_time.val()));
        //ele_clone.find('td:eq(1) input').attr('readonly', true);
        ele_clone.find(".removerow").show();
        ele_clone.find(".removerow").click(function () 
        {        
            var row = $(this).closest('tr');
            row.nextAll('tr').remove();
            row.prev('tr').find('td:not(:eq(1)) input').attr('readonly', false);
            row.remove();
        });
        ele.after(ele_clone);
    }

}).trigger('click'); 
$(".removerow").click(function(){

    var row = $(this).closest('tr');
    row.nextAll('tr').remove();
    row.prev('tr').find('td:not(:eq(1)) input').attr('readonly', false);
    row.remove();
    $('#add').show();
   
});
$('.removerow:first').hide();
</script>