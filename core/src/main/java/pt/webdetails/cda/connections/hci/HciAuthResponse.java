package pt.webdetails.cda.connections.hci;

public class HciAuthResponse {
	
	private String access_token;
	
	private int expires_in;
	
	public HciAuthResponse() {}
	
	public HciAuthResponse(String accessToken, int expiresIn) {
		this.setAccessToken(accessToken);
		this.setExpiresIn(expiresIn);
	}

	public String getAccessToken() {
		return access_token;
	}

	public void setAccessToken(String access_token) {
		this.access_token = access_token;
	}

	public int getExpiresIn() {
		return expires_in;
	}

	public void setExpiresIn(int expires_in) {
		this.expires_in = expires_in;
	}
	

}
