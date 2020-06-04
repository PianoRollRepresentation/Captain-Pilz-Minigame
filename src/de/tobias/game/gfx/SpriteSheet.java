package de.tobias.game.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Klasse, die für die Realisierung des SpriteSheets zuständig ist. Aus diesem
 * werden alle Grafiken, alle einzelnen Kacheln entnommen und in das Spiel
 * eingefügt. Das SpriteSheet enthält ein Image und ein Array von Pixeln mit der
 * Größe 32 * 32 * 16 * 16. Es sind 32 * 32 Kacheln, wobei eine Kachel 16 * 16
 * Pixel groß ist.
 * 
 * @author Tobias
 *
 */
public class SpriteSheet {

	public String path;
	public int width;
	public int height;
	public int[] pixels;

	/**
	 * Erzeugt ein neues SpritSheet. Es wird ein neues Image eingelesen; Der
	 * Parameter gibt dessen Pfad an. Das Array pixel basiert auf dem
	 * RGB-Prinzip und deshalb wird jedem Element dieses Arrays der
	 * entsprechende Pixel des SpriteSheets zugewiesen.
	 * 
	 * @param path
	 *            Pfad des Images
	 */
	public SpriteSheet(String path) {
		BufferedImage image = null;

		try {
			image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (image == null) {
			return;
		}

		this.path = path;
		this.width = image.getWidth();
		this.height = image.getHeight();

		pixels = image.getRGB(0, 0, width, height, null, 0, width);
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = (pixels[i] & 0xff) / 64;
		}
	}
}
