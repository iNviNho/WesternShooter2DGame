package game.game;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import game.client.Client;
import game.keyboard.Keyboard;
import game.map.Map;
import game.mouse.Mouse;
import game.player.Player;
import game.player.PlayerMP;
import game.screen.Screen;
import game.window.Window;
import javafx.embed.swing.JFXPanel;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	private Thread thread;
	private JFrame frame;
	private Screen screen;
	
	private Mouse mouse;
	private Keyboard keyboard;
	private Window window;
	
	public Map map;
	public Player player;
	
	private final int scale = 3;
	public int screenWidth;
	public int screenHeight;
	private int dimWidth;
	private int dimHeight;
	
	private String title = "Western shooter";
	private boolean running = false;
	
	public Client client;
	public List<PlayerMP> connectedPlayers = new ArrayList<PlayerMP>();
	
	private BufferedImage image;
	private int[] pixels;
	
	public static void main(String[] args) {
		
		Game game = new Game();
		game.client.start();
		game.client.login(game.player);
		game.start();
	}
	
	public Game() {	
		
		this.initializeJavaFx();
		this.setDimensionAndPixels();
		this.setResolution();
		
		this.screen = new Screen(this.dimWidth, this.dimHeight);
		this.map = new Map("Map1");
		this.keyboard = new Keyboard();
		this.addKeyListener(keyboard);
		
		this.mouse = new Mouse();
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		
		this.client = new Client(this, "localhost");
		
		String name = JOptionPane.showInputDialog(this, "Set your name", "Western shooter | Set your unique name", JOptionPane.INFORMATION_MESSAGE);
		this.player = new Player(name ,20 ,20 ,this.mouse , this.keyboard, this, this.map, this.client);
	
		this.window = new Window(this.client, this.player);
		this.frame.addWindowListener(window);
		
		
		this.disableCursor();
	}
	
	public void setDimensionAndPixels() {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		this.screenWidth = (int) (screenSize.getWidth() / 16) * 16;
		this.screenHeight = (int) (screenSize.getHeight() / 16) * 16;
		
		this.dimWidth = this.screenWidth / this.scale;
		this.dimHeight = this.dimWidth / 16 * 8;		
		
		this.image = new BufferedImage(this.dimWidth, this.dimHeight, BufferedImage.TYPE_INT_RGB);
		this.pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}
	
	public void setResolution() {
		
		JFrame frame = new JFrame();
		
		Dimension size = new Dimension(this.dimWidth * this.scale, this.dimHeight * this.scale);
		this.setPreferredSize(size);
		
		frame.setResizable(false);
		frame.add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		this.frame = frame;
	}

	
	
	private void update() {
		this.keyboard.update();
		this.player.update();
		
		for (PlayerMP pMP : this.connectedPlayers) {
			pMP.updateProjectiles();
		}
	}
	
	private void render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		this.screen.clear();
		int xScroll = player.x - screen.width / 2;
		int yScroll = player.y - screen.height / 2;
		this.map.render(xScroll, yScroll, this.screen);
		this.player.render(screen);
		for (PlayerMP pMP : this.connectedPlayers) {
			pMP.render(this.screen);
		}
		
		
		
		// we render cursor
		screen.renderCursor(this.mouse.getX(), this.mouse.getY());
		
		for (int i = 0; i < this.pixels.length; i++) {
			this.pixels[i] = this.screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(this.image, 0, 0, this.getWidth(), this.getHeight(), null);
		this.player.renderName(g, this);
		this.player.renderCurrentGunInfo(g, this);
		
		for (PlayerMP pMP : this.connectedPlayers) {
//			pMP.renderName(g, this);
		}
		
		g.dispose();
		bs.show();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		while (this.running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				this.update();
				updates++;
				delta--;
			}
			this.render();
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				this.frame.setTitle(this.title + " | " + updates + "ups, " + frames + " fps" + " Resolution: "
				+ this.dimWidth + "x" + this.dimHeight + " Position: " + this.player.x + "|" + this.player.y);
				updates = 0;
				frames = 0;
			}
		}
	}
	
	private void disableCursor() {
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");
		this.frame.getContentPane().setCursor(blankCursor);
	}
	
	public synchronized void start() {
		this.running = true;
		this.thread = new Thread(this, "Display");
		this.thread.start();
	}

	public synchronized void stop() {
		this.running = false;
		try {
			this.thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void initializeJavaFx() {
		final CountDownLatch latch = new CountDownLatch(1);
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		        new JFXPanel(); // initializes JavaFX environment
		        latch.countDown();
		    }
		});
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
