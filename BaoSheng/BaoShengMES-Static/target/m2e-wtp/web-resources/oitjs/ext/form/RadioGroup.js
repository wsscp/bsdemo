/**
 * 有bug
 */
Ext.define("Oit.form.RadioGroup", {
	override : 'Ext.form.RadioGroup',
	setValue : function(value) {
		var radios = this.items.items;
    	Ext.each(radios,function(item,i){
    		if(item.inputValue == value){
    			item.setValue(true);
    		}
    	});
        return this;
	},
	getValue : function(value) {
		 var boxes = this.getBoxes(); 
		 var bLen = boxes.length;
		 for (i = 0; i < bLen; i++) {
			 var box = boxes[i];
			 if (box.getValue()) {
				 return box.inputValue;
			 }
		 }
	}
});