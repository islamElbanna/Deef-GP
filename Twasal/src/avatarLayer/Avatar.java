package avatarLayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Avatar implements Avatar_Interface {

	@Override
	public void show(String code) throws IOException {
		String serverHostname = "localhost";
		Socket echoSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			echoSocket = new Socket(serverHostname, 56734);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					echoSocket.getInputStream()));
		} catch (Exception e) {
			System.err
					.println("Blender is not connected please run the code first");
			System.exit(1);
		}
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(
				System.in));

		out.print(code);
		while (!in.ready())
			;
		System.out.println("echo: " + in.readLine());
		System.out.println("Next one");

		out.close();
		in.close();
		stdIn.close();
		echoSocket.close();

	}

}
