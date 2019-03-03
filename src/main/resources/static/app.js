var stompClient = null;

function connect() {
	var socket = new SockJS('/websocket');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		console.log('Connected: ' + frame);
		stompClient.subscribe('/topic/greetings', function(packet) {
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
	stompClient.send("/app/hello", {}, (JSON.stringify({
		'content' : message
	})));
}

function handleKey(content) {
	var a = content.split(" ");
	if (a[0] == "id") {
		id = a[1];
	} else if (id != a[0]) {
		if (a[1] == 0) {
			pressedO[a[2]] = false;
		} else if (a[1] == 1) {
			pressedO[a[2]] = true;
		} else {
			playerXO = parseInt(a[2]);
			playerYO = parseInt(a[3]);
			cursorXO = parseInt(a[4]);
			cursorYO = parseInt(a[5]);
			healthO = parseInt(a[6]);

		}
	}
}