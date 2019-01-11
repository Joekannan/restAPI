# restAPI
Uploading project for evaluation

Background:

In this Maven project, a sample of java code has been written to validate few acceptance criteria for the given API

API = https://api.tmsandbox.co.nz/v1/Categories/6327/Details.json?catalogue=false

Project Name: restAcceptanceTest

Description : This project is to validate the acceptance criteria of the response of the given get method.

Executing the App.java class inside src/main folder, produces the desired result.

This is achieved using HttpClient and the received response is converted a string using EntityUtils.toString(httpResponse.getEntity())

The response String is again converted to JSON format for easy process using 
JSONObject jo = new JSONObject(result);

Then started reading the expected element and the corresponding values by implementing the required logics.

Comparing the expected values to actual values to print the Pass/Fail status.
