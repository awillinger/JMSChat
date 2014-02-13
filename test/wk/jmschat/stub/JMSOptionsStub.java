package wk.jmschat.stub;

import wk.jmschat.JMSOptions;

/**
 * Options Stub for testing
 *
 * @author Andreas Willinger
 */
public class JMSOptionsStub
    implements JMSOptions
{
    /**
     * @return hostname
     */
    public String getHost(){
        return "tcp://127.0.0.1:61616";
    }

    /**
     * @return username
     */
    public String getUsername()
    {
        return "test";
    }

    /**
     * @return channel name
     */
    public String getChannel()
    {
        return "test";
    }

    /**
     * @return public ip address of the client
     */
    public String getIp()
    {
        return "123.123.123.123";
    }
}
