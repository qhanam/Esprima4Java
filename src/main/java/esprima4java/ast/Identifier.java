package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Identifier extends Node {
    public static Identifier create(String name) {
	return new AutoValue_Identifier(NodeType.IDENTIFIER, name);
    }

    public abstract String name();

}
