package de.tobias.game.fight;

import java.util.ArrayList;
import java.util.List;

import de.tobias.game.Game;
import de.tobias.game.entities.Entity;
import de.tobias.game.entities.Unit;
import de.tobias.game.gfx.Colours;
import de.tobias.game.gfx.Screen;
import de.tobias.game.level.Level;

/**
 * Klasse, welche die für die Einheiten verfügbaren Felder realisiert. In der
 * Kampfsequenz ist es für die Einheiten nur möglich auf bestimmte Felder zu
 * gehen; Mit dieser Klasse werden diese implementiert.
 * 
 * @author Tobias
 *
 */
public class AvailableField {

	private int x;
	private int y;

	private int xa;
	private int ya;

	private Level level = null;
	private List<Unit> units = new ArrayList<>();

	private boolean fightingField = false;
	private boolean available = true;

	public AvailableField(int x, int y) {
		this.x = x * Game.TILE_LENGTH;
		this.y = y * Game.TILE_LENGTH;
		xa = this.x;
		ya = this.y;
	}

	public void tick(List<Entity> entities) {
		for (Unit u : units) {
			if (x == u.getX() && y == u.getY()) {
				fightingField = true;
				return;
			}
		}
		fightingField = false;

		for (Entity e : entities) {
			if (y == e.getY() && x == e.getX()) {
				available = false;
				return;
			}
		}
		available = true;
	}

	/**
	 * Zeichnet ein AvailableField, falls der boolean visible auf true gesetzt
	 * ist und die Kampfsequenz stattfindet.
	 * 
	 * @param screen
	 *            Screen in dem das Feld gezeichnet wird
	 */
	public void render(Screen screen) {
		if (available) {
			if (y > 0 && y < (level.height - 1) << 4 && x > 0 && x < (level.width - 1) << 4) {
				if (fightingField)
					screen.render(x, y, 0, Colours.get(411, -1, -1, -1), 0, 4, 1);
				else
					screen.render(x, y, 0, Colours.get(141, -1, -1, -1), 0, 4, 1);
			}
		}
	}

	public boolean isAvailable() {
		return available;
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
	}

	public List<Unit> getUnits() {
		return units;
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

	public boolean isFightingField() {
		return fightingField;
	}

	public void setFightingField(boolean fightingField) {
		this.fightingField = fightingField;
	}

	public int getLocX() {
		return xa;
	}

	public void setLocX(int locX) {
		this.xa = locX;
	}

	public int getLocY() {
		return ya;
	}

	public void setLocY(int locY) {
		this.ya = locY;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}
}
