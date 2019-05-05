package esprima4java.ast;

import org.eclipse.jdt.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class TryStatement extends Node {
    public static TryStatement create(BlockStatement block, CatchClause handler,
	    BlockStatement finalizer) {
	return new AutoValue_TryStatement(NodeType.TRY_STATEMENT, block, handler, finalizer);
    }

    public abstract BlockStatement block();

    @Nullable
    public abstract CatchClause handler();

    @Nullable
    public abstract BlockStatement finalizer();
}
