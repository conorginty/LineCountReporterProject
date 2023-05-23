package org.linecountreporter.file.collector;

import java.io.File;
import java.util.List;

public interface IFileEntityCollector {
    void collectFrom(List<File> directoryContents);
}
