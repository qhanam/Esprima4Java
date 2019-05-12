package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;

@AutoValue
public abstract class ClassBody extends Node {
    public static ClassBody create(List<MethodDefinition> body) {
	return new AutoValue_ClassBody(NodeType.CLASS_BODY, body);
    }

    public abstract List<MethodDefinition> body();

    @Override
    @Memoized
    public List<Node> getChildren() {
	List<Node> children = new ArrayList<>(body());
	return children;
    }

    @Override
    public Node clone() {
	List<MethodDefinition> childrenCopy = new ArrayList<>();
	childrenCopy.forEach(child -> childrenCopy.add((MethodDefinition) child.clone()));
	return new AutoValue_ClassBody(type(), childrenCopy);
    }
}
