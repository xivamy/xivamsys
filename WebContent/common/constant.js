Ext.regModel("commonTreeModel", { 
    fields: ['id', 'text', 'other'] 
});

Ext.define('commonComboDataModel',{
    extend: 'Ext.data.Model',
    fields: [
        //第一个字段需要指定mapping，其他字段，可以省略掉。
        {name:'statuskey', mapping:'statuskey'},
         'statusdes'
    ]
});