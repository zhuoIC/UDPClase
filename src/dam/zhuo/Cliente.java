package dam.zhuo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class Cliente {
	public static final String IP = "localhost";
	public static final int PUERTO = 8000;
	private DatagramSocket miSocket;
	public Cliente() {
		try {
			System.out.println("Cliente arrancado");
			miSocket = new DatagramSocket(PUERTO);
			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in, SrvEspejo.CHARSET));
			byte[] mensaje = new byte[SrvEspejo.LENGTH];
			System.out.print("Introduce el mensaje a enviar al servidor: ");
			String lectura = teclado.readLine();
			mensaje = lectura.getBytes(SrvEspejo.CHARSET);
			DatagramPacket envio = new DatagramPacket(mensaje, mensaje.length, InetAddress.getByName(SrvEspejo.IP), SrvEspejo.PUERTO);
			System.out.print("Mensaje enviado: " + new String(mensaje));
			miSocket.send(envio);
			if(teclado != null)
				teclado.close();
			
			byte[] mensajeSrv = new byte[SrvEspejo.LENGTH];
			DatagramPacket respuesta = new DatagramPacket(mensajeSrv, SrvEspejo.LENGTH);
			miSocket.receive(respuesta);
			System.out.println("Respuesta del servidor: ");
			System.out.println(new String(respuesta.getData(), respuesta.getOffset(), respuesta.getLength()));
			System.out.println("Longitud: " + respuesta.getData().length);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if(miSocket != null)
				miSocket.close();
		}
	}
	
	public static void main(String[] args) {
		new Cliente();
	}
}
