package crevice;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;

/**
 *
 * @author Loïc Vital
 *
 */

public class CreviceProject {

	private CreviceApp app;

	private String dataPath;
	private String outputPath;

	private List<ProjectStage> stages;
	private int lastStageId;

	private ProjSurface surface;
	private LineSet lines;
	private Network net;
	private Skeleton skeleton;

	public CreviceProject(CreviceApp _app) {
		app = _app;

		dataPath = "data/";
		outputPath = "output/";

		stages = new ArrayList<ProjectStage>();
		lastStageId = -1;

		surface = new ProjSurface();
		stages.add(surface);

		lines = new LineSet();
		stages.add(lines);

		net = new Network();
		stages.add(net);

		skeleton = new Skeleton();
		stages.add(skeleton);
	}

	public void load() {
		try {
			surface.setImage(ImageIO.read(new File(dataPath+"surface.png")));
			app.stageChanged(surface);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void save() {
		saveLines();
		saveNetwork();
		saveSkeleton();
		System.out.println("Project saved");
	}

	private void saveLines() {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> toMap = new HashMap<String, Object>();

		List<Map<String, Object>> linesObj = new LinkedList<Map<String, Object>>();
		Iterator<LineSegment> it = lines.lineIterator();
		while(it.hasNext()) {
			LineSegment line = it.next();
			Map<String, Object> lineObj = new HashMap<String, Object>();
			lineObj.put("x1", line.getStart().getX());
			lineObj.put("y1", line.getStart().getY());
			lineObj.put("x2", line.getEnd().getX());
			lineObj.put("y2", line.getEnd().getY());
			linesObj.add(lineObj);
		}
		toMap.put("lines", linesObj);

		try {
			mapper.writeValue(new File(outputPath+"lines.json"), toMap);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void saveNetwork() {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> toMap = new HashMap<String, Object>();

		/* Nodes */

		// in-nodes

		List<Map<String, Object>> inNodesObj = new LinkedList<Map<String, Object>>();
		Iterator<InNode> inNodeIt = net.inNodeIterator();
		while(inNodeIt.hasNext()) {
			InNode node = inNodeIt.next();
			Map<String, Object> nodeObj = new HashMap<String, Object>();
			nodeObj.put("id", node.getId());
			nodeObj.put("x", node.getGeo().getPos().getX());
			nodeObj.put("y", node.getGeo().getPos().getY());
			nodeObj.put("angle", node.getGeo().getAngle());
			inNodesObj.add(nodeObj);
		}
		toMap.put("inNodes", inNodesObj);

		// out-nodes

		List<Map<String, Object>> outNodesObj = new LinkedList<Map<String, Object>>();
		Iterator<OutNode> outNodeIt = net.outNodeIterator();
		while(outNodeIt.hasNext()) {
			OutNode node = outNodeIt.next();
			Map<String, Object> nodeObj = new HashMap<String, Object>();
			nodeObj.put("id", node.getId());
			nodeObj.put("x", node.getGeo().getPos().getX());
			nodeObj.put("y", node.getGeo().getPos().getY());
			nodeObj.put("angle", node.getGeo().getAngle());
			outNodesObj.add(nodeObj);
		}
		toMap.put("outNodes", outNodesObj);

		/* Edges */

		List<Map<String, Object>> edgesObj = new LinkedList<Map<String, Object>>();
		Iterator<Edge> edgeIt = net.edgeIterator();
		while(edgeIt.hasNext()) {
			Edge edge = edgeIt.next();
			if(!net.filter(edge)) continue;
			Map<String, Object> edgeObj = new HashMap<String, Object>();
			edgeObj.put("id", edge.getId());
			edgeObj.put("sourceId", edge.getSource().getId());
			edgeObj.put("targetId", edge.getTarget().getId());
			if(edge.getSegment() instanceof LineSegment) {
				edgeObj.put("geo", "line");
			} else {
				edgeObj.put("geo", "parabola");
			}
			edgesObj.add(edgeObj);
		}
		toMap.put("edges", edgesObj);

		try {
			mapper.writeValue(new File(outputPath+"network.json"), toMap);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void saveSkeleton() {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> toMap = new HashMap<String, Object>();

		/* Root */

		Map<String, Object> rootNodeObj = new HashMap<String, Object>();
		Node rootNode = skeleton.getRootNode();
		rootNodeObj.put("id", rootNode.getId());
		rootNodeObj.put("x", rootNode.getGeo().getPos().getX());
		rootNodeObj.put("y", rootNode.getGeo().getPos().getY());
		rootNodeObj.put("angle", rootNode.getGeo().getAngle());
		toMap.put("rootNode", rootNodeObj);

		Map<String, Object> rootEdgeObj = new HashMap<String, Object>();
		Edge rootEdge = skeleton.getRootEdge();
		rootEdgeObj.put("id", rootEdge.getId());
		rootEdgeObj.put("sourceId", rootEdge.getSource().getId());
		rootEdgeObj.put("targetId", rootEdge.getTarget().getId());
		rootEdgeObj.put("geo", "parabola");
		toMap.put("rootEdge", rootEdgeObj);

		/* Children */

		List<Map<String, Object>> childrenMapObj = new LinkedList<Map<String, Object>>();
		Queue<Edge> queue = new LinkedList<Edge>();
		queue.add(skeleton.getRootEdge());
		while(!queue.isEmpty()) {
			Edge e = queue.poll();

			Map<String, Object> mapEntryObj = new HashMap<String, Object>();
			mapEntryObj.put("edgeId", e.getId());

			List<Object> childrenObj = new LinkedList<Object>();
			Iterator<Edge> childrenIt = skeleton.childrenIterator(e);
			while(childrenIt.hasNext()) {
				Edge c = childrenIt.next();
				if(!skeleton.filter(c)) continue;
				queue.add(c);
				childrenObj.add(c.getId());
			}
			mapEntryObj.put("children", childrenObj);

			childrenMapObj.add(mapEntryObj);
		}
		toMap.put("childrenMap", childrenMapObj);

		try {
			mapper.writeValue(new File(outputPath+"skeleton.json"), toMap);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public boolean updateLastStage(ProjectStage stage) {
		int stageId = stage.getModelingMode().getStageId();
		if(stageId == lastStageId) {
			return false;
		} else if(stageId == lastStageId+1) {
			lastStageId++;
			return true;
		} else if(stageId < lastStageId) {
			for(int i=stageId+1; i<=lastStageId; i++) {
				stages.get(i).clear();
			}
			lastStageId = stageId;
			return true;
		}
		return false;
	}

	public int getLastStageId() {
		return lastStageId;
	}

	public ProjSurface getSurface() {
		return surface;
	}

	public LineSet getLines() {
		return lines;
	}

	public Network getNetwork() {
		return net;
	}

	public Skeleton getSkeleton() {
		return skeleton;
	}

}
