package crevice;

/**
 *
 * @author Loïc Vital
 *
 */

public class Camera {

	private double zoom;
	private Vec2D origin;

	public Camera() {
		zoom = 1;
		origin = new Vec2D(0, 0);
	}

	public void setZoom(double z) {
		zoom = z;
	}

	public double getZoom() {
		return zoom;
	}

	public void setOrigin(double x, double y) {
		origin.setX(x);
		origin.setY(y);
	}

	public Vec2D getOrigin() {
		return origin;
	}

	public Vec2D applyTo(Vec2D v) {
		return Vec2D.add(Vec2D.mult(v, zoom), origin);
	}

	public Vec2D applyInvTo(Vec2D v) {
		return Vec2D.div(Vec2D.sub(v, origin), zoom);
	}

}
