package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class RegEx {
    public static RegEx create(String pattern, String flags) {
	return new AutoValue_RegEx(pattern, flags);
    }

    public static RegEx create() {
	return new AutoValue_RegEx("", "");
    }

    public abstract String pattern();

    public abstract String flags();
}
