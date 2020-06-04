package de.tobias.game.entities;

import de.tobias.game.gfx.Screen;
import de.tobias.game.level.Level;

/**
 * Grundgerüst für die Lebewesen im Spiel. Die Klasse legt die groben
 * Eigenschaften jedes Wesens fest.
 * 
 * @see Mob
 * @author Tobias
 *
 */
public abstract class Entity {

	protected int x;
	protected int y;
	protected Level level;
	protected boolean remove = false;

	/**
	 * Fügt ein Objekt vom Typ Entity in ein Level ein
	 * 
	 * @param level
	 *            ebengenanntes Level
	 */
	public Entity(Level level) {
		this.level = level;
	}

	public void remove() {
		remove = true;
	}

	/**
	 * Getter für den int x
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter für den int y
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Setter für den int x
	 * 
	 * @param x
	 *            erwartet neuen Wert für x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Setter für den int y
	 * 
	 * @param y
	 *            erwartet neuen Wert für y
	 */
	public void setY(int y) {
		this.y = y;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	/**
	 * Die ständig aufgerufene Updatemethode. In den Unterklassen wird die
	 * Bewegung des jeweiligen Charakters realisiert.
	 * 
	 * @see Mob#tick()
	 */
	public abstract void tick();

	/**
	 * Die ständig aufgerufene Rendermethode. In den Unterklassen werden die
	 * jeweiligen Charaktere gezeichnet und möglicherweise auch weiter Objekte
	 * auf denen die Unterklassen referenzieren.
	 * 
	 * @see Mob#render(Screen, int)
	 * @param screen
	 *            Screen in dem der Mob gezeichnet wird
	 * @param spriteLine
	 *            y-Koordinate des Sprite
	 */
	public abstract void render(Screen screen, int spriteLine);
}
