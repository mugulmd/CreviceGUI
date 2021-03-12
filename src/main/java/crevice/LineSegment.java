package crevice;

import java.awt.Shape;
import java.awt.geom.Line2D;

/**
 *
 * @author Loïc Vital
 *
 */

public class LineSegment extends AbstractSegment {

	public LineSegment(Vec2D _vStart, Vec2D _vEnd) {
		super(_vStart, _vEnd);
	}

	@Override
	public Vec2D vecAt(double t) {
		double u = Math.max(0, Math.min(1, t));
		return Vec2D.add(vStart, Vec2D.mult(Vec2D.sub(vEnd, vStart), u));
	}

	public Vec2D direction() {
		return Vec2D.normalized(Vec2D.sub(vEnd, vStart));
	}

	@Override
	public Vec2D dirAt(double t) {
		return direction();
	}

	@Override
	public LineSegment applyCam(Camera cam) {
		return new LineSegment(cam.applyTo(vStart), cam.applyTo(vEnd));
	}

	@Override
	public LineSegment applyCamInv(Camera cam) {
		return new LineSegment(cam.applyInvTo(vStart), cam.applyInvTo(vEnd));
	}

	@Override
	public Shape shape() {
		return new Line2D.Double(vStart.getX(), vStart.getY(), vEnd.getX(), vEnd.getY());
	}

	public Vec2D project(Vec2D v) {
		Vec2D vDir = Vec2D.sub(vEnd, vStart);
		double t = Vec2D.dot(vDir, Vec2D.sub(v, vStart))/(vDir.norm()*vDir.norm());
		return vecAt(t);
	} 

}
