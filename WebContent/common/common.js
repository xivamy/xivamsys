
xiva.synGetJsonObject = function(url){
	try{
		var conn = Ext.lib.Ajax.getConnectionObject().conn; 
		conn.open("GET",url,false); 
		conn.send(null); 
		return Ext.JSON.decode(conn.responseText);
	}catch(e){
		Ext.log(e.message || e.descript);
		return null;
	}
};

/**
 * set FormPanel's fields readOnly,一般用于查看时，设置所有字段只读
 * @param {Ext.form.FormPanel} formPanel
 */
xiva.setFormReadOnly=function(formPanel,readOnly){
	var children=formPanel.items;
	if (!children) return;
	if (readOnly===true) readOnly=true;else readOnly=false;
	for (var i=0;i<children.getCount();i++){
		var childTmp=children.get(i);
		if (childTmp.items){
			xiva.setFormReadOnly(childTmp,readOnly);
		}
		var xtype=childTmp.getXType();
		if (childTmp.isFormField) {
			if (xtype=="hidden") continue;
			if (xtype=="textfield" || xtype=="textarea" || xtype=="numberfield"){
				if (childTmp.rendered){
					childTmp.getEl().dom.readOnly=readOnly;
				}else{
					childTmp.readOnly=readOnly;
				}
			}else if (childTmp.isXType("trigger")){
				childTmp.setDisabled(readOnly);
			}else {
				childTmp.setDisabled(readOnly);
			} 
		}else{
			if (xtype=="button") childTmp.setDisabled(readOnly);
		}
	}	
};

var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';