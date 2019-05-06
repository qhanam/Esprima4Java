package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Property extends Node {
    public static Property create(Node key, Node value, Kind kind, boolean method,
	    boolean shorthand, boolean computed) {
	return new AutoValue_Property(NodeType.PROPERTY, key, value, kind, method, shorthand,
		computed);
    }

    public enum Kind {
	INIT, GET, SET
    }

    public abstract Node key();

    public abstract Node value();

    public abstract Kind kind();

    public abstract boolean method();

    public abstract boolean shorthand();

    public abstract boolean computed();
}
