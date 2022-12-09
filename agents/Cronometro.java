package semaforo_inteligente;

import java.net.*;
import java.io.*;

public class Cronometro {
	public Cronometro() {
	}

	public void start(double clock1, double clock2) {
		try {

			URL url = new URL(
					"http://localhost:3000/setup-timer"
							+ "?clock1=" + clock1
							+ "&clock2=" + clock2);
			URLConnection yc = url.openConnection();
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							yc.getInputStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				// System.out.println(inputLine);
			}
			in.close();
		} catch (Exception e) {

		}

	}

}