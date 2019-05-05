package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ThisExpression extends Node {
    public static ThisExpression create() {
	return new AutoValue_ThisExpression(NodeType.THIS_EXPRESSION);
    }
}
