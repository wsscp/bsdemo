/**
 * 登陆控件
 * @ignore
 */
function loginOCX() {
	var map = mrl.split(',');
	if (map.length == 1) {
		var OCX = {
			    ip: map[0],
			    logintype: 0,
			    username: 'admin',
			    password: 'admin',
			    tcpPort: 37777
			};
	    var r = $(map[0]).LoginDeviceEx(OCX.ip, OCX.tcpPort, OCX.username, OCX.password, OCX.logintype);
		connectMain(map[0],map[0]+"_b");
	} else {
		for (var i = 0; i < map.length; i++) {
			var OCX = {
				    ip: map[i],
				    logintype: 0,
				    username: 'admin',
				    password: 'admin',
				    tcpPort: 37777
				};
		    var r = $(map[i]).LoginDeviceEx(OCX.ip, OCX.tcpPort, OCX.username, OCX.password, OCX.logintype);
			connectMain(map[i],map[i]+"_b");
		}
	}
}


function connectMain(id,b_id) {
    //ind是当前需要打开的通道号, 目前暂且为0
    var ch = 0;
    if ($(b_id).className == 'b_Stream1') {
        if ($(id) && $(id).ConnectRealVideo(ch, 1)) {
            $(b_id).className = 'b_Stream2';
        } 
    }
}

window.addEvent('domready', function() {
	
	loginOCX();
});

