package kay;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RefineryUtilities;

public class MainUI extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 695, HEIGHT = 330;
	private int totalNum = 50,percent=50;
	private boolean flag = false;
	private Thread serverThread;
	private ImageButton checkB, answerB, settingB,questionB, dispTrendB, dispPerfB;
	private JFileChooser fileChooser = new JFileChooser(".");
	private ArrayList<String> idList = new ArrayList<String>();
	private DefaultPieDataset defaultPieDataset;
	private String tempPath="",path="";
	private URL url;

	public MainUI() {
		init();
		//initValues();

		this.setLayout(new BorderLayout(5,5));
		// west panel
		url=this.getClass().getResource("/image/title.png");
		JPanel westPanel = new ImagePanel(url);
		westPanel.setLayout(new BorderLayout());
		westPanel.setPreferredSize(new Dimension(70, 330));
		westPanel.setOpaque(false);
		this.add(westPanel, BorderLayout.WEST);

		// east panel
		url=this.getClass().getResource("/image/pc.png");
		JPanel eastPanel = new ImagePanel(url);
		eastPanel.setLayout(new BorderLayout());
		eastPanel.setPreferredSize(new Dimension(305, 330));
		this.add(eastPanel, BorderLayout.EAST);

		url=getClass().getResource("/image/question_trend.png");
		dispTrendB = new ImageButton(new ImageIcon(url));
		dispTrendB.addActionListener(this);
		eastPanel.add(dispTrendB, BorderLayout.WEST);

		url=this.getClass().getResource("/image/pc.png");
		JPanel east_eastPanel = new ImagePanel(url);
		east_eastPanel.setLayout(new BorderLayout(5,5));
		east_eastPanel.setPreferredSize(new Dimension(150, 330));
		eastPanel.add(east_eastPanel, BorderLayout.EAST);

		url=getClass().getResource("/image/disp_question.png");
		questionB = new ImageButton(new ImageIcon(url));
		questionB.addActionListener(this);
		east_eastPanel.add(questionB, BorderLayout.NORTH);

		url=getClass().getResource("/image/personal_performance.png");
		dispPerfB = new ImageButton(new ImageIcon(url));
		dispPerfB.addActionListener(this);
		east_eastPanel.add(dispPerfB, BorderLayout.SOUTH);

		// center panel
		url=this.getClass().getResource("/image/pc.png");
		JPanel centerPanel = new ImagePanel(url);
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setPreferredSize(new Dimension(305, 330));
		this.add(centerPanel, BorderLayout.CENTER);

		url=getClass().getResource("/image/check.png");
		checkB = new ImageButton(new ImageIcon(url));
		checkB.addActionListener(this);
		centerPanel.add(checkB, BorderLayout.NORTH);

		url=this.getClass().getResource("/image/pc.png");
		JPanel center_southPanel = new ImagePanel(url);
		center_southPanel.setLayout(new BorderLayout(5,5));
		center_southPanel.setPreferredSize(new Dimension(305, 150));
		centerPanel.add(center_southPanel, BorderLayout.SOUTH);

		url=getClass().getResource("/image/answer.png");
		answerB = new ImageButton(new ImageIcon(url));
		answerB.addActionListener(this);
		center_southPanel.add(answerB, BorderLayout.WEST);

		url=getClass().getResource("/image/setting.png");
		settingB = new ImageButton(new ImageIcon(url));
		settingB.addActionListener(this);
		center_southPanel.add(settingB, BorderLayout.EAST);
		settingB.setMargin(new Insets(0, 0, 0, 15));

		serverThread = new Thread(new ServerThread());
		serverThread.start();

	}

	private void init() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int h = (int) d.getHeight() / 3;
		int w = (int) d.getWidth() / 3;
		BufferedImage backgroundImage=null;
		url=this.getClass().getResource("/image/pc.png");
		try {
			backgroundImage = ImageIO.read(url);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.setContentPane(new ImageBackground(backgroundImage));
		this.setLocation(w, h);
		this.setTitle("Server");
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				/*
				 * int exit=JOptionPane.showConfirmDialog(MainUI.this, "确定退出吗？",
				 * "退出", JOptionPane.OK_CANCEL_OPTION);
				 * if(exit==JOptionPane.OK_OPTION){
				 * 
				 * }
				 */
				System.exit(0);
			}
		});
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
				}
			}
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

	private class ServerThread implements Runnable {
		public void run() {
			try {
				new Server();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new MainUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		tempPath=getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		try {
			tempPath=URLDecoder.decode(tempPath,"utf-8");
		} catch (UnsupportedEncodingException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		path=tempPath.substring(0,tempPath.lastIndexOf("/"))+ "/file/";
		// TODO Auto-generated method stub
		if (e.getSource() == checkB) {
			Thread drawPieChart=new Thread(new DrawPieChart());
			Thread getAbsentId=new Thread(new GetAbsentId());
			getAbsentId.start();
			drawPieChart.start();
			try {
				getAbsentId.join();
				drawPieChart.join();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			IdChart idChart=new IdChart("到课人数", defaultPieDataset,idList);
			idChart.pack();
			RefineryUtilities.centerFrameOnScreen(idChart);
			idChart.setVisible(true);

		} else if (e.getSource() == answerB) {
			int i = 1;
			HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
			String answer, line;
			DefaultPieDataset defaultPieDataset = new DefaultPieDataset();
			File file = null;

			initValues();
			if (this.flag) {
				while (true) {
					file = new File(path + "FileAnswer" + i + ".txt");
					if (!file.exists()) {
						break;
					}
					i++;
				}
				i--;
				file = new File(path + "FileAnswer" + i + ".txt");
			} else {
				fileChooser
						.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fileChooser.setDialogTitle("打开文件");
				int ret = fileChooser.showOpenDialog(null);
				if (ret == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
				} else {
					file = null;//没选文件时报错
				}
			}
			if (file.exists()) {
				try {
					BufferedReader bufferedReader = new BufferedReader(
							new FileReader(file));
					while ((line = bufferedReader.readLine()) != null) {
						int colonIndex = line.indexOf(':');
						answer = line.substring(colonIndex + 1);
						if (hashMap.containsKey(answer)) {
							int tmp = hashMap.get(answer);
							hashMap.put(answer, tmp + 1);
						} else {
							hashMap.put(answer, 1);
						}
					}
					bufferedReader.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Iterator iter = hashMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					String key = (String) entry.getKey();
					Integer num = (Integer) entry.getValue();
					defaultPieDataset.setValue(key, num);
				}

				AnswerChart answerChart = new AnswerChart("答案分布", defaultPieDataset);
				answerChart.pack();
				RefineryUtilities.centerFrameOnScreen(answerChart);
				answerChart.setVisible(true);
			}

		} else if (e.getSource() == settingB) {
			new SettingFrame();
		} else if(e.getSource()==questionB){
			new QuestionWin();
		} else if (e.getSource() == dispTrendB) {
			int i = 1;
			File file = null;
			String series1 = "正确", series2 = "错误";
			BufferedReader bufferedReader;
			ArrayList<String> answerList = new ArrayList<String>();
			ArrayList<Integer> correctList = new ArrayList<Integer>();
			ArrayList<Integer> wrongList = new ArrayList<Integer>();
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			answerList = readFromExcel(path + "answer_list.xls");
			while (true) {
				file = new File(path + "FileAnswer" + i + ".txt");
				if (!file.exists()) {
					break;
				}
				i++;
			}// i比实际文件数多1
			for (int j = 1; j < i; j++) {
				int countR = 0, countW = 0;
				file = new File(path + "FileAnswer" + j + ".txt");
				try {
					bufferedReader = new BufferedReader(new FileReader(file));
					String line, answer;
					while (true) {
						line = bufferedReader.readLine();
						if ("".equals(line)||line==null){//如果没有空指针检查，会报错
								break;
							}
						int index = line.indexOf(":");
						answer = line.substring(index + 1);
						if (answer.equals(answerList.get(j - 1))) {
							countR++;
						} else {
							countW++;
						}
					}
					correctList.add(countR);
					wrongList.add(countW);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			for (int j = 1; j < i; j++) {
				dataset.addValue(correctList.get(j - 1), series1, "问题" + j);
				dataset.addValue(wrongList.get(j - 1), series2, "问题" + j);
			}

			BarChart barChart = new BarChart("学生答题情况", dataset, 0);
			barChart.pack();
			RefineryUtilities.centerFrameOnScreen(barChart);
			barChart.setVisible(true);
		} else if (e.getSource() == dispPerfB) {
			int i = 1;
			File file;
			String /* id_input="10212075", */id, line, answer = null, seriesR = "正确", seriesW = "错误", seriesU = "未回答";
			BufferedReader bufferedReader;
			ArrayList<String> answerList = new ArrayList<String>();
			ArrayList<String> idList = new ArrayList<String>();
			HashMap<String, Integer> hashCorrect = new HashMap<String, Integer>();
			HashMap<String, Integer> hashWrong = new HashMap<String, Integer>();
			HashMap<String, Integer> hashUnanswer = new HashMap<String, Integer>();
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			answerList = readFromExcel(path + "answer_list.xls");

			while (true) {
				file = new File(path + "FileAnswer" + i + ".txt");
				if (!file.exists()) {
					break;
				}
				i++;
			}
			for (int j = 1; j < i; j++) {
				int temp;
				file = new File(path + "FileAnswer" + j + ".txt");
				idList = readFromExcel(path + "id_list.xls");
				try {
					bufferedReader = new BufferedReader(new FileReader(file));
					while (true) {
						line = bufferedReader.readLine();

						if ("".equals(line)||line==null) {//如果没有空指针检查，会报错
							break;
						}

						int index = line.indexOf(":");
						//System.out.println("" + index);
						id = line.substring(0, index);
						answer = line.substring(index + 1);

						if (answer.equals(answerList.get(j - 1))) {
							if (hashCorrect.containsKey(id)) {
								temp = hashCorrect.get(id);
								hashCorrect.put(id, temp + 1);
							} else {
								hashCorrect.put(id, 1);
							}
						} else {
							if (hashWrong.containsKey(id)) {
								temp = hashWrong.get(id);
								hashWrong.put(id, temp + 1);
							} else {
								hashWrong.put(id, 1);
							}
						}
						idList.remove(id);
					}
					Iterator it = idList.iterator();
					while (it.hasNext()) {
						String id_temp;
						id_temp = (String) it.next();
						if (hashUnanswer.containsKey(id_temp)) {
							temp = hashUnanswer.get(id_temp);
							hashUnanswer.put(id_temp, temp + 1);
						} else {
							hashUnanswer.put(id_temp, 1);
						}
					}

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			idList = readFromExcel(path + "id_list.xls");
			Iterator it = idList.iterator();
			while (it.hasNext()) {
				String id_temp = (String) it.next();
				dataset.addValue(hashCorrect.get(id_temp), seriesR, id_temp);
				dataset.addValue(hashWrong.get(id_temp), seriesW, id_temp);
				dataset.addValue(hashUnanswer.get(id_temp), seriesU, id_temp);
			}

			BarChart barChart = new BarChart("学生答题情况", dataset, 1);
			barChart.pack();
			RefineryUtilities.centerFrameOnScreen(barChart);
			barChart.setVisible(true);
		}
	}

	// 从excel中读取数据
	private ArrayList<String> readFromExcel(String path) {
		File file = new File(path);
		ArrayList<String> idList = new ArrayList<String>();
		try {
			Workbook book = Workbook.getWorkbook(file);
			Sheet sheet = book.getSheet(0);
			for (int i = 0; i < sheet.getRows(); i++) {
				Cell cell = sheet.getCell(0, i);
				idList.add(cell.getContents());
			}
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idList;
	}
	
	private class DrawPieChart implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			int i = 0;
			initValues();
			try {
				BufferedReader bufferedReader = new BufferedReader(
						new FileReader(path + "FileUser.txt"));
				// BufferedReader bufferedReader=new BufferedReader(new
				// InputStreamReader(new FileInputStream(file)));
				while (bufferedReader.readLine() != null) {
					i++;
				}
				bufferedReader.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			defaultPieDataset = new DefaultPieDataset();
			defaultPieDataset.setValue("currentNum", i);
			defaultPieDataset.setValue("absentNum", totalNum - i);
		}
		
	}
	
	private class GetAbsentId implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			idList = readFromExcel(path + "id_list.xls");
			totalNum=idList.size();
			setValues(flag,percent,totalNum);
			File file = new File(path + "FileUser.txt");
			if (file.exists()) {
				try {
					BufferedReader bufferedReader = new BufferedReader(
							new FileReader(file));
					while (true) {
						String line = bufferedReader.readLine();
						if (line == null)
							break;
						if (idList.contains(line)) {
							idList.remove(line);
						}
					}
					bufferedReader.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	}
}
