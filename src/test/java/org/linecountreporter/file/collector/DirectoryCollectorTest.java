package org.linecountreporter.file.collector;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DirectoryCollectorTest extends IFileEntityCollectorTest {
    @Test
    public void given_a_list_of_files_and_directories_only_get_directories() {
        DirectoryCollector directoryCollector = new DirectoryCollector();
        assertEquals(0, directoryCollector.getDirectories().size());
        assertEquals(0, directoryCollector.getDirectoryNames().size());

        List<File> directoryContents = List.of(
            newDirectory(), newDirectory(), newFile()
        );
        directoryCollector.collectFrom(directoryContents);
        assertEquals(2, directoryCollector.getDirectories().size());
        assertEquals(2, directoryCollector.getDirectoryNames().size());
    }

    @Test
    public void given_collected_files_when_given_another_list_of_files_and_directories_replace_old_directories_with_new_directories() {
        DirectoryCollector directoryCollector = new DirectoryCollector();
        assertEquals(0, directoryCollector.getDirectories().size());
        assertEquals(0, directoryCollector.getDirectoryNames().size());

        List<File> directoryContents = List.of(
            newDirectory(), newFile()
        );
        directoryCollector.collectFrom(directoryContents);
        assertEquals(1, directoryCollector.getDirectories().size());
        assertEquals(1, directoryCollector.getDirectoryNames().size());

        List<File> newDirectoryContents = List.of(
            newDirectory(), newDirectory()
        );

        directoryCollector.collectFrom(newDirectoryContents);
        assertEquals(2, directoryCollector.getDirectories().size());
        assertEquals(2, directoryCollector.getDirectoryNames().size());
    }

}