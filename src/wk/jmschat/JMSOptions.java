package wk.jmschat;

/**
 * Options used in various parts of the program.
 *
 * @author Jakob Klepp
 */
public interface JMSOptions {
    /**
     * @return hostname
     */
	public abstract String getHost();

    /**
     * @return username
     */
	public abstract String getUsername();

    /**
     * @return channel name
     */
	public abstract String getChannel();

    /**
     * @return public ip address of the client
     */
	public abstract String getIp();
}
