<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/load-css.jsp"%>
<%@ include file="../common/load-ext.jsp"%>
<script type="text/javascript">

xiva.checkResponse = function(response,options) {
	var msg;
	if (response){
		if (typeof(response) == "string") 
		{
			try
			{
				msg = Ext.JSON.decode(response);
			}
			catch (e)
			{
				if (options.headers && options.headers.requestHtml=="1")
				{
					return;
				}
				else
				{
					var tmp={message:'不是有效的JSON数据格式',detailMessage:'返回数据为：\n'+response};
					return;
				}
			}
		}
		else if (typeof(response) == "object")
		{
			msg = response;
		}
		else 
		{
			return true;
		}
		try
		{
			msg = Ext.JSON.decode(response.responseText);
		}
		catch (e)
		{
			return;
		}
		if (msg.success == false) 
		{
			Ext.MessageBox.alert('提示', msg.exceptionMsg);
			return false;
		}
	}
	return true;
};

//Ext.Ajax请求全局设置
(function(){
	Ext.Ajax.on("requestcomplete", function(conn, response, option) {
		xiva.checkResponse(response, option);
	});
})();

</script>