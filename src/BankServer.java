
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;

public class BankServer{
	private static final int BANKPORT = 8888;

	private ServerSocket serverSocket;
	
	public void init(){
		try{
			try{
				serverSocket = new ServerSocket(BANKPORT);
				System.out.println("Socket created.");
			
				while(true){	
					System.out.println( "Listening for a connection on the local port " +
										serverSocket.getLocalPort() + "..." );
					Socket socket = serverSocket.accept();
					System.out.println( "\nA connection established with the remote port " + 
										socket.getPort() + " at " +
										socket.getInetAddress().toString() );
					
					try{
						Scanner in = new Scanner( socket.getInputStream() );
						PrintWriter out = new PrintWriter( socket.getOutputStream() );
						System.out.println( "I/O setup done" );
						
						while(true){
							if( in.hasNext() ){
								String command = in.next();
								if( command.equals("QUIT") ){
									System.out.println( "QUIT: Connection being closed." );
									out.println( "QUIT accepted. Connection being closed." );
									out.close();
									return;
								}
								accessAccount( command, in, out );
							}
						}
					}	finally{
						socket.close();
						System.out.println( "A connection is closed." );
					}
				}
			}finally{
				serverSocket.close();
			}
		}
		catch(IOException exception){}
	}

	private void accessAccount( String command, Scanner in, PrintWriter out ){
		
		if( command.equals("Server") )
		{
			System.out.println( "Hello Client" );
			out.println( "Hello from Server" );
		}
		else{
			System.out.println( "Invalid Command" );
			out.println( "Invalid Command. Try another command." );
		}
		out.flush();
	}
	
	public static void main(String[] args){
		BankServer server = new BankServer();
		server.init();
	}
}
