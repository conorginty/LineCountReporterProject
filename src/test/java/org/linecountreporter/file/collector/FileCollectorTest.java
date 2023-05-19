package org.linecountreporter.file.collector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.linecountreporter.file.FileCollector;
import org.linecountreporter.utils.Utils;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileCollectorTest {
    private FileCollector fileCollector;
    private static final Path FILE_COLLECTOR_DIRECTORY_PATH = Path.of(Utils.RESOURCES_DIRECTORY_PATH + "/file-collector");
    private static final Path DIRECTORY_1_PATH = Path.of(FILE_COLLECTOR_DIRECTORY_PATH + "/directory-1");
    private static final Path DIRECTORY_2_PATH = Path.of(FILE_COLLECTOR_DIRECTORY_PATH + "/directory-2");

    @BeforeEach
    void setup() {
        fileCollector = new FileCollector(DIRECTORY_1_PATH);
    }

    @Test
    void given_an_invalid_path_then_throw_exception() {
        Path invalidPath = Path.of("i/don't/exist");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new FileCollector(invalidPath));
        assertEquals(String.format("Supplied path: %s does not exist", invalidPath), exception.getMessage());
    }

    @Test
    void can_update_path_to_directory() {
        assertEquals(DIRECTORY_1_PATH, fileCollector.getPath());

        Path updatedPath = DIRECTORY_2_PATH;
        fileCollector.updatePath(updatedPath);
        assertEquals(updatedPath, fileCollector.getPath());
    }

    @Test
    void can_collect_all_files_in_path() {
        int filesCount = fileCollector.getFiles().size();
        assertEquals(2, filesCount);
    }

    @Test
    void can_collect_all_filenames_in_path() {
        int filenamesCount = fileCollector.getFilenames().size();
        assertEquals(2, filenamesCount);
    }

    @Test
    void when_path_is_updated_then_directory_contents_also_gets_updated() {
        List<File> expected = new ArrayList<>();
        expected.add(new File(DIRECTORY_1_PATH + "/dir1_file1.txt"));
        expected.add(new File(DIRECTORY_1_PATH + "/dir1_file2.txt"));

        List<File> actual = fileCollector.getDirectoryContents();

        assertEquals(expected, actual);

        fileCollector.updatePath(DIRECTORY_2_PATH);

        expected.clear();
        expected.add(new File(DIRECTORY_2_PATH + "/dir2_file1.txt"));
        expected.add(new File(DIRECTORY_2_PATH + "/dir2_file2.txt"));

        actual = fileCollector.getDirectoryContents();

        assertEquals(expected, actual);
    }

    @Test
    void when_path_is_updated_then_files_also_gets_updated() {
        List<File> expected = new ArrayList<>();
        expected.add(new File(DIRECTORY_1_PATH + "/dir1_file1.txt"));
        expected.add(new File(DIRECTORY_1_PATH + "/dir1_file2.txt"));

        List<File> actual = fileCollector.getFiles();

        assertEquals(expected, actual);

        fileCollector.updatePath(DIRECTORY_2_PATH);

        expected.clear();
        expected.add(new File(DIRECTORY_2_PATH + "/dir2_file1.txt"));
        expected.add(new File(DIRECTORY_2_PATH + "/dir2_file2.txt"));

        actual = fileCollector.getFiles();

        assertEquals(expected, actual);
    }

    @Test
    void when_path_is_updated_then_filenames_also_gets_updated() {
        List<String> expected = new ArrayList<>();
        expected.add("dir1_file1.txt");
        expected.add("dir1_file2.txt");

        List<String> actual = fileCollector.getFilenames();

        assertEquals(expected, actual);

        fileCollector.updatePath(DIRECTORY_2_PATH);

        expected.clear();
        expected.add("dir2_file1.txt");
        expected.add("dir2_file2.txt");

        actual = fileCollector.getFilenames();

        assertEquals(expected, actual);
    }
}