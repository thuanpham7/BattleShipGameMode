package battleship;

public enum AILevel {

	RANDOM_MODE ("random"),
	CHEAT_MODE ("cheat"),
	INTELLIGENT_MODE("intelligent");
	private final String myMode;
	private AILevel(String mode) {
		myMode = mode;
	}
	public String getModeType() {
		return myMode;
	}
}
