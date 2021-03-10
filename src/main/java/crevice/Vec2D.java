package crevice;

/**
 *
 * @author Loïc Vital
 *
 */

public class Vec2D {

	private double x, y;

	public Vec2D(double _x, double _y) {
		x = _x;
		y = _y;
	}

	public Vec2D(Vec2D v) {
		this(v.getX(), v.getY());
	}

	public Vec2D() {
		this(0, 0);
	}

	public void setX(double _x) {
		x = _x;
	}

	public void setY(double _y) {
		y = _y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void add(Vec2D v) {
		x += v.getX();
		y += v.getY();
	}

	public static Vec2D add(Vec2D u, Vec2D v) {
		return new Vec2D(u.getX()+v.getX(), u.getY()+v.getY());
	}

	public void sub(Vec2D v) {
		x -= v.getX();
		y -= v.getY();
	}

	public static Vec2D sub(Vec2D u, Vec2D v) {
		return new Vec2D(u.getX()-v.getX(), u.getY()-v.getY());
	}

	public void mult(double lambda) {
		x *= lambda;
		y *= lambda;
	}

	public static Vec2D mult(Vec2D u, double lambda) {
		return new Vec2D(u.getX()*lambda, u.getY()*lambda);
	}

	public void div(double lambda) {
		x /= lambda;
		y /= lambda;
	}

	public static Vec2D div(Vec2D u, double lambda) {
		return new Vec2D(u.getX()/lambda, u.getY()/lambda);
	}

	public double norm() {
		return Math.sqrt(x*x+y*y);
	}

	public void normalize() {
		div(norm());
	}

	public static Vec2D normalized(Vec2D u) {
		return Vec2D.div(u, u.norm());
	}

	public static double sqDist(Vec2D u, Vec2D v) {
		return (u.getX()-v.getX())*(u.getX()-v.getX()) + (u.getY()-v.getY())*(u.getY()-v.getY());
	}

	public static double dist(Vec2D u, Vec2D v) {
		return Math.sqrt(Vec2D.sqDist(u, v));
	}

	public static double dot(Vec2D u, Vec2D v) {
		return u.getX()*v.getX() + u.getY()*v.getY();
	}

}
