package de.tobias.game.tiles;

/**
 * Realisierung für ein BasicSolidTile, eine Kachel auf der sich kein Entity
 * bewegen kann. Die Klasse ist identisch mit der Klasse BasicTile, der einzige
 * Unterschied besteht darin, dass der boolean solid auf true gesetzt wurde.
 * 
 * @author Tobias
 *
 */
public class BasicSolidTile extends BasicTile {

	/**
	 * Erzeugt ein neues BasicSolidTile.
	 * 
	 * @param id
	 *            Nummer der Kachel
	 * @param x
	 *            x-Koordinate der Kachel im SpriteSheet
	 * @param y
	 *            y-Koordinate der Kachel im SpriteSheet
	 * @param tileColour
	 *            Farbe der Kachel
	 * @param levelColour
	 *            Farbe der Kachel in der Level-Datei
	 */
	public BasicSolidTile(int id, int x, int y, int tileColour, int levelColour) {
		super(id, x, y, tileColour, levelColour);
		this.solid = true;
	}
}
