package ws.utils;

public class ConnectionTokens {

	private String api_key;
	private String PHPSESSID;
	private String key;
	
	public String getApi_key() {
		return api_key;
	}
	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}
	public String getPHPSESSID() {
		return PHPSESSID;
	}
	public void setPHPSESSID(String pHPSESSID) {
		PHPSESSID = pHPSESSID;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
