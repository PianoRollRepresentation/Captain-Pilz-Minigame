package de.tobias.game.menus;

import de.tobias.game.Game;
import de.tobias.game.InputHandler;
import de.tobias.game.gfx.Button;
import de.tobias.game.gfx.Colours;
import de.tobias.game.gfx.Font;
import de.tobias.game.gfx.Screen;

/**
 * Realisierung eines Menüs im Spiel. Ein Menü besteht aus einer Anzahl an Fonts
 * und Buttons. Diese Klasse implementiert die Beziehungen zwischen den Buttons
 * und Fonts.
 * 
 * @author Tobias
 *
 */
public abstract class Menu {

	protected Font[] fonts;
	protected Button[] buttons;
	protected InputHandler input;
	protected Game game;

	protected boolean[] buttonPressed;

	private final static float NEEDEDANITIME = 0.3f;
	private float aniTime = 0;
	private long lastFrame = System.currentTimeMillis();

	/**
	 * Erzeugt ein neues Menü.
	 * 
	 * @param input
	 *            Eingabe der Tastatur für die Buttons
	 */
	public Menu(Game game, InputHandler input) {
		this.game = game;
		this.input = input;
		create(input);
	}

	/**
	 * Erzeugt ein neues Font, welches an einer Stelle dem Array fonts
	 * zugewiesen wird. Diese Stelle wird als Argument mitgegeben.
	 * 
	 * @param msg
	 *            Inhalt des Fonts
	 * @param index
	 *            Stelle an der das neue Font eingfügt wird
	 */
	public void createFonts(String... msg) {
		for (int i = 0; i < msg.length; i++) {
			for (int k = 0; k < fonts.length; k++) {
				if (fonts[k] == null) {
					fonts[k] = new Font(msg[i]);
					break;
				}
			}
		}
	}

	/**
	 * Erzeugt einen neuen Button, welcher an einer Stelle dem Array buttons
	 * zugewiesen wird. Diese Stelle wird als Argument mitgegeben.
	 * 
	 * @param msg
	 *            Inhalt des Buttons
	 * @param index
	 *            Stelle an der der Button eingefügt wird
	 * @param input
	 *            InputHandler des Buttons
	 */
	public void createButtons(InputHandler input, String... msg) {
		for (int i = 0; i < msg.length; i++) {
			for (int k = 0; k < buttons.length; k++) {
				if (buttons[k] == null) {
					buttons[k] = new Button(msg[i], input);
					break;
				}
			}
		}
	}

	/**
	 * Zeichnet ein Font des Arrays fonts im Menü.
	 * 
	 * @param font
	 *            Fontobjekt welches gezeichnet wird
	 * @param screen
	 *            Screen in dem das Font gezeichnet wird
	 * @param x
	 *            x-Koordinate des Fonts
	 * @param y
	 *            y-Koordinate des Fonts
	 * @param colour
	 *            Farbe des Fonts
	 * @param index
	 *            Das Font an dieser Stelle wird gezeichnet
	 */
	public void renderFont(Screen screen, int x, int y, int colour, int index) {
		fonts[index].renderBasic(screen, x, y, colour, 1);
	}

	/**
	 * Zeichnet einen Button des Arrays buttons im Menü.
	 * 
	 * @param button
	 *            Buttonobjekt welches gezeichnet wird
	 * @param screen
	 *            Screen in dem der Button gezeichnet wird
	 * @param x
	 *            x-Koordinate des Buttons
	 * @param y
	 *            y-Koordinate des Buttons
	 * @param colour
	 *            Farbe des Buttons
	 * @param index
	 *            Der Button an dieser Stelle wird gezeichnet
	 */
	public void renderButton(Screen screen, int x, int y, int colour, int index) {
		buttons[index].renderBlink(screen, x, y, colour, 1);
	}

	protected void renderButtonsWithLayout(Screen screen, int xLayout, int yLayout, int widthLayout, int heightLayout,
			int indexFrom, int indexTo) {
		int buttonDistance = 14;
		renderLayout(screen, xLayout, yLayout, widthLayout, heightLayout);

		for (int i = indexFrom; i <= indexTo; i++) {
			renderButton(screen, (xLayout + (widthLayout << 4) / 2 - (buttons[i].getMsg().length() << 3) / 2),
					yLayout + (heightLayout << 4) / 2 - ((indexTo - indexFrom + 1) << 3) + buttonDistance / 2,
					Colours.get(-1, -1, 444, 000), i);
			yLayout += buttonDistance;
		}
	}

