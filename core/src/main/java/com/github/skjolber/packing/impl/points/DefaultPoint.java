package com.github.skjolber.packing.impl.points;

public class DefaultPoint extends Point {

	public DefaultPoint(int minX, int minY, int maxY, int maxX) {
		super(minX, minY, maxY, maxX);
	}

	@Override
	public boolean isFixedY() {
		return false;
	}

	@Override
	public boolean isFixedX() {
		return false;
	}

}
