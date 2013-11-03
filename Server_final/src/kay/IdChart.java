package kay;

import java.awt.BorderLayout;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jfree.data.general.DefaultPieDataset;

public class IdChart extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jPanel;
	private JTextArea textArea;
	private JScrollPane scrollPane;

	
	public IdChart(String title,DefaultPieDataset defaultPieDataset,ArrayList<String> idList){
		super(title);
		this.setLayout(new BorderLayout());

		jPanel=new PieChart(title,defaultPieDataset).getPanel();
		
		textArea = new JTextArea("È±¿ÎÑ§ÉúÑ§ºÅ:\n");
		scrollPane = new JScrollPane(textArea);
		textArea.setEditable(false);
		
		Iterator it = idList.iterator();// how to display
		while (it.hasNext()) {
			textArea.append((String) it.next()+"\n");
		}
		this.add(scrollPane,BorderLayout.EAST);
		this.add(jPanel,BorderLayout.WEST);
		
	}

}
