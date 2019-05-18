package esprima4java.abstractstate;

/**
 * The state of an abstract domain.
 * 
 * @param <T>
 */
public interface AbstractState<T extends AbstractState<T>> {

    /**
     * Joins two {@code AbstractState}s.
     * 
     * @return a new AbstractState, which is the join of this state and that state.
     */
    abstract T join(T that);

    /**
     * Checks if two {@code AbstractState}s are equivalent.
     * 
     * @return {@code true} if this state and that state are equivalent,
     *         {@code false} otherwise.
     */
    abstract boolean equivalentTo(T that);
}
