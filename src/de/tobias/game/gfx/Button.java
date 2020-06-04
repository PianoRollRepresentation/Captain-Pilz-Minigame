package de.tobias.game.gfx;

import de.tobias.game.InputHandler;

/**
 * Klasse realisiert die Buttons im Spiel, welche Fonts sind, die, wenn sie
 * gedrückt werden, bestimmte Aktionen ausführen. Es ist auch möglich, dass
 * Buttons - wenn sie ausgewählt werden - blinken. Das heißt sie sind immer kurz
 * sichtbar, dann nicht sichtbar, dann sichtbar, usw. Die Klasse erbt von der
 * Klasse Font.
 * 
 * @author Tobias
 *
 */
public class Button extends Font {

	private InputHandler input;
	private final static float NEEDEDANITIME = 1f;
	private float aniTime = 0;
	private long lastFrame = System.currentTimeMillis();
	private boolean pressed = false;
	private boolean onCursor = false;

	/**
	 * Erzeugt einen neuen Button.
	 * 
	 * @param msg
	 *            Inhalt des Buttons
	 * @param input
	 *            Objekt, das für die Tastatureingabe zuständig ist
	 */
	public Button(String msg, InputHandler input) {
		super(msg);
		this.input = input;
	}

	/**
	 * Überschriebene Rendermethode; Zeichnet einen Button, welcher - wenn er
	 * ausgewählt wird - ständig blinkt. Dannach werden - wenn der Button
	 * ausgewählt wird - rechts und links neben dem Button zwei auf den Button
	 * zeigende Pfeile gezeichnet, damit deutlich angezeigt wird, dass dieser
	 * Button gerade ausgewählt wird.
	 * 
	 * @param msg
	 *            Inhalt des Buttons
	 * @param screen
	 *            Screen in dem der Button gezeichnet wird
	 * @param x
	 *            x-Koordinate des Buttons
	 * @param y
	 *            y-Koordinate des Buttons
	 * @param colour
	 *            Farbe des Buttons
	 * @param scale
	 *            Scalefaktor des Buttons
	 */
	public void renderBlink(Screen screen, int x, int y, int colour, int scale) {
		if (onCursor) {
			blink(screen, x, y, colour, scale);
			screen.renderBasic(x - 8, y, 32 * 16, colour, 0, 3, scale);
		} else {
			renderBasic(screen, x, y, colour, scale);
		}
	}

	/**
	 * Methode ist für das Blinken des Buttons zuständig. Die Variable visble
	 * wird immer in gleichen zeitlichen Abständen von true auf false und
	 * umgekehrt gesetzt.
	 */
	private void blink(Screen screen, int x, int y, int colour, int scale) {
		long thisFrame = System.currentTimeMillis();
		float timeSinceLastFrame = ((float) (thisFrame - lastFrame)) / 1000f;
		lastFrame = thisFrame;

		aniTime += timeSinceLastFrame;

		if (aniTime > NEEDEDANITIME)
			renderBasic(screen, x, y, colour, scale);
		if (aniTime > NEEDEDANITIME * 2)
			aniTime = 0;
	}

	/**
	 * Setter für den boolean onCursor
	 * 
	 * @param onCursor
	 *            erwartet neuen Wert für onCursor
	 */
	public void setOnCursor(boolean onCursor) {
		this.onCursor = onCursor;
	}

	/**
	 * Getter für den boolean onCursor
	 * 
	 * @return onCursor
	 */
	public boolean getOnCursor() {
		return onCursor;
	}

	/**
	 * Getter für die Referenz input
	 * 
	 * @return input
	 */
	public InputHandler getInput() {
		return input;
	}

	/**
	 * Setter für die Referenz input
	 * 
	 * @param input
	 *            erwartet neuen Wert für input
	 */
	public void setInput(InputHandler input) {
		this.input = input;
	}

	/**
	 * Getter für den boolean pressed
	 * 
	 * @return pressed
	 */
	public boolean isPressed() {
		return pressed;
	}

	/**
	 * Setter für den boolean pressed
	 * 
	 * @param pressed
	 *            erwartet neuen Wert für pressed
	 */
	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
}
