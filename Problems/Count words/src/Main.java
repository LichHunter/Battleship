import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String input = reader.readLine();
		String output = !input.isBlank() ? String.valueOf(input.split("[^\\s]+\\b").length) : "0";
		System.out.println(output);

		reader.close();
	}
}