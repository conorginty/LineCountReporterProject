package org.linecountreporter.file.collector;

import org.linecountreporter.validator.PathValidator;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileCollector {
    private Path path;
    private List<File> directoryContents;
    private final List<File> files = new ArrayList<>();
    private final List<String> filenames = new ArrayList<>();

    public FileCollector(Path path) {
        this.path = path;
        update();
    }

    public void updatePath(Path updatedPath) {
        this.path = updatedPath;
        update();
    }

    private void update() {
        PathValidator.validateDirectory(this.path);
        this.directoryContents = new ArrayList<>(List.of(Objects.requireNonNull(new File(this.path.toString()).listFiles())));
        directoryContents.sort(File::compareTo);
        updateFilesAndFilenames();
    }

    private void updateFilesAndFilenames() {
        collectFiles();


        collectFilenames();
    }

    private void collectFiles() {
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

    public Path getPath() {
        return this.path;
    }

    public List<File> getFiles() {
        return this.files;
    }

    public List<String> getFilenames() {
        return this.filenames;
    }

    public List<File> getDirectoryContents() {
        return this.directoryContents;
    }
}
