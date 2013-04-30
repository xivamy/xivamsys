Ext.require([
    'Ext.grid.*',
    'Ext.data.*'
]);

Ext.namespace("Com.xiva.org");

Com.xiva.org.addOrgWin = null;
Com.xiva.org.orgGrid = null;
Com.xiva.org.checkedOrgId = 0;
Com.xiva.org.orgComboxStore = null;
Com.xiva.org.orgTreeStore = null;
Com.xiva.org.selOrgId = null;

Com.xiva.org.toolbar = {
		
	init : function()
	{
		var editOrgForm = null;
		var formStatus = 0; // 1表示编辑，0表示新增
		var win = Com.xiva.org.addOrgWin;
		
		function showOrgWin(title, type)
		{
			var orgGrid = Com.xiva.org.orgGrid;
			formStatus = type;
	        if (!win) 
	        {
	        	createOrgForm();
	        };
	        
	        if (type == 1)
	    	{
	        	var selModel = orgGrid.getSelectionModel();
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
	    			var orgId = selectData[0].get('sysId');
	    			Com.xiva.org.selOrgId = orgId;
	    			editOrgForm.getForm().load({
	    				url :xiva.webContextRoot+ '/org/getOrgInfo.action',
	    				params : {orgId : orgId},//请求参数
	    				success : function(form, action)
	    				{
	    				},
	    				failure : function(form, action)
	    				{
	    					Ext.Msg.alert('提示', '加载失败.');
	    				}
	    			});
	    		}
	    	}
	        else
	    	{
	    	}
	        
	        win = Ext.widget('window', {
                title: title,
                closeAction: 'hide',
                width: 460,
                height: 300,
                layout: 'fit',
                resizable: true,
                modal: true,
                items: editOrgForm
            });
	        
	        win.setTitle(title);
	        win.show();
	    }
	    
		function createOrgForm()
		{
			editOrgForm = Ext.widget('form', {
                layout: {
                    type: 'vbox',
                    align: 'stretch'
                },
                border: false,
                bodyPadding: 10,

                fieldDefaults: {
                    labelAlign: 'left',
                    labelWidth: 120,
                    labelStyle: 'font-weight:bold'
                },
                items: [{
                    xtype: 'combobox',
                    name : "parentId",
                    id : "parentId",
                    fieldLabel: '上级组织名称',
                    editable : false,
                    store: Com.xiva.org.orgComboxStore,
                    displayField: 'orgName',
                    valueField: 'orgId',
                    queryMode: 'local',
                    afterLabelTextTpl: required,
                    allowBlank: false
                }, {
                    xtype: 'textfield',
                    name : "orgName",
                    fieldLabel: '组织单元名称',
                    afterLabelTextTpl: required,
                    allowBlank: false
                }, {
                    xtype: 'textfield',
                    name : "orgType",
                    fieldLabel: '组织单元性质',
                    afterLabelTextTpl: required,
                    allowBlank: false
                }, {
                    xtype: 'textfield',
                    name : "orgCode",
                    fieldLabel: '组织单元编码',
                    afterLabelTextTpl: required,
                    allowBlank: false
                }, {
                    xtype: 'textfield',
                    name : "unicode",
                    fieldLabel: '统一编码',
                    afterLabelTextTpl: required,
                    allowBlank: false
                }, {
                    xtype: 'datefield',
                    name : "setUpDate",
                    format : 'Y-m-d',
                    fieldLabel : '设立日期',
                    afterLabelTextTpl : required,
                    editable : false,
                    allowBlank: false
                }, {
                    xtype: 'datefield',
                    name : "invalidDate",
                    format : 'Y-m-d',
                    fieldLabel: '注销日期',
                    afterLabelTextTpl : required,
                    editable : false,
                    allowBlank: false
                }],

                buttons: [{
                    text: '提交',
                    handler: function() {
                		var form = this.up('form').getForm();
                		
                        if (form.isValid()) {
                        	
                        	if (formStatus == '0')
                        	{
                        		this.up('form').getForm().submit({
                                	url: xiva.webContextRoot+'/org/addorg.action',
                        			method:"post",
                                	waitTitle:'提示',
                                	waitMsg:'正在向服务器提交数据...', 
                                	success : function(form, action) {
                                		var checkedOrgId = Com.xiva.org.checkedOrgId;
                                		if(checkedOrgId != 0)
                                		{
                                			var orgTreeStore = Com.xiva.org.orgTreeStore;
                                			var treePath = orgTreeStore.getNodeById(checkedOrgId).getPath();
                							var requestConfig = {
                								url :xiva.webContextRoot+'/org/saveTreePath.action',
                								params : {treePath : treePath},//请求参数
                								callback : function(options,success,response){
                									orgTreeStore.load();
                								}
                							};
                							
                							Ext.Ajax.request(requestConfig);//发送请求
                                		}
                                		Com.xiva.org.orgGrid.getStore().reload();
                            			win.hide();
                            			form.reset();
                        			}
                                });
                        	}
                        	else
                        	{
                        		this.up('form').getForm().submit({
                                	url: xiva.webContextRoot+'/org/modifyOrg.action',
                        			method:"post",
                        			params : {orgId : Com.xiva.org.selOrgId},//请求参数
                                	waitTitle:'提示',
                                	waitMsg:'正在向服务器提交数据...', 
                                	success : function(form, action) {
                                		var checkedOrgId = Com.xiva.org.checkedOrgId;
                                		if(checkedOrgId != 0)
                                		{
                                			var orgTreeStore = Com.xiva.org.orgTreeStore;
                                			var treePath = orgTreeStore.getNodeById(checkedOrgId).getPath();
                							var requestConfig = {
                								url :xiva.webContextRoot+'/org/saveTreePath.action',
                								params : {treePath : treePath},//请求参数
                								callback : function(options,success,response){
                									orgTreeStore.load();
                								}
                							};
                							
                							Ext.Ajax.request(requestConfig);//发送请求
                                		}
                                		Com.xiva.org.orgGrid.getStore().reload();
                            			win.hide();
                            			form.reset();
                        			}
                                });
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
	    }
	    
	    
	    function delOrg()
		{
			var selModel = Com.xiva.org.orgGrid.getSelectionModel();
			var selectData = selModel.getSelection();
			var selectCnt = selModel.getCount();
			
			if (selectCnt == 0)
			{
				Ext.Msg.alert('提示', '没有记录被选择.');
			}
			else
			{
				
				var orgIds = '';
				for (var i=0;i<selectCnt; i++)
				{
					orgIds = selectData[i].get('sysId') + ',';
				}
				var requestConfig = {
					url :xiva.webContextRoot+'/org/delSelOrg.action',
					params : {orgIds : orgIds},//请求参数
					success : function(response){
						Com.xiva.org.orgTree.refreshTree();
						var orgStore = Com.xiva.org.orgGrid.getStore();
		    			orgStore.proxy.extraParams.nodeId = Com.xiva.org.checkedOrgId;
	        			orgStore.load();
					}
				};
				Ext.Ajax.request(requestConfig);//发送请求
			}
		}
	    
		Ext.define('com.xiva.org.comboxDataModel',{
	        extend: 'Ext.data.Model',
	        fields: [
	            //第一个字段需要指定mapping，其他字段，可以省略掉。
	            {name:'orgId', mapping:'sysId'},
	             'orgName'
	        ]
	    });
		
		Ext.define('com.xiva.org.ListDataModel',{
	        extend: 'Ext.data.Model',
	        fields: [
	            //第一个字段需要指定mapping，其他字段，可以省略掉。
	            {name:'sysId', mapping:'sysId'},
	             'orgName',
	             'orgType',
	             'orgCode',
	             'unicode',
	             'area',
	             'setUpDate',
	             'invalidDate',
	             'registerTime'
	        ]
	    });
		
	    Ext.regModel("xiva.org.orgTreeModel", { 
	        fields: ['id', 'text', 'other'] 
	    });
	    
	    var orgComboxStore = Ext.create('Ext.data.Store', {
	        model: 'com.xiva.org.comboxDataModel',
	        proxy: {
	           //异步获取数据，这里的URL可以改为任何动态页面，只要返回JSON数据即可
	            type: 'ajax',
	            url: xiva.webContextRoot+'/org/orgcomb.action',
	            reader: {
	                type: 'json'
	            }
	        },
	        autoLoad: false
	    });
	    Com.xiva.org.orgComboxStore = orgComboxStore;
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
					showOrgWin('新增基准组织单元', 0);
				}
		    },{
		        xtype: 'button',
		        text: '编辑',
		        iconCls : 'btnIconEdit',
		        handler: function(){
					showOrgWin('编辑基准组织单元', 1);
				}
		    },{
		        xtype: 'button',
		        text: '删除',
		        iconCls : 'btnIconDel',
		        handler: delOrg
		    },{
		        xtype: 'button',
		        text: '导出',
		        iconCls : 'btnIconExcel',
		        handler: exportZipFile
		    }]
		});
		return tb;
	}
};


Com.xiva.org.orgTree = {
		
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
	            text: '根节点',
	            id: '0',
	            expanded: false
	        }
	    });
	    Com.xiva.org.orgTreeStore = orgTreeStore;
	    
		var orgTree = Ext.create('Ext.tree.Panel', {
	        width: 200,
	        height: 150,
	        store: orgTreeStore,
	        rootVisible: false,
	        loadMask : {
	    		msg : "数据加载中请等待 ....."
	    	},
	        listeners: {
	    		itemclick: function(view, record, item, index, e) 
	    		{
	    			var orgCombStore = Com.xiva.org.orgComboxStore;
	    			var orgStore = Com.xiva.org.orgGrid.getStore();
	    			var checkedOrgId = record.data.id;
	    			
	    			orgCombStore.proxy.extraParams.porgId = checkedOrgId;
	    			orgCombStore.reload();
	    			
	    			if(!record.data.leaf)
	    			{
	    				Com.xiva.org.checkedOrgId = checkedOrgId;
	    				orgStore.proxy.extraParams.nodeId = checkedOrgId;
	        			orgStore.load();
	    			}
	    		} 
			}
	    });
	    return orgTree;
	},
	refreshTree : function()
	{
		var orgTreeStore = Com.xiva.org.orgTreeStore;
		var checkedOrgId = Com.xiva.org.checkedOrgId;
		var treePath = orgTreeStore.getNodeById(checkedOrgId).getPath();
		var requestConfig = {
			url :xiva.webContextRoot+'/org/saveTreePath.action',
			params : {treePath : treePath},//请求参数
			callback : function(options,success,response){
				orgTreeStore.load();
			}
		};
		
		Ext.Ajax.request(requestConfig);//发送请求
	}
};

