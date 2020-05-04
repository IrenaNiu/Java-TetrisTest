package unitTests;
import tetris.*;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

public class TetrisBlockTest {
   
   TetrisBlock longBlock;  // Block with shape of LONG
   TetrisBlock llBlock;    // Block with shape of LEFT_L
   TetrisBlock teeBlock;   // Block with shape of TEE
   TetrisBlock rlBlock;    // Block with shape of RIGHT_L
   TetrisBlock sqrBlock;   // Block with shape of SQUARE
   

   @Before
   public void setUp() throws Exception {
      longBlock = TetrisBlock.LONG;
      llBlock = TetrisBlock.LEFT_L;
      teeBlock = TetrisBlock.TEE;
      rlBlock = TetrisBlock.RIGHT_L;
      sqrBlock = TetrisBlock.SQUARE;
   }

   //Test the Constructor
   @Test
   public void testTetrisBlock() {
      // Test Five types of blocks
      assertEquals(1, longBlock.getWidth());
      assertEquals(4, longBlock.getHeight());
      
      assertEquals(2, llBlock.getWidth());
      assertEquals(3, llBlock.getHeight());
      
      assertEquals(2, teeBlock.getWidth());
      assertEquals(3, teeBlock.getHeight());
      
      assertEquals(2, rlBlock.getWidth());
      assertEquals(3, rlBlock.getHeight());
      
      assertEquals(2, sqrBlock.getWidth());
      assertEquals(2, sqrBlock.getHeight());
   }
   
   //Test setLocation
   @Test
   public void testSetLocation() {
      TetrisBlock start = longBlock.setLocation(0, 1);
      int x = start.getX();
      int y = start.getY();
      int expectedX = 0;
      int expectedY = 1;
      assertEquals(x, expectedX);
      assertEquals(y, expectedY);
   }
   
   //Test moveDown
   @Test
   public void testMoveDown() {
      TetrisBlock start = longBlock.setLocation(0, 1);
      TetrisBlock oneMove = start.moveDown();
      int x = oneMove.getX();
      int y = oneMove.getY();
      int expectedX = 0;
      int expectedY = 2; // Y should be +1
      assertEquals(x, expectedX);
      assertEquals(y, expectedY);
   }
   
   //Test moveLeft
   @Test
   public void testMoveLeft() {
      TetrisBlock start = longBlock.setLocation(1, 0);
      TetrisBlock oneMove = start.moveLeft();
      int x = oneMove.getX();
      int y = oneMove.getY();
      int expectedX = 0;  // X should be -1
      int expectedY = 0;
      assertEquals(x, expectedX);
      assertEquals(y, expectedY);
   }
   
   //Test moveRight
   @Test
   public void testMoveRight() {
      TetrisBlock start = longBlock.setLocation(1, 0);
      TetrisBlock oneMove = start.moveRight();
      int x = oneMove.getX();
      int y = oneMove.getY();
      int expectedX = 2;  // X should be +1
      int expectedY = 0;
      assertEquals(x, expectedX);
      assertEquals(y, expectedY);
   }
   
   //Test overlaps
   @Test
   public void testOverlaps() {
      TetrisBlock a = TetrisBlock.SQUARE.setLocation(2,2);
      TetrisBlock b = TetrisBlock.SQUARE.setLocation(3,2);
      TetrisBlock c = TetrisBlock.SQUARE.setLocation(4,2);
      boolean overlapAB = a.overlaps(b);
      boolean overlapAC = a.overlaps(c);
      assertEquals(true, overlapAB);
      assertEquals(false, overlapAC);
   }
   
   //Test turnLeft
   @Test
   public void testTurnLeft() {
      TetrisBlock start = longBlock.setLocation(1, 0);
      TetrisBlock oneMove = start.turnLeft();
      assertEquals(4, oneMove.getWidth());
      assertEquals(1, oneMove.getHeight());
      
      TetrisBlock secMove = oneMove.turnLeft();
      assertEquals(1, secMove.getWidth());
      assertEquals(4, secMove.getHeight());
   }
   
   //Test turnRight
   @Test
   public void testTurnRight() {
      TetrisBlock start = longBlock.setLocation(1, 0);
      TetrisBlock oneMove = start.turnRight();
      assertEquals(4, oneMove.getWidth());
      assertEquals(1, oneMove.getHeight());
      
      TetrisBlock secMove = oneMove.turnRight();
      assertEquals(1, secMove.getWidth());
      assertEquals(4, secMove.getHeight());
   }
   
   //Test getSingleBlocks
   @Test
   public void testGetSingleBlocks() {
      TetrisBlock start = sqrBlock.setLocation(0, 1);
      Vector<TetrisBlock> parts = start.getSingleBlocks();
      for (int i = 0; i < parts.size(); i++) {
         TetrisBlock single = parts.get(i);    
         int width = single.getWidth();
         int height = single.getHeight();
         int expectedW = 1;
         int expectedH = 1;
         assertEquals(width, expectedW);
         assertEquals(height, expectedH);     
      } 
   }
   
   
   

}
