package org.netspeak;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

/**
 * @author marcel.gohsen@uni-weimar.de
 */
public class NetspeakClient {
    private final Process netspeakProcess;

    private final BufferedWriter stdOut;
    private final BufferedReader stdIn;
    private final BufferedReader stdErr;

    private final ObjectMapper objectMapper;

    public NetspeakClient() throws IOException {
        netspeakProcess = Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c",
                "./src/main/python/build/exe.linux-x86_64-3.10/netspeak_search_wrapper -stdin"});
        stdOut = new BufferedWriter(new OutputStreamWriter(netspeakProcess.getOutputStream()));
        stdIn = new BufferedReader(new InputStreamReader(netspeakProcess.getInputStream()));
        stdErr = new BufferedReader(new InputStreamReader(netspeakProcess.getErrorStream()));
        objectMapper = new ObjectMapper();
    }

    public SearchResults search(final String query) throws InterruptedException, IOException {
        stdOut.write(query);
        stdOut.newLine();
        stdOut.flush();

        String line = stdIn.readLine();
        if (line == null){
            StringBuilder errStringBuilder = new StringBuilder();
            while ((line = stdErr.readLine()) != null){
                errStringBuilder.append(line).append("\n");
            }

            throw new IOException(errStringBuilder.toString());
        }

        return objectMapper.readValue(line, SearchResults.class);
    }

    public void close() throws IOException {
        stdOut.write("\\exit");
        stdOut.newLine();
        stdOut.close();
        stdIn.close();
        stdErr.close();
        netspeakProcess.destroy();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        NetspeakClient netspeakClient = new NetspeakClient();
        SearchResults searchResults = netspeakClient.search("this is ... test");
        searchResults.getPhrases().forEach(System.out::println);
        netspeakClient.close();
    }
}
