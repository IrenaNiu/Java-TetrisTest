package tested;

import java.awt.*;

public class BlockGrid
{
   public static final int BLOCK_SIDE = 25;
   public static final Color BACKGROUND_COLOR = Color.WHITE;

   private Color[][] blocks;
   private Color currentColor = BACKGROUND_COLOR;

   public BlockGrid(int width, int height)
   {
      blocks = new Color[width][height];
   }

   public void drawSquare(int x, int y)
   {
      try
      {
         blocks[x][y] = currentColor;
      }
      catch(Exception ex)
      {
      }
   }

   public void drawSquare(Point p)
   {
      drawSquare(p.x, p.y);
   }

   public void setColor(Color color)
   {
      currentColor = color;
   }
   
   //Code additions made for testing DrawSquare() method
   public Color getSquare(int x, int y)
   {
      return blocks[x][y];
   }
   
   //Code additions made for testing DrawSquare() method
   public Color getSquare(Point p)
   {
      return blocks[p.x][p.y];
   }
   
   //Code additions made for testing SetColor() method
   public Color getColor() {
      return currentColor;
   }

   public int getWidth()
   {
      return blocks.length;
   }

   public int getHeight()
   {
      return blocks[0].length;
   }

   public int getGraphicsWidth()
   {
      return getWidth()*BLOCK_SIDE;
   }

   public int getGraphicsHeight()
   {
      return getHeight()*BLOCK_SIDE;
   }

   public void clear()
   {
      blocks = new Color[blocks.length][blocks[0].length];
      //System.out.println(blocks.length + " " + blocks[0].length); //re initialize?
   }

   public void draw(Graphics g)
   {
      for(int i = 0; i < blocks.length; i++)
      {
         for(int j = 0; j < blocks[0].length; j++)
         {
            if(blocks[i][j] != null)
               g.setColor(blocks[i][j]);
            else
               g.setColor(BACKGROUND_COLOR);
            g.fillRect(i*BLOCK_SIDE, j*BLOCK_SIDE, BLOCK_SIDE-1, BLOCK_SIDE-1);
            g.setColor(Color.BLACK);
            g.drawRect(i*BLOCK_SIDE, j*BLOCK_SIDE, BLOCK_SIDE-1, BLOCK_SIDE-1);
         }
      }
   }

   public String toString()
   {
      String ret = "";
      for(int j = 0; j < blocks[0].length; j++)
      {
         if(j != 0)
            ret+="\n";
         for(int i = 0; i < blocks.length; i++)
         {
            if(blocks[i][j] != null)
               ret+="#";
            else
               ret+="-";
         }
      }
      return ret;
   }
}