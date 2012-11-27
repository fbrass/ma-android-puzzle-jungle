package hm.edu.mabrst.puzzleJungle.app;

import java.util.LinkedList;
import java.util.List;

import android.view.MotionEvent;

/**
 * 
 * @author Moritz
 *
 */
public class Shooter implements IShooter {
	
	public static int Y = 20;
	public static int X = 11;

	private IHandler handler;
	
	
	/**
	 * Constructor for Shooter
	 * @param h Handler of the Shooter
	 */
	public Shooter(IHandler h){
		this.handler=h;
	}
	
	@Override
	public int[][] shoot(int[][] coordinates, MotionEvent e)
	{
		
		
		// ]0°,90°[
		double angle = 0.0;
		
		// right or left shoot
		boolean isRight= false;
		
		// Coordinates from event
		float ex = e.getRawX();
		float ey = e.getRawY();
		
		// first row with all elements zero
		int firstZeroRow = getFirstZeroRow(coordinates);
		boolean isHit = false;	// is something hit by bubble
		int[][] positions;		// new bubble position
		double indx = 0; 		// index of bubble-hit
		double indy = 0; 		// index of bubble-hit
		double angleRad = 0;	// angle in radiant
		List<int[]> path;		// bubble path
		
		// special case angle = 0, no path calculation needed
		if(angle == 0)
		{
			indx = X/2;
			indy = 0;
		}
		// calculation for angles ]0,PI[
		else
		{
			angleRad = (90-angle) * Math.PI/180;
			// calculated y distance
			double dy = Math.tan(angleRad) * (X/2.0);
			
			// if y-distance dy > Y-BB -> intercept theorem
			// BB: Bounding Box
			if (Math.abs(dy) > Y)
			{
				// AB/CD = SB/SD
				if (isRight)
					indx = (int) (X - (dy-Y)/dy * (X/2.0));
				else
					indx = (int) ((dy-Y)/dy * X/2.0);
			}
			// bubble will hit BB
			else
			{
				if (isRight)
					indx = X-1;	// x at last array position
				else
					indx = 0;	// x at first array position
				
				// [0][0] -> [y][y]
				indy = (int) (Y - dy);
			}
		}
		path = getBubblePath(Y-1, X/2, (int)indy, (int)indx);
 
		if (indy <= firstZeroRow)
		{
			positions = getPosition(coordinates, path, firstZeroRow);
			if (positions != null)
				isHit = true;
		}
			
		// -----------------------------------------------------------------------------------------
		
		// Calculation after x-BB hit
		double xstart = indx;
		double ystart = indy;
		
		// reached y-BB 
		while(indy != 0 && !isHit)
		{
			
			// direction change after x-BB hit
			if (isRight)
				isRight = false;
			else
				isRight = true;
		
			// last y-Index
			double ytmp = indy;
			// new y-distance
			double dy = Math.tan(angleRad) * X;
			// reached y-BB
			if (Math.abs(dy) > ytmp) 
			{	
				double tmp = (dy-ytmp)/dy * X;
				if (isRight)
				{
					indx = (int)(X - tmp);
				}
				else
				{
					indx = tmp;
				}
				indy = 0;
			}
			// new wall reflection
			else
			{
				// actual y level
				double yh = Y-ytmp;
				

				if (isRight)
					indx = X-1;
				else
					indx = 0;	
				indy = (int) (Y - (dy+yh)) ;
		
			}
			
			path = getBubblePath((int)ystart, (int)xstart, (int)indy, (int)indx);
			if (indy < firstZeroRow)
			{
				
				positions = getPosition(coordinates, path, firstZeroRow);
				if (positions != null)
					isHit = true;
			}
				
			xstart = indx;
			ystart = indy;
		}
		return coordinates;
	}
	
	
	/**
	 * bresenham benutzen
	 * @param startX
	 * @param startY
	 * @param endY
	 * @param endY
	 * @return
	 */
	private List<int[]> getBubblePath(final int ystart, final int xstart, final int yend, final int xend)
	{
		
		List<int[]> path = new LinkedList<int[]>();
		// delta x and delta y
		int dx = xend - xstart;
		int dy = yend - ystart;
		
		// positive or negative increment
		int incx = sgn(dx);
		int incy = sgn(dy);
		
		if (dx < 0) dx = -dx;
		if (dy < 0) dy = -dy;
		
		int pdx, pdy, ddx, ddy, es, el;
		
		if (dx > dy)
		{
			pdx = incx;
			pdy = 0;
			ddx = incx;
			ddy = incy;
			es = dy;
			el = dx;
		}
		else
		{
	        pdx = 0;
	        pdy = incy;
	        ddx = incx;
	        ddy = incy;
	        es  = dx;
	        el = dy;
		}
		// start values
		int x = xstart;
		int y = ystart;
		int err = el/2;
		path.add(new int[]{y,x});
		int i = 0;
		while (i < el)
		{
			err -= es;
			if (err < 0)
			{
				err += el;
				x += ddx;
				y+= ddy;
			}
			else
			{
				x += pdx;
				y += pdy;
			}
			path.add(new int[]{y,x});
			i++;
		}
		
		return path;
	}
	
