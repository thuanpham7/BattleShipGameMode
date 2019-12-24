package testcases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import battleship.Ship;
import battleship.Shiptype;

/**
 * Test cases for the Ship class
 * 
 * @author Thuan Pham
 *
 */

public class ShipTestCases 
{
	private Ship myDestroyer;
	private Ship myCruiser;
	private Ship mySubmarine;
	private Ship myBattleship;
	private Ship myCarrier;
	
	@Before
	public void setUp() throws Exception
	{
		myDestroyer = new Ship(Shiptype.DESTROYER, 2);
		myCruiser = new Ship(Shiptype.CRUISER, 3);
		mySubmarine = new Ship(Shiptype.SUBMARINE, 3);
		myBattleship = new Ship(Shiptype.BATTLESHIP, 4);
		myCarrier = new Ship(Shiptype.CARRIER, 5);
	}

	/**
	 * Test to ensure proper ship lengths
	 */
	@Test
	public void lengthTest()
	{
		assertEquals(2, myDestroyer.getLength());
		assertEquals(3, myCruiser.getLength());
		assertEquals(3, mySubmarine.getLength());
		assertEquals(4, myBattleship.getLength());
		assertEquals(5, myCarrier.getLength());
	}
	
	/**
	 * Test to ensure that setFrontLocation and setBackLocation are working properly
	 */
	@Test
	public void locationSetTest()
	{
		assertTrue(myDestroyer.setFrontLocation(new Point (1,1)));
		assertTrue(myDestroyer.setBackLocation(new Point (2,1)));
		//assertFalse(myCarrier.setFrontLocation(new Point(1,1)));
		assertTrue(myCarrier.setFrontLocation(new Point(3,1)));
		assertFalse(myCarrier.setBackLocation(new Point(3,-3)));
		assertTrue(myCarrier.setBackLocation(new Point(7,1)));
		assertFalse(myCruiser.setFrontLocation(new Point(9,-1)));
		assertFalse(myCruiser.setBackLocation(new Point(9,-1)));
		assertTrue(myCruiser.setFrontLocation(new Point(9,1)));
		assertTrue(myCruiser.setBackLocation(new Point(9,3)));
		assertFalse(mySubmarine.setFrontLocation(new Point(9,11)));
		assertFalse(mySubmarine.setFrontLocation(new Point(11,9)));
		assertFalse(mySubmarine.setFrontLocation(new Point(1,11)));
		assertFalse(mySubmarine.setBackLocation(new Point(9,12)));
		assertFalse(mySubmarine.setBackLocation(new Point(12,9)));
		assertFalse(mySubmarine.setBackLocation(new Point(1,12)));
	}
	
	/**
	 * Test to ensure a peg can't be placed at the same location on a ship twice
	 */
	@Test
	public void duplicatePegTest() 
	{
		myCruiser.setFrontLocation(new Point(1,1));
		myCruiser.setBackLocation(new Point(1,3));
		assertTrue(myCruiser.placePeg(new Point(1,1)));
		assertFalse(myCruiser.placePeg(new Point(1,1)));
		assertTrue(myCruiser.placePeg(new Point (1,2)));
		assertFalse(myCruiser.placePeg(new Point(1,2)));
	}
	
	/**
	 * Test to make sure isSunk is working properly
	 */
	@Test
	public void isSunkTest()
	{
		myDestroyer.setFrontLocation(new Point(1,1));
		myDestroyer.setBackLocation(new Point(1,2));
		assertFalse(myDestroyer.isSunk());
		myDestroyer.placePeg(new Point(1,1));
		assertFalse(myDestroyer.isSunk());
		myDestroyer.placePeg(new Point(1,2));
		assertTrue(myDestroyer.isSunk());
		myBattleship.setFrontLocation(new Point(2,2));
		myBattleship.setBackLocation(new Point(2,5));
		assertFalse(myBattleship.isSunk());
		assertTrue(myDestroyer.isSunk());
		myBattleship.placePeg(new Point(2,2));
		myBattleship.placePeg(new Point(2,3));
		myBattleship.placePeg(new Point(2,4));
		assertFalse(myBattleship.isSunk());
		assertTrue(myDestroyer.isSunk());
		myBattleship.placePeg(new Point(2,5));
		assertTrue(myBattleship.isSunk());
		assertTrue(myDestroyer.isSunk());
	}
	
	/**
	 * Test to make sure myHits is working properly
	 */
	@Test
	public void myHitsTest()
	{
		myCarrier.setFrontLocation(new Point(1,1));
		myCarrier.setBackLocation(new Point(1,5));
		for(int i = 0; i < 5; ++i) {
			assertFalse(myCarrier.getHits().get(i));
		}
		myCarrier.placePeg(new Point(1,1));
		assertTrue(myCarrier.getHits().get(0));
		myCarrier.placePeg(new Point (1,3));
		assertTrue(myCarrier.getHits().get(2));
		myCarrier.placePeg(new Point(1,5));
		assertTrue(myCarrier.getHits().get(4));
		myCarrier.placePeg(new Point(1,4));
		assertTrue(myCarrier.getHits().get(3));
		myCarrier.placePeg(new Point(1,2));
		assertTrue(myCarrier.getHits().get(1));
		
		mySubmarine.setFrontLocation(new Point(2,2));
		mySubmarine.setBackLocation(new Point(2,4));
		mySubmarine.placePeg(new Point(2,4));
		assertTrue(mySubmarine.getHits().get(2));
		mySubmarine.placePeg(new Point(2,3));
		assertTrue(mySubmarine.getHits().get(2));
		assertTrue(mySubmarine.getHits().get(1));
		mySubmarine.placePeg(new Point(2,2));
		assertTrue(mySubmarine.getHits().get(2));
		assertTrue(mySubmarine.getHits().get(1));
		assertTrue(mySubmarine.getHits().get(0));
		
	}
	
