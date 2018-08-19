import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ThePanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

	private Timer timer;
	private JButton Start;
	private JButton addVillage;
	private JButton addRoad;
	private JButton Exit;
	private JButton DataPlayer;
	private boolean adVil, adRd, data, show;
	private Point mouse;
	private int makeRd;
	private VillageNode temp, dataTar;
	private ArrayList<Gnome> gnomes;
	private ArrayList<VillageNode> villages;
	private PlayerGnome p;

	public static void main(String[] args) {
		JFrame window = new JFrame("GnomenWald ");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(0, 0, 1440, 880);
		ThePanel panel = new ThePanel();
		window.add(panel);
		window.setVisible(true);
		panel.setFocusable(true);
		panel.requestFocusInWindow();
	}

	public ThePanel() {
		timer = new Timer(50, this);
		Start = new JButton("Click to Start");
		Start.addActionListener(this);
		DataPlayer = new JButton("Click to toggle between destination selection and data reading");
		DataPlayer.addActionListener(this);
		addVillage = new JButton("Add Village");
		addVillage.addActionListener(this);
		addRoad = new JButton("Add Road");
		addRoad.addActionListener(this);
		Exit = new JButton("Exit");
		Exit.addActionListener(this);
		mouse = new Point(0, 0);
		this.add(Start);
		this.add(addVillage);
		this.add(addRoad);
		this.add(Exit);
		this.add(DataPlayer);
		DataPlayer.setVisible(false);
		Exit.setVisible(false);
		adVil = false;
		adRd = false;
		data = true;
		makeRd = 0;
		timer.start();
		villages = new ArrayList<VillageNode>();
		gnomes = new ArrayList<Gnome>();
		addMouseListener(this);
		addMouseMotionListener(this);
		//THIS IS A INTIAL TEST CASE WHICH CAN BE USED IF UNCOMMENTED
		VillageNode tempnode = new VillageNode(2, 5);
		p = new PlayerGnome(tempnode);
		tempnode.getVillage().addVillager(p);
		for (int i = 0; i < 5; i++) {
			villages.add(new VillageNode(i * 6, i));
			for (int j = 0; j < 5; j++) {
				gnomes.add(new Gnome(villages.get(i)));
				villages.get(i).getVillage().addVillager(gnomes.get(i * 5 + j));
			}
			DirectedGraph.addNode(villages.get(i));
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.white);
		g2.fillRect(0, 0, 100000, 100000);
		g2.setColor(Color.black);
		ArrayList<Edge> roads = DirectedGraph.getEdges();
		for (int i = 0; i < roads.size(); i++) {
			roads.get(i).draw(g2, getWidth());
		}
		ArrayList<VillageNode> villages = DirectedGraph.getVillages();
		for (int i = 0; i < villages.size(); i++) {
			villages.get(i).draw(g2, getWidth());
			for (int j = 0; j < villages.get(i).getVillage().getResidents().size(); j++) {
				if (villages.get(i).getVillage().getResidents().get(j) != null
						&& villages.get(i).getVillage().getResidents().get(j).getDisttoDest() != 0) {
					villages.get(i).getVillage().getResidents().get(j).draw(g2, getWidth());
				}
			}
		}

		g2.setColor(new Color(10, 10, 10));
		for (int i = 0; i < 30; i++) {
			int x = getWidth() * i / 30;
			g2.drawLine(x, 50, x, getWidth() * 16 / 30 - 2);
		}
		for (int i = 0; i < (getHeight() - 50) / (getWidth() / 30) + 1; i++) {
			int y = 50 + getWidth() / 30 * i;
			g2.drawLine(0, y, getWidth(), y);
		}
		g2.setColor(new Color(0, 255, 0));
		if (adVil == true || adRd == true) {
			g2.drawOval((int) (mouse.getX()) / (getWidth() / 30) * getWidth() / 30,
					50 + (int) (mouse.getY() - 50) / (getWidth() / 30) * getWidth() / 30, getWidth() / 30,
					getWidth() / 30);
		}
		g2.setColor(Color.BLACK);
		if (show) {
			g2.fillRect(0, 0, 100, (dataTar.getVillage().getResidents().size() + 2) * 12);
			g2.drawRect(0, 0, 100, (dataTar.getVillage().getResidents().size() + 2) * 12);
			g2.setColor(Color.white);
			g2.drawString(dataTar.getVillage().getName(), 0, 12);
			for (int i = 0; i < dataTar.getVillage().getResidents().size(); i++) {
				g2.drawString(dataTar.getVillage().getResidents().get(i).getName(), 0, 12 * (i + 2));
			}
		}
		p.draw(g2, getWidth());
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (actionEvent.getSource() == Start) {
			Start.setVisible(false);
			addVillage.setVisible(false);
			addRoad.setVisible(false);
			Exit.setVisible(true);
			new Thread(p).start();
			DataPlayer.setVisible(true);
			for (int i = 0; i < gnomes.size(); i++) {
				new Thread(gnomes.get(i)).start();
			}
			 new Thread(new AddRemove()).start();
		} else if (actionEvent.getSource() == addVillage) {
			adVil = true;
			adRd = false;
		} else if (actionEvent.getSource() == addRoad) {
			adRd = true;
			adVil = false;
		} else if (actionEvent.getSource() == Exit) {
			System.exit(0);
		} else if (actionEvent.getSource() == DataPlayer) {
			data = !data;
			if (!data) {
				show = false;
			}
		}
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (adVil == true) {
			int x = (e.getX()) / (getWidth() / 30);
			int y = (e.getY() - 50) / (getWidth() / 30);
			if (!DirectedGraph.VillageExists(x, y))
				DirectedGraph.addNode(new VillageNode(x, y));
			adVil = false;
		}
		if (adRd == true) {
			if (makeRd == 0) {
				temp = DirectedGraph.getVillage(e.getX() / (getWidth() / 30), (e.getY() - 50) / (getWidth() / 30));
				if (temp != null) {
					makeRd++;
				}
			} else {
				VillageNode n = DirectedGraph.getVillage(e.getX() / (getWidth() / 30),
						(e.getY() - 50) / (getWidth() / 30));
				if (n != null) {
					int length = (int) Math.sqrt(Math.pow(n.getVillage().getXLoc() - temp.getVillage().getXLoc(), 2)
							+ Math.pow(n.getVillage().getYLoc() - temp.getVillage().getYLoc(), 2));
					// Edge edge =new Edge(10, length, temp,
					// DirectedGraph.getVillage(e.getX() / (getWidth() / 30),
					// (e.getY() - 50) / (getWidth() / 30)));
					try {
						temp.addEdge((int) (Math.random() * 500) + 1000, length, DirectedGraph
								.getVillage(e.getX() / (getWidth() / 30), (e.getY() - 50) / (getWidth() / 30)));
					} catch (AlreadyExistsException e1) {
					}
					makeRd = 0;
					adRd = false;
				}
			}
		}
		if (data && !adVil && !adRd) {
			int x = e.getX() / (getWidth() / 30);
			int y = (e.getY() - 50) / (getWidth() / 30);
			if (DirectedGraph.VillageExists(x, y)) {
				show = true;
				dataTar = DirectedGraph.getVillage(x, y);
			} else {
				show = false;
			}
		}
		if (!data) {
			int x = e.getX() / (getWidth() / 30);
			int y = (e.getY() - 50) / (getWidth() / 30);
			VillageNode tempnode = null;
			if (DirectedGraph.VillageExists(x,  y)){
				tempnode = DirectedGraph.getVillage(x, y);
			}
			p.setVillage(tempnode);

		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if (adVil == true || adRd == true) {
			mouse = new Point(e.getX(), e.getY());
		}
	}
}