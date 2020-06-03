package tested;

import org.junit.Test;

import static org.junit.Assert.*;

import java.awt.*;

public class BlockGridTest {
	
	private BlockGrid gridTest;

	
	//Testing the Constructor
	@Test
	public void testBlockGrid() {
		gridTest = new BlockGrid(10,20);
		int actualWidth = gridTest.getWidth();
		int actualHeight = gridTest.getHeight();
		
		assertEquals(10, actualWidth);
		assertEquals(20, actualHeight);
	}

	//Testing the DrawSquare(int,int)
	@Test
	public void testDrawSquareIntInt() {
		
		gridTest = new BlockGrid(3,4);
		gridTest.drawSquare(1,2);
		Color actualColor = gridTest.getSquare(1,2);
		Boolean matchValue = actualColor.equals(Color.WHITE);
		
		assertEquals(true, matchValue);
	}
	
	//Testing DrawSquare(Point)
	@Test
	public void testDrawSquarePoint() {
		Point pointTest = new Point (3,2);
		gridTest = new BlockGrid(5,4);
		gridTest.drawSquare(pointTest);
		Color actualColor = gridTest.getSquare(pointTest);
		Boolean matchValue = actualColor.equals(Color.WHITE);
		
		assertEquals(true, matchValue);
	}

	//Testing SetColor()
	@Test
	public void testSetColor() {
		gridTest = new BlockGrid(1,2);
		gridTest.setColor(Color.BLUE);
		Color actualColor = gridTest.getColor();
		Boolean matchValue = actualColor.equals(Color.BLUE);
		
		assertEquals(true, matchValue);
	}

	//Testing GetGraphicsWidth() 
	@Test
	public void testGetGraphicsWidth() {
		gridTest = new BlockGrid(7,1);
		int actualWidth = gridTest.getGraphicsWidth();
		int expectedWidth = 7 * 25;
		
		assertEquals(expectedWidth, actualWidth);
	}

	//Testing GetGraphicsHeight() 
	@Test
	public void testGetGraphicsHeight() {
		gridTest = new BlockGrid(1,7);
		int actualHeight = gridTest.getGraphicsHeight();
		int expectedHeight = 7 * 25;
		
		assertEquals(expectedHeight, actualHeight);
	}

	
	//Testing Clear()
	@Test
	public void testClear() {	
		gridTest = new BlockGrid(3,5);
		gridTest.setColor(Color.GREEN);
		gridTest.drawSquare(1,2);
		Color prevColor = gridTest.getSquare(1,2);
		Boolean matchValue = prevColor.equals(Color.GREEN);
		
		assertEquals(true, matchValue);
		
		gridTest.clear();
		int actualWidth = gridTest.getWidth();
		int actualHeight = gridTest.getHeight();
		
		assertEquals(3, actualWidth);
		assertEquals(5, actualHeight);
		assertNotNull(gridTest);
	}

	//Testing Draw()
	@Test
	public void testDraw() {
		BlockGrid gridnullTest = new BlockGrid(0,0);
		gridnullTest.draw(null);
		Color old = gridnullTest.getColor();
		Boolean matchValue = old.equals(Color.WHITE);
		
		assertEquals(matchValue, true);

		BlockGrid gridTest = new BlockGrid(5,7);
		TetrisBlock.RIGHT_L.setLocation(3,5).draw(gridTest);
		int iterations = 0;
		
		for(int i = 0; i < gridTest.getWidth(); i++) {
			for(int j = 0; j < gridTest.getHeight(); j++) {
	            iterations++;
	        }
		} 
		
		assertEquals(35, iterations); 
		
		TetrisBlock.RIGHT_L.setLocation(5,3).draw(gridTest);
		TetrisBlock.SQUARE.setLocation(2,3).draw(gridTest);
		TetrisBlock.LEFT_L.setLocation(5,7).draw(gridTest);
		TetrisBlock.TEE.setLocation(3,2).draw(gridTest);
		TetrisBlock.LONG .setLocation(1,2).draw(gridTest);
	}

	//Testing ToString()
	@Test
	public void testToString() {
		BlockGrid gridNullTest = new BlockGrid(3,2);
		String stringValue = gridNullTest.toString();
		
		assertEquals("---\n" + "---", stringValue);
		
		BlockGrid gridTest = new BlockGrid(2,4);
		gridTest.drawSquare(1,2);
		String stringValueNew = gridTest.toString();
		
		assertEquals("--\n" + "--\n" + "-#\n" + "--", stringValueNew);
	}

}
