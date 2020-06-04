package de.tobias.game.fight;

import de.tobias.game.level.Level;
import de.tobias.game.Game;
import de.tobias.game.InputHandler;
import de.tobias.game.gfx.Colours;
import de.tobias.game.gfx.Screen;

/**
 * Gerüst für den Einheitenselektor in der Kampfsequenz. Der Selektor kann nur
 * vom Spieler verwendet werden. Es werden mit diesem Einheiten in der
 * Kampfsequenz gesteuert.
 * 
 * @author Tobias
 *
 */
public class UnitSelector {

	public int colour = Colours.get(-1, 0, 555, -1);

	private int x;
	private int y;
	private int xToMove = 0;
	private int yToMove = 0;

	private Level level;
	private AvailableField field = null;
	private InputHandler input = null;

	private final static float NEEDEDANITIME = 0.1f;
	private float aniTime = 0;
	private long lastFrame = System.currentTimeMillis();

	private boolean player = false;
	private boolean noneChosen = true;
	private boolean active = false;

	/**
	 * Erzeugt einen neuen Einheitenselektor. Die x- und y-Werte sind in dem
	 * Fall nicht die Pixelnummern im Frame, sondern geben an auf welcher
	 * Kachelnummer der Selektor plaziert werden soll.
	 * 
	 * @param level
	 *            Level in das der Selektor eingefügt wird
	 * @param x
	 *            x-Koordinate bezüglich der Kacheln
	 * @param y
	 *            y-Koordinate bezüglich der Kacheln
	 * @param input
	 *            Objekt für die Benutzereingabe
	 * @param player
	 *            Spieler, auf den der Selektor angewendet wird
	 */
	public UnitSelector(Level level, int x, int y) {
		this.x = x * Game.TILE_LENGTH;
		this.y = y * Game.TILE_LENGTH;
		this.level = level;
	}

	public void setInput(InputHandler input) {
		this.input = input;
	}

	/**
	 * In Updatemethode wird die Bewegung des Selektors, und die der
	 * ausgewählten Einheit realisiert. Es wird immer kurz gewartet bis der
	 * Selektor sich um eine Kachel bewegen kann. Wenn eine Einheit mit ENTER
	 * ausgewählt wurde ändert sich die Farbe des Selektors. Drückt man wiederum
	 * ENTER, bewegt sich die ausgewählte Einheit an die Stelle auf der das
	 * zweite mal ENTER gedrückt wurde und die Farbe des Selektors wird wieder
	 * in den vorigen Zustand zurückgesetzt.
	 */
	public void tick() {
		long thisFrame = System.currentTimeMillis();
		float timeSinceLastFrame = ((float) (thisFrame - lastFrame)) / 1000f;
		lastFrame = thisFrame;
		aniTime += timeSinceLastFrame;
		if (aniTime > NEEDEDANITIME && input != null) {
			aniTime = 0;
			if (input.up.isPressed() && y > 16)
				y -= 16;
			if (input.down.isPressed() && y < (level.height - 2) << 4)
				y += 16;
			if (input.left.isPressed() && x > 16)
				x -= 16;
			if (input.right.isPressed() && x < (level.width - 2) << 4)
				x += 16;
		}
	}

	/**
	 * Zeichnen des UnitSelectors, wenn eine Kampfsequenz vorliegt.
	 * 
	 * @param screen
	 *            Screen in dem der Selektor gezeichnet wird
	 */
	public void render(Screen screen) {
		screen.render(x, y, 64 * 2 + 8, colour, 0, 4, 1);
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public boolean isPlayer() {
		return player;
	}

	public void setPlayer(boolean player) {
		this.player = player;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public boolean isNoneChosen() {
		return noneChosen;
	}

	public void setNoneChosen(boolean noneChosen) {
		this.noneChosen = noneChosen;
	}

	public int getxToMove() {
		return xToMove;
	}

	public void setxToMove(int xToMove) {
		this.xToMove = xToMove;
	}

	public int getyToMove() {
		return yToMove;
	}

	public void setyToMove(int yToMove) {
		this.yToMove = yToMove;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public AvailableField getField() {
		return field;
	}

	public void setField(AvailableField field) {
		this.field = field;
	}
}
