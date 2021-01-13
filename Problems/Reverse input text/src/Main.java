import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		// start coding here

		System.out.println(new StringBuilder().append(reader.readLine()).reverse());

		reader.close();
	}
}