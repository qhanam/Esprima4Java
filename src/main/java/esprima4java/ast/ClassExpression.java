package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ClassExpression extends Node {
    public static ClassExpression create(Identifier id, Node superClass, ClassBody body) {
	return new AutoValue_ClassExpression(NodeType.CLASS_EXPRESSION, id, superClass, body);
    }

    @Nullable
    public abstract Identifier id();

    @Nullable
    public abstract Node superClass();

    public abstract ClassBody body();

    @Override
    public List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	if (id() != null)
	    children.add(id());
	if (superClass() != null)
	    children.add(superClass());
	children.add(body());
	return children;
    }

    public Node clone() {
	Identifier idClone = id() != null ? (Identifier) id().clone() : null;
	Node superClassClone = superClass() != null ? superClass().clone() : null;
	return new AutoValue_ClassExpression(type(), idClone, superClassClone,
		(ClassBody) body().clone());
    }
}
