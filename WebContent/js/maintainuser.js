Ext.require([
    'Ext.grid.*',
    'Ext.data.*'
]);

Ext.namespace("Com.xiva.user");

Com.xiva.user.selUserId = null;
Com.xiva.user.checkedOrgId = 1;
Com.xiva.user.userStore = null;
Com.xiva.user.userGrid = null;
Com.xiva.user.addUserWin = null;

Com.xiva.user.toolBar = {
	
	init : function(){
		
		var editUserForm = null;
		var formStatus = 0; // 1表示编辑，0表示新增
		
		var win = Com.xiva.user.addUserWin;
	
		Ext.regModel("xiva.org.orgTreeModel", { 
	        fields: ['id', 'text', 'other'] 
	    });
	
		/**
		 * type为0新增,为1表示编辑
		 */
	    function showUserWin(title, type) {
	    	var userGrid = Com.xiva.user.userGrid;
	    	
	    	formStatus = type;
	    	if (!win)
	    	{
	    		showUserForm();
	    	}
	    	
	    	var comPicker = editUserForm.getForm().findField('companyId').getPicker();
			comPicker.getRootNode().expand(true);
			comPicker.getRootNode().collapseChildren(true);
			
	        if (type == 1)
	    	{
	        	var selModel = userGrid.getSelectionModel();
	    		var selectData = selModel.getSelection();
	    		var selectCnt = selModel.getCount();
	    		if (selectCnt == 0)
	    		{
	    			Ext.Msg.alert('提示', '没有记录被选择.');
	    			return;
	    		}
	    		else if (selectCnt > 1)
	    		{
	    			Ext.Msg.alert('提示', '多条记录被选中，请选择单条记录.');
	    			return;
	    		}
	    		else
	    		{
	    			var userId = selectData[0].get('userId');
	    			Com.xiva.user.selUserId = userId;
	    			var userObj = null;
	    			editUserForm.getForm().findField('userName').disabled = true;
	    			
	    			var requestConfig = {
						url :xiva.webContextRoot+ '/user/getUserInfo.action',
						params : {userId : userId},//请求参数
						callback : function(options,success,response){
							userObj = Ext.JSON.decode(response.responseText);
							var userNameFiled = editUserForm.getForm().findField('userName');
							var emailFiled = editUserForm.getForm().findField('email');
							var nickNameFiled = editUserForm.getForm().findField('nickName');
							var companyIdFiled = editUserForm.getForm().findField('companyId');
							var userCodeFiled = editUserForm.getForm().findField('userCode');
							var statusFiled = editUserForm.getForm().findField('status');
							var phoneNumFiled = editUserForm.getForm().findField('phoneNum');
							var validDateFiled = editUserForm.getForm().findField('validDate');
							
				        	userNameFiled.setValue(userObj.userName);
				        	emailFiled.setValue(userObj.email);
				        	nickNameFiled.setValue(userObj.nickName);
				        	companyIdFiled.setValue(userObj.orgId);
				        	userCodeFiled.setValue(userObj.userCode);
				        	statusFiled.setValue(userObj.status);
				        	phoneNumFiled.setValue(userObj.phoneNum);
				        	validDateFiled.setValue(userObj.validateTime);
						}
					};
	    			
					Ext.Ajax.request(requestConfig);//发送请求
	    		}
	    	}
	        else
	        {
	        	editUserForm.getForm().findField('userName').disabled = false;
	        }
	        
	        win = Ext.widget('window', {
	            title: title,
	            closeAction: 'hide',
	            width: 350,
	            height: 300,
	            layout: 'fit',
	            resizable: true,
	            modal: true,
	            items: editUserForm
	        });
	    	win.setTitle(title);
	        win.show();
	    };
	    
	    Ext.define('com.xiva.userStatus.comboxDataModel',{
	        extend: 'Ext.data.Model',
	        fields: [
	            //第一个字段需要指定mapping，其他字段，可以省略掉。
	            {name:'statuskey', mapping:'statuskey'},
	             'statusdes'
	        ]
	    });
		
		var userStatusComStore = Ext.create('Ext.data.Store', {
			
	        model: 'com.xiva.userStatus.comboxDataModel',
	        proxy: {
	           //异步获取数据，这里的URL可以改为任何动态页面，只要返回JSON数据即可
	            type: 'ajax',
	            url: xiva.webContextRoot+'/base/loadDictCode.action',
	            reader: {
	                type: 'json'
	            }
	        },
	        autoLoad: false
	    });
		
		userStatusComStore.load({
	        params:{
				statuskey : 'user_status'
	        }
	    });
		
	    function showUserForm ()
	    {
	    	editUserForm = Ext.widget('form', {
	            layout: {
	                type: 'vbox',
	                align: 'stretch'
	            },
	            border: false,
	            bodyPadding: 10,

	            fieldDefaults: {
	                labelAlign: 'left',
	                labelWidth: 80,
	                labelStyle: 'font-weight:bold'
	            },
	            items: [{
	                xtype: 'textfield',
	                name : 'userName',
	                id : 'userName',
	                fieldLabel: '用户名',
	                afterLabelTextTpl: required,
	                allowBlank: false
	            }, {
	                xtype: 'textfield',
	                name : "nickName",
	                id : 'nickName',
	                fieldLabel: '姓名',
	                afterLabelTextTpl: required,
	                allowBlank: false
	            }, {
	                xtype: 'treepicker',
	                name : "companyId",
	                id : "companyId",
	                store : orgTreeComboStore,
	                displayField : 'text',
	                fieldLabel: '组织机构',
	                afterLabelTextTpl: required,
	                allowBlank: false
	            }, {
	                xtype: 'textfield',
	                name : "userCode",
	                id : 'userCode',
	                fieldLabel: '用户编码',
	                afterLabelTextTpl: required,
	                allowBlank: false
	            }, {
	                xtype: 'combobox',
	                name : 'status',
	                id : 'status',
	                fieldLabel: '用户状态',
	                editable : false,
	                store: userStatusComStore,
	                displayField: 'statusdes',
	                valueField: 'statuskey',
	                queryMode: 'local',
	                afterLabelTextTpl: required,
	                allowBlank: false
	            }, {
	                xtype: 'textfield',
	                name : 'phoneNum',
	                id : 'phoneNum',
	                fieldLabel: '手机号码',
	                allowBlank: true
	            }, {
	                xtype: 'textfield',
	                name : 'email',
	                id : 'email',
	                fieldLabel: '电子邮件',
	                afterLabelTextTpl: required,
	                vtype: 'email',
	                allowBlank: false
	            }, {
	                xtype: 'datefield',
	                name : 'validDate',
	                id : 'validDate',
	                fieldLabel: '有效期至',
	                format : 'Y-m-d',
	                afterLabelTextTpl: required,
	                emptyText: '请选择一个日期...',
	                allowBlank: false
	            }],

	            buttons: [{
	                text: '提交',
	                handler: function() {
	                    if (this.up('form').getForm().isValid()) {
	                    	
	                    	if (formStatus == 0)//新增时提交form
	                    	{
	                    		this.up('form').getForm().submit({
	                            	url: xiva.webContextRoot+'/user/adduserinfo.action',
	                    			method:"post",
	                            	waitTitle:'提示',
	                            	waitMsg:'正在向服务器提交数据...', 
	                            	success : function(form, action) {
	                    					Com.xiva.user.userGrid.getStore().reload();
	                            			win.hide();
	                            			editUserForm.reset();
	                    			}
	                            });
	                    	}
	                    	else
	                    	{
	                    		
	                    		var userNameFiled = editUserForm.getForm().findField('userName');
	    						var emailFiled = editUserForm.getForm().findField('email');
	    						var nickNameFiled = editUserForm.getForm().findField('nickName');
	    						var companyIdFiled = editUserForm.getForm().findField('companyId');
	    						var userCodeFiled = editUserForm.getForm().findField('userCode');
	    						var statusFiled = editUserForm.getForm().findField('status');
	    						var phoneNumFiled = editUserForm.getForm().findField('phoneNum');
	    						var validDateFiled = editUserForm.getForm().findField('validDate');
	    						
	    						var validDateValue = Ext.util.Format.date(validDateFiled.getValue(), 'Y-m-d');
	    						var params = {
	    								'userBasicVo.userId' : Com.xiva.user.selUserId,
	    								'userBasicVo.email' : emailFiled.getValue(),
	    								'userBasicVo.nickName' : nickNameFiled.getValue(),
	    								'userBasicVo.orgId' : companyIdFiled.getValue(),
	    								'userBasicVo.userCode' : userCodeFiled.getValue(),
	    								'userBasicVo.status' : statusFiled.getValue(),
	    								'userBasicVo.phoneNum' : phoneNumFiled.getValue(),
	    								'userBasicVo.validateTime' : validDateValue
	    						};
	                    		var requestConfig = {
	                					url :xiva.webContextRoot+ '/user/modifyUserInfo.action',
	                					params : params,//请求参数
	                					callback : function(options,success,response){
	                						if (success)
	                						{
	                							Ext.MessageBox.alert('提示', '修改成功.');
	                							Com.xiva.user.userGrid.getStore().reload();
	                                			win.hide();
	                                			editUserForm.getForm().reset();
	                						}
	                						else
	                						{
	                							Ext.MessageBox.alert('提示', '修改失败.');
	                						}
	                						
	                					}
	                				};
	                    			
	            				Ext.Ajax.request(requestConfig);//发送请求
	                    	}
	                    } else {
	                    	Ext.MessageBox.alert('提示', '请将红色区域编辑框填写正确.');
	                    }
	                }
	            }, {
	                text: '关闭',
	                handler: function() {
	                    this.up('form').getForm().reset();
	                    this.up('window').hide();
	                }
	            }]
	        });
	    }
	    
	    function exportZipFile()
	    {
//			window.location.href = xiva.webContextRoot+'/downloadFile';
	    	window.location.href = xiva.webContextRoot+'/user/exportZip.action';
	    	
	    	Ext.MessageBox.show({
				msg: '正在下载, 请稍等...',
				waitConfig : {text : '下载中...'},
				width:300,
				wait :true
	        });
	    	 
			window.onblur=function(){
				Ext.MessageBox.hide();
				window.onblur = null;
			};
	    }
	    
		function searchUser()
		{
			var searchUserName = Ext.getCmp('searchUserName').value;
			var userStore = Com.xiva.user.userStore;
			userStore.proxy.extraParams.userName = searchUserName;
			userStore.proxy.extraParams.orgId = Com.xiva.user.checkedOrgId;
			userStore.load();
			userStore.proxy.extraParams.userName = '';
		}
		
		function delUser()
		{
			var selModel = Com.xiva.user.userGrid.getSelectionModel();
			var selectData = selModel.getSelection();
			var selectCnt = selModel.getCount();
			if (selectCnt == 0)
			{
				Ext.Msg.alert('提示', '没有记录被选择.');
			}
			else
			{
				
				var userIds = '';
				for (var i=0;i<selectCnt; i++)
				{
					userIds = selectData[i].get('userId') + ',';
				}
				var requestConfig = {
					url :xiva.webContextRoot+'/user/delSelUser.action',
					params : {userIds : userIds},//请求参数
					callback : function(options, success,response){
						if (success == true)
						{
							searchUser();
						}
						else
						{
							Ext.Msg.alert('提示', '删除失败.');
						}
						
					}
				};
				Ext.Ajax.request(requestConfig);//发送请求
			}
		}
		
	    var orgTreeComboStore = Ext.create('Ext.data.TreeStore', {
	        proxy: {
	            type: 'ajax',  
	            url: xiva.webContextRoot+'/org/orgtree.action'
	        },
	        model : 'xiva.org.orgTreeModel',
	        storeId : 'id',
	        root: {
	            text: '',
	            id: '0',
	            expanded: false
	        },
	        autoLoad: true
	    });
	    
		var tb = new Ext.Toolbar({
			region : 'north',
			style : {
				border : '0',
				background : '#DFE8F7'
			},
			border : false,
			items : [{
		        xtype: 'button',
		        text: '新增',
		        iconCls : 'btnIconAdd',
		        handler: function(){
					showUserWin('用户新增页面', 0);
				}
		    },{
		        xtype: 'button',
		        text: '编辑',
		        iconCls : 'btnIconEdit',
		        handler: function(){
		    		showUserWin('用户编辑页面', 1);
		    	}
		    },{
		        xtype: 'textfield',
		        name: 'searchUserName',
		        id : 'searchUserName',
		        fieldLabel: '',
		        emptyText : '请输入用户名或姓名',
		        allowBlank: true
		    },{
		        xtype: 'button',
		        text: '查询',
		        iconCls : 'btnIconFind',
		        handler: searchUser
		    },{
		        xtype: 'button',
		        text: '删除',
		        iconCls : 'btnIconDel',
		        handler: delUser
		    },{
		        xtype: 'button',
		        text: '导出',
		        iconCls : 'btnIconExcel',
		        handler: exportZipFile
		    }
		    ]
		});
		
		return tb;
	}
};

