package ru.job4j.io.searchfilesbycriterion;

import ru.job4j.io.ArgsName;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static java.nio.file.FileVisitResult.CONTINUE;

public class FileFinder extends SimpleFileVisitor<Path> {
    private final Map<String, String> arguments;
    private Predicate<Path> predicateCheck;
    private final Path directory;
    private final StringBuilder result;
    private static final ArrayList<ParameterDescription> PARAMETERS = new ArrayList<>(
            Arrays.asList(
                new ParameterBuilder()
                        .setName(Params.DIRECTORY.getCode())
                        .addParameterValidation("directory",
                                Pattern.compile("^([a-zA-Z]:\\\\)([^<>:.#\"/\\\\|?*]+((\\\\)(?=[^<>:.#\"/\\\\|?*]))?)*$")
                                        .asPredicate())
                        .setPrompt("Invalid directory. The path must only contain the characters a-zA-Z0-9_-.")
                        .build(),
                new ParameterBuilder()
                        .setName(Params.NAME.getCode())
                        .addParameterValidation("name",
                                Pattern.compile("[^<>:\\\\\"/|.?*]+\\.[a-z]+")
                                        .asPredicate())
                        .addParameterValidation("mask",
                                Pattern.compile("^(\\*|[\\*\\?a-zA-Z0-9-_()]+)(\\.[a-z]+)+$")
                                        .asPredicate())
                        .addParameterValidation("regex",
                                s -> {
                                    try {
                                        Pattern.compile(s);
                                    } catch (Exception e) {
                                        return false;
                                    }
                                    return true;
                                })
                        .setPrompt("Invalid search data. You can search only by file name, mask or regular expression.")
                        .build(),
                new ParameterBuilder()
                        .setName(Params.SEARCH_TYPE.getCode())
                        .addParameterValidation("typeSearch",
                                Pattern.compile("(mask|name|regex)")
                                        .asPredicate())
                        .setPrompt("Invalid search type. You must specify one of three types: name, mask, regex.")
                        .build(),
                new ParameterBuilder()
                        .setName(Params.OUT.getCode())
                        .addParameterValidation("fileResult",
                                Pattern.compile("[^<>:\\\\\"/|.?*]+\\.[a-z]+")
                                        .asPredicate())
                        .setPrompt("Invalid output file. The filename must only contain the characters a-zA-Z0-9_-.")
                        .build()
            )
    );

    public FileFinder(ArgsName argsObj) {
        this.arguments = Validator.validationStart(argsObj, FileFinder.PARAMETERS);
        this.result = new StringBuilder();
        this.directory = Paths.get(arguments.get(Params.DIRECTORY.getCode()));
        setTemplateCheck(arguments.get(Params.SEARCH_TYPE.getCode()), arguments.get(Params.NAME.getCode()));
    }

    private void setTemplateCheck(String type, String subjectOfSearch) {
        if ("mask".equals(type)) {
            String regexFromMask = subjectOfSearch.replace("?", ".?").replace("*", ".*");
            this.predicateCheck = s -> s.getFileName().toString().matches(regexFromMask);
        } else if ("name".equals(type)) {
            this.predicateCheck = s -> s.getFileName().toString().equals(subjectOfSearch);
        } else if ("regex".equals(type)) {
            Pattern pattern = Pattern.compile(subjectOfSearch);
            this.predicateCheck = s -> pattern.matcher(s.getFileName().toString()).find();
        }
    }

    public StringBuilder getResult() {
        return result;
    }

    public String getOutputFile() {
        return arguments.get(Params.OUT.getCode());
    }

    public Path getDirectory() {
        return directory;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if (this.predicateCheck.test(file)) {
            this.result.append(file.toAbsolutePath())
                    .append(System.lineSeparator());
        }
        return CONTINUE;
    }

    private enum Params {
        DIRECTORY("d"), OUT("o"), NAME("n"), SEARCH_TYPE("t");
        private final String code;

        Params(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public static void main(String[] args) throws IOException {
        ArgsName argsObj = ArgsName.of(args);
        FileFinder finder = new FileFinder(argsObj);
        Files.walkFileTree(finder.getDirectory(), finder);
        Writer.stringBuilderToFile(finder.getResult(), finder.getOutputFile());
    }
}
