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
				System.out.println("Mensaje recibido del cliente: ");
				byte[] respuesta = new byte[LENGTH];
				respuesta = mensaje.getBytes();
				DatagramPacket envio = new DatagramPacket(respuesta, respuesta.length);
				
				DatagramSocket socketCli = new DatagramSocket(packet.getPort(), packet.getAddress());
				System.out.println("Enviando mensjae al cliente...");
				socketCli.send(envio);
				System.out.println("Terminada conexión con el cliente");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		new SrvEspejo();
	}
}
