package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class FunctionDeclaration extends Function {
    public static FunctionDeclaration create(boolean generator, boolean async, Identifier id,
	    List<Identifier> params, BlockStatement body) {
	return new AutoValue_FunctionDeclaration(NodeType.FUNCTION_DECLARATION, generator, async,
		id, params, body);
    }

    public abstract Identifier id();

    public abstract List<Identifier> params();

    public abstract BlockStatement body();

    @Override
    public List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	children.add(id());
	children.addAll(params());
	children.add(body());
	return children;
    }

}
