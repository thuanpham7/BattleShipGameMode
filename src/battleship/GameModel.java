package battleship;

public class GameModel {
	private ShipBoard myShipBoard;
	private ShipBoard myComputerBoard;
	private String myPlayerName;
	private boolean myIsComputerTurn;
	private String myMessage;
	private AILevel myAILevel;
	
	public GameModel(String name, AILevel level) {
	}
	
	public void beginGame() {
	}
	
	public boolean checkIfWinner() {
		return true;
	}
	
	public String generateMessage() {
		return myMessage;
	}
	
	public void setPlayerName(String name) {
		myPlayerName = name;
	}
	
	public String getPlayerName() {
		return myPlayerName;
	}
	
	public boolean ComputerTurn() {
		return myIsComputerTurn;
	}
	
	public ShipBoard myShip() {
		return myShipBoard;
	}
	
	public ShipBoard computerBoard() {
		return myComputerBoard;
	}
	
	public AILevel mode() {
		return myAILevel;
	}
	
}
