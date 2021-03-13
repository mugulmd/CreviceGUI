package crevice;

/**
 *
 * @author Loïc Vital
 *
 */

public abstract class Explorer {

	protected Network net;
	protected Skeleton skeleton;

	public void setContext(Network _net, Skeleton _skeleton) {
		net = _net;
		skeleton = _skeleton;
	}

	abstract public void explore();

}
