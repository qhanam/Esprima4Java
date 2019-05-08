package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;

@AutoValue
public abstract class CatchClause extends Node {
    public static CatchClause create(Node param, BlockStatement body) {
	return new AutoValue_CatchClause(NodeType.CATCH_CLAUSE, param, body);
    }

    @Nullable
    public abstract Node param();

    public abstract BlockStatement body();

    @Override
    @Memoized
    protected List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	if (param() != null)
	    children.add(param());
	children.add(body());
	return children;
    }

}
