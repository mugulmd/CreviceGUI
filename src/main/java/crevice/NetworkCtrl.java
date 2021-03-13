package crevice;

import javax.swing.JPanel;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author Loïc Vital
 *
 */

public class NetworkCtrl extends AbstractCtrl implements PaintedElement {

	private Network net;
	private NetworkModelingPane modelPane;
	private NetworkLayer layer;

	private class SliderOffsetHandle implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			net.setSamplingOffset((double)modelPane.getSliderOffset().getValue()/200);
			modelPane.getFieldOffset().setText(String.valueOf(net.getSamplingOffset()));
		}
	}

	private class BuildAction extends AbstractAction {

		public BuildAction() {
			super("Build network");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			app.stageChanged(net);
			LineSet lineSet = app.getProject().getLines();
			net.build(lineSet);
			layer.repaint();
		}
	}

	private class SliderLengthHandle implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			app.stageChanged(net);
			net.setMaxLength(modelPane.getSliderLength().getValue());
			modelPane.getFieldLength().setText(String.valueOf(net.getMaxLength()));
			layer.repaint();
		}
	}

	private class SliderAffineWeightHandle implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			app.stageChanged(net);
			net.setMaxAffineWeight(modelPane.getSliderAffineWeight().getValue());
			modelPane.getFieldAffineWeight().setText(String.valueOf(net.getMaxAffineWeight()));
			layer.repaint();
		}
	}

	private class SliderSupportAngleHandle implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			app.stageChanged(net);
			double angle = ((double)modelPane.getSliderSupportAngle().getValue())*Math.PI*0.01;
			net.setMinSupportAngle(angle);
			modelPane.getFieldSupportAngle().setText(String.format("%.2f", net.getMinSupportAngle()));
			layer.repaint();
		}
	}

	public NetworkCtrl(CreviceApp _app, Network _net) {
		super(_app);
		net = _net;

		modelPane = new NetworkModelingPane(this);
		modelPane.getSliderOffset().addChangeListener(new SliderOffsetHandle());
		modelPane.getSliderLength().addChangeListener(new SliderLengthHandle());
		modelPane.getSliderAffineWeight().addChangeListener(new SliderAffineWeightHandle());
		modelPane.getSliderSupportAngle().addChangeListener(new SliderSupportAngleHandle());

		layer = new NetworkLayer(this);
	}

	public Network getNetwork() {
		return net;
	}

	@Override
	public JPanel getModelingPane() {
		return modelPane;
	}

	@Override 
	public JPanel getPaintLayer() {
		return layer;
	}

	public Action createBuildAction() {
		return new BuildAction();
	}

}
