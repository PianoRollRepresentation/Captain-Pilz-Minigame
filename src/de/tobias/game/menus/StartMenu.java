package de.tobias.game.menus;

import de.tobias.game.Game;
import de.tobias.game.InputHandler;
import de.tobias.game.gfx.Button;
import de.tobias.game.gfx.Colours;
import de.tobias.game.gfx.Screen;
import de.tobias.game.level.Level;

/**
 * Die Klasse erstellt das Startmenü. Dieses besteht aus 3 Buttons, dem Start-,
 * Optionen- und Verlassen-Button. Dazu gehört noch die Überschrift.
 * 
 * @author Tobias
 *
 */
public class StartMenu extends Menu {

	private Level level;

	/**
	 * Erzeugt ein neues Startmenü.
	 * 
	 * @param entities
	 *            Liste an Entities, welche bei Klicken des Start-Buttons
	 *            sichtbar werden
	 * @param input
	 *            InputHandler für die Buttons
	 */
	public StartMenu(Game game, Level level, InputHandler input) {
		super(game, input);
		this.level = level;
	}

	/**
	 * Erstellt alle Objekte die Bestandteil des Startmenüs sind.
	 * 
	 * @param input
	 *            InputHandler für die Buttons
	 */
	@Override
	public void create(InputHandler input) {
		fonts = null;
		buttons = new Button[3];
		buttonPressed = new boolean[buttons.length];
		createButtons(input, "start", "optionen", "verlassen");
		buttons[0].setOnCursor(true);
	}

	/**
	 * Sorgt für die Aktionen, die bei Anklicken der Buttons geschehen sollen
	 * und den Wechsel des Cursors.
	 */
	@Override
	public void tick() {
		super.tick();

		if (buttonPressed[2])
			Game.exit = true;

		if (buttonPressed[0]) {
			level.tick();
			for (int i = 0; i < buttons.length; i++) {
				buttons[i].setPressed(true);
			}
		}
	}

	/**
	 * Zeichnet alle Buttons, die Überschrift und den Cursor, wenn der boolean
	 * started auf false gesetzt ist.
	 * 
	 * @param screen
	 *            Screen in dem die Objekte gezeichnet werden
	 */
	@Override
	public void render(Screen screen) {
		for (int i = 0; i < 6; i++) {
			screen.render((i << 5) + Game.WIDTH / 2 - (5 << 5) / 2, 15, 64 * 7 + i, Colours.get(-1, 010, 551, 131), 0,
					4, 2);
		}
		for (int i = 0; i < 3; i++) {
			screen.render((i << 5) + Game.WIDTH / 2 - (2 << 5) / 2, 20 + 32, 64 * 7 + 6 + i,
					Colours.get(-1, 010, 551, 131), 0, 4, 2);
		}
		renderButtonsWithLayout(screen, Game.WIDTH / 2 - (8 << 4) / 2, 85, 8, 5, 0, buttons.length - 1);

		if (buttonPressed[0]) {
			game.setMenu(null);
		}
	}
}
