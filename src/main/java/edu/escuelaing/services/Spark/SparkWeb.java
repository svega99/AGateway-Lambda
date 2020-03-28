/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.services.Spark;

import java.io.BufferedReader;
import static java.lang.System.in;
import java.io.*;
import java.net.*;
import java.util.*;
import spark.*;
import static spark.Spark.*;
/**
 *
 * @author santiago.vega-r
 */
public class SparkWeb {
    public static void main( String[] args )
    {
        port(getPort());
          
        get("/data", (req, res) -> inputDataPage(req, res));
        get("/results", (req, res) -> resultsPage(req, res));
   
    }
    
     private static String inputDataPage(Request req, Response res) {
        String pageContent
                = "<!DOCTYPE html>"
                + "<html>"
                + "<body>"
                + "<h2>Square calculation with Amazon Lambda and Amazon API Gateway</h2>"
                + "<form action=\"/results\">"
                + "  Enter a number <br>"
                + "  <input type=\"number\" name=\"number\" >"
				 + "  <input type=\"submit\" value=\"Submit\"> "
                + "  <br><br>"
                + "</form>"
                + "<p>If you click the \"Submit\" button, the form-data will be sent to a page called \"/results\".</p>"
                + "</body>"
                + "</html>";
        return pageContent;
    }

    private static String resultsPage(Request req, Response res) throws IOException {
        
        BufferedReader in = null;

       String request= req.queryParams("number");
       URL API = new URL("https://vae1jz8nf4.execute-api.us-east-1.amazonaws.com/Beta?value="+request);
		
        URLConnection con = API.openConnection();
        
        String result = "";
        in = new BufferedReader(new InputStreamReader( con.getInputStream()));
		
		System.err.println("Conectado");
		BufferedReader stdIn = new BufferedReader(
		new InputStreamReader(System.in));
		String line;
                
		while ((line = in.readLine()) != null) { 
                        result=result+line;
			System.out.println(line); 
		}
        
        
        String pageContent
                = "<!DOCTYPE html>"
                + "<html>"
                + "<body>"
                + "<h3>Square: "+ result+" </h3>"
                + "</body>"
                + "</html>";
        
        return pageContent;
    }
    
    
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }        
           
        return 4567; //returns default port if heroku-port isn't set(i.e. on localhost)    }
    }
}
