package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class EmptyStatement extends AstNode {
    static EmptyStatement create(String name) {
	return new AutoValue_EmptyStatement(NodeType.EMPTY_STATEMENT, name);
    }

    abstract String name();
}
