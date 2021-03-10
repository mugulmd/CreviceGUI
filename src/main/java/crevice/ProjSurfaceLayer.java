package crevice;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 *
 * @author Loïc Vital
 *
 */

public class ProjSurfaceLayer extends JPanel {

	private ProjSurfaceCtrl ctrl;

	public ProjSurfaceLayer(ProjSurfaceCtrl _ctrl) {
		super();
		ctrl = _ctrl;
		setSize(ctrl.getApp().getCanvasDim());
		setOpaque(false);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (ctrl.getSurface().hasImage()) {
			Graphics2D g2d = (Graphics2D) g;
			RenderingHints rh = new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2d.setRenderingHints(rh);

			BufferedImage img = ctrl.getSurface().getImage();
			Camera cam = ctrl.getApp().getCameraCtrl().getCamera();
			Vec2D vTopLeft = cam.applyTo(new Vec2D(0, 0));
			Vec2D vBottomRight = cam.applyTo(new Vec2D(img.getWidth(), img.getHeight()));
			int posX = (int)vTopLeft.getX();
			int posY = (int)vTopLeft.getY();
			int sizeX = (int)(vBottomRight.getX()-vTopLeft.getX());
			int sizeY = (int)(vBottomRight.getY()-vTopLeft.getY());

			g2d.drawImage(img, posX, posY, sizeX, sizeY, this);
		}
	}

}
