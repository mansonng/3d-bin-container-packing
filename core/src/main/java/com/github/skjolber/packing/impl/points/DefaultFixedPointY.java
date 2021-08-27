package com.github.skjolber.packing.impl.points;

public class DefaultFixedPointY extends Point implements FixedPointX  {

	/** range constrained to current minX */
	private int fixedMinY;
	private int fixedMaxY;
	
	public DefaultFixedPointY(int minX, int minY, int maxY, int maxX, int fixedY, int fixedYy) {
		super(minX, minY, maxY, maxX);
		this.fixedMinY = fixedY;
		this.fixedMaxY = fixedYy;
	}
	@Override
	public boolean isFixedY() {
		return false;
	}
	@Override
	public boolean isFixedX() {
		return true;
	}

	public int getFixedMinY() {
		return fixedMinY;
	}
	
	public int getFixedMaxY() {
		return fixedMaxY;
	}
}
