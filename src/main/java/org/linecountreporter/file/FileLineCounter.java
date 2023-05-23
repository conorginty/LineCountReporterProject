package org.linecountreporter.file;

import org.linecountreporter.validator.PathValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileLineCounter {

    public static long lineCount(Path path) throws IOException {
        PathValidator.validateFile(path);

        boolean isBinaryFile = isBinaryFile(path);
        if (isBinaryFile) {
            return -1;
        }

        long count = 0;
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            throw new IOException("Something went wrong while the lines were counted", e);
        }
        return count;
    }

    private static boolean isBinaryFile(Path path) throws IOException {
        try (InputStream inputStream = Files.newInputStream(path)) {
            byte[] buffer = new byte[4096];
            int bytesRead = inputStream.read(buffer);
            for (int i = 0; i < bytesRead; i++) {
                if ((buffer[i] & 0xFF) <= 8) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            throw new IOException("Something went wrong with reading the file: " + path);
        }
    }
}
