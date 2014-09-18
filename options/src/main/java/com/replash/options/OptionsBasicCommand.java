package com.replash.options;

import com.replash.CommandWithHelp;
import org.apache.commons.cli.Options;

public interface OptionsBasicCommand extends CommandWithHelp {
    Options getOptions();
}
