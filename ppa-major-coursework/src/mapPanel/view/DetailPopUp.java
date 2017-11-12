package mapPanel.view;

import java.awt.BorderLayout;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import api.ripley.Ripley;

/**
 * 
 * @author jack
 *	Class to pop up when you click on an incident, shows incident details
 */
@SuppressWarnings("serial")
public class DetailPopUp extends JFrame {
	
	
	private String details, summary;
	private JTextArea textArea;
	private JTabbedPane tabs;
	private String id;
	private Ripley ripley;
	
	/**
	 * 
	 * @param summary - summary of incident, shown in first tab
	 * @param id - incident id of selected incident, used to get the details
	 */
	public DetailPopUp(String summary, String id) {
		this.summary = summary;
		this.id = id;
		this.ripley = new Ripley("10tLI3CXstqyVD6ql2OMtA==", "tBgm4pRp9grVqL46EnH7ew==");

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Details");
		this.setLayout(new BorderLayout());
		setMaximumSize(new Dimension(600, 400));

		JPanel summaryPanel = new JPanel();
		JPanel detailPanel = new JPanel();
		
		JTextArea detailsTextArea = new JTextArea(replaceBreaks());
		JTextArea summaryTextArea = new JTextArea(summary); 
		summaryTextArea.setMaximumSize(new Dimension(500, 300));
		detailsTextArea.setMaximumSize(new Dimension(500, 300));
		
		
		//make text areas uneditable, word wrapped and give them bounds
		detailsTextArea.setLineWrap(true);
		detailsTextArea.setWrapStyleWord(true);
		detailsTextArea.setBounds(0, 0, 500, 300);
		detailsTextArea.setEditable(false);

		summaryTextArea.setLineWrap(true);
		summaryTextArea.setWrapStyleWord(true);
		summaryTextArea.setBounds(0, 0, 500, 300);
		summaryTextArea.setEditable(false);
		
		
		
		summaryPanel.add(new JScrollPane(summaryTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		detailPanel.add(new JScrollPane(detailsTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		
		
		//add summary and details panels to tabs
		tabs = new JTabbedPane();
		tabs.addTab("Summary", null, summaryPanel, "Short summary of incident.");
		tabs.addTab("Details", null, detailPanel, "Long description");

		
		this.add(tabs, BorderLayout.CENTER);
		this.pack();
	}
	
	/**
	 * replace the <br>s in incident details with a new line
	 * @return reformatted string
	 */
	public String replaceBreaks() {
		getIncidentDetails(id);
		return details.replace("<br>", "\n");
	}
	

	/**
	 * 
	 * @param id incident id of the incident you want details for
	 */
	public void getIncidentDetails(String id) {
 
		if(details == null) {
			details = ripley.getIncidentDetails(id);
			
		}
	}

	
}
	