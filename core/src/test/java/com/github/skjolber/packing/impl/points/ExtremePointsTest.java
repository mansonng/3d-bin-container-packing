package com.github.skjolber.packing.impl.points;

import org.junit.jupiter.api.Test;

import com.github.skjolber.packing.impl.ExtremePoint;
import com.github.skjolber.packing.impl.ExtremePoints;

public class ExtremePointsTest {

	@Test
	public void test() {
		
		ExtremePoints extremePoints = new ExtremePoints(1000, 1000);
		
		ExtremePoint extremePoint = extremePoints.getValues().get(0);
		extremePoints.add(extremePoint, 50, 100);

		System.out.println("One: " + extremePoints);
		
		ExtremePoint extremePoint2 = extremePoints.getValues().get(extremePoints.getValues().size() - 1);
		extremePoints.add(extremePoint2, 50, 75);
		
		System.out.println("Two: " + extremePoints);
		
		ExtremePoint extremePoint3 = extremePoints.getValues().get(extremePoints.getValues().size() - 1);
		extremePoints.add(extremePoint3, 50, 50);

		System.out.println("Three: " + extremePoints);

		ExtremePoint extremePoint4 = extremePoints.getValues().get(extremePoints.getValues().size() - 1);
		extremePoints.add(extremePoint4, 50, 25);

		System.out.println("Four: " + extremePoints);

		DrawOnImage.show(extremePoints);

	}
}
