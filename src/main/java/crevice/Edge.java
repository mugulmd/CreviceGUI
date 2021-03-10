package crevice;

/**
 *
 * @author Loïc Vital
 *
 */

public abstract class Edge<T_SOURCE extends Node, T_TARGET extends Node> {

	private int id;
	private T_SOURCE source;
	private T_TARGET target;
	private AbstractSegment segment;
	private double affineWeight;
	private double supportAngle;

	public Edge(int _id, T_SOURCE _source, T_TARGET _target) {
		id = _id;
		source = _source;
		target = _target;
	}

	public int getId() {
		return id;
	}

	public T_SOURCE getSource() {
		return source;
	}

	public T_TARGET getTarget() {
		return target;
	}

	public AbstractSegment getSegment() {
		return segment;
	}

	public double getAffineWeight() {
		return affineWeight;
	}

	public double getSupportAngle() {
		return supportAngle;
	}

	protected void setGeoLine() {
		segment = new LineSegment(source.getGeo().getPos(), target.getGeo().getPos());
		affineWeight = 0;
		supportAngle = Math.PI;
	}

	protected void setGeoParabola(Vec2D vMid) {
		ParabolaSegment parabola = new ParabolaSegment(source.getGeo().getPos(), vMid, target.getGeo().getPos());
		segment = parabola;
		affineWeight = Math.pow(parabola.supportTriangleArea(), 1./3.);
		supportAngle = parabola.supportAngle();
	}

}
