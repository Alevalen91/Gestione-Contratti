package gestionale;

public class LoginException extends Exception {
	private static final long serialVersionUID = 1L;

	public LoginException() {
		super("Login error");
	}
	
	public String toString() {
		return getMessage() + ": username o password errata!";
	}

}