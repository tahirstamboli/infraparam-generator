package com.reantest.infraparam;

import java.util.Map;

import com.reantest.infraparam.core.CommandLineParser;
import com.reantest.infraparam.core.InfraParamJsonGenerator;
import com.reantest.infraparam.core.KeyValueCommandLineParser;
import com.reantest.infraparam.models.CmdLineArguments;
import com.reantest.infraparam.utils.Utils;

/**
 * @author tahir
 *
 */
public class InfraParamMain {

	public static void main(String args[]) {
		try {
			if (args != null && args.length > 0) {

				// Parse command line arguments
				CommandLineParser<Map<String, String>> keyValueParser = new KeyValueCommandLineParser();
				Map<String, String> arguments = keyValueParser.parse(args);

				// Validate passed arguments
				Utils.validateArguments(arguments);

				// Generate infraParam.json
				InfraParamJsonGenerator.generateInfraParamJson(arguments.get(CmdLineArguments.TF_STATE_PATH.value()),
						arguments.get(CmdLineArguments.INFRAPARAM_OUTPUT_PATH.value()));
			} else {
				System.err.println("Error: Invalid command.");
				Utils.example();
				System.exit(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("");
			Utils.example();
			System.exit(1);
		}
	}

}
