package kay;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SettingFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 460, HEIGHT = 275;
	private JTextField numTF;
	private JButton checkB, okB;
	private URL url;
	private boolean flag = false;// false means it's not selected, true means
									// it's selected
	private int percent=50,totalNum=50;

	public SettingFrame() {
		init();
		initValues();
		this.setLayout(new BorderLayout());
		
		url=getClass().getResource("/image/pc.png");
		JPanel backPanel=new ImagePanel(url);
		backPanel.setPreferredSize(new Dimension(374,HEIGHT));
		// numPanel
		url=getClass().getResource("/image/percent.png");
		ImagePanel percentPanel = new ImagePanel(url);
		percentPanel.setPreferredSize(new Dimension(374,114));
		percentPanel.setLayout(new BorderLayout());
		
		//JPanel to contain TextField
		JPanel bgPanel=new JPanel();
		bgPanel.setPreferredSize(new Dimension(380,50));
		bgPanel.setLayout(new BorderLayout());
		
		url=getClass().getResource("/image/percent_bg.png");
		ImagePanel blankPanel=new ImagePanel(url);
		blankPanel.setPreferredSize(new Dimension(140,52));

		numTF = new JTextField();
		
		url=getClass().getResource("/image/percent_mark.png");
		ImagePanel percentMarkPanel=new ImagePanel(url);
		percentMarkPanel.setPreferredSize(new Dimension(140,52));

		bgPanel.add(blankPanel,BorderLayout.WEST);
		bgPanel.add(percentMarkPanel,BorderLayout.EAST);
		bgPanel.add(numTF,BorderLayout.CENTER);
		
		percentPanel.add(bgPanel, BorderLayout.SOUTH);
		// answerPanel
		url=getClass().getResource("/image/recentAnswer.png");
		ImagePanel answerPanel = new ImagePanel(url);
		answerPanel.setPreferredSize(new Dimension(374,117));
		answerPanel.setLayout(new BorderLayout());

		/*
		url=getClass().getResource("/image/recentAnswer.png");
		ImageIcon img = new ImageIcon(url);
		answerLabel = new JLabel(img);*/
		
		if(flag){
			url=getClass().getResource("/image/selected.png");
		}
		else{
			url=getClass().getResource("/image/unselected.png");
		}
		checkB = new ImageButton(new ImageIcon(url));
		checkB.addActionListener(this);
		answerPanel.add(checkB, BorderLayout.SOUTH);

		// okButton
		url=getClass().getResource("/image/go.png");
		okB = new ImageButton(new ImageIcon(url));
		okB.addActionListener(this);

		backPanel.add(percentPanel,BorderLayout.NORTH);
		backPanel.add(answerPanel,BorderLayout.SOUTH);
		this.add(backPanel,BorderLayout.WEST);
		this.add(okB, BorderLayout.EAST);
	}

	private void init() {
		this.setSize(WIDTH, HEIGHT);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int h = (int) d.getHeight() / 3;
		int w = (int) d.getWidth() / 3;
		this.setLocation(w, h);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		BufferedImage backgroundImage=null;
		url=this.getClass().getResource("/image/pc.png");
		try {
			backgroundImage = ImageIO.read(url);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.setContentPane(new ImageBackground(backgroundImage));
	}
	
	private void initValues() {
		Properties property = new Properties();
		String trait = null;
		try {
			property.load(new FileInputStream("properties"));
			if(property.containsKey("selected")){
				trait = property.getProperty("selected");
				if (!"".equals(trait)) {//have key and value
					flag = Boolean.parseBoolean(trait);
				}/*
				else{//have key no value
					System.out.println("flag1:"+flag);
				}*/
			}/*
			else{
				System.out.println("flag2:"+flag);
			}*/
			if(property.containsKey("totalNum")){
				trait = property.getProperty("totalNum");
				if (!"".equals(trait)) {//have key and value
					totalNum = Integer.parseInt(trait);
				}
			}
			if(property.containsKey("percent")){
				trait = property.getProperty("percent");
				if (!"".equals(trait)) {//have key and value
					percent = Integer.parseInt(trait);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setValues(Boolean flag,int percent,int totalNum){
		Properties property= new Properties();
		property.setProperty("selected", toString(flag));
		property.setProperty("percent", ""+percent);
		property.setProperty("totalNum", ""+totalNum);
		try {
			property.store(new FileOutputStream("properties"),
					"properties");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private String toString(boolean flag) {
		return flag ? "true" : "false";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		boolean flag = false;
		if (e.getSource() == okB) {
			String input = numTF.getText();
			int num = input.length();
			for (int i = 0; i < num; i++) {
				char c = input.charAt(i);
				if (c < '0' || c > '9') {
					flag = true;
					break;
				}
			}
			if (flag) {
				numTF.setText("");
			} else {
				percent=Integer.parseInt(input);
				setValues(this.flag,this.percent,this.totalNum);
			}
			this.dispose();
		} else if (e.getSource() == checkB) {
			this.flag = !this.flag;
			new Thread(new changeBackground()).start();
			setValues(this.flag,this.percent,this.totalNum);
		}
	}

	private class changeBackground implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (flag) {
				url=getClass().getResource("/image/selected.png");
				ImageIcon img = new ImageIcon(url);
				checkB.setIcon(img);
			} else {
				url=getClass().getResource("/image/unselected.png");
				ImageIcon img = new ImageIcon(url);
				checkB.setIcon(img);
			}
		}

	}

	private class ImageBackground extends JComponent{
		private Image image;
		public ImageBackground(Image image){
			this.image=image;
		}
		protected void paintComponent(Graphics g){
			g.drawImage(image, 0, 0, null);
		}
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

	private class ImageButton extends JButton {
		private static final long serialVersionUID = -1735835064795832595L;

		public ImageButton(ImageIcon icon) {
			setSize(icon.getImage().getWidth(null),
					icon.getImage().getHeight(null));
		    setIcon(icon);
			setMargin(new Insets(0, 0, 0, 0));// 将边框外的上下左右空间设置为0
			setIconTextGap(0);// 将标签中显示的文本和图标之间的间隔量设置为0
			setBorderPainted(false);// 不打印边框
			setBorder(null);// 除去边框
			setText(null);// 除去按钮的默认名称
			setFocusPainted(false);// 除去焦点的框
			setContentAreaFilled(false);// 除去默认的背景填充
		}
	}
}
