package crevice;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Collections;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author Loïc Vital
 *
 */

public class Skeleton implements ProjectStage {

	private OutNode rootNode;
	private Iterator<ParabolaEdge> rootEdgeIt;
	private ParabolaEdge rootEdge;
	private boolean connected;
	private Map<Integer, List<Edge>> children;
	private Map<Integer, Integer> depth;
	private boolean complete;
	private int maxDepth;

	public Skeleton() {
		connected = false;
		children = new HashMap<Integer, List<Edge>>();
		depth = new HashMap<Integer, Integer>();
		complete = false;
	}

	@Override
	public ModelingMode getModelingMode() {
		return ModelingMode.SKELETON;
	}

	@Override
	public void clear() {
		connected = false;
		clearChildren();
		maxDepth = Integer.MAX_VALUE;
	}

	public void clearChildren() {
		if(complete) {
			children = new HashMap<Integer, List<Edge>>();
			depth = new HashMap<Integer, Integer>();
			complete = false;
		}
	}

	public void connect(Network net, AffineElement elt) {
		clear();

		rootNode = new OutNode(-1, elt);

		Iterator<InNode> inNodeIt = net.inNodeIterator();
		while(inNodeIt.hasNext()) {
			InNode node = inNodeIt.next();
			Vec2D p = AffineElement.intersection(elt, node.getGeo());
			if(p != null) {
				ParabolaEdge e = new ParabolaEdge(-1, rootNode, node, p);
				rootNode.addEdge(e);
			}
		}

		rootEdgeIt = rootNode.edgeIterator();
		if(rootEdgeIt.hasNext()) {
			rootEdge = rootEdgeIt.next();
			connected = true;
		}
	}

	public boolean isConnected() {
		return connected;
	}

	public OutNode getRootNode() {
		return rootNode;
	}

	public ParabolaEdge getRootEdge() {
		return rootEdge;
	}

	public void changeRootEdge() {
		clearChildren();

		if(!rootEdgeIt.hasNext()) rootEdgeIt = rootNode.edgeIterator();
		rootEdge = rootEdgeIt.next();
	}

	public void addChild(Edge parent, Edge child) {
		if(!children.containsKey(parent.getId())) {
			children.put(parent.getId(), new LinkedList<Edge>());
		}
		children.get(parent.getId()).add(child);
	}

	public Iterator<Edge> childrenIterator(Edge e) {
		List<Edge> lst = children.get(e.getId());
		if(lst != null) {
			return lst.iterator();
		} else {
			return Collections.emptyIterator();
		}
	}

	public void setDepth(Edge e, int d) {
		depth.put(e.getId(), d);
	}

	public int getDepth(Edge e) {
		return depth.get(e.getId());
	}

	public void setComplete() {
		complete = true;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setMaxDepth(int d) {
		maxDepth = d;
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	public boolean filter(Edge e) {
		return (depth.get(e.getId()) <= maxDepth);
	}

}
