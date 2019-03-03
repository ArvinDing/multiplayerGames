var stompClient = null;

function connect() {
	var socket = new SockJS('/websocket');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		console.log('Connected: ' + frame);
		stompClient.subscribe('/topic/server', function(packet) {
			// alert(packet);
			var save = JSON.parse(packet.body);
			handleKey(save.content);

		});
	});
}

function disconnect() {
	if (stompClient !== null) {
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}

function send(message) {
	
	// n=1 downkey,n=0 upkey
	stompClient.send("/app/wow", {}, (JSON.stringify({
		'content' : message
	})));
}

function handleKey(content) {
	var a = content.split(" ");
	if(id!=a[0]){
		if (a[1] == 0) {
			pressedO[a[2]] = false;
		} else if(a[1]==1) {
			pressedO[a[2]] = true;
		}else if(a[1]==2) {
			playerXO=parseInt(a[2]);
			playerYO=parseInt(a[3]); 
			healthO=parseInt(a[4]); 
		}else{
			missleO.push(new Array(a[2],parseInt(a[3]),parseInt(a[4]),2));
		}
	}
}