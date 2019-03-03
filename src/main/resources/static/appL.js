var stompClient = null;

function connect() {
	var socket = new SockJS('/websocket');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		console.log('Connected: ' + frame);
		stompClient.subscribe('/topic/s1', function(packet) {
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
	stompClient.send("/app/c1", {}, (JSON.stringify({
		'content' : message
	})));
}

function handleKey(content) {
	var a = content.split(" ");
	if (a[0] == "id") {
		if (id == -1)
			id = a[1];
		if(a[2]!=null){
			start();
		}
	} 
}