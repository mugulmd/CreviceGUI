package crevice;

import javax.swing.JPanel;

/**
 *
 * @author Loïc Vital
 *
 */

public abstract class AbstractCtrl {

	protected CreviceApp app;

	public AbstractCtrl(CreviceApp _app) {
		app = _app;
	}

	public CreviceApp getApp() {
		return app;
	}

	abstract public JPanel getModelingPane();

}
