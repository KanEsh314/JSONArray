package my.edu.itrain.example.heck;

import android.content.res.Resources;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CardDetails extends AppCompatActivity {

    // Will show the string "data" that holds the results
    TextView results;
    // URL of object to be parsed
    String JsonURL = "https://sheetsu.com/apis/v1.0/c7df39a121a5";
    // This string will hold the results
    String data = "";
    // Defining the Volley request queue that handles the URL request concurrently
    RequestQueue requestQueue;
    private String jsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);

        //Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(this);

        //Casts results into the TextView found within the main layout XML with id jsonData
        results = (TextView) findViewById(R.id.jsonData);

        // Creating the JsonObjectRequest class called obreq, passing required parameters:
        //GET is used to fetch data from the server, JsonURL is the URL to be fetched from.
        JsonArrayRequest obreq = new JsonArrayRequest(Request.Method.GET, JsonURL,null,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONArray>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            jsonResponse = "";
                            for(int i = 0; i < response.length();i++)
                            {
                                JSONObject jsonObject = (JSONObject)response.get(i);

                                //Retrieves the string labeled " " and " " , and covert them into javascript objects
                                String courseName = jsonObject.getString("course_name");
                                String sdesc = jsonObject.getString("simple_desc");

                                jsonResponse += "Course Name: " + courseName + "\n\n";
                                jsonResponse += "Detail: " + sdesc + "\n\n";
                            }

                            //Adds the data string to the TextView "results"
                            results.setText(jsonResponse);
                        }
                        //Try and Catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                //The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    //Handle errors that occur due to volley
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("Test","Error : " +error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        //Adds the JSON object request "obreq" to the request queue
        requestQueue.add(obreq);

    }
}
