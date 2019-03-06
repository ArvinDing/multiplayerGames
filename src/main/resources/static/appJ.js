var stompClient = null;

function connect() {
	var socket = new SockJS('/websocket');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		console.log('Connected: ' + frame);
		stompClient.subscribe('/topic/s2', function(packet) {
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
	stompClient.send("/app/c2", {}, (JSON.stringify({
		'content' : message
	})));
}

function handleKey(content) {
	var a = content.split(" ");
	if (a[0] == "id") {
		if (id == -1&&tempId==a[1])
			id = a[2];
	} else if (a[0] == "quit") {
		if (id == -1) {
			window.location = "lose.html";
			clearInterval(intervalthing);
		}
	} else if (a[0] == "started") {
		if (id == -1&&tempId==a[1])
			id = a[2];
		start = true;
		for (var i = 3; i < 1003; i++) {
			rando[i - 3] = a[i];
		}
		clock();
	} else if (a[0] == "win") {
		if (a[1] != id) {
			window.location = "lose.html";
			clearInterval(intervalthing);
		}
	} else if (id != a[0]) {
		other[a[0]] = new Array(a[1], a[2], a[3]);
	}
}