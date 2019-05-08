package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.ThrowStatement;

public class ThrowStatementDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.THROW_STATEMENT;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	Node expression = Esprima2Java.deserialize(json.get("expression"));
	return ThrowStatement.create(expression);
    }

}
