package wk.jmschat;

import java.util.*;

/**
 * The JMSModel class contains all data used in this Application and provides simply Methods to access/modify them.
 *
 * @author Andreas Willinger
 * @version 0.2
 */
public class JMSModel
{
    // these observers get notified once a change has been done to the message list below
    private List<ModelObserver> observers;
	private List<String> messages;

    public JMSModel(JMSOptions options)
    {
        this.observers = new ArrayList<>();
        this.messages = Collections.synchronizedList(new ArrayList<String>());
    }

    /**
     * Adds a message to the local buffer and notifies all observers to update themselves.
     *
     * @param message The Message to append
     */
	public void appendMessage(String message)
    {
        if(message != null)
        {
            this.messages.add(message);

            for(ModelObserver observer:this.observers)
            {
                observer.update(this);
            }
        }
	}

    /**
     * Adds a new observer to notify.
     *
     * @param observer The Observer to add
     */
	public void addObserver(ModelObserver observer)
    {
        if(!this.observers.contains(observer) && observer != null) this.observers.add(observer);
    }

    /**
     * Gets all Messages currently available in the local buffer.
     *
     * @return An Array containing all Messages
     */
    public String[] getMessages()
    {
        return Arrays.copyOf(this.messages.toArray(), this.messages.toArray().length, String[].class);
    }
}
