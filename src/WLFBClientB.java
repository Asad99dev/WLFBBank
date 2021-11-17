/* Original code from ActionServer by Simon Taylor,
 * modifications made by Asad Talha to create bank application.
 */

import java.io.*;
import java.net.*;

public class WLFBClientB {
	public static void main(String[] args) throws IOException {

		// Set up the socket, in and out variables

		Socket WLFBClientSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		int WLFBSocketNumber = 4545;
		String WLFBServerName = "localhost";
		String WLFBClientID = "WLFBClientB";

		try {
			WLFBClientSocket = new Socket(WLFBServerName, WLFBSocketNumber);
			out = new PrintWriter(WLFBClientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(WLFBClientSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: localhost ");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: " + WLFBSocketNumber);
			System.exit(1);
		}

		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String fromServer;
		String fromUser;

		System.out.println("Initialised " + WLFBClientID + " client and IO connections");

		System.out.println("Menu");
		System.out.println("[1] Add money to your account");
		System.out.println("[2] Subtract money from your account");
		System.out.println("[3] Transfer money to another account");
		System.out.println("[4] Check your account balance");

		while (true) {

			fromUser = stdIn.readLine();
			if (fromUser != null) {
				System.out.println(WLFBClientID + " sending " + fromUser + " to WLFBServer");
				out.println(fromUser);
			}
			fromServer = in.readLine();
			System.out.println(WLFBClientID + " received " + fromServer + " from WLFBServer");
		}

	}
}
