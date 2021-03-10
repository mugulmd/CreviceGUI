package crevice;

import javax.swing.JPanel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author Loïc Vital
 *
 */

public class BackgroundCtrl extends AbstractCtrl implements PaintedElement {

	private Background bg;
	private BackgroundModelingPane modelPane;
	private BackgroundLayer layer;

	private class SliderBrightnessHandle implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			bg.setBrightness(modelPane.getSliderBrightness().getValue());
			modelPane.getFieldBrightness().setText(String.valueOf(bg.getBrightness()));
			layer.repaint();
		}
	}

	public BackgroundCtrl(CreviceApp _app, Background _bg) {
		super(_app);

		bg = _bg;

		modelPane = new BackgroundModelingPane(this);
		modelPane.getSliderBrightness().addChangeListener(new SliderBrightnessHandle());

		layer = new BackgroundLayer(this);
	}

	public Background getBackground() {
		return bg;
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
