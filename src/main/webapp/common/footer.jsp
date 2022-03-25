<% 
	pageContext.setAttribute("templateUrl", request.getContextPath()+"/resources/static/template2");
	pageContext.setAttribute("currentYear", java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));
%>
      <!-- <footer class="c-footer">
          <div><a href="https://coreui.io">CoreUI</a> &copy; 2020 creativeLabs.</div>
          <div class="ml-auto">Powered by&nbsp;<a href="https://coreui.io/">CoreUI</a></div>
        </footer> -->
      </div>
    </div>
    <!-- CoreUI and necessary plugins-->
    <script src="${templateUrl}/js/coreui.bundle.min.js"></script>
    <script src="${templateUrl}/js/jquery-min.js"></script>
  </body>
</html>