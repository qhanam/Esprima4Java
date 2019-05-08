package esprima4java.ast;

import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ContinueStatement extends Node {
    public static ContinueStatement create(Identifier label) {
	return new AutoValue_ContinueStatement(NodeType.CONTINUE_STATEMENT, label);
    }

    @Nullable
    public abstract Identifier label();

    @Override
    protected List<Node> getChildren() {
	if (label() != null)
	    return Collections.singletonList(label());
	return Collections.emptyList();
    }

}
