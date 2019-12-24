package battleship;


import java.awt.Point;
import java.util.Vector;

public class ShipBoard {
	public int size;
	private PegType[][] myGrid = new PegType[10][10];
	private PegType myLastMove;
	private Vector<Boolean> newShip = new Vector<Boolean>(10);
	private boolean[][] pegPoint = new boolean[10][10];
	private Shiptype[][] myShipPlace = new Shiptype[10][10];
	private int sequence = 0;
	private Shiptype myType;
	private int sunk = 0;
	private DecisionMaker Level;
	
	
	public ShipBoard() {
	}

	public ShipBoard(PegType[][] myGrid, int length) {
		this.myGrid = myGrid;
		length = size;
		
	}

	public boolean placePeg (Point point) {
		boolean declare = true;
		if (pegPoint[point.x][point.y] != true) {
			pegPoint[point.x][point.y] = true;
			if(myShipPlace[point.x][point.y] != null) {
				myLastMove = PegType.RED; 
				myGrid[point.x][point.y] = PegType.RED;
				sequence += 1;
				if ((myShipPlace[point.x][point.y] == Shiptype.BATTLESHIP) && (sequence == 4)) {
					sunk += 1;
					sequence = 0;
				} else if((myShipPlace[point.x][point.y] == Shiptype.CARRIER) && (sequence == 5)) {
					sunk += 1;
					sequence = 0;
				} else if ((myShipPlace[point.x][point.y] == Shiptype.DESTROYER) && (sequence == 2)) {
					sunk +=1;
					sequence = 0;
				} else if (((myShipPlace[point.x][point.y] == Shiptype.SUBMARINE) || (myShipPlace[point.x][point.y] == Shiptype.CRUISER)) && (sequence == 3)) {
					sunk += 1;
					sequence = 0;
				}
				newShip.add(true);
			} else {
				myLastMove = PegType.WHITE;
				myGrid[point.x][point.y] = PegType.WHITE;
				newShip.add(true);
			}
		}else {
			declare = false;
		}
		return declare;
	}

	public boolean isValidMove(Point point) {
		if ((point.x > 9) || (point.y>9) ||(point.x < 0) || (point.y < 0)) {
			return false;
		} if (pegPoint[point.x][point.y] == true) {
			return false;
		}
		return true;
	}

	public boolean placeShips() {
		return true;
	}

	public void resetBoard() {
		for(int i = 0; i < 10; ++i)
		{
			for(int j = 0; j < 10; ++j)
			{
				myGrid[i][j] = PegType.NONE;
			}
		}
	}



	public boolean placeShip(Shiptype type, Point front, Point back) {
		boolean declare = true;
		int length;
		if ((front.x < 0) || (front.y<0) || (back.x > 10) || (back.y > 10) ||
				(back.x < 0) || (back.y < 0) || (front.x > 10) || (front.y >10)){
			declare = false;
		}
		else {
			if (type == Shiptype.BATTLESHIP) {
				length = 4;
			} else if (type == Shiptype.CARRIER) {
				length = 5;
			} else if ((type == Shiptype.CRUISER) || (type == Shiptype.SUBMARINE))  {
				length = 3;
			} else {
				length = 2;
			}
			if ( ((myShipPlace[front.x][front.y]) != null) || ((myShipPlace[back.x][back.y] != null))) {
				declare = false;
			} else {
				if ( (((front.x - (length-1) <= back.x) || (front.x + (length-1) <= back.x)) && (front.y == back.y))
						||   (((front.y - (length-1) <= back.y) || (front.y + (length-1) <= back.y)) && (front.x == back.x))) {
					for (int i = 0; i <= length-1; i++) {
						if ((front.x - back.x != 0) && (front.y - back.y == 0)) {
							if (front.x - back.x < 0) {
								myShipPlace[front.x+i][back.y] = type;
							} else if (front.x - back.x > 0) {
								myShipPlace[front.x - i][back.y] = type;
							}
						} else if((front.y - back.y != 0) && (front.x - back.x == 0)) {
							if (front.y - back.y < 0) {
								myShipPlace[front.x][back.y-i] = type;
							} else if (front.x - back.x > 0) {
								myShipPlace[front.x][back.y+i] = type;
							}
						}
					}
					declare = true;
				} else {
					declare = false;
				}
			}	
		}
		return declare;
	}

	public int numberShipsSunk(){
		
		return sunk;
	}

	public boolean allShipsSunk() {
		if (sunk == 5) {
			return true;
		} else {
			return false;
		}
	}

	public PegType getLastMove() {
		if (newShip.isEmpty() == true) {
			myLastMove = PegType.NONE;
		} 
		return myLastMove;
	}

	public PegType[][] getGrid(){
		if (myLastMove == null) {
			for(int i = 0; i < 10; ++i)
			{
				for(int j = 0; j < 10; ++j)
				{
					myGrid[i][j] = PegType.NONE;
				}
			}
		} else {
			return myGrid;
		}
		
		return myGrid;
	}

	public Vector<Ship> getShips(){
		return null;
	}

}
