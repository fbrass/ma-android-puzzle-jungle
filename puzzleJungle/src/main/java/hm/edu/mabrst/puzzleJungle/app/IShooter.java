package hm.edu.mabrst.puzzleJungle.app;

import android.view.MotionEvent;
/**
 * 
 * @author Moritz
 *
 */
public interface IShooter {
	/**
	 * 
	 * @param coordinates
	 * @param e
	 * @return
	 */
	public int[][] shoot(int[][] coordinates,MotionEvent e);
}
