package crevice;

/**
 *
 * @author Loïc Vital
 *
 */

public class LineEdge extends Edge<InNode, OutNode> {

	public LineEdge(int _id, InNode _source, OutNode _target) {
		super(_id, _source, _target);
		setGeoLine();
	}

}
