/**
 * 重写field的验证
 */
var recordErrors;
Ext.define("Oit.form.Panel", {
	override : 'Ext.form.Panel',
	isValid : function() {
		// 验证model的validation
		if(typeof(this.getRecord()) == "undefined"){
			return this.form.isValid(); // 没有model的直接返回form验证
		}else{
			recordErrors = this.getRecord().validate();
		}
		// 验证form的validation
		var valid = this.form.isValid() && recordErrors.isValid();
		// 验证markInvalid
		this.getForm().getFields().each(function(field) {
			var fieldErrors = [];
			Ext.each(recordErrors.getByField(field.name), function(error) {
				fieldErrors.push(error.message);
			});
			field.markInvalid(fieldErrors);
		});

		recordErrors = undefined;
		return valid;
	}
});

Ext.define("Oit.form.field.Text", {
	override : 'Ext.form.field.Text',
	validateValue : function(value) {
		var me = this, errors = me.getErrors(value), isValid = Ext.isEmpty(errors);
		// add by chanedi! begin 汇总所有错误到recordErrors
		if (recordErrors) {
			Ext.each(errors, function(error) {
				recordErrors.items.push({
					field : me.name,
					message : error
				});
				recordErrors.length++; 
			});
		} else
		// add by chanedi! end 汇总所有错误到recordErrors

		if (!me.preventMark) {
			if (isValid) {
				me.clearInvalid();
			} else {
				me.markInvalid(errors);
			}
		}

		return isValid;
	}
});
