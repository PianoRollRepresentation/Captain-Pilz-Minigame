package de.tobias.game.tiles;

import de.tobias.game.gfx.Screen;
import de.tobias.game.level.Level;

/**
 * BasicTiles sind Kacheln, auf denen sich ein Entity bewegen kann. Ein Beispiel
 * dafür ist eine Graskachel.
 * 
 * @author Tobias
 *
 */
public class BasicTile extends Tile {

	protected int tileId;
	protected int tileColour;

	/**
	 * Erzeugt ein neues BasicTile.
	 * 
	 * @param id
	 *            Nummer der Kachel
	 * @param x
	 *            x-Koordinate im SpriteSheet
	 * @param y
	 *            y-Koordinate im SpriteSheet
	 * @param tileColour
	 *            Farbe der Kachel
	 * @param levelColour
	 *            Farbe der Kachel in der Level-Datei
	 */
	public BasicTile(int id, int x, int y, int tileColour, int levelColour) {
		super(id, false, levelColour);
		this.tileId = x + y * 64;
		this.tileColour = tileColour;
	}

	/**
	 * Überschriebene Rendermethode zeichnet ein BasicTile.
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
	@Override
	public void render(Screen screen, Level level, int x, int y) {
		screen.render(x, y, tileId, tileColour, 0x00, 4, 1);
	}

	/**
	 * Getter für den int tileColour
	 * 
	 * @return tileColour
	 */
	public int getTileColour() {
		return tileColour;
	}
}
