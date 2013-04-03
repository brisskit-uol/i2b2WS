package ws;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import org.brisskit.i2b2.dataimport.ImportPdo ;
import ws.utils.GetConnection;
import ws.utils.Ontology;
import ws.utils.StreamGobbler;
import ws.utils.TableAccess;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * 
 * <brisskit:licence> Copyright University of Leicester, 2012 This software is
 * made available under the terms & conditions of brisskit
 * 
 * @author Saj Issa This resource represents the WebServices to send pdo files
 *         to i2b2 and send an acknowledgement to civi on receipt of patient
 *         data from civi </brisskit:licence>
 * 
 */

@Path("/service")
public class WebService {
	final static Logger logger = LogManager.getLogger(WebService.class);
	
	// Standardised (by convention) path to the webservice.properties file:
	final static String PROPERTIES_PATH = "/var/local/brisskit/i2b2/webservice.properties" ;
	final static String CIVI_PROPERTIES_PATH = "/etc/brisskit/civicrm_integration.cfg" ;
	
	
	// These are the keys (or partial keys) to properties held within the webservice.properties file...
	final static String ENVIRONMENT = "env" ;
	final static String DBNAME = "db_name" ;
	final static String DBURL = "db_url" ;
	final static String SOURCE_SYSTEM = "sourcesystem" ;	
	final static String IMPORTPDO_INPUT_PATH = "importpdo.input.path" ;
	final static String IMPORTPDO_LOG4J_CONFIG = "importpdo.log4j.configuration" ;
	final static String IMPORTPDO_ENDORSED_LIBS = "importpdo.endorsed.lib" ;
	final static String IMPORTPDO_CONFIG = "importpdo.config" ;
	final static String TEST_INVOCATION = "test.invocation" ;
	
	final static String CIVIURL = "civiurl" ;
	final static String CIVI_REST_LOCATION = "civi_rest_location";
	final static String CIVI_INTEGRATION_USERNAME = "civi_integration_username";
	final static String CIVI_INTEGRATION_PASSWORD = "civi_integration_password";
	final static String CIVI_INTEGRATION_API_KEY = "civi_integration_api_key";
	final static String CIVI_SITE_API_KEY = "civi_site_api_key";

	
	
