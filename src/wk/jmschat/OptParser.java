package wk.jmschat;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * Parses the Options provided by Main.
 *
 * @author Jakob Klepp
 */
public class OptParser implements JMSOptions {
    /** Host address */
    @Parameter(names = { "-h" }, description = "Host address, looks like tcp://hostname:port")
	public String host;

    /** Username, will be displayed in chat */
    @Parameter(names = { "-u" }, description = "Username")
    public String username;

    /** Channel name */
    @Parameter(names = { "-c" }, description = "Channel name")
    public String channel;

    /** public ip address of the client */
    private String ip;

    /**
     * @param args Commandline arguments
     *             -h <hostname>
     *             -u <username>
     *             -c <channel>
     */
	public void parse(String [] args) {
        new JCommander(this, args);

        //todo retrieve ip
	}

	/**
	 * @see JMSOptions#getHost()
	 */
	public String getHost() {
		return this.host;
	}


	/**
	 * @see JMSOptions#getUsername()
	 */
	public String getUsername() {
		return this.username;
	}


	/**
	 * @see JMSOptions#getChannel()
	 */
	public String getChannel() {
		return this.channel;
	}


	/**
	 * @see JMSOptions#getIp()
	 */
	public String getIp() {
		return this.ip;
	}

}
