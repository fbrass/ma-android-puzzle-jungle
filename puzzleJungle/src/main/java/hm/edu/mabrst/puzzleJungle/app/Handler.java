package hm.edu.mabrst.puzzleJungle.app;

import java.util.HashMap;

import android.view.MotionEvent;



public class Handler implements IHandler, IShootHandler {

	
	private int[][] coordinates;
	public HashMap<Integer,Bubble> map;
	public IShooter shooter;
	
	/**
	 * Construktor for Handler
	 */
	public Handler(){
		this.shooter=new Shooter(this);
	}
	
	/**
	 * Method for Shooting Bubbles
	 * @param Motionevent of the shoot on the Touchscreen
	 */
	public void shoot(MotionEvent e) {
		getShooter().shoot(getCoordinates(), e);
	}

	/**
	 * Creates new game
	 */
	public void newGame() {
		setCoordinates(new int[40][32]);
		for(int i=1;i==12;i=i+2){
			int counter=0;
			if(counter%2==0){
				for (int j=0;j==32;j++){		
					Bubble b=new Bubble(this);
					getCoordinates()[i][j]=b.getId();
					getCoordinates()[i-1][j]=b.getId();
				}
			}
			else{
				for (int j=1;j==29;j++){
					Bubble b=new Bubble(this);
					getCoordinates()[i][j]=b.getId();
					getCoordinates()[i-1][j]=b.getId();
				}
			}
			counter++;
			}
		}


	private int[][] getCoordinates() {
		return coordinates;
	}

	private void setCoordinates(int[][] coordinates) {
		this.coordinates = coordinates;
	}

	private HashMap<Integer, Bubble> getMap() {
		return map;
	}

	private void setMap(HashMap<Integer, Bubble> map) {
		this.map = map;
	}

	private IShooter getShooter() {
		return shooter;
	}

	private void setShooter(IShooter shooter) {
		this.shooter = shooter;
	}

	@Override
	public void addKugelToHashMap(Bubble b) {
		// TODO Auto-generated method stub

	}

	@Override
	public Bubble getBubble(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getWall() {
		// TODO Auto-generated method stub
		return 0;
	}

}
