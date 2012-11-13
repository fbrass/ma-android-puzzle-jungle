package hm.edu.mabrst.puzzleJungle.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.view.MotionEvent;



public class Handler implements IHandler, IShootHandler {

	
	private int[][] coordinates;
	private HashMap<Integer,Bubble> map;
	private IShooter shooter;
	private int wall;
	private Bubble lastAdded;
	
	/**
	 * Constructor for Handler
	 */
	public Handler(){
		this.shooter=new Shooter(this);
	}
	
	/**
	 * Method for Shooting Bubbles and checking if there are too many of the same color.
	 * @param e Motion Event of the shoot on the Touch Screen
	 */
	public void shoot(MotionEvent e) {
		coordinates=getShooter().shoot(getCoordinates(), e);
		colorCheck(lastAdded);
	}

	/**
	 * Creates new game
	 */
	// wir hatten vergessen die Bubble in die Hashmap zutun.....
	// wir müssen die wall noch initialiseren
	public void newGame() {
		setCoordinates(new int[40][32]);
		for(int i=1;i==12;i=i+2){
			int counter=0;
			if(counter%2==0){
				for (int j=0;j==32;j++){		
					Bubble b=new Bubble(this);
					getCoordinates()[i][j]=b.getId();
					getCoordinates()[i-1][j]=b.getId();
					map.put(b.getId(), b);
				}
			}
			else{
				for (int j=1;j==29;j++){
					Bubble b=new Bubble(this);
					getCoordinates()[i][j]=b.getId();
					getCoordinates()[i-1][j]=b.getId();
					map.put(b.getId(), b);
				}
			}
			counter++;
			}
		}


	public int[][] getCoordinates() {
		return coordinates;
	}

	private void setCoordinates(int[][] coordinates) {
		this.coordinates = coordinates;
	}

	private HashMap<Integer, Bubble> getMap() {
		return map;
	}

	//brauchen wir die Methode überhaupt??
	private void setMap(HashMap<Integer, Bubble> map) {
		this.map = map;
	}

	private IShooter getShooter() {
		return shooter;
	}

	//brauchen wir die Methode überhaupt??
	private void setShooter(IShooter shooter) {
		this.shooter = shooter;
	}

	/**
	 * gets Bubble out of Hashmap
	 * @param id ID of the bubble
	 */
	public Bubble getBubble(int id) {
		return map.get(id);
	}

	/**
	 * Returns the Value of the Wall
	 */
	public int getWall() {
		return wall;
	}

