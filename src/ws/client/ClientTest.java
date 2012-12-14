package ws.client;

import java.io.File;  
import java.net.URI;  
  
import javax.ws.rs.core.MediaType;  
import javax.ws.rs.core.UriBuilder;  
  
import com.sun.jersey.api.client.Client;  
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;  
import com.sun.jersey.api.client.config.ClientConfig;  
import com.sun.jersey.api.client.config.DefaultClientConfig;  
import com.sun.jersey.api.representation.Form;
  
/**
*
* <brisskit:licence>
* Copyright University of Leicester, 2012
* This software is made available under the terms & conditions of brisskit
* @author Saj Issa
* </brisskit:licence>
*
*/

public class ClientTest {  
	
	public String performCall()
	{
        ClientConfig config = new DefaultClientConfig();  
        Client client = Client.create(config);  
        WebResource service = client.resource(getBaseURI());  

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        			 "<pdo:patient_data xmlns:pdo=\"http://www.i2b2.org/xsd/hive/pdo/1.1/pdo\">" +
        			 "<pdo:event_set>" +
        			 "<event download_date=\"2012-06-26T09:12:02+00:00\" import_date=\"2012-06-26T09:12:02+00:00\" sourcesystem_cd=\"BRICCS\" update_date=\"2012-06-26T09:12:02+00:00\" upload_id=\"1\">" +
        			 "<event_id source=\"BRICCS\">bru2-111111111_2012-06-26T09:12:02+00:00</event_id>" +
        			 "<patient_id source=\"BRICCS\">bru2-111111111</patient_id>" +
        			 "<param column=\"ACTIVE_STATUS_CD\" name=\"active status\">F</param>" +
        			 "<param column=\"INOUT_CD\" name=\"\">@</param>" +
        			 "<param column=\"LOCATION_CD\" name=\"\">@</param>" +
        			 "<param column=\"LOCATION_PATH\" name=\"\">@</param>" +
        			 "<start_date>2012-06-26T09:12:02+00:00</start_date>" +
        			 "<end_date>@</end_date>" +
        			 "</event>" +
        			 "</pdo:event_set>" +
        			 "<pdo:pid_set>" +
        			 "<pid>" +
        			 "<patient_id download_date=\"2012-06-26T09:12:02+00:00\" import_date=\"2012-06-26T09:12:02+00:00\" sourcesystem_cd=\"BRICCS\" source=\"BRICCS\" status=\"Active\" update_date=\"2012-06-26T09:12:02+00:00\" upload_id=\"1\">bru2-111111111</patient_id>" +
        			 "</pid>" +
        			 "</pdo:pid_set>" +
        			 "<pdo:eid_set>" +
        			 "<eid>" +
        			 "<event_id download_date=\"2012-06-26T09:12:02+00:00\" import_date=\"2012-06-26T09:12:02+00:00\" source=\"BRICCS\" sourcesystem_cd=\"BRICCS\" status=\"Active\" update_date=\"2012-06-26T09:12:02+00:00\" upload_id=\"1\">bru2-111111111_2012-06-26T09:12:02+00:00</event_id>" +
        			 "</eid>" +
        			 "</pdo:eid_set>" +
        			 "<pdo:patient_set>" +
        			 "<patient download_date=\"2012-06-26T09:12:02+00:00\" import_date=\"2012-06-26T09:12:02+00:00\" sourcesystem_cd=\"BRICCS\" update_date=\"2012-06-26T09:12:02+00:00\" upload_id=\"1\">" +
        			 "<patient_id source=\"BRICCS\">bru2-111111111</patient_id>" +
        			 "<param column=\"vital_status_cd\" name=\"date interpretation code\">N</param>" +
        			 "<param column=\"birth_date\" name=\"birthdate\">1962-04-07T00:00:00.000+01:00</param>" +
        			 "<param column=\"age_in_years_num\" name=\"age\">50</param>" +
        			 "<param column=\"race_cd\" name=\"ethnicity\">Unknown</param>" +
        			 "<param column=\"sex_cd\" name=\"sex\">UNSPECIFIED</param>" +
        			 "</patient>" +
        			 "</pdo:patient_set>" +
        			 "</pdo:patient_data>";  
    
        Form form = new Form();
        form.add( "incomingXML", xml ); 
        form.add( "activity_id", "941" );
        
        //ClientResponse response = service.path("service").path("pdo").type(MediaType.APPLICATION_XML).post(ClientResponse.class, xml);
        ClientResponse response = service.path("service").path("pdo").type(MediaType.APPLICATION_XML).post(ClientResponse.class, form);
                
 		System.out.println("performCall Output from Server .... \n");
 		String output = response.getEntity(String.class);
 		System.out.println(output);
 		
 		return output;
		
	}
	
	
    public static void main(String[] args) {  
    	ClientTest c = new ClientTest();
    	String s = c.performCall();
    }  
  
    private static URI getBaseURI() {  
        //return UriBuilder.fromUri("http://localhost:8080/i2b2WS/rest").build();  
        return UriBuilder.fromUri("http://bru2.brisskit.le.ac.uk:8080/i2b2WS/rest").build();
        //return UriBuilder.fromUri("http://i2b2:8080/i2b2WS/rest").build();
        
    }  
    //http://bru2.brisskit.le.ac.uk:8080/i2b2WS/rest/service/pdo
}  