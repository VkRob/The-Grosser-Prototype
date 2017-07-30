package grosser.engine.levels;

public class LevelVariableVector2f extends LevelVariable {

	private int x, y;
	
	public LevelVariableVector2f(String name) {
		super(name);
	}

	public void parse(String s) {

		int num1 = 0;
		int num2 = 0;

		String num1s = "";
		String num2s = "";

		boolean num1Passed = false;
		boolean loadingNum1 = false;
		boolean loadingNum2 = false;

		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != ' ' && s.charAt(i) != ',') {
				if (!loadingNum1 && !num1Passed) {
					loadingNum1 = true;
				} else if (!loadingNum1 && num1Passed) {
					loadingNum2 = true;
				}
			}
			if (loadingNum1) {
				if (s.charAt(i) == ',' || s.charAt(i) == ' ') {
					loadingNum1 = false;
					num1Passed = true;
					continue;
				} else {
					num1s = num1s.concat("" + s.charAt(i));
				}
			}
			if (loadingNum2) {
				if (s.charAt(i) == '\n' || s.charAt(i) == ';') {
					break;
				} else {
					num2s = num2s.concat("" + s.charAt(i));
				}
			}
		}

		num1 = Integer.parseInt(num1s);
		num2 = Integer.parseInt(num2s);

		System.out.println("n1: " + num1 + ", n2: " + num2);

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
