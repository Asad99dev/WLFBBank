/* Original code from ActionServer by Simon Taylor,
 * modifications made by Asad Talha to create bank application.
 */

import java.net.*;
import java.io.*;

public class WLFBServerThread extends Thread {

	
	  private Socket WLFBSocket = null;
	  private WLFBState myWLFBStateObject;
	  private String myWLFBServerThreadName;


	  	public WLFBServerThread(Socket WLFBSocket, String WLFBServerThreadName, WLFBState WLFBSharedObject) {
		

		  this.WLFBSocket = WLFBSocket;
		  myWLFBStateObject = WLFBSharedObject;
		  myWLFBServerThreadName = WLFBServerThreadName;
		}

	  public void run() {
	    try {
	      System.out.println(myWLFBServerThreadName + "initialising.");
	      PrintWriter out = new PrintWriter(WLFBSocket.getOutputStream(), true);
	      BufferedReader in = new BufferedReader(new InputStreamReader(WLFBSocket.getInputStream()));
	      String inputLine, outputLine;

	      while ((inputLine = in.readLine()) != null) {

	    	  try { 
	    		  myWLFBStateObject.acquireLock();  
	    		  outputLine = myWLFBStateObject.processInput(myWLFBServerThreadName, inputLine);
	    		  out.println(outputLine);
	    		  myWLFBStateObject.releaseLock();  
	    	  } 
	    	  catch(InterruptedException e) {
	    		  System.err.println("Failed to get lock when reading:"+e);
	    	  }
	      }

	       out.close();
	       in.close();
	       WLFBSocket.close();

	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }
	}