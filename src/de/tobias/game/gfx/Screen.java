package de.tobias.game.gfx;

/**
 * Klasse für die Zeichenfläche im Spiel. Der Screen bewegt sich dynamisch mit
 * Hilfe der Variablen xOffset und yOffset. Außerdem kann der Screen mit Hilfe
 * von BIT_MIRROR_X und BIT_MIRROR_Y an beiden Achsen gespiegelt werden. In der
 * Klasse wird die Logik der Grundmethode render(int, int, int, int, int, int)
 * realisiert, also der Zugriff auf die einzelnen Kacheln des SpriteSheets.
 * 
 * @author Tobias
 *
 */
public class Screen {

	public static final byte BIT_MIRROR_X = 0x01;
	public static final byte BIT_MIRROR_Y = 0x02;
	public int[] pixels;
	public int xOffset = 0;
	public int yOffset = 0;
	public int width;
	public int height;
	public SpriteSheet sheet;

	/**
	 * Erzeugt einen neuen Screen, in dem die Kacheln des SpriteSheet gezeichnet
	 * werden. Die Größe des Arrays pixels wird hier auch bestimmt. Sie
	 * entspricht genau der Größe des Screens, also width * height.
	 * 
	 * @param width
	 *            Weite des Screens
	 * @param height
	 *            Höhe des Screens
	 * @param sheet
	 *            SpriteSheet dessen Kacheln im Screen gezeichnet werden
	 */
	public Screen(int width, int height, SpriteSheet sheet) {
		this.width = width;
		this.height = height;
		this.sheet = sheet;

		pixels = new int[width * height];
	}

	/**
	 * Setter für die ints xOffset und yOffset.
	 * 
	 * @param xOffset
	 *            erwartet neuen Wert für xOffset
	 * @param yOffset
	 *            erwartet neuen Wert für yOffset
	 */
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	/**
	 * Die Methode, welche die grundsätzliche Logik für das Zeichnen von
	 * Grafiken des SpriteSheet implementiert.
	 * 
	 * @param xPos
	 *            x-Koordinate des Screens
	 * @param yPos
	 *            y-Koordinate des Screens
	 * @param tile
	 *            tileIndex im SpriteSheet
	 * @param colour
	 *            Farbe des Screens
	 * @param mirrorDir
	 *            Spiegelung des Screens (Werte: 0x00, 0x01, 0x02 oder 0x03)
	 * @param scale
	 *            Scalefaktor des Screens
	 */
	public void renderBasic(int xPos, int yPos, int tile, int colour, int mirrorDir, int shift, int scale) {

		/*
		 * Zuerst wird das dynamische Verschieben der x- und y-Koordinate mit
		 * Hilfe der Variablen xOffset und yOffset realisiert. Dann werden zwei
		 * booleans für das eventuelle Spiegeln des SpriteSheets eingeführt(0x00
		 * und 0x01 für Spiegelung an der y-Achse; 0x002 und 0x03 für Spiegelung
		 * an der x-Achse). Als Nächstes wird eine Variable eingeführt, welche
		 * als Index im SpriteSheet dient. Wenn man beispielsweise die Kachel an
		 * der Stelle x = 7, y = 9 haben will, hat die Variable den Wert 7 + 9 *
		 * 64 = 583. Dann wird eine Variable eingeführt, welche die Farbe der
		 * Pixel an den entsprechenden Stellen wiederspiegelt und diese wird
		 * dann jedem Element aus dem Array pixels mit dem richtigen Wert
		 * zugewiesen.
		 */

		boolean mirrorX = (mirrorDir & BIT_MIRROR_X) > 0;
		boolean mirrorY = (mirrorDir & BIT_MIRROR_Y) > 0;

		int scaleMap = scale - 1;
		int xTile = tile % (4 << shift);
		int yTile = tile / (4 << shift);
		int tileOffset = (xTile << shift) + (yTile << shift) * sheet.width;
		for (int y = 0; y < (1 << shift); y++) {
			int ySheet = y;
			if (mirrorY)
				ySheet = (1 << shift) - 1 - y;

			int yPixel = y + yPos + (y * scaleMap) - ((scaleMap << shift) / 2);
			for (int x = 0; x < (1 << shift); x++) {
				int xSheet = x;
				if (mirrorX)
					xSheet = (1 << shift) - 1 - x;
				int xPixel = x + xPos + (x * scaleMap) - ((scaleMap << shift) / 2);
				int col = (colour >> (sheet.pixels[xSheet + ySheet * sheet.width + tileOffset] * 8)) & 255;
				if (col < 255) {
					for (int yScale = 0; yScale < scale; yScale++) {
						if (yPixel + yScale < 0 || yPixel + yScale >= height) {
							continue;
						}
						for (int xScale = 0; xScale < scale; xScale++) {
							if (xPixel + xScale < 0 || xPixel + xScale >= width) {
								continue;
							}
							pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col;
						}
					}
				}
			}
		}
	}

	public void render(int xPos, int yPos, int tile, int colour, int mirrorDir, int shift, int scale) {
		xPos -= xOffset;
		yPos -= yOffset;
		renderBasic(xPos, yPos, tile, colour, mirrorDir, shift, scale);
	}
}
