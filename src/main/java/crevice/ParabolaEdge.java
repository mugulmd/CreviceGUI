package crevice;

/**
 *
 * @author Loïc Vital
 *
 */

public class ParabolaEdge extends Edge<OutNode, InNode> {

	public ParabolaEdge(int _id, OutNode _source, InNode _target, Vec2D vMid) {
		super(_id, _source, _target);
		setGeoParabola(vMid);
	}

}
