package battleship;

import java.awt.Point;
import java.util.HashMap;
import java.util.Vector;

public class DecisionMaker {
	private ShipBoard myShipBoard;
	private Point myLastMove;
	private AILevel mode;
	private Point myNextMove;
	private Vector<Point> myMove;
	private HashMap<String, Vector<Point>> myHashMap;

	public DecisionMaker(AILevel level, ShipBoard board)
	{
		myShipBoard = board;
		myHashMap = new HashMap<>();
		mode = level;
	}

	public Point nextMove() {
		int length = 0;
		if (mode == AILevel.INTELLIGENT_MODE) {
			if ((myHashMap.containsKey("Submarine")) || (myHashMap.containsKey("Cruiser"))) {
				length = 2;
				myMove = myHashMap.get("Submarine");
				if ((myMove.get(1).y == myMove.get(0).y) && (myMove.get(length-1).x > myMove.get(0).x)){
					if ((myMove.get(1).x)+1<10) {
						myNextMove = new Point((myMove.get(1).x)+1, myMove.get(1).y);
					}else
						myNextMove = new Point((myMove.get(0).x)-1, myMove.get(1).y);
				} else if ((myMove.get(1).x == myMove.get(0).x) && (myMove.get(1).y > myMove.get(0).y)){
					if ((myMove.get(1).y)+1<10) {
						myNextMove = new Point((myMove.get(1).x), myMove.get(1).y+1);
					}else {
						myNextMove = new Point((myMove.get(1).x), (myMove.get(0).y)-1);
					}
				}
			} else if ((myHashMap.containsKey("BattleShip"))) {
				myMove = myHashMap.get("BattleShip");
			}
		} else if (mode == AILevel.CHEAT_MODE) {
			myNextMove = new Point(1,1);
		}
		return myNextMove;
	}

	public void setLastMove(Point point) {
		myLastMove = point;
	}

	public Point getLastMove() {
		return myLastMove;
	}

	public ShipBoard getShipBoard() {

		return myShipBoard;
	}

	public void setHashMap(HashMap<String, Vector<Point>> map) {
		myHashMap = map;
	}

	public HashMap<String, Vector<Point>> getHashMap() {
		return myHashMap;
	}

	public AILevel getLevel() {
		return mode;
	}

}
