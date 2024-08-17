package homework3;

public enum Colors {
	// my colors
	DARKERPURPLE("\033[38;5;153m"), OG("\033[0m"), LIGHTERPURPLE("\033[38;5;183m");
	// stores my colors
	private final String color;
	// constructor for the colors
	Colors(String color) {
		this.color = color;
	}
	// method to grab the color
	public String getColor() {
		return color;
	}

}