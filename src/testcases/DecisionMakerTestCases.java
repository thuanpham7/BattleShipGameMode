package testcases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

import battleship.DecisionMaker;
import battleship.PegType;
import battleship.Ship;
import battleship.ShipBoard;
import battleship.Shiptype;
import battleship.AILevel;

/**
 * Test cases for the DecisionMaker class
 * 
 * @author CSCI142 Students
 *
 */
public class DecisionMakerTestCases 
{
	private DecisionMaker myCheatDecisionMaker;
	private DecisionMaker myIntelligentDecisionMaker;
	
	@Before
	public void setUp() throws Exception
	{
		myCheatDecisionMaker = new DecisionMaker(AILevel.CHEAT_MODE, new ShipBoard());
		myIntelligentDecisionMaker = new DecisionMaker(AILevel.INTELLIGENT_MODE, new ShipBoard());
	}

	@Test
	/**
	 * Ensure cheat mode works properly
	 */
	public void testCheatFirstMove()
	{
		boolean valid = myCheatDecisionMaker.getShipBoard().placePeg(myCheatDecisionMaker.nextMove());
		assertTrue("First cheat should be valid", valid);
		PegType type = myCheatDecisionMaker.getShipBoard().getLastMove();
		assertEquals("First cheat should be a hit", type.getPegType(), "Red");
	}
	
	@Test
	/**
	 * Ensure cheat mode works properly
	 */
	public void testCheatSinkAll()
	{
		for(int i = 0; i < 16; ++i)
		{
			myCheatDecisionMaker.getShipBoard().placePeg(myCheatDecisionMaker.nextMove());
		}
		assertFalse(myCheatDecisionMaker.getShipBoard().allShipsSunk());
		myCheatDecisionMaker.getShipBoard().placePeg(myCheatDecisionMaker.nextMove());
		assertTrue(myCheatDecisionMaker.getShipBoard().allShipsSunk());
	}
	
	/**
	 * Intelligence Test
	 * If every spot is guessed along a ship except for one, and one directly above it
	 * then the next guess will be the last hit
	 */
	@Test
	public void testPlaceLastPointIntelligent() {
		myIntelligentDecisionMaker = new DecisionMaker(AILevel.INTELLIGENT_MODE, new ShipBoard());

		HashMap<String, Vector<Point>> map = new HashMap<>();
		map.put("Battleship", new Vector<Point>());
		map.get("Battleship").add(new Point(1,2));
		map.get("Battleship").add(new Point(1,3));
		map.get("Battleship").add(new Point(1,4));
		myIntelligentDecisionMaker.setHashMap(map);
		
		myIntelligentDecisionMaker.setLastMove(new Point(1,1));
		assertEquals("Next Move should be (1,5)", new Point(1,5), myIntelligentDecisionMaker.nextMove());

		/* could have done this too */
//		Point next = myIntelligentDecisionMaker.nextMove();
//		boolean valid = false;
//		if ((next.x == 1 && next.y == 1) || (next.x == 1 && next.y == 5)) {
//			valid = true;
//		}
//		assertTrue("Should be valid move", valid);
	}

	/**
	 * The AI is intelligent
	 * Test if one spot is hit, the next hit should be directly above, below,
	 * left, or right
	 */
	@Test
	public void testSecondMoveIntelligent() {
		myIntelligentDecisionMaker = new DecisionMaker(AILevel.INTELLIGENT_MODE, null);
		
		HashMap<String, Vector<Point>> map = new HashMap<>();
		map.put("Battleship", new Vector<Point>());
		map.get("Battleship").add(new Point(5,4));
		myIntelligentDecisionMaker.setHashMap(map);
		
		boolean valid;
		if(myIntelligentDecisionMaker.nextMove().equals(new Point(5,3)) || 
				myIntelligentDecisionMaker.nextMove().equals(new Point(5,4)) ||
				myIntelligentDecisionMaker.nextMove().equals(new Point(4,4)) ||
				myIntelligentDecisionMaker.nextMove().equals(new Point(6,4))) {
			valid = true;
		}
		else {
			valid = false;
		}
		assertTrue("Second point guessed should be next to last hit", valid);
	}
	
	/**
	 * The AI Level is Intelligent
	 * 2 ships are side by side
	 * if one ship is sunk, it should remember to go back to the original ship
	 */
	@Test
	public void testTwoShipsOneSunk() {
		myIntelligentDecisionMaker = new DecisionMaker(AILevel.INTELLIGENT_MODE, null);
		
		HashMap<String, Vector<Point>> map = new HashMap<>();
		map.put("Cruiser", new Vector<Point>());
		map.get("Cruiser").add(new Point(7,3));
		
		map.put("Submarine", new Vector<Point>());
		map.get("Submarine").add(new Point(6,3));
		map.get("Submarine").add(new Point(6,4));
		map.get("Submarine").add(new Point(6,5));
		myIntelligentDecisionMaker.setHashMap(map);
		
		myIntelligentDecisionMaker.setLastMove(new Point(6, 5));
		boolean valid;
		if(myIntelligentDecisionMaker.nextMove().equals(new Point(7,2)) || 
				myIntelligentDecisionMaker.nextMove().equals(new Point(7,4)) ||
				myIntelligentDecisionMaker.nextMove().equals(new Point(7,2)) ||
				myIntelligentDecisionMaker.nextMove().equals(new Point(8,3))) {
			valid = true;
		}
		else {
			valid = false;
		}
		assertTrue("Should return to previous unsunken ship", valid);
	}
	
