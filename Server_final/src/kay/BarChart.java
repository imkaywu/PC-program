package kay;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.RenderingHints;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;

public class BarChart extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CategoryDataset dataset;
	private String title;

	public BarChart(String title, CategoryDataset dataset, int i) {
		super(title);
		this.title = title;
		this.dataset = dataset;
		JFreeChart chart = createChart(dataset, i);
		ChartPanel chartPanel = new ChartPanel(chart);
		if (i == 0) {
			chartPanel.setPreferredSize(new Dimension(500, 270));
		} else {
			chartPanel.setPreferredSize(new Dimension(1000, 270));
		}
		setContentPane(chartPanel);
		
		configFont(chart);
	}
	
	private void configFont(JFreeChart chart){
		//chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);//对12到14号的宋体字最有效
        // 配置字体  
        Font xfont = new Font("宋体",Font.PLAIN,12) ;// X轴  
        Font yfont = new Font("宋体",Font.PLAIN,12) ;// Y轴  
        Font kfont = new Font("宋体",Font.PLAIN,12) ;// 底部  
        Font titleFont = new Font("隶书", Font.BOLD , 25) ; // 图片标题  
        CategoryPlot plot = chart.getCategoryPlot();// 图形的绘制结构对象  
          
        // 图片标题  
        chart.setTitle(new TextTitle(chart.getTitle().getText(),titleFont));  
          
        // 底部  
        chart.getLegend().setItemFont(kfont);  
          
        // X 轴  
        CategoryAxis domainAxis = plot.getDomainAxis();     
        domainAxis.setLabelFont(xfont);// 轴标题  
        domainAxis.setTickLabelFont(xfont);// 轴数值    
        domainAxis.setTickLabelPaint(Color.BLUE) ; // 字体颜色  
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // 横轴上的label斜显示   
          
        // Y 轴  
        ValueAxis rangeAxis = plot.getRangeAxis();     
        rangeAxis.setLabelFont(yfont);   
        rangeAxis.setLabelPaint(Color.BLUE) ; // 字体颜色  
        rangeAxis.setTickLabelFont(yfont);    
          
    }

	private JFreeChart createChart(CategoryDataset dataset, int i) {
		JFreeChart chart;
		if (i == 0) {
			chart = ChartFactory.createBarChart/*3D*/(title, "题号", "人数", dataset,
					PlotOrientation.VERTICAL, true, true, false);
		} else {
			chart = ChartFactory.createBarChart(title, "人数", "答题情况", dataset,
					PlotOrientation.VERTICAL, true, true, false);
		}

		// set the background color for the chart...
		chart.setBackgroundPaint(Color.white);

		// get a reference to the plot for further customisation...
		final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);

		// set the range axis to display integers only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// disable bar outlines...
		final BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);

		// set up gradient paints for series...
		final GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue,
				0.0f, 0.0f, Color.lightGray);
		final GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green,
				0.0f, 0.0f, Color.lightGray);
		final GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red,
				0.0f, 0.0f, Color.lightGray);
		renderer.setSeriesPaint(0, gp0);
		renderer.setSeriesPaint(1, gp1);
		renderer.setSeriesPaint(2, gp2);

		final CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(Math.PI / 6.0));
		// OPTIONAL CUSTOMISATION COMPLETED.

		return chart;
	}
}
