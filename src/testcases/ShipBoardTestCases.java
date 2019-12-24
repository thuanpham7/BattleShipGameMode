package testcases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import battleship.ShipBoard;
import battleship.Shiptype;
import battleship.PegType;
/**
 * Test cases for the ShipBoard class
 * 
 * @author CSCI142 Students
 *
 */
public class ShipBoardTestCases 
{
	private ShipBoard myShipBoard;
	
	@Before
	public void setUp() throws Exception 
	{
		myShipBoard = new ShipBoard();
	}

	@Test
	/**
	 * Testing invalid peg placements
	 */
	public void invalidPegsTest() 
	{
		assertFalse(myShipBoard.isValidMove(new Point(9,10)));
		assertFalse(myShipBoard.isValidMove(new Point(10,9)));
		assertFalse(myShipBoard.isValidMove(new Point(-1,0)));
		assertFalse(myShipBoard.isValidMove(new Point(0,-1)));
		myShipBoard.placePeg(new Point(5,5));
		assertFalse(myShipBoard.isValidMove(new Point(5,5)));
		myShipBoard.placePeg(new Point(5,6));
		assertFalse(myShipBoard.isValidMove(new Point(5,5)));
		assertTrue(myShipBoard.isValidMove(new Point(5,7)));
		myShipBoard.placePeg(new Point(5,7));
		assertFalse(myShipBoard.isValidMove(new Point(5,7)));
	}
	/**
	 * Testing ship placement validity
	 */
	@Test
	public void shipPlacementTest()
	{
		assertFalse(myShipBoard.placeShip(Shiptype.CRUISER, new Point(-1,0), new Point(3,0)));
		assertFalse(myShipBoard.placeShip(Shiptype.CRUISER, new Point(9,9), new Point(9,11)));
		assertTrue(myShipBoard.placeShip(Shiptype.CRUISER, new Point(1,1), new Point(3,1)));
		assertTrue(myShipBoard.placeShip(Shiptype.CARRIER, new Point (5,5), new Point(5,9)));
		//Ships cannot overlap at any point
		assertFalse(myShipBoard.placeShip(Shiptype.BATTLESHIP, new Point(2,1), new Point(5,1)));
		assertFalse(myShipBoard.placeShip(Shiptype.BATTLESHIP, new Point(1,1), new Point(4,1)));
		assertTrue(myShipBoard.placeShip(Shiptype.SUBMARINE, new Point(4,7), new Point(6,7)));
		assertFalse(myShipBoard.placeShip(Shiptype.SUBMARINE, new Point(5,3), new Point(5,5)));
		assertTrue(myShipBoard.placeShip(Shiptype.SUBMARINE, new Point(5,2), new Point(5,4)));
		//Ships cannot have diagonal orientation
		assertFalse(myShipBoard.placeShip(Shiptype.SUBMARINE, new Point(7,7), new Point(9,9)));
	}
	/**
	 * Testing last move accuracy
	 */
	@Test
	public void lastMoveTest()
	{
		myShipBoard.placeShip(Shiptype.BATTLESHIP, new Point(3,3), new Point(6,3));
		assertEquals(PegType.NONE, myShipBoard.getLastMove());
		myShipBoard.placePeg(new Point(2,3));
		assertEquals(PegType.WHITE, myShipBoard.getLastMove());
		myShipBoard.placePeg(new Point(3,3));
		assertEquals(PegType.RED, myShipBoard.getLastMove());
		myShipBoard.placePeg(new Point(3,4));
		assertEquals(PegType.WHITE, myShipBoard.getLastMove());
		myShipBoard.placePeg(new Point(4,3));
		assertEquals(PegType.RED, myShipBoard.getLastMove());
		myShipBoard.placePeg(new Point(5,3));
		assertEquals(PegType.RED, myShipBoard.getLastMove());
		myShipBoard.placePeg(new Point(6,3));
		assertEquals(PegType.RED, myShipBoard.getLastMove());
		myShipBoard.placePeg(new Point(7,3));
		assertEquals(PegType.WHITE, myShipBoard.getLastMove()); 
	}
	/**
	 * Testing numberShipsSunk and allShipsSunk methods
	 */
	@Test
	public void sunkShipsTest()
	{
		myShipBoard.placeShip(Shiptype.DESTROYER, new Point(1,1), new Point(2,1));
		myShipBoard.placeShip(Shiptype.SUBMARINE, new Point(2,2), new Point(4,2));
		myShipBoard.placeShip(Shiptype.CRUISER, new Point(3,3), new Point(5,3));
		myShipBoard.placeShip(Shiptype.BATTLESHIP, new Point(4,4), new Point(7,4));
		myShipBoard.placeShip(Shiptype.CARRIER, new Point(5,9), new Point(9,9));
		assertEquals(0, myShipBoard.numberShipsSunk());
		assertFalse(myShipBoard.allShipsSunk());
		myShipBoard.placePeg(new Point(1,1));
		myShipBoard.placePeg(new Point(2,1));
		assertEquals(1, myShipBoard.numberShipsSunk());
		assertFalse(myShipBoard.allShipsSunk());
		myShipBoard.placePeg(new Point(2,2));
		myShipBoard.placePeg(new Point(3,2));
		myShipBoard.placePeg(new Point(4,2));
		assertEquals(2, myShipBoard.numberShipsSunk());
		assertFalse(myShipBoard.allShipsSunk());
		myShipBoard.placePeg(new Point(3,3));
		myShipBoard.placePeg(new Point(4,3));
		myShipBoard.placePeg(new Point(5,3));
		assertEquals(3, myShipBoard.numberShipsSunk());
		assertFalse(myShipBoard.allShipsSunk());
		myShipBoard.placePeg(new Point(4,4));
		myShipBoard.placePeg(new Point(5,4));
		myShipBoard.placePeg(new Point(6,4));
		myShipBoard.placePeg(new Point(7,4));
		assertEquals(4, myShipBoard.numberShipsSunk());
		assertFalse(myShipBoard.allShipsSunk());
		myShipBoard.placePeg(new Point(5,9));
		myShipBoard.placePeg(new Point(6,9));
		myShipBoard.placePeg(new Point(7,9));
		myShipBoard.placePeg(new Point(8,9));
		myShipBoard.placePeg(new Point(9,9));
		assertEquals(5, myShipBoard.numberShipsSunk());
		assertTrue(myShipBoard.allShipsSunk());		
	}
	/**
	 * Test to ensure grid is updating accurately
	 */
	@Test
	public void gridTest()
	{
		for(int i = 0; i < 10; ++i)
		{
			for(int j = 0; j < 10; ++j)
			{
				assertEquals(PegType.NONE, myShipBoard.getGrid()[i][j]);
			}
		}
		myShipBoard.placeShip(Shiptype.CRUISER, new Point(1,1), new Point(1,3));
		myShipBoard.placePeg(new Point(2,2));
		assertEquals(PegType.WHITE, myShipBoard.getGrid()[2][2]);
		myShipBoard.placePeg(new Point(1,1));
		assertEquals(PegType.WHITE, myShipBoard.getGrid()[2][2]);
		assertEquals(PegType.RED, myShipBoard.getGrid()[1][1]);
		myShipBoard.placePeg(new Point(1,2));
		assertEquals(PegType.WHITE, myShipBoard.getGrid()[2][2]);
		assertEquals(PegType.RED, myShipBoard.getGrid()[1][1]);
		assertEquals(PegType.RED, myShipBoard.getGrid()[1][2]);
		myShipBoard.placePeg(new Point(1,3));
		assertEquals(PegType.WHITE, myShipBoard.getGrid()[2][2]);
		assertEquals(PegType.RED, myShipBoard.getGrid()[1][1]);
		assertEquals(PegType.RED, myShipBoard.getGrid()[1][2]);
		assertEquals(PegType.RED, myShipBoard.getGrid()[1][3]);
		myShipBoard.resetBoard();
		for(int i = 0; i < 10; ++i)
		{
			for(int j = 0; j < 10; ++j)
			{
				assertEquals(PegType.NONE, myShipBoard.getGrid()[i][j]);
			}
		}
	}
	/**
	 * Test to ensure resetBoard works properly
	 */
	@Test
	public void resetBoardTest()
	{
		for(int i = 0; i < 10; ++i)
		{
			for(int j = 0; j < 10; ++j)
			{
				assertEquals(PegType.NONE, myShipBoard.getGrid()[i][j]);
			}
		}
		myShipBoard.placePeg(new Point(2,4));
		assertEquals(PegType.WHITE, myShipBoard.getGrid()[2][4]);
		myShipBoard.placeShip(Shiptype.DESTROYER, new Point(1,1), new Point(1,2));
		myShipBoard.placePeg(new Point(1,1));
		assertEquals(PegType.RED, myShipBoard.getGrid()[1][1]);
		myShipBoard.resetBoard();
		for(int i = 0; i < 10; ++i)
		{
			for(int j = 0; j < 10; ++j)
			{
				assertEquals(PegType.NONE, myShipBoard.getGrid()[i][j]);
			}
		}
	}
	/**
	 * Test to ensure placeShips works properly
	 */
	@Test
	public void placeShipsTest()
	{
		myShipBoard.placeShips();
		Point myTempFronts[] = new Point[5];
		Point myTempBacks[] = new Point[5];
		for(int i = 0; i < 5; ++i)
		{
			myTempFronts[i] = myShipBoard.getShips().get(i).getFrontLocation();
			myTempBacks[i] = myShipBoard.getShips().get(i).getBackLocation();
			assertNotNull(myTempFronts[i]);
			assertNotNull(myTempBacks[i]);
		}
		myShipBoard.resetBoard();
		for(int i = 0; i < 5; ++i)
		{
			assertNull(myShipBoard.getShips().get(i).getFrontLocation());	
			assertNull(myShipBoard.getShips().get(i).getBackLocation());	
		}
	}	
}
