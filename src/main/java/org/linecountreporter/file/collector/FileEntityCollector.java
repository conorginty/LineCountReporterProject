package org.linecountreporter.file.collector;

import org.linecountreporter.validator.PathValidator;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileEntityCollector {
    private Path path;
    private List<File> directoryContents;
    private final FileCollector fileCollector = new FileCollector();
    private final DirectoryCollector directoryCollector = new DirectoryCollector();

    public FileEntityCollector(Path path) {
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
        update(this.directoryContents);
    }

    private void update(List<File> directoryContents) {
        this.fileCollector.collectFrom(directoryContents);
        this.directoryCollector.collectFrom(directoryContents);
    }

    public Path getPath() {
        return this.path;
    }

    public List<File> getFiles() {
        return this.fileCollector.getFiles();
    }

    public List<String> getFilenames() {
        return this.fileCollector.getFilenames();
    }

    public List<File> getDirectories() {
        return this.directoryCollector.getDirectories();
    }

    public List<File> getDirectoryContents() {
        return this.directoryContents;
    }
}
