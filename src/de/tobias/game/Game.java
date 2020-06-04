package de.tobias.game;

import de.tobias.game.background.FightSequence;
import de.tobias.game.entities.Enemy;
import de.tobias.game.entities.Entity;
import de.tobias.game.entities.Hero;
import de.tobias.game.entities.Player;
import de.tobias.game.entities.Unit;
import de.tobias.game.fight.AvailableField;
import de.tobias.game.gfx.Screen;
import de.tobias.game.gfx.SpriteSheet;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import de.tobias.game.level.FightingScreen;
import de.tobias.game.level.Level;
import de.tobias.game.menus.Menu;
import de.tobias.game.menus.StartMenu;
import de.tobias.game.net.GameClient;
import de.tobias.game.net.GameServer;

/**
 * Die Hauptklasse des Spiels; Sie erbt von der Klasse Canvas, die Zeichenfläche
 * des Spiels und implementiert das Interface Runnable, welches für den Start
 * eines Threads zuständig ist. In der Klasse wird auch der Frame erzeugt und
 * alle Objekte, welche in dem Spiel vorhanden sind, werden hier eingefügt.
 * Zudem wird hier das BufferedImage, also das SpriteSheet erzeugt, auf dem alle
 * nötigen Grafiken vorhanden sind. In der Klasse befindet sich auch die
 * Mainmethode, d.h. hier wird auch das Spiel gestartet.
 * 
 * @author Tobias
 *
 */

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 240;
	public static final int HEIGHT = WIDTH / 4 * 3;
	public static final int SCALE = 5;
	public static final int TILE_LENGTH = 16;
	public static final String NAME = "Captain Pilz";
	public static boolean exit = false;

	private JFrame frame;
	private boolean running = false;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private int[] colours = new int[6 * 6 * 6];

	public Player player;

	private Screen screen;
	private InputHandler input;
	private Level[] levels;
	private Level level;
	private List<Entity> entities = new ArrayList<>();
	private List<Enemy> enemies = new ArrayList<>();
	private Menu menu;
	private FightSequence fs;

	private GameClient socketClient;
	@SuppressWarnings("unused")
	private GameServer socketServer;

	/**
	 * Erzeugt ein neues Objekt vom Typ Game; Hier wird der JFrame mit all
	 * seinen Bestandteilen erzeugt.
	 * 
	 */
	public Game() {
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		frame = new JFrame();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		frame.add(this, BorderLayout.CENTER);
		frame.pack();

		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * In dieser Methode wird dem Array colours an jeder Stelle der
	 * entsprechende Integer für die jeweilige Farbe zugewiesen, zudem werden
	 * alle Objekt des Spiels erzeugt.
	 */
	public void init() {
		int index = 0;
		for (int r = 0; r < 6; r++) {
			for (int g = 0; g < 6; g++) {
				for (int b = 0; b < 6; b++) {
					int rr = (r * 255 / 5);
					int gg = (g * 255 / 5);
					int bb = (b * 255 / 5);

					colours[index++] = rr << 16 | gg << 8 | bb;
				}
			}
		}

		levels = new Level[2];
		screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/sprite.png"));
		input = new InputHandler(this);
		levels[0] = new Level("/Level.png");
		levels[1] = new FightingScreen("/fightingLevel.png");
		level = levels[0];
		//player = new Player(this, level, JOptionPane.showInputDialog(this, "Benutzernamen eingeben!"), 5, 5, input, 10);
		player = new Player(this, level, "", 5, 5, input, 10);
		menu = new StartMenu(this, levels[0], input);
		for (int i = 0; i < 6; i++) {
			enemies.add(new Enemy(this, level, "", i * 6 + 2, ((i * 14) & 14) + 11, (int) (Math.random() * 10 + 1)));
		}

		fs = new FightSequence(this, player);

		entities.add(player);
		level.addEntity(player);
		
		for (Enemy e : enemies) {
			entities.add(e);
		}
		
		for (Enemy e : enemies) {
			level.addEntity(e);
		}

		socketClient.sendData("ping".getBytes());
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
		if (menu != null)
			menu.create(input);
	}

	public void setLevel(int index, Entity... entities) {
		level = levels[index];
		for (Entity e : entities){
			level.addEntity(e);
			e.setLevel(level);
			((Hero) e).getSelector().setLevel(level);
			for(Unit u: ((Hero) e).getTeam().getUnits()){
				u.setLevel(level);
				for(AvailableField af: u.getFields()){
					af.setLevel(level);
				}
			}
		}
	}

	public Level getLevel() {
		return level;
	}

	/**
	 * Hier wird der boolean running auf true gesetzt und ein neuer Thread wird
	 * erzeugt.
	 */
	public synchronized void start() {
		running = true;
		new Thread(this).start();

		/*if (JOptionPane.showConfirmDialog(this, "Willst du den Server aktivieren?") == 0) {
			socketServer = new GameServer(this);
			socketServer.start();
		}*/

		socketClient = new GameClient(this, "localhost");
		socketClient.start();

	}

	/**
	 * Hier wird der boolean running auf false gesetzt.
	 */
	public synchronized void stop() {
		running = false;
	}

	/**
	 * Überschriebene Methode, in der die Laufzeit des Spiels realisiert wird.
	 * So wird festgelegt wie oft die Methode tick() in einer bestimmten Zeit
	 * aufgerufen wird. Es wird zudem die Methode render() beliebig oft zu einer
	 * bestimmten Zeit aufgerufen.
	 */
	@Override
	public void run() {

		/*
		 * Es werden einige Variablen definiert; Die aktuelle Zeit(2 Variablen:
		 * In Nano- und Milliesekunden), die Zeit vom letzten Frame(2 Variablen:
		 * In Nano- und Milliesekunden), die Variable, welche indirekt angibt
		 * wie viele ticks zwischen den Frames ablaufen sollen und die Differenz
		 * zischen der aktuellen Zeit und der Zeit vom letzten Frame. Am Ende
		 * wird auch der boolean shouldRender zu bestimmten Zeiten auf true
		 * gesetzt und sonst auf false.
		 */
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;

		@SuppressWarnings("unused")
		int ticks = 0;
		@SuppressWarnings("unused")
		int frames = 0;

		long lastTimer = System.currentTimeMillis();
		double delta = 0;

		init();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = false;

			while (delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				frame.setTitle(NAME);
				frames = 0;
				ticks = 0;
			}
		}
	}

	/**
	 * Methode in der das Objekt level die Methode tick() aufruft. Zudem wird
	 * immer der tickCount bei Aufruf der Methode tick() vom Typ level um 1
	 * erhöht. Außerdem bricht das Programm ab wenn der boolean frameClosed den
	 * Wert true besitzt.
	 * 
	 * @see Level#tick()
	 */
	public void tick() {
		if (exit)
			System.exit(0);
		if (menu != null)
			menu.tick();
		else if (level != null)
			level.tick();
		if (player.hasCollidedEnemy())
			fs.tick();
	}

	/**
	 * Zeichnen des BufferedImage. Davor wird noch eine BufferStrategy erzeugt
	 * um einen Performanceschub zu geben. Zudem wird die Rendermethode aus der
	 * Klasse level aufgerufen und dem Array pixels wird an jeder Stelle die
	 * entsprechende Farbe zugewiesen, mit dem Array colours.
	 * 
	 * @see Level#render(Screen, int, int)
	 */
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		int xOffset = player.getX() - (screen.width / 2);
		int yOffset = player.getY() - (screen.height / 2);

		if (menu != null)
			menu.render(screen);
		else if (level != null)
			level.render(screen, xOffset, yOffset);

		if (player.hasCollidedEnemy())
			fs.render(screen);

		for (int y = 0; y < screen.height; y++) {
			for (int x = 0; x < screen.width; x++) {
				int colourCode = screen.pixels[x + y * screen.width];
				if (colourCode < 255)
					pixels[x + y * WIDTH] = colours[colourCode];
			}
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	/**
	 * Mainmethode in der ein neues Objekt vom Typ Game die Methode start
	 * aufruft.
	 * 
	 * @param args
	 *            Argumente haben in diesem Fall keinen Nutzen
	 */
	public static void main(String[] args) {
		new Game().start();
	}
}
