package de.tobias.game.team;

import java.util.ArrayList;
import java.util.List;

import de.tobias.game.entities.Unit;

public class Team {

	private List<Unit> units = new ArrayList<>();
	private int deadUnits = 0;

	public Team(Unit... units) {
		for (int i = 0; i < units.length; i++) {
			this.units.add(units[i]);
		}
	}

	public List<Unit> getUnits() {
		return units;
	}

	public int getDeadUnits() {
		for (Unit u : units) {
			if (deadUnits < units.size()){
				deadUnits += u.getDead();
				u.setDead(0);
			}
		}
		return deadUnits;
	}

	public void setDeadUnits(int deadUnits) {
		this.deadUnits = deadUnits;
	}
}
