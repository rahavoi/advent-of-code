package rahavoi.advent.of.code._2017;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

public class Util {
    public static String loadFileAsString(String path) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(path);

        if (inputStream == null) {
            throw new IllegalArgumentException("File does not exist: " + path);
        }

        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(inputStream, writer, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return writer.toString();
    }
}
