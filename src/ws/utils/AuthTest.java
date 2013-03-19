package ws.utils;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import com.jayway.jsonpath.JsonPath;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class AuthTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource getAuthenticatedService = client.resource(getAuthenticated("saj","saj","2977543f6e21e66ee0c28a504fb4554e"));
        String getAuthenticatedValueService = getAuthenticatedService.get(String.class);
        
        
        String api_key = JsonPath.read(getAuthenticatedValueService,"$.api_key");
        
        System.out.println(" api_key " + api_key);
        
        String PHPSESSID = JsonPath.read(getAuthenticatedValueService,"$.PHPSESSID");
        
        System.out.println(" PHPSESSID " + PHPSESSID);
        
        String key = JsonPath.read(getAuthenticatedValueService,"$.key");
        
        System.out.println(" key " + key);
        
       

	}
	
	private static URI getAuthenticated(String name, String pass, String key) {

		System.out.println(" *************** getAuthenticated");
		
		http://hack5.brisskit.le.ac.uk/civicrm/sites/all/modules/civicrm/extern/rest.php?q=civicrm/login&name=saj&pass=saj&key=2977543f6e21e66ee0c28a504fb4554e&json=1
			
			System.out.println("http://hack5.brisskit.le.ac.uk/civicrm/sites/all/modules/civicrm/extern/rest.php?q=civicrm/login&name="+ name + "&pass="+ pass + "&key="+ key + "&json=1");

		return UriBuilder
				.fromUri("http://hack5.brisskit.le.ac.uk/civicrm/sites/all/modules/civicrm/extern/rest.php?q=civicrm/login&name="+ name + "&pass="+ pass + "&key="+ key + "&json=1").build();
	}

}
