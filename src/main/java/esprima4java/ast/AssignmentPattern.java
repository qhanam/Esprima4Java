package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;

@AutoValue
public abstract class AssignmentPattern extends Node {
    public static AssignmentPattern create(Node left, Node right) {
	return new AutoValue_AssignmentPattern(NodeType.ASSIGNMENT_PATTERN, left, right);
    }

    public abstract Node left();

    public abstract Node right();

    @Override
    @Memoized
    public List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	children.add(left());
	children.add(right());
	return children;
    }

}
