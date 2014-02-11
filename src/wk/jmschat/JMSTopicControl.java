package wk.jmschat;

import javax.jms.*;
import java.awt.event.ActionEvent;

/**
 * @author Andreas Willinger
 */
public class JMSTopicControl implements javax.jms.MessageListener, Runnable, java.awt.event.ActionListener {

	private javax.jms.Connection topicConnection;

	private javax.jms.Session topicSession;

	private javax.jms.MessageProducer topicSender;

	private MessageConsumer topicReceiver;

	private JMSOptions options;

	private JMSModel jMSModel;

	private Text text;

	public JMSTopicControl(JMSModel model, Text textContainer, JMSOptions options) {

	}

	public void stop() {

	}

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void onMessage(Message message) {

    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

    }
}
