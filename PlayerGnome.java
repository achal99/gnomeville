import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class PlayerGnome extends Gnome{
	//Exactly like gnome except destination can be selected by user
	private boolean manual;
	private VillageNode dest = null;
	private static BufferedImage pic;
	
	
	public PlayerGnome(VillageNode n){
		super(n);
		super.setXLoc(n.getVillage().getXLoc());
		super.setYLoc(n.getVillage().getYLoc());
	}
	
	public void run(){
		while(true){
			if(dest == null){
				;
			}else{
			DjikstraAlgorithm dj = new DjikstraAlgorithm();
			dj.execute(currentVillage.getVillageNode());
			LinkedList<VillageNode> path = dj.getPath(dest);
			if(path == null) {
				System.out.println("null path");
				//run();
			} else {
				path.removeFirst();
				while(!path.isEmpty()){
					basicMove(path.getFirst());
					if(destination == null){
						path.removeFirst();
					}
					try{
						Thread.sleep(4000);
					}catch(Exception e){
						;
					}
				}
			}
		}
		try{
			Thread.sleep(4000);
		}catch(Exception e){
			;
		}
	}
}
	
	static{
        try{
            pic = ImageIO.read(new File("res/" + "Player.png"));
        }catch(IOException e){
            System.out.println("Didn't find the file.");
        }
    }
	public void draw(Graphics2D g2, int w) {
		// TODO Auto-generated method stub
        g2.drawImage(pic, xLoc * w / 30 - 10, (yLoc) * w / 30 + 50, null);
	}
	
	public void setVillage(VillageNode v){
		dest = v;
	}
	public void toggleControl(){
		manual = !manual;
	}

}
