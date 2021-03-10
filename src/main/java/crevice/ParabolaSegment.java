package crevice;

import java.awt.Shape;
import java.awt.geom.QuadCurve2D;

/**
 *
 * @author Loïc Vital
 *
 */

public class ParabolaSegment extends AbstractSegment {

	private Vec2D vMid;

	public ParabolaSegment(Vec2D _vStart, Vec2D _vMid, Vec2D _vEnd) {
		super(_vStart, _vEnd);
		vMid = _vMid;
	}

	@Override
	public Vec2D vecAt(double t) {
		double u = Math.max(0, Math.min(1, t));
		Vec2D v = Vec2D.mult(vStart, (1-u)*(1-u));
		v.add(Vec2D.mult(vMid, 2*u*(1-u)));
		v.add(Vec2D.mult(vEnd, u*u));
		return v;
	}

	@Override
	public Vec2D dirAt(double t) {
		double u = Math.max(0, Math.min(1, t));
		Vec2D d = Vec2D.mult(Vec2D.sub(vMid, vStart), 1-u);
		d.add(Vec2D.mult(Vec2D.sub(vEnd, vMid), u));
		d.mult(2);
		return d;
	}

	@Override
	public Shape shape() {
		return new QuadCurve2D.Double(vStart.getX(), vStart.getY(), vMid.getX(), vMid.getY(), vEnd.getX(), vEnd.getY());
	}

	@Override
	public Shape shape(Camera cam) {
		Vec2D u = cam.applyTo(vStart);
		Vec2D v = cam.applyTo(vMid);
		Vec2D w = cam.applyTo(vEnd);
		return new QuadCurve2D.Double(u.getX(), u.getY(), v.getX(), v.getY(), w.getX(), w.getY());
	}

	public double supportTriangleArea() {
		double a = Vec2D.dist(vStart, vMid);
		double b = Vec2D.dist(vMid, vEnd);
		double c = Vec2D.dist(vEnd, vStart);
		double s = (a+b+c)/2;
		return Math.sqrt(s*(s-a)*(s-b)*(s-c));
	}

	public double supportAngle() {
		Vec2D v1 = Vec2D.sub(vStart, vMid); v1.normalize();
		Vec2D v2 = Vec2D.sub(vEnd, vMid); v2.normalize();
		return Math.acos(Vec2D.dot(v1, v2));
	}

}
