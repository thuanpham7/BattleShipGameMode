package battleship;

public enum Shiptype {
	BATTLESHIP ("BattleShip"),
	CRUISER ("Cruiser"),
	CARRIER ("Carrier"),
	SUBMARINE ("Submarine"),
	DESTROYER ("Destroyer");
	private final String myShipType;
	private Shiptype(String shipType) {
		myShipType = shipType;
	}
	public String getShipType() {
		return myShipType;
	}
	
	
}