<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>

<div class="d-sm-flex align-items-center justify-content-between mb-4">
	<h1 class="h3 mb-0 text-gray-800">Dashboard</h1>
</div>
<div class="row">
	<div class="col-xl-3 col-md-6 mb-4">
		<div class="card border-left-primary shadow h-100 py-2">
			<div class="card-body">
				<div class="row no-gutters align-items-center">
					<div class="col mr-2">
						<div class="text-xs font-weight-bold text-primary text-uppercase mb-1">주기적인 데몬</div>
						<div class="h5 mb-0 font-weight-bold text-gray-800"><c:out value="${intervalCount }"/></div>
					</div>
					<div class="col-auto">
	          			<i class="fas fa-tasks fa-2x text-gray-300"></i>
	        		</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- Earnings (Monthly) Card Example -->
    <div class="col-xl-3 col-md-6 mb-4">
    	<div class="card border-left-success shadow h-100 py-2">
        	<div class="card-body">
          		<div class="row no-gutters align-items-center">
            		<div class="col mr-2">
              			<div class="text-xs font-weight-bold text-success text-uppercase mb-1">하루에 한번 데몬</div>
              			<div class="h5 mb-0 font-weight-bold text-gray-800"><c:out value="${everydayCount }"/></div>
            		</div>
            		<div class="col-auto">
              			<i class="fas fa-tasks fa-2x text-gray-300"></i>
            		</div>
          		</div>
        	</div>
      	</div>
    </div>
    
    <!-- Earnings (Monthly) Card Example -->
    <div class="col-xl-3 col-md-6 mb-4">
      	<div class="card border-left-info shadow h-100 py-2">
        	<div class="card-body">
          		<div class="row no-gutters align-items-center">
	            	<div class="col mr-2">
              			<div class="text-xs font-weight-bold text-info text-uppercase mb-1">데몬 성공 (<c:out value="${mm }"/>월)</div>
              			<div class="row no-gutters align-items-center">
                			<div class="col-auto">
                  				<div class="h5 mb-0 mr-3 font-weight-bold text-gray-800"><c:out value="${monthMap.succ }"/>%</div>
                			</div>
                			<div class="col">
                  				<div class="progress progress-sm mr-2">
                    				<div class="progress-bar bg-info" role="progressbar" style="width: <c:out value="${monthMap.succ }"/>%" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
                  				</div>
                			</div>
              			</div>
            		</div>
            		<div class="col-auto">
              			<i class="fas fa-clipboard-list fa-2x text-gray-300"></i>
            		</div>
          		</div>
        	</div>
      	</div>
    </div>

    <!-- Pending Requests Card Example -->
    <div class="col-xl-3 col-md-6 mb-4">
      	<div class="card border-left-danger shadow h-100 py-2">
        	<div class="card-body">
          		<div class="row no-gutters align-items-center">
	            	<div class="col mr-2">
              			<div class="text-xs font-weight-bold text-danger text-uppercase mb-1">데몬 실패 (<c:out value="${mm }"/>월) </div>
              			<div class="row no-gutters align-items-center">
                			<div class="col-auto">
                  				<div class="h5 mb-0 mr-3 font-weight-bold text-gray-800"><c:out value="${monthMap.fail }"/>%</div>
                			</div>
                			<div class="col">
                  				<div class="progress progress-sm mr-2">
                    				<div class="progress-bar bg-danger" role="progressbar" style="width: <c:out value="${monthMap.fail }"/>%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
                  				</div>
                			</div>
              			</div>
            		</div>
            		<div class="col-auto">
              			<i class="fas fa-clipboard-list fa-2x text-gray-300"></i>
            		</div>
          		</div>
        	</div>
      	</div>
    </div>
</div>

<div class="row">
	<div class="col-xl-12 col-lg-7">
     	<div class="card shadow mb-4">
       	<!-- Card Header - Dropdown -->
       		<div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
         		<h6 class="m-0 font-weight-bold text-primary">6개월 성공률</h6>
         		<div class="dropdown no-arrow">
           <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
             <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
           </a>
         </div>
       </div>
       <!-- Card Body -->
       <div class="card-body">
         <div class="chart-area">
           <canvas id="myAreaChart"></canvas>
         </div>
       </div>
     </div>
   </div>
