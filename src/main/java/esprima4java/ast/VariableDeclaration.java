package esprima4java.ast;

import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class VariableDeclaration extends Node {
    public static VariableDeclaration create(List<VariableDeclarator> declarations) {
	return new AutoValue_VariableDeclaration(NodeType.VARIABLE_DECLARATION, declarations,
		Kind.VAR);
    }

    public enum Kind {
	VAR
    }

    public abstract List<VariableDeclarator> declarations();

    public abstract Kind kind();
}
