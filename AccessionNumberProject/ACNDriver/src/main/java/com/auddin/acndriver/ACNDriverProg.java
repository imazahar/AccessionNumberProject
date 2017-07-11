package com.auddin.acndriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.auddin.accessionnum.AccessionNumberProcessor;

public class ACNDriverProg {

	private static final int numThreadsForSocket = 50;
	private static final Executor thPool = Executors.newFixedThreadPool(numThreadsForSocket);
	private static int nPort = 8080;

	public static void main(String[] args) throws IOException {
		Scanner console = new Scanner(System.in);
		while (true) {
			System.out.println("1. Type cmd to provide list of accession numbers on command prompt.");
			System.out.println(
					"2. Type ws to start thin web server. Use http testing tool e.g. Postman to provide list of accession numbers. Provide list of accession numbers in body of POST method. Web endpoint will be http://localhost:<portnum> ");
			System.out.println("3. Type exit to close the program ");
			String cmd = console.nextLine();
			if (cmd.compareToIgnoreCase("cmd") == 0) {
				rumCmd(console);
			} else if (cmd.compareToIgnoreCase("ws") == 0) {
				System.out.println("Please provide port number where you want to start thin web server:");
				String port = console.nextLine();
				try {
					nPort = Integer.parseInt(port);
					runThinWebServer();
				} catch (NumberFormatException e) {
					// TODO: Log information in Log Files using Log4j
					System.out.println("Incorrect port number.");
				}
			} else if (cmd.compareToIgnoreCase("exit") == 0) {
				console.close();
				return;
			} else {
				System.out.println("Incorrect Option");
			}
		}
	}

	/**
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	private static void runThinWebServer() {
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(nPort);
		} catch (IOException e) {
			// TODO: Log information in Log Files using Log4j
			System.out.println("Could not open socket at 1010. Please make sure port is free");
			return;
		}
		System.out.println("Thin Web Server Started. Precc CTRL C to exit.");
		while (true) {
			final Socket sockConn;
			try {
				sockConn = socket.accept();
			} catch (IOException e) {
				// TODO: Log information in Log Files using Log4j
				System.out.println("Could not accpet request at sockets. Please make sure port is free");
				return;
			}
			Runnable exeTask = new Runnable() {
				public void run() {
					HandleRequest(sockConn);
				}
			};
			thPool.execute(exeTask);
		}
	}

	private static void rumCmd(Scanner console) {
		System.out.println(System.getProperty("line.separator")+"Please enter accession numbers seprated by comma, press enter once you are done: ");
		String slist = console.nextLine();
		String returnList = AccessionNumberProcessor.getSortedList(slist);
		System.out.println("");
		System.out.println(System.getProperty("line.separator") + "Ordered list of accession numbers : "
				+ System.getProperty("line.separator"));
		if (returnList != null) {
			System.out.print(returnList+System.getProperty("line.separator"));
		} else {
			System.out.println(
					System.getProperty("line.separator") + " No data to show. " + System.getProperty("line.separator"));
		}
		System.out.println("");
	}

	private static void HandleRequest(Socket sockConn) {
		BufferedReader bufReader;
		PrintWriter outWriter;
		String strReq;

		try {
			bufReader = new BufferedReader(new InputStreamReader(sockConn.getInputStream()));

			int contentLength = 0;
			while (!(strReq = bufReader.readLine()).equals("")) {
				if (strReq.startsWith("Content-Length:")) {
					String cl = strReq.substring("Content-Length:".length()).trim();
					contentLength = Integer.parseInt(cl);
				}
			}

			char[] buf = new char[contentLength];
			bufReader.read(buf);
			String slist = new String(buf);

			outWriter = new PrintWriter(sockConn.getOutputStream(), true);
			outWriter.println("HTTP/1.0 200");
			outWriter.println("Content-type: text/html");
			outWriter.println("Server-name: Azahar");
			String response = AccessionNumberProcessor.getSortedList(slist);
			;
			outWriter.println("Content-length: " + response.length());
			outWriter.println("");
			outWriter.println(response);
			outWriter.flush();
			outWriter.close();
			sockConn.close();
		} catch (IOException e) {
			// TODO: Log information in Log Files using Log4j
			System.out.println("Response could not be sent to client " + e.getMessage());
		} finally {
			if (sockConn != null) {
				try {
					sockConn.close();
				} catch (IOException e) {
					// TODO: Log information in Log Files using Log4j
					System.out.println("Closing of socket failed");
				}
			}
		}
		return;
	}

}
