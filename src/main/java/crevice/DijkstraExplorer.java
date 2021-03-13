package crevice;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Comparator;

/**
 *
 * @author Loïc Vital
 *
 */

public class DijkstraExplorer extends Explorer {

	private Map<Edge, Double> dist;

	private class CompareEdgesByDist implements Comparator<Edge> {
		@Override
		public int compare(Edge e1, Edge e2) {
			return Double.compare(dist.get(e1), dist.get(e2));
		}
	}

	@Override
	public String toString() {
		return "Dijkstra";
	}

	@Override
	public void explore() {
		skeleton.clearChildren();

		dist = new HashMap<Edge, Double>();
		Map<Edge, Integer> status = new HashMap<Edge, Integer>();
		Comparator<Edge> comparator = new CompareEdgesByDist();
		PriorityQueue<ParabolaEdge> qP = new PriorityQueue<ParabolaEdge>(100, comparator);
		PriorityQueue<LineEdge> qL = new PriorityQueue<LineEdge>(100, comparator);
		Map<Edge, Edge> parent = new HashMap<Edge, Edge>();

		Iterator<Edge> it = net.edgeIterator();
		while(it.hasNext()) {
			Edge e = it.next();
			status.put(e, -1);
		}
		dist.put(skeleton.getRootEdge(), skeleton.getRootEdge().getSegment().length());
		qP.add(skeleton.getRootEdge());
		status.replace(skeleton.getRootEdge(), 0);
		skeleton.setDepth(skeleton.getRootEdge(), 0);

		while(!qP.isEmpty() || !qL.isEmpty()) {
			if(qP.isEmpty() || (!qL.isEmpty() && comparator.compare(qL.peek(), qP.peek()) < 0)) {
				LineEdge e1 = qL.poll();
				status.replace(e1, 1);
				OutNode n1 = e1.getTarget();
				Iterator<ParabolaEdge> edgeIt = n1.edgeIterator();
				while(edgeIt.hasNext()) {
					ParabolaEdge e2 = edgeIt.next();
					if(!net.filter(e2)) continue;
					if(status.get(e2) < 0) {
						dist.put(e2, dist.get(e1)+e2.getSegment().length());
						qP.add(e2);
						status.replace(e2, 0);
						parent.put(e2, e1);
						skeleton.setDepth(e2, skeleton.getDepth(e1)+1);
					} else if(status.get(e2) == 0) {
						if(dist.get(e2) > dist.get(e1)+e2.getSegment().length()) {
							qP.remove(e2);
							dist.replace(e2, dist.get(e1)+e2.getSegment().length());
							qP.add(e2);
							parent.replace(e2, e1);
							skeleton.setDepth(e2, skeleton.getDepth(e1)+1);
						}
					}
				}
			} else {
				ParabolaEdge e1 = qP.poll();
				status.replace(e1, 1);
				InNode n1 = e1.getTarget();
				LineEdge e2 = n1.getEdge();
				if(net.filter(e2)) {
					if(status.get(e2) < 0) {
						dist.put(e2, dist.get(e1)+e2.getSegment().length());
						qL.add(e2);
						status.replace(e2, 0);
						parent.put(e2, e1);
						skeleton.setDepth(e2, skeleton.getDepth(e1)+1);
					} else if(status.get(e2) == 0) {
						if(dist.get(e2) > dist.get(e1)+e2.getSegment().length()) {
							qL.remove(e2);
							dist.replace(e2, dist.get(e1)+e2.getSegment().length());
							qL.add(e2);
							parent.replace(e2, e1);
							skeleton.setDepth(e2, skeleton.getDepth(e1)+1);
						}
					}
				}
			}
		}

		for(Edge e : parent.keySet()) {
			skeleton.addChild(parent.get(e), e);
		}

		skeleton.setComplete();
	}

}
