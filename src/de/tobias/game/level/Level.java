package de.tobias.game.level;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import de.tobias.game.tiles.AnimatedTile;
import de.tobias.game.tiles.BasicSolidTile;
import de.tobias.game.tiles.Tile;
import de.tobias.game.entities.Enemy;
import de.tobias.game.entities.Entity;
import de.tobias.game.entities.Player;
import de.tobias.game.gfx.Colours;
import de.tobias.game.gfx.Screen;

/**
 * Klasse für die Realisierung eines Levels. Ein Level besteht aus einer Anzahl
 * von Kacheln, welche in einer png-Datei abgelegt werden. In dieser Datei
 * entspricht ein Pixel einer Kachel. Die Klasse Level verwaltet alle Objekt des
 * Levels. Dazu gehören der Spieler die Kacheln und Ähnliches. In dieser Klasse
 * werden die Beziehungen zwischen diesen Objekten implementiert.
 * 
 * @author Tobias
 *
 */
public class Level {

	public int width;
	public int height;
	
	public boolean stop = false;
	
	public String imagePath;
	
	public List<Entity> entities = new ArrayList<Entity>();
	
	protected BufferedImage image;
	
	protected byte[] tiles;

	/**
	 * Erzeugt ein neues Level aus einer png-Datei. Für die Datei ist der
	 * Parameter imagePath verantwortlich. Ist der angegebene Pfad nicht
	 * vorhanden, oder es wird null eingegeben, wird ein Level mit der Größe 64
	 * * 64 Kacheln erzeugt. Die Kacheln werden dann nach der Methode
	 * generateLevel() implementiert.
	 * 
	 * @see Level#generateLevel()
	 * @param imagePath
	 *            Pfad der png-Datei
	 */
	public Level(String imagePath) {
		if (imagePath != null) {
			this.imagePath = imagePath;
			loadLevelFromFile();
		} else {
			width = 64;
			height = 64;
			tiles = new byte[width * height];
			generateLevel();
		}
	}

