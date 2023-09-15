package org.netspeak;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class NetspeakClientTest {
@Test
    public void testQueryNewYorkTimes() throws Exception {
        NetspeakClient client = new NetspeakClient(new File("/maven-dependencies/netspeak-client"));
        long expected = 26069786;

        long actual = client.search("new york times").getPhrases().get(0).getFrequency();
        
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void testQueryTimesSquare() throws Exception {
        NetspeakClient client = new NetspeakClient(new File("/maven-dependencies/netspeak-client"));
        long expected = 1535958;

        long actual = client.search("times square").getPhrases().get(0).getFrequency();
        
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testQueryDanceTimesSquare() throws Exception {
        NetspeakClient client = new NetspeakClient(new File("/maven-dependencies/netspeak-client"));
        long expected = 509;

        long actual = client.search("dance times square").getPhrases().get(0).getFrequency();
        
        Assert.assertEquals(expected, actual);
    } 
}
