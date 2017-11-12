package secretPanel.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import secretPanel.controller.ShipController;
import secretPanel.model.Level;

/**
 * 	JFrame in which all the components get created. It contains the GamePanel and player stats.
 */
@SuppressWarnings("serial")
public class View extends JFrame implements Observer {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 620;
	private GamePanel gamePanel;
	private ShipController controller;
	private Level level;
	private JLabel data;
	private JLabel gameStatus;
	private JPanel bottomPanel;

	public View() {
		this.setSize(WIDTH, HEIGHT);
		this.setTitle("UFO Hunt");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		level = new Level(WIDTH, HEIGHT - 20, 1);
		level.addObserver(this);

		
		controller = new ShipController(level);
		gamePanel = new GamePanel(WIDTH, HEIGHT, controller);

		this.add(gamePanel, BorderLayout.CENTER);

		// used to set a fixed refresh rate for the repaint
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
		executor.scheduleAtFixedRate(new RepaintView(this), 0L, 20L, TimeUnit.MILLISECONDS);

		data = new JLabel();
		data.setText(String.valueOf("lives: " + level.getLives() + ", level: " + level.getLevel()));
		data.setForeground(Color.WHITE);
		
		gameStatus = new JLabel();
		gameStatus.setText("");
		gameStatus.setForeground(Color.WHITE);
		
		//bottom panel containing player data
		bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setBackground(Color.BLACK);
		bottomPanel.add(gameStatus, BorderLayout.CENTER);
		bottomPanel.add(data, BorderLayout.EAST);
		this.add(bottomPanel, BorderLayout.SOUTH);

		this.addKeyListener(controller);
		setFocusable(true);

	}

	// repaint class
	class RepaintView implements Runnable {
		View view;

		public RepaintView(View view) {
			this.view = view;

		}

		@Override
		public void run() {
			view.repaint();

		}

	}

	/**
	 * This method updates level dynamically based on the current game instance.
	 */
	public void updateLevel() {

		if (level.getNoOfShips() == 0 && !level.getLives().equals("0")) {
			switch (level.getLevel()) {
			case "2":
				changeLevel(new Level(WIDTH, HEIGHT - 20, 3));
				data.setText(String.valueOf("lives: " + level.getLives() + " | level: " + level.getLevel()));
				break;
			case "1":
				changeLevel(new Level(WIDTH, HEIGHT - 20, 2));
				data.setText(String.valueOf("lives: " + level.getLives() + " | level: " + level.getLevel()));
				break;

			}

		}
		if (level.getLives().equals("0")) {
			
			changeLevel(new Level(WIDTH, HEIGHT - 20, 1));
			data.setText(String.valueOf("lives: " + level.getLives() + ", level: " + level.getLevel()));
			
		}

	}

	
	/**
	 * Changes the level of the game by re-instantiating the model fields. 
	 * @param level
	 */
	public void changeLevel(Level level) {
		this.level = level;
		level.addObserver(this);
		controller = new ShipController(level);			
		gamePanel.stop();
		if(level.getLevel().equals("1")) {
			gameStatus.setText("Game over! Restarting..");
		} else {
			gameStatus.setText("");
		}

	}

	@Override
	public void update(Observable o, Object arg) {
		data.setText(String.valueOf("lives: " + level.getLives() + ", level: " + level.getLevel()));
		System.out.println(level.getLives());

		updateLevel();
		if(gamePanel.canRun()) gameStatus.setText("");

	}

	public static void main(String[] args) {
		new View().setVisible(true);
	}

}
