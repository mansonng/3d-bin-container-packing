package com.github.skjolber.packing.impl.points;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.skjolber.packing.Placement;

/**
 * 
 *
 */

public class ExtremePoints {
	
	private final int width;
	private final int depth;

	private List<Point> values = new ArrayList<>();
	private List<Placement> placements = new ArrayList<>();

	public ExtremePoints(int width, int depth) {
		super();
		this.width = width;
		this.depth = depth;
		
		values.add(new DefaultFixedPointXY(0, 0, width, depth, 0, width, 0, depth));
	}
	
	public boolean add(Point source, Placement placement) {
		
		System.out.println("Add " + source.getMinX() + "x" + source.getMinY() + " " + placement.getBox().getWidth() + "x" + placement.getBox().getDepth());
		
		int xx = source.getMinX() + placement.getBox().getWidth();
		int yy = source.getMinY() + placement.getBox().getDepth();
		
		if(source.isFixedY() && source.isFixedX()) {
			FixedPointY fixedPointY = (FixedPointY)source;
			FixedPointX fixedPointX = (FixedPointX)source;

			System.out.println("Could be fixed both ways");

			if(xx < fixedPointY.getFixedMaxX() && yy < fixedPointX.getFixedMaxY()) {

				System.out.println("Fixed both ways");
				
				//
				// fmaxY |----|
				//       |    |   dx
				// yy    |    |--------|
				//       |    |        |
				//       |    |        | dy
				//       |    |        |
				// minY  |    |---------------|
				//       |    |               |
				//       |    |               |
				//       |----|---------------|-----
				//          minX      xx    fmaxX

				
				// using dx
				//
				// yy   |             |
				//      |             |
				//      |             |
				//      |             |
				// minY |             *-------
				//      |                    
				//      |                    
				//      |--------------------------
				//                    xx   fmaxX

				values.add(new DefaultFixedPointXY(xx, source.getMinY(), source.getMaxX(), source.getMaxY(), xx, fixedPointY.getFixedMaxX(), source.getMinY(), yy));
				
				// using dy
				//
				//
				// fmaxY |    |
				//       |    |   dx
				// yy    |    *---------
				//       |
				//       |
				//       |
				//       |
				//       |
				//       |
				//       |--------------------------
				//           minX      xx    

				values.add(new DefaultFixedPointXY(source.getMinX(), yy, source.getMaxX(), source.getMaxY(),  source.getMinX(), xx, yy, fixedPointX.getFixedMaxY()));
				
				
			} else if(xx < fixedPointY.getFixedMaxX()) {

				System.out.println("Fixed dx");

				//       |
				//       |
				// yy    |    |--------|
				//       |    |        |
				// fmaxY |----|        |
				//       |    |        | dy
				//       |    |        |
				//       |    |        |
				//       |    |        | 
				//       |    |        |
				//  minY |    |---------------|
				//       |    |               |
				//       |    |               |
				//       |----|---------------|-----
				//           minX      xx    fmaxX
				
				
				// using dx
				values.add(new DefaultFixedPointXY(xx, source.getMinY(), source.getMaxX(), source.getMaxY(), xx, fixedPointY.getFixedMaxX(), source.getMinY(), yy));

				// using dy
				unsupportedDy(source, xx, yy);
			} else if(yy < fixedPointX.getFixedMaxY()) {

				System.out.println("Fixed dy");

				//
				// maxY |----|
				//      |    |   dx
				//      |    |-------------------|
				//      |    |                   |
				//      |    |                   | dy
				//      |    |                   |
				// minY |    |--------------------
				//      |    |               |
				//      |    |               |
				//      |----|---------------|-----
				//          minX            maxX

				// using dy
				values.add(new DefaultFixedPointXY(source.getMinX(), yy, source.getMaxX(), source.getMaxY(),  source.getMinX(), xx, yy, fixedPointX.getFixedMaxY()));

				// using dx
				unsupportedDx(source, xx, yy);
				
			} else {

				//      |
				//      |           dx
				// xx   |    |------------------------|
				//      |    |                        |
				// maxY |----|                        |
				//      |    |                        |  dy
				//      |    |                        |
				//      |    |                        |
				//      |    |                        |
				//      |    |                        |
				// minY |    |-------------------------
				//      |    |               |
				//      |    |               |
				//      |----|---------------|-------------	
				//          minX            maxX     yy
				
				unsupportedDx(source, xx, yy);
				unsupportedDy(source, xx, yy);
			}
			
		} else if(source.isFixedY()) {
			System.out.println("Could be fixed y");

			FixedPointY fixedPointY = (FixedPointY)source;
			if(xx < fixedPointY.getFixedMaxX()) {
				
				System.out.println("Is fixed y");
				//       |
				//       |
				// yy    |    |--------|
				//       |    |        |
				// fmaxY |----|        |
				//       |    |        | dy
				//       |    |        |
				//       |    |        |
				//       |    |        | 
				//       |    |        |
				//  minY |    |---------------|
				//       |    |               |
				//       |    |               |
				//       |----|---------------|-----
				//           minX      xx    fmaxX				
				
				// using dx
				values.add(new DefaultFixedPointXY(xx, source.getMinY(), source.getMaxX(), source.getMaxY(), xx, fixedPointY.getFixedMaxX(), source.getMinY(), yy));
				
				unsupportedDy(source, xx, yy);
			} else {
				System.out.println("Is not fixed y");

				unsupportedDx(source, xx, yy);
				unsupportedDy(source, xx, yy);
			}
		} else if(source.isFixedX()) {
			System.out.println("Could be fixed x");
			
			FixedPointX fixedPointX = (FixedPointX)source;
			if(yy < fixedPointX.getFixedMaxY()) {
				
				//
				// maxY |----|
				//      |    |        dx
				//      |    |-------------------|
				//      |    |                   |
				//      |    |                   | dy
				//      |    |                   |
				// minY |    |--------------------
				//      |    |               |
				//      |    |               |
				//      |----|---------------|-----
				//          minX            maxX

				// using dy
				values.add(new DefaultFixedPointXY(source.getMinX(), yy, source.getMaxX(), source.getMaxY(),  source.getMinX(), xx, yy, fixedPointX.getFixedMaxY()));

				unsupportedDx(source, xx, yy);
				
			} else {
				unsupportedDx(source, xx, yy);
				unsupportedDy(source, xx, yy);
			}
		} else {
			System.out.println("Not fixed any way");
			
			unsupportedDx(source, xx, yy);
			unsupportedDy(source, xx, yy);
		}

		values.remove(source);
		
		placements.add(placement);
		Collections.sort(values);
		
		for (int i = 0; i < values.size(); i++) {
			Point point = values.get(i);
			
			int maxX = projectRight(point.getMinX(), point.getMinY());
			int maxY = projectUp(point.getMinX(), point.getMinY() );
			
			if(maxX <= point.getMinX() || maxY <= point.getMinY()) {
				System.out.println("Remove " + point.getMinX() + "x" + point.getMinY());
				System.out.println("Because " + maxX + "x" + maxY);
				values.remove(i);
				i--;
			} else {
				point.setMaxX(maxX);
				point.setMaxY(maxY);
			}
		}
		
		System.out.println("Now have " + values.size());
		
		for (Point point2 : values) {
			System.out.println(point2.getMinX() + "x" + point2.getMinY());
		}
		
		return !values.isEmpty();
	}

