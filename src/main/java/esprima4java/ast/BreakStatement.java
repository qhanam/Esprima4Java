package esprima4java.ast;

import org.eclipse.jdt.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class BreakStatement extends Node {
    public static BreakStatement create(Identifier label) {
	return new AutoValue_BreakStatement(NodeType.BREAK_STATEMENT, label);
    }

    @Nullable
    public abstract Identifier label();
}
