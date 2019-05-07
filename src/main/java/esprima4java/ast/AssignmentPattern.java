package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class AssignmentPattern extends Node {
    public static AssignmentPattern create(Node left, Node right) {
	return new AutoValue_AssignmentPattern(NodeType.ASSIGNMENT_PATTERN, left, right);
    }

    public abstract Node left();

    public abstract Node right();
}
