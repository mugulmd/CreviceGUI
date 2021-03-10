package crevice;

/**
 *
 * @author Loïc Vital
 *
 */

public class InNode extends Node {

	private LineEdge edge;

	public InNode(int _id, AffineElement _elt) {
		super(_id, _elt);
	}

	public void setEdge(LineEdge e) {
		edge = e;
	}

	public LineEdge getEdge() {
		return edge;
	}

}
