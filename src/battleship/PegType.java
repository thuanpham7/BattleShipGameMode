package battleship;

public enum PegType 
{
	RED ("Red"),
	WHITE ("White"),
	NONE ("None");

	private final String pegType;

	private PegType(String type)
	{
		pegType = type;
	}

	public String getPegType()
	{
		return pegType;
	}
	

}