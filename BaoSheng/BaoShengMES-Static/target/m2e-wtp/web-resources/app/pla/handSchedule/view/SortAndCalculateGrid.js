/**
 * 可选工艺列表
 */
Ext.define('bsmes.view.SortAndCalculateGrid', {
			extend : 'Oit.app.view.Grid',
			itemId : 'sortAndCalculateGrid',
			alias : 'widget.sortAndCalculateGrid',
			store : 'SortAndCalculateStore',
			hasPaging : false,
			height : 370,
			overflowY : 'auto',
			defaultEditingPlugin : false,
			plugins : [{
						ptype : 'rowexpander',
						rowBodyTpl : ['<div id="{id}">', '</div>']
					}],
			columns : [{
						text : Oit.msg.pla.customerOrderItem.customerContractNO,
						dataIndex : 'contractNo',
						flex : 1,
						sortable : false,
						menuDisabled : true
					}, {
						text : Oit.msg.pla.customerOrderItem.customerCompany,
						dataIndex : 'customerCompany',
						flex : 1,
						sortable : false,
						menuDisabled : true
					}, {
						text : Oit.msg.pla.customerOrderItem.importance,
						dataIndex : 'importance',
						flex : 1,
						sortable : false,
						menuDisabled : true
					}, {
						text : Oit.msg.pla.customerOrderItem.customerOaDate,
						dataIndex : 'customerOaDate',
						xtype : 'datecolumn',
						format : 'Y-m-d',
						flex : 1,
						sortable : false,
						menuDisabled : true
					}, {
						text : Oit.msg.pla.customerOrderItem.fixedOa,
						dataIndex : 'fixedOa',
						flex : 1,
						sortable : false,
						menuDisabled : true
					}, {
						text : Oit.msg.pla.customerOrderItem.operator,
						dataIndex : 'operator',
						flex : 1,
						sortable : false,
						menuDisabled : true
					}],
			tbar : [{
						itemId : 'move',
						text : Oit.msg.pla.customerOrderItem.button.move
					}, {
						itemId : 'down',
						text : Oit.msg.pla.customerOrderItem.button.down
					}, {
						itemId : 'top',
						text : Oit.msg.pla.customerOrderItem.button.top
					}, {
						itemId : 'end',
						text : Oit.msg.pla.customerOrderItem.button.end
					}],

			initComponent : function(){
				var me = this;

				this.callParent(arguments);
				me.view.on('expandBody', function(rowNode, record, expandRow, eOpts, a, b, c){

							var customerOrderId = record.get('id');
							var innerStore = Ext.create('bsmes.store.SortAndCalculateSonStore');
							innerStore.load({
										params : {
											customerOrderId : customerOrderId
										}
									});
							var innerGrid = Ext.create('bsmes.view.SortAndCalculateSonGrid', {
										store : innerStore,
										renderTo : customerOrderId,
										parentRowindex : record.index
									});
							innerGrid.getEl().swallowEvent(['mousedown', 'mouseup', 'click', 'contextmenu',
									'mouseover', 'mouseout', 'dblclick', 'mousemove']);
							innerGrid.on('toOpenSetWindow', me.doOpenSetWindow, me);
						});

				me.view.on('collapsebody', function(rowNode, record, expandRow, eOpts){
							var parent = document.getElementById(record.get('id'));
							var child = parent.firstChild;
							while (child) {
								child.parentNode.removeChild(child);
								child = child.nextSibling;
							}
						});
			},
			doOpenSetWindow : function(data, grid){
				console.log(data);
				console.log(grid);
			}

		});
