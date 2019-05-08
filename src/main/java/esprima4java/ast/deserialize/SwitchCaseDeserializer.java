package esprima4java.ast.deserialize;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import esprima4java.Esprima2Java;
import esprima4java.ast.Node;
import esprima4java.ast.NodeType;
import esprima4java.ast.SwitchCase;

public class SwitchCaseDeserializer extends NodeDeserializer {

    @Override
    public NodeType getSupportedType() {
	return NodeType.SWITCH_CASE;
    }

    @Override
    public Node deserializePartial(JsonObject json) throws DeserializationException {

	List<Node> consequent = new ArrayList<>();
	for (JsonElement je : json.get("consequent").getAsJsonArray()) {
	    consequent.add(Esprima2Java.deserialize((JsonObject) je));
	}

	if (json.has("test")) {
	    Node test = Esprima2Java.deserialize((JsonObject) json.get("test"));
	    return SwitchCase.create(test, consequent);
	} else {
	    return SwitchCase.create(consequent);
	}

    }

}
