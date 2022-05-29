package input;

import model.State;
import model.StateImpl;

import java.util.Arrays;

import static java.lang.String.format;
import static model.StateConst.ENTITIES_AMOUNT;

public class BaseInputReader implements InputReader {

    public State read(String[] arguments) throws InputReaderException {
        State inputState = null;

        if (arguments == null) {
            throw new InputReaderException( "Provided arguments array is null!" );
        }

        if (arguments.length < ENTITIES_AMOUNT || arguments.length > ENTITIES_AMOUNT) {
            String errorMessage = format( "Required %d arguments, but provided %d", ENTITIES_AMOUNT, arguments.length );
            throw new InputReaderException( errorMessage );
        }

        int inputValue = 0;

        try {
            for (String argument : arguments) {
                int argumentValue = Integer.parseInt( argument );

                if (argumentValue < 0 || argumentValue > 1) {
                    throwNotAllowedArgumentsException( arguments );
                }

                inputValue = (inputValue << 1) | argumentValue;
            }
            inputState = new StateImpl( inputValue );
        } catch (NumberFormatException numberFormatException) {
            throwNotAllowedArgumentsException( arguments );
        }

        return inputState;
    }

    private static void throwNotAllowedArgumentsException(String[] arguments) throws InputReaderException {
        String errorMessage = format(
                "Provided %s arguments, allowed values are [0, 1]",
                Arrays.toString( arguments )
        );
        throw new InputReaderException( errorMessage );
    }
}
