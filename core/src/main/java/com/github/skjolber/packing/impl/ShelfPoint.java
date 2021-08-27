package com.github.skjolber.packing.impl;

public class ShelfPoint implements Comparable<ShelfPoint>{

	private int x;
	private int y;
	
	private int xx;
	private int yy;
	
	public ShelfPoint(int x, int y, int xx, int yy) {
		super();
		this.x = x;
		this.y = y;
		
		this.xx = xx;
		this.yy = yy;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public ShelfPoint splitDx(int dx, int dy) {
		return new ShelfPoint(x + dx, y, Math.max(x + dx, xx), y + dy);
	}

	public ShelfPoint splitDy(int dx, int dy) {
		return new ShelfPoint(x, y + dy, x + dx, Math.max(y + dy, yy));
	}

	@Override
	public int compareTo(ShelfPoint o) {
		
		int x = Integer.compare(this.x, o.x);

		if(x == 0) {
			return Integer.compare(this.y, o.y);
		}
		return x;
	}

	public int getXx() {
		return xx;
	}
	
	public int getYy() {
		return yy;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

	@Override
	public String toString() {
		return "ExtremePoint [x=" + x + ", y=" + y + ", xx=" + xx + ", yy=" + yy + "]";
	}

	public void setXX(int xx) {
		this.xx = xx;
	}

	public void setYY(int yy) {
		this.yy = yy;
	}
	
	
	
}
