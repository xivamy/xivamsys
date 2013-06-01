Ext.require([
    'Ext.grid.*',
    'Ext.data.*'
]);

Ext.namespace("Com.xiva.res");

Com.xiva.res.resTreeStore = null;
Com.xiva.res.resGridStore = null;
Com.xiva.res.resComboTreeStore = null;
Com.xiva.res.resGrid = null;
Com.xiva.res.selResId = null;
Com.xiva.res.resTypeComStore = null;

Com.xiva.res.toolBar = {
	init : function()
	{
		var resWin = null;
		var editResForm = null;
		var formStatus = 0; // 1表示编辑，0表示新增
		
		/**
		 * type为0新增,为1表示编辑
		 */
	    function showResWin(title, type)
	    {
	    	var resourceGrid = Com.xiva.res.resGrid;
	    	
	    	formStatus = type;
	    	if (!resWin)
	    	{
	    		showResForm();
	    	}
	    	
	    	if (formStatus == 1)
	    	{
		    	var selModel = resourceGrid.getSelectionModel();
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
	    			var resId = selectData[0].get('resId');
	    			Com.xiva.res.selResId = resId;
	    			var resObj = null;
	    			
	    			var requestConfig = {
						url :xiva.webContextRoot+ '/res/getResInfo.action',
						params : {resId : resId},//请求参数
						callback : function(options,success,response){
							resObj = Ext.JSON.decode(response.responseText);
							var resNameFiled = editResForm.getForm().findField('resourceName');
							var resTypeFiled = editResForm.getForm().findField('resourceType');
							var resUrlFiled = editResForm.getForm().findField('resourceUrl');
							var resDescFiled = editResForm.getForm().findField('resourceDesc');
							var resPidFiled = editResForm.getForm().findField('resourcePid');
							
							resNameFiled.setValue(resObj.resourceName);
							resTypeFiled.setValue(resObj.resourceType + '');
							resUrlFiled.setValue(resObj.resourceUrl);
							resDescFiled.setValue(resObj.resourceDesc);
							resPidFiled.setValue(resObj.resourcePid);
						}
					};
	    			
					Ext.Ajax.request(requestConfig);//发送请求
	    		}
	    	}
	    	else
	    	{
	    		
	    	}
    		
	    	resWin = Ext.widget('window', {
	            title: title,
	            closeAction: 'hide',
	            width: 350,
	            height: 300,
	            layout: 'fit',
	            resizable: true,
	            modal: true,
	            items: editResForm
	        });
	    	resWin.setTitle(title);
	    	resWin.show();
	    }
	    
		var resTypeComStore = Ext.create('Ext.data.Store', {
			
	        model: 'commonComboDataModel',
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
				
		resTypeComStore.load({
	        params:{
				statuskey : 'res_type'
	        }
	    });
		
		Com.xiva.res.resTypeComStore = resTypeComStore;
		
		Com.xiva.res.resComboTreeStore = Ext.create('Ext.data.TreeStore', {
	        proxy: {
	            type: 'ajax',  
	            url: xiva.webContextRoot+'/res/getAllResTree.action'
	        },
	        model : 'commonTreeModel',
	        storeId : 'id',
	        root: {
	            text: '应用框架',
	            id: '0',
	            expanded: false
	        },
	        autoLoad: true
	    });
		
	    function showResForm ()
	    {
	    	editResForm = Ext.widget('form', {
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
	                name : 'resourceName',
	                id : 'resourceName',
	                fieldLabel: '资源名称',
	                afterLabelTextTpl: required,
	                allowBlank: false
	            },{
	                xtype: 'combobox',
	                name : 'resourceType',
	                id : 'resourceType',
	                fieldLabel: '资源类型',
	                editable : false,
	                store: resTypeComStore,
	                displayField: 'statusdes',
	                valueField: 'statuskey',
	                queryMode: 'local',
	                afterLabelTextTpl: required,
	                allowBlank: false
	            }, {
	                xtype: 'textfield',
	                name : "resourceUrl",
	                id : "resourceUrl",
	                fieldLabel: '资源URL',
	                afterLabelTextTpl: required,
	                allowBlank: false
	            }, {
	                xtype: 'textfield',
	                name : "resourceDesc",
	                id : 'resourceDesc',
	                fieldLabel: '资源描述',
	                afterLabelTextTpl: required,
	                allowBlank: false
	            }, {
	                xtype: 'treepicker',
	                name : 'resourcePid',
	                id : 'resourcePid',
	                store : Com.xiva.res.resComboTreeStore,
	                displayField : 'text',
	                fieldLabel: '所属',
	                afterLabelTextTpl: required,
	                allowBlank: false
	            }],
	            buttons: [{
	                text: '提交',
	                handler: function() {
	                    if (this.up('form').getForm().isValid()) {
	                    	
	                    	if (formStatus == 0)//新增时提交form
	                    	{
	                    		this.up('form').getForm().submit({
	                            	url: xiva.webContextRoot+'/res/addResInfo.action',
	                    			method:"post",
	                            	waitTitle:'提示',
	                            	waitMsg:'正在向服务器提交数据...', 
	                            	success : function(form, action) {
                    					resWin.hide();
                            			editResForm.getForm().reset();
                            			Com.xiva.res.resGridStore.reload();
                            			Com.xiva.res.resComboTreeStore.load();
                            			Com.xiva.res.resTreeStore.load();
	                    			},
	                    			failure : function()
	                    			{
	                    				Ext.MessageBox.alert('提示', '保存失败.');
	                    				resWin.hide();
	                    			}
	                            });
	                    	}
	                    	else
	                    	{
	                    		
	                    		var resNameFiled = editResForm.getForm().findField('resourceName');
								var resTypeFiled = editResForm.getForm().findField('resourceType');
								var resUrlFiled = editResForm.getForm().findField('resourceUrl');
								var resDescFiled = editResForm.getForm().findField('resourceDesc');
								var resPidFiled = editResForm.getForm().findField('resourcePid');
	    						
	    						var params = {
	    								'resource.resourceId' : Com.xiva.res.selResId,
	    								'resource.resourceName' : resNameFiled.getValue(),
	    								'resource.resourceType' : resTypeFiled.getValue(),
	    								'resource.resourceUrl' : resUrlFiled.getValue(),
	    								'resource.resourceDesc' : resDescFiled.getValue(),
	    								'resource.resourcePid' : resPidFiled.getValue()
	    						};
	    						
	    						if (Com.xiva.res.selResId == resPidFiled.getValue())
	    						{
	    							Ext.MessageBox.alert('提示', '所属不能修改为自己.');
	    							return;
	    						}
	                    		var requestConfig = {
	                					url :xiva.webContextRoot+ '/res/modifyResInfo.action',
	                					params : params,//请求参数
	                					callback : function(options,success,response){
	                						if (success)
	                						{
	                							Ext.MessageBox.alert('提示', '修改成功.');
	                                			
	                                			resWin.hide();
	                                			editResForm.getForm().reset();
	                                			Com.xiva.res.resGridStore.reload();
	                                			Com.xiva.res.resComboTreeStore.load();
	                                			Com.xiva.res.resTreeStore.load();
	                                			
	                						}
	                						else
	                						{
	                							Ext.MessageBox.alert('提示', '修改失败.');
	                						}
	                					}
	                				};
	                    			
	            				Ext.Ajax.request(requestConfig);//发送请求
	                    	}
                    	}
	                    else
	                    {
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
	    
		function searchRes()
		{
			Com.xiva.res.resGridStore.reload();
		}
		
		function delResource(btn)
		{
			if(btn == 'no')
			{
				return;
			}
			var selModel = Com.xiva.res.resGrid.getSelectionModel();
			var selectData = selModel.getSelection();
			var selectCnt = selModel.getCount();
			if (selectCnt == 0)
			{
				Ext.Msg.alert('提示', '没有记录被选择.');
			}
			else
			{
				
				var resIds = '';
				for (var i=0;i<selectCnt; i++)
				{
					resIds = selectData[i].get('resId') + ',';
				}
				var requestConfig = {
					url :xiva.webContextRoot+'/res/delSelRes.action',
					params : {resIds : resIds},//请求参数
					callback : function(options, success,response){
						if (success == true)
						{
							searchRes();
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
					showResWin('资源新增页面', 0);
				}
		    },{
		        xtype: 'button',
		        text: '编辑',
		        iconCls : 'btnIconEdit',
		        handler: function(){
		    		showResWin('资源编辑页面', 1);
		    	}
		    },{
		        xtype: 'textfield',
		        name: 'searchUserName',
		        id : 'searchUserName',
		        fieldLabel: '',
		        emptyText : '请输入资源ID或资源名称',
		        allowBlank: true
		    },{
		        xtype: 'button',
		        text: '查询',
		        iconCls : 'btnIconFind',
		        handler: searchRes
		    },{
		        xtype: 'button',
		        text: '删除',
		        iconCls : 'btnIconDel',
		        handler: function()
		        {
		    		Ext.MessageBox.confirm('提示', '是否删除所选记录?', delResource);
		    	}
		    }]
		});
	
	return tb;
	}
};

Com.xiva.res.resTree = {
	init : function()
	{
		Com.xiva.res.resTreeStore = Ext.create('Ext.data.TreeStore', {
	        proxy: {
	            type: 'ajax',  
	            url: xiva.webContextRoot+'/res/getResTree.action'
	        },
	        model : 'commonTreeModel',
	        storeId : 'id',
	        root: {
	            text: '应用框架',
	            id: '0',
	            expanded: false
	        }
	    });
		
	    var resTree = Ext.create('Ext.tree.Panel', {
	        width: 200,
	        height: 150,
	        store: Com.xiva.res.resTreeStore,
	        rootVisible: false,
	        loadMask : {
	    		msg : "数据加载中请等待 ....."
	    	},
	        listeners: {
	    		itemclick: function(view, record, item, index, e) {
		    		if(!record.data.leaf)
		    		{
		    			Com.xiva.res.resGridStore.removeAll();
			    		Com.xiva.res.resGridStore.proxy.extraParams.resId = record.data.id;
			    		Com.xiva.res.resGridStore.load();
		    		}
	    		} 
			}
	    });
	    
	    return resTree;
	}
};

Com.xiva.res.gridPanel = {
	init : function()
	{
		Ext.define('resDataModel',{
	        extend: 'Ext.data.Model',
	        fields: [
	            //第一个字段需要指定mapping，其他字段，可以省略掉。
	            {name:'resId', mapping:'resourceId'},
	             'resourceType',
	             'resourceName',
	             'resourceUrl',
	             'resourceDesc',
	             'resourcePid'
	        ]
	    });
	
	    //创建store
		Com.xiva.res.resGridStore = Ext.create('Ext.data.Store', {
	        model: 'resDataModel',
	        proxy: {
	           //异步获取数据，这里的URL可以改为任何动态页面，只要返回JSON数据即可
	            type: 'ajax',
	            url: xiva.webContextRoot+'/res/resInfoList.action',
	            reader: {
	                type: 'json',
	                root: 'items',
	                totalProperty  : 'total'
	            }
	        },
	        pageSize: 20,
	        autoLoad: false
	    });
	    
		Com.xiva.res.resGridStore.load({
	        params:{
	            start:0,
	            limit: 20
	        }
	    });
	    
	    //创建grid
		Com.xiva.res.resGrid = Ext.create('Ext.grid.Panel',{
	        store: Com.xiva.res.resGridStore,
	        columns: [
	            {text: "资源ID", width: 75, dataIndex: 'resId', sortable: false},
	            {text: "资源名称", width: 120, dataIndex: 'resourceName', sortable: false},
	            {text: "资源类型", width: 120, dataIndex: 'resourceType',
	                renderer: function(val){
	            		var value = null;
		                var record = Com.xiva.res.resTypeComStore.findRecord('statuskey',val);
		                if (record != null)
		                {
		                	value = record.data.statusdes; 
		                }
		                else
		                {
		                	value = val; 
		                }
		                return value; 	
	            	},sortable: true},
	            {text: "资源URL", width: 160, dataIndex: 'resourceUrl', sortable: true},
	            {text: "描述", width: 280, dataIndex: 'resourceDesc', sortable: true},
	            {text: "父资源ID", width: 75, dataIndex: 'resourcePid', sortable: true}
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
	            store: Com.xiva.res.resGridStore,
	            dock: 'bottom',
	            displayInfo: true
	        }]
	    });
	    return Com.xiva.res.resGrid;
	}
};

Ext.onReady(function() {

	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [Com.xiva.res.toolBar.init(), {
					region : 'west',
					id : 'west-panel',
					title : '应用框架',
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
					items : [ Com.xiva.res.resTree.init() ]
				}, Com.xiva.res.gridPanel.init()]
	});
});