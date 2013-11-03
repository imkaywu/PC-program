package kay;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

public class PopCanvas extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 400, HEIGHT = 300;// 软件界面的长宽
	private HashMap<String, String> hashMap = new HashMap<String, String>();
	private ArrayList<String> answerList = new ArrayList<String>();
	private CanvasRec canvas;
	private WriteFile writeFile;

	public PopCanvas(final int num) {
		this.setLayout(new BorderLayout());

		canvas = new CanvasRec();
		canvas.setSize(WIDTH, HEIGHT);
		canvas.setBackground(Color.WHITE);

		this.add(canvas, BorderLayout.CENTER);

		init();

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ClientServerThread.setState();
				writeFile = new WriteFile(num, hashMap);
				Thread threadWrite = new Thread(writeFile);
				threadWrite.start();
				try {
					threadWrite.join();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				hashMap.clear();
				answerList.clear();
			}
		});
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	void init() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int h = (int) d.getHeight() - HEIGHT;
		int w = (int) d.getWidth() - WIDTH;// 在屏幕上定位
		this.setLocation(w, h);
		this.setTitle("draw rectangles");
		this.setSize(WIDTH, HEIGHT);
		this.setVisible(true);
	}

	public CanvasRec getCanvas() {
		return this.canvas;
	}

	public ArrayList<String> getList() {
		return this.answerList;
	}

	public ArrayList<Integer> dataHandler(String id, String answer) {
		int index, indexP;
		ArrayList<Integer> al = new ArrayList<Integer>();
		String value = "";
		//System.out.println("" + id + ":" + answer);
		if (hashMap.containsKey(id) && answerList.contains(answer)) {
			value = hashMap.get(id);
			indexP = answerList.indexOf(value);
			index = answerList.indexOf(answer);
		} else if (hashMap.containsKey(id) && !answerList.contains(answer)) {
			answerList.add(answer);
			value = hashMap.get(id);
			indexP = answerList.indexOf(value);
			index = answerList.indexOf(answer);
		} else if (!hashMap.containsKey(id) && answerList.contains(answer)) {
			indexP = -1;
			index = answerList.indexOf(answer);
		} else {
			answerList.add(answer);
			indexP = -1;
			index = answerList.indexOf(answer);
		}
		hashMap.put(id, answer);
		al.add(index);
		al.add(indexP);
		return al;
	}

}
