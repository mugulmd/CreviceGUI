package crevice;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author Loïc Vital
 *
 */

public class BFSExplorer extends Explorer {

	@Override
	public String toString() {
		return "BFS";
	}

	@Override
	public void explore() {
		skeleton.clearChildren();

		Queue<ParabolaEdge> queue = new LinkedList<ParabolaEdge>();
		Set<Integer> seen = new HashSet<Integer>();

		queue.add(skeleton.getRootEdge());
		seen.add(skeleton.getRootEdge().getId());
		skeleton.setDepth(skeleton.getRootEdge(), 0);

		while(!queue.isEmpty()) {
			ParabolaEdge e1 = queue.poll();

			InNode n1 = e1.getTarget();
			LineEdge e2 = n1.getEdge();
			if(seen.contains(e2.getId())) continue;
			seen.add(e2.getId());
			skeleton.setDepth(e2, skeleton.getDepth(e1)+1);
			skeleton.addChild(e1, e2);

			OutNode n2 = e2.getTarget();
			Iterator<ParabolaEdge> edgeIt = n2.edgeIterator();
			while(edgeIt.hasNext()) {
				ParabolaEdge e3 = edgeIt.next();
				if(seen.contains(e3.getId()) || !net.filter(e3)) continue;
				queue.add(e3);
				seen.add(e3.getId());
				skeleton.setDepth(e3, skeleton.getDepth(e2)+1);
				skeleton.addChild(e2, e3);
			}
		}

		skeleton.setComplete();
	}

}