	static Properties prop = new Properties();
	static String db_name;
	static String db_url;
	static String sourcesystem;
	static String civiurl;
	
	
	static Properties prop_civi = new Properties();
	static String name;
	static String pass;
	static String key;
	static String site_key;
	
	
	static 
	{
		try {		
			prop.load(new FileInputStream(PROPERTIES_PATH) );
			String env = prop.getProperty( ENVIRONMENT );
						
			db_name = prop.getProperty( env + "." + DBNAME );
			db_url  = prop.getProperty( env + "." + DBURL );
			sourcesystem = prop.getProperty( env + "." + SOURCE_SYSTEM );
			civiurl  = prop.getProperty( env + "." + CIVIURL );
			
	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		try {
			prop_civi.load(new FileInputStream(CIVI_PROPERTIES_PATH) );
			String env = prop_civi.getProperty( ENVIRONMENT );
						
			name = prop_civi.getProperty( CIVI_INTEGRATION_USERNAME );
			pass  = prop_civi.getProperty( CIVI_INTEGRATION_PASSWORD );
			key = prop_civi.getProperty( CIVI_INTEGRATION_API_KEY );
			site_key = prop_civi.getProperty( CIVI_SITE_API_KEY );
			
	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	@GET
	@Path("i2b2callback2/{incomingXML}")
	@Produces({ MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
	public String postOnlyXMLi2b2(@PathParam("incomingXML") String incomingXML) {
		return "{tttt,gggg}";
	}

	@GET
	@Path("i2b2callback3/{incomingXML}")
	@Produces({ MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
	public String postOnlyXMLi2b3(@PathParam("incomingXML") String incomingXML) {
		return "callbacki2b2({\"status\" : \"OK\"})";
	}
	
	@GET
	@Path("i2b2callback1/{incomingXML}")
	@Produces("text/html")
	public String postOnlyXMLi2b1(@PathParam("incomingXML") String incomingXML) {
		logger.info("postOnlyXMLi2b1 incomingXML :" + incomingXML);
		
		String ids = "";
		String project = "";
		List<String> civi_contact_id = new ArrayList<String>();
		
		
		
		if (incomingXML.contains("*")) {
			String[] parts = incomingXML.split("\\*");
			ids = parts[0]; 
			project = parts[1]; 
			logger.info("if ids :" + ids);
			logger.info("if project :" + project);
			
		} 
		else 
		{
			ids = incomingXML;
			logger.info("else ids :" + ids);
		}
		
		List<String> brisskitids = sql(ids);

		/*
		String date = project + "-i2b2-cohort-"
				+ new java.text.SimpleDateFormat("yyyy-MM-dd--HH-mm-ss")
						.format(new java.util.Date());
		System.out.println(date);*/

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);

		/*WebResource createGroupService = client.resource(createGroup(date));
		String createGroupValueService = createGroupService.get(String.class);

		logger.info(" ");
		logger.info("responseoptionValueService............"
				+ createGroupValueService);

		int group_id = JsonPath.read(createGroupValueService, "$.values[0].id");

		logger.info("group_id............" + group_id);*/

		System.out.println("2");
		
		String error = "";
		int error_count = 0;
		int patient_count = 0;
				
		//Authenticate
		
		WebResource getAuthenticatedService = client.resource(getAuthenticated());
		
		logger.info(" * "); 
		
        String getAuthenticatedValueService = getAuthenticatedService.get(String.class);
        
        logger.info(" ** ");
       
        String api_key = JsonPath.read(getAuthenticatedValueService,"$.api_key");       
        logger.info(" api_key " + api_key);        
        String PHPSESSID = JsonPath.read(getAuthenticatedValueService,"$.PHPSESSID");        
        logger.info(" PHPSESSID " + PHPSESSID);        
        String key = JsonPath.read(getAuthenticatedValueService,"$.key");        
        logger.info(" key " + key);
        
        //Authenticate
        
		
		for (String brisskitid : brisskitids) {
			logger.info("brisskitid :" + brisskitid);
      
			patient_count++;
			WebResource getContactIdService = client
					.resource(getContactId(brisskitid,api_key,PHPSESSID,key));
			String getContactIdValueService = getContactIdService
					.get(String.class);

			logger.info(" ");
			logger.info("getContactIdValueService............"
					+ getContactIdValueService);

			try
			{
			String contact_id = JsonPath.read(getContactIdValueService,
					"$.values[0].contact_id");

			logger.info("contact_id............" + contact_id);

			
			civi_contact_id.add(contact_id);
			
			/*
			
			WebResource addContactToGroupService = client
					.resource(addContactToGroup(group_id, contact_id));
			String addContactToGroupValueService = addContactToGroupService
					.get(String.class);

			logger.info(" ");
			logger.info("addContactToGroupValueService............"
					+ addContactToGroupValueService);
			
			*/
			
			
			
			}
			catch(IndexOutOfBoundsException e)
			{
				error_count++;
				logger.info("Doesn't exist in civi " + brisskitid);
				error = error + brisskitid + ", ";
			}


		}
		
		
		
		
		String groupname = "i2b2-" + project + "-p-" +patient_count + " "
				+ new java.text.SimpleDateFormat("yyyy-MM-dd--HH-mm-ss")
						.format(new java.util.Date());
		
		if (error_count > 0)
		{
			groupname = "i2b2-" + project + "-p-" + patient_count + "-m-" + error_count + " "
					+ new java.text.SimpleDateFormat("yyyy-MM-dd--HH-mm-ss")
							.format(new java.util.Date());
		}
		
		
		
		System.out.println(groupname);
		
		WebResource createGroupService = client.resource(createGroup(groupname,api_key,PHPSESSID,key));
		String createGroupValueService = createGroupService.get(String.class);

		logger.info(" ");
		logger.info("responseoptionValueService............"
				+ createGroupValueService);

		Integer iGroup_id = JsonPath.read(createGroupValueService, "$.values[0].id") ;
		int group_id = iGroup_id.intValue() ;

		logger.info("group_id............" + group_id);
		
		
		
		
		for (String civi_contact_ids : civi_contact_id) {
			logger.info("civi_contact_ids :" + civi_contact_ids);
			
			WebResource addContactToGroupService = client
					.resource(addContactToGroup(group_id, civi_contact_ids,api_key,PHPSESSID,key));
			String addContactToGroupValueService = addContactToGroupService
					.get(String.class);

			logger.info(" ");
			logger.info("addContactToGroupValueService............"
					+ addContactToGroupValueService);
			
		}
		
		
		

		if (error != "")
		{
			logger.info("Do not exist in civi " + error.substring(0, error.length()-1));
			//return "callbacki2b2({\"status\" : \" "+ error.substring(0, error.length()-1) + "\"})";
			return "callbacki2b2({\"status\" : \"success\", \"log\" : \"some patients do not exist in civicrm\", \"patients\" : \""+ patient_count + "\", \"missing\" : \""+ error_count + "\"})";
		}
		else
		{
		    return "callbacki2b2({\"status\" : \"success\", \"log\" : \"all patients exist in civicrm\", \"patients\" : \""+ patient_count + "\", \"missing\" : \""+ error_count + "\"})";
		}
		
		//return "success";
	}

	@GET
	@Path("disp/{val}")
	@Produces("text/plain")
	public String getParameterToAdd(@PathParam("val") String name) {

		StringBuffer buffer = new StringBuffer();
		buffer.append("Dislay Message : ").append(name);

		return buffer.toString();
	}

	@POST
	@Path("pdo")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces({ MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
	public String postOnlyXML2(@FormParam("incomingXML") String incomingXML,
			@FormParam("activity_id") String activity_id) {
		System.out.println("incomingXML :" + incomingXML);
		String str = incomingXML;
		String status = "";
		String status_var = "Failed";

		// logger.info("replace + with %2B");
		logger.info("************* A PDO HAS ARRIVED *******************");
		logger.info(" ");
		logger.info("CONTENT OF XML " + incomingXML);
		logger.info(" ");
		logger.info("activity_id : " + activity_id);
		
		//
		// Current Date time in Local time zone
		SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = localDateFormat.format(new Date());	
		//
		// Locate directory where input PDO will be passed to the ImportPdo programme...
		String pdoDirectoryPath = prop.getProperty( IMPORTPDO_INPUT_PATH ) ;
		String pdoFullPathName = pdoDirectoryPath + System.getProperty( "file.separator" ) + "pdo" + date + ".xml" ;

		if (str.contains("xml=")) {
			str = str.substring(str.indexOf("xml=") + 4);
		}
		
		try {

			logger.info(" ");
			logger.info("FILE NAME : " + pdoFullPathName );

			FileWriter fstream = new FileWriter( pdoFullPathName );
			BufferedWriter out = new BufferedWriter(fstream);
			// out.write(param2AfterDecoding);
			out.write(str);
			// Close the output stream
			out.close();

			/****************/

			/** This is on a per PDO basis **/

			ImportPdo importer = new ImportPdo();

			String[] args = new String[6];

			args[0] = "-Dlog4j.configuration=file://" + prop.getProperty( IMPORTPDO_LOG4J_CONFIG ) ;
			args[1] = "-Djava.endorsed.dirs=" + prop.getProperty( IMPORTPDO_ENDORSED_LIBS ) ;
			//
			// Not sure whether this setting has been overlooked somewhere.
			// It is not used by the ImportPdo.
			args[2] = "-DFRClearTempTables=true";
			args[3] = "-config=" + prop.getProperty( IMPORTPDO_CONFIG ) ;
			args[4] = "-import=" + pdoFullPathName ; 
			args[5] = "-append=false" ;

			boolean good = importer.retrieveArgs(args);
			if (!good) {
				// log.error( USAGE ) ;
				logger.info(" ");
				logger.info("I2B2 FAILED1");
			}
			//
			// Process the config file...
			good = importer.retrieveConfigDetails();
			if (!good) {
				logger.info(" ");
				logger.info("I2B2 FAILED2");
			}

			//
			// Do the import process...
			good = importer.exec();
			if (!good) {
				// log.debug( "Failure!" ) ;
				logger.info(" ");
				logger.info("I2B2 FAILED3");

			} else {
				status = "success";
				logger.info(" ");
				logger.info("I2B2 SUCCESS");
				status_var = "Completed";
			}
			//
			// All appears to have gone well...
			// log.debug( "Done!" ) ;

			logger.info(" ");
			logger.info("I2B2 IMPORT COMPLETE");

			/***************/

			/******** CIVI callback ***********/

			if (!activity_id.equals("X")) {

				try {

					String option_group_id = "";
					String activity_status_id = "";

					ClientConfig config = new DefaultClientConfig();
					Client client = Client.create(config);
					
					
					//Authenticate
					
					WebResource getAuthenticatedService = client.resource(getAuthenticated());
			        String getAuthenticatedValueService = getAuthenticatedService.get(String.class);
			       
			        String api_key = JsonPath.read(getAuthenticatedValueService,"$.api_key");       
			        logger.info(" api_key " + api_key);        
			        String PHPSESSID = JsonPath.read(getAuthenticatedValueService,"$.PHPSESSID");        
			        logger.info(" PHPSESSID " + PHPSESSID);        
			        String key = JsonPath.read(getAuthenticatedValueService,"$.key");        
			        logger.info(" key " + key);
			        
			        //Authenticate
					
					
					
					
					
					
					// client.addFilter(new
					// HTTPBasicAuthFilter("soma","Leicester2"));

					/* get options list */
					WebResource optionGroupService = client
							.resource(getOptionGroupBaseURI(api_key,PHPSESSID,key));
					String responseoptionGroupService = optionGroupService
							.get(String.class);

					logger.info(" ");
					logger.info("responseoptionGroupService............"
							+ responseoptionGroupService);

					option_group_id = jsonGetId(
							optionGroupService.get(String.class),
							"option_group");

					logger.info(" ");
					logger.info("option_group_id............" + option_group_id);

					if (status.equalsIgnoreCase("success"))
						status_var = "Completed";

					logger.info(" ");
					logger.info("status_var............" + status_var);

					WebResource optionValueService = client
							.resource(getOptionValueBaseURI(option_group_id,
									status_var,api_key,PHPSESSID,key));
					String responseoptionValueService = optionValueService
							.get(String.class);

					logger.info(" ");
					logger.info("responseoptionValueService............"
							+ responseoptionValueService);

					activity_status_id = jsonGetId(
							optionValueService.get(String.class),
							"option_value");

					logger.info(" ");
					logger.info("activity_status_id............ "
							+ activity_status_id);

					WebResource service = client.resource(getBaseURI(
							activity_id, activity_status_id,api_key,PHPSESSID,key));

					ClientResponse response = service.type(MediaType.TEXT_HTML)
							.post(ClientResponse.class); // added

					logger.info("3");

					String response1 = service.get(String.class);

					logger.info(" " + response1);
					
					
					logger.info("response1............"+response1);

					logger.info(" ");
					logger.info("CIVI CALLBACK COMPLETE");

				} catch (UniformInterfaceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			/******** CIVI callback ***********/

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return status_var;
	}



	private static String jsonGetId(String service, String option)
			throws JSONException {
		String id = "";

		JSONObject obj1 = new JSONObject(service);
		JSONObject obj2 = new JSONObject(obj1.get("values").toString());
		Iterator itr = obj2.keys();

		if (itr.hasNext()) {
			JSONObject obj3 = new JSONObject(obj2.get(itr.next().toString())
					.toString());

			if (option.equalsIgnoreCase("option_group"))
				id = obj3.get("id").toString();
			else
				id = obj3.get("value").toString();
		}

		return id;
	}

	private List sql(String p_ids) {
		logger.info("p_ids :" + p_ids);

		List<String> brisskitids = new ArrayList();

		String delims = "[,]";
		String[] tokens = p_ids.split(delims);
		String where_clause = "";

		for (int i = 0; i < tokens.length; i++) {
			logger.info(i + " " + tokens[i]);
			where_clause = where_clause + "PATIENT_NUM = '" + tokens[i]
					+ "' or ";
		}

		if (where_clause != null) {
			where_clause = "where  [SOURCESYSTEM_CD] = '"+ sourcesystem +"' AND ("
					+ where_clause.substring(0, where_clause.length() - 3)
					+ ")";
		}

		logger.info(" where_clause " + where_clause);

		Connection conn = GetConnection.getSimpleConnectionMSSQL(db_url);
		Statement s;

		try {

			String query = "SELECT "
					+ "[PATIENT_IDE] " +
					",[PATIENT_NUM] " +
					"FROM "+ db_name +".[dbo].[Patient_Mapping]" +					
					where_clause;

			s = conn.createStatement();

			// execute the query, and get a java resultset
			ResultSet rs = s.executeQuery(query);
			int count = 0;

			// iterate through the java resultset
			while (rs.next()) {
				count++;
				String c1 = rs.getString(1);
				brisskitids.add(c1);
				logger.info(c1);
			}
			s.close();
			logger.info(count + " rows were selected");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return brisskitids;
	}
	
	
	/************************************/
	/* CIVI CALLS                       */
	/************************************/
		
	private static URI getOptionGroupBaseURI(String api_key, String PHPSESSID, String key) {
		logger.info("getOptionGroupBaseURI ");
		logger.info("http://"+ civiurl + CIVI_REST_LOCATION + "?json=1&debug=1&version=3&entity=OptionGroup&action=get&name=activity_status" + "&key=" + key +"&api_key=" + api_key + "&PHPSESSID=" + PHPSESSID);
			
		return UriBuilder
				.fromUri(
						"http://" + civiurl +  CIVI_REST_LOCATION + "?json=1&debug=1&version=3&entity=OptionGroup&action=get&name=activity_status" + "&key=" + key +"&api_key=" + api_key + "&PHPSESSID=" + PHPSESSID)
				.build();
	}

	private static URI getOptionValueBaseURI(String option_group_id,String status,String api_key, String PHPSESSID, String key) {
		logger.info("getOptionValueBaseURI ");
		logger.info("option_group_id = " + option_group_id);
		logger.info("status = " + status);
		
		logger.info("http://" + civiurl +  CIVI_REST_LOCATION + "?json=1&debug=1&version=3&entity=OptionValue&action=get&option_group_id="
				+ option_group_id + "&name=" + status + "&key=" + key +"&api_key=" + api_key + "&PHPSESSID=" + PHPSESSID);

		return UriBuilder
				.fromUri(
						"http://" + civiurl +  CIVI_REST_LOCATION + "?json=1&debug=1&version=3&entity=OptionValue&action=get&option_group_id="
								+ option_group_id + "&name=" + status + "&key=" + key +"&api_key=" + api_key + "&PHPSESSID=" + PHPSESSID).build();

	}

	private static URI getBaseURI(String activity_id, String status_id, String api_key, String PHPSESSID, String key) {
		logger.info("getBaseURI ");
		logger.info("activity_id = " + activity_id);
		logger.info("status_id = " + status_id);
				
		logger.info("http://" + civiurl +  CIVI_REST_LOCATION + "?json=1&debug=1&entity=Activity&action=update&status_id="
				+ status_id + "&id=" + activity_id + "&key=" + key +"&api_key=" + api_key + "&PHPSESSID=" + PHPSESSID);

		return UriBuilder
				.fromUri(
						"http://" + civiurl +  CIVI_REST_LOCATION + "?json=1&debug=1&entity=Activity&action=update&status_id="
								+ status_id
								+ "&id="
								+ activity_id
								+ "&details=" + "&key=" + key +"&api_key=" + api_key + "&PHPSESSID=" + PHPSESSID).build();

	}

	
	private static URI getAuthenticated() {

		logger.info(" *************** getAuthenticated");
		
		http://hack5.brisskit.le.ac.uk/civicrm/sites/all/modules/civicrm/extern/rest.php?q=civicrm/login&name=saj&pass=saj&key=c3d22d956e7c6531e750bdbe2ee3c115&json=1
			
		logger.info("http://" + civiurl + "/civicrm/sites/all/modules/civicrm/extern/rest.php?q=civicrm/login&name="+ name + "&pass="+ pass + "&key="+ key + "&json=1");

		return UriBuilder
				.fromUri("http://" + civiurl + "/civicrm/sites/all/modules/civicrm/extern/rest.php?q=civicrm/login&name="+ name + "&pass="+ pass + "&key="+ key + "&json=1").build();
	}
	
	private static URI createGroup(String groupname, String api_key, String PHPSESSID, String key) {

		logger.info(" ***************" + groupname);
		logger.info("http://" + civiurl +  CIVI_REST_LOCATION + "?json=1&sequential=1&debug=1&entity=Group&action=create&title="
				+ groupname + "&key=" + key +"&api_key=" + api_key + "&PHPSESSID=" + PHPSESSID);

		return UriBuilder
				.fromUri(
						"http://" + civiurl +  CIVI_REST_LOCATION + "?json=1&sequential=1&debug=1&entity=Group&action=create&title="
								+ groupname + "&key=" + key +"&api_key=" + api_key + "&PHPSESSID=" + PHPSESSID).build();
	}

	
	private static URI getContactId(String brisskitid, String api_key, String PHPSESSID, String key) {

		logger.info(" ***************" + brisskitid);
		//logger.info("http://" + civiurl +  rest_location_orig + "?json=1&sequential=1&debug=1&entity=Brisskit&action=get&id=" + brisskitid);
		logger.info("http://" + civiurl +  CIVI_REST_LOCATION + "?json=1&sequential=1&debug=1&entity=Contact&action=get&custom_2=" + brisskitid + "&key=" + key +"&api_key=" + api_key + "&PHPSESSID=" + PHPSESSID);

		// Ensure that custom_2 is correct, could be custom_3 4 5 6, we dont know
		///civicrm/civicrm/ajax/rest?json=1&sequential=1&debug=1&&entity=CustomField&action=get&name=BRISSkit_ID
				
		
		return UriBuilder
				.fromUri("http://" + civiurl +  CIVI_REST_LOCATION + "?json=1&sequential=1&debug=1&entity=Contact&action=get&custom_2=" + brisskitid + "&key=" + key +"&api_key=" + api_key + "&PHPSESSID=" + PHPSESSID).build();
	}

	private static URI addContactToGroup(int groupid, String contactid, String api_key, String PHPSESSID, String key) {

		logger.info(groupid + " ***************" + contactid);
		logger.info("http://" + civiurl +  CIVI_REST_LOCATION + "?json=1&sequential=1&debug=1&entity=GroupContact&action=create&group_id="
				+ groupid + "&contact_id=" + contactid + "&key=" + key +"&api_key=" + api_key + "&PHPSESSID=" + PHPSESSID);

		return UriBuilder
				.fromUri(
						"http://" + civiurl +  CIVI_REST_LOCATION + "?json=1&sequential=1&debug=1&entity=GroupContact&action=create&group_id="
								+ groupid + "&contact_id=" + contactid + "&key=" + key +"&api_key=" + api_key + "&PHPSESSID=" + PHPSESSID).build();
	}
	
	/********** TESTS **********/	

	@GET
	@Path("catissue")
	@Produces(MediaType.TEXT_XML)
	public String getTodosBrowser() {
		    
		String output = "Empty";
		    // http://blog.rassemblr.com/2011/05/jquery-ajax-and-rest-http-basic-authentication-done-deal/
		    // add i2b2 credentials
		    // perhaps call through php, then a servlet, pass i2b2 login credentials
		
		    try {
				Process proc = Runtime.getRuntime().exec("/local/webservice.sh");
                StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");
                 
        // any output?
                StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT");
                
                //output = proc.getInputStream().toString();

        // kick them off
        errorGobbler.start();
        outputGobbler.start();
        
        try {
			outputGobbler.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        output = outputGobbler.out;
        System.out.println(output);
        
     /*   
        synchronized(outputGobbler){
            while (!outputGobbler.isReady()){
            	outputGobbler.wait();
            }
        }
*/
        

        // any error???
        int exitVal;
		try {
			exitVal = proc.waitFor();
			logger.info("ExitValue: " + exitVal);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
        

        
				logger.info("Calling script");
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		    logger.info("Print Test Line.");
		
			return "<status>" + output +"</status>"; 
	}
	
	
	
	
	@GET
	@Path("getonttree/{incomingXML}")
	@Produces({ MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
	public String postOnlyXMLonttree(@PathParam("incomingXML") String incomingXML) {
		
		String j = sql_onttree(incomingXML);
		
		//return "{\"name\":\"John\"}";
		
		/*
		return "{bindings: [ {ircEvent: PRIVMSG, method: newURI, regex: ; " +
        "{ircEvent: PRIVMSG, method: deleteURI, regex: d }," +
        "{ircEvent: PRIVMSG, method: randomURI, regex: r }" + 
        "]}";
        */
		
		return j;
		//return "{\"results\":[{\"optionDisplay\":\"ARMY / WARREN J GM\",\"optionValue\":1},{\"optionDisplay\":\"CIVILIANS\",\"optionValue\":2},{\"optionDisplay\":\"WOMEN\",\"optionValue\":3},{\"optionDisplay\":\"DIED OF WOUNDS\",\"optionValue\":4},{\"optionDisplay\":\"POST 1945\",\"optionValue\":5},{\"optionDisplay\":\"PRISONER OF WAR\",\"optionValue\":6},{\"optionDisplay\":\"SURVIVORS\",\"optionValue\":7},{\"optionDisplay\":\"SURVIVIORS\",\"optionValue\":8},{\"optionDisplay\":\"SURVIVOR\",\"optionValue\":9}]}";
	}
	
	
	
	
	@GET
	@Path("ont/{incomingXML}")
	@Produces({ MediaType.TEXT_HTML, MediaType.TEXT_PLAIN })
	public String postOnlyXMLont(@PathParam("incomingXML") String incomingXML) {
		
		String j = sql_test(incomingXML);
		
		//return "{\"name\":\"John\"}";
		
		/*
		return "{bindings: [ {ircEvent: PRIVMSG, method: newURI, regex: ; " +
        "{ircEvent: PRIVMSG, method: deleteURI, regex: d }," +
        "{ircEvent: PRIVMSG, method: randomURI, regex: r }" + 
        "]}";
        */
		
		return j;
		//return "{\"results\":[{\"optionDisplay\":\"ARMY / WARREN J GM\",\"optionValue\":1},{\"optionDisplay\":\"CIVILIANS\",\"optionValue\":2},{\"optionDisplay\":\"WOMEN\",\"optionValue\":3},{\"optionDisplay\":\"DIED OF WOUNDS\",\"optionValue\":4},{\"optionDisplay\":\"POST 1945\",\"optionValue\":5},{\"optionDisplay\":\"PRISONER OF WAR\",\"optionValue\":6},{\"optionDisplay\":\"SURVIVORS\",\"optionValue\":7},{\"optionDisplay\":\"SURVIVIORS\",\"optionValue\":8},{\"optionDisplay\":\"SURVIVOR\",\"optionValue\":9}]}";
	}
	
	
	
	List <TableAccess> tb_list = new ArrayList<TableAccess>();
	List <Ontology> o_list = new ArrayList<Ontology>();
	
	private String sql_test(String p_ids) {
		System.out.println("p_ids :" + p_ids);

		Connection conn = GetConnection.getSimpleConnectionMSSQL(db_url);
		Statement s;

		try {

			String query = "SELECT * from project1.dbo.TABLE_ACCESS";

			s = conn.createStatement();

			// execute the query, and get a java resultset
			ResultSet rs = s.executeQuery(query);
			int count = 0;

			// iterate through the java resultset
			while (rs.next()) {
				count++;
				String c1 = rs.getString("c_fullname");

				TableAccess tb = new TableAccess();
				tb.setC_table_cd(rs.getString("c_table_cd"));
				tb.setC_table_name(rs.getString("c_table_name"));
				tb.setC_protected_access( rs.getString("c_protected_access").charAt(0) );
				tb.setC_hlevel(rs.getString("c_hlevel").charAt(0));
				tb.setC_fullname(rs.getString("c_fullname"));
				tb.setC_name(rs.getString("c_name"));
				
				tb_list.add(tb);
			
				System.out.println(tb);
				
				
			}
			s.close();
			System.out.println(count + " rows were selected");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		 Gson myGson = new Gson();
		 
		 String j = myGson.toJson(tb_list);
		 System.out.println(j);
			
		 return j;
		 
	}
	

	private String sql_onttree(String p_ids) {
		System.out.println("p_ids :" + p_ids);

		Connection conn = GetConnection.getSimpleConnectionMSSQL(db_url);
		Statement s;

		try {

			String query = "SELECT * from project1.dbo.CATISSUE";

			s = conn.createStatement();

			// execute the query, and get a java resultset
			ResultSet rs = s.executeQuery(query);
			int count = 0;

			// iterate through the java resultset
			while (rs.next()) {
				count++;
				
				//get c_hlevel 0
				//get c_hlevel 1 mark as children

				Ontology o = new Ontology();
				
				o.setC_hlevel(rs.getInt("c_hlevel"));
				o.setC_fullname(rs.getString("c_fullname"));
				o.setC_name(rs.getString("c_name"));
				o.setC_synonym_cd(rs.getString("c_synonym_cd").charAt(0));
				o.setC_visualattributes(rs.getString("c_visualattributes"));
				o.setC_totalnum(rs.getInt("c_totalnum"));
				o.setC_basecode(rs.getString("c_basecode"));
				o.setC_metadataxml(rs.getString("c_metadataxml"));
				o.setC_facttablecolumn(rs.getString("c_facttablecolumn"));
				o.setC_tablename(rs.getString("c_tablename"));
				o.setC_columnname(rs.getString("c_columnname"));
				o.setC_columndatatype(rs.getString("c_columndatatype"));
				o.setC_operator(rs.getString("c_operator"));
				o.setC_dimcode(rs.getString("c_dimcode"));
				o.setC_comment(rs.getString("c_comment"));
				o.setC_tooltip(rs.getString("c_tooltip"));
				o.setUpdate_date(rs.getDate("update_date"));
				o.setDownload_date(rs.getDate("download_date"));
				o.setImport_date(rs.getDate("import_date"));
				o.setSourcesystem_cd(rs.getString("sourcesystem_cd"));
				o.setValuetype_cd(rs.getString("valuetype_cd"));
				
				o_list.add(o);
			
				System.out.println(o);
				
				
			}
			s.close();
			System.out.println(count + " rows were selected");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		 Gson myGson = new Gson();
		 
		 String j = myGson.toJson(tb_list);
		 System.out.println(j);
			
		 return j;
		 
	}
	
	

	
	
	



}