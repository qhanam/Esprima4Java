package esprima4java.ast;

import org.eclipse.jdt.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ClassExpression extends Node {
    public static ClassExpression create(Identifier id, Node superClass, ClassBody body) {
	return new AutoValue_ClassExpression(NodeType.CLASS_EXPRESSION, id, superClass, body);
    }

    @Nullable
    public abstract Identifier id();

    @Nullable
    public abstract Node superClass();

    public abstract ClassBody body();

}
