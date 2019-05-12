package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	children.add(block());
	if (handler() != null)
	    children.add(handler());
	if (finalizer() != null)
	    children.add(finalizer());
	return children;
    }

    @Override
    public Node clone() {
	return new AutoValue_TryStatement(type(), (BlockStatement) block().clone(),
		(CatchClause) handler().clone(), (BlockStatement) finalizer().clone());
    }

}
