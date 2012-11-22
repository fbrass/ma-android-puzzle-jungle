package hm.edu.mabrst.puzzleJungle.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.view.MotionEvent;

public class Handler implements IHandler, IShootHandler {

	private int[][] coordinates;
	private HashMap<Integer, Bubble> map;
	private IShooter shooter;
	private int wall;
	private Bubble lastAdded;
	public int idCounter;

	/**
	 * Constructor for Handler
	 */
	public Handler() {
		this.shooter = new Shooter(this);
		this.map = new HashMap<Integer, Bubble>();
	}

	/**
	 * Method for Shooting Bubbles and checking if there are too many of the
	 * same color.
	 * 
	 * @param e
	 *            Motion Event of the shoot on the Touch Screen
	 */
	public void shoot(MotionEvent e) {
		coordinates = getShooter().shoot(getCoordinates(), e);
		colorCheck(lastAdded);
	}

	/**
	 * Creates new game
	 */
	// wir hatten vergessen die Bubble in die Hashmap zutun.....
	// wir m�ssen die wall noch initialiseren
	public void newGame() {
		int[][] co = new int[20][16];
		int counter = 0;

		for (int i = 1; i <= 3 * 2; i += 2) {
			if (counter % 2 == 0) {
				for (int j = 0; j < 16; j += 2) {
					Bubble b = new Bubble(this,getID());
					map.put(b.getId(), b);
					for (int r = 0; r < 2; r++) {
						for (int c = 0; c < 2; c++) {
							co[i - r][j + c] = b.getId();
						}
					}

				}
			} else {
				for (int k = 1; k < 15; k += 2) {
					Bubble b = new Bubble(this,getID());
					map.put(b.getId(), b);
					for (int r = 0; r < 2; r++) {
						for (int c = 0; c < 2; c++) {
							co[i - r][k + c] = b.getId();
						}
					}
				}
			}
			counter++;
		}
		// bei jeder Kugel muss noch der Nachbar eingetragen werden.
		for (int k = 1; k < 10; k += 2) {
			for (int i = 1; i < 16; i += 2) {
				if (co[k][i] != 0) {
					Bubble b = map.get(co[k][i]);
					int[] ba = new int[6];
					for (int o = 0; o < 6; o++) {
						try {
							if (o == 0)
								ba[o] = co[k - 2][i];
							if (o == 1)
								ba[o] = co[k - 2][i + 2];
							if (o == 2)
								ba[o] = co[k][i + 2];
							if (o == 3)
								ba[o] = co[k + 2][i + 2];
							if (o == 4)
								ba[o] = co[k + 2][i];
							if (o == 5)
								ba[o] = co[k][i - 2];
						} catch (Exception e) {
							ba[o] = 0;
						}
					}
					b.setNeighbors(ba);
				}
			}

		}

		setCoordinates(co);
	}

	public int[][] getCoordinates() {
		return coordinates;
	}

	private void setCoordinates(int[][] coordinates) {
		this.coordinates = coordinates;
	}

	public HashMap<Integer, Bubble> getMap() {
		return map;
	}

	// brauchen wir die Methode �berhaupt??
	private void setMap(HashMap<Integer, Bubble> map) {
		this.map = map;
	}

	private IShooter getShooter() {
		return shooter;
	}

	// brauchen wir die Methode �berhaupt??
	private void setShooter(IShooter shooter) {
		this.shooter = shooter;
	}

	/**
	 * gets Bubble out of Hashmap
	 * 
	 * @param id
	 *            ID of the bubble
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
	 * Returns id for a Bubble
	 * @return ID for a new Bubble
	 */
	public int getID(){
		return idCounter;
	}

	/**
	 * Method for checking if the new Bubble has more than 2 samecolored
	 * neighbors.
	 * 
	 * @param b
	 *            new Bubble
	 */
	public void colorCheck(Bubble b) {
		ArrayList<Bubble> list = moreColorCheck(getNextSameColoredNeighbors(b
				.getSameColoredNeighbors()));
		if (list.size() > 3) {
			Iterator<Bubble> it = list.iterator();
			while (it.hasNext()) {
				deleteBubble(it.next());
			}
			checkIsland();
		}

	}

	/**
	 * Method for finding sameColored Neighbors.
	 * 
	 * @param list
	 *            Bubbles of first Selection.
	 * @return List of Bubbles with same Color.
	 */
	private ArrayList<Bubble> moreColorCheck(ArrayList<Bubble> list) {
		ArrayList<Bubble> list2 = new ArrayList<Bubble>();
		Iterator<Bubble> it = list.iterator();
		while (it.hasNext()) {
			ArrayList<Bubble> list3 = getNextSameColoredNeighbors(it.next()
					.getSameColoredNeighbors());
			Iterator<Bubble> it2 = list3.iterator();
			while (it2.hasNext()) {
				list2.add(it2.next());
			}
		}
		it = list2.iterator();
		boolean bool = false;
		while (it.hasNext()) {
			Bubble b = it.next();
			if (!list.contains(b)) {
				list.add(b);
				bool = true;
			}
		}
		if (bool == true) {
			list = moreColorCheck(list2);
		}
		return list;

	}

