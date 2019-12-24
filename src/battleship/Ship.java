package battleship;

import java.awt.Point;
import java.util.Arrays;
import java.util.Vector;

public class Ship {
	private Point myFrontLocation;
	private Point myBackLocation;
	private int myLength;
	private Vector<Boolean> myHits = new Vector<Boolean>(10);
	private boolean[][] mypegPoint = new boolean[10][10];
	private Shiptype[][] myshipPlace = new Shiptype[10][10];
	private String myship;
	private boolean mySunk;
	private static String myShip;
	private Shiptype myShiptype;
	private int sequence = 0;

	public Ship(Shiptype type, int length) {
		myShiptype = type;
		myLength = length;
	}
	
	


	public boolean placePeg (Point location) {
		boolean declare = true;
		if ( (myHits.isEmpty() == true) || (myHits.firstElement() == false)) {
			for (int i =0; i < 10; i++) {
				myHits.add(false);
			}
			myHits.add(0, true);
		} 
		if (mypegPoint[location.x][location.y] != true) {
			mypegPoint[location.x][location.y] = true;
			if(myshipPlace[location.x][location.y] != null) {
				sequence += 1;
				myHits.set(location.y - myFrontLocation.y,true);  
				
			}
		}else {
			declare = false;
		}
		return declare;
	}
	

	public boolean isSunk() {
		if ((myShiptype == Shiptype.BATTLESHIP) && (sequence == 4)) {
			return true;
		} else if(((myShiptype == Shiptype.CARRIER) && (sequence == 5))) {
			return true;
		} else if ((myShiptype == Shiptype.DESTROYER) && (sequence == 2)) {
			return true;
		} else if( ((myShiptype == Shiptype.CRUISER) || (myShiptype == Shiptype.SUBMARINE)) && (sequence == 4)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean setFrontLocation (Point location) {
		// Testing bad location based on grid
		if ((location.x <= -1) || (location.y <=-1)) {
			return false;
		} else if ((location.x >= 10) || (location.y >=10)) {
			return false;
		} if (myshipPlace[location.x][location.y] != null) {
			return false;
		}
		else {
			myFrontLocation = location;
			myshipPlace[location.x][location.y] = myShiptype;
			return true;
		}
		// Testing bad location based on Ship length


	}

	public boolean setBackLocation (Point location) {
		// Testing bad location based on grid
		if ((location.x <= -1) || (location.y <=-1)) {
			return false;
		} if ((location.x >= 10) || (location.y >=10)) {
			return false;
		} if (myshipPlace[location.x][location.y] != null) {
			return false;
		}
		//Testing bad location based on Ship length 
		else {
			myBackLocation = location;
			if ( (((myFrontLocation.x - (myLength-1) <= location.x) || (myFrontLocation.x + (myLength-1) <= location.x)) && (myFrontLocation.y == location.y))
				||   (((myFrontLocation.y - (myLength-1) <= location.y) || (myFrontLocation.y + (myLength-1) <= location.y)) && (myFrontLocation.x == location.x))) {
					for (int i = 0; i <= myLength-1; i++) {
						if ((myFrontLocation.x - location.x != 0) && (myFrontLocation.y - location.y == 0)) {
							if (myFrontLocation.x - location.x < 0) {
								myshipPlace[myFrontLocation.x+i][location.y] = myShiptype;
							}else if (myFrontLocation.x - location.x > 0) {
								myshipPlace[myFrontLocation.x - i][location.y] = myShiptype;
							}
						} else if((myFrontLocation.y - location.y != 0) && (myFrontLocation.x - location.x == 0)) {
							if (myFrontLocation.y - location.y < 0) {
								myshipPlace[myFrontLocation.x][location.y-i] = myShiptype;
							}else if (myFrontLocation.x - location.x > 0) {
								myshipPlace[myFrontLocation.x][location.y+i] = myShiptype;
							}
						}
					}
			
			return true;
		} else {
			return false;
		}
	}
}


	public Point getFrontLocation() {
		return myFrontLocation;
	}

	public Point getBackLocation() {
		return myBackLocation;
	}

	public Shiptype getShiptype() {
		return myShiptype;
	}

	public void setShiptype(Shiptype type) {
		myShiptype = type;
	}

	public int getLength() {
		int length = 0;
		if (myShiptype == Shiptype.BATTLESHIP) {
			length =  4;
			myShip = "BattleShip";
		} else if (myShiptype == Shiptype.DESTROYER) {
			length =  2;
			myShip = "Destroyer";
		} else if (myShiptype == Shiptype.CRUISER) {
			length = 3;
			myShip = "Cruiser";
		} else if (myShiptype == Shiptype.CARRIER) {	
			length = 5;
			myShip = "Carrier";
		} else if (myShiptype == Shiptype.SUBMARINE) {
			length = 3;
			myShip = "Submarine";
		}
		return length;
	}

	public Vector<Boolean> getHits(){
		if ( (myHits.isEmpty() == true) || (myHits.firstElement() == false)) {
				myHits.add(false);
		} else {
			return myHits;
		}
		return myHits;
	}
}
