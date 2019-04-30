package esprima4java.ast;

import com.google.gson.JsonObject;

import esprima4java.DeserializationException;

public class Identifier extends AstNode {

    private String name;

    private Identifier() {
	super(NodeType.IDENTIFIER);
    }

    public String getName() {
	return name;
    }

    public static Builder builder() {
	return new Builder();
    }

    public static Identifier deserialize(JsonObject json) throws DeserializationException {
	if (!json.get("type").getAsString().equals("Identifier") || !json.has("name")) {
	    throw new DeserializationException("Identifier type was not well formed.");
	}
	return Identifier.builder().name(json.get("name").getAsString()).build();
    }

    @Override
    public boolean equals(Object o) {
	if (!(o instanceof Identifier))
	    return false;
	Identifier that = (Identifier) o;
	return this.name.equals(that.name);
    }

    public static class Builder {

	private String name;

	public Identifier build() {
	    Identifier identifier = new Identifier();
	    identifier.name = name;
	    return identifier;
	}

	public Builder name(String name) {
	    this.name = name;
	    return this;
	}

    }

}
