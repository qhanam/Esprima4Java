package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.MemberExpression;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class MemberExpressionDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.MEMBER_EXPRESSION;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	Node object = Esprima2Java.deserialize(json.get("object"));
	Node property = Esprima2Java.deserialize(json.get("property"));
	boolean computed = json.get("computed").getAsBoolean();
	return MemberExpression.create(object, property, computed);
    }

}
