package esprima4java.ast;

import java.util.Collections;
import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ThrowStatement extends Node {
    public static ThrowStatement create(Node expression) {
	return new AutoValue_ThrowStatement(NodeType.THROW_STATEMENT, expression);
    }

    abstract Node expression();

    @Override
    public List<Node> getChildren() {
	return Collections.singletonList(expression());
    }

}
