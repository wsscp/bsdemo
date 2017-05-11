Ext.define("bsmes.view.DataDicList", {
			extend : 'Oit.app.view.Grid',
			alias : 'widget.dataDicList',
			store : 'DataDicStore',
			defaultEditingPlugin : false,
			columns : [{
						text : Oit.msg.bas.dic.termsCode,
						dataIndex : 'termsCode'
					}, {
						text : Oit.msg.bas.dic.termsName,
						dataIndex : 'termsName',
						sortable : false
					}, {
						text : Oit.msg.bas.dic.code,
						dataIndex : 'code'
					}, {
						text : Oit.msg.bas.dic.name,
						dataIndex : 'name',
						editor : 'textfield',
						sortable : false
					}, {
						text : Oit.msg.bas.dic.seq,
						dataIndex : 'seq',
						editor : 'textfield'
					}, {
						text : Oit.msg.bas.dic.extatt,
						dataIndex : 'extatt',
						editor : 'textfield'
					}, {
						text : Oit.msg.bas.dic.marks,
						dataIndex : 'marks',
						editor : 'textfield'
					}, {
						text : Oit.msg.bas.dic.status,
						dataIndex : 'status',
						editor : {
							xtype : 'radiogroup',
							items : [{
										boxLabel : Oit.msg.bas.dic.normal,
										name : 'status',
										inputValue : '1'
									}, {
										boxLabel : Oit.msg.bas.dic.freeze,
										name : 'status',
										inputValue : '0'
									}]
						}
					}],
			dockedItems : [{
						xtype : 'toolbar',
						dock : 'top',
						items : [{
									xtype : 'hform',
									items : [{
												fieldLabel : Oit.msg.bas.dic.termsCode,
												name : 'termsCode',
												xtype : 'combobox',
												labelWidth : 85,
												queryMode : 'local',
												displayField : 'termsName',
												valueField : 'termsCode',

												store : new Ext.data.Store({
															fields : ['termsName', 'termsCode'],
															autoLoad : true,
															proxy : {
																type : 'rest',
																url : 'dataDic/getTermsCode?needALL=true'
															}

														})
											}, {
												fieldLabel : '&nbsp; ' + Oit.msg.bas.dic.code,
												name : 'code'
											}, {
												fieldLabel : '&nbsp; ' + Oit.msg.bas.dic.name,
												name : 'name'
											}]
								}]
					}],
			actioncolumn : [{
						itemId : 'edit',
						isDisabled : function(view, rowIndex, colIndex, item, record) {
							if (record.get('canModify')) {
								return false;
							}
							return true;
						}
					}],
			tbar : [{
						itemId : 'add'
					}, {
						itemId : 'remove'
					}, {
						itemId : 'search'
					}]
		});
