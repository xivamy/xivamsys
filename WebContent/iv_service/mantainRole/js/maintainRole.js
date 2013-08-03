Ext.require([
    'Ext.grid.*',
    'Ext.data.*'
]);

Ext.namespace("Com.xiva.role");



Ext.define('roleDataModel',{
    extend: 'Ext.data.Model',
    fields: [
        //第一个字段需要指定mapping，其他字段，可以省略掉。
        {name:'roleId', mapping:'roleId'},
         'roleName',
         'roleDesc',
         'rolePId'
    ]
});

Com.xiva.role.toolBar = {
	init : function()
	{
		function searchRole()
		{
			
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
					showResWin('角色新增页面', 0);
				}
		    },{
		        xtype: 'button',
		        text: '编辑',
		        iconCls : 'btnIconEdit',
		        handler: function(){
		    		showResWin('角色编辑页面', 1);
		    	}
		    },{
		        xtype: 'textfield',
		        name: 'searchUserName',
		        id : 'searchUserName',
		        fieldLabel: '',
		        emptyText : '请输入角色ID或角色名称',
		        allowBlank: true
		    },{
		        xtype: 'button',
		        text: '查询',
		        iconCls : 'btnIconFind',
		        handler: searchRole
		    },{
		        xtype: 'button',
		        text: '删除',
		        iconCls : 'btnIconDel',
		        handler: function()
		        {
		    		Ext.MessageBox.confirm('提示', '是否删除所选记录?', delRole);
		    	}
		    }]
		});
		
		return tb;
	}
};

Com.xiva.role.roleTree = {
	init : function()
	{
		
	}
};

Com.xiva.role.gridPanel = {
	init : function()
	{
	
	    //创建store
		Com.xiva.role.roleGridStore = Ext.create('Ext.data.Store', {
	        model: 'roleDataModel',
	        proxy: {
	           //异步获取数据，这里的URL可以改为任何动态页面，只要返回JSON数据即可
	            type: 'ajax',
	            url: xiva.webContextRoot+'/role/roleInfoList.action',
	            reader: {
	                type: 'json',
	                root: 'items',
	                totalProperty  : 'total'
	            }
	        },
	        pageSize: 20,
	        autoLoad: false
	    });
	    
		Com.xiva.role.roleGridStore.load({
	        params:{
	            start:0,
	            limit: 20
	        }
	    });
	    
	    //创建grid
		Com.xiva.role.roleGrid = Ext.create('Ext.grid.Panel',{
	        store: Com.xiva.role.roleGridStore,
	        columns: [
	            {text: "角色ID", width: 75, dataIndex: 'roleId', sortable: false},
	            {text: "角色名称", width: 160, dataIndex: 'roleName', sortable: false},
	            {text: "角色描述", width: 520, dataIndex: 'roleDesc', sortable: true},
	            {text: "父角色ID", width: 75, dataIndex: 'rolePId', sortable: true}
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
	            store: Com.xiva.role.roleGridStore,
	            dock: 'bottom',
	            displayInfo: true
	        }]
	    });
	    return Com.xiva.role.roleGrid;
	}
};

Ext.onReady(function() {

	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [Com.xiva.role.toolBar.init(), {
					region : 'west',
					id : 'west-panel',
					title : '角色树',
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
					items : [ Com.xiva.role.roleTree.init() ]
				}, Com.xiva.role.gridPanel.init()]
	});
});