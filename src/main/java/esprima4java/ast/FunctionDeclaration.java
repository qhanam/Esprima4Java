package esprima4java.ast;

import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class FunctionDeclaration extends Function {
    public static FunctionDeclaration create(boolean generator, Identifier id,
	    List<Identifier> params, BlockStatement body) {
	return new AutoValue_FunctionDeclaration(NodeType.FUNCTION_DECLARATION, generator, id,
		params, body);
    }

    public abstract Identifier id();

    public abstract List<Identifier> params();

    public abstract BlockStatement body();
}
