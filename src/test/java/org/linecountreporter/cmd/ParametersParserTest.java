package org.linecountreporter.cmd;

import org.junit.jupiter.api.Test;
import org.linecountreporter.report.model.ReportArguments;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.linecountreporter.cmd.DefaultCliValues.SHORT_ROOT_PATH;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class ParametersParserTest {
    @Test
    public void given_valid_required_arg_create_and_initialize_report_args_object() {
        ParametersParser parser = new ParametersParser();
        parser.loadOptions();
        String[] requiredArgs = {
            "-" + SHORT_ROOT_PATH, "root-directory-path-name"
        };
        ReportArguments reportArguments = parser.parseArguments(requiredArgs);
        Path actual = reportArguments.getRootPath();
        Path expected = Path.of("root-directory-path-name");
        assertEquals(expected, actual);
    }

    @Test
    public void given_invalid_required_arg_then_exception_thrown() {
        ParametersParser parser = new ParametersParser();
        parser.loadOptions();
        String[] requiredArgs = {
            "-bad-arg", "root-directory-path-name"
        };
        Exception exception = assertThrows(RuntimeException.class, () -> parser.parseArguments(requiredArgs));
        assertEquals("Error parsing command-line arguments", exception.getMessage());
    }

    @Test
    public void when_zero_args_provided_then_jvm_is_killed() {
        ParametersParser parser = new ParametersParser();
        JvmKiller jvmKiller = mock(JvmKiller.class);
        String exceptionMessage = "JvmKiller.exit() was called";
        doThrow(new RuntimeException(exceptionMessage))
            .when(jvmKiller)
                .exit();

        parser.setJvmKiller(jvmKiller);

        parser.loadOptions();
        Exception exception = assertThrows(RuntimeException.class, () -> parser.parseArguments(new String[]{}));
        assertEquals(exceptionMessage, exception.getMessage());
    }
}