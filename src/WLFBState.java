/* Original code from ActionServer by Simon Taylor,
 * modifications made by Asad Talha to create bank application.
 */

public class WLFBState {

	private WLFBState mySharedObj;
	private String myThreadName;
	private double AccountOne, AccountTwo, AccountThree;
	private boolean accessing = false; // true a thread has a lock, false otherwise
	private int threadsWaiting = 0; // number of waiting writers

	WLFBState(double balance) {
		AccountOne = balance;
		AccountTwo = balance;
		AccountThree = balance;
	}

	public synchronized void acquireLock() throws InterruptedException {
		Thread me = Thread.currentThread(); // get a ref to the current thread
		System.out.println(me.getName() + " is attempting to acquire a lock!");
		++threadsWaiting;
		while (accessing) { // while someone else is accessing or threadsWaiting > 0
			System.out.println(me.getName() + " waiting to get a lock as someone else is accessing...");
			// wait for the lock to be released - see releaseLock() below
			wait();
		}
		// nobody has got a lock so get one
		--threadsWaiting;
		accessing = true;
		System.out.println(me.getName() + " got a lock!");
	}

	// Releases a lock to when a thread is finished

	public synchronized void releaseLock() {
		// release the lock and tell everyone
		accessing = false;
		notifyAll();
		Thread me = Thread.currentThread(); // get a ref to the current thread
		System.out.println(me.getName() + " released a lock!");
	}

	public synchronized String processInput(String myThreadName, String theInput) {
		System.out.println(myThreadName + " received " + theInput);
		String theOutput = null;

		if (theInput.equalsIgnoreCase("1")) {
			// Correct request

			int money = 200;

			switch (myThreadName) {

			case "WLFBServerThread1":

				AccountOne = AccountOne + money;

				theOutput = "AccountOne balance is now " + AccountOne;
				break;

			case "WLFBServerThread2":

				AccountTwo = AccountTwo + money;

				theOutput = "AccountTwo balance is now " + AccountTwo;
				break;

			case "WLFBServerThread3":

				AccountTwo = AccountThree + money;

				theOutput = "AccountThree balance is now " + AccountThree;
				break;

			}

		} else if (theInput.equalsIgnoreCase("2")) {

			int money = 200;

			switch (myThreadName) {

			case "WLFBServerThread1":

				AccountOne = AccountOne - money;

				theOutput = "AccountOne balance is now " + AccountOne;
				break;

			case "WLFBServerThread2":

				AccountTwo = AccountTwo - money;

				theOutput = "AccountTwo balance is now " + AccountTwo;
				break;

			case "WLFBServerThread3":

				AccountThree = AccountThree - money;

				theOutput = "AccountThree balance is now " + AccountThree;
				break;

			}

		} else if (theInput.equalsIgnoreCase("3")) {

			int transferMoney = 200;

			switch (myThreadName) {

			case "WLFBServerThread1":

				AccountOne = AccountOne - transferMoney;
				AccountTwo = AccountTwo + transferMoney;

				theOutput = "AccountTwo balance is now " + AccountTwo + " and AccountOne balance is " + AccountOne;
				break;

			case "WLFBServerThread2":

				AccountTwo = AccountTwo - transferMoney;
				AccountThree = AccountThree + transferMoney;

				theOutput = "AccountThree balance is now " + AccountThree + " and AccountTwo balance is " + AccountTwo;
				break;

			case "WLFBServerThread3":

				AccountThree = AccountThree - transferMoney;
				AccountOne = AccountOne + transferMoney;

				theOutput = "AccountOne balance is now " + AccountOne + " and AccountThree balance is " + AccountThree;
				break;

			}

		} else if (theInput.equalsIgnoreCase("4")) {

			switch (myThreadName) {

			case "WLFBServerThread1":

				theOutput = "AccountOne balance is " + AccountOne;
				break;

			case "WLFBServerThread2":

				theOutput = "AccountTwo balance is " + AccountTwo;
				break;

			case "WLFBServerThread3":

				theOutput = "AccountThree balance is " + AccountThree;
				break;

			}

		}

		else { // incorrect request
			theOutput = myThreadName + " received incorrect request - only understand 1, 2, 3 or 4";

		}

		// Return the output message to the ActionServer
		System.out.println(theOutput);
		return theOutput;
	}

}
