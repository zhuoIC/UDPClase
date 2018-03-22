package dam.zhuo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class SrvEspejo {
	public static final int PUERTO = 9000;
	public static final String IP = "0.0.0.0";
	public static final String CHARSET = "utf8";
	private DatagramSocket daSocket = null;
	public static final int LENGTH = 144;
	public SrvEspejo() {
		
		System.out.println("Arrancando servidor");
		try {
			System.out.println("");
			InetSocketAddress inetSocketAddress = new InetSocketAddress(IP, PUERTO);
			daSocket = new DatagramSocket(inetSocketAddress);
			while (true) {
				DatagramPacket packet = new DatagramPacket(new byte[LENGTH], LENGTH);
				daSocket.receive(packet);
				String mensaje = new String(packet.getData());
				System.out.println(packet.getData().length);

				String reves = delReves(new String(mensaje.getBytes(), packet.getOffset(), packet.getLength()));
				System.out.println(packet.getLength());
				System.out.println("Mensaje recibido del cliente: " + reves);
				System.out.println("Offset: " + packet.getOffset());
				System.out.println("Longitud: "+packet.getData().length);
				byte[] respuesta = new byte[LENGTH];
				respuesta = reves.getBytes();
				DatagramPacket envio = new DatagramPacket(respuesta, respuesta.length, packet.getSocketAddress());

				System.out.println("Enviando mensaje al cliente...");
				daSocket.send(envio);
				System.out.println("Terminada conexión con el cliente");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		new SrvEspejo();
	}
	
	private String delReves(String mensaje) {
		StringBuilder str = new StringBuilder(mensaje);
		/*Character c;
		for(int i = mensaje.length() - 1; i >= 0; i--) {
			c = mensaje.charAt(i);
			if(Character.isSpaceChar(c) || Character.isLetterOrDigit(c)) {
				System.out.println("Char["+i+"]="+mensaje.charAt(i));
				str.append(mensaje.charAt(i));
			}
		}*/
		str.reverse();
		return str.toString().toUpperCase();
	}
}
