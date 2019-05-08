package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.ThisExpression;

public class ThisExpressionDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.THIS_EXPRESSION;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	return ThisExpression.create();
    }

}
