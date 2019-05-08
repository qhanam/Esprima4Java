package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class VariableDeclaration extends Node {
    public static VariableDeclaration create(List<VariableDeclarator> declarations, Kind kind) {
	return new AutoValue_VariableDeclaration(NodeType.VARIABLE_DECLARATION, declarations, kind);
    }

    public enum Kind {
	VAR("var"), LET("let"), CONST("const");

	private String kind;

	Kind(String kind) {
	    this.kind = kind;
	}

	public static Kind deserializer(String serial) {
	    for (Kind kind : Kind.values()) {
		if (kind.toString().equals(serial))
		    return kind;
	    }
	    return null;
	}

	@Override
	public String toString() {
	    return kind;
	}
    }

    public abstract List<VariableDeclarator> declarations();

    public abstract Kind kind();

    @Override
    protected List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	children.addAll(declarations());
	return children;
    }

}