Com.xiva.org.showOrgGrid = {
	init : function () {
	
	    // 创建数据源
	    var orgGridStore = Ext.create('Ext.data.Store', {
	        model: 'com.xiva.org.ListDataModel',
	        proxy: {
	            // 异步获取数据，这里的URL可以改为任何动态页面，只要返回JSON数据即可
	            type: 'ajax',
	            url: xiva.webContextRoot+'/org/orgInfoList.action',
	            reader: {
	                type: 'json',
	                root: 'items',
	                totalProperty  : 'total'
	            }
	        },
	        pageSize: 10,
	        autoLoad: false
	    });
	    
	    orgGridStore.load({
	        params:{
	            start:0,
	            limit: 10
	        }
	    });
    
    	// 创建Grid
	    var grid = Ext.create('Ext.grid.Panel',{
	        store: orgGridStore,
	        title: '下级组织信息',
	        columns: [
	            {text: "sysId", hidden:true,disabled:true, dataIndex: 'sysId', sortable: false},
	            {text: "组织单元名称", width: 120, dataIndex: 'orgName', sortable: true},
	            {text: "组织单元性质", dataIndex: 'orgType', sortable: false},
	            {text: "组织单元编码", width: 100, dataIndex: 'orgCode', sortable: true},
	            {text: "统一编码", width: 100, dataIndex: 'unicode', sortable: true},
	            {text: "所处域", width: 100, dataIndex: 'area', sortable: true},
	            {text: "设立日期", width: 100, dataIndex: 'setUpDate', sortable: true},
	            {text: "撤销日期", width: 100, dataIndex: 'invalidDate', sortable: true},
	            {text: "Leaf", width: 100, dataIndex: 'registerTime', sortable: true, flex: 1}
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
	            store: orgGridStore,
	            dock: 'bottom',
	            displayInfo: true
	        }]
	    });
	
	    Com.xiva.org.orgGrid = grid;
	    
		return grid;
	}
};
Ext.onReady(function() {

	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [Com.xiva.org.toolbar.init(), 
		   {
			region : 'west',
			id : 'west-panel',
			title : '基准组织单元',
			split : true,
			width : 200,
			minSize : 175,
			maxSize : 400,
			collapsible : false,
			margins : '0 0 0 5',
			layout : 'fit',
			layoutConfig : {
				animate : true
			},
			tools:[{
			    type:'refresh',
			    tooltip: 'Refresh tree Data',
			    handler: function(event, toolEl, panel){
					Com.xiva.org.orgTree.refreshTree();
			    }
			}],
			items : [
			   Com.xiva.org.orgTree.init()
			]
		}, Com.xiva.org.showOrgGrid.init()]
	});
});
