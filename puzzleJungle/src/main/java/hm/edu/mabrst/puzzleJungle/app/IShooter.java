package hm.edu.mabrst.puzzleJungle.app;

import android.view.MotionEvent;

public interface IShooter {

	public int[][] shoot(int[][] koordinates,MotionEvent e,Bubble b);
}
