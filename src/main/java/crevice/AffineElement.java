package crevice;

/**
 *
 * @author Loïc Vital
 *
 */

public class AffineElement {

	private Vec2D pos, dir;
	private double angle;

	public AffineElement(Vec2D _pos, double _angle) {
		pos = _pos;
		angle= _angle;
		dir = new Vec2D(Math.cos(angle), Math.sin(angle));
	}

	public AffineElement(Vec2D _pos, Vec2D _dir) {
		pos = _pos;
		dir = _dir; dir.normalize();
		if(Math.abs(dir.getX()+1) < 1e-5) {
			angle = (dir.getY() > 0) ? Math.PI : (-1)*Math.PI;
		} else {
			angle = 2*Math.atan(dir.getY()/(dir.getX()+1));
		}
	}

	public Vec2D getPos() {
		return pos;
	}

	public double getAngle() {
		return angle;
	}

	public Vec2D getDir() {
		return dir;
	}

	public static Vec2D intersection(AffineElement fst, AffineElement snd) {
		double det = fst.dir.getX()*snd.dir.getY() - fst.dir.getY()*snd.dir.getX();
		if(Math.abs(det) < 1e-3) return null;

		Vec2D delta = Vec2D.sub(snd.pos, fst.pos);
		double lambda1 = (snd.dir.getY()*delta.getX() - snd.dir.getX()*delta.getY()) / det;
		double lambda2 = (fst.dir.getY()*delta.getX() - fst.dir.getX()*delta.getY()) / det;
		if(lambda1 <= 0 || lambda2 >= 0) return null;

		return Vec2D.add(fst.pos, Vec2D.mult(fst.dir, lambda1));
	}

}
