package input;

import model.State;

public interface InputReader {

    State read(String[] arguments) throws InputReaderException;
}
