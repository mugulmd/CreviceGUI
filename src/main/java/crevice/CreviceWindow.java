package crevice;

import java.util.List;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Action;
import javax.swing.AbstractAction;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;

/**
 *
 * @author Loïc Vital
 *
 */

public class CreviceWindow extends JFrame {

	private CreviceApp app;

	private CreviceCanvas canvas;
	private CreviceModelingPane modelingPane;
	private CreviceMenuBar menuBar;

	private List<ModelingModeAction> modelingModeActions;

	private class ModelingModeAction extends AbstractAction {

		ModelingMode mode;

		public ModelingModeAction(ModelingMode _mode) {
			super(_mode.getDisplayName());
			mode = _mode;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			app.changeModelingMode(mode);
			modelingPane.update();
			canvas.update();
		}
	}

	private class ChooseSurfaceAction extends AbstractAction {

		private CreviceWindow window;

		public ChooseSurfaceAction(CreviceWindow _window) {
			super("Choose surface");
			window = _window;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser surfaceChooser = app.getSysManager().getSurfaceChooser();
			int result = surfaceChooser.showOpenDialog(window);
			if(result == JFileChooser.APPROVE_OPTION) {
				app.getSysManager().setSurfaceFile(surfaceChooser.getSelectedFile());
				app.getSysManager().saveConfig();
				app.getProject().load();
				canvas.repaint();
			}
		}
	}

	private class LoadProjectAction extends AbstractAction {

		public LoadProjectAction() {
			super("Load project");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			app.getProject().load();
			canvas.repaint();
		} 
	}

	private class ChooseOutputAction extends AbstractAction {

		private CreviceWindow window;

		public ChooseOutputAction(CreviceWindow _window) {
			super("Choose output directory");
			window = _window;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser outputChooser = app.getSysManager().getOutputChooser();
			int result = outputChooser.showOpenDialog(window);
			if(result == JFileChooser.APPROVE_OPTION) {
				app.getSysManager().setOutputDir(outputChooser.getSelectedFile());
				app.getSysManager().saveConfig();
			}
		}
	}

	private class SaveProjectAction extends AbstractAction {

		public SaveProjectAction() {
			super("Save project");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			app.getProject().save();
		}
	}

	public CreviceWindow(CreviceApp _app) {
		super();
		app = _app;
		modelingModeActions = new LinkedList<ModelingModeAction>();
		buildUI();
		updateEnabledActions();
	}

	private void buildUI() {
		setTitle("Crevice");
		setSize(app.getWindowDim());
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buildContentPane();
		buildMenuBar();
	}

	private void buildContentPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		canvas = new CreviceCanvas(this);
		panel.add(canvas, BorderLayout.CENTER);

		modelingPane = new CreviceModelingPane(this);
		panel.add(modelingPane, BorderLayout.EAST);

		setContentPane(panel);
	}

	private void buildMenuBar() {
		menuBar = new CreviceMenuBar(this);
		setJMenuBar(menuBar);
	}

	public CreviceApp getApp() {
		return app;
	}

	public CreviceCanvas getCanvas() {
		return canvas;
	}

	public CreviceModelingPane getModelingPane() {
		return modelingPane;
	}

	public Action createModelingModeAction(ModelingMode _mode) {
		ModelingModeAction action = new ModelingModeAction(_mode);
		modelingModeActions.add(action);
		return action;
	}

	public Action createChooseSurfaceAction() {
		return new ChooseSurfaceAction(this);
	}

	public Action createLoadProjectAction() {
		return new LoadProjectAction();
	}

	public Action createChooseOutputAction() {
		return new ChooseOutputAction(this);
	}

	public Action createSaveProjectAction() {
		return new SaveProjectAction();
	}

	public void updateEnabledActions() {
		for(ModelingModeAction action : modelingModeActions) {
			if(action.mode.getStageId() <= app.getProject().getLastStageId()+1) {
				action.setEnabled(true);
			} else {
				action.setEnabled(false);
			}
		}
	}

}
