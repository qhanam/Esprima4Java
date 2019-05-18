package esprima4java.abstractstate;

public class AnalysisState {

    private BuiltinState builtinState;

    private UserState[] userStates;

    private AnalysisState(BuiltinState builtinState, UserState[] userStates) {
	this.builtinState = builtinState;
	this.userStates = userStates;
    }

    public AnalysisState join(AnalysisState that) {
	BuiltinState newBuiltinState = this.builtinState.join(that.builtinState);
	UserState[] newUserStates = new UserState[userStates.length];
	for (int i = 0; i < userStates.length; i++) {
	    newUserStates[i] = this.userStates[i].join(that.userStates[i]);
	}
	return new AnalysisState(newBuiltinState, newUserStates);
    }

    public BuiltinState getBuiltinState() {
	return builtinState;
    }

    public boolean equivalentTo(AnalysisState that) {
	if (!this.builtinState.equivalentTo(that.builtinState))
	    return false;
	if (this.userStates.length != that.userStates.length)
	    return false;
	for (int i = 0; i < this.userStates.length; i++) {
	    if (!this.userStates[i].equivalentTo(that.userStates[i]))
		return false;
	}
	return true;
    }

    /**
     * Returns an initialized analysis state.
     * 
     * @param builtinState
     *            the state of the built-in control flow/data flow analysis.
     * @param userState
     *            the state of the user-specified flow analysis.
     */
    public static AnalysisState initializeAnalysisState(BuiltinState builtinState,
	    UserState[] userState) {
	return new AnalysisState(builtinState, userState);
    }

}
