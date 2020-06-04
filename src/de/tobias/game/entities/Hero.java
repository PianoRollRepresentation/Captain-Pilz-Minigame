package de.tobias.game.entities;

import de.tobias.game.Game;
import de.tobias.game.fight.UnitSelector;
import de.tobias.game.gfx.Colours;
import de.tobias.game.gfx.Font;
import de.tobias.game.gfx.Screen;
import de.tobias.game.level.Level;
import de.tobias.game.team.Team;

/**
 * Grundgerüst für die Realiserung vom Spieler(Player) und CPU(Enemy). Ein Held
 * ist ein Mob, welcher zusätlich eine Liste von Einheiten(Unit) besitzt. Diese
 * werden in der Kampfsequenz eingesetzt.
 * 
 * @author Tobias
 *
 */
public abstract class Hero extends Mob {

	protected Game game;
	protected Team team = new Team();
	protected UnitSelector selector = new UnitSelector(level, 2, 3);
	protected int savedX;
	protected int savedY;

	/**
	 * Erzeugt einen Helden in einem Level.
	 * 
	 * @param level
	 *            Level in das der Held eingefügt wird
	 * @param name
	 *            Name des Helden
	 * @param x
	 *            x-Koordinate des Helden im Frame
	 * @param y
	 *            y-Koordinate des Helden im Frame
	 * @param speed
	 *            Geschwindigkeit des Helden
	 * @param colour
	 *            Farbe des Helden
	 */
	public Hero(Game game, Level level, String name, int x, int y, int speed, int colour, int units) {
		super(level, name, x, y, speed, Colours.get(-1, 000, colour, 555));

		this.game = game;
		for (int i = 0; i < units; i++) {
			team.getUnits().add(new Unit(level, colour));
		}
	}

	/**
	 * Überschriebene Rendermethode der Klasse Mob. Zusätzlich wird hier noch
	 * die Rendermethode der Klasse Mob aufgerufen.
	 * 
	 * @see Mob#render(Screen, int)
	 * @see Unit#render(Screen, int)
	 * @param screen
	 *            Screen in dem der Held und die Einheiten gezeichnet werden
	 * @param spriteLine
	 *            y-Koordinate im SpriteSheet
	 */
	@Override
	public void render(Screen screen, int spriteLine) {
		super.render(screen, spriteLine);

		if (name != null)
			new Font(name).render(screen, x - (name.length() << 4) / 4, y - 22, Colours.get(-1, -1, -1, 000), 1);
	}

	public abstract void tickSelector();

	public UnitSelector getSelector() {
		return selector;
	}

	public Team getTeam() {
		return team;
	}

	public int getSavedX() {
		return savedX;
	}

	public void setSavedX(int savedX) {
		this.savedX = savedX;
	}

	public int getSavedY() {
		return savedY;
	}

	public void setSavedY(int savedY) {
		this.savedY = savedY;
	}
}
