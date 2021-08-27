package com.github.skjolber.packing.impl.points;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * 2D shelf points, i.e. points organized as in a stair.
 *
 */

public class ExtremePoints {
	
	private final int width;
	private final int depth;

	private List<Point> values = new ArrayList<>();

	public ExtremePoints(int width, int depth) {
		super();
		this.width = width;
		this.depth = depth;
		
		values.add(new DefaultFixedPointXY(0, 0, width, width, 0, 0, width, width));
	}
	
	public boolean add(Point source, int dx, int dy) {
		
		System.out.println("Add " + source.getMinX() + "x" + source.getMinY() + " " + dx + "x" + dy);
		
		int xx = source.getMinX() + dx;
		int yy = source.getMinY() + dy;
		
		
		if(source.isFixedY() && source.isFixedX()) {
			FixedPointY fixedPointY = (FixedPointY)source;
			FixedPointX fixedPointX = (FixedPointX)source;
			
			if(xx < fixedPointY.getFixedMaxX() && yy < fixedPointX.getFixedMaxY()) {
				
			} else if(xx < fixedPointY.getFixedMaxX()) {
				
			} else if(yy < fixedPointX.getFixedMaxY()) {
				
			} else {
				
			}
		} else if(source.isFixedY()) {
			FixedPointY fixedPointY = (FixedPointY)source;
			if(xx < fixedPointY.getFixedMaxX()) {
				
			}
		} else if(source.isFixedX()) {
			FixedPointX fixedPointX = (FixedPointX)source;
			if(yy < fixedPointX.getFixedMaxY()) {
				
			}
		} else {
			
		}

		Collections.sort(values);
		
		return !values.isEmpty();
	}
	
	public List<ExtremePoint> getValues() {
		return values;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public int getWidth() {
		return width;
	}

	@Override
	public String toString() {
		return "ExtremePoints [width=" + width + ", depth=" + depth + ", values=" + values + "]";
	}

	protected boolean intersects(int ax, int axx, int bx, int bxx) {
		return between(ax, bx, axx) || between(ax, bxx, axx) || (bx < ax && axx < bxx);
	}

	protected static boolean between(int start, int value, int end) {
		return start <= value && value <= end;
	}
	
	public List<ExtremePoint> getAllPoints() {
		List<ExtremePoint> list = new ArrayList<>();
		list.addAll(values);
		list.addAll(previous);
		Collections.sort(list);
		return list;
	}

	
}
