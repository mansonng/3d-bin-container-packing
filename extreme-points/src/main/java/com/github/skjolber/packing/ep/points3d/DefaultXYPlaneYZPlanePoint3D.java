package com.github.skjolber.packing.ep.points3d;

import com.github.skjolber.packing.api.StackPlacement;
import com.github.skjolber.packing.api.ep.Point3D;

public class DefaultXYPlaneYZPlanePoint3D extends SimplePoint3D implements XYPlanePoint3D, YZPlanePoint3D {

	private static final long serialVersionUID = 1L;

	/** range constrained to current minX */
	private final StackPlacement yzPlane;

	/** range constrained to current minZ */
	private final StackPlacement xyPlane;
	
	public DefaultXYPlaneYZPlanePoint3D(int minX, int minY, int minZ, int maxX, int maxY, int maxZ,
			StackPlacement yzPlane,
			StackPlacement xyPlane) {
		super(minX, minY, minZ, maxX, maxY, maxZ);

		this.yzPlane = yzPlane;
		this.xyPlane = xyPlane;
	}

	public int getSupportedYZPlaneMinY() {
		return yzPlane.getAbsoluteY();
	}

	public int getSupportedYZPlaneMaxY() {
		return yzPlane.getAbsoluteEndY();
	}

	@Override
	public int getSupportedYZPlaneMinZ() {
		return yzPlane.getAbsoluteZ();
	}

	@Override
	public int getSupportedYZPlaneMaxZ() {
		return yzPlane.getAbsoluteEndZ();
	}

	@Override
	public boolean isSupportedYZPlane(int y, int z) {
		return yzPlane.getAbsoluteY() <= y && y <= yzPlane.getAbsoluteEndY() && yzPlane.getAbsoluteZ() <= z && z <= yzPlane.getAbsoluteEndZ();
	}

	public boolean isYZPlaneEdgeZ(int z) {
		return yzPlane.getAbsoluteEndZ() == z - 1;
	}

	public boolean isYZPlaneEdgeY(int y) {
		return yzPlane.getAbsoluteEndY() == y - 1;
	}

	public StackPlacement getYZPlane() {
		return yzPlane;
	}

	public int getSupportedXYPlaneMinY() {
		return xyPlane.getAbsoluteY();
	}

	public int getSupportedXYPlaneMaxY() {
		return xyPlane.getAbsoluteEndY();
	}

	@Override
	public int getSupportedXYPlaneMinX() {
		return xyPlane.getAbsoluteX();
	}

	@Override
	public int getSupportedXYPlaneMaxX() {
		return xyPlane.getAbsoluteEndX();
	}

	@Override
	public boolean isSupportedXYPlane(int x, int y) {
		return xyPlane.getAbsoluteX() <= x && x <= xyPlane.getAbsoluteEndX() && xyPlane.getAbsoluteY() <= y && y <= xyPlane.getAbsoluteEndY();
	}

	public boolean isXYPlaneEdgeX(int x) {
		return xyPlane.getAbsoluteEndX() == x - 1;
	}

	public boolean isXYPlaneEdgeY(int y) {
		return xyPlane.getAbsoluteEndY() == y - 1;
	}

	public StackPlacement getXYPlane() {
		return xyPlane;
	}

	@Override
	public DefaultXYPlaneYZPlanePoint3D clone(int maxX, int maxY, int maxZ) {
		return new DefaultXYPlaneYZPlanePoint3D(
				minX, minY, minZ,
				maxX, maxY, maxZ,
				yzPlane, xyPlane);
	}

	@Override
	public SimplePoint3D moveY(int y) {
		boolean withinXYPlane = y <= xyPlane.getAbsoluteEndY();
		boolean withinYZPlane = y <= yzPlane.getAbsoluteEndY();

		if(withinXYPlane && withinYZPlane) {
			return new DefaultXYPlaneYZPlanePoint3D(minX, y, minZ, maxX, maxY, maxZ, yzPlane, xyPlane);
		} else if(withinXYPlane) {
			return new DefaultXYPlanePoint3D(minX, y, minZ, maxX, maxY, maxZ, xyPlane);
		} else if(withinYZPlane) {
			return new DefaultYZPlanePoint3D(minX, y, minZ, maxX, maxY, maxZ, yzPlane);
		}

		return new DefaultPoint3D(minX, y, minZ, maxX, maxY, maxZ);
	}

