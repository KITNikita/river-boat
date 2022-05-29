package path;

import model.State;

import java.util.Deque;

public interface Path {

    void add(Element element);

    Deque<Element> getElements();

    interface Element {

        State getState();

        State getAction();

        State applyActionAndGetState();
    }
}
