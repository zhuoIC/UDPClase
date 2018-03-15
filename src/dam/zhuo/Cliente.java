package dam.zhuo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Cliente {
	private DatagramSocket socketSrv;
	private DatagramSocket socketMio;
	public Cliente() {
		try {
			System.out.println("Cliente arrancado");
			socketSrv = new DatagramSocket();
			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in, SrvEspejo.CHARSET));
			byte[] mensaje = new byte[SrvEspejo.LENGTH];
			System.out.print("Introduce el mensaje a enviar al servidor: ");
			String lectura = teclado.readLine();
			mensaje = lectura.getBytes(SrvEspejo.CHARSET);
			DatagramPacket envio = new DatagramPacket(mensaje, mensaje.length, InetAddress.getByName(SrvEspejo.IP), SrvEspejo.PUERTO);
			System.out.print("Mensaje enviado: " + new String(mensaje));
			socketSrv.send(envio);
			if(teclado != null)
				teclado.close();
			
			socketMio = new DatagramSocket();
			byte[] mensajeSrv = new byte[SrvEspejo.LENGTH];
			DatagramPacket respuesta = new DatagramPacket(mensajeSrv, SrvEspejo.LENGTH);
			socketMio.receive(respuesta);
			System.out.println("Respuesta del servidor: ");
			System.out.println(new String(respuesta.getData()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if(socketSrv != null)
				socketSrv.close();
		}
	}
	
	public static void main(String[] args) {
		new Cliente();
	}
}
