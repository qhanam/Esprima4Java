package esprima4java.abstractstate;

/**
 * An abstract state and abstract interpreter for built-in abstract domains.
 * 
 * This interpreter performs the underlying control and data flow analysis
 * needed by checkers. The results of this analysis (ie. control flow, variable
 * dependencies, data dependencies and type state) are both made available to
 * injected checkers.
 */
public class BuiltinState implements AbstractState<BuiltinState> {

    /**
     * Return a new analysis state, which is the join of {@code this} state and
     * {@code that} state.
     * 
     * @param that
     *            The AnalysisState to join with this AnalysisState.
     */
    @Override
    public BuiltinState join(BuiltinState that) {
	throw new UnsupportedOperationException();
    }

    /**
     * Returns true if this state is equivalent to that state.
     */
    @Override
    public boolean equivalentTo(BuiltinState that) {
	throw new UnsupportedOperationException();
    }

}
