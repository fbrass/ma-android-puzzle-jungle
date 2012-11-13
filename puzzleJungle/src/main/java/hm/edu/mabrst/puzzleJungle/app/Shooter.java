package hm.edu.mabrst.puzzleJungle.app;

import android.view.MotionEvent;

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
	public int[][] shoot(int[][] coordinates, MotionEvent e) {
		// -90 0 90
		double angle = 0.0;
		
		// Unterscheidung ob links oder rechts vom Nullpunkt
		boolean isRight= false;
		
		// Coordinates from event
		float ex = e.getRawX();
		float ey = e.getRawY();
		
		// Berechnung des Winkels aus Event
		
		int indx = 0;
		int indy = 0;
		
		if(angle == 0)
		{
			indx = X/2;
			indy = 0;
		}
		
		else
		{
			double angleRad = ((90-angle)) * Math.PI/180;
			// Delta y Y + darueber, umbennen in ytmp oder so
			double dy = Math.tan(angleRad) * (X/2.0);
			//System.out.println(Math.round(Math.tan(angleRad) * (X/2)));
			
			// wenn posy > y, dann strahlensatz um x zu bekommen
			// Indexposition des Endpunktes
			if (Math.abs(dy) > Y){
				// AB/CD = SB/SD
				// Cast weil indexpositionen gebraucht werden
				if (isRight)
				{
					indx = (int) (X - (dy-Y)/dy * (X/2) + 1);
				}
				else
					indx = (int) ((dy-Y)/dy * (X/2)-1) ;
			}
			else
			{
				if (isRight)
					indx = X-1;	// x ist dann an der letzten pos
				else
					indx = 0;	// x ist an erster Position
				// y wieder umdrehen da [0][0] -> [y][y]
				indy = (int)(Y - dy);
			}
		}
		
		int[] path = getBubblePath(Y-1, X/2, indy, indx);
		
		
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
	private static int[] getBubblePath(final int ystart, final int xstart, final int yend, final int xend)
	{
		int[] positions = new int[2];
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
			i++;
		}
		
		return positions;
	}
	
	/**
	 * 
	 * @param x
	 * @return
	 */
	private static int sgn(final int x)
	{
		if (x > 0)
			return 1;
		else if (x < 0)
			return -1;
		else
			return 0;
	}
}
