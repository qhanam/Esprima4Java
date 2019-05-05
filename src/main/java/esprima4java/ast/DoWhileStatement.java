package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class DoWhileStatement extends Node {
    public static DoWhileStatement create(Node test, Node body) {
	return new AutoValue_DoWhileStatement(NodeType.DO_WHILE_STATEMENT, test, body);
    }

    public abstract Node test();

    public abstract Node body();
}
