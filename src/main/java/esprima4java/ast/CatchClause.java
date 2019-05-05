package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class CatchClause extends Node {
    public static CatchClause create(Node param, BlockStatement body) {
	return new AutoValue_CatchClause(NodeType.CATCH_CLAUSE, param, body);
    }

    public abstract Node param();

    public abstract BlockStatement body();
}