	/**
	 * Getter for all bubbles neighbored to the bubbles in the array
	 * 
	 * @param ba
	 *            Bubbles in the array
	 * @return List of all Bubbles
	 */
	private ArrayList<Bubble> getNextSameColoredNeighbors(Bubble[] ba) {
		ArrayList<Bubble> list = new ArrayList<Bubble>();

		// System.out.println(ba.length + " vor der Forschleife");
		for (int i = 1; i < ba.length; i++) {
			// System.out.println(i + " erste for Schleife");
			// System.out.println(ba[i]);
			if (ba[i] != null) {
				for (int j = 1; j <= ba[i].getSameColoredNeighbors().length; j++) {
					System.out.println(j);
					if (ba[i].getSameColoredNeighbors()[j] != null) {
						if (!list.contains(ba[i].getSameColoredNeighbors()[j]))
							list.add(ba[i].getSameColoredNeighbors()[j]);
					}
				}
			}

		}
		return list;
	}

	/**
	 * Deletes bubble from their neighbors. Deletes bubble from hashmap and
	 * coordinates.
	 * 
	 * @param b
	 *            bubble to delete
	 */
	private void deleteBubble(Bubble b) {
		b.delete();
		map.remove(b);
		deleteFromCoordinates(b);
	}

	/**
	 * Deletes a bubble from the coordinates array
	 * 
	 * @param b
	 *            the bubble to delete
	 */
	private void deleteFromCoordinates(Bubble b) {
		for (int i = 0; i <= 40; i = i++) {
			for (int j = 0; i <= 32; j = j++) {
				if (b.getId() == coordinates[i][j])
					coordinates[i][j] = 0;
			}
		}
	}

	// m�ssen noch einarbeiten das die wall nach unten kommt
	private void checkIsland() {
		// methode ist hier da sie nur einmal ausgef�hrt werden muss. wenn sie
		// in CheckConnection w�re w�rde sie jedesmal ausgef�hrt werden
		ArrayList<Bubble> top = new ArrayList<Bubble>();
		for (int i = 0; i <= 32; i = i + 2) {
			top.add(map.get(coordinates[0][i]));
		}
		// versuch einen Array zu erstellen, der alle Kugeln die eine Verbindung
		// zur Wand haben enth�lt.
		for (int i = 2; i >= 40; i = i + 2) {
			for (int j = 0; i <= 32; j = j + 2) { // eventuell muss man die
													// geichen schleife nochmal
													// ausf�hren nur diesmal
													// abw�rtsz�hlend weil wenn
													// eine kugel von links
													// angebunden ist dann kommt
													// sie nciht in den array
				if (coordinates[i][j] != 0) {
					if (!map.get(coordinates[i][j]).gotNeighbor())
						deleteBubble(map.get(coordinates[i][j])); // ist dies If
																	// Abfrage
																	// �berfl�ssig
																	// weil die
																	// K�gel
																	// w�rde ja
																	// unten ehh
																	// rausfliegen.
					else {
						for (int k = 0; k <= 6; k++) {
							if (map.get(coordinates[i][j]).getNeighbors()[k] != 0) {
								if (top.contains(map.get(coordinates[i][j])
										.getNeighbors()[k])) {
									top.add(map.get(coordinates[i][j]));
									break;
								}
							}
						}

					}

				}

			}
		}
		// hier werden die Kugel letzendlich gel�scht.
		for (int i = 2; i >= 40; i = i + 2) {
			for (int j = 0; i <= 32; j = j + 2) {
				if (!top.contains(map.get(coordinates[i][j])))
					deleteBubble(map.get(coordinates[i][j]));
			}

		}
		/*
		 * Versuch f�r jede Kugel zu �berpr�fen ob sie ein Verbindung zur wand
		 * hat. for (int i=40;i>=2;i--){ for (int j=0;i<=32;j++){
		 * if(!map.get(coordinates[i][j]).gotNeighbor())
		 * deleteBubble(map.get(coordinates[i][j])); else{
		 * if(!checkConnectionToWall(map.get(coordinates[i][j]),top))
		 * deleteBubble(map.get(coordinates[i][j])); }
		 * 
		 * } }
		 */

		// checkt ob Inseln von Kugeln bestehen
	}

	/*
	 * Problem bei der Methode ist das man noch eine L�sung finden m�sste wie
	 * man es schafft das er in einem zweiten array alle speichert die er schon
	 * durchsucht hat. ich denke das wir dann ganz unhandlich private boolean
	 * checkConnectionToWall(Bubble b,ArrayList list){ int[]
	 * neighbors=b.getNeighbors(); for(int i=0;i<=6;i++){ if(neighbors[i]!=0){
	 * if(list.contains(map.get(neighbors[i])))return true;
	 * if(checkConnectionToWall(map.get(neighbors[i]),list))return true; }
	 * 
	 * 
	 * }
	 * 
	 * return null; }
	 */

	/**
	 * Puts Bubble into Hashmap
	 * 
	 * @param b
	 *            Bubble to put in Hashmap
	 */

	public void addBubbleToHashMap(Bubble b) {
		lastAdded = b;
		map.put(b.getId(), b);

	}
}