	protected void renderFontsWithLayout(Screen screen, int xLayout, int yLayout, int widthLayout, int heightLayout,
			int indexFrom, int indexTo) {
		int fontDistance = 14;

		renderLayout(screen, xLayout, yLayout, widthLayout, heightLayout);
		for (int i = indexFrom; i <= indexTo; i++) {
			renderFont(screen, (xLayout + (widthLayout << 4) / 2 - (fonts[i].getMsg().length() << 3) / 2),
					yLayout + (heightLayout << 4) / 2 - ((indexTo - indexFrom + 1) << 3) + fontDistance / 2,
					Colours.get(-1, -1, 444, 000), i);
			yLayout += fontDistance;
		}
	}

	protected void renderLayout(Screen screen, int x, int y, int tileWidth, int tileHeight) {
		for (int i = 0; i < tileHeight; i++) {
			if (i < 1) {

				screen.renderBasic(x, (i << 4) + y, 64, Colours.get(-1, 555, 000, -1), 0, 4, 1);
				for (int k = 1; k < tileWidth - 1; k++) {
					screen.renderBasic((k << 4) + x, (i << 4) + y, 65, Colours.get(-1, 555, 000, -1), 0, 4, 1);
				}
				screen.renderBasic(x + ((tileWidth - 1) << 4), (i << 4) + y, 64, Colours.get(-1, 555, 000, -1), 1, 4,
						1);
			} else if (i < tileHeight - 1) {

				screen.renderBasic(x, (i << 4) + y, 64 * 2, Colours.get(-1, 555, 000, -1), 0, 4, 1);
				for (int k = 1; k < tileWidth - 1; k++) {
					screen.renderBasic((k << 4) + x, (i << 4) + y, 0, Colours.get(555, -1, -1, -1), 0, 4, 1);
				}
				screen.renderBasic(x + ((tileWidth - 1) << 4), (i << 4) + y, 64 * 2, Colours.get(-1, 555, 000, -1), 1,
						4, 1);
			} else {
				screen.renderBasic(x, (i << 4) + y, 64, Colours.get(-1, 555, 000, -1), 2, 4, 1);
				for (int k = 1; k < tileWidth - 1; k++) {
					screen.renderBasic((k << 4) + x, (i << 4) + y, 65, Colours.get(-1, 555, 000, -1), 2, 4, 1);
				}
				screen.renderBasic(x + ((tileWidth - 1) << 4), (i << 4) + y, 64, Colours.get(-1, 555, 000, -1), 3, 4,
						1);
			}
		}
	}

	protected boolean buttonPressed(String msg) {
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i].getInput().enter.isPressed() && buttons[i].getMsg().equals(msg)
					&& buttons[i].getOnCursor()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Methode, welche bei Eingabe der Tastatur den Cursor auf den Button setzt,
	 * welcher gerade ausgewählt wird. Wird beispielsweise der oberste Button
	 * des Menüs ausgewählt und die Pfeiltaste nach unten wird gedrückt, so
	 * befindet sich der Cursor nicht mehr am obersten Button, sondern bewegt
	 * sich einen Button weiter runter.
	 * 
	 */
	public void switchCursor() {
		long thisFrame = System.currentTimeMillis();
		float timeSinceLastFrame = ((float) (thisFrame - lastFrame)) / 1000f;
		lastFrame = thisFrame;

		if (input.down.isPressed()) {
			for (int i = 0; i < buttons.length; i++) {
				aniTime += timeSinceLastFrame;
				if (buttons[i].getOnCursor() && i != buttons.length - 1 && aniTime > NEEDEDANITIME) {
					aniTime = 0;
					buttons[i].setOnCursor(false);
					buttons[i + 1].setOnCursor(true);
				}
			}
		}
		if (input.up.isPressed()) {
			for (int i = 0; i < buttons.length; i++) {
				aniTime += timeSinceLastFrame;
				if (buttons[i].getOnCursor() && i != 0 && aniTime > NEEDEDANITIME) {
					aniTime = 0;
					buttons[i].setOnCursor(false);
					buttons[i - 1].setOnCursor(true);
				}
			}
		}
	}

	/**
	 * Die Methode wird von den Unterklassen überschrieben. Hier werden dann
	 * alle Objekte des jeweiligen Menüs geupdatet.
	 */
	public void tick() {
		switchCursor();
		for (int i = 0; i < buttons.length; i++) {
			if (buttonPressed(buttons[i].getMsg()))
				buttonPressed[i] = true;
		}
	}

	/**
	 * Die abstrakte Methode wird von den Unterklassen überschrieben. Hier
	 * werden dann alle Objekte des jeweiligen Menüs gezeichnet.
	 * 
	 * @param screen
	 *            Screen in dem das Menü gezeichnet wird
	 */
	public abstract void render(Screen screen);

	/**
	 * Die abstrakte Methode wird von den Unterklassen überschrieben. Hier
	 * werden dann alle Objekte des jeweiligen Menüs eingefügt.
	 * 
	 * @param input
	 *            InputHandler für die Buttons
	 */
	public abstract void create(InputHandler input);
}
