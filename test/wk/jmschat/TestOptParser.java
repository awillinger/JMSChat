package wk.jmschat;

import org.junit.*;

import java.util.regex.Pattern;

/**
 * Junittest for OptParser
 * @author Jakob Klepp
 */
public class TestOptParser {
    OptParser optParser;
    String [] validArgs = {"-h", "tpc://localhost:22222", "-u", "testuser", "-c", "Channel"};
    String [] emptyArgs = {};
    String [] unknownArgs = {"-h", "tpc://localhost:22222", "-u", "testuser", "-c", "Channel", "-z", "zzzz"};

    @BeforeClass
    public static void beforeClass() {
        System.out.println("");
        System.out.println("===================");
        System.out.println("TestOptParser Start");
        System.out.println("===================");
    }

    @Before
    public void before() {
        optParser = new OptParser();
        optParser.parse(validArgs);
    }

    @Test
    public void test_parse() {
        System.out.println("");
        System.out.println("==========");
        System.out.println("test_parse");
        System.out.println("==========");
        //empty args
        boolean success = optParser.parse(emptyArgs);
        System.out.println(success);
        Assert.assertFalse("Empty arguments are NOT allowed, return false", success);
        System.out.println(optParser.getHost());
        Assert.assertNull("Empty args should result in null objects", optParser.getHost());
        System.out.println(optParser.getUsername());
        Assert.assertNull("Empty args should result in null objects", optParser.getUsername());
        System.out.println(optParser.getChannel());
        Assert.assertNull("Empty args should result in null objects", optParser.getChannel());

        //unknows args
        Assert.assertFalse("Unknows arguments are NOT allowed, return false", optParser.parse(unknownArgs));
    }

    @Test
    public void test_getHost() {
        System.out.println("");
        System.out.println("============");
        System.out.println("test_getHost");
        System.out.println("============");
        System.out.println(optParser.getHost());
        Assert.assertEquals("Should equal", validArgs[1], optParser.getHost());
    }

    @Test
    public void test_getUsername() {
        System.out.println("");
        System.out.println("================");
        System.out.println("test_getUsername");
        System.out.println("================");
        System.out.println(optParser.getUsername());
        Assert.assertEquals("Should equal", validArgs[3], optParser.getUsername());
    }

    @Test
    public void test_getChannel() {
        System.out.println("");
        System.out.println("===============");
        System.out.println("test_getChannel");
        System.out.println("===============");
        System.out.println(optParser.getChannel());
        Assert.assertEquals("Should equal", validArgs[5], optParser.getChannel());
    }

    @Test
    public void test_getIp() {
        System.out.println("");
        System.out.println("==========");
        System.out.println("test_getIp");
        System.out.println("==========");
        String regex =  "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        System.out.println(optParser.getIp());
        Assert.assertNotNull("IP address should not be null", optParser.getIp());
        Assert.assertTrue("Should match the Pattern of an IP address", Pattern.matches(regex, optParser.getIp()));
    }

    @After
    public void after() {
        //nothing to do here
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("");
        System.out.println("=================");
        System.out.println("TestOptParser End");
        System.out.println("=================");
    }
}