</div>


<!-- Page level plugins -->
<script src="<c:url value='/resources/vendor/chart.js/Chart.min.js'/>"></script>

<!-- Page level custom scripts -->
<%-- <script src="<c:url value='/resources/js/demo/chart-area-demo.js'/>"></script> --%>
<%-- <script src="<c:url value='/resources/js/demo/chart-pie-demo.js'/>"></script> --%>


<script>
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

function number_format(number, decimals, dec_point, thousands_sep) {
  // *     example: number_format(1234.56, 2, ',', ' ');
  // *     return: '1 234,56'
  number = (number + '').replace(',', '').replace(' ', '');
  var n = !isFinite(+number) ? 0 : +number,
    prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
    sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
    dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
    s = '',
    toFixedFix = function(n, prec) {
      var k = Math.pow(10, prec);
      return '' + Math.round(n * k) / k;
    };
  // Fix for IE parseFloat(0.55).toFixed(0) = 0;
  s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
  if (s[0].length > 3) {
    s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
  }
  if ((s[1] || '').length < prec) {
    s[1] = s[1] || '';
    s[1] += new Array(prec - s[1].length + 1).join('0');
  }
  return s.join(dec);
}

// Area Chart Example
var ctx = document.getElementById("myAreaChart");
var myLineChart = new Chart(ctx, {
  type: 'line',
  data: {
    labels: [
    	"<c:out value="${sixMonthMap.m6}"/>", "<c:out value="${sixMonthMap.m5}"/>", 
    	"<c:out value="${sixMonthMap.m4}"/>", "<c:out value="${sixMonthMap.m3}"/>", 
    	"<c:out value="${sixMonthMap.m2}"/>", "<c:out value="${sixMonthMap.m1}"/>"
    	],
    datasets: [{
      label: "Earnings",
      lineTension: 0.3,
      backgroundColor: "rgba(78, 115, 223, 0.05)",
      borderColor: "rgba(78, 115, 223, 1)",
      pointRadius: 3,
      pointBackgroundColor: "rgba(78, 115, 223, 1)",
      pointBorderColor: "rgba(78, 115, 223, 1)",
      pointHoverRadius: 3,
      pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
      pointHoverBorderColor: "rgba(78, 115, 223, 1)",
      pointHitRadius: 10,
      pointBorderWidth: 2,
      data: [
    	  <c:out value="${sixMonthMap.per6}"/>, <c:out value="${sixMonthMap.per5}"/>, 
    	  <c:out value="${sixMonthMap.per4}"/>, <c:out value="${sixMonthMap.per3}"/>, 
    	  <c:out value="${sixMonthMap.per2}"/>, <c:out value="${sixMonthMap.per1}"/>
    	  ],
    }],
  },
  options: {
    maintainAspectRatio: false,
    layout: {
      padding: {
        left: 10,
        right: 25,
        top: 25,
        bottom: 0
      }
    },
    scales: {
      xAxes: [{
        time: {
          unit: 'date'
        },
        gridLines: {
          display: false,
          drawBorder: false
        },
        ticks: {
          maxTicksLimit: 7
        }
      }],
      yAxes: [{
        ticks: {
          maxTicksLimit: 5,
          padding: 10,
          // Include a dollar sign in the ticks
          callback: function(value, index, values) {
            return '' + number_format(value);
          }
        },
        gridLines: {
          color: "rgb(234, 236, 244)",
          zeroLineColor: "rgb(234, 236, 244)",
          drawBorder: false,
          borderDash: [2],
          zeroLineBorderDash: [2]
        }
      }],
    },
    legend: {
      display: false
    },
    tooltips: {
      backgroundColor: "rgb(255,255,255)",
      bodyFontColor: "#858796",
      titleMarginBottom: 10,
      titleFontColor: '#6e707e',
      titleFontSize: 14,
      borderColor: '#dddfeb',
      borderWidth: 1,
      xPadding: 15,
      yPadding: 15,
      displayColors: false,
      intersect: false,
      mode: 'index',
      caretPadding: 10,
      callbacks: {
        label: function(tooltipItem, chart) {
          var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
          return datasetLabel + ': ' + number_format(tooltipItem.yLabel);
        }
      }
    }
  }
});

</script>