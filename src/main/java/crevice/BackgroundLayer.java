package crevice;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Color;

/**
 *
 * @author Loïc Vital
 *
 */

public class BackgroundLayer extends JPanel {

	private BackgroundCtrl ctrl;

	public BackgroundLayer(BackgroundCtrl _ctrl) {
		super();
		ctrl = _ctrl;
		setSize(ctrl.getApp().getCanvasDim());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int brightness = ctrl.getBackground().getBrightness();
		g.setColor(new Color(brightness, brightness, brightness));
		g.fillRect(0, 0, getWidth(), getHeight());
	}

}
