package co.runtime.exceptions;

/**
 * Class ...
 * <p/>
 * Creation: 5/26/14, 6:15 PM.
 */
public class UnsetParameterException extends Exception {
  
	private static final long serialVersionUID = 1L;

	public UnsetParameterException(String s) {
        super(s);
    }
}
