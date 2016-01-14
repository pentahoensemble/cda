package pt.webdetails.cda.connections.hci;

import org.dom4j.Element;

public class HciConnectionInfo {
	
	private static String url;
	private static String userName;
	private static String password;
	
	public HciConnectionInfo( final Element connection ) {
	   url = ((String) connection.selectObject( "string(./Url)" ));
	   userName = ((String) connection.selectObject( "string(./Property[@name='HciUser'])" ));
	   password = ((String) connection.selectObject( "string(./Property[@name='HciPassword'])" ));
	}
	
	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

}
