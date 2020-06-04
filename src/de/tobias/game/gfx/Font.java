package de.tobias.game.gfx;

/**
 * Klasse, welche Strings grafisch realisiert. Es sind dabei nur Gro�buchstaben
 * Zahlen und die Zeichen .,:;�\"!?$%()-=+/ zul�ssig.
 * 
 * @author Tobias
 *
 */
public class Font {

	private String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ      " + "0123456789.,:;�\"!?$%()-=+/      ";
	private String msg;

	/**
	 * Erzeugt einen neuen Font mit dem Wert des Parameters als Inhalt.
	 * 
	 * @param msg
	 *            Inhalt des Fonts
	 */
	public Font(String msg) {
		this.msg = msg;
	}

	
	/**
	 * Methode f�r das Zeichnen des Fonts. Zun�chst werden alle Buchstaben des
	 * Strings, welcher mitgegeben wird, auf Gro�buchstaben gesetzt. Dannach
	 * wird der String, welcher eingegeben wird, gezeichnet.
	 * 
	 * @param msg
	 *            Inhalt des Fonts
	 * @param screen
	 *            Screen in dem der Font einge�gt wird
	 * @param x
	 *            x-Koordinate des Fonts
	 * @param y-Koordinate
	 *            des Fonts
	 * @param colour
	 *            Farbe des Fonts
	 * @param scale
	 *            Scalefaktor des Fonts
	 */
	public void render(Screen screen, int x, int y, int colour, int scale) {
		msg = msg.toUpperCase();

		for (int i = 0; i < msg.length(); i++) {
			int charIndex = chars.indexOf(msg.charAt(i));
			if (charIndex >= 0)
				screen.render(x + (i << 3) + 8, y + 12, charIndex + 32 * 30, colour, 0x00, 3, scale);
		}
	}
	
	/**
	 * Methode f�r das Zeichnen des Fonts. Zun�chst werden alle Buchstaben des
	 * Strings, welcher mitgegeben wird, auf Gro�buchstaben gesetzt. Dannach
	 * wird der String, welcher eingegeben wird, gezeichnet.
	 * 
	 * @param msg
	 *            Inhalt des Fonts
	 * @param screen
	 *            Screen in dem der Font einge�gt wird
	 * @param x
	 *            x-Koordinate des Fonts
	 * @param y-Koordinate
	 *            des Fonts
	 * @param colour
	 *            Farbe des Fonts
	 * @param scale
	 *            Scalefaktor des Fonts
	 */
	public void renderBasic(Screen screen, int x, int y, int colour, int scale) {
		msg = msg.toUpperCase();

		for (int i = 0; i < msg.length(); i++) {
			int charIndex = chars.indexOf(msg.charAt(i));
			if (charIndex >= 0)
				screen.renderBasic(x + (i << 3), y, charIndex + 32 * 30, colour, 0x00, 3, scale);
		}
	}

	/**
	 * Getter f�r den String msg.
	 * 
	 * @return msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * Setter f�r den String msg.
	 * 
	 * @param msg
	 *            erwartet neuen Wert f�r msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