	private void unsupportedDx(Point source, int xx, int yy) {
		Placement moveY = projectDownRight(xx, yy);
		if(moveY == null) {
			
			// supported one way (by container border)
			//
			//      |    |                   |
			// minY |    |--------------------
			//      |    |               |   |
			//      |    |               |   ↓
			//      |----|---------------|---*-----
			//          minX            maxX
			
			values.add(new DefaultFixedPointY(xx, 0, source.getMaxX(), source.getMaxY(), xx, width));
			
		} else if(moveY.getAbsoluteEndY() < source.getMinY()) {

			if(moveY.getAbsoluteX() <= xx && xx < moveY.getAbsoluteEndX() ) {

				// supported one way
				//
				//      |    |                   |
				// minY |    |--------------------
				//      |    |               |   ↓
				//      |    |               |   *--------|
				//      |    |               |   |        |
				//      |----|---------------|---|--------|--------
				//          minX            maxX
	
				values.add(new DefaultFixedPointY(xx, moveY.getAbsoluteEndY(), source.getMaxX(), source.getMaxY(), xx, moveY.getAbsoluteEndX()));

			} else {
				
				// unsupported both ways
				//
				//      |    |                   |
				// minY |    |--------------------
				//      |    |               |   ↓
				//      |    |               |   *    |--------|
				//      |    |               |        |        |
				//      |----|---------------|--------|--------|--------
				//          minX            maxX

				values.add(new DefaultPoint(xx, moveY.getAbsoluteEndY(), source.getMaxX(), source.getMaxY()));
			}
			
		} else {

			if(moveY.getAbsoluteX() <= xx && xx < moveY.getAbsoluteEndX() ) {
				
				// supported both ways
				// 
				//      |    |                   |
				//      |    |                   ↓
				//      |    |                   *--------|
				//      |    |                   |        |
				// minY |    |-------------------|        |
				//      |    |               |   |        |
				//      |    |               |   |        |
				//      |    |               |   |        |
				//      |----|---------------|---|--------|--------
				//          minX            maxX
	
				values.add(new DefaultFixedPointXY(xx, moveY.getAbsoluteEndY(), source.getMaxX(), source.getMaxY(), xx, moveY.getAbsoluteEndX(), moveY.getAbsoluteEndY(), yy));
			} else {

				// supported one way
				//
				//      |    |                   |
				//      |    |                   ↓
				//      |    |                   *  |-------|
				//      |    |                   |  |       |
				// minY |    |-------------------|  |       |
				//      |    |               |      |       |
				//      |    |               |      |       |
				//      |    |               |      |       |
				//      |----|---------------|------|-------|--------
				//          minX            maxX

				values.add(new DefaultPoint(xx, moveY.getAbsoluteEndY(), source.getMaxX(), source.getMaxY()));

			}
		}
	}

