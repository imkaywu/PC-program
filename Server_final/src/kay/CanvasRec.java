package kay;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class CanvasRec extends Canvas {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int index, indexP, sizeL, x, y, temp, size;
	private int width, height;// the width and height of rectangle
	private ArrayList<Integer> count = new ArrayList<Integer>();
	private ArrayList<String> answerList = new ArrayList<String>();
	private Image image;

	public CanvasRec() {
		width = 50;
		height = 32;
	}

	public void paint(Graphics g) {
		int i = 0, j = 0, n = 0;
		g.setColor(Color.black);
		// System.out.println("size"+i);
		// System.out.println("count.get(i)"+count.get(i))
		while (true) {
			try {
				URL url=getClass().getResource("/image/cube"+j+".png");
				image = ImageIO.read(url);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (count.get(i) > 0) {
				x = j * (width + 10)+5;
				y = 300;
				System.out.println(answerList.get(i));
				System.out.println(answerList.size());
				g.drawString(answerList.get(i), x, y-40);
				n = count.get(i);
				while (n > 0) {
					g.drawImage(image, x, y-85, this);
					y = y - (2*height/3);
					g.drawString(""+count.get(i),x+20,y-65);
					n--;
				}
				j++;
			}
			i++;
			if (i == count.size()) {
				break;
			}
		}
		/*
		 * for(int i=0;i<count.size();i++){ x=i*(width+10); y=250;
		 * if(count.get(i)>0){ //int j=0;
		 * 
		 * 
		 * } /* for(int j=0;j<count.get(i);j++){ //g.drawRect(x, y, width,
		 * height); g.drawImage(image,x,y,this); g.drawString(aL.get(i),
		 * x+width/3, y-height/10); g.setColor(Color.green); y=y-height; }
		 */

	}

	public void update(Graphics g) {
		super.update(g);
	}

	public void computeValue() {
		size = count.size();// 答案的种数

		if (indexP == -1 && sizeL == size) {
			temp = count.get(index);
			count.set(index, temp + 1);
		} else if (indexP == -1 && sizeL != size) {
			count.add(index, 1);
		} else if (indexP != -1 && sizeL == size) {
			temp = count.get(index);
			count.set(index, temp + 1);
			temp = count.get(indexP);
			count.set(indexP, temp - 1);
		} else if (indexP != -1 && sizeL != size) {
			count.add(index, 1);
			temp = count.get(indexP);
			count.set(indexP, temp - 1);
		}
		/*
		for (int i = 0; i < count.size(); i++) {
			System.out.println("count(" + i + "):" + count.get(i));
		}*/
	}

	public void setValue(ArrayList<Integer> al, ArrayList<String> answerList) {
		index = al.get(0);
		indexP = al.get(1);
		this.answerList = answerList;
		sizeL = answerList.size();
	}

}
