package esprima4java.ast;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class TemplateValue {
    public static TemplateValue create(String cooked, String raw) {
	return new AutoValue_TemplateValue(cooked, raw);
    }

    public abstract String cooked();

    public abstract String raw();
}
