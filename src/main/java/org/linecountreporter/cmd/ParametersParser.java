package org.linecountreporter.cmd;

import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.cli.*;
import org.linecountreporter.report.model.ReportArguments;
import org.linecountreporter.report.writer.ConfigurationReader;
import org.linecountreporter.util.AppProperty;

import java.nio.file.Path;
import java.util.Properties;

import static org.linecountreporter.cmd.DefaultCliValues.*;
import static org.linecountreporter.report.writer.DefaultProperties.*;

public class ParametersParser {
    private final Options options = new Options();
    private final CommandLineParser parser = new DefaultParser();
    private CommandLine cli;
    private final Properties config = ConfigurationReader.readConfiguration();
    private JvmKiller jvmKiller = new JvmKiller();

    public void loadOptions() {
        loadRequiredOptions();
        loadOptionalOptions();
    }

    private void loadRequiredOptions() {
        Option filePath = createOption(SHORT_ROOT_PATH, "root-path", true, "descr", true);
        options.addOption(filePath);
    }

    private void loadOptionalOptions() {
        Option help = createOption(SHORT_HELP, "help", false, "desc", false);
        Option title = createOption(SHORT_TITLE, "title", true, "desc", false);
        Option fileType = createOption(SHORT_FILE_TYPE, "file-type", true, "desc", false);
        Option lineCountLimit = createOption(SHORT_LINE_COUNT_LIMIT, "line-count-limit", true, "desc", false);
        Option outputFilename = createOption(SHORT_OUTPUT_FILENAME, "output-filename", true, "desc", false);
        options.addOption(help);
        options.addOption(title);
        options.addOption(fileType);
        options.addOption(lineCountLimit);
        options.addOption(outputFilename);
    }

    private static Option createOption(String shortOpt, String longOpt, boolean hasArg, String description, boolean isRequired) {
        Option option = new Option(shortOpt, longOpt, hasArg, description);
        option.setRequired(isRequired);
        return option;
    }

    public ReportArguments parseArguments(String[] args) {
        if (args.length == 0) {
            // Log that the number of args were incorrect
            printHelp();
        }

        try {
            cli = parser.parse(options, args);
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing command-line arguments", e);
        }

        if (cli.hasOption(SHORT_HELP)) {
            printHelp();
        }

        ReportArguments reportArguments = new ReportArguments();
        initialiseArguments(reportArguments);
        return reportArguments;
    }

    private void initialiseArguments(ReportArguments reportArguments) {
        reportArguments.setRootPath(getRootPath());
        reportArguments.setTitle(getTitle());
        reportArguments.setFileType(getFileType());
        reportArguments.setLineCountLimit(getLineCountLimit());
        reportArguments.setOutputFilename(getOutputFilename());
    }

    private void printHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("Line Count Reporter Usage:", options);
        jvmKiller.exit();
    }

    public Path getRootPath() {
        return Path.of(cli.getOptionValue(SHORT_ROOT_PATH));
    }

    public String getTitle() {
        return cli.getOptionValue(SHORT_TITLE, config.getProperty(TITLE, AppProperty.TITLE.getDefaultValue()));
    }

    public String getFileType() {
        return cli.getOptionValue(SHORT_FILE_TYPE, config.getProperty(FILE_TYPE, AppProperty.FILE_TYPE.getDefaultValue()));
    }

    public String getLineCountLimit() {
        return cli.getOptionValue(SHORT_LINE_COUNT_LIMIT, config.getProperty(LINE_COUNT_LIMIT, AppProperty.LINE_COUNT_LIMIT.getDefaultValue()));
    }

    public String getOutputFilename() {
        return cli.getOptionValue(SHORT_OUTPUT_FILENAME, config.getProperty(OUTPUT_FILENAME, AppProperty.OUTPUT_FILENAME.getDefaultValue()));
    }

    @VisibleForTesting
    protected void setJvmKiller(JvmKiller jvmKiller) {
        this.jvmKiller = jvmKiller;
    }
}
