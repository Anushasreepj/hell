package com.foo.gol.patterns;

import com.foo.gol.logic.ICell;

import java.util.List;

public class PatternRLEEncoder {
	public static String encode(int width, List<ICell> cells) throws InvalidRLEFormatException {
		if (cells.size() % width != 0) {
			throw new InvalidRLEFormatException("Cells list must have size consistent with specified width");
		}
		int[] pattern = new int[cells.size()];
		for (int i = 0; i < cells.size(); i++) {
			pattern[i] = cells.get(i).isAlive() ? 1 : 0;
		}
		return encode(width, pattern);
	}

	public static String encode(int width, int[] pattern) throws InvalidRLEFormatException {
		if (pattern.length % width != 0) {
			throw new InvalidRLEFormatException("Cells list must have size consistent with specified width");
		}
		StringBuilder builder = new StringBuilder(20 + pattern.length);
		builder.append("x=").append(width).append(",y=").append(pattern.length / width).append("\n");
		int rows = pattern.length / width;
		int columns = width;
		String line = "";
		for (int row = 0; row < rows; row++) {
			String rowString = "";
			int runLength = 0;
			Boolean lastOn = null;
			for (int column = 0; column < columns; column++) {
				Boolean isOn = pattern[(row * columns) + column] != 0;
				if (lastOn == null) {
					runLength = 1;
					lastOn = isOn;
				} else if (isOn != lastOn) {
					rowString += (runLength > 1 ? "" + runLength : "") + (lastOn ? "o" : "b");
					runLength = 1;
					lastOn = isOn;
					if (column == (columns - 1) && isOn) {
						rowString += (runLength > 1 ? "" + runLength : "") + (isOn ? "o" : "b");
					}
				} else {
					runLength++;
					if (column == (columns - 1) && isOn) {
						rowString += (runLength > 1 ? "" + runLength : "") + "o";
					}
				}
			}
			if (rowString.equals(columns + "b") || rowString.isEmpty()) {
				rowString = "b";
			}
			rowString += (row == (rows - 1) ? "!" : "$");
			if ((line.length() + rowString.length()) > 80) {
				builder.append(line);
				line = rowString;
			} else {
				line += rowString;
			}
		}
		builder.append(line);
		return builder.toString();
	}
}
