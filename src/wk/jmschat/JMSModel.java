package wk.jmschat;

import javax.jms.Message;
import java.util.Set;

/**
 * @author Andreas Willinger
 */
public class JMSModel {

	private Message[] messages; //maybe a collection would be better, should be a thread safe one

	private JMSView jMSView;

	private ModelObserver modelObserver;

	public Set<javax.jms.TextMessage> getMessages() {
		return null;
	}

	public void appendMessage(javax.jms.TextMessage message) {

	}

	public void addObserver() {

	}

	public JMSModel(String host, String username, String channel) {

	}

}
