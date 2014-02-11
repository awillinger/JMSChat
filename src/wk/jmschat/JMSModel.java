package wk.jmschat;

import javax.jms.TextMessage;
import java.util.*;

/**
 * @author Andreas Willinger
 */
public class JMSModel
{
    private List<ModelObserver> observers;
	private List<TextMessage> messages;

    private JMSOptions options;

    public JMSModel(JMSOptions options)
    {
        this.options = options;

        this.observers = new ArrayList<ModelObserver>();
        this.messages = Collections.synchronizedList(new ArrayList<TextMessage>());
    }

	public TextMessage[] getMessages()
    {
		return Arrays.copyOf(this.messages.toArray(), this.messages.toArray().length, TextMessage[].class);
	}

	public void appendMessage(TextMessage message)
    {
        this.messages.add(message);

        for(ModelObserver m:this.observers)
        {
            m.update(this);
        }
	}

	public void addObserver(ModelObserver m)
    {
        if(!this.observers.contains(m)) this.observers.add(m);
    }
}
