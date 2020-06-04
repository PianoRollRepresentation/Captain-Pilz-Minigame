package de.tobias.game.menus;

import de.tobias.game.Game;
import de.tobias.game.InputHandler;
import de.tobias.game.gfx.Button;
import de.tobias.game.gfx.Colours;
import de.tobias.game.gfx.Font;
import de.tobias.game.gfx.Screen;
import de.tobias.game.level.Level;
import de.tobias.game.team.Team;

public class PlayerMenu extends Menu {

	private Team team = null;
	private Level level;

	public PlayerMenu(Game game, Level level, InputHandler input) {
		super(game, input);
		this.level = level;
	}

	@Override
	public void create(InputHandler input) {
		fonts = null;
		buttons = new Button[4];
		fonts = new Font[6];
		buttonPressed = new boolean[buttons.length];
		createButtons(input, "team", "speichern", "hilfe", "beenden");
		createFonts("Tasten: ", "Bewegung: Pfeiltasten", "Menue: Leertaste", "Menue Verlassen: Escape",
				"Auswahl: Enter");
		buttons[0].setOnCursor(true);
	}

	@Override
	public void tick() {
		super.tick();
		
		if (input.esc.isPressed()) {
			level.resume();
			game.setMenu(null);
		}
		
		if (buttonPressed[3])
			Game.exit = true;
	}

	@Override
	public void render(Screen screen) {
		renderButtonsWithLayout(screen, Game.WIDTH / 2 - (6 << 4) / 2, Game.HEIGHT / 2 - (5 << 4) / 2, 6, 5, 0, 3);
		if (buttonPressed[0]) {
			renderLayout(screen, Game.WIDTH / 2 - (13 << 4) / 2, Game.HEIGHT / 2 - (6 << 4) / 2, 13, 6);
			screen.renderBasic(195, 50, 32 * 26 + 2, Colours.get(-1, 111, 222, 333), 0, 4, 1);
			new Font(team.getUnits().get(0).getName() + "   " + team.getUnits().size() + "x").renderBasic(screen, 30,
					55, Colours.get(-1, -1, -1, 000), 1);
		}
		if (buttonPressed[2])
			renderFontsWithLayout(screen, Game.WIDTH / 2 - (13 << 4) / 2, Game.HEIGHT / 2 - (6 << 4) / 2, 13, 6, 0, 4);
	}

	public boolean[] getButtonPressed() {
		return buttonPressed;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
}
