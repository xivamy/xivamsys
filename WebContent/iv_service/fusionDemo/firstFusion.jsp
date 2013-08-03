<html>
<head>
<script language="JavaScript"
	src="<%=request.getContextPath()%>/jslib/FusionCharts/FusionCharts.js"></script>
<script language="JavaScript"
	src="<%=request.getContextPath()%>/jslib/FusionCharts/FusionChartsExportComponent.js"></script>
</head>
<body bgcolor="#ffffff">
<div id="chartdiv" align="center">The chart will appear within
this DIV. This text will be replaced by the chart.</div>
<script type="text/javascript">
	var myChart = new FusionCharts("<%=request.getContextPath()%>/jslib/FusionCharts/Column2D.swf",
			"myChartId", "500", "300", "0", "1");
	myChart.setXMLUrl("Data.xml");
	myChart.render("chartdiv");
</script>
<!-- We also create a DIV to contain the FusionCharts client-side exporter component -->
<div id="fcexpDiv" align="center">FusionCharts Export Handler
Component</div>
<script type="text/javascript">
	var myExportComponent = new FusionChartsExportObject("fcExporter1",
			"<%=request.getContextPath()%>/jslib/FusionCharts/FCExporter.swf"); 
			myExportComponent.Render("fcexpDiv");
			function myFN(objRtn){
                if (objRtn.statusCode=="1"){
                    alert("The chart was successfully saved. Its DOM Id is " + objRtn.DOMId);
                } else{
                    alert("There was an error saving the chart. Error message: " + objRtn.statusMessage + ". Its DOM Id is " + objRtn.DOMId);
                }
            }
</script>
</body>
</html>



