package org.linecountreporter.file.collector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryCollector implements IFileEntityCollector {
    private final List<File> directories = new ArrayList<>();
    private final List<String> directoryNames = new ArrayList<>();

    @Override
    public void collectFrom(List<File> directoryContents) {
        collectDirectories(directoryContents);
        collectDirectoryNames();
    }

    private void collectDirectories(List<File> directoryContents) {
        clearFiles();
        for (File item: directoryContents) {
            if (item.isDirectory()) {
                this.directories.add(item);
            }
        }
    }

    private void clearFiles() {
        this.directories.clear();
    }

    private void collectDirectoryNames() {
        clearFilenames();
        for (File file: this.directories) {
            this.directoryNames.add(file.getName());
        }
    }

    private void clearFilenames() {
        this.directoryNames.clear();
    }

    public List<File> getDirectories() {
        return this.directories;
    }

    public List<String> getDirectoryNames() {
        return this.directoryNames;
    }
}