	/**
	 * Test to ensure ships cannot be set diagonally
	 */
	@Test
	public void noDiagonalsTest()
	{
		myDestroyer.setFrontLocation(new Point(3,3));
		assertFalse(myDestroyer.setBackLocation(new Point(4,4)));
		assertTrue(myDestroyer.setBackLocation(new Point(3,4)));
		assertFalse(myDestroyer.setBackLocation(new Point(2,2)));
		assertTrue(myDestroyer.setBackLocation(new Point(3,2)));
		assertFalse(myDestroyer.setBackLocation(new Point(4,2)));
		assertTrue(myDestroyer.setBackLocation(new Point(4,3)));
		assertFalse(myDestroyer.setBackLocation(new Point(2,4)));
		assertTrue(myDestroyer.setBackLocation(new Point(2,3)));
	}
	
	/**
	 * Second test to ensure ships cannot be set diagonally
	 */
	@Test
	public void noLongDiagonalsTest()
	{
		mySubmarine.setFrontLocation(new Point(3,3));
		assertFalse(mySubmarine.setBackLocation(new Point(5,5)));
		assertTrue(mySubmarine.setBackLocation(new Point(3,5)));
		assertFalse(mySubmarine.setBackLocation(new Point(1,1)));
		assertTrue(mySubmarine.setBackLocation(new Point(3,1)));
		assertFalse(mySubmarine.setBackLocation(new Point(5,1)));
		assertTrue(mySubmarine.setBackLocation(new Point(5,3)));
		assertFalse(mySubmarine.setBackLocation(new Point(1,5)));
		assertTrue(mySubmarine.setBackLocation(new Point(1,3)));
	}
	
	/**
	 * Test to ensure ships cannot be placed out of bounds below the grid
	 */
	@Test
	public void noBelowGridTest()
	{
		assertFalse(myDestroyer.setFrontLocation(new Point(-1,-1)));
		assertTrue(myDestroyer.setFrontLocation(new Point(1,0)));
		assertFalse(myDestroyer.setBackLocation(new Point(1,-1)));
		assertTrue(myDestroyer.setFrontLocation(new Point(2,0)));
	}
	
	/**
	 * Test to ensure ships cannot be placed out of bounds above the grid
	 */
	@Test
	public void noAboveGridTest()
	{
		assertFalse(mySubmarine.setFrontLocation(new Point(5,11)));
		assertTrue(mySubmarine.setFrontLocation(new Point(5,9)));
		assertFalse(mySubmarine.setBackLocation(new Point(5,11)));
		assertTrue(mySubmarine.setBackLocation(new Point(7,9)));
	}
	
	/**
	 * Test to ensure ships cannot be placed out of bounds to the left of the grid
	 */
	@Test
	public void noLeftOfGridTest()
	{
		assertFalse(myDestroyer.setFrontLocation(new Point(-1,5)));
		assertTrue(myDestroyer.setFrontLocation(new Point(0,5)));
		assertFalse(myDestroyer.setBackLocation(new Point(-1,5)));
		assertTrue(myDestroyer.setFrontLocation(new Point(0,4)));
	}
	
	/**
	 * Test to ensure ships cannot be placed out of bounds to the right of the grid
	 */
	@Test
	public void noRightOfGridTest()
	{
		assertFalse(myDestroyer.setFrontLocation(new Point(10,5)));
		assertTrue(myDestroyer.setFrontLocation(new Point(9,5)));
		assertFalse(myDestroyer.setBackLocation(new Point(10,5)));
		assertTrue(myDestroyer.setBackLocation(new Point(9,6)));
	}
	
	/**
	 * Test to ensure left edge ships place properly
	 */
	@Test
	public void leftEdgeTest()
	{
		assertTrue(myCarrier.setFrontLocation(new Point(0,0)));
		assertTrue(myCarrier.setBackLocation(new Point(0,4)));
		assertTrue(myBattleship.setFrontLocation(new Point(0,9)));
		assertTrue(myBattleship.setBackLocation(new Point(0,6)));
	}
	
	/**
	 * Test to ensure right edge ships place properly
	 */
	@Test
	public void rightEdgeTest()
	{
		assertTrue(myCarrier.setFrontLocation(new Point(9,0)));
		assertTrue(myCarrier.setBackLocation(new Point(9,4)));
		assertTrue(myBattleship.setFrontLocation(new Point(9,9)));
		assertTrue(myBattleship.setBackLocation(new Point(9,6)));
	}
	
	/**
	 * Test to ensure bottom edge ships place properly
	 */
	@Test
	public void bottomEdgeTest()
	{
		assertTrue(myCarrier.setFrontLocation(new Point(0,0)));
		assertTrue(myCarrier.setBackLocation(new Point(4,0)));
		assertTrue(myBattleship.setFrontLocation(new Point(9,0)));
		assertTrue(myBattleship.setBackLocation(new Point(6,0)));
	}
	
	/**
	 * Test to ensure top edge ships place properly
	 */
	@Test
	public void topEdgeTest()
	{
		assertTrue(myCarrier.setFrontLocation(new Point(0,9)));
		assertTrue(myCarrier.setBackLocation(new Point(4,9)));
		assertTrue(myBattleship.setFrontLocation(new Point(9,9)));
		assertTrue(myBattleship.setBackLocation(new Point(6,9)));
	}
}
