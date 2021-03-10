package crevice;

import java.awt.image.BufferedImage;

/**
 *
 * @author Loïc Vital
 *
 */

public class ProjSurface implements ProjectStage {
	
	private BufferedImage img;

	public ProjSurface() {
		img = null;
	}

	@Override
	public ModelingMode getModelingMode() {
		return ModelingMode.SURFACE;
	}

	@Override
	public void clear() {
		img = null;
	}

	public boolean hasImage() {
		return (img != null);
	}

	public void setImage(BufferedImage _img) {
		img = _img;
	}

	public BufferedImage getImage() {
		return img;
	}

}
