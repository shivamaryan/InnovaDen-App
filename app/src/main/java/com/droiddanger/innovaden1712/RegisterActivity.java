package com.droiddanger.innovaden1712;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;
import com.droiddanger.innovaden1712.app.AppConfig;
import com.droiddanger.innovaden1712.app.AppController;
import org.json.JSONException;
import org.json.JSONObject;
public class RegisterActivity extends AppCompatActivity {
    private Button register;
    private EditText name;
    private EditText email;
    private EditText mobile;
    private EditText password;
    private EditText password1;
    private String mob;
    private String pass;
    private String email1;
    private String name1;
    private String pass1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
        register = (Button) findViewById(R.id.button);
        name = (EditText) findViewById(R.id.nameid);
        email = (EditText) findViewById(R.id.emailid);
        mobile = (EditText) findViewById(R.id.phoneid);
        password = (EditText) findViewById(R.id.pass1id);
        password1 = (EditText) findViewById(R.id.pass2id);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                //startActivity(homeIntent);
                mob = mobile.getText().toString().trim();
                pass = password.getText().toString().trim();
                name1 = name.getText().toString().trim();
                email1 = email.getText().toString().trim();
                pass1 = password1.getText().toString().trim();
                checkLogin(mob, pass, name1, email1, pass1);
            }
        });
    }
    private void checkLogin(final String phone, final String password, final String name1, final String email1, final String password1) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");
                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String phone = user.getString("phone");
                        String password = user.getString("password");
                        String password1 = user.getString("password1");
                        // Inserting row in users table
                        // Launch main activity
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name1);
                params.put("email", email1);
                params.put("phone", mob);
                params.put("password", pass);
                return params;
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}