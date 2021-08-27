package com.github.skjolber.packing.impl;

import org.junit.jupiter.api.Test;

public class ShelfPointsTest {

	@Test
	public void test() {
		
		ShelfPoints extremePoints = new ShelfPoints(1000, 1000);
		
		ShelfPoint extremePoint = extremePoints.getValues().get(0);
		extremePoints.add(extremePoint, 100, 100);

		System.out.println("One: " + extremePoints);

		ShelfPoint extremePoint2 = extremePoints.getValues().get(1);
		extremePoints.add(extremePoint2, 50, 50);
		
		System.out.println("Two: " + extremePoints);
		
		ShelfPoint extremePoint3 = extremePoints.getValues().get(2);
		extremePoints.add(extremePoint3, 50, 75);

		System.out.println("Three: " + extremePoints);

		ShelfPoint extremePoint4 = extremePoints.getValues().get(1);
		extremePoints.add(extremePoint4, 150, 50);

		System.out.println("Four: " + extremePoints);
	}
}
