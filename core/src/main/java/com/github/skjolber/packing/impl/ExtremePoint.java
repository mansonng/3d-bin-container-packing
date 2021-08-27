package com.github.skjolber.packing.impl;

public class ExtremePoint implements Comparable<ExtremePoint>{

	private int x;
	private int y;
	
	private int xx;
	private int yy;

	private int itemx;
	private int itemy;

	private int itemxx;
	private int itemyy;

	public ExtremePoint(int x, int y, int xx, int yy, int itemx, int itemy, int itemxx, int itemyy) {
		super();
		this.x = x;
		this.y = y;

		this.xx = xx;
		this.yy = yy;

		this.itemx = itemx;
		this.itemy = itemy;
		
		this.itemxx = itemxx;
		this.itemyy = itemyy;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public ExtremePoint splitDx(int dx, int dy, int width, int height) {
		return new ExtremePoint(x + dx, y+dy, width, height, x, y, x + dx, y + dy);
	}

	public ExtremePoint splitDy(int dx, int dy, int width, int height) {
		return new ExtremePoint(x+dx, y + dy, width, height, x, y, x + dx, y + dy);
	}

	@Override
	public int compareTo(ExtremePoint o) {
		
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

	public void setXx(int xx) {
		this.xx = xx;
	}

	public void setYy(int yy) {
		this.yy = yy;
	}

	public int getItemX() {
		return itemx;
	}
	
	public int getItemXx() {
		return itemxx;
	}
	
	public int getItemY() {
		return itemy;
	}
	
	public int getItemYy() {
		return itemyy;
	}

	public boolean isEmpty() {
		return x >= xx || y >= yy;
		
	}

	@Override
	public String toString() {
		return "ExtremePoint [x=" + x + ", y=" + y + ", xx=" + xx + ", yy=" + yy + ", itemx=" + itemx + ", itemy="
				+ itemy + ", itemxx=" + itemxx + ", itemyy=" + itemyy + "]";
	}

	
}
