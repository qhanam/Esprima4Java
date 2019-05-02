package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class WithStatement extends Node {
    public static WithStatement create(Node object, Node body) {
	return new AutoValue_WithStatement(NodeType.WITH_STATEMENT, object, body);
    }

    public abstract Node object();

    public abstract Node body();
}
