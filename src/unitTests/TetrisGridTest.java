package unitTests;
import tetris.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TetrisGridTest {

    TetrisGrid grid;

    @org.junit.Before
    public void setUp() throws Exception {
        grid = new TetrisGrid(10,20);
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    //test constructor
    public void constructor_should_pass() {
        assertEquals(10, grid.getWidth());
        assertEquals(20, grid.getHeight());
    }

    //test MoveDown() method
    @Test
    public void when_grid_empty_can_moveDown() {
        grid.setWorkingBlock(TetrisBlock.LONG);
        grid.moveDown();

        assertEquals(1, grid.getWorkingBlock().getY());
    }

    @Test
    public void when_bottom_is_blocked_cannot_moveDown() {
        grid.addLockedBlocks(TetrisBlock.LONG.setLocation(1,0));

        grid.setWorkingBlock(TetrisBlock.LONG.setLocation(0,0));
        grid.moveDown();

        assertEquals(0, grid.getWorkingBlock().getX());
    }

    @Test
    public void when_reach_bottom_boundary_cannot_moveDown() {
        grid.setWorkingBlock(TetrisBlock.LONG.setLocation(1,19));
        grid.moveDown();

        assertEquals(null, grid.getWorkingBlock());
    }

    //test moveLeft()
    @Test
    public void when_left_is_blocked_cannot_moveLeft() {
        grid.addLockedBlocks(TetrisBlock.SQUARE.setLocation(1,0));

        grid.setWorkingBlock(TetrisBlock.LONG.setLocation(1,2));
        grid.moveLeft();

        assertEquals(null, grid.getWorkingBlock());
    }

    @Test
    public void when_reach_left_boundary_cannot_moveLeft() {
        grid.setWorkingBlock(TetrisBlock.LONG.setLocation(0,3));
        grid.moveLeft();

        assertEquals(0, grid.getWorkingBlock().getX());
        assertEquals(3, grid.getWorkingBlock().getY());
    }

    //test moveRight()
    @Test
    public void when_right_is_blocked_cannot_moveRight() {
        TetrisBlock block = TetrisBlock.SQUARE;
        block.setLocation(8,1);
        grid.addLockedBlocks(block);

        grid.setWorkingBlock(TetrisBlock.LONG.setLocation(0,0));
        grid.moveRight();

        assertEquals(null, grid.getWorkingBlock());
    }

    @Test
    public void when_reach_right_boundary_cannot_moveRight() {
        grid.setWorkingBlock(TetrisBlock.SQUARE.setLocation(8,2));
        grid.moveRight();

        assertEquals(8, grid.getWorkingBlock().getX());
        assertEquals(2, grid.getWorkingBlock().getY());
    }

    //test turnLeft()
    @Test
    public void when_left_is_blocked_cannot_turnLeft() {
        grid.addLockedBlocks(TetrisBlock.SQUARE.setLocation(2,0));

        grid.setWorkingBlock(TetrisBlock.LONG.setLocation(0,0));
        grid.turnLeft();

        assertEquals(0, grid.getWorkingBlock().getX());
        assertEquals(0, grid.getWorkingBlock().getY());
    }

    @Test
    public void turnLeft_should_pass_when_not_blocked() {
        grid.setWorkingBlock(TetrisBlock.LONG.setLocation(0,0));
        grid.turnLeft();

        assertEquals(0, grid.getWorkingBlock().getX());
        assertEquals(0, grid.getWorkingBlock().getY());
    }


    //test turnRight()
    @Test
    public void when_right_is_blocked_cannot_turnRight() {
        grid.addLockedBlocks(TetrisBlock.SQUARE.setLocation(2,8));

        grid.setWorkingBlock(TetrisBlock.LONG.setLocation(0,6));
        grid.turnRight();

        assertEquals(0, grid.getWorkingBlock().getX());
        assertEquals(6, grid.getWorkingBlock().getY());
    }

    @Test
    public void turnRight_should_pass_when_not_blocked() {
        grid.setWorkingBlock(TetrisBlock.LEFT_L);
        grid.turnRight();

        assertEquals(0, grid.getWorkingBlock().getX());
        assertEquals(0, grid.getWorkingBlock().getY());
    }

    //test isFill()
    @Test
    public void isFill_should_return_false_when_grid_is_empty() {
        boolean isFilled = grid.isFilled();

        assertEquals(false, isFilled);
    }

    @Test
    public void isFilled_should_return_true_when_grid_is_full() {
        grid.addLockedBlocks(TetrisBlock.TEE.setLocation(0,0));
        boolean isFilled = grid.isFilled();

        assertEquals(true, isFilled);
    }

    @Test
    public void isFilled_should_return_false_when_grid_is_not_full() {
        grid.addLockedBlocks(TetrisBlock.TEE.setLocation(3,3));
        grid.addLockedBlocks(TetrisBlock.SQUARE.setLocation(5,4));
        boolean isFilled = grid.isFilled();

        assertEquals(false, isFilled);
    }

 }