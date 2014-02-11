package wk.jmschat;

/**
 * @author Andreas Willinger
 */
public class JMSView extends javax.swing.JFrame implements ModelObserver, Text {

	private JMSModel model;

	private JMSTopicControl jMSTopicControl;

	private JMSMailControl jMSMailControl;

	public JMSView(JMSModel model, JMSOptions options) {

	}

	public void update() {

	}


	/**
	 * @see ModelObserver#update(JMSModel)
	 * 
	 *  
	 */
	public void update(JMSModel model) {

	}


	/**
	 * @see Text#getText()
	 * 
	 *  
	 */
	public String getText() {
		return null;
	}

}
