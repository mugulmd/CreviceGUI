package crevice;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

/**
 *
 * @author Loïc Vital
 *
 */

public class OutNode extends Node {

	private List<ParabolaEdge> edges;

	public OutNode(int _id, AffineElement _elt) {
		super(_id, _elt);
		edges = new LinkedList<ParabolaEdge>();
	}

	public void addEdge(ParabolaEdge e) {
		edges.add(e);
	}

	public Iterator<ParabolaEdge> edgeIterator() {
		return edges.iterator();
	}

}
