<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<title>FusionCharts XT- Server-side Export using Download action
</title>

<link href="../../assets/ui/css/style.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" language="Javascript"
	src="../../../Charts/FusionCharts.js"></script>
<script type="text/javascript" language="JavaScript"
	src="../../../Charts/FusionChartsExportComponent.js"></script>
<script type="text/javascript" language="Javascript"
	src="../../assets/ui/js/jquery.min.js"></script>
<script type="text/javascript" language="Javascript"
	src="../../assets/ui/js/lib.js"></script>
<!--[if IE 6]>
        <script src="../../assets/ui/js/DD_belatedPNG_0.0.8a-min.js"></script>
        <script>
          /* select the element name, css selector, background etc */
          DD_belatedPNG.fix('img');

          /* string argument can be any CSS selector */
        </script>
        <![endif]-->


<style type="text/css">
h2.headline {
	font: normal 110%/ 137.5% "Trebuchet MS", Arial, Helvetica, sans-serif;
	padding: 0;
	margin: 25px 0 25px 0;
	color: #7d7c8b;
	text-align: center;
}

p.small {
	font: normal 68.75%/ 150% Verdana, Geneva, sans-serif;
	color: #919191;
	padding: 0;
	margin: 0 auto;
	width: 664px;
	text-align: center;
}
</style>

</head>
<body>
<!-- wrapper -->
<div id="wrapper"><!-- header -->
<div id="header">
<div class="logo"><a class="imagelink"
	href="http://www.fusioncharts.com/" target="_blank"> <img
	src="../../assets/ui/images/fusionchartsv3.2-logo.png" width="131"
	height="75" alt="FusionCharts XT logo" /> </a></div>
<h1 class="brand-name">FusionCharts XT</h1>
<h1 class="logo-text">Server-side Export using Download action</h1>

</div>
<!-- content area -->
<div class="content-area">
<div id="content-area-inner-main">
<p class="text" align="center">Server-side Export using Download
action</p>
<div id="messageBox"
	style="margin-left: 100px; margin-right: 100px; display: none;"></div>
<p>&nbsp;</p>


<div class="clear"></div>
<div class="gen-chart-render">

<div id="chartContainer">The chart will appear within this DIV.
This text will be replaced by the chart.</div>
<script type="text/javascript">
							var serverSideExportDownload_data ='<chart yAxisName="Sales Figure" caption="Top 5 Sales Person" numberPrefix="$" useRoundEdges="1" bgColor="FFFFFF,FFFFFF" showBorder="0" exportEnabled="1" exportAtClient="0" exportAction="download" exportHandler="http://www.fusioncharts.com/ExportHandlers/PHP/FCExporter.php" exportFileName="MyFileName">'
								 + '<set label="Alex" value="25000"  />'
								 + '<set label="Mark" value="35000" />'
								 + '<set label="David" value="42300" />'
								 + '<set label="Graham" value="35300" />'
								 + '<set label="John" value="31300" />'
								 + '</chart>';

                            //Create the chart.
                            var myChart = new FusionCharts("../../../Charts/Column2D.swf", "myChartId", "600", "300", "0", "1");
                            myChart.setXMLData( serverSideExportDownload_data );
                            myChart.render("chartContainer");
                        </script></div>

<div class="clear"></div>

<p>&nbsp;</p>
<p class="small">&nbsp;</p>
<p>&nbsp;</p>
<div class="underline-dull"></div>
<div>

<p class="highlightBlock">The above sample showcases how to initiate
Server side export using JavaScript. Click <a
	href="../../../Contents/index.html?exporting-image/server-side/ECServerDownload.html"
	target="_blank">here</a> to know more on how the code of this example
works.</p>
</div>
</div>


</div>

<!-- footer -->
<div id="footer">
<ul>
	<li><a href="../index.html"><span>&laquo; Back to list
	of examples</span></a></li>
	<li class="pipe">|</li>
	<li><a href="../../NoChart.html"><span>Unable to see
	the chart above?</span></a></li>
</ul>
</div>
</div>
<script type="text/javascript"><!--//
			$(document).ready ( function() {
			   showConditionalMessage( "Your browser does not seem to have Flash Player support. JavaScript charts are rendered instead.", isJSRenderer(myChart) );
			});	
		// -->
		</script>
</body>

</html>
