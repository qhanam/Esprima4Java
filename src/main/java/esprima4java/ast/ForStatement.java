package esprima4java.ast;

import org.eclipse.jdt.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ForStatement extends Node {
    public static ForStatement create(Node init, Node test, Node update, Node body) {
	return new AutoValue_ForStatement(NodeType.FOR_STATEMENT, init, test, update, body);
    }

    @Nullable
    public abstract Node init();

    @Nullable
    public abstract Node test();

    @Nullable
    public abstract Node update();

    public abstract Node body();
}
