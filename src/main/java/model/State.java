package model;

public interface State {

    int getAsInt();

    String getAsString();

    byte[] getAsByteArray();

    State applyAction(State action);
}
