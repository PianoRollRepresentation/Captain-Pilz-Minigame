package de.tobias.game.background;

import de.tobias.game.Game;
import de.tobias.game.entities.Player;
import de.tobias.game.gfx.Colours;
import de.tobias.game.gfx.Screen;

/**
 * Realisierung der Zwischensequenz bei Kollidierung mit einem Gegner. In
 * "Sprite.png" sind 4 verschiedene Kacheln enthalten mit einer
 * unterschiedlichen Fülle an schwarzen Pixeln. Der Hintergrund im Spiel wird
 * dann mit jeweils einer dieser Kacheln komplett gefüllt. Die Kacheln werden
 * jeweils nacheinander sichtbar, zuerst Die Kachel mit der geringsten Fülle an
 * schwarzen Pixeln, und am Ende die mit der höchsten Fülle an schwarzen Pixeln
 * (also komplett schwarz).
 * 
 * @author Tobias
 *
 */
public class FightSequence {

	private Game game;
	private Player player;

	private final static float NEEDEDANITIME = 0.7f;
	private float aniTime = 0;
	private long lastFrame = System.currentTimeMillis();

	private int spriteNr = 5;

	public FightSequence(Game game, Player player) {
		this.game = game;
		this.player = player;
	}

	public void tick() {
		long thisFrame = System.currentTimeMillis();
		float timeSinceLastFrame = ((float) (thisFrame - lastFrame)) / 1000f;
		lastFrame = thisFrame;
		aniTime += timeSinceLastFrame;

		game.getLevel().stop();
		if (aniTime > NEEDEDANITIME) {
			aniTime = 0;
			spriteNr += 1;
		}

		if (spriteNr > 7) {
			game.setLevel(1, player, player.getEnemyInt());
			game.getLevel().resume();
			game.getLevel().setDefault(1, 6, 13, 6);
		}
	}

	/**
	 * Zeichnen der Zwischensequenzanimation.
	 * 
	 * @param screen
	 *            Screen in welchem die Sequenz gezeichnet wird
	 */
	public void render(Screen screen) {
		for (int y = 0; y < 64; y++) {
			for (int x = 0; x < 64; x++) {
				if (spriteNr < 9)
					screen.render(x << 4, y << 4, 64 + spriteNr, Colours.get(-1, 0, -1, -1), 0x00, 4, 1);
			}
		}
	}

	/**
	 * Getter für den int SpriteNr, also die x-Koordinate im SpriteSheet
	 * 
	 * @return spriteNr
	 */
	public int getSpriteNr() {
		return spriteNr;
	}

	public void setSpriteNr(int spriteNr) {
		this.spriteNr = spriteNr;
	}
}
