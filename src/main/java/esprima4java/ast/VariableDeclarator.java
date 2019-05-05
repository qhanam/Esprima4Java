package esprima4java.ast;

import org.eclipse.jdt.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class VariableDeclarator extends Node {
    public static VariableDeclarator create(Node id, Node init) {
	return new AutoValue_VariableDeclarator(NodeType.VARIABLE_DECLARATOR, id, init);
    }

    public abstract Node id();

    @Nullable
    public abstract Node init();
}
