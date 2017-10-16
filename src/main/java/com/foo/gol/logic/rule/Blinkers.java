package com.foo.gol.logic.rule;

import com.foo.gol.logic.ICell;

public class Blinkers extends AbstractPredefinedRule implements IChangeAliveRule {
	public static final String LABEL = "Blinkers";

	@Override
	public boolean evaluate(ICell cell) {
		int adjacentsAlive = IChangeAliveRule.countAdjacentsAlive(cell);
		boolean changes = false;
		if (cell.isAlive()) {
			changes = adjacentsAlive != 2;
		} else {
			changes = (adjacentsAlive == 3 || adjacentsAlive == 4 || adjacentsAlive == 5);
		}
		return changes;
	}

	@Override
	public String getAlivesSurviveString() {
		return "2";
	}

	@Override
	public String getDeadsBornString() {
		return "3,4,5";
	}

	@Override
	public String getRleString() {
		return "B345/S2";
	}

	@Override
	public String getType() {
		return LABEL;
	}
}