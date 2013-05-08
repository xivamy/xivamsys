<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>jQuery EasyUI</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jslib/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jslib/easyui/themes/icon.css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/jslib/easyui/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jslib/easyui/jquery.easyui.min.js"></script>
	<script>
		function reload(){
			$('#orgTree').combotree('reload');
		}
		function setValue(){
			$('#orgTree').combotree('setValue', 2);
		}
		function getValue(){
			var val = $('#orgTree').combotree('getValue');
			alert(val);
		}
		function disable(){
			$('#orgTree').combotree('disable');
		}
		function enable(){
			$('#orgTree').combotree('enable');
		}
	</script>
</head>
<body>
	<h1>ComboTree</h1>
	<div style="margin-bottom:10px;">
		<a href="#" onclick="reload()">reload</a>
		<a href="#" onclick="setValue()">setValue</a>
		<a href="#" onclick="getValue()">getValue</a>
		<a href="#" onclick="disable()">disable</a>
		<a href="#" onclick="enable()">enable</a>
	</div>
	<span>Select:</span>
	<select class="easyui-combotree" id="orgTree" name="org" url="http://127.0.0.1:8080/xivamsys/org/getAllOrgTree.action" multiple="true" cascadeCheck="false" style="width:200px;"></select>
	<select id="dynamicTree" style="width:200px ;"  multiple="true" ></select>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$('#orgTree').combotree('setValue', 1);
		});
		$( '#dynamicTree' ).combotree ({
			url:"http://127.0.0.1:8080/xivamsys/org/getEasyUITree.action",  
			onBeforeExpand:function(node) {
			      $('#dynamicTree').combotree("tree").tree("options").url = "http://127.0.0.1:8080/xivamsys/org/getEasyUITree.action?node=" + node.id;
			}
	    }); 
	</script>
</body>
</html>