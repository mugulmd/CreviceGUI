package crevice;

/**
 *
 * @author Loïc Vital
 *
 */

public enum ModelingMode {

	BACKGROUND("Background", -1), 
	CAMERA("Camera", -1), 
	SURFACE("Projection surface", 0), 
	LINES("Line segments", 1), 
	NETWORK("Network", 2), 
	SKELETON("Skeleton", 3), 
	PLANT("Plant", 4);

	private String displayName;
	private int stageId;

	private ModelingMode(String _displayName, int _stageId) {
		displayName = _displayName;
		stageId = _stageId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public int getStageId() {
		return stageId;
	}

}
