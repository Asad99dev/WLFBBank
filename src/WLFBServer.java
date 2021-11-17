/* Original code from ActionServer by Simon Taylor,
 * modifications made by Asad Talha to create bank application.
 */


import java.net.*;
import java.io.*;

public class WLFBServer {

	public static void main(String[] args) throws IOException {

		ServerSocket WLFBServerSocket = null;
		boolean listening = true;
		String WLFBServerName = "WLFBServer";
		int WLFBServerNumber = 4545;

		double initialBalance = 1000;

		WLFBState WLFBStateObject = new WLFBState(initialBalance);

		try {
			WLFBServerSocket = new ServerSocket(WLFBServerNumber);
		} catch (IOException e) {
			System.err.println("Could not start " + WLFBServerName + " specified port.");
			System.exit(-1);
		}

		System.out.println("New " + WLFBServerName + " started");

		while (listening) {

			new WLFBServerThread(WLFBServerSocket.accept(), "WLFBServerThread1", WLFBStateObject).start();
			new WLFBServerThread(WLFBServerSocket.accept(), "WLFBServerThread2", WLFBStateObject).start();
			new WLFBServerThread(WLFBServerSocket.accept(), "WLFBServerThread3", WLFBStateObject).start();

			System.out.println("New " + WLFBServerName + " thread started");
		}

		WLFBServerSocket.close();

	}

}
