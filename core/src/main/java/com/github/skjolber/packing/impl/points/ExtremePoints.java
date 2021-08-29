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
	
	public boolean add(int index, Placement placement) {
		
		Point source = values.get(index);
		
		System.out.println();
		System.out.println("**************************************");
		System.out.println("Add " + source.getMinX() + "x" + source.getMinY() + " " + placement.getBox().getWidth() + "x" + placement.getBox().getDepth());
		
		int xx = source.getMinX() + placement.getBox().getWidth();
		int yy = source.getMinY() + placement.getBox().getDepth();
		
		Point dx = null;
		Point dy = null;
		
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

				dx = new DefaultFixedPointXY(xx, source.getMinY(), width, depth, xx, fixedPointY.getFixedMaxX(), source.getMinY(), yy);
				
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

				dy = new DefaultFixedPointXY(source.getMinX(), yy, width, depth,  source.getMinX(), xx, yy, fixedPointX.getFixedMaxY());
				
				
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
				dx = new DefaultFixedPointXY(xx, source.getMinY(), width, depth, xx, fixedPointY.getFixedMaxX(), source.getMinY(), yy);

				// using dy
				dy = unsupportedDy(source, xx, yy);
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
				dy = new DefaultFixedPointXY(source.getMinX(), yy, width, depth,  source.getMinX(), xx, yy, fixedPointX.getFixedMaxY());

				// using dx
				dx = unsupportedDx(source, xx, yy);
				
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
				
				dx = unsupportedDx(source, xx, yy);
				dy = unsupportedDy(source, xx, yy);
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
				dx = new DefaultFixedPointXY(xx, source.getMinY(), width, depth, xx, fixedPointY.getFixedMaxX(), source.getMinY(), yy);
				
				dy = unsupportedDy(source, xx, yy);
			} else {
				System.out.println("Is not fixed y");

				dx = unsupportedDx(source, xx, yy);
				dy = unsupportedDy(source, xx, yy);
			}
		} else if(source.isFixedX()) {
			System.out.println("Could be fixed x");
			
			FixedPointX fixedPointX = (FixedPointX)source;
			if(yy < fixedPointX.getFixedMaxY()) {
				
				System.out.println("Fixed x, unsupported y");
				//
				// maxY |----|
				//      |    |                  xx
				//      |    |-------------------| yy
				//      |    |                   |
				//      |    |                   | 
				//      |    |                   |
				// minY |    |--------------------
				//      |                   
				//      |                   
				//      |--------------------------
				//                          

				// using dy
				values.add(new DefaultFixedPointXY(source.getMinX(), yy, width, depth,  source.getMinX(), xx, yy, fixedPointX.getFixedMaxY()));

				dx = unsupportedDx(source, xx, yy);
			} else {
				System.out.println("Not fixed x or y");
				dx = unsupportedDx(source, xx, yy);
				dy = unsupportedDy(source, xx, yy);
			}
		} else {
			System.out.println("Not fixed any way");
			
			dx = unsupportedDx(source, xx, yy);
			dy = unsupportedDy(source, xx, yy);
		}

		values.remove(index);
		
		for (int i = 0; i < values.size(); i++) {
			Point point = values.get(i);
			
			if(!project(point, placement)) {
				System.out.println("Remove " + point.getMinX() + "x" + point.getMinY());
				values.remove(i);
				i--;
			}
		}

		if(dy != null) {
			project(dy);
		}
		if(dx != null) {
			project(dx);
		}

		if(dy != null) {
			project(dy);
			values.add(index, dy);
			index++;
		}
		if(dx != null) {
			values.add(index, dx);
		}
		
		placements.add(placement);
		Collections.sort(values);
		System.out.println("Now have " + values.size());
		
		for (int j = 0; j < values.size(); j++) {
			Point point2 = values.get(j);
			
			System.out.println(j + " " + point2.getMinX() + "x" + point2.getMinY() + " " + point2.getClass().getSimpleName());
		}
		
		return !values.isEmpty();
	}

	private boolean project(Point point, Placement placement) {
		int maxX = projectRight(point.getMinX(), point.getMinY(), placement, point.getMaxX());
		int maxY = projectUp(point.getMinX(), point.getMinY(), placement, point.getMaxY());
		
		if(maxX <= point.getMinX() || maxY <= point.getMinY()) {
			return false;
		} else {
			point.setMaxX(maxX);
			point.setMaxY(maxY);
		}

		return true;
	}

	private int projectUp(int minX, int minY, Placement placement, int maxY) {
		// TODO Auto-generated method stub
		return 0;
	}

	private int projectRight(int minX, int minY, Placement placement, int maxX) {
		// TODO Auto-generated method stub
		return 0;
	}

	private boolean project(Point point) {
		int maxX = projectRight(point.getMinX(), point.getMinY());
		int maxY = projectUp(point.getMinX(), point.getMinY() );
		
		if(maxX <= point.getMinX() || maxY <= point.getMinY()) {
			return false;
		} else {
			point.setMaxX(maxX);
			point.setMaxY(maxY);
		}

		return true;
	}

	private Point unsupportedDx(Point source, int xx, int yy) {
		System.out.println("Add unsupported dx");
		Placement moveY = projectDownRight(xx, yy);
		if(moveY == null) {
			
			System.out.println(" all the way");
			
			// supported one way (by container border)
			//
			//      |    |-------------------|
			//      |    |                   |
			//      |    |                   |
			//      |    |                   |
			// minY |    |--------------------
			//      |    |               |   |
			//      |    |               |   ↓
			//      |----|---------------|---*-----
			//          minX            maxX
			
			return new DefaultFixedPointY(xx, 0, width, depth, xx, width);
			
		} else if(moveY.getAbsoluteEndY() < source.getMinY()) {

			System.out.println(" directly below");

			if(moveY.getAbsoluteX() <= xx && xx < moveY.getAbsoluteEndX() ) {

				// supported one way
				//
				//      |    |-------------------|
				//      |    |                   |
				//      |    |                   |
				//      |    |                   |
				// minY |    |--------------------
				//      |    |               |   ↓
				//      |    |               |   *--------|
				//      |    |               |   |        |
				//      |----|---------------|---|--------|--------
				//          minX            maxX
	
				System.out.println(" directly below one way");

				return new DefaultFixedPointY(xx, moveY.getAbsoluteEndY(), width, depth, xx, moveY.getAbsoluteEndX());

			} else {

				System.out.println(" below no ways");

				// unsupported both ways
				//
				//      |    |-------------------|
				//      |    |                   |
				//      |    |                   |
				//      |    |                   |
				// minY |    |--------------------
				//      |    |               |   ↓
				//      |    |               |   *    |--------|
				//      |    |               |        |        |
				//      |----|---------------|--------|--------|--------
				//          minX            maxX

				return new DefaultPoint(xx, moveY.getAbsoluteEndY(), width, depth);
			}
			
		} else {

			if(moveY.getAbsoluteX() <= xx && xx < moveY.getAbsoluteEndX() ) {
				
				System.out.println("Supported both ways");
				// supported both ways
				// 
				//      |    |-------------------|
				//      |    |                   |
				//      |    |                   |
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
	
				return new DefaultFixedPointXY(xx, moveY.getAbsoluteEndY(), width, depth, xx, moveY.getAbsoluteEndX(), moveY.getAbsoluteEndY(), yy);
			} else {

				System.out.println("Supported one way");
				// supported one way
				//
				//      |    |-------------------|
				//      |    |                   |
				//      |    |                   |
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

				return new DefaultFixedPointX(xx, moveY.getAbsoluteEndY(), width, depth, moveY.getAbsoluteEndY(), yy);
			}
		}
	}

	private Point unsupportedDy(Point source, int xx, int yy) {
		System.out.println("Add unsupported dy");
		
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
			
			return new DefaultFixedPointX(0, yy, width, depth, yy, depth);
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
				
				return new DefaultFixedPointX(moveX.getAbsoluteEndX(), yy, width, depth, yy, moveX.getAbsoluteEndY());
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

				return new DefaultPoint(moveX.getAbsoluteEndX(), yy, width, depth);
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
				
				return new DefaultFixedPointXY(moveX.getAbsoluteX(), yy, width, depth,  moveX.getAbsoluteEndX(), xx, yy, moveX.getAbsoluteEndY());
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

				return new DefaultFixedPointY(moveX.getAbsoluteEndX(), yy, width, depth, moveX.getAbsoluteEndX(), xx);
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
			if(placement.getAbsoluteEndY() >= y && placement.getAbsoluteX() < x) {
				
				// most to the right
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
			if(placement.getAbsoluteY() <= y && placement.getAbsoluteEndX() > x) {
				
				// the highest
				if(leftmost == null || placement.getAbsoluteEndY() > leftmost.getAbsoluteEndY()) {
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
