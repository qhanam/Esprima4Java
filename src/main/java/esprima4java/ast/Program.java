package esprima4java.ast;

import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Program extends Node {
    public static Program create(List<Node> body) {
	return new AutoValue_Program(NodeType.PROGRAM, body);
    }

    public abstract List<Node> body();
}
