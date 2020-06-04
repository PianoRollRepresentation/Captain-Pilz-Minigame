package de.tobias.game.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import de.tobias.game.Game;

public class GameServer extends Thread {

	private DatagramSocket socket;
	@SuppressWarnings("unused")
	private Game game;

	public GameServer(Game game) {
		this.game = game;
		try {
			this.socket = new DatagramSocket(1331);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String msg = new String(packet.getData());
			System.out.println("CLIENT [" + packet.getAddress().getHostAddress() + ":" + packet.getPort() + "]> " + msg);
			if (msg.trim().equalsIgnoreCase("ping"))
				sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
		}
	}

	public void sendData(byte[] data, InetAddress ipAddress, int port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
