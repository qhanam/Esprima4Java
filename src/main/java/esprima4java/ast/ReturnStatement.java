package esprima4java.ast;

import org.eclipse.jdt.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ReturnStatement extends Node {
    public static ReturnStatement create() {
	return new AutoValue_ReturnStatement(NodeType.RETURN_STATEMENT, null);
    }

    public static ReturnStatement create(Node argument) {
	return new AutoValue_ReturnStatement(NodeType.RETURN_STATEMENT, argument);
    }

    @Nullable
    public abstract Node argument();
}
