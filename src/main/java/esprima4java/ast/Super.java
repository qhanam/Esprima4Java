package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Super extends Node {
    public static Super create() {
	return new AutoValue_Super(NodeType.SUPER);
    }

    @Override
    public Node clone() {
	return new AutoValue_Super(type());
    }
}
