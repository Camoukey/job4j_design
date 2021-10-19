package ru.job4j.io.duplicates;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {

    private final Map<FileProperty, List<Path>> fileMap = new HashMap<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        var key = new FileProperty(file.toFile().length(), file.getFileName().toString());
        fileMap.putIfAbsent(key, new ArrayList<>());
        fileMap.get(key).add(file);
        return super.visitFile(file, attrs);
    }

    public Map<FileProperty, List<Path>> getAllFiles() {
        return fileMap;
    }
}
