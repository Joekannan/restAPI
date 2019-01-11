package com.test.RestProgram.restAcceptanceTest;
import java.io.IOException;

import org.apache.http.HttpResponse;

import org.apache.http.HttpStatus;

import org.apache.http.client.ClientProtocolException;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.client.methods.HttpUriRequest;

import org.apache.http.impl.client.HttpClientBuilder;

import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import org.json.JSONObject;

import org.xml.sax.SAXException;

public class App 

{

    public static void main( String[] args ) throws SAXException

    {

    	// This is request which we are sending to server     	

    	String restURL_JSON = "https://api.tmsandbox.co.nz/v1/Categories/6327/Details.json?catalogue=false";    	 

    	try {
    		//Acceptance criteria 1  Name = "Carbon credits"
	    	testContentJSON(restURL_JSON,"Name","Carbon credits");
	    	//Acceptance criteria 2  CanRelist = "true"
	    	testContentJSON(restURL_JSON,"CanRelist","true");
	    	//Acceptance criteria 3  "Promotions" element with Name = "Gallery" has s Description that contains "2x larger image"
	    	testContentJSON(restURL_JSON,"Name","Gallery");

	    	} 

    	catch (Exception e) {
    		//Catch and print the exception
    		e.printStackTrace();

    		} 
    	}


    	public static void testContentJSON(String restURL, String element, String expectedValue) throws ClientProtocolException, IOException, JSONException  {
    		
	    	HttpUriRequest request = new HttpGet(restURL);
	    	
	    	//Send the request and receive the response
	    	HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
	    	
	    	//Validate the success status code = 200	
	    	if(httpResponse.getStatusLine().getStatusCode()== HttpStatus.SC_OK) {
	    		
	    		// Convert the response to a String format
		    	String result = EntityUtils.toString(httpResponse.getEntity());

		    	// Convert the result as a String to a JSON object to validate the given acceptance criteria		    	
		    	JSONObject jo = new JSONObject(result);
		    	
		    	//This condition is for JSON String type Key = Value pair
		    	if(jo.getString(element).trim().equalsIgnoreCase(expectedValue.trim())) {

		    		System.out.println(element +" = "+jo.getString(element));

		    	}

		    	else {
		    		//This condition to validate the values in JSON Array type Key = Value pair
		    		
		    		// Get the String of the Promotion element
		    		String promotion = jo.getString("Promotions");
		    		
		    		//Convert the inner string to an array 
			    	JSONArray jsonarray = new JSONArray(promotion);
			    	
			    	// Loop the array till the condition satisfied and break from the loop
			    	for (int i = 0; i < jsonarray.length(); i++) {

			    	    JSONObject jsonobject = jsonarray.getJSONObject(i);

			    	    String name = jsonobject.getString("Name").trim();
			    	    
			    	    //If the name is Gallery then, get the value of Description attribute to print it
			    	    if(name.equalsIgnoreCase(expectedValue)) {

			    	    	System.out.println(element +" = "+name + " in Promotions Element");

			    	    	String desc = jsonobject.getString("Description").trim();
			    	    	
			    	    	//If description contains \n, then split and retrieve the data for validation
			    	    	if(desc.contains("\n")) {
			    	    		
			    	    		String desclines[] = desc.split("\n");

				    	    	for(int iline = 0 ; iline < desclines.length; iline++) {

				    	    		if(desclines[iline].contains("2x larger image") ) {

						    	    	System.out.println("Description = " + desclines[iline] + " in Promotions Element");

						    	    	break;

						    	    }

				    	    	}
			    	    	}
			    	    	else {
			    	    		// If no \n, print the value as it is.
			    	    		if(desc.contains("2x larger image") ) {

					    	    	System.out.println("Description = " + desc + " in Promotions Element");
					    	    }
			    	    	}

			    	    }

			    	}

		    	}

	    	}

	    	else {
	    		//Return failure code if the response is not available 
	    		System.out.println("Getting failure status code." + httpResponse.getStatusLine().getStatusCode());

	    	}

	    }

   }

