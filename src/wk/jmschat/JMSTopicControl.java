package wk.jmschat;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.awt.event.*;
/**
 * The JMSTopicControl class handles the communication between this Application and the Chat Server.
 * To do that, it utilises the <code>JMS Topic</code> technique, in order to simulate a behaviour similar to IRC Chatrooms.
 *
 * Once a message has arrived, the underlying JMSModel is getting updated.
 * This also happens in the case of an error/exception.
 *
 * @author Andreas Willinger
 * @version 0.5
 */
public class JMSTopicControl
    implements MessageListener, Runnable, ActionListener
{
    // the JMS connection itself
	private Connection topicConnection;
	private Session topicSession;
	private MessageProducer topicSender;
	private MessageConsumer topicReceiver;

    // general stuff
	private JMSOptions options;
	private JMSModel model;
	private Text text;

	public JMSTopicControl(JMSModel model, Text textContainer, JMSOptions options)
    {
        this.options = options;
        this.model = model;
        this.text = textContainer;
	}


    /**
     *  Disconnect from the Chat Server
     */
	public void stop()
    {
        try
        {
            String sendMessage = String.format("%s@%s %s", this.options.getUsername(), this.options.getIp(), "hat den Chat verlassen.");
            TextMessage message = this.topicSession.createTextMessage(sendMessage);

            this.topicSender.send(message);

            this.topicReceiver.close();
            this.topicSender.close();
            this.topicSession.close();
            this.topicConnection.close();
        }
        catch(JMSException e)
        {
            this.model.appendMessage("SYSTEM: Konnte Verbindung nicht trennen!");
        }
	}

    /**
     * Invoked when an action occurs.
     *
     * @param e An Object containing the Source and similar data
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String currentContent = this.text.getText();

        if(currentContent.equals(""))
        {
            this.model.appendMessage("** Bitte geben Sie eine Nachricht ein!");
        }
        else if(currentContent.equalsIgnoreCase("exit"))
        {
            this.stop();
        }
        // regular chat message
        else
        {
            try
            {
                String sendMessage = String.format("%s@%s: %s", this.options.getUsername(), this.options.getIp(), currentContent);
                TextMessage message = this.topicSession.createTextMessage(sendMessage);

                this.topicSender.send(message);
            }
            catch(JMSException | NullPointerException ex)
            {
                this.model.appendMessage("SYSTEM: Fehler beim Senden der Nachricht! Bitte ueberpruefen Sie Ihre Netzwerkverbindung!");
            }
        }
    }

    @Override
    public void onMessage(Message message)
    {
        TextMessage msg = (TextMessage) message;

        if(msg != null)
        {
            try
            {
                String text = msg.getText();
                this.model.appendMessage(text);
            }
            catch(JMSException e)
            {
                this.model.appendMessage("SYSTEM: Fehler beim Empfangen der Nachricht!");
            }
        }
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
    public void run()
    {
        try
        {
            // set up connection
            this.topicConnection = new ActiveMQConnectionFactory(this.options.getHost()).createConnection();

            // create a sesssion & destination
            this.topicSession = this.topicConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination topicDestination = this.topicSession.createTopic(this.options.getChannel());

            // open up sender & receiver streams and set the delivery mode to non-persistent, since we use JMS as a Chatroom
            this.topicReceiver = this.topicSession.createConsumer(topicDestination);
            this.topicSender = this.topicSession.createProducer(topicDestination);
            this.topicSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // finally, fire up the connection
            this.topicReceiver.setMessageListener(this);
            this.topicConnection.start();

            String sendMessage = String.format("%s@%s %s", this.options.getUsername(), this.options.getIp(), "ist dem Chat beigetreten.");
            TextMessage message = this.topicSession.createTextMessage(sendMessage);

            this.topicSender.send(message);
        }
        catch(JMSException e)
        {
            this.model.appendMessage("SYSTEM: Fehler beim Herstellen der Verbindung zum Chat-Server!");
        }
    }
}
