package statsPanel.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StatsBox extends JPanel {

	private static final long serialVersionUID = -4794799520762005179L;
	private JPanel display, leftPanel, rightPanel;
	private JButton left, right;
	private JLabel title, info;
	private int id;
	/**
	 * The box used to display each statistic
	 */
	public StatsBox()
	{
		super(new BorderLayout(10, 15));
		init();
		setBorder(BorderFactory.createRaisedSoftBevelBorder());
	}
	/**
	 * The box used to display each statistic
	 * @param id ID used to discern which statistic box this is
	 */
	public StatsBox(int id)
	{
		this();
		setID(id);
	}
	
	
	/** 
	 * Initialization function
	 */
	public void init(){
		left = new JButton("<"); right = new JButton(">");
		left.setFont(new Font("Calibri", Font.BOLD, 30));
		right.setFont(new Font("Calibri", Font.BOLD, 30));
		display = new JPanel(new BorderLayout());
		
		leftPanel = new JPanel(new BorderLayout()); rightPanel = new JPanel(new BorderLayout());
		
		title = new JLabel("Title", JLabel.CENTER);
		
		info = new JLabel("Info");
		title.setFont(new Font("Calibri", Font.BOLD, 20));
		info.setFont(new Font("Calibri", Font.PLAIN, 25));
		
		setSize(this, new Dimension(600, 450));
		setSize(left, new Dimension(110, 300));
		setSize(right, new Dimension(110, 300));
		setSize(display, new Dimension(350, 350));
		
		rightPanel.add(right, BorderLayout.CENTER); leftPanel.add(left, BorderLayout.CENTER);
		rightPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 15));
		leftPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 10));
		//Empty borders for the buttons so they don't look cramped into the box
		
		display.add(info, BorderLayout.CENTER);
		display.add(title, BorderLayout.NORTH);
		display.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
		//Empty border for the display for the same reason
		
		add(leftPanel, BorderLayout.WEST); add(rightPanel, BorderLayout.EAST); add(display, BorderLayout.CENTER);
	}
	
	public void setName(String s)
	{
		super.setName(s);
		left.setName(s);
		right.setName(s + "Next");
	}
	public int getID()
	{
		return id;
	}
	
	public void setID(int id)
	{
		this.id = id;
	}
	
	/**
	 * Adds listeners to the left and right buttons of the box
	 * @param l
	 */
	public void addListeners(ActionListener l)
	{
		left.addActionListener(l);
		right.addActionListener(l);
	}
	/**
	 * Utility function used to size components
	 * @param comp Component to be sized
	 * @param d Desired dimension of the component
	 */
	private void setSize(Component comp, Dimension d)
	{
		comp.setSize(d);
		comp.setPreferredSize(d);
		comp.setMinimumSize(d);
		comp.setMaximumSize(d);
	}
	/**
	 * Sets the title and the statistic of the box. Also changes the size of the components accordingly to display the text.
	 * @param t
	 * @param d
	 */
	public void setDisplay(String t, String d)
	{
		title.setText(t); info.setText(d);
		title.validate(); info.validate();
	}
	
	public static void main(String[] args)
	{
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.add(new StatsBox());
		f.setVisible(true);
		f.pack();
	}


}
