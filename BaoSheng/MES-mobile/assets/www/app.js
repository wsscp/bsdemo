 

//数据库文件
var localFileName = "superdad.db",fgDB;
var weinaiStore, muruStore, niaobuStore, tiwenStore, shuijiaoStore;

function onBodyLoad() {
	// 注册回退按钮事件监听器
	document.addEventListener("backbutton", onBackKeyDown, false); // 返回键
	 
	if (Ext.os.is.Windows) {
		 
		//fgDB = new sqliteDB(localFileName, 1024*1024*2); 
		if(0) {
			initFGdb();
		}
	} else {
		document.addEventListener("deviceready", initSystem, true);
	}
}

function initFGdb() {
	fgDB.transaction(function(tx) {
		tx.executeSql('DROP TABLE IF EXISTS weiyang');
		tx.executeSql('CREATE TABLE IF NOT EXISTS [weiyang] (' +
				'[id] INTEGER PRIMARY KEY AUTOINCREMENT, ' +
				'[itemhash] VARCHAR2(16), ' +
				'[stype] VARCHAR2(2), ' +
				'[date] VARCHAR2(20), ' +
				'[volume] VARCHAR2(4), ' +
				'[remark] VARCHAR2(200), ' +
				'[dateCreated] DATETIME)'
			);
	}, function(){  
		//alert('初始化表成功'); 
	}, function (er) {
		console.log('error with executeSql', er);
	});	
}

function initSystem() {
	//compass = new Compass();
	//compass.startWatch();
	//alert("罗盘成功！");
	window.requestFileSystem(LocalFileSystem.PERSISTENT, 0, function(fs) {
				fileSystem = fs;
				// isFirstLoad = false;
				if (fileSystem != null) {
					// alert(fileSystem.root.fullPath);
					var mapFile = fileSystem.root.getDirectory("superdad/", {
								create : true,
								exclusive : false
							}, function(parent) {
								//打开数据库
								openMBdb(parent.fullPath);
								// alert(mapPath);
						}, function(msg) {
							// alert(msg);
						});
				} else {
					alert("数据库打开失败!");
				}
			}, function() {
				alert("数据库打开失败!");
			});
}
// enable Ext autoloader
Ext.Loader.setConfig({
			enabled : true
		});
		
function openMBdb(path) {
			var options = {};
			options.storage = "external";
			options.path = path;
			fgDB = new PGSQLitePlugin(localFileName, function(dbResult, dbObject){
			    console.log("Database status=" + dbResult.status);
			    console.log("Database version=" + dbResult.version);
			    //fgdb = dbObject;
			    //alert("数据库打开成功");
			    if(dbResult.isNew) {
			    	initFGdb();
			    }
			}, function(err){
			    console.log("Error create database::err=" + err);
			    alert("数据库打开失败" + err);
			},options);
}

function onConfirm(button) {
	// alert('You selected button ' + button);
	if (button == 1)
		navigator.app.exitApp(); // 选择了确定才执行退出
}
// Show a custom confirmation dialog
//
function onBackKeyDown() {
	navigator.notification.confirm('按确定退出程序!', // message
			onConfirm, // callback to invoke with index of button pressed
			'确定要退出程序吗?', // title
			'确定,取消' // buttonLabels
	);
}

// <debug>
Ext.Loader.setPath({
			'Ext.ux' : 'ux',
			'Ext' : 'lib/st2.3.1/src',
			'MES' : 'app'
		});
// </debug>
Ext.application({
			name : 'MES',	//程序名称
			requires : ['Ext.MessageBox'], //引用的资源
			//models : ['MESInfo'],	//数据模型
			//stores : ['MESStore'],	//数据源
			views : ['Login'],	//视图
			controllers : ['MainController'],	//控制器,
			launch : function() {
				// Destroy the #appLoadingIndicator element
				// Ext.fly('appLoadingIndicator').destroy();
				//Ext.Viewport.add(Ext.create('MES.view.Login'));
				Ext.Viewport.add(Ext.create('MES.view.Main'));
				/*
				weinaiStore = Ext.create('MES.store.MESStore');
				muruStore = Ext.create('MES.store.MESStore');
				niaobuStore = Ext.create('MES.store.MESStore');
				tiwenStore = Ext.create('MES.store.MESStore');
				shuijiaoStore = Ext.create('MES.store.MESStore');
				*/
				Ext.Date.monthNames = [  
					   '一月', '二月', '三月', '四月', '五月', '六月',   
					   '七月', '八月', '九月', '十月', '十一月', '十二月'  
				      ]; 
				      
				Ext.Date.dayNames=["星期一", "星期二",  "星期三",  "星期四",  "星期五",  "星期六",  "星期日"];
			},
			// html5缓存更新
			onUpdated : function() {
				Ext.Msg.confirm("更新", "系统已经自动更新到最新版本，是否重新加载？", function(
								buttonId) {
							if (buttonId === 'yes') {
								window.location.reload();
							}
						});
			}
		});
		
