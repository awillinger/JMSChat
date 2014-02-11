package wk.jmschat;

/**
 * @author Jakob Klepp
 */
public class Main {
    /**
     * @param args Commandline arguments
     *             -h <hostname>
     *             -u <username>
     *             -c <channel>
     */
	public static void main(String... args) {
        OptParser optParser;
        JMSModel model;
        JMSView view;

        optParser = new OptParser();
        boolean success = optParser.parse(args);

        if(!success) {
            System.out.println("Commandline arguments");
            System.out.println("\t-h <hostname>");
            System.out.println("\t-u <username>");
            System.out.println("\t-c <channel>");

            return;
        }

        model = new JMSModel(optParser);

        view = new JMSView(model, optParser);
	}
}
