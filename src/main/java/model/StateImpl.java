package model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Arrays;

import static model.StateConst.ENTITIES_AMOUNT;

public class StateImpl implements State {

    private final int value;

    public StateImpl(int value) {
        this.value = value;
    }

    public StateImpl(byte[] array) {
        this.value = byteArrayToInt(array);
    }

    public int getAsInt() {
        return value;
    }

    public String getAsString() {
        return Arrays.toString(getAsByteArray());
    }

    public byte[] getAsByteArray() {
        byte[] result = new byte[ENTITIES_AMOUNT];

        for (int i = 0; i < result.length; i++) {
            int index = result.length - (i + 1);
            byte byteValue = (byte) ((value >> i) & 1);

            result[index] = byteValue;
        }

        return result;
    }

    @Override
    public State applyAction(State action) {
        int result = this.value ^ action.getAsInt();
        return new StateImpl(result);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        StateImpl state = (StateImpl) o;

        return new EqualsBuilder().append(value, state.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(value).toHashCode();
    }

    @Override
    public String toString() {
        return getAsString();
    }

    private static int byteArrayToInt(byte[] array) {
        int result = 0;

        for (byte b : array) {
            result = (result << 1) | b;
        }

        return result;
    }
}
