package path;

import model.State;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Deque;
import java.util.LinkedList;

public class PathImpl implements Path {

    private final Deque<Element> elements;

    public PathImpl() {
        this.elements = new LinkedList<>();
    }

    public PathImpl(Iterable<Element> elementIterable) {
        this.elements = new LinkedList<>();
        for (Element element : elementIterable) {
            elements.add( element );
        }
    }

    public PathImpl(Element element) {
        this.elements = new LinkedList<>();
        elements.add( element );
    }

    @Override
    public void add(Element element) {
        this.elements.add( element );
    }

    @Override
    public Deque<Element> getElements() {
        return new LinkedList<>( elements );
    }

    @Override
    public String toString() {
        return new ToStringBuilder( this )
                .append( "elements", elements )
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PathImpl path = (PathImpl) o;

        return new EqualsBuilder().append( elements, path.elements ).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder( 17, 37 ).append( elements ).toHashCode();
    }

    public static final class PathElementImpl implements Element {

        private final State state;
        private final State action;

        public PathElementImpl(State state, State action) {
            this.state = state;
            this.action = action;
        }

        @Override
        public State getState() {
            return this.state;
        }

        @Override
        public State getAction() {
            return this.action;
        }

        @Override
        public State applyActionAndGetState() {
            if (this.action == null) {
                return this.state;
            }
            return this.state.applyAction( this.action );
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            PathElementImpl that = (PathElementImpl) o;

            return new EqualsBuilder().append( state, that.state ).append( action, that.action ).isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder( 17, 37 ).append( state ).append( action ).toHashCode();
        }

        @Override
        public String toString() {
            return "(state:" +
                    state.getAsString() +
                    ", action:" +
                    (action == null ? "no action" : action.getAsString()) +
                    ")";
        }
    }
}
