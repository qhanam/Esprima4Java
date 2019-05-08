package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SwitchCase extends Node {
    public static SwitchCase create(List<Node> consequent) {
	return new AutoValue_SwitchCase(NodeType.SWITCH_CASE, null, consequent);
    }

    public static SwitchCase create(Node test, List<Node> consequent) {
	return new AutoValue_SwitchCase(NodeType.SWITCH_CASE, test, consequent);
    }

    @Nullable
    public abstract Node test();

    public abstract List<Node> consequent();

    @Override
    protected List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	if (test() != null)
	    children.add(test());
	children.addAll(consequent());
	return children;
    }

}
