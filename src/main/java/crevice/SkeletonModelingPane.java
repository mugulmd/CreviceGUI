package crevice;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.Box;

/**
 *
 * @author Loïc Vital
 *
 */

public class SkeletonModelingPane extends JPanel {

	private SkeletonCtrl ctrl;

	private JButton buttonChangeRootEdge;
	private JComboBox listExplorer;
	private JButton buttonExplore;
	private JSlider sliderDepth;
	private JTextField fieldDepth;

	public SkeletonModelingPane(SkeletonCtrl _ctrl) {
		super();
		ctrl = _ctrl;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(new JLabel("Skeleton"));

		buttonChangeRootEdge = new JButton(ctrl.createChangeRootEdgeAction());
		add(buttonChangeRootEdge);

		buildExplorerPane();

		buttonExplore = new JButton(ctrl.createExploreAction());
		add(buttonExplore);

		buildDepthPane();

		add(Box.createVerticalGlue());
	}

	private void buildExplorerPane() {
		JPanel panel = new JPanel();

		listExplorer = new JComboBox();
		panel.add(listExplorer);

		add(panel);
	}

	private void buildDepthPane() {
		JPanel panel = new JPanel();

		panel.add(new JLabel("Depth"));

		int d = 100;

		sliderDepth = new JSlider(0, 100, d);
		panel.add(sliderDepth);

		fieldDepth = new JTextField(String.valueOf(d), 3);
		fieldDepth.setEditable(false);
		panel.add(fieldDepth);

		add(panel);
	}

	public JComboBox getListExplorer() {
		return listExplorer;
	}

	public JSlider getSliderDepth() {
		return sliderDepth;
	}

	public JTextField getFieldDepth() {
		return fieldDepth;
	}

}
