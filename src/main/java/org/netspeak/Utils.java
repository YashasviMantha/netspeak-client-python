package org.netspeak;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

/**
 * @author marcel.gohsen@uni-weimar.de
 */
public class Utils {
    public static void downloadFile(final String url, final String destination) throws IOException {
        final URL imagePath = new URL(url);
        ReadableByteChannel readableByteChannel = Channels.newChannel(imagePath.openStream());
        try(FileOutputStream fileOutputStream = new FileOutputStream(destination)){
            FileChannel fileChannel = fileOutputStream.getChannel();

            fileChannel.transferFrom(readableByteChannel,0, Long.MAX_VALUE);
        }
    }

    public static void unzipFile(final String archive, final String destination) throws ZipException {
        ZipFile zipFile = new ZipFile(archive);
        zipFile.extractAll(destination);
    }
}
