package de.tobias.game.tiles;

import de.tobias.game.level.Level;
import de.tobias.game.gfx.Colours;
import de.tobias.game.gfx.Screen;

/**
 * Klasse, welche für die Verwaltung der verschiedenen Kachelarten zuständig
 * ist. Sie enthält eine Selbstreferenz in Form eines Arrays der Größe 256. Dort
 * werden alle Kachelarten abgespeichert. Jedes Tile hat zudem eine ID, welche
 * die Nummer der Kachel beschreibt, die man selber festlegen kann.
 * 
 * @author Tobias
 *
 */
public abstract class Tile {

	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new BasicSolidTile(0, 0, 0, Colours.get(000, -1, -1, -1), 0xFF000000);
	public static final Tile STONE = new BasicSolidTile(1, 1, 0, Colours.get(131, 111, 222, 333), 0xFF555555);
	public static final Tile GRASS_1 = new BasicTile(2, 2, 0, Colours.get(-1, 131, 141, -1), 0xFF00FF00);
	public static final Tile WATER = new AnimatedTile(3, new int[][] { { 0, 5 }, { 1, 5 }, { 2, 5 }, { 1, 5 } },
			Colours.get(-1, 004, 115, -1), 0xFF0000FF, 500);
	public static final Tile GRASS_2 = new BasicTile(4, 3, 0, Colours.get(-1, 131, 141, -1), 0xFF02bf02);
	public static final Tile WAY = new BasicTile(5, 4, 0, Colours.get(-1, 131, 141, -1), 0xFF50321b);
	public static final Tile TREE_PART_1 = new BasicSolidTile(6, 5, 0, Colours.get(131, 111, 141, 121), 0xFFa46a96);
	public static final Tile TREE_PART_2 = new BasicSolidTile(7, 6, 0, Colours.get(131, 111, 141, 121), 0xFF7f1c7e);
	public static final Tile TREE_PART_3 = new BasicSolidTile(8, 7, 0, Colours.get(131, 111, 211, 121), 0xFF7d307c);
	public static final Tile TREE_PART_4 = new BasicSolidTile(9, 8, 0, Colours.get(131, 111, 211, 121), 0xFF7d477c);
	public static final Tile FENCE = new BasicSolidTile(10, 9, 0, Colours.get(131, 111, 334, 555), 0xFFff0000);
	public static final Tile BUSH = new BasicSolidTile(11, 10, 0, Colours.get(131, 111, 121, 141), 0xFF011e00);

	protected byte id;
	protected boolean solid;
	private int levelColour;

	/**
	 * Erzeugt eine neue Kachel.
	 * 
	 * @param id
	 *            Nummer der Kachel
	 * @param isSolid
	 *            wenn dieser boolean true, handelt es sich um ein SolidTile
	 * @param levelColour
	 *            Farbe der Kachel in der Level-Datei
	 */
	public Tile(int id, boolean isSolid, int levelColour) {
		this.id = (byte) id;
		if (tiles[id] != null)
			throw new RuntimeException("Duplicate tile id on" + id);
		this.solid = isSolid;
		this.levelColour = levelColour;
		tiles[id] = this;
	}

	/**
	 * Getter für den byte id
	 * 
	 * @return id
	 */
	public byte getId() {
		return id;
	}

	/**
	 * Getter für den boolean solid
	 * 
	 * @return solid
	 */
	public boolean isSolid() {
		return solid;
	}

	/**
	 * Getter für den int levelColour. Es handelt sich dabei um die Farbe des
	 * Pixels(welcher eine Kachel repräsentiert) in der Level-Datei.
	 * 
	 * @return levelColour
	 */
	public int getLevelColour() {
		return levelColour;
	}

	/**
	 * Abstrakte Rendermethode. Sie wird von den spezifizierten Klassen, welche
	 * von dieser Klasse erben, überschrieben.
	 * 
	 * @param screen
	 *            Screen indem die Kachel gezeichnet wird
	 * @param level
	 *            Level in dem sich die Kachel befindet
	 * @param x
	 *            x-Koordinate
	 * @param y
	 *            y-Koordinate
	 */
	public abstract void render(Screen screen, Level level, int x, int y);
}
