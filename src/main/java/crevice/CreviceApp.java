package crevice;

import java.awt.Dimension;

/**
 *
 * @author Loïc Vital
 *
 */

public class CreviceApp implements Runnable {

	private Dimension windowDim;
	private Dimension canvasDim;
	private BackgroundCtrl bgCtrl;
	private CameraCtrl camCtrl;
	private ModelingMode mode;
	private CreviceWindow window;

	private CreviceProject project;
	private ProjSurfaceCtrl surfaceCtrl;
	private LineSetCtrl linesCtrl;
	private NetworkCtrl netCtrl;
	private SkeletonCtrl skCtrl;

	public CreviceApp() {
		windowDim = new Dimension(1200, 900);
		canvasDim = new Dimension(800, 800);
		bgCtrl = new BackgroundCtrl(this, new Background());
		camCtrl = new CameraCtrl(this, new Camera());
		mode = ModelingMode.BACKGROUND;

		project = new CreviceProject(this);
		surfaceCtrl = new ProjSurfaceCtrl(this, project.getSurface());
		linesCtrl = new LineSetCtrl(this, project.getLines());
		netCtrl = new NetworkCtrl(this, project.getNetwork());
		skCtrl = new SkeletonCtrl(this, project.getSkeleton());
	}

	@Override
	public void run() {
		window = new CreviceWindow(this);
		window.setVisible(true);
	}

	public Dimension getWindowDim() {
		return windowDim;
	}

	public Dimension getCanvasDim() {
		return canvasDim;
	}

	public BackgroundCtrl getBackgroundCtrl() {
		return bgCtrl;
	}

	public CameraCtrl getCameraCtrl() {
		return camCtrl;
	}

	public void cameraChanged() {
		surfaceCtrl.getPaintLayer().repaint();
		linesCtrl.getPaintLayer().repaint();
		netCtrl.getPaintLayer().repaint();
		skCtrl.getPaintLayer().repaint();
	}

	public CreviceWindow getWindow() {
		return window;
	}

	public ModelingMode getModelingMode() {
		return mode;
	}

	public void changeModelingMode(ModelingMode _mode) {
		if (!mode.equals(_mode)) {
			mode = _mode;
		}
	}

	public CreviceProject getProject() {
		return project;
	}

	public ProjSurfaceCtrl getSurfaceCtrl() {
		return surfaceCtrl;
	}

	public LineSetCtrl getLinesCtrl() {
		return linesCtrl;
	}

	public NetworkCtrl getNetworkCtrl() {
		return netCtrl;
	}

	public SkeletonCtrl getSkeletonCtrl() {
		return skCtrl;
	}

	public void stageChanged(ProjectStage stage) {
		if(project.updateLastStage(stage)) {
			window.updateEnabledActions();
		}
	}

}
