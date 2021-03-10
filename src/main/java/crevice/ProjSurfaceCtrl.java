package crevice;

import javax.swing.JPanel;

/**
 *
 * @author Loïc Vital
 *
 */

public class ProjSurfaceCtrl extends AbstractCtrl implements PaintedElement {

	private ProjSurface surface;
	private ProjSurfaceModelingPane modelPane;
	private ProjSurfaceLayer layer;

	public ProjSurfaceCtrl(CreviceApp _app, ProjSurface _surface) {
		super(_app);
		surface = _surface;
		modelPane = new ProjSurfaceModelingPane(this);
		layer = new ProjSurfaceLayer(this);
	}

	public ProjSurface getSurface() {
		return surface;
	}

	@Override
	public JPanel getModelingPane() {
		return modelPane;
	}

	@Override
	public JPanel getPaintLayer() {
		return layer;
	}

}
