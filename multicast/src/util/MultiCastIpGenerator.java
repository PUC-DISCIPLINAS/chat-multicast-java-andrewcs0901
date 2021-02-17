package util;

import java.util.StringJoiner;
import java.util.stream.IntStream;

public class MultiCastIpGenerator {
	private static final int[] ip = { 240, 0, 0, 0 };

	public static String nextIp() throws Exception {
		for (int i = 3; i > -1; i--) {
			if (ip[i] < 255) {
				ip[i]++;
				StringJoiner separator = new StringJoiner(".");
				IntStream.of(ip[0] - 16, ip[1], ip[2], ip[3]).forEach(x -> separator.add(String.valueOf(x)));
				return separator.toString();
			}
			ip[i] = 0;		
		}
		throw new Exception("Não existem mais numeros de IP's para Multicast disponíveis");
	}
}
