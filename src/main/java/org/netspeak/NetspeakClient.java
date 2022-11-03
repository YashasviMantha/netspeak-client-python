package org.netspeak;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.ProtectionDomain;
import java.util.Map;

/**
 * @author marcel.gohsen@uni-weimar.de
 */
public class NetspeakClient {
    private static final String PYTHON_IMAGE =
            "https://files.webis.de/data-in-production/data-research/netspeak/netspeak-client/py3.10-linux-x86_64.zip";

    private Process netspeakProcess;

    private BufferedWriter stdOut;
    private BufferedReader stdIn;
    private BufferedReader stdErr;

    private final ObjectMapper objectMapper;

    public NetspeakClient() throws IOException, ZipException {
        File dir = new File("netspeak");
        if(!dir.exists()){
            if(!dir.mkdirs()){
                throw new IOException("Can't create directory!");
            };

            Utils.downloadFile(PYTHON_IMAGE, "netspeak/python-netspeak-client.zip");
            Utils.unzipFile("netspeak/python-netspeak-client.zip", "netspeak");
        }

        netspeakProcess = Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c",
                "chmod +x netspeak/build/exe.linux-x86_64-3.10/netspeak_search_wrapper && " +
                        "netspeak/build/exe.linux-x86_64-3.10/netspeak_search_wrapper -stdin"});


        stdOut = new BufferedWriter(new OutputStreamWriter(netspeakProcess.getOutputStream()));
        stdIn = new BufferedReader(new InputStreamReader(netspeakProcess.getInputStream()));
        stdErr = new BufferedReader(new InputStreamReader(netspeakProcess.getErrorStream()));
        objectMapper = new ObjectMapper();
    }

    public SearchResults search(final String query) throws InterruptedException, IOException {
        try{
            stdOut.write(query);
            stdOut.newLine();
            stdOut.flush();
        } catch (IOException e){
            String line;
            StringBuilder errStringBuilder = new StringBuilder();
            while ((line = stdErr.readLine()) != null){
                errStringBuilder.append(line).append("\n");
            }

            throw new IOException(errStringBuilder.toString());
        }


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
        NetspeakClient netspeakClient = null;
        try{
            netspeakClient = new NetspeakClient();
            SearchResults searchResults = netspeakClient.search("this is ... test");
            searchResults = netspeakClient.search("this ... test");
            searchResults.getPhrases().forEach(System.out::println);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (netspeakClient != null) {
                netspeakClient.close();
            }
        }
    }
}
