package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.Identifier;
import esprima4java.ast.LabeledStatement;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class LabeledStatementDeserializer implements NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.LABELED_STATEMENT;
    }

    @Override
    public Node deserialize(JsonObject json) throws DeserializationException {
	return LabeledStatement.create(
		(Identifier) Esprima2Java.deserialize((JsonObject) json.get("label")),
		Esprima2Java.deserialize((JsonObject) json.get("body")));
    }

}
