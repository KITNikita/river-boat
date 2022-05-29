package path;

import model.State;

import java.util.List;

public interface PathFinder {

    List<Path> find(State inputState) throws PathFinderException;
}
