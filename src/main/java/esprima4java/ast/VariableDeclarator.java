package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	children.add(id());
	if (init() != null)
	    children.add(init());
	return children;
    }

    @Override
    public Node clone() {
	return new AutoValue_VariableDeclarator(type(), id().clone(), init().clone());
    }
}
