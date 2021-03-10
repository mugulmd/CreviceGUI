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

	private void clearChildren() {
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

	public Edge getRootEdge() {
		return rootEdge;
	}

	public void changeRootEdge() {
		clearChildren();

		if(!rootEdgeIt.hasNext()) rootEdgeIt = rootNode.edgeIterator();
		rootEdge = rootEdgeIt.next();
	}

	public Iterator<Edge> childrenIterator(Edge e) {
		List<Edge> lst = children.get(e.getId());
		if(lst != null) {
			return lst.iterator();
		} else {
			return Collections.emptyIterator();
		}
	}

	public void explore(Network net) {
		clearChildren();

		Queue<ParabolaEdge> queue = new LinkedList<ParabolaEdge>();
		Set<Integer> seen = new HashSet<Integer>();

		queue.add(rootEdge);
		seen.add(rootEdge.getId());
		depth.put(rootEdge.getId(), 0);

		while(!queue.isEmpty()) {
			ParabolaEdge e1 = queue.poll();

			InNode n1 = e1.getTarget();
			LineEdge e2 = n1.getEdge();
			if(seen.contains(e2.getId())) continue;
			seen.add(e2.getId());
			depth.put(e2.getId(), depth.get(e1.getId())+1);

			LinkedList<Edge> lst1 = new LinkedList<Edge>();
			lst1.add(e2);
			children.put(e1.getId(), lst1);

			LinkedList<Edge> lst2 = new LinkedList<Edge>();
			OutNode n2 = e2.getTarget();
			Iterator<ParabolaEdge> edgeIt = n2.edgeIterator();
			while(edgeIt.hasNext()) {
				ParabolaEdge e3 = edgeIt.next();
				if(seen.contains(e3.getId()) || !net.filter(e3)) continue;
				queue.add(e3);
				seen.add(e3.getId());
				depth.put(e3.getId(), depth.get(e2.getId())+1);
				lst2.add(e3);
			}
			children.put(e2.getId(), lst2);
		}

		complete = true;
	}

	public boolean isComplete() {
		return complete;
	}

	public int getDepth(Edge e) {
		return depth.get(e.getId());
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
