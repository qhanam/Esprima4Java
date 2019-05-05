package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ForInStatement extends Node {
    public static ForInStatement create(Node left, Node right, Node body) {
	return new AutoValue_ForInStatement(NodeType.FOR_IN_STATEMENT, left, right, body);
    }

    public abstract Node left();

    public abstract Node right();

    public abstract Node body();
}
