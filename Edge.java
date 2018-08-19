import java.awt.*;

public class Edge{
	
	private double price;
	private int distance;
	private double totalMoney; //Changed
	private VillageNode starter;
	private VillageNode ender;
	private Que<Gnome> gnomelist = new Que();
	
	
	public Edge(int prc,int dstance, VillageNode strt, VillageNode end) {
		price = prc;
		distance = dstance;
		starter = strt;
		ender = end;
	}
	
	public VillageNode getEnd() {
		return ender;
	}
	
	public VillageNode getStart() {
		return starter;
	}
	
	public double getPrice(){
		return price;
	}
	public int getDistance(){
		return distance;
	}
	public void setTotalMoney(double amt){
		totalMoney += amt;
	}
	public double getTotalMoney(){
		return totalMoney;
	}

	public void draw(Graphics2D g2, int w){
		g2.setColor(new Color(255, 0, 255));
		g2.setStroke(new BasicStroke(5, BasicStroke.JOIN_MITER, BasicStroke.CAP_ROUND));
		int offset = 5;
		if (ender.getVillage().getXLoc() > starter.getVillage().getXLoc()){
			g2.setColor(new Color(0, 255, 255));
			offset = -5;
		}
		g2.drawLine(starter.getVillage().getXLoc() * w / 30 + w / 60, 50 + (starter.getVillage().getYLoc()) * w / 30 + w / 60 + offset, ender.getVillage().getXLoc() * w / 30 + w / 60, 50 + (ender.getVillage().getYLoc()) * w / 30 + w / 60 + offset);
		

		//		g2.drawLine(ender.getVillage().getXLoc() * w / 30 - (int)(Math.cos(angle) * distance), 
//				ender.getVillage().getYLoc() * w / 30 - (int)(Math.sin(angle) * distance), 
//				(int)(Math.cos(angle - Math.PI/4) * 15 + ender.getVillage().getXLoc() * w / 30 - (int)(Math.cos(angle) * distance)),
//				(int)(Math.sin(angle - Math.PI/4) * 15 + ender.getVillage().getYLoc() * w / 30 - (int)(Math.sin(angle) * distance)));
//		g2.drawLine(ender.getVillage().getXLoc() * w / 30 - (int)(Math.cos(angle) * distance), 
//				ender.getVillage().getYLoc() * w / 30 - (int)(Math.sin(angle) * distance), 
//				(int)(Math.cos(angle + Math.PI/4) * 15 + ender.getVillage().getXLoc() * w / 30 - (int)(Math.cos(angle) * distance)),
//				(int)(Math.sin(angle + Math.PI/4) * 15 + ender.getVillage().getYLoc() * w / 30 - (int)(Math.sin(angle) * distance)));
		g2.setStroke(new BasicStroke(1, BasicStroke.JOIN_MITER, BasicStroke.CAP_ROUND));
		g2.setColor(Color.black);
		g2.drawString("" + distance, (starter.getVillage().getXLoc() + ender.getVillage().getXLoc()) * w / 60, (starter.getVillage().getYLoc() + ender.getVillage().getYLoc() + 4) * w / 60);
	}
	

		


}
