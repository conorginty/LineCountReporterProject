package org.linecountreporter.file.collector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileCollector {
    private String path;
    private File[] directoryContents;
    private List<File> files = new ArrayList<>();
    private List<String> filenames = new ArrayList<>();

    public FileCollector(String path) {
        this.path = path;
        updateDirectoryContents();
        collectFilesAndFilenames();
    }

    public void updatePath(String updatedPath) {
        this.path = updatedPath;
        updateDirectoryContents();
    }

    private boolean directoryExists() {
        File directory = new File(this.path);
        return directory.exists();
    }

    private boolean isDirectory() {
        File directory = new File(this.path);
        return directory.isDirectory();
    }

    public boolean hasValidPath() {
        return directoryExists() && isDirectory();
    }

    private void updateDirectoryContents() {
        if (hasValidPath()) {
            this.directoryContents = new File(this.path).listFiles();
        } else {
            this.directoryContents = new File[0];
        }
        collectFilesAndFilenames();
    }

    private void collectFilesAndFilenames() {
        clearFiles();
        collectFiles();

        clearFilenames();
        collectFilenames();
    }

    private void clearFiles() {
        this.files.clear();
    }

    private void collectFiles() {
        for (File item: directoryContents) {
            if (item.isFile()) {
                this.files.add(item);
            }
        }
    }

    private void clearFilenames() {
        this.filenames.clear();
    }

    private void collectFilenames() {
        for (File item: directoryContents) {
            if (item.isFile()) {
                this.filenames.add(item.getName());
            }
        }
    }

    public String getPath() {
        return this.path;
    }

    public List<File> getFiles() {
        return this.files;
    }

    public List<String> getFilenames() {
        return this.filenames;
    }

    public File[] getDirectoryContents() {
        return this.directoryContents;
    }
}
