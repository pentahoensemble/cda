package pt.webdetails.cda.connections.hci;

import org.dom4j.Element;

import pt.webdetails.cda.connections.AbstractConnection;
import pt.webdetails.cda.connections.ConnectionCatalog.ConnectionType;
import pt.webdetails.cda.connections.InvalidConnectionException;

public class HciConnection extends AbstractConnection {
	
	public static final String TYPE = "hci";
	
	private HciConnectionInfo connectionInfo;
	
	public HciConnection() {
	    super();
	}

	public HciConnection( String id ) {
	    super( id );
	}
	
	public HciConnection( final Element connection ) throws InvalidConnectionException {
	    super( connection );
	}

	@Override
	public ConnectionType getGenericType() {
		return ConnectionType.HCI;
	}

	@Override
	protected void initializeConnection(Element connection)
			throws InvalidConnectionException {
		setConnectionInfo(new HciConnectionInfo( connection ));
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public int hashCode() {
		return connectionInfo != null ? connectionInfo.hashCode() : 0;
	}

	@Override
	public boolean equals(Object obj) {
		 if ( this == obj ) {
		      return true;
		    }
		    if ( obj == null || getClass() != obj.getClass() ) {
		      return false;
		    }

		    final HciConnection that = (HciConnection) obj;

		    if ( connectionInfo != null ? !connectionInfo.equals( that.connectionInfo ) : that.connectionInfo != null ) {
		      return false;
		    }

		    return true;
	}

	public HciConnectionInfo getConnectionInfo() {
		return connectionInfo;
	}

	public void setConnectionInfo(HciConnectionInfo connectionInfo) {
		this.connectionInfo = connectionInfo;
	}

}
