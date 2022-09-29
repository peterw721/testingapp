package org.peterwhyte;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.*;

/**
 * @author Peter Whyte
 * @ for Assurity Consulting 2023 Graduate Programme
 * @ description technical assessment to create test automation for task involving
 *              acceptance criteria
 * @ date 28/09/2022
 */
public class App {
    // get data back from tsmsandbox in form os a string
    static String tmsData = getTmsSandboxJSON();

    // transform string data back into JSON Object
    static JSONObject tmsJson = new JSONObject( tmsData );

    public static String getArray( String srchArr, String srchNm, String srchFeat, String srchDesc ) {
        // look through an array contained in JSON to find  a value associated to a Promotions element
        // with Name = "Feature" that has a Description that contains a set text value
        try {
            JSONArray promotions = tmsJson.getJSONArray( srchArr );
            for(int i=0; i<promotions.length(); i++) {
                JSONObject promo = promotions.getJSONObject(i);
                String promoName = promo.getString( srchNm );
                if(promoName.equals( srchFeat )) {
                    return promo.getString( srchDesc );
                }
            }
        }
        // if the specified key is not found -> return not_found
        catch( org.json.JSONException je ) 	{ return( "not_found"); }
        catch( Exception e ) 					{ e.printStackTrace(); }
        return null;
    }
    public static String getName( String searchString ) {
        // look through a JSON object and return the value associated with a key value
        try {
            return tmsJson.getString( searchString );
        }
        // if the specified key is not found -> return not_found
        catch( org.json.JSONException je ) 	{ return( "not_found" ); }
        catch( Exception e ) 					{ e.printStackTrace(); }
        return null;
    }
    public static Status getBool( String searchString ) {
        // look through a JSON object and return a Boolean for specified search parameter
        try {
            if ( tmsJson.getBoolean(searchString )) { return Status.TRUE;  }
        }
        //
        catch( org.json.JSONException je ) 	{ return( Status.NOT_FOUND ); }
        catch( Exception e)  					{ e.printStackTrace(); }
        return null;
    }
    private static String getTmsSandboxJSON() {
        /*
        * This is a web server which receives a request to connect to tmsandbox API endpoint and pass on the
        * request for Details data
         */
        try {
            URL url = new URL("https://api.tmsandbox.co.nz/v1/Categories/6328/Details.json?catalogue=false");
            HttpURLConnection conn = ( HttpURLConnection ) url.openConnection();
            conn.setRequestMethod( "GET" );
            conn.connect();

            //Getting the response code
            int responsecode = conn.getResponseCode();

            if ( responsecode != 200 ) {
                throw new RuntimeException( "HttpResponseCode: " + responsecode );
            } else {
                StringBuilder inline = new StringBuilder();
                Scanner scanner = new Scanner( url.openStream() );

                //Write all the JSON data into a string using a scanner
                while ( scanner.hasNext() ) {
                    inline.append( scanner.nextLine() );
                }
                //Close the scanner
                scanner.close();
                return inline.toString();
            }

        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return "";
    }
    public enum Status {
        // use enum instead of primitive booleans to return a value when a search string not found
        TRUE,
        NOT_FOUND
    }
}
