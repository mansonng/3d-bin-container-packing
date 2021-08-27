package com.github.skjolber.packing.impl.points;

public class DefaultFixedPointXY extends Point implements FixedPointY, FixedPointX {

	/** range constrained to current minY */
	private int fixedMinX;
	private int fixedMaxX;
	
	/** range constrained to current minX */
	private int fixedMinY;
	private int fixedMaxY;
	
	public DefaultFixedPointXY(int minX, int minY, int maxY, int maxX, int fixedX, int fixedXx, int fixedY, int fixedYy) {
		super(minX, minY, maxY, maxX);
		this.fixedMinX = fixedX;
		this.fixedMaxX = fixedXx;
		this.fixedMinY = fixedY;
		this.fixedMaxY = fixedYy;
	}


	@Override
	public boolean isFixedY() {
		return true;
	}
	
	@Override
	public boolean isFixedX() {
		return true;
	}
	
	public int getFixedMinX() {
		return fixedMinX;
	}

	public int getFixedMaxX() {
		return fixedMaxX;
	}

	public int getFixedMinY() {
		return fixedMinY;
	}
	
	public int getFixedMaxY() {
		return fixedMaxY;
	}

}
