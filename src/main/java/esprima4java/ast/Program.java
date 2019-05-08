package esprima4java.ast;

import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Program extends Node {
    public static Program create(SourceType sourceType, List<Node> body) {
	return new AutoValue_Program(NodeType.PROGRAM, sourceType, body);
    }

    public enum SourceType {
	SCRIPT("script"), MODULE("module");

	String type;

	SourceType(String type) {
	    this.type = type;
	}

	public static SourceType deserialize(String serial) {
	    for (SourceType type : SourceType.values()) {
		if (type.toString().equals(serial))
		    return type;
	    }
	    return null;
	}

	@Override
	public String toString() {
	    return type;
	}
    }

    public abstract SourceType sourceType();

    public abstract List<Node> body();

    @Override
    protected List<Node> getChildren() {
	return body();
    }

}
