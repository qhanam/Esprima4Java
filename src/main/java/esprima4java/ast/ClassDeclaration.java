package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ClassDeclaration extends Node {
    public static ClassDeclaration create(Identifier id, Node superClass, ClassBody body) {
	return new AutoValue_ClassDeclaration(NodeType.CLASS_DECLARATION, id, superClass, body);
    }

    public abstract Identifier id();

    @Nullable
    public abstract Node superClass();

    public abstract ClassBody body();

    @Override
    public List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	children.add(id());
	if (superClass() != null)
	    children.add(superClass());
	children.add(body());
	return children;
    }

}
