package com.github.skjolber.packing.impl;

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

	private List<ExtremePoint> values = new ArrayList<>();
	private List<ExtremePoint> previous = new ArrayList<>();

	public ExtremePoints(int width, int depth) {
		super();
		this.width = width;
		this.depth = depth;
		
		values.add(new ExtremePoint(0, 0, width, depth, 0, 0, 0, 0));
	}
	
	public boolean add(ExtremePoint source, int dx, int dy) {
		
		System.out.println("Add " + source.getX() + "x" + source.getY() + " " + dx + "x" + dy);
		
		int x = source.getX() + dx;
		int y = source.getY() + dy;
		
		ExtremePoint moveDy = source.splitDy(dx, dy, width, depth); // a
		ExtremePoint moveDx = source.splitDx(dx, dy, width, depth); // b
		
		int index = values.indexOf(source);
		values.remove(index);

		previous.add(source);

		for(int i = 0; i < values.size(); i++) {
			
			ExtremePoint point = values.get(i);

			if(point.getX() >= source.getX() && point.getX() < x) {
				if(point.getY() >= source.getY() && point.getY() < y) {
					System.out.println("Remove shadowed point " + point.getX() + "x" + point.getY());
					values.remove(i);
					i--;
					
					previous.add(point);
				}
			}
		}
		
		if(!values.isEmpty()) {
			
			for(int i = 0; i < values.size(); i++) {
				
				// constrain upwards
				ExtremePoint point = values.get(i);
				
				// before
				// 
				// -------------------    
				// |                      
				// |                           
				// *-----|    
				// |     |    
				// |     |    
				// |     *----|
				// |     |    |
				// |     |    |
				// -----------*-------

				
				// after
				// 
				// -------------------    
				// |                      
				// |                           
				// *-----|    
				// |     |--------|
				// |     |        |
				// |     *--------|
				// |     |    |
				// |     |    |
				// -----------*---*---
				//            ^
				//        constrained upwards

				
				if(point.getItemY() < moveDx.getItemY()) {
					
					if(moveDx.getItemX() <= point.getX() && point.getX() < moveDx.getItemXx()) {
						if(point.getYy() > moveDx.getItemY()) {
							System.out.println("Constrain point xx " + i);
							
							point.setYy(moveDx.getItemY());
							if(point.isEmpty()) {
								values.remove(i);
								i--;
								
								previous.add(point);
							}						
						}
						
					}
				}
			}

			// constraint points to the left
			for(int i = 0; i < values.size(); i++) {
				ExtremePoint point = values.get(i);

				if(point.getX() < moveDy.getItemX()) {
					if(moveDy.getItemY() <= point.getY() && point.getY() < moveDy.getItemYy()) {
						if(point.getXx() > moveDy.getItemX()) {
							System.out.println("Constrain point xx " + i);

							point.setXx(moveDy.getItemX());
							if(point.isEmpty()) {
								values.remove(i);
								i--;
								
								previous.add(point);
							}
						}
					}
				}

			}
			
			if(moveDx.getY() > 0) {
				// find highest item which is below to the right
				
				ExtremePoint max = null;
				for(int i = 0; i < values.size(); i++) {
					ExtremePoint point = values.get(i);
					if(point.getItemXx() >= moveDx.getX()) {
						if(max == null || point.getItemYy() > max.getItemYy()) {
							max = point;
						}
					}
				}
				if(max != null) {
					if(max.getItemYy() < moveDx.getY()) {
						moveDx.setY(max.getItemYy());
						moveDx.setXx(max.getXx());
						System.out.println("Shift partly down for " + moveDx.getItemX() + "x" + moveDx.getItemY());
					} else {
						System.out.println("No shift down for "  + moveDx.getItemX() + "x" + moveDx.getItemY());
					}
				} else {
					System.out.println("Shift all the way down " + moveDx.getItemX() + "x" + moveDx.getItemY());
					moveDx.setY(0);
				}
			} else {
				System.out.println("No shift down, already all the way down " + moveDx.getItemX() + "x" + moveDx.getItemY());
			}
			
			// movedy shift left
			if(moveDy.getX() > 0) {
					// find above item which is closes to the left

				ExtremePoint max = null;
				for(int i = 0; i < values.size(); i++) {
					ExtremePoint point = values.get(i);
					
					
					// bokser og punkter må være to forskjellige lister
					
					
					
					
					if(point.getItemXx() < moveDy.getX()) {
						
						if(point.getItemYy() >= moveDy.getY()) {
							if(max == null || point.getItemXx() > max.getItemXx()) {
								max = point;
								System.out.println("Max " + max);
							}
						}
					}
				}

				if(max != null) {
					System.out.println("Max is " + max.getItemX() + "x" + max.getItemY());
					if(max.getItemXx() < moveDy.getX()) {
						moveDy.setX(max.getItemXx());
						
						System.out.println("Shift partly left for " + moveDy.getItemX() + "x" + moveDy.getItemY());
					} else {
						System.out.println("No shift left for " + moveDy.getItemX() + "x" + moveDy.getItemY());
					}
				} else {
					System.out.println("Shift all the way left for " + moveDy.getItemX() + "x" + moveDy.getItemY());
					moveDy.setX(0);
				}
			} else {
				System.out.println("No shift, already all the way left for " + moveDy.getItemX() + "x" + moveDy.getItemY());
			}
			
			// constraint xx and yy
			for(int i = 0; i < values.size(); i++) {
				ExtremePoint point = values.get(i);
				
				if(point.getItemX() <= moveDy.getX() && moveDy.getX() < point.getItemXx()) {
					if(point.getItemY() > moveDy.getY()) {
						if(moveDy.getYy() > point.getItemY()) {
							System.out.println("Constraint movedy y ");
							moveDy.setYy(point.getItemY());
						}
					}
				}
				if(point.getItemX() <= moveDx.getX() && moveDx.getX() < point.getItemXx()) {
					if(point.getItemY() > moveDx.getY()) {
						if(moveDx.getYy() > point.getItemY()) {
							System.out.println("Constraint movedx y ");
							moveDx.setYy(point.getItemY());
						}
					}
				}
				
				if(point.getItemY() <= moveDy.getY() && moveDy.getY() < point.getItemYy()) {
					if(point.getItemX() > moveDy.getX()) {
						if(moveDy.getXx() > point.getItemX()) {
							System.out.println("Constraint movedy x ");
							moveDy.setXx(point.getItemX());
						}
					}
				}
				
				if(point.getItemY() <= moveDx.getY() && moveDx.getY() < point.getItemYy()) {
					if(point.getItemX() > moveDx.getX()) {
						if(moveDx.getXx() > point.getItemX()) {
							System.out.println("Constraint movedx x ");
							moveDx.setXx(point.getItemX());
						}
					}
				}
			}
		} else {
			moveDy.setX(0);
			moveDx.setY(0);
		}
		
		if(moveDy.getX() < width && moveDy.getY() < depth) {
			values.add(moveDy);
		}

		if(moveDx.getX() < width && moveDx.getY() < depth) {
			values.add(moveDx);
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