	@Override
	public SimplePoint3D moveY(int y, StackPlacement xzSupport) {
		boolean withinXYPlane = y <= xyPlane.getAbsoluteEndY();
		boolean withinYZPlane = y <= yzPlane.getAbsoluteEndY();

		if(withinXYPlane && withinYZPlane) {
			return new Default3DPlanePoint3D(minX, y, minZ, maxX, maxY, maxZ, yzPlane, xzSupport, xyPlane);
		} else if(withinXYPlane) {
			return new DefaultXYPlaneXZPlanePoint3D(minX, y, minZ, maxX, maxY, maxZ, xzSupport, xyPlane);
		} else if(withinYZPlane) {
			return new DefaultXZPlaneYZPlanePoint3D(minX, y, minZ, maxX, maxY, maxZ, xzSupport, yzPlane);
		}

		return new DefaultXZPlanePoint3D(minX, y, minZ, maxX, maxY, maxZ, xzSupport);
	}

	@Override
	public SimplePoint3D moveX(int x) {
		// yz plane support is lost
		if(x <= xyPlane.getAbsoluteEndX()) {
			return new DefaultXYPlanePoint3D(x, minY, minZ, maxX, maxY, maxZ, xyPlane);
		}
		// all previous support is lost
		return new DefaultPoint3D(x, minY, minZ, maxX, maxY, maxZ);
	}

	@Override
	public SimplePoint3D moveX(int x, StackPlacement yzSupport) {
		if(x <= xyPlane.getAbsoluteEndX()) {
			return new DefaultXYPlaneYZPlanePoint3D(x, minY, minZ, maxX, maxY, maxZ, yzSupport, xyPlane);
		}
		// all previous support is lost
		return new DefaultYZPlanePoint3D(x, minY, minZ, maxX, maxY, maxZ, yzSupport);
	}

	@Override
	public SimplePoint3D moveZ(int z) {
		// xy plane support is lost
		if(z <= yzPlane.getAbsoluteEndZ()) {
			return new DefaultYZPlanePoint3D(minX, minY, z, maxX, maxY, maxZ, yzPlane);
		}
		// all previous support is lost
		return new DefaultPoint3D(minX, minY, z, maxX, maxY, maxZ);
	}

	@Override
	public SimplePoint3D moveZ(int z, StackPlacement xySupport) {
		// xy plane support is lost
		if(z <= yzPlane.getAbsoluteEndZ()) {
			return new DefaultXYPlaneYZPlanePoint3D(minX, minY, z, maxX, maxY, maxZ, yzPlane, xySupport);
		}
		// all previous support is lost
		return new DefaultXYPlanePoint3D(minX, minY, z, maxX, maxY, maxZ, xySupport);
	}

	/**
	 * Rotate box, i.e. in 3D
	 *
	 * @return this instance
	 */

	@Override
	public Point3D rotate() {
		return new DefaultPoint3D(minY, minZ, minX, maxY, maxZ, maxX);
	}

	@Override
	public long calculateXYSupport(int dx, int dy) {
		return Math.min(dx, xyPlane.getAbsoluteEndX() - minX + 1) * (long)Math.min(dy, xyPlane.getAbsoluteEndY() - minY + 1);
	}

	@Override
	public long calculateXZSupport(int dx, int dz) {
		return 0;
	}

	@Override
	public long calculateYZSupport(int dy, int dz) {
		return (long)Math.min(dy, yzPlane.getAbsoluteEndY() - minY + 1) * Math.min(dz, yzPlane.getAbsoluteEndZ() - minZ + 1);
	}
	
	public boolean isSupportedYZPlane() {
		return true;
	}

	public boolean isSupportedXYPlane() { // i.e. z is fixed
		return true;
	}

}
