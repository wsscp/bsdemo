Ext.define('bsmes.view.TaskInfoGrid',{
    extend:'Ext.grid.Panel',
    alias:'widget.taskInfoGrid',
    forceFit:true,
    store:'TaskInfoStore',
    itemId:'taskInfoGrid',
    changeRowColor:function(metaData, record){
        if(record.get('status') == 'IN_PROGRESS'){
            metaData.style = "background-color: green";
        }else if(record.get('status') == 'FINISHED'){
            metaData.style = "background-color: yellow";
        }
    },
    listeners:{
        itemdblclick:function(me,record,index){
            var status = record.get('status');
            if(status== 'IN_PROGRESS'
                || status == 'FINISHED'
                || status == 'CANCELED'){
                return;
            }
            Ext.MessageBox.confirm('确认', '是否切换任务?',  function(btn) {
                if (btn == 'yes') {
                    Ext.Ajax.request({
                        url:'startTask',
                        method:'POST',
                        params:{
                            workOrderNo:record.get('workOrderNo'),
                            color:record.get('color')
                        },
                        success:function(response){
                            Ext.ComponentQuery.query('#taskInfoGrid')[0].getStore().reload();
                            Ext.ComponentQuery.query('#feedOrderTaskId')[0].setValue(record.get('id'));
                            Ext.ComponentQuery.query('#feedColor')[0].setValue(record.get('color'));

                            console.log(Ext.ComponentQuery.query('#feedColor')[0].getValue());
                        }
                    });
                }
            });
        }
    },
    columns:[{
                text : '颜色',
                dataIndex : 'color',
                flex:0.5,
                renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
                    if(record.get('status') == 'IN_PROGRESS'){
                        metaData.style = "background-color: green";
                        Ext.ComponentQuery.query('#feedOrderTaskId')[0].setValue(record.get('id'));
                        Ext.ComponentQuery.query('#feedColor')[0].setValue(record.get('color'));
                    }else if(record.get('status') == 'FINISHED'){
                        metaData.style = "background-color: yellow";
                    }
                    return value;
                }
            },{
                text : '模芯<br/>模套',
                dataIndex : 'modeInter',
                flex:1,
                renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
                    this.changeRowColor(metaData, record);
                    return value;
                }
            },{
                text : '材料',
                dataIndex : 'material',
                flex:2,
                renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
                    this.changeRowColor(metaData, record);
                    return value;
                }
            },{
                text : '长度',
                dataIndex : 'taskLength',
                flex:0.8,
                renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
                    this.changeRowColor(metaData, record);
                    return value;
                }
            },{
                text:'已完成<br/>长度',
                dataIndex:'finishedLength',
                flex:1,
                renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
                    this.changeRowColor(metaData, record);
                    return value;
                }
            },{
                text:'标称<br/>厚度',
                dataIndex:'standardPly',
                flex:0.8,
                renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
                    this.changeRowColor(metaData, record);
                    return value;
                }
            },{
                text:'最大值',
                dataIndex:'standardMaxPly',
                flex:0.7,
                renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
                    this.changeRowColor(metaData, record);
                    return value;
                }
            },{
                text:'最小值',
                dataIndex:'standardMinPly',
                flex:0.7,
                renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
                    this.changeRowColor(metaData, record);
                    return value;
                }
            },{
                text:'外径',
                dataIndex:'outsideValue',
                flex:0.5,
                renderer :function(value, metaData, record, rowIndex, columnIndex, store, view){
                    this.changeRowColor(metaData, record);
                    return value;
                }
            }]
});