	private void unsupportedDy(Point source, int xx, int yy) {
		Placement moveX = projectLeftTop(xx, yy);
		if(moveX == null) {
			
			// supported one way (by container border)
			//
			//       |  
			//       |  
			// yy    |←------------|
			//       |    |        |
			// fmaxY |----|        |
			//
			
			values.add(new DefaultFixedPointX(0, yy, source.getMaxX(), source.getMaxY(), yy, depth));
		} else if(moveX.getAbsoluteEndX() < source.getMinX()) {
			
			if(moveX.getAbsoluteY() <= yy && yy < moveX.getAbsoluteEndY()) {

				// supported one way
				//
				// aendy |-|
				//       | |
				// yy    | |←----------|
				//       | |  |        |
				// fmaxY |----|        |
				//
				//       aendx
				
				values.add(new DefaultFixedPointX(moveX.getAbsoluteEndX(), yy, source.getMaxX(), source.getMaxY(), yy, moveX.getAbsoluteEndY()));
			} else {

				// unsupported both ways
				//
				//
				//       |-|
				//       | |
				// aendy |-|
				//       | 
				// yy    | *←----------|
				//       |    |        |
				// fmaxY |----|        |
				//
				//       aendx

				values.add(new DefaultPoint(moveX.getAbsoluteEndX(), yy, source.getMaxX(), source.getMaxY()));
			}

		} else {

			if(moveX.getAbsoluteY() <= yy && yy < moveX.getAbsoluteEndY()) {

				// supported both ways
				//
				//
				// aendy |-------|
				//       |       |
				//       |       |
				// yy    |    |--*←----|
				//       |    |        |
				// fmaxY |----|        |
				//
				//             aendx
				
				values.add(new DefaultFixedPointXY(moveX.getAbsoluteX(), yy, source.getMaxX(), source.getMaxY(),  moveX.getAbsoluteEndX(), xx, yy, moveX.getAbsoluteEndY()));
			} else {

				// unsupported one way
				//
				//
				//       |-------|
				//       |       |
				// aendy |-------|
				//       |       
				//       |       
				// yy    |    |--*←----|
				//       |    |        |
				// fmaxY |----|        |
				//
				//             aendx

				values.add(new DefaultFixedPointY(moveX.getAbsoluteEndX(), yy, source.getMaxX(), source.getMaxY(), moveX.getAbsoluteEndX(), xx));
			}
		}
	}
	
