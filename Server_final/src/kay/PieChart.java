package kay;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.StandardChartTheme;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;

import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;

import javax.swing.*;

public class PieChart extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jPanel;
	private DefaultPieDataset defaultpiedataset = new DefaultPieDataset();
	private String str;

	// ���캯��
	public PieChart(String s, DefaultPieDataset defaultpiedataset) {
		this.defaultpiedataset = defaultpiedataset;
		str = s;
		createPanel();
	}

	// ����ͼ��������JFreeChart
	private JFreeChart createChart(PieDataset piedataset) {
		// ����ͼ�����
		JFreeChart jfreechart = ChartFactory.createPieChart(str, piedataset,
				true, true, false);
		configFont(jfreechart);

		return jfreechart;
	}

	// ������ʾͼ������
	private JPanel createPanel() {
		JFreeChart jfreechart = createChart(defaultpiedataset);
		jPanel=new ChartPanel(jfreechart);
		return jPanel;
	}
	
	public JPanel getPanel(){
		return jPanel;
	}
	
	private void configFont(JFreeChart jfreechart){
		//jfreechart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		//���ñ�������ʹ�С
		TextTitle textTitle = jfreechart.getTitle();  
        textTitle.setFont(new Font("����", Font.BOLD, 20));
        //����ͼ������ʹ�С
        LegendTitle legend = jfreechart.getLegend();  
        if (legend != null) {  
            legend.setItemFont(new Font("����", Font.PLAIN, 15));  
        }
        //������������ʹ�С
        PiePlot pie = (PiePlot) jfreechart.getPlot();  
        pie.setLabelFont(new Font("����", Font.PLAIN, 15));  
        pie.setNoDataMessage("No data available");  
        pie.setCircular(true);  
        pie.setLabelGap(0.01D);// ���  
    }
}
