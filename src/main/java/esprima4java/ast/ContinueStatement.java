package esprima4java.ast;

import org.eclipse.jdt.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ContinueStatement extends Node {
    public static ContinueStatement create(Identifier label) {
	return new AutoValue_ContinueStatement(NodeType.CONTINUE_STATEMENT, label);
    }

    @Nullable
    public abstract Identifier label();
}
