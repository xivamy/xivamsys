Ext.onReady(function() {

	var tb = new Ext.Toolbar({
		region : 'north',
		style : {
			border : '0',
			background : '#DFE8F7'
		},
		border : false,
		items : [' ', '->', ' ', '-', ' ', {
					text : '帮助',
					iconCls : 'btnIconHelp',
					menu : {
						items : [{
									text : '关于...',
									handler : function() {
										function aboutclose() {
											aboutwin.close();
										}
										var aboutwin = new Ext.Window({
													title : '关于',
													width : 420,
													height : 280,
													modal : false,
													html : '制作中...',
													autoScroll : true,
													plain : true,
													resizable : false,

													buttons : [{
																text : '关闭',
																handler : aboutclose
															}]
												});
										aboutwin.show();
									}
								}]
					}
				}, ' ', '-', ' ', {
					xtype : 'combo',
					width : 100,
					value : '',
					editable : false,
					triggerAction : 'all',
					store : [['', '默认风格'], ['black', '黑色'], ['calista', '黄绿色'],
							['gray', '灰白Vista'], ['green', '青色'],
							['indigo', '湛蓝'], ['olive', '绿色'], ['pink', '粉色'],
							['purple', '紫色'], ['slate', '暗蓝灰色']],
					listeners : {
						select : function(cb) {
							var val = cb.getValue();
							var themecss = document.getElementById('css-theme');
							if (val != '')
								themecss.href = Ext.WebRoot
										+ "spark/resources/theme/css/xtheme-"
										+ val + ".css";
							else
								themecss.href = "";
						}
					}
				}, ' ', '-', ' ', {
					text : '更改密码',
					// icon:'icons/pwd.gif',
					iconCls : 'btnIconEdit',
					handler : function() {
						Ext.Msg.alert('提示', '暂无此功能...');
					}

			}	, ' ', '-', ' ', {
					text : '注销',
					iconCls : 'btnIconReject',
					handler : function() {
						Ext.Msg.confirm('注销', '您确定要注销登陆吗？', function(btn) {
									if (btn == 'yes') {
										Ext.Ajax.request({
													url : xiva.webContextRoot + '/base/logout.action'
												});
										window.location = xiva.webContextRoot + '/index.jsp';
									}
								});
					}
				}]
	});
	var tabs = new Ext.TabPanel({
		region : "center",
		id : 'tabs',
		activeTab : 0,
		border : false,
		minTabWidth : 80,
		tabWidth : 80,
		height : 500,
		enableTabScroll : true,
		defaults : {
			autoScroll : true
		},
		items : [{
			title : '代办事宜',
			id : 'todoList',
			iconCls : 'home-icon',
			html : " <iframe scrolling='no' frameborder='0' marginwidth='0' marginheight='0' id='ge'  width=100% height=100% src='../img/sys/Penguins.jpg' > </iframe>",
			closeable : false
		},{
			title : '已办事宜',
			id : 'doneList',
			iconCls : 'home-icon',
			html : " <iframe scrolling='no' frameborder='0' marginwidth='0' marginheight='0' id='ge'  width=100% height=100% src='../img/sys/peacock.jpg' > </iframe>",
			closeable : false
		},{
			title : '历史事宜',
			id : 'historyList',
			iconCls : 'home-icon',
			html : " <iframe scrolling='no' frameborder='0' marginwidth='0' marginheight='0' id='ge'  width=100% height=100% src='../img/sys/Penguins.jpg' > </iframe>",
			closeable : false
		},{
			title : '消息管理',
			id : 'msgMgr',
			iconCls : 'home-icon',
			html : " <iframe scrolling='no' frameborder='0' marginwidth='0' marginheight='0' id='ge'  width=100% height=100% src='../img/sys/Penguins.jpg' > </iframe>",
			closeable : false
		}]
	});
	
/*	Ext.ux.IFrameComponent = Ext.extend(Ext.Component, {
		onRender : function(ct, position) {
			var url = this.url;
			if (this.params)
				url += (url.indexOf('?') != -1 ? '&' : '?')
						+ Ext.urlEncode(this.params);
			url += (url.indexOf('?') != -1 ? '&' : '?') + '_dc='
					+ (new Date().getTime());
			this.el = ct.createChild({
						tag : 'iframe',
						id : 'iframe-' + this.id,
						frameBorder : 0,
						src : url
					});
		}
	});*/
	
	Ext.define('ctreemodel', {
	    extend: 'Ext.data.Model',
	    fields: [
	        {name: 'id',  type: 'string'},
	        {name: 'url',  type: 'string'},
	        {name: 'text',  type: 'string'}
	    ]
	});
	
	var userManageTree = Ext.create('Ext.tree.Panel',{ 
	    minWidth: 135,
	    maxWidth: 200,
	    border : false,
	    rootVisible: false,
	    store : Ext.create('Ext.data.TreeStore', {
	    	model: 'ctreemodel',
			root : {
	        	rootVisible : false,
				expanded : true,
				children : [ {
					id : '1',
					text : "用户列表",
					url:xiva.webContextRoot+'/iv_service/maintainuser.jsp',
					leaf : true
				},{
					id : '2',
					text : "基准组织管理",
					leaf : false,
					expanded : false,
					children :[{
						id : '6',
						text : "基准组织单元维护",
						url : xiva.webContextRoot+'/iv_service/maintainorg.jsp',
						leaf : true
					},{
						id : '7',
						text : "岗位维护",
						url : xiva.webContextRoot+'/iv_service/maintainuser.jsp',
						leaf : true
					}]
				},{
					id : '3',
					text : "角色管理",
					url : xiva.webContextRoot+'/iv_service/failure.jsp',
					leaf : true
				},{
					id : '4',
					text : "IP管理",
					url : xiva.webContextRoot+'/iv_service/failure.jsp',
					leaf : true
				},{
					id : '5',
					text : "登陆日志管理",
					url : xiva.webContextRoot+'/iv_service/maintainlog.jsp',
					leaf : true
				} ]
			}
	    }),
	    listeners : {
			'itemclick' : function(view,re){
				
				if (re.data.leaf != true) {
					return;
				}
				var tabId = 'tab-' + re.data.id;
				if (Ext.getCmp(tabId)) {
					tabs.setActiveTab(tabId);
					return
				}
				var iframePanel = new Ext.Panel({
							id : tabId,
							title : re.data.text,
							layout : 'fit',
							items : [{
							        	 html : "<iframe scrolling='no' frameborder='0' marginwidth='0' marginheight='0' id='ge'  width=100% height=100% src='"+re.data.url +"' > <div id='demo'></div></iframe>"
							         }],
							closable : true
						});
				tabs.add(iframePanel).show();
			}
		}
	});

	
	var authorityTree = Ext.create('Ext.tree.Panel',{ 
	    minWidth: 200,
	    maxWidth: 320,
	    border : false,
	    rootVisible: false,
	    store : Ext.create('Ext.data.TreeStore', {
	    	model: 'ctreemodel',
			root : {
	        	rootVisible : false,
				expanded : true,
				children : [ {
					id : '8',
					text : "系统资源管理",
					url:xiva.webContextRoot+'/iv_service/mantainRes/jsp/maintainResr.jsp',
					leaf : true
				},{
					id : '9',
					text : "角色管理",
					url:xiva.webContextRoot+'/iv_service/mantainRole/jsp/maintainRole.jsp',
					leaf : true
				}]
			}
	    }),
	    listeners : {
			'itemclick' : function(view,re){
				if (re.data.leaf != true) {
					return;
				}
				var tabId = 'tab-' + re.data.id;
				if (Ext.getCmp(tabId)) {
					tabs.setActiveTab(tabId);
					return
				}
				var iframePanel = new Ext.Panel({
							id : tabId,
							title : re.data.text,
							layout : 'fit',
							items : [{
							        	 html : "<iframe scrolling='no' frameborder='0' marginwidth='0' marginheight='0' id='ge'  width=100% height=100% src='"+re.data.url +"' > <div id='demo'></div></iframe>"
							         }],
							closable : true
						});
				tabs.add(iframePanel).show();
			}
		}
	});
	

	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [tb, {
							region : 'west',
							id : 'west-panel',
							title : '功能列表',
							split : true,
							width : 280,
							minSize : 175,
							maxSize : 400,
							collapsible : true,
							margins : '0 0 0 5',
							layout : 'accordion',
							layoutConfig : {
								animate : true
							},
							items : [{
										title : '内容管理',
										border : false,
										iconCls : 'settings'
									},{
										title : '用户组织管理',
										border : false,
										iconCls : 'nav',
										items : [userManageTree]
									},{
										title : '权限管理',
										border : false,
										iconCls : 'settings',
										items : [authorityTree]
									},{
										title : '系统参数',
										border : false,
										iconCls : 'settings'
									}]
						}, tabs]
			});

});