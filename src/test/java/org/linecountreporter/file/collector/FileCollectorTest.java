package org.linecountreporter.file.collector;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileCollectorTest extends IFileEntityCollectorTest {
    @Test
    public void given_a_list_of_files_and_directories_only_get_files() {
        FileCollector fileCollector = new FileCollector();
        assertEquals(0, fileCollector.getFiles().size());
        assertEquals(0, fileCollector.getFilenames().size());

        List<File> directoryContents = List.of(
            newFile(), newFile(), newDirectory()
        );
        fileCollector.collectFrom(directoryContents);
        assertEquals(2, fileCollector.getFiles().size());
        assertEquals(2, fileCollector.getFilenames().size());
    }

    @Test
    public void given_collected_files_when_given_another_list_of_files_and_directories_replace_old_files_with_new_ones() {
        FileCollector fileCollector = new FileCollector();
        assertEquals(0, fileCollector.getFiles().size());
        assertEquals(0, fileCollector.getFilenames().size());

        List<File> directoryContents = List.of(
            newFile(), newFile(), newDirectory()
        );
        fileCollector.collectFrom(directoryContents);
        assertEquals(2, fileCollector.getFiles().size());
        assertEquals(2, fileCollector.getFilenames().size());

        List<File> newDirectoryContents = List.of(
            newFile()
        );

        fileCollector.collectFrom(newDirectoryContents);
        assertEquals(1, fileCollector.getFiles().size());
        assertEquals(1, fileCollector.getFilenames().size());
    }
}