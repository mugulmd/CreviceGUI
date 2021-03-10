package crevice;

/**
 *
 * @author Loïc Vital
 *
 */

public abstract class Node {

	private int id;
	private AffineElement elt;

	public Node(int _id, AffineElement _elt) {
		id = _id;
		elt = _elt;
	}

	public int getId() {
		return id;
	}

	public AffineElement getGeo() {
		return elt;
	}

}