	/**
	 * The AI Level is Intelligent
	 * 2 are guessed in a line, the next guess will be one directly above or below
	 */
	@Test
	public void testVerticalShipInMiddle() {
		myIntelligentDecisionMaker = new DecisionMaker(AILevel.INTELLIGENT_MODE, null);
		
		HashMap<String, Vector<Point>> map = new HashMap<>();
		map.put("Submarine", new Vector<Point>());
		map.get("Submarine").add(new Point(6,3));
		map.get("Submarine").add(new Point(6,4));
		myIntelligentDecisionMaker.setHashMap(map);
		
		boolean valid;
		if(myIntelligentDecisionMaker.nextMove().equals(new Point(6,2)) || 
				myIntelligentDecisionMaker.nextMove().equals(new Point(6,5))) {
			valid = true;
		}
		else {
			valid = false;
		}
		assertTrue("Next guess should be above or below", valid);
	}

	/**
	 * The AI Level is Intelligent
	 * 2 are guessed in a line, the next guess will be one directly left or right
	 */
	@Test
	public void testHorizontalShipInMiddle() {
		myIntelligentDecisionMaker = new DecisionMaker(AILevel.INTELLIGENT_MODE, null);
		
		HashMap<String, Vector<Point>> map = new HashMap<>();
		map.put("Submarine", new Vector<Point>());
		map.get("Submarine").add(new Point(3,6));
		map.get("Submarine").add(new Point(4,6));
		myIntelligentDecisionMaker.setHashMap(map);
		
		boolean valid;
		if(myIntelligentDecisionMaker.nextMove().equals(new Point(2,6)) || 
				myIntelligentDecisionMaker.nextMove().equals(new Point(5,6))) {
			valid = true;
		}
		else {
			valid = false;
		}
		assertTrue("Next guess should be left or right", valid);
	}

	/**
	 * The AI Level is Intelligent
	 * the ship is against the upper left wall, there is only one possible guess
	 */
	@Test
	public void testVerticalUpperLeft() {
		myIntelligentDecisionMaker = new DecisionMaker(AILevel.INTELLIGENT_MODE, null);
				
		HashMap<String, Vector<Point>> map = new HashMap<>();
		map.put("Submarine", new Vector<Point>());
		map.get("Submarine").add(new Point(0,0));
		map.get("Submarine").add(new Point(0,1));
		myIntelligentDecisionMaker.setHashMap(map);
		
		myIntelligentDecisionMaker.setLastMove(new Point(0, 0));
		assertEquals("The next guess should be (0,2)", new Point(0,2), myIntelligentDecisionMaker.nextMove());

		myIntelligentDecisionMaker.setLastMove(new Point(0, 1));
		assertEquals("The next guess should be (0,2)", new Point(0,2), myIntelligentDecisionMaker.nextMove());
	}

	/**
	 * The AI Level is Intelligent
	 * the ship is against the lower left wall, there is only one possible guess
	 */
	@Test
	public void testVerticalLowerLeft() {
		myIntelligentDecisionMaker = new DecisionMaker(AILevel.INTELLIGENT_MODE, null);
				
		HashMap<String, Vector<Point>> map = new HashMap<>();
		map.put("Submarine", new Vector<Point>());
		map.get("Submarine").add(new Point(0,8));
		map.get("Submarine").add(new Point(0,9));
		myIntelligentDecisionMaker.setHashMap(map);
		
		myIntelligentDecisionMaker.setLastMove(new Point(0, 8));
		assertEquals("The next guess should be (0,7)", new Point(0,7), myIntelligentDecisionMaker.nextMove());
		
		myIntelligentDecisionMaker.setLastMove(new Point(0, 9));
		assertEquals("The next guess should be (0,7)", new Point(0,7), myIntelligentDecisionMaker.nextMove());
	}

	/**
	 * The AI Level is Intelligent
	 * the ship is against the wall, there is only one possible guess
	 */
	@Test
	public void testVerticalUpperRight() {
		myIntelligentDecisionMaker = new DecisionMaker(AILevel.INTELLIGENT_MODE, null);
				
		HashMap<String, Vector<Point>> map = new HashMap<>();
		map.put("Submarine", new Vector<Point>());
		map.get("Submarine").add(new Point(9,0));
		map.get("Submarine").add(new Point(9,1));
		myIntelligentDecisionMaker.setHashMap(map);
		
		myIntelligentDecisionMaker.setLastMove(new Point(9, 0));
		assertEquals("The next guess should be (9,2)", new Point(9,2), myIntelligentDecisionMaker.nextMove());
		
		myIntelligentDecisionMaker.setLastMove(new Point(9, 1));
		assertEquals("The next guess should be (9,2)", new Point(9,2), myIntelligentDecisionMaker.nextMove());
	}

