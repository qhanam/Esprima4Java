package esprima4java.ast;

import com.google.auto.value.AutoValue;
import com.google.gson.JsonObject;

import esprima4java.DeserializationException;

@AutoValue
public abstract class Identifier extends AstNode {
    public static Identifier create(String name) {
	return new AutoValue_Identifier(NodeType.IDENTIFIER, name);
    }

    public abstract String name();

    public static Identifier deserialize(JsonObject json) throws DeserializationException {
	if (!json.get("type").getAsString().equals("Identifier")) {
	    throw new DeserializationException("Node type must be `Identifier`.");
	}
	return Identifier.create(json.get("name").getAsString());
    }

    @Override
    public boolean equals(Object o) {
	if (!(o instanceof Identifier))
	    return false;
	Identifier that = (Identifier) o;
	return this.name().equals(that.name());
    }

}
