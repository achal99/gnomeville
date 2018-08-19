import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class Gnome implements Runnable {

	private double balance;
	private String name;
	protected Village currentVillage;
	private Edge currentRoad;
	protected Village destination;
	protected int xLoc;
	protected int yLoc;
	protected int lazyLevel;
	private int moveRate;
	private int distToDest = 0;
	private Que<String> passport; // List of villages that the gnome has been to
	private static int a = (int) (Math.random() * 1000);
	private static int b = (int) (Math.random() * 1000);
	private static int c = (int) (Math.random() * 1000); 
	// Also known as the key
	private int xKey;
	private int yKey;

	public String getName() {
		return name;
	}

	public Gnome(VillageNode v) {
		this(200, (int) (Math.random() * 3) + 1, (int) (Math.random() * 5) + 2, 0, 0, null);
		currentVillage = v.getVillage();
		DirectedGraph.addGnome(this);
	}

	public Edge currentRoad() {
		return currentRoad;
	}

	public Gnome(double balance, int lazyLevel, int moveRate, int xLoc, int yLoc, Village v) {
		this.balance = balance;
		this.lazyLevel = lazyLevel;
		this.moveRate = moveRate;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		currentVillage = v;
		xKey = (int) (Math.random() * 1000);
		yKey = (int) (a * Math.pow(xKey, 2) + b * xKey + c);
		name = "Gnome " + randomNameGenerator();
	}

	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			int index = (int) (Math.random() * DirectedGraph.getVillages().size());
			VillageNode dest = DirectedGraph.getVillages().get(index);
			// choses destination
			while (dest.getVillage().equals(currentVillage)) {
				index = (int) (Math.random() * DirectedGraph.getVillages().size());
				dest = DirectedGraph.getVillages().get(index);
			}
			// MOVES or not depending on lazy level towards destination
			if (lazyLevel == 1) {
				lazyLevel = (int) (Math.random() * 3 + 1);
				// run();
			} else if (lazyLevel == 2) {
				DjikstraAlgorithmPrice dj = new DjikstraAlgorithmPrice();
				LinkedList<VillageNode> path = new LinkedList<VillageNode>();
				while (path != null) {
					dj.execute(currentVillage.getVillageNode());
					path = dj.getPath(dest);
				}
				path.pop();
				while (!path.isEmpty()) {
					basicMove(path.getFirst());
					if (destination == null) {
						path.pop();
					}
					try {
						Thread.sleep(2000);
					} catch (Exception e) {
						;
					}
				}
				lazyLevel = (int) (Math.random() * 3 + 1);
			} else if (lazyLevel == 3) {
				DjikstraAlgorithm dj = new DjikstraAlgorithm();
				dj.execute(currentVillage.getVillageNode());
				LinkedList<VillageNode> path = dj.getPath(dest);
				while (path == null){
					dj.execute(currentVillage.getVillageNode());
					path = dj.getPath(dest);
				}
				path.removeFirst();
				while (!path.isEmpty()) {
					basicMove(path.getFirst());
					if (destination == null) {
						path.removeFirst();
					}
					try {
						Thread.sleep(2000);
					} catch (Exception e) {
					}					
				}
					lazyLevel = (int) (Math.random() * 3 + 1);
				
			}
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
				;
			}
		}
	}

	private VillageNode getRandomplace() {
		int index = (int) (Math.random() * DirectedGraph.getVillages().size());
		VillageNode dest = DirectedGraph.getVillages().get(index);
		while (dest.getVillage().equals(currentVillage)) {
			index = (int) (Math.random() * DirectedGraph.getVillages().size());
			dest = DirectedGraph.getVillages().get(index);
		}
		return dest;
	}

	public int getDisttoDest() {
		return distToDest;
	}

	public void basicMove(VillageNode dest) {
		// moves for the gnome to a target village
		try {
			if (destination == null || !destination.equals(dest.getVillage())) {
				destination = dest.getVillage();
				distToDest = currentVillage.getVillageNode().findEdge(dest).getCurrent().getData().getDistance();
			}
			distToDest -= moveRate;
			if (distToDest <= 0) {
				System.out.println("I'm here!");
				currentVillage.leave(this);
				currentVillage = dest.getVillage();
				dest.getVillage().addVillager(this);
				xLoc = dest.getVillage().getXLoc();
				yLoc = dest.getVillage().getYLoc();
				destination = null;
				distToDest = 0;
				lazyLevel = (int) (Math.random() * 3) + 1;
			} else {
				System.out.println("Still walking");
				currentRoad = currentVillage.getVillageNode().findEdge(dest).getCurrent().getData();
				double totalDistance = (double) currentVillage.getVillageNode().findEdge(dest).getCurrent().getData()
						.getDistance();
				xLoc = (int) (currentVillage.getXLoc() + (((totalDistance - distToDest) / totalDistance))
						* (dest.getVillage().getXLoc() - currentVillage.getXLoc()));
				yLoc = (int) (currentVillage.getYLoc() + (((totalDistance - distToDest) / totalDistance))
						* (dest.getVillage().getYLoc() - currentVillage.getYLoc()));
			}
			System.out.println(xLoc + "," + yLoc);
		} catch (NotFoundException nfe) {
			System.out.println("Crap");
			System.out.println(xLoc + "," + yLoc);
			destination = null;
		}
	}

	public void pay(Edge e) {//pay for movement not truly implemented
		balance -= e.getPrice();
		e.setTotalMoney(e.getPrice());
	}

	public void pay(Village v) {//pay for staying in village not truely implemented
		balance -= v.getRent();
		v.setTotalMoney(v.getRent());
		;

	}

	public boolean authorized(double x1, double y1, double x2, double y2, double x3, double y3) {
		//authorization for location finding not truly implemented
		double keyNumerator = (y1 * x3 - y3 * x1) * (x1 * x1 * x2 - x2 * x2 * x1)
				+ y2 * x1 * (x1 * x1 * x3 - x3 * x3 * x1) - y1 * x2 * (x1 * x1 * x3 - x3 * x3 * x1);
		double keyDenominator = x1 * (x1 * x1 * x3 - x3 * x3 * x1) - x2 * (x1 * x1 * x3 - x3 * x3 * x1)
				+ x3 * (x1 * x1 * x2 - x2 * x2 * x1) - x1 * (x1 * x1 * x2 - x2 * x2 * x1);
		int keyTry = (int) Math.round((keyNumerator / keyDenominator));
		if (keyTry == c) {
			return true;
		} else {
			return false;
		}
	}

	public int[] getCoords(Que<Gnome> gnomes) {
		//authorization for location finding not truly implemented
		Gnome first = null;
		Gnome second = null;
		Gnome third = null;
		int[] coords = new int[2];
		for (LystIterator<Gnome> i = new LystIterator(gnomes.getFront()); !i.pastEnd(); i.increment()) {
			double chance = Math.random();
			if (chance >= .95) {
				if (first == null) {
					first = i.getCurrent().getData();
				} else if (second == null) {
					second = i.getCurrent().getData();
				} else if (third == null) {
					third = i.getCurrent().getData();
				} else
					;
			}
		}
		try {
			if (authorized(first.getXKey(), first.getYKey(), second.getXKey(), second.getYKey(), third.getXKey(),
					third.getYKey())) {
				coords[0] = xLoc;
				coords[1] = yLoc;
				return coords;
			} else {
				coords[0] = -1;
				coords[1] = -1;
				System.out.println("Access denied");
				return coords;
			}
		} catch (NullPointerException npe) {
			System.out.println("Access denied");
			coords[0] = -1;
			coords[1] = -1;
			return coords;
		}

	}

	public void setXLoc(int xLoc) {
		this.xLoc = xLoc;
	}

	public void setYLoc(int yLoc) {
		this.yLoc = yLoc;
	}

	public int getXKey() {
		return xKey;
	}

	public int getYKey() {
		return yKey;
	}

	public Village getCurrent() {
		return currentVillage;
	}

	public Village getDestination() {
		return destination;
	}

	public void setDestination(Village v) {
		destination = v;
	}

	public void setCurrent(Village v) {
		currentVillage = v;
		currentRoad = null;
		xLoc = currentVillage.getXLoc();
		yLoc = currentVillage.getYLoc();
	}

	public void setCurrent(Edge e) {
		currentRoad = e;
		currentVillage = null;
	}

	// Delete later
	public int getXLoc() {
		return xLoc;
	}

	public int getYLoc() {
		return yLoc;
	}

	private String randomNameGenerator() { // generates a random name and then
											// creates a person
		String[] cons = { "b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "r", "s", "t", "v", "w", "z" };
		String[] vowels = { "a", "e", "i", "o", "u" };
		String name = cons[(int) (Math.random() * cons.length)] + vowels[(int) (Math.random() * vowels.length)]
				+ cons[(int) (Math.random() * cons.length)] + vowels[(int) (Math.random() * vowels.length)];
		return name;
	}

	private static BufferedImage pic;
	static {
		try {
			pic = ImageIO.read(new File("res/" + "gnome.png"));
		} catch (IOException e) {
			System.out.println("Didn't find the file.");
		}
	}

	public void draw(Graphics2D g2, int w) {
		// TODO Auto-generated method stub
		g2.drawImage(pic, xLoc * w / 30 - 10, (yLoc) * w / 30 + 50, null);
	}

}
