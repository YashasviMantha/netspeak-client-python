package org.netspeak;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.lingala.zip4j.exception.ZipException;

import java.io.*;

/**
 * Netspeak client
 *
 * @author marcel.gohsen@uni-weimar.de
 */
public class NetspeakClient {
    private static final String PYTHON_IMAGE =
            "https://files.webis.de/data-in-production/data-research/netspeak/netspeak-client/py3.10-linux-x86_64.zip";

    private final Process netspeakProcess;

    private final BufferedWriter stdOut;
    private final BufferedReader stdIn;
    private final BufferedReader stdErr;

    private final ObjectMapper objectMapper;

    public NetspeakClient() {
        this(new File("netspeak"));
    }

    /**
     * Constructor that downloads the Netspeak client binary and executes it as subprocess.
     *
     */
    public NetspeakClient(File dir) {
        if(!dir.exists()){
            downloadNetspeakPythonClient(dir);
        }

        try {
            netspeakProcess = Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c",
                dir.getAbsolutePath() + "/build/exe.linux-x86_64-3.10/netspeak_search_wrapper -stdin"});

            stdOut = new BufferedWriter(new OutputStreamWriter(netspeakProcess.getOutputStream()));
            stdIn = new BufferedReader(new InputStreamReader(netspeakProcess.getInputStream()));
            stdErr = new BufferedReader(new InputStreamReader(netspeakProcess.getErrorStream()));
            objectMapper = new ObjectMapper();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void downloadNetspeakPythonClient(File dir) {
        try {
            if(!dir.mkdirs()){
                throw new IOException("Can't create directory!");
            }

            Utils.downloadFile(PYTHON_IMAGE, "netspeak/python-netspeak-client.zip");
            Utils.unzipFile("netspeak/python-netspeak-client.zip", "netspeak");
            Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c",
                "chmod +x " + dir + "/build/exe.linux-x86_64-3.10/netspeak_search_wrapper"});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Submits a search query to the Netspeak API.
     * @param query Search query (support Netspeak syntax)
     * @return Search results
     * @throws InterruptedException if subprocess crashes
     * @throws IOException if an error occurred in the subprocess
     */
    public SearchResults search(final String query) throws InterruptedException, IOException {
        try{
            stdOut.write(query);
            stdOut.newLine();
            stdOut.flush();
        } catch (IOException e){
            String line;
            final StringBuilder errStringBuilder = new StringBuilder();
            while ((line = stdErr.readLine()) != null){
                errStringBuilder.append(line).append("\n");
            }

            throw new IOException(errStringBuilder.toString());
        }


        String line = stdIn.readLine();
        if (line == null){
            final StringBuilder errStringBuilder = new StringBuilder();
            while ((line = stdErr.readLine()) != null){
                errStringBuilder.append(line).append("\n");
            }

            throw new IOException(errStringBuilder.toString());
        }

        return objectMapper.readValue(line, SearchResults.class);
    }

    /***
     * Ends the client subprocess.
     * @throws IOException if closing of IO streams fail.
     */
    public void close() throws IOException {
        stdOut.write("\\exit");
        stdOut.newLine();
        stdOut.close();
        stdIn.close();
        stdErr.close();
        netspeakProcess.destroy();
    }

    public static void main(String[] args) throws IOException {
        NetspeakClient netspeakClient = null;
        try {
            netspeakClient = new NetspeakClient();
            SearchResults searchResults = netspeakClient.search("how to ? this");
            searchResults.getPhrases().forEach(System.out::println);
            searchResults = netspeakClient.search("see ... works");
            searchResults.getPhrases().forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException("Oh no. Something went wrong :(", e);
        } finally {
            if (netspeakClient != null) {
                netspeakClient.close();
            }
        }
    }
}