	/**
	 * Erzeugt die png-Datei, weist den Variablen width und height die Weite und
	 * Höhe der Datei zu, erzeugt das Array tiles mit der Größe width * height
	 * und lädt die Kacheln mit loadTiles()
	 * 
	 * @see Level#loadTiles()
	 */
	public void loadLevelFromFile() {
		try {
			this.image = ImageIO.read(Level.class.getResourceAsStream(this.imagePath));
			this.width = image.getWidth();
			this.height = image.getHeight();
			tiles = new byte[width * height];
			this.loadTiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Weist dem Array tiles an dem Punkt (x|y) das Tile newTile zu und weist
	 * dem Attribut image an dem Punkt (x|y) die Farbe von newTile zu.
	 * 
	 * @param x
	 *            x-Koordinate des Levels
	 * @param y
	 *            y-Koordinate des Levels
	 * @param newTile
	 *            Tile, das in das Level eingefügt wird
	 */
	public void alterTile(int x, int y, Tile newTile) {
		this.tiles[x + y * width] = newTile.getId();
		image.setRGB(x, y, newTile.getLevelColour());
	}

	/**
	 * Die Methode weist dem Array tiles Tiles, bestehend aus GRASS_1 und STONE,
	 * nach einem unregelmäßigen Muster zu.
	 * 
	 */
	public void generateLevel() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x * y % 10 < 7) {
					tiles[x + y * width] = Tile.GRASS_1.getId();
				} else {
					tiles[x + y * width] = BasicSolidTile.STONE.getId();
				}
			}
		}
	}

	public void setDefault(int playerX, int playerY, int enemyX, int enemyY) {
		for (Entity p : entities) {
			if (p instanceof Player) {
				p.setX(playerX << 4);
				p.setY(playerY << 4);
				((Player) p).setDir(3);
				for (Entity e : entities) {
					if (e.equals(((Player) p).getEnemyInt())) {
						e.setX(enemyX << 4);
						e.setY(enemyY << 4);
						((Enemy) e).setDir(4);
					}
				}
			}
		}
		loadLevelFromFile();
	}

	/**
	 * Die Updatemethode, welche die einzelnen Updatemethoden aller im Level
	 * befindlichen Objekte aufruft.
	 */
	public void tick() {
		if (!stop) {
			for (Entity e : entities) {
				e.tick();
			}

			for (Tile t : Tile.tiles) {
				if (t == null) {
					break;
				} else if (t instanceof AnimatedTile) {
					((AnimatedTile) t).tick();
				}
			}
		}
	}

	/**
	 * Die Rendermethode zeichnet alle Objekte des Levels indem die Methoden,
	 * welche für das Zeichnen der einzelnen Objekte verantwortlich sind,
	 * aufgerufen werden.
	 * 
	 * @see Level#renderTiles(Screen, int, int)
	 * @see Level#renderEntities(Screen)
	 * @param screen
	 *            Screen in dem die Objekte gezeichnet werden
	 * @param xOffset
	 *            x-Koordinate des Screens
	 * @param yOffset
	 *            y-Koordinate des Screens
	 */
	public void render(Screen screen, int xOffset, int yOffset) {
		if (!stop) {
			renderTiles(screen, xOffset, yOffset);
			renderEntities(screen);
		}
	}

	public void stop() {
		stop = true;
	}

	public void resume() {
		stop = false;
	}

	/**
	 * Getter für die Referenz Tile.
	 * 
	 * @param x
	 *            x-Koordinate des Tiles im Level
	 * @param y
	 *            y-Koordinate des Tiles im Level
	 * @return Tile am Punkt (x|y)
	 */
	public Tile getTile(int x, int y) {
		if (0 > x || x >= width || 0 > y || y >= height)
			return Tile.VOID;
		return Tile.tiles[tiles[x + y * width]];
	}

	/**
	 * Setter für die Referenz tiles am Punkt (x|y).
	 * 
	 * @param x
	 *            x-Koordinate des Tiles im Level
	 * @param y
	 *            y-Koordinate des Tiles im Level
	 * @param tile
	 *            erwartet neuen Wert für tiles am Punkt (x|y)
	 */
	public void setTile(int x, int y, Tile tile) {
		Tile.tiles[tiles[x + y * width]] = tile;
	}

	/**
	 * Fügt ein neues Objekt vom Typ Entity in die Liste entities ein.
	 * 
	 * @param entity
	 *            erwartet neues Entityobjekt
	 */
	public void addEntity(Entity entity) {
		this.entities.add(entity);
	}

	/**
	 * Lädt die neuen Tiles mit der entsprechenden Farbe im Level.
	 */
	private void loadTiles() {
		int[] tileColours = this.image.getRGB(0, 0, width, height, null, 0, width);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tileCheck: for (Tile t : Tile.tiles) {
					if (t != null && t.getLevelColour() == tileColours[x + y * width]) {
						this.tiles[x + y * width] = t.getId();
						break tileCheck;
					}
				}
			}
		}
	}

	/**
	 * Die Methode kopiert ein Level in neues File.
	 */
	@SuppressWarnings("unused")
	private void saveLevelToFile() {
		try {
			ImageIO.write(image, "png", new File(Level.class.getResource(this.imagePath).getFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Zeichnen der einzelnen Tiles im Level.
	 * 
	 * @param screen
	 *            Screen in dem die Kacheln gezeichnet werden
	 * @param xOffset
	 *            x-Koordinate des Screens
	 * @param yOffset
	 *            y-Koordinate des Screens
	 */
	protected void renderTiles(Screen screen, int xOffset, int yOffset) {
		if (xOffset < 0)
			xOffset = 0;
		if (xOffset > ((width << 4) - screen.width))
			xOffset = ((width << 4) - screen.width);
		if (yOffset < 0)
			yOffset = 0;
		if (yOffset > ((height << 4) - screen.height))
			yOffset = ((height << 4) - screen.height);

		screen.setOffset(xOffset, yOffset);

		for (int y = (yOffset >> 4); y < (yOffset + screen.height >> 4) + 1; y++) {
			for (int x = (xOffset >> 4); x < (xOffset + screen.width >> 4) + 1; x++) {
				getTile(x, y).render(screen, this, x << 4, y << 4);
			}
		}

		renderWaterEdges(screen);
	}

	/**
	 * Alle Entities im Level werden gezeichnet.
	 * 
	 * @param screen
	 *            Screen in dem die Entities gezeichnet werden
	 */
	protected void renderEntities(Screen screen) {
		for (Entity e : entities) {
			e.render(screen, 14);
		}
	}

	/**
	 * Zeichnen des Wasserrandes. der Wasserrand ist ein BasicTile; mit dieser
	 * Methode wird die richtige Kachel für jede Position des Wasserrandes
	 * automatisch erzeugt.
	 * 
	 * @param screen
	 *            Screen in dem Wasserrand gezeichnet wird
	 */
	private void renderWaterEdges(Screen screen) {

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (getTile(x, y).getId() != 3) {
					if (getTile(x - 1, y).getId() != 3 && getTile(x, y - 1).getId() != 3
							&& getTile(x + 1, y).getId() != 3 && getTile(x, y + 1).getId() != 3
							&& getTile(x + 1, y + 1).getId() == 3) {
						screen.render(x * 16, y * 16, 64 * 2 + 5, Colours.get(131, 211, 322, 004), 0, 4, 1);
					} else if (getTile(x - 1, y).getId() != 3 && getTile(x + 1, y).getId() != 3
							&& getTile(x, y - 1).getId() != 3 && getTile(x, y + 1).getId() == 3) {
						screen.render(x * 16, y * 16, 64 * 2 + 6, Colours.get(131, 211, 322, 004), 0, 4, 1);
					} else if (getTile(x + 1, y).getId() != 3 && getTile(x, y - 1).getId() != 3
							&& getTile(x - 1, y).getId() != 3 && getTile(x, y + 1).getId() != 3
							&& getTile(x - 1, y + 1).getId() == 3) {
						screen.render(x * 16, y * 16, 64 * 2 + 7, Colours.get(131, 211, 322, 004), 0, 4, 1);
					} else if (getTile(x - 1, y).getId() != 3 && getTile(x, y - 1).getId() != 3
							&& getTile(x, y + 1).getId() != 3 && getTile(x + 1, y).getId() == 3) {
						screen.render(x * 16, y * 16, 64 * 3 + 5, Colours.get(131, 211, 322, 004), 0, 4, 1);
					} else if (getTile(x + 1, y).getId() != 3 && getTile(x, y - 1).getId() != 3
							&& getTile(x, y + 1).getId() != 3 && getTile(x - 1, y).getId() == 3) {
						screen.render(x * 16, y * 16, 64 * 3 + 7, Colours.get(131, 211, 322, 004), 0, 4, 1);
					} else if (getTile(x - 1, y).getId() != 3 && getTile(x, y + 1).getId() != 3
							&& getTile(x + 1, y).getId() != 3 && getTile(x, y - 1).getId() != 3
							&& getTile(x + 1, y - 1).getId() == 3) {
						screen.render(x * 16, y * 16, 64 * 4 + 5, Colours.get(131, 211, 322, 004), 0, 4, 1);
					} else if (getTile(x, y + 1).getId() != 3 && getTile(x - 1, y).getId() != 3
							&& getTile(x + 1, y).getId() != 3 && getTile(x, y - 1).getId() == 3) {
						screen.render(x * 16, y * 16, 64 * 4 + 6, Colours.get(131, 211, 322, 004), 0, 4, 1);
					} else if (getTile(x + 1, y).getId() != 3 && getTile(x, y + 1).getId() != 3
							&& getTile(x - 1, y).getId() != 3 && getTile(x, y - 1).getId() != 3
							&& getTile(x - 1, y - 1).getId() == 3) {
						screen.render(x * 16, y * 16, 64 * 4 + 7, Colours.get(131, 211, 322, 004), 0, 4, 1);
					} else if (getTile(x - 1, y).getId() == 3 && getTile(x, y - 1).getId() == 3
							&& getTile(x + 1, y).getId() != 3 && getTile(x, y + 1).getId() != 3) {
						screen.render(x * 16, y * 16, 64 * 2 + 5, Colours.get(004, 211, 322, 131), 0, 4, 1);
					} else if (getTile(x + 1, y).getId() == 3 && getTile(x, y - 1).getId() == 3
							&& getTile(x, y + 1).getId() != 3 && getTile(x - 1, y).getId() != 3) {
						screen.render(x * 16, y * 16, 64 * 2 + 7, Colours.get(004, 211, 322, 131), 0, 4, 1);
					} else if (getTile(x - 1, y).getId() == 3 && getTile(x, y + 1).getId() == 3
							&& getTile(x + 1, y).getId() != 3 && getTile(x, y - 1).getId() != 3) {
						screen.render(x * 16, y * 16, 64 * 4 + 5, Colours.get(004, 211, 322, 131), 0, 4, 1);
					} else if (getTile(x + 1, y).getId() == 3 && getTile(x, y + 1).getId() == 3
							&& getTile(x - 1, y).getId() != 3 && getTile(x, y - 1).getId() != 3) {
						screen.render(x * 16, y * 16, 64 * 4 + 7, Colours.get(004, 211, 322, 131), 0, 4, 1);
					}
				}
			}
		}
	}
}
