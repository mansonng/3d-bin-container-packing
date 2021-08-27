package com.github.skjolber.packing.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 2D shelf points, i.e. points organized as in a staircase.
 *
 */

public class ShelfPoints {

	private static final ShelfPoint ZERO = new ShelfPoint(0, 0, 0, 0);
	
	private final int width;
	private final int depth;

	private List<ShelfPoint> values = new ArrayList<>();

	public ShelfPoints(int width, int depth) {
		super();
		this.width = width;
		this.depth = depth;
		
		values.add(ZERO);
	}

	public boolean add(ShelfPoint point, int dx, int dy) {
		
		ShelfPoint moveDy = point.splitDy(dx, dy); // a
		ShelfPoint moveDx = point.splitDx(dx, dy); // b
		
		int index = values.indexOf(point);

		values.remove(index);
		
		p:
		if(index > 0) {
			for(int i = index - 1; i >= 0; i--) {
				ShelfPoint previous = values.get(i); // c
				if(previous.getY() <= moveDy.getY()) {
					if(previous.getYy() <= moveDy.getY()) {
						
						// |
						// |
						// |
						// |
						// |
						// |               a---------------|
						// |               |               |
						// d--------       |               |
						// |       |       |               |
						// |       c-------|               |
						// |       |       |               |
						// |-------|-------|---------------b-------------
						//
						
						// delete c
						values.remove(i);

						// adjust left
						moveDy.setX(previous.getX());
						
						// |
						// |
						// |
						// |
						// |
						// |       a'----------------------|
						// |       |                       |
						// d--------                       |
						// |       |                       |
						// |       |                       |
						// |       |                       |
						// |-------|-----------------------b-------------
						//
						
						index--;
					} else {
						
						// |
						// |
						// |
						// d-------|
						// |       |
						// |       |
						// |       |       a---------------|
						// |       |       |               |
						// |       c-------|               |
						// |       |       |               |
						// |-------|-------|---------------b-------------
						//

						previous.setY(moveDy.getY());
						previous.setXX(moveDy.getXx());
						
						// |
						// |
						// |
						// d-------|
						// |       |
						// |       |
						// |       c'----------------------|
						// |       |                       |
						// |       |                       |
						// |       |                       |
						// |-------|-----------------------b-------------
						//
						
						break p;
					}
				} else {
					
					// |
					// |
					// |
					// c-------|
					// |       |
					// |       |
					// |       a----------------|
					// |       |                |
					// |       |                |
					// |       |                |
					// |-------|----------------b-------------------
					//
					
					break;
				}
			}
			// removed all to the left
			if(moveDy.getXx() < width && moveDy.getYy() < depth) {
				values.add(index, moveDy);
				index++;
			}
		} else {
			if(moveDy.getXx() < width && moveDy.getYy() < depth) {
				values.add(0, moveDy);
				index++;
			}
		}
		
		n:
		if(index < values.size()) {
			for(int i = index; i < values.size(); i++) {
				ShelfPoint next = values.get(i); // c
				if(next.getX() <= moveDx.getX()) {
					if(next.getXx() <= moveDx.getX()) {
						
						// |
						// |
						// |
						// |---------------|
						// |               |
						// |               a-----------------------
						// |               |                      |
						// |               |---------------|------b
						// |               |               |
						// |               |               |
						// |               |---------------c---|
						// |               |                   |
						// |               |                   |
						// |               |                   |
						// |---------------|-------------------d--------------
						//
												
						// delete
						
						values.remove(i);
						i--;

						// adjust down
						moveDx.setY(next.getY());
						
						// |
						// |
						// |
						// |---------------|
						// |               |
						// |               a-----------------------
						// |               |                      |
						// |               |                      |
						// |               |                      |
						// |               |                      |
						// |               |----------------------b'
						// |               |                   |
						// |               |                   |
						// |               |                   |
						// |---------------|-------------------d--------------
						//
							
						
					} else {
						// |
						// |
						// |
						// |---------------|
						// |               |
						// |               a-----------------------
						// |               |                      |
						// |               |----------------------b
						// |               |                |         
						// |               |                |          
						// |               |----------------c------------|
						// |               |                             |
						// |               |                             |
						// |               |                             |
						// |---------------|-----------------------------d---------
						//
						
						// move c right, give new top y position
						
						next.setX(moveDx.getX());
						next.setYY(moveDx.getYy());
						
						// |
						// |
						// |
						// |---------------|
						// |               |
						// |               a-----------------------
						// |               |                      |
						// |               |                      |          
						// |               |                      |          
						// |               |                      |          
						// |               |----------------------c'-----|
						// |               |                             |
						// |               |                             |
						// |               |                             |
						// |---------------|-----------------------------d--------
						//

						break n;
					}
				} else {
					
					// |
					// |
					// |
					// |-------|
					// |       |
					// |       |
					// |       a----------------|
					// |       |                |
					// |       |                b---------c 
					// |       |                |         |
					// |-------|----------------|---------|---------
					//
					
					break;
				}
			}
			// removed all below
			if(moveDx.getXx() < width && moveDx.getYy() < depth) {
				values.add(moveDx);
			}
		} else {
			if(moveDx.getXx() < width && moveDx.getYy() < depth) {
				values.add(moveDx);
			}
		}
		
		return !values.isEmpty();
	}
	
	public List<ShelfPoint> getValues() {
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
		return "ShelfPoints [width=" + width + ", depth=" + depth + ", values=" + values + "]";
	}
	
	
}
