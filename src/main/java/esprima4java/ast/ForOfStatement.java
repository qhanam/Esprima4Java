package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ForOfStatement extends Node {
    public static ForOfStatement create(Node left, Node right, Node body, boolean await) {
	return new AutoValue_ForOfStatement(NodeType.FOR_OF_STATEMENT, left, right, body, await);
    }

    public abstract Node left();

    public abstract Node right();

    public abstract Node body();

    public abstract boolean await();
}
