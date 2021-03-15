package crevice;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.Box;

/**
 *
 * @author Loïc Vital
 *
 */

public class LineSetModelingPane extends JPanel {

	private JButton buttonSwitchAdd, buttonSwitchRemove;

	public LineSetModelingPane(LineSetCtrl _ctrl) {
		super();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(new JLabel("Line set"));
		add(Box.createVerticalStrut(50));

		buttonSwitchAdd = new JButton(_ctrl.createSwitchAddAction());
		add(buttonSwitchAdd);
		add(Box.createVerticalStrut(10));

		buttonSwitchRemove = new JButton(_ctrl.createSwitchRemoveAction());
		add(buttonSwitchRemove);
		add(Box.createVerticalStrut(10));

		add(Box.createVerticalStrut((int)_ctrl.getApp().getWindowDim().getHeight()));
	}

}
