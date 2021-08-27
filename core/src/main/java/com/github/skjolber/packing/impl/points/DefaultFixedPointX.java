package com.github.skjolber.packing.impl.points;

public class DefaultFixedPointX extends Point implements FixedPointY {

	/** range constrained to current minY */
	private int fixedMinX;
	private int fixedMaxX;
	
	public DefaultFixedPointX(int minX, int minY, int maxY, int maxX, int fixedX, int fixedXx) {
		super(minX, minY, maxY, maxX);
		this.fixedMinX = fixedX;
		this.fixedMaxX = fixedXx;
	}
	
	@Override
	public boolean isFixedY() {
		return true;
	}
	@Override
	public boolean isFixedX() {
		return false;
	}
	
	public int getFixedMinX() {
		return fixedMinX;
	}
	
	public int getFixedMaxX() {
		return fixedMaxX;
	}

}
