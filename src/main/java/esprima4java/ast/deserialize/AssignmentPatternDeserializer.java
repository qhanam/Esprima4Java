package esprima4java.ast.deserialize;

import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.AssignmentPattern;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;

public class AssignmentPatternDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.ASSIGNMENT_PATTERN;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {
	Node left = Esprima2Java.deserialize(json.get("left"));
	Node right = Esprima2Java.deserialize(json.get("right"));
	return AssignmentPattern.create(left, right);
    }

}
