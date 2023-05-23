package org.linecountreporter.file.collector;

import java.io.File;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IFileEntityCollectorTest {

    protected File newFile() {
        File file = mock(File.class);
        when(file.isFile()).thenReturn(true);
        return file;
    }

    protected File newDirectory() {
        File directory = mock(File.class);
        when(directory.isDirectory()).thenReturn(true);
        return directory;
    }
}
