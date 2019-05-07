package esprima4java.ast;

import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ClassBody extends Node {
    public static ClassBody create(List<MethodDefinition> body) {
	return new AutoValue_ClassBody(NodeType.CLASS_BODY, body);
    }

    public abstract List<MethodDefinition> body();
}
