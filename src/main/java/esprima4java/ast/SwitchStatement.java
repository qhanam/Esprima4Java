package esprima4java.ast;

import java.util.List;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SwitchStatement extends Node {
    public static SwitchStatement create(Node discriminant, List<SwitchCase> cases) {
	return new AutoValue_SwitchStatement(NodeType.SWITCH_STATEMENT, discriminant, cases);
    }

    public abstract Node discriminant();

    public abstract List<SwitchCase> cases();
}
