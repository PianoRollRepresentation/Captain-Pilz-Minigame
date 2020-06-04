package de.tobias.game.gfx;

/**
 * Die abstrakte Klasse ist f�r die Farben von jedem Objekt im Spiel zust�ndig.
 * Sie besitzt nur 2 Methoden, welche beide eine Farbe als Integer-Wert
 * zur�ckgeben, beide erwarten jedoch unterschiedliche Parameter und haben eine
 * andere Funktion. Die Logik ist so aufgebaut, das jedes Objekt nur 4
 * unterschiedlich Farben besitzt, welche erst in Java festgelegt werden. Es
 * wird das RGB-Prinzip verwendet.
 * 
 * @author Tobias
 *
 */
public abstract class Colours {

	/**
	 * Die Methode erwartet f�r die Parameter die jeweils eine der 4 Farben des
	 * Objekts als einen Integer-Wert. Die Berechnung der einzelnen Farben wird
	 * in der Methode get(int) realisiert. Es wird in der Methode ein Wert
	 * zwischen 000 und 555 f�r die Farben erwartet. 000 ist die Farbe schwarz,
	 * 555 die Farbe wei�. Die erste Ziffer der dreistelligen Zahl gibt den
	 * Rotton an, die zweite den Gr�nton und die dritte den Blauton. Je h�her
	 * der Wert der jeweiligen Ziffer ist, desto st�rker ist der Farbton. 5 ist
	 * die h�chste Zahl jeder Ziffer; Es ist zwar m�glich f�r die 2. und 3.
	 * Ziffer von links einer h�here Zahl als die 5 einzugeben ist aber nicht
	 * sehr sinnvoll, da dann ein unvorhersebarer Farbton erscheint.
	 * 
	 * @see Colours#get(int)
	 * @param colour1
	 *            Wert der Farbe, welche im Sprite schwarz ist
	 * @param colour2
	 *            Wert der Farbe, welche im Sprite dunkelgrau ist
	 * @param colour3
	 *            Wert der Farbe, welche im Sprite hellgrau ist
	 * @param colour4
	 *            Wert der Farbe, welche im Sprite wei� ist
	 * @return Berechnet den Gesamtwert der 4 Farben
	 */
	public static int get(int colour1, int colour2, int colour3, int colour4) {
		return (get(colour4) << 24) + (get(colour3) << 16) + (get(colour2) << 8) + get(colour1);
	}

	private static int get(int colour) {

		/*
		 * Die Methode gibt den Wert der Farbe zur�ck, welcher in der Methode
		 * render(int, int, int, int, int, int) der Klase Screen ben�tigt wird.
		 * Es wird in der Methode ein Wert zwischen 000 und 555 erwartet. 000
		 * ist die Farbe schwarz, 555 die Farbe wei�. Die erste Ziffer der
		 * dreistelligen Zahl gibt den Rotton an, die zweite den Gr�nton und die
		 * dritte den Blauton. Je h�her der Wert der jeweiligen Ziffer ist,
		 * desto st�rker ist der Farbton. 5 ist die h�chste Zahl jeder Ziffer;
		 * Es ist zwar m�glich f�r die 2. und 3. Ziffer von links einer h�here
		 * Zahl als die 5 einzugeben ist aber nicht sehr sinnvoll, da dann ein
		 * unvorhersebarer Farbton erscheint.
		 */
		if (colour < 0)
			return 255;
		int r = colour / 100 % 10;
		int g = colour / 10 % 10;
		int b = colour % 10;
		return r * 36 + g * 6 + b;
	}
}