	/**
	 * The AI Level is Intelligent
	 * the ship is against the wall, there is only one possible guess
	 */
	@Test
	public void testVerticalLowerRight() {
		myIntelligentDecisionMaker = new DecisionMaker(AILevel.INTELLIGENT_MODE, null);
				
		HashMap<String, Vector<Point>> map = new HashMap<>();
		map.put("Submarine", new Vector<Point>());
		map.get("Submarine").add(new Point(9,8));
		map.get("Submarine").add(new Point(9,9));
		myIntelligentDecisionMaker.setHashMap(map);
		
		myIntelligentDecisionMaker.setLastMove(new Point(9,8));
		assertEquals("The next guess should be (9,7)", new Point(9,7), myIntelligentDecisionMaker.nextMove());
		
		myIntelligentDecisionMaker.setLastMove(new Point(9,9));
		assertEquals("The next guess should be (9,7)", new Point(9,7), myIntelligentDecisionMaker.nextMove());
	}

	/**
	 * The AI Level is Intelligent
	 * the ship is against the top left wall, there is only one possible guess
	 */
	@Test
	public void testHorizontalUpperLeft() {
		myIntelligentDecisionMaker = new DecisionMaker(AILevel.INTELLIGENT_MODE, null);
				
		HashMap<String, Vector<Point>> map = new HashMap<>();
		map.put("Submarine", new Vector<Point>());
		map.get("Submarine").add(new Point(0,0));
		map.get("Submarine").add(new Point(1,0));
		myIntelligentDecisionMaker.setHashMap(map);
		
		myIntelligentDecisionMaker.setLastMove(new Point(0, 0));
		assertEquals("The next guess should be (2,0)", new Point(2,0), myIntelligentDecisionMaker.nextMove());
		
		myIntelligentDecisionMaker.setLastMove(new Point(1, 0));
		assertEquals("The next guess should be (2,0)", new Point(2,0), myIntelligentDecisionMaker.nextMove());
	}

	/**
	 * The AI Level is Intelligent
	 * the ship is against the lower left wall, there is only one possible guess
	 */
	@Test
	public void testHorizontalUpperRight() {
		myIntelligentDecisionMaker = new DecisionMaker(AILevel.INTELLIGENT_MODE, null);
				
		HashMap<String, Vector<Point>> map = new HashMap<>();
		map.put("Submarine", new Vector<Point>());
		map.get("Submarine").add(new Point(8,0));
		map.get("Submarine").add(new Point(9,0));
		myIntelligentDecisionMaker.setHashMap(map);
		
		myIntelligentDecisionMaker.setLastMove(new Point(8,0));
		assertEquals("The next guess should be (7,0)", new Point(7,0), myIntelligentDecisionMaker.nextMove());
		
		myIntelligentDecisionMaker.setLastMove(new Point(9,0));
		assertEquals("The next guess should be (7,0)", new Point(7,0), myIntelligentDecisionMaker.nextMove());
	}

	/**
	 * The AI Level is Intelligent
	 * the ship is against the wall, there is only one possible guess
	 */
	@Test
	public void testHorizontalLowerLeft() {
		myIntelligentDecisionMaker = new DecisionMaker(AILevel.INTELLIGENT_MODE, null);
				
		HashMap<String, Vector<Point>> map = new HashMap<>();
		map.put("Submarine", new Vector<Point>());
		map.get("Submarine").add(new Point(0,9));
		map.get("Submarine").add(new Point(1,9));
		myIntelligentDecisionMaker.setHashMap(map);
		
		myIntelligentDecisionMaker.setLastMove(new Point(0,9));
		assertEquals("The next guess should be (2,9)", new Point(2,9), myIntelligentDecisionMaker.nextMove());
		
		myIntelligentDecisionMaker.setLastMove(new Point(1,9));
		assertEquals("The next guess should be (2,9)", new Point(2,9), myIntelligentDecisionMaker.nextMove());
	}

	/**
	 * The AI Level is Intelligent
	 * the ship is against the wall, there is only one possible guess
	 */
	@Test
	public void testHorizontalLowerRight() {
		myIntelligentDecisionMaker = new DecisionMaker(AILevel.INTELLIGENT_MODE, null);
				
		HashMap<String, Vector<Point>> map = new HashMap<>();
		map.put("Submarine", new Vector<Point>());
		map.get("Submarine").add(new Point(8,9));
		map.get("Submarine").add(new Point(9,9));
		myIntelligentDecisionMaker.setHashMap(map);
		
		myIntelligentDecisionMaker.setLastMove(new Point(8,9));
		assertEquals("The next guess should be (7,9)", new Point(7,9), myIntelligentDecisionMaker.nextMove());
		
		myIntelligentDecisionMaker.setLastMove(new Point(9,9));
		assertEquals("The next guess should be (7,9)", new Point(7,9), myIntelligentDecisionMaker.nextMove());
	}

}
