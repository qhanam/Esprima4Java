package esprima4java.ast;

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