	private Placement projectLeftTop(int x, int y) {
		
		// included:
		//
		//         |
		// absEndy-|-----|
		//         |     |
		//         |     |
		//         |-----|
		//         |
		//         |        *
		//         |                
		//         |--------------------------
		//               |
		//            absEndX
		//
		//         |
		//         |
		//         |
		// absEndy-|-----|
		//         |     |
		//         |     |  *
		//         |     |          
		//         |-----|--------------------
		//               |
		//            absEndX
		//
		// excluded:
		//
		//         |
		// absEndy-|-----------|
		//         |           |
		//         |           |
		//   absY  |-----------|
		//         |
		//         |        *
		//         |                
		//         |--------------------------
		//         |           |
		//        absX       absEndX
		//
		
		Placement rightmost = null;
		for (Placement placement : placements) {
			if(placement.getAbsoluteEndY() >= y && placement.getAbsoluteEndX() <= x) {
				if(rightmost == null || placement.getAbsoluteEndX() > rightmost.getAbsoluteEndX()) {
					rightmost = placement;
				}
			}
		}
		
		return rightmost;
	}

	private Placement projectDownRight(int x, int y) {

		// included:
		// |
		// |
		// |                    |-----| absEndY
		// |                 *  |     |
		// |                    |     |
		// |                    |     |
		// |--------------------|-----|  absY
		//                      |     |
		//                    absX absEndX
		//
		// |
		// |                  
		// |                 *
		// |                    |-----| absEndY
		// |                    |     |
		// |--------------------|-----|- absY
		//                      |     |
		//                    absX absEndX
		//
		// excluded:
		// |                  
		// |                 *
		// |              |------------| absEndY
		// |              |            |
		// |--------------|------------|  absY
		//                |            |
		//               absX       absEndX
		//
		
		Placement leftmost = null;
		for (Placement placement : placements) {
			if(placement.getAbsoluteY() <= y && placement.getAbsoluteX() >= x) {
				if(leftmost == null || placement.getAbsoluteX() < leftmost.getAbsoluteX()) {
					leftmost = placement;
				}
			}
		}
		
		return leftmost;
	}

	private int projectRight(int x, int y) {
		int closest = width;
		for (Placement placement : placements) {
			if(placement.getAbsoluteX() >= x) {
				if(placement.getAbsoluteY() <= y && y < placement.getAbsoluteEndY()) {
					if(placement.getAbsoluteX() < closest) {
						closest = placement.getAbsoluteX();
					}
				}
			}
		}
		
		return closest;
	}

	private int projectUp(int x, int y) {
		int closest = depth;
		for (Placement placement : placements) {
			if(placement.getAbsoluteY() >= y) {
				if(placement.getAbsoluteX() <= x && x < placement.getAbsoluteEndX()) {
					if(placement.getAbsoluteY() < closest) {
						closest = placement.getAbsoluteY();
					}
				}
			}
		}
		
		return closest;
	}

	public List<Point> getValues() {
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
		return "Points [width=" + width + ", depth=" + depth + ", values=" + values + "]";
	}
	
	public List<Placement> getPlacements() {
		return placements;
	}
	
}
