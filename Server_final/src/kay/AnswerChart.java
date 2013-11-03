package kay;

import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;


public class AnswerChart extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// ¹¹Ôìº¯Êý
	public AnswerChart(String s, DefaultPieDataset defaultpiedataset) {
		super(s);
		setContentPane(new PieChart(s,defaultpiedataset).getPanel());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

}
