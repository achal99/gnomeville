import java.util.ArrayList;

public class Village {
	
	private int xLoc;
	private int yLoc;
	private ArrayList<Gnome> residents = new ArrayList<Gnome>();
	private String name;
	private VillageNode location;
	private double rent;
	private double totalMoney;
	
	public Village(int xLoc, int yLoc){
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		name = "Village "+ randomNameGenerator();
	}
	
	public int getXLoc(){
		return xLoc;
	}
	public int getYLoc(){
		return yLoc;
	}
	public String getName(){
		return name;
	}
	//Changed
	public ArrayList<Gnome> getResidents(){
		return residents;
	}
	//Changed
	public double getRent(){
		return rent;
	}
	//Changed
	public void setTotalMoney(double amt){
		totalMoney += amt;
	}
	//Changed
	public double getTotalMoney(){
		return totalMoney;
	}
	
	public VillageNode getVillageNode(){
		return location;
	}
	public void setVillageNode(VillageNode n){
		location = n;
	}
	//Changed
	public String toString(){
		return name;
	}
	private String randomNameGenerator(){ //generates a random name
		String[] cons = {"b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "r", "s", "t", "v", "w", "z"};
		String[] vowels = {"a", "e", "i", "o", "u"};
		String name = cons[(int)(Math.random() * cons.length)] + vowels[(int)(Math.random() * vowels.length)] + cons[(int)(Math.random() * cons.length)]+vowels[(int)(Math.random() * vowels.length)];
		return name;
	}
	
//	public LystIterator<Gnome> findGnome(Gnome end) throws NotFoundException {
//		if(residents.isEmpty()) { //checks if the edgelist is empty or its a real
//			throw new NotFoundException();
//		}
//		boolean found = false;
//		LystIterator<Gnome> where = null;
//		for (LystIterator<Gnome> i = new LystIterator(residents.getFront());!i.pastEnd() && !found; i.increment()) {
//			if(((Gnome) i.getCurrent().getData()).equals(end)) {
//				where = i;
//				found = true;
//				break;
//			}
//			} //end of for loop
//		if(found) {
//			return where;
//		} else {
//			return null;
//		}
//	}
	
	public void leave(Gnome g){
		residents.remove(g);
	}
	
	public void addVillager(Gnome g){
		residents.add(g);
	}
}
