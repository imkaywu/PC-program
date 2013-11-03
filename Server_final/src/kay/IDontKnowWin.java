package kay;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class IDontKnowWin extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 192, HEIGHT = 150;// 软件界面的长宽
	private ArrayList<String> userList1 = new ArrayList<String>();
	
	public IDontKnowWin(ArrayList<String> userList){
		this.userList1=userList;
		URL url=getClass().getResource("/image/idontknow.png");
		//System.out.println(""+url);
		ImagePanel promptP=new ImagePanel(url);
		this.setContentPane(promptP);
		init();
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ClientServerThread.setFirstTime();
				userList1.clear();
			}
		});
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	
	void init() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int h = (int) d.getHeight() - HEIGHT;
		int w = (int) d.getWidth() - WIDTH;// 在屏幕上定位
		this.setLocation(w, h);
		this.setTitle("学生听不懂了。。。");
		this.setSize(WIDTH, HEIGHT);
		this.setVisible(true);
		this.setResizable(false);
	}
	
	private class ImagePanel extends JPanel {
		private static final long serialVersionUID = 1322033879006651787L;
		private Image image;

		public ImagePanel(URL url) {
			try {
				image = ImageIO.read(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image, 0, 0, this);
		}
	}
}
