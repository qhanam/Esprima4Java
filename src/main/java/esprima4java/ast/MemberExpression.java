package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class MemberExpression extends Node {
    public static MemberExpression create(Node object, Node property, boolean computed) {
	return new AutoValue_MemberExpression(NodeType.MEMBER_EXPRESSION, object, property,
		computed);
    }

    public abstract Node object();

    public abstract Node property();

    public abstract boolean computed();

}