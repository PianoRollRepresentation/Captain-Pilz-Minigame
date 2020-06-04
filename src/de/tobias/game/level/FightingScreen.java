package de.tobias.game.level;

import java.util.ArrayList;
import java.util.List;

import de.tobias.game.entities.Enemy;
import de.tobias.game.entities.Entity;
import de.tobias.game.entities.Hero;
import de.tobias.game.entities.Mob;
import de.tobias.game.entities.Player;
import de.tobias.game.entities.Unit;
import de.tobias.game.fight.AvailableField;
import de.tobias.game.gfx.Screen;
import de.tobias.game.tiles.AnimatedTile;
import de.tobias.game.tiles.Tile;

public class FightingScreen extends Level {

	public FightingScreen(String imagePath) {
		super(imagePath);
		stop = true;
	}

	@Override
	public void tick() {
		if (!stop) {

			for (Tile t : Tile.tiles) {
				if (t == null) {
					break;
				} else if (t instanceof AnimatedTile) {
					((AnimatedTile) t).tick();
				}
			}

			for (Entity e : entities) {
				if (((Hero) e).getSelector().isActive()) {
					((Hero) e).tickSelector();
				}
				((Hero) e).setMoving(false);
				for (Unit u : ((Hero) e).getTeam().getUnits()) {
					u.tick();
					u.setDir(((Hero) e).getDir());
					for (AvailableField af : u.getFields()) {
						af.tick(getAfEntities());
					}
				}
			}
		}
	}

	@Override
	public void render(Screen screen, int xOffset, int yOffset) {
		if (!stop) {
			renderTiles(screen, xOffset, yOffset);
			renderEntities(screen);
			for (Entity e : entities) {
				for (Unit u : ((Hero) e).getTeam().getUnits()) {
					for (AvailableField af : u.getFields()) {
						if (u.isChosen())
							af.render(screen);
					}
				}
				for (Unit u : ((Hero) e).getTeam().getUnits()) {
					u.render(screen, 13);
					u.getHp().render(screen);
				}
				if (((Hero) e).getSelector().isActive()) {
					((Hero) e).getSelector().render(screen);
				}
			}
		}
	}

	public List<Entity> getAfEntities() {
		List<Entity> afEntities = new ArrayList<Entity>();
		for (Entity e : entities) {
			afEntities.add(e);
			for (Unit u : ((Hero) e).getTeam().getUnits()) {
				afEntities.add(u);
			}
		}

		return afEntities;
	}

	@Override
	public void setDefault(int playerX, int playerY, int enemyX, int enemyY) {
		super.setDefault(playerX, playerY, enemyX, enemyY);

		for (Entity e : entities) {
			((Mob) e).setSwimming(false);
			for (int i = 0; i < ((Hero) e).getTeam().getUnits().size(); i++) {
				((Hero) e).getTeam().getUnits().get(i).getHp().setPixels(12);
				((Hero) e).getTeam().getUnits().get(i).setY((i + 1) << 4);
				if (e instanceof Player)
					((Hero) e).getTeam().getUnits().get(i).setX((playerX + 1) << 4);
				else if (e instanceof Enemy)
					((Hero) e).getTeam().getUnits().get(i).setX((enemyX - 1) << 4);

				((Hero) e).getTeam().getUnits().get(i).setFieldLoc();
			}
		}
	}
}
