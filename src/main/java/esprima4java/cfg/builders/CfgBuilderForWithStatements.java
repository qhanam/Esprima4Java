package esprima4java.cfg.builders;

import esprima4java.ast.WithStatement;
import esprima4java.cfg.Cfg;

/**
 * A builder for creating control flow graphs from With statements.
 * 
 * TODO: The behaviour of the With statement is currently ignored.
 */
public class CfgBuilderForWithStatements {

    public static Cfg build(WithStatement statement) {
	return statement.body().buildCfg();
    }

}
