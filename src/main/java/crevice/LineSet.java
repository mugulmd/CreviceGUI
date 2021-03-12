package crevice;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

/**
 *
 * @author Loïc Vital
 *
 */

public class LineSet implements ProjectStage {

	private List<LineSegment> lines;

	public LineSet() {
		lines = new LinkedList<LineSegment>();
	}

	@Override
	public ModelingMode getModelingMode() {
		return ModelingMode.LINES;
	}

	@Override
	public void clear() {
		lines = new LinkedList<LineSegment>();
	}

	public void addLine(LineSegment line) {
		lines.add(line);
	}

	public void removeLine(LineSegment line) {
		lines.remove(line);
	}

	public Iterator<LineSegment> lineIterator() {
		return lines.iterator();
	}

}
