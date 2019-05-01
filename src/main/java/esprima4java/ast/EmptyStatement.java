package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class EmptyStatement extends Node {
    public static EmptyStatement create() {
	return new AutoValue_EmptyStatement(NodeType.EMPTY_STATEMENT);
    }
}
