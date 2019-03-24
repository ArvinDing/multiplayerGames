package hello;

import java.util.LinkedList;
import java.util.Random;
import java.util.StringTokenizer;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {
	private boolean[] takenC = new boolean[2];
	private boolean[] takenJ = new boolean[3];
	private LinkedList<Integer> openLawn = new LinkedList<Integer>();
	boolean lawnStarted = false;

	public GameController() {
	}

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public UpdatePacket s(CilentMessage message) throws Exception {
		// Thread.sleep(1000); // simulated delay
		String a = message.getContent();
		StringTokenizer read = new StringTokenizer(a);
		String first = read.nextToken();
		return new UpdatePacket(a);
	}

	@MessageMapping("/lol")
	@SendTo("/topic/giveme")
	public UpdatePacket s1(CilentMessage message) throws Exception {
		String a = message.getContent();
		StringTokenizer read = new StringTokenizer(a);
		String first = read.nextToken();
		if (a.equals("-1")) {
			int curr = 0;
			if (takenC[0] && takenC[1])
				return new UpdatePacket("quit");
			else if (takenC[0])
				curr = 1;
			else if (takenC[1])
				curr = 0;
			takenC[curr] = true;
			// System.out.println(curr + " need");
			return new UpdatePacket("id" + " " + curr);
		} else if (first.equals("404")) {
			int imp = Integer.parseInt(read.nextToken());
			// System.out.println(imp + " done");
			if (imp != -1)
				takenC[imp] = false;

		}
		return new UpdatePacket(a);
	}

	@MessageMapping("/wow")
	@SendTo("/topic/server")
	public UpdatePacket s2(CilentMessage message) throws Exception {
		String a = message.getContent();

		return new UpdatePacket(a);
	}

	private int playerCnt = 0;

	@MessageMapping("/c")
	@SendTo("/topic/s")
	public UpdatePacket s3(CilentMessage message) throws Exception {
		String a = message.getContent();
		return new UpdatePacket(a);
	}

	@MessageMapping("/c1")
	@SendTo("/topic/s1")
	public UpdatePacket s4(CilentMessage message) throws Exception {
		String a = message.getContent();
		StringTokenizer read = new StringTokenizer(a);
		String first = read.nextToken();
		if (a.equals("id")) {
			if (openLawn.size() == 1) {
				lawnStarted = true;
				return new UpdatePacket("id" + " " + openLawn.poll() + " started");
			}
			return new UpdatePacket("id" + " " + openLawn.poll());
		} else if (first.equals("404")) {
			int imp = Integer.parseInt(read.nextToken());
			System.out.println(imp);
			openLawn.add(imp);
		}
		return new UpdatePacket(a);
	}

	// jump game

	public boolean allTaken(boolean[] tkn) {
		for (int i = 0; i < tkn.length; i++) {
			if (!tkn[i])
				return false;
		}
		return true;
	}

	private boolean inProgress = false;

	@MessageMapping("/c2")
	@SendTo("/topic/s2")
	public UpdatePacket s5(CilentMessage message) throws Exception {
		String a = message.getContent();
		StringTokenizer read = new StringTokenizer(a);
		String first = read.nextToken();
		if (first.equals("-1")) {
			int curr = 0;
			if (allTaken(takenJ))
				return new UpdatePacket("quit");
			for (int i = 0; i < takenJ.length; i++) {
				if (!takenJ[i]) {
					curr = i;
					break;
				}
			}

			takenJ[curr] = true;
			System.out.println(curr + " need");
			if (!inProgress && allTaken(takenJ)) {
				inProgress = true;
				String temp = "";
				Random ran = new Random();
				for (int i = 0; i < 1000; i++) {
					temp += " " + ran.nextInt(2);
				}
				String tempId=read.nextToken();
				return new UpdatePacket("started" + " " + tempId  + " " + curr + temp);
			}
			return new UpdatePacket("id" + " " + read.nextToken() + " " + curr);
		} else if (first.equals("404")) {
			int imp = Integer.parseInt(read.nextToken());
			System.out.println(imp + " done");
			if (imp != -1)
				takenJ[imp] = false;

		}
		if (first.equals("win"))
			inProgress = false;
		return new UpdatePacket(a);
	}
	
}
