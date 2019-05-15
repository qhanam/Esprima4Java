package esprima4java.ast;

import java.util.ArrayList;
import java.util.List;

import com.google.auto.value.AutoValue;

import esprima4java.cfg.Cfg;
import esprima4java.cfg.builders.CfgBuilderForSwitchStatements;

@AutoValue
public abstract class SwitchStatement extends Node {
    public static SwitchStatement create(Node discriminant, List<SwitchCase> cases) {
	return new AutoValue_SwitchStatement(NodeType.SWITCH_STATEMENT, discriminant, cases);
    }

    public abstract Node discriminant();

    public abstract List<SwitchCase> cases();

    @Override
    public List<Node> getChildren() {
	List<Node> children = new ArrayList<>();
	children.add(discriminant());
	children.addAll(cases());
	return children;
    }

    @Override
    public Cfg buildCfg() {
	return CfgBuilderForSwitchStatements.build(this);
    }

    @Override
    public Node clone() {
	List<SwitchCase> casesCopy = new ArrayList<>();
	cases().forEach(switchCase -> casesCopy.add((SwitchCase) switchCase.clone()));
	return new AutoValue_SwitchStatement(type(), discriminant().clone(), casesCopy);
    }

}