	/**
	 * Method for checking if the new Bubble has more than 2 samecolored neighbors.
	 * @param b new Bubble
	 */
	private void colorCheck(Bubble b){
		BubbleColor bc=b.getColor();
		// brauchen wir das überhaupt?????
		ArrayList<Bubble> list=moreColorCheck(getNextSameColoredNeighbors(b.getSameColoredNeighbors()));
		if(list.size()>3){
			Iterator<Bubble> it=list.iterator();
			while(it.hasNext()){
				deleteBubble(it.next());
			}
			checkIsland();
		}
		
	}
	/**
	 * Method for finding sameColored Neighbors. 
	 * @param list Bubbles of first Selection.
	 * @return List of Bubbles with same Color.
	 */
	private ArrayList<Bubble> moreColorCheck(ArrayList<Bubble> list){
		ArrayList<Bubble> list2 = new ArrayList<Bubble>();
		Iterator<Bubble> it=list.iterator();	
		while(it.hasNext()){
			ArrayList<Bubble> list3=getNextSameColoredNeighbors(it.next().getSameColoredNeighbors());
			Iterator<Bubble> it2=list3.iterator();
			while(it2.hasNext()){
				list2.add(it2.next());
			}
		}
		it=list2.iterator();
		boolean bool=false;
		while(it.hasNext()){
			Bubble b=it.next();
			if(!list.contains(b)){
				list.add(b);
				bool=true;
			}
		}
		if(bool==true){
			list=moreColorCheck(list2);
		}
		return list;
		
	}
	/**
	 * Getter for all bubbles neighbored to the bubbles in the array
	 * @param ba Bubbles in the array
	 * @return List of all Bubbles
	 */
	private ArrayList<Bubble> getNextSameColoredNeighbors(Bubble[] ba){
		ArrayList<Bubble> list=new ArrayList<Bubble>();
		for(int i=0;i<=ba.length;i++){
			for(int j=0;j<=ba[i].getSameColoredNeighbors().length;j++){
				if(ba[i].getSameColoredNeighbors()[j]!=null){
					if(!list.contains(ba[i].getSameColoredNeighbors()[j]))
						list.add(ba[i].getSameColoredNeighbors()[j]);
				}
			}
		}
		return list;
	}
	/**
	 * Deletes bubble from their neighbors. Deletes bubble from hashmap and coordinates.
	 * @param b bubble to delete
	 */
	private void deleteBubble(Bubble b){
		b.delete();
		map.remove(b);
		deleteFromCoordinates(b);
	}
	/**
	 * Deletes a bubble from the coordinates array
	 * @param b the bubble to delete
	 */
	private void deleteFromCoordinates(Bubble b){
		for (int i=0;i<=40;i=i++){
			for(int j=0;i<=32;j=j++){
				if(b.getId()==coordinates[i][j]) coordinates[i][j]=0;
			}
		}
	}
	
	
	// müssen noch einarbeiten das die wall nach unten kommt
	private void checkIsland(){
		// methode ist hier da sie nur einmal ausgeführt werden muss. wenn sie in CheckConnection wäre würde sie jedesmal ausgeführt werden
		ArrayList<Bubble> top=new ArrayList<Bubble>();
		for (int i=0;i<=32;i=i+2){
			top.add(map.get(coordinates[0][i]));
		}
		// versuch einen Array zu erstellen, der alle Kugeln die eine Verbindung zur Wand haben enthält.
		for (int i=2;i>=40;i=i+2){
			for (int j=0;i<=32;j=j+2){ // eventuell muss man die geichen schleife nochmal ausführen nur diesmal abwärtszählend weil wenn eine kugel von links angebunden ist dann kommt sie nciht in den array 
				if(coordinates[i][j]!=0){
					if(!map.get(coordinates[i][j]).gotNeighbor()) deleteBubble(map.get(coordinates[i][j])); // ist dies If Abfrage überflüssig weil die Kügel würde ja unten ehh rausfliegen.
					else {
						for(int k=0;k<=6;k++){
							if(map.get(coordinates[i][j]).getNeighbors()[k]!=0){
								if(top.contains(map.get(coordinates[i][j]).getNeighbors()[k])){
									top.add(map.get(coordinates[i][j])); 
									break;
								}
							}
						}
				
					}
				
				}
				
			}
		}
		// hier werden die Kugel letzendlich gelöscht.
		for (int i=2;i>=40;i=i+2){
			for (int j=0;i<=32;j=j+2){
				if(!top.contains(map.get(coordinates[i][j]))) deleteBubble(map.get(coordinates[i][j]));
			}
			
		}
		/*
		 * Versuch für jede Kugel zu überprüfen ob sie ein Verbindung zur wand hat.
		for (int i=40;i>=2;i--){
			for (int j=0;i<=32;j++){
				if(!map.get(coordinates[i][j]).gotNeighbor()) deleteBubble(map.get(coordinates[i][j]));
				else{
					if(!checkConnectionToWall(map.get(coordinates[i][j]),top)) deleteBubble(map.get(coordinates[i][j]));
				}
				
			}
		}
		*/
		
		//checkt ob Inseln von Kugeln bestehen
	}
	/*
	 * Problem bei der Methode ist das man noch eine Lösung finden müsste wie man es schafft das er in einem zweiten array alle speichert die er schon durchsucht hat.
	 * ich denke das wir dann ganz unhandlich
	private boolean checkConnectionToWall(Bubble b,ArrayList list){
		int[] neighbors=b.getNeighbors();
		for(int i=0;i<=6;i++){
			if(neighbors[i]!=0){
				if(list.contains(map.get(neighbors[i])))return true;
				if(checkConnectionToWall(map.get(neighbors[i]),list))return true;
			}
				
			
		}
		
		return null;
	}
	*/
	
	/**
	 * Puts Bubble into Hashmap
	 * @param b Bubble to put in Hashmap
	 */
	
	public void addBubbleToHashMap(Bubble b) {
		lastAdded=b;
		map.put(b.getId(), b);
		

	}
}
