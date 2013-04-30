Ext.namespace("Com.xiva.login");

Com.xiva.login.form = {
		
	initForm : function()
	{
		Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
		// 使用表单提示
		Ext.QuickTips.init();
		
		Ext.form.Field.prototype.msgTarget = 'side';
		
		var loginForm = Ext.create('Ext.form.Panel', {
			
			labelWidth : 55,
			monitorValid : true,
			baseCls : 'x-plain',
			style : {
				align : 'center'
			},
			defaults : {
				width : 250
			},
			defaultType : 'textfield',
			// 定义表单元素
			items : [ {
				fieldLabel : '帐户',
				name : 'username',
				allowBlank : false,
				blankText : '帐户不能为空!',
				value: 'admin',
				style : {
					align : 'center'
				}
			}, {
				inputType : 'password',
				fieldLabel : '密码',
				name : 'pwd',
				allowBlank : false,
				blankText : '密码不能为空!',
				value : '123456',
				style : {
					align : 'center'
				}
			} ],
			buttons : [ {
				text : '登陆',
				formBind : true,
				type : 'submit',
				handler : function() {
					Ext.MessageBox.show( {
						title : '请稍等',
						msg : '正在登陆...',
						progressText : '',
						width : 300,
						progress : true,
						closable : false,
						animEl : 'loading'
					});
	
					var f = function(v)
					{
						return function() {
							var i = v / 11;
							Ext.MessageBox.updateProgress(i, '');
						};
					};
					
					for ( var i = 1; i < 12; i++)
					{
						setTimeout(f(i), i * 150);
					}
					loginForm.form.doAction('submit', {
						url : xiva.webContextRoot+'/user/login.action',
						method : 'post',
						params:{userName:loginForm.form.getValues().username,password:loginForm.form.getValues().pwd},
						success : function(form, action) {
							if (action.result.success == true) {
								window.location = xiva.webContextRoot+'/iv_service/main.jsp';
							} else {
								Ext.Msg.alert('登陆失败', action.result.msg);
							}
						},
						failure : function(form, action) {
							Ext.Msg.alert('错误', '服务器出现错误请稍后再试！' + action.result);
						}
					});
				}
			}, {
				text : '取消',
				handler : function() {
					loginForm.form.reset();
				}
			} ]
		});
		
		return loginForm;
	}
};

Com.xiva.login.win = {
	initWin :function()
	{
		// 定义窗体
		var win = new Ext.Window( {
			id : 'loginWin',
			title : '用户登陆(user:admin,pswd:123456)',
			layout : 'fit',
			width : 500,
			height : 350,
			modal : true,
			plain : true,
			bodyStyle : 'padding:5px;',
			maximizable : false,
			closeAction : 'close',
			closable : false,
			collapsible : true,
			plain : true,
			buttonAlign : 'center',
			items : Com.xiva.login.form.initForm()
		});
		
		return win;
	}
};

Ext.onReady( function() {
	Com.xiva.login.win.initWin().show();
});
