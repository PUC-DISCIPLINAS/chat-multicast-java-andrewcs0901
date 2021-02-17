package util;

public class EAcceptedOptions {

	public static final String LIST_GROUPS = "/list-groups";
	public static final String LIST_USERS = "/list-users";
	public static final String CREATE = "/create";
	public static final String IN = "/in";
	public static final String LEAVE = "/leave";
	public static final String INVALID = "comando invalido";
	
	

	public static String[] values() {
		String[] val = {LIST_GROUPS,LIST_USERS, CREATE, IN, LEAVE};
		return val;
	}
}
