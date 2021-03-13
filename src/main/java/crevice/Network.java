package crevice;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

/**
 *
 * @author Loïc Vital
 *
 */

public class Network implements ProjectStage {

	private List<InNode> inNodes;
	private List<OutNode> outNodes;
	private int nodeCounter;

	private List<Edge> edges;
	private int edgeCounter;

	private double samplingOffset;
	private double maxLength;
	private double maxAffineWeight;
	private double minSupportAngle;
 
	public Network() {
		inNodes = new LinkedList<InNode>();
		outNodes = new LinkedList<OutNode>();
		nodeCounter = 0;
		edges = new LinkedList<Edge>();
		edgeCounter = 0;

		samplingOffset = 0.1;
		maxLength = 1000;
		maxAffineWeight = 100;
		minSupportAngle = 0;
	}

	@Override
	public ModelingMode getModelingMode() {
		return ModelingMode.NETWORK;
	}

	@Override
	public void clear() {
		inNodes = new LinkedList<InNode>();
		outNodes = new LinkedList<OutNode>();
		nodeCounter = 0;
		edges = new LinkedList<Edge>();
		edgeCounter = 0;
	}

	public void setSamplingOffset(double offset) {
		samplingOffset = offset;
	}

	public double getSamplingOffset() {
		return samplingOffset;
	}

	public void build(LineSet lineSet) {
		clear();

		Iterator<LineSegment> it = lineSet.lineIterator();
		while(it.hasNext()) {
			LineSegment line = it.next();

			Vec2D v1 = line.vecAt(samplingOffset);
			Vec2D v2 = line.vecAt(1-samplingOffset);
			Vec2D d1 = line.direction();
			Vec2D d2 = Vec2D.mult(d1, -1);

			InNode n1 = addInNode(new AffineElement(v1, d1));
			OutNode n2 = addOutNode(new AffineElement(v2, d1));
			InNode n3 = addInNode(new AffineElement(v2, d2));
			OutNode n4 = addOutNode(new AffineElement(v1, d2));

			addLineEdge(n1, n2);
			addLineEdge(n3, n4);
		}

		for(OutNode s : outNodes) {
			for(InNode t : inNodes) {
				Vec2D p = AffineElement.intersection(s.getGeo(), t.getGeo());
				if(p != null) {
					addParabolaEdge(s, t, p);
				}
			}
		}
	}

	private InNode addInNode(AffineElement elt) {
		InNode n = new InNode(nodeCounter, elt);
		nodeCounter++;
		inNodes.add(n);
		return n;
	}

	private OutNode addOutNode(AffineElement elt) {
		OutNode n = new OutNode(nodeCounter, elt);
		nodeCounter++;
		outNodes.add(n);
		return n;
	}

	private LineEdge addLineEdge(InNode source, OutNode target) {
		LineEdge e = new LineEdge(edgeCounter, source, target);
		edgeCounter++;
		edges.add(e);
		source.setEdge(e);
		return e;
	}

	private ParabolaEdge addParabolaEdge(OutNode source, InNode target, Vec2D vMid) {
		ParabolaEdge e = new ParabolaEdge(edgeCounter, source, target, vMid);
		edgeCounter++;
		edges.add(e);
		source.addEdge(e);
		return e;
	}

	public Iterator<InNode> inNodeIterator() {
		return inNodes.iterator();
	}

	public Iterator<OutNode> outNodeIterator() {
		return outNodes.iterator();
	}

	public Iterator<Edge> edgeIterator() {
		return edges.iterator();
	}

	public void setMaxLength(double l) {
		maxLength = l;
	}

	public double getMaxLength() {
		return maxLength;
	}

	public void setMaxAffineWeight(double w) {
		maxAffineWeight = w;
	}

	public double getMaxAffineWeight() {
		return maxAffineWeight;
	}

	public void setMinSupportAngle(double angle) {
		minSupportAngle = angle;
	}

	public double getMinSupportAngle() {
		return minSupportAngle;
	}

	public boolean filter(Edge e) {
		return (e.getSegment().length() < maxLength) && (e.getSegment().affineWeight() < maxAffineWeight) && (e.getSegment().supportAngle() > minSupportAngle);
	} 

}
