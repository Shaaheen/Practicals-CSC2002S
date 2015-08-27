package golfGame;
/*
	Modified by Shaaheen Sacoor
	SCRSHA001

	Object to assign unique individual IDs to an Object
*/

public class golfBall {

	//Instance variables
	private static int noBalls; //The next ID to be set
	private int myID; //ID of golf ball

	/*
	Constructor to create new golf ball object
	 */
	golfBall() {
		//Assign ID
		myID=noBalls;
		//Find out what next ID will
		incID();
	}

	/*
	Returns the ID of the golf ball
	 */
	public int getID() {
		return myID;		
	}

	/*
	Increments ID for the next ID to be set
	 */
	private static void  incID() {
		noBalls++;
	}

	/*
	For Printing purposes
	 */
	public String toString(){
		return getID() + "";
	}
	
}
