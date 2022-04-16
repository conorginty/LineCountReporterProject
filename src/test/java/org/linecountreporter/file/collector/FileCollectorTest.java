package org.linecountreporter.file.collector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.linecountreporter.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileCollectorTest {
    private FileCollector fileCollector;
    private static final String FILE_COLLECTOR_DIRECTORY_PATH = Utils.RESOURCES_DIRECTORY_PATH + "/file-collector";
    private static final String DIRECTORY_1_PATH = FILE_COLLECTOR_DIRECTORY_PATH + "/directory-1";
    private static final String DIRECTORY_2_PATH = FILE_COLLECTOR_DIRECTORY_PATH + "/directory-2";

    @BeforeEach
    void setup() {
        fileCollector = new FileCollector(DIRECTORY_1_PATH);
    }

    @Test
    void can_verify_path_validity() {
        assertTrue(fileCollector.hasValidPath());

        String invalidPath = "i/don't/exist";
        fileCollector = new FileCollector(invalidPath);
        assertFalse(fileCollector.hasValidPath());
    }

    @Test
    void can_update_path_to_directory() {
        assertEquals(DIRECTORY_1_PATH, fileCollector.getPath());

        String updatedPath = DIRECTORY_2_PATH;
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
        File[] expected = new File[2];
        expected[0] = new File(DIRECTORY_1_PATH + "/dir1_file1.txt");
        expected[1] = new File(DIRECTORY_1_PATH + "/dir1_file2.txt");

        File[] actual = fileCollector.getDirectoryContents();

        assertArrayEquals(expected, actual);

        fileCollector.updatePath(DIRECTORY_2_PATH);

        expected[0] = new File(DIRECTORY_2_PATH + "/dir2_file1.txt");
        expected[1] = new File(DIRECTORY_2_PATH + "/dir2_file2.txt");

        actual = fileCollector.getDirectoryContents();

        assertArrayEquals(expected, actual);
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