package kay;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class QuestionWin extends JFrame{
	private static final int STRING_SIZE=128;
	private String path="",tempPath="",line="",id="",question="",formatS="";
	private BufferedReader bufferedReader=null;
	private JScrollPane scrollPane;
	private JPanel panel;
	private File file;
	private StringBuilder stringBuilder;
	
	public QuestionWin(){
		URL url;
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int h = (int) d.getHeight() / 3;
		int w = (int) d.getWidth() / 3;
		this.setLocation(w, h);
		this.setVisible(true);
		this.setSize(355,385);
		this.setTitle("学生提问");
		//this.setResizable(false);
		/*
		URL url=this.getClass().getResource("/image/pc.png");
		BufferedImage background = null;
		try {
			background = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setContentPane(new ImageBackground(background));*/
		
		panel=new JPanel();
		panel.setPreferredSize(new Dimension(326,5000));
		scrollPane=new JScrollPane(panel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		panel.setLayout(new FlowLayout());
		//panel.setLayout(new GridBagLayout());
		//GridBagConstraints c = new GridBagConstraints();
		
		this.setContentPane(scrollPane);
		//this.setContentPane(panel);
		
		tempPath=getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		try {
			tempPath=URLDecoder.decode(tempPath,"utf-8");
		} catch (UnsupportedEncodingException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		path=tempPath.substring(0,tempPath.lastIndexOf("/"))+ "/file/";
		file=new File(path+"FileQuestion.txt");
		if(file.exists()){
			int j=0;
			try {
				bufferedReader = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while(true){
				try {
					line=bufferedReader.readLine();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if ("".equals(line)||line==null){
					break;
				}
				int index=line.indexOf(':');
				id=line.substring(0,index);
				question=line.substring(index+1);
				int num=j%4;
				url=getClass().getResource("/image/bg"+num+".png");
				ImageIcon image=new ImageIcon(url);
				//image.setImage(image.getImage().getScaledInstance(150,150,Image.SCALE_DEFAULT));
				JLabel promptL=new ImageLabel(image);
				promptL.setPreferredSize(new Dimension(150,150));
				promptL.setOpaque(false);
				stringBuilder=new StringBuilder(STRING_SIZE);
				stringBuilder.append("<html>");
				stringBuilder.append("<table border='0'>");
				stringBuilder.append("<tr>");
				stringBuilder.append("<td align='top'>"+id+":</td>");
				stringBuilder.append("</tr>");
				while(true){
					String tempS;
					boolean flag=false;
					int length=question.length();
					if(length>10){
						tempS=question.substring(0,10);
						question=question.substring(10);
					}
					else{
						tempS=question.substring(0);
						flag=true;
					}
					stringBuilder.append("<tr>");
					stringBuilder.append("<td align='left'>"+tempS+"</td>");
					stringBuilder.append("</tr>");
					if(flag){
						stringBuilder.append("</table>");
						stringBuilder.append("</html>");
						formatS=stringBuilder.toString();
						break;
					}
				}
				promptL.setText(formatS);
				/*
				num=j%2;
				if(num==0){
					c.gridx=(j/2);
					c.gridy=0;
					c.fill=GridBagConstraints.HORIZONTAL;
				}
				else if(num==1){
					c.gridx=(j/2);
					c.gridy=0;
					c.fill=GridBagConstraints.HORIZONTAL;
				}
				panel.add(promptL,c);*/
				panel.add(promptL,FlowLayout.LEFT);
				//new dispContent();
				j++;
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
	
	private class ImageLabel extends JLabel{
		ImageIcon image;
		public ImageLabel(ImageIcon image){
			 this.image=image;
		}
		public void paintComponent(Graphics g) {
	        g.drawImage(image.getImage(), 0, 0, null);
	        super.paintComponent(g);
	      }
	}

}
