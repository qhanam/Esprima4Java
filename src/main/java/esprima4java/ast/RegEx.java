package esprima4java.ast;

public class RegEx {

    private String pattern;
    private String flags;

    private RegEx() {
    }

    public String getPattern() {
	return pattern;
    }

    public String getFlags() {
	return flags;
    }

    public static Builder builder() {
	return new Builder();
    }

    @Override
    public boolean equals(Object o) {
	if (!(o instanceof RegEx))
	    return false;
	RegEx that = (RegEx) o;
	return this.pattern.equals(that.pattern) && this.flags.equals(that.flags);
    }

    public static class Builder {

	private String pattern;
	private String flags;

	private Builder() {
	    this.pattern = "";
	    this.flags = "";
	}

	public RegEx build() {
	    RegEx regex = new RegEx();
	    regex.pattern = this.pattern;
	    regex.flags = this.flags;
	    return regex;
	}

	public Builder pattern(String pattern) {
	    this.pattern = pattern;
	    return this;
	}

	public Builder flags(String flags) {
	    this.flags = flags;
	    return this;
	}

    }
}
