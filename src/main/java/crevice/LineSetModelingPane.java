package crevice;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JButton;

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

		buttonSwitchAdd = new JButton(_ctrl.createSwitchAddAction());
		add(buttonSwitchAdd);

		buttonSwitchRemove = new JButton(_ctrl.createSwitchRemoveAction());
		add(buttonSwitchRemove);
	}

}
