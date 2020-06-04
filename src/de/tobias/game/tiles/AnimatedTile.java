package de.tobias.game.tiles;

/**
 * Klasse für die Realisierung einer animierten Kachel. Das Aussehen der Kachel
 * wechselt dabei dynamisch. Ein Beispiel dafür ist die Wasserkachel.
 * 
 * @author Tobias
 *
 */
public class AnimatedTile extends BasicTile {

	private int[][] animationTileCoords;
	private int currentAnimationIndex;
	private long lastIterationTime;
	private int animationSwitchDelay;

	/**
	 * Erzeugt eine neue animierte Kachel.
	 * 
	 * @param id
	 *            Nummer der Kachel
	 * @param animationCoords
	 *            x- und y-Koordinate der Kachel im SpriteSheet
	 * @param tileColour
	 *            Farbe der Kachel
	 * @param levelColour
	 *            Farbe in der Level-Datei
	 * @param animationSwitchDelay
	 *            Animationsgeschwindigkeit
	 */
	public AnimatedTile(int id, int[][] animationCoords, int tileColour, int levelColour, int animationSwitchDelay) {
		super(id, animationCoords[0][0], animationCoords[0][1], tileColour, levelColour);
		this.animationTileCoords = animationCoords;
		this.currentAnimationIndex = 0;
		this.lastIterationTime = System.currentTimeMillis();
		this.animationSwitchDelay = animationSwitchDelay;
	}

	/**
	 * Methode für das Updaten der Kachel. das Aussehen der Kachel ändert sich
	 * immer mit der Geschwindigkeit animationSwitchDelay.
	 */
	public void tick() {
		if ((System.currentTimeMillis() - lastIterationTime) >= (animationSwitchDelay)) {
			lastIterationTime = System.currentTimeMillis();
			currentAnimationIndex = (currentAnimationIndex + 1) % animationTileCoords.length;
			this.tileId = (animationTileCoords[currentAnimationIndex][0]
					+ (animationTileCoords[currentAnimationIndex][1] * 64));
		}
	}
}
