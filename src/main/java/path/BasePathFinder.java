package path;

import model.State;
import model.StateImpl;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static model.StateConst.*;

public class BasePathFinder implements PathFinder {

    private static final Predicate<byte[]> WOLF_COAT_RULE = (position) ->
            position[BOAT] != position[WOLF] && position[WOLF] == position[GOAT];

    private static final Predicate<byte[]> GOAT_CABBAGE_RULE = (position) ->
            position[BOAT] != position[GOAT] && position[GOAT] == position[CABBAGE];

    private final List<State> failStates;
    private final List<State> actions;
    private final State goalState;
    private final Set<State> visitedStates;

    public BasePathFinder() {
        List<State> states = allStates();
        this.actions = allActions();
        this.failStates = filterFailStates( states );
        this.goalState = states.get( states.size() - 1 );
        this.visitedStates = new HashSet<>();
    }

    @Override
    public List<Path> find(State inputState) {
        List<Path> pathList = new ArrayList<>();

        if (failStates.contains( inputState )) {
            System.out.println( "Input " + inputState + "  leads to defeat according to known rules" );
            return pathList;
        }

        Deque<Path> queue = new LinkedList<>();
        Path path = new PathImpl( new PathImpl.PathElementImpl( inputState, null ) );
        queue.add( path );

        while (!queue.isEmpty()) {
            path = queue.pop();
            Path.Element element = path.getElements().peekLast();
            if (element != null && element.applyActionAndGetState().equals( goalState )) {
                pathList.add( path );
            } else {
                assert element != null;
                State current = element.applyActionAndGetState();
                visitedStates.add( current );
                List<State> filterActions = filterActions( current, actions );
                for (State action : filterActions) {
                    State state = current.applyAction( action );
                    if (!visitedStates.contains( state ) && !failStates.contains( state )) {
                        Path aNew = new PathImpl( path.getElements() );
                        aNew.add( new PathImpl.PathElementImpl( current, action ) );
                        queue.add( aNew );
                    }
                }
            }
        }

        return pathList;
    }

    private static List<State> allStates() {
        List<State> stateList = new ArrayList<>();

        int max = (int) Math.pow( 2, ENTITIES_AMOUNT );
        for (int i = 0; i < max; i++) {
            State state = new StateImpl( i );
            stateList.add( state );
        }

        return stateList;
    }

    private static List<State> filterFailStates(List<State> states) {
        return states.stream()
                .filter( state ->
                        WOLF_COAT_RULE.test( state.getAsByteArray() ) ||
                                GOAT_CABBAGE_RULE.test( state.getAsByteArray() )
                )
                .collect( Collectors.toList() );
    }

    private static List<State> allActions() {
        List<State> actionList = new ArrayList<>();

        State onlyBoat = new StateImpl( 0b1000 );
        State boatAndWolf = new StateImpl( 0b1100 );
        State boatAndGoat = new StateImpl( 0b1010 );
        State boatAndCabbage = new StateImpl( 0b1001 );

        actionList.add( onlyBoat );
        actionList.add( boatAndWolf );
        actionList.add( boatAndGoat );
        actionList.add( boatAndCabbage );

        return actionList;
    }

    private List<State> filterActions(State state, List<State> actions) {
        final byte boatSide = state.getAsByteArray()[BOAT];
        int positionMask = createPositionMask( boatSide, state );

        return actions.stream()
                .map( action ->
                        new StateImpl( action.getAsInt() & positionMask ) )
                .distinct()
                .filter( action ->
                        !this.failStates.contains( state.applyAction( action ) ) &&
                                !this.visitedStates.contains( state.applyAction( action ) )

                )
                .collect( Collectors.toList() );
    }

    private static int createPositionMask(byte boatSide, State state) {
        byte[] mask = new byte[ENTITIES_AMOUNT];
        byte[] stateArray = state.getAsByteArray();

        for (int i = 0; i < mask.length; i++) {
            mask[i] = (byte) (stateArray[i] == boatSide ? 1 : 0);
        }

        return new StateImpl( mask ).getAsInt();
    }
}