Com.xiva.user.orgTree = {
	init : function ()
	{
	    var orgTreeStore = Ext.create('Ext.data.TreeStore', {
	        proxy: {
	            type: 'ajax',  
	            url: xiva.webContextRoot+'/org/orgtree.action'
	        },
	        model : 'xiva.org.orgTreeModel',
	        storeId : 'id',
	        root: {
	            text: 'root',
	            id: '0',
	            expanded: false
	        }
	    });
		
	    var orgTree = Ext.create('Ext.tree.Panel', {
	        width: 200,
	        height: 150,
	        store: orgTreeStore,
	        rootVisible: false,
	        loadMask : {
	    		msg : "数据加载中请等待 ....."
	    	},
	        listeners: {
	    		itemclick: function(view, record, item, index, e) {
	    			Com.xiva.user.checkedOrgId = record.data.id;
	    			Com.xiva.user.userStore.removeAll();
	    			Com.xiva.user.userStore.proxy.extraParams.orgId = Com.xiva.user.checkedOrgId;
	    			Com.xiva.user.userStore.load();
	    		} 
			}
	    });
	    
	    return orgTree;
	}
		
};

Com.xiva.user.cuserGrid = {
		
	init : function()
	{
		Ext.define('userDataModel',{
	        extend: 'Ext.data.Model',
	        fields: [
	            //第一个字段需要指定mapping，其他字段，可以省略掉。
	            {name:'userId', mapping:'userId'},
	             'userName',
	             'orgName',
	             'email',
	             'nickName',
	             'registerTime',
	             'userCode',
	             'statusCode'
	        ]
	    });
	
	    //创建store
		Com.xiva.user.userStore = Ext.create('Ext.data.Store', {
	        model: 'userDataModel',
	        proxy: {
	           //异步获取数据，这里的URL可以改为任何动态页面，只要返回JSON数据即可
	            type: 'ajax',
	            url: xiva.webContextRoot+'/user/userInfoList.action',
	            reader: {
	                type: 'json',
	                root: 'items',
	                totalProperty  : 'total'
	            }
	        },
	        pageSize: 20,
	        autoLoad: false
	    });
	    
		Com.xiva.user.userStore.load({
	        params:{
	            start:0,
	            limit: 20
	        }
	    });
	    
	    //创建grid
		Com.xiva.user.userGrid = Ext.create('Ext.grid.Panel',{
	        store: Com.xiva.user.userStore,
	        columns: [
	            {text: "ID", hidden:true,disabled:true, dataIndex: 'userId', sortable: false},
	            {text: "用户统一码", width: 120, dataIndex: 'userCode', sortable: true},
	            {text: "用户名", dataIndex: 'userName', sortable: false},
	            {text: "姓名", width: 100, dataIndex: 'nickName', sortable: true},
	            {text: "用户状态", width: 100, dataIndex: 'statusCode', sortable: true},
	            {text: "用户电子邮件", width: 100, dataIndex: 'email', sortable: true},
	            {text: "组织机构", width: 100, dataIndex: 'orgName', sortable: true},
	            {text: "用户注册时间", width: 100, dataIndex: 'registerTime', sortable: true, flex: 1}
	        ],
	        region : 'center',
	        border : false,
	        viewConfig: {
	            stripeRows: true
	        },
	        selType:'checkboxmodel', 
	        multiSelect:true, 
	        dockedItems: [{
	            xtype: 'pagingtoolbar',
	            store: Com.xiva.user.userStore,
	            dock: 'bottom',
	            displayInfo: true
	        }]
	    });
	    return Com.xiva.user.userGrid;
	}
};


Ext.onReady(function() {

	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [Com.xiva.user.toolBar.init(), {
					region : 'west',
					id : 'west-panel',
					title : '组织单元',
					split : true,
					width : 200,
					minSize : 175,
					maxSize : 400,
					collapsible : true,
					margins : '0 0 0 5',
					layout : 'fit',
					layoutConfig : {
						animate : true
					},
					items : [ Com.xiva.user.orgTree.init() ]
				}, Com.xiva.user.cuserGrid.init()]
	});
});