
public class AddRemove implements Runnable{

	public void run(){
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		while(true){
			int option = (int) (Math.random()*2);
			System.out.println(option);
			if(option == 0){
				DirectedGraph.addVillage(new VillageNode((int)(Math.random()*30),(int)(Math.random()*15)));
			}/* else if(option == 1){
				boolean destroying = true;
				int destroyIndex = (int) Math.random()*DirectedGraph.getVillages().size();
				VillageNode destroyed = DirectedGraph.getVillages().get(destroyIndex);
				if(destroyed.getVillage().getResidents().size() != 0){
					destroying = false;
				}
				while(destroying){
					synchronized(destroyed){
						DirectedGraph.evacuate(destroyed);
					}
					destroying = false;
				}

				try{
					DirectedGraph.removeNode(destroyed);
					System.out.println(destroyed.getVillage() + " has been destroyed");
				}catch(Exception e){
					;
				}*/
			
			try{
				Thread.sleep(10000);
			}catch(Exception e){
			}
		}
	}
}