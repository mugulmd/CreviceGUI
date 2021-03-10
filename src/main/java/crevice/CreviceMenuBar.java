package crevice;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 *
 * @author Loïc Vital
 *
 */

public class CreviceMenuBar extends JMenuBar {

	public CreviceMenuBar(CreviceWindow _window) {
		super();

		// Menu for project operations

		JMenu menuProject = new JMenu("Project");

		JMenuItem itemLoad = new JMenuItem(_window.createLoadProjectAction());
		menuProject.add(itemLoad);

		JMenuItem itemSave = new JMenuItem(_window.createSaveProjectAction());
		menuProject.add(itemSave);

		add(menuProject);

		// Menu for plant modeling stages
		
		JMenu menuStages = new JMenu("Stages");

		JMenuItem itemSurface = new JMenuItem(_window.createModelingModeAction(ModelingMode.SURFACE));
		menuStages.add(itemSurface);

		JMenuItem itemLines = new JMenuItem(_window.createModelingModeAction(ModelingMode.LINES));
		menuStages.add(itemLines);

		JMenuItem itemNetwork = new JMenuItem(_window.createModelingModeAction(ModelingMode.NETWORK));
		menuStages.add(itemNetwork);

		JMenuItem itemSkeleton = new JMenuItem(_window.createModelingModeAction(ModelingMode.SKELETON));
		menuStages.add(itemSkeleton);

		/*
		JMenuItem itemPlant = new JMenuItem(_window.createModelingModeAction(ModelingMode.PLANT));
		menuStages.add(itemPlant);
		*/

		add(menuStages);

		// Menu for camera and background

		JMenu menuOther = new JMenu("Other");

		JMenuItem itemBackground = new JMenuItem(_window.createModelingModeAction(ModelingMode.BACKGROUND));
		menuOther.add(itemBackground);

		JMenuItem itemCamera = new JMenuItem(_window.createModelingModeAction(ModelingMode.CAMERA));
		menuOther.add(itemCamera);

		add(menuOther);
	}

}
