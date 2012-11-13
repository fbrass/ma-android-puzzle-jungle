package hm.edu.mabrst.puzzleJungle.app;

import android.view.MotionEvent;

public class Shooter implements IShooter {

	private IHandler handler;
	
	
	/**
	 * Construktor for Shooter
	 * @param h Handler of the Shooter
	 */
	public Shooter(IHandler h){
		this.handler=h;
	}
	
	@Override
	public int[][] shoot(int[][] koordinates, MotionEvent e) {
		// TODO Auto-generated method stub
		return null;
	}

}
