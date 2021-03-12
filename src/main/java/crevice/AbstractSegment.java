package crevice;

import java.awt.Shape;

/**
 *
 * @author Loïc Vital
 *
 */

public abstract class AbstractSegment {

	protected Vec2D vStart, vEnd;

	public AbstractSegment(Vec2D _vStart, Vec2D _vEnd) {
		vStart = _vStart;
		vEnd = _vEnd;
	}

	public Vec2D getStart() {
		return vStart;
	}

	public Vec2D getEnd() {
		return vEnd;
	}

	abstract public Vec2D vecAt(double t);

	abstract public Vec2D dirAt(double t);

	abstract public AbstractSegment applyCam(Camera cam);

	abstract public AbstractSegment applyCamInv(Camera cam);

	abstract public Shape shape();

}
