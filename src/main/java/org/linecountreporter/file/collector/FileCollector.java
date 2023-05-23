package org.linecountreporter.file.collector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileCollector implements IFileEntityCollector {
    private final List<File> files = new ArrayList<>();
    private final List<String> filenames = new ArrayList<>();

    @Override
    public void collectFrom(List<File> directoryContents) {
        collectFiles(directoryContents);
        collectFilenames();
    }

    private void collectFiles(List<File> directoryContents) {
        clearFiles();
        for (File item: directoryContents) {
            if (item.isFile()) {
                this.files.add(item);
            }
        }
    }

    private void clearFiles() {
        this.files.clear();
    }

    private void collectFilenames() {
        clearFilenames();
        for (File file: this.files) {
            this.filenames.add(file.getName());
        }
    }

    private void clearFilenames() {
        this.filenames.clear();
    }

    public List<File> getFiles() {
        return this.files;
    }

    public List<String> getFilenames() {
        return this.filenames;
    }
}