	/**
	 * 
	 * @param x
	 * @return
	 */
	private int sgn(final int x)
	{
		if (x > 0)
			return 1;
		else if (x < 0)
			return -1;
		else
			return 0;
	}
	
	private int getFirstZeroRow(int[][] coordinates)
	{
		int firstzero = 0;
		// check every 2. row
		for (int row=1; row < Y; row+=2)
		{
			int rowsum = 0;
			// and every 2. col
			for (int col = 1; col < X; col+=2)
			{
				rowsum += coordinates[row][col];
			}
			if (rowsum == 0)
			{
				return firstzero;
			}
			else
				firstzero += 2;
			
		}
		return firstzero;
	}
	
	
	private boolean getIsFullRow(int row)
	{
		int r = row / 2;
		return r % 2 == 0;
	}
	
	private int getOtherX(int x, boolean isFullRow)
	{
		// aufeinander folgende Elemente addieren und mod 4
		// Ergebnis 3, verschoben; Ergebnis 1 volle Reihe
		int mod4full = 1;
		int mod4not  = 3;
		int x2 = 0;
		if (isFullRow)
		{
			if ((x + x+1) % 4 == mod4full)
				x2 = x+1;
			else
				x2 = x-1;
		}
		else
		{
			if ((x + x+1) % 4 == mod4not)
				x2 = x+1;
			else
				x2 = x-1;
		}
		return x2;
	}
	
	private int[] getXNeighbours(int x, int x2)
	{
		
		if (x > x2)
		{
			if (x < X -1 && x2 > 0) 
				return new int[]{x+1, x2-1};
			else if(x2 == 0)
				return new int[]{x+1, x2};
			else // x == X-1 
				return new int[]{x, x2-1};
		}
		else
		{
			if (x2 < X -1 && x > 0) 
				return new int[]{x-1, x2+1};
			else if(x == 0)
				return new int[]{x, x2+1};
			else // x2 == X-1 
				return new int[]{x-1, x2};
		}
		
	}
	
	
	private int[][] getPosition(int[][] coordinates, List<int[]> path, int firstzero)
	{

		// ist es eine volle Reihe
		boolean isFullRow = getIsFullRow(firstzero);
		// alle 4 Positionen einer Kugel
		int[][] positions = new int[4][2];
		boolean isHit = false;
		
		if (firstzero == 0)
		{
			System.out.println("Hit Decke");
			int[] p = path.get(17);
			int x = p[1];
			int x2 = getOtherX(x, isFullRow);

			positions[0] = new int[]{0, x};
			positions[1] = new int[]{0, x2};
			positions[2] = new int[]{1, x};
			positions[3] = new int[]{1, x2};
			isHit = true;
		}
		else
		{
			for (int[] p : path)
			{
				
				if (p[0] <= firstzero)
				{
					int x = p[1];
					int y = p[0];
					
					// moeglich bei sehr großen Winkeln
					// flache Flugbahn
					if (!isFullRow)
						if(x == 0)
							x += 1;
						if (x == X-1)
							x -= 1;
					
					int x2 = getOtherX(x, isFullRow);
					
					// check
					int[] neighbours = getXNeighbours(x, x2);
					int x_n  = neighbours[0];
					int x2_n = neighbours[1];
					if(coordinates[y][x_n] != 0 || coordinates[y][x2_n] != 0)
					{
						// hit left or right
						System.out.println("Hit links, rechts");
						positions[0] = new int[]{y, x};
						positions[1] = new int[]{y, x2};
						positions[2] = new int[]{y+1, x};
						positions[3] = new int[]{y+1, x2};
						isHit = true;
						break;
					}
					if (y > 0)
					{
						if (coordinates[y-1][x] != 0 || coordinates[y-1][x2] != 0)
						{
							// hit on above bubble
							// [y+1][x], [y+1][x2]
							// [y  ][x], [y  ][x2]
	
							positions[0] = new int[]{y, x};
							positions[1] = new int[]{y, x2};
							positions[2] = new int[]{y+1, x};
							positions[3] = new int[]{y+1, x2};
							isHit = true;
							break;
						}
					}
					else
					{
						positions[0] = new int[]{y, x};
						positions[1] = new int[]{y, x2};
						positions[2] = new int[]{y+1, x};
						positions[3] = new int[]{y+1, x2};
						isHit = true;
						break;
					}
					firstzero -= 2;
					isFullRow = getIsFullRow(firstzero);
				}
			}
		}
		if (isHit)
			return positions;
		else
			return null;
	}
}
