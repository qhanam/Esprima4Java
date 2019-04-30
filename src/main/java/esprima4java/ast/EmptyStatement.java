package esprima4java.ast;

import com.google.auto.value.AutoValue;
import com.google.gson.JsonObject;

import esprima4java.DeserializationException;

@AutoValue
public abstract class EmptyStatement extends AstNode {
    public static EmptyStatement create() {
	return new AutoValue_EmptyStatement(NodeType.EMPTY_STATEMENT);
    }

    public static EmptyStatement deserialize(JsonObject jo) throws DeserializationException {
	if (!jo.get("type").getAsString().equals("EmptyStatement")) {
	    throw new DeserializationException("JSON object must have `EmptyStatement` type.");
	}
	return EmptyStatement.create();
    }
}
