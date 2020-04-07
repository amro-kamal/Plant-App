package org.tensorflow.lite.examples.classification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.examples.classification.utils.MyPreferences;

import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends AppCompatActivity {
    EditText username,userpassword;
    ProgressBar login_pb;
    Button loginBtn;
    TextView regtv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        username=(EditText) findViewById(R.id.user_et);
        userpassword=(EditText) findViewById(R.id.login_password_et);
        loginBtn=(Button) findViewById(R.id.login_btn);
        login_pb=(ProgressBar) findViewById(R.id.login_Pb);
        regtv=(TextView) findViewById(R.id.login_to_register);

        RequestQueue requestqueue = Volley.newRequestQueue(this);

        loginBtn.setOnClickListener(view -> {
            //login here
            login_pb.setVisibility(View.VISIBLE);
            final String name=username.getText().toString().trim();
            final String password=userpassword.getText().toString().trim();


            final String url = NetworkingLab.END_POINT + "login";
            Map<String, String> postParam= new HashMap<String, String>();
            postParam.put("name", name);
            postParam.put("password", password);
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    url, new JSONObject(postParam),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                                    try {
                                        String success=response.getString("message");
                                        if(success.equals("success")){
                                            Toast.makeText(LogInActivity.this,"Log in success",Toast.LENGTH_LONG).show();
                                            boolean isFirstTime = MyPreferences.isFirstSelection(LogInActivity.this);
                                            if (isFirstTime) {
                                                Intent intent=new Intent(getApplicationContext(), modelSelectionActivity.class);
                                                startActivity(intent);                    }
                                            else{
                                                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
                                            }

                                        }
                                        else if(success.equals("not registered")){
                                            Toast.makeText(LogInActivity.this,"You don't have an account yet",Toast.LENGTH_LONG).show();
                                            login_pb.setVisibility(View.GONE);

                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        login_pb.setVisibility(View.GONE);
                                        Toast.makeText(LogInActivity.this,"Log in Error",Toast.LENGTH_LONG).show();

                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            login_pb.setVisibility(View.GONE);
                            Toast.makeText(LogInActivity.this,"Log in Error",Toast.LENGTH_LONG).show();


                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json; charset=utf-8");
                            return headers;
                        }

                    };

// Add the request to the RequestQueue.
            requestqueue.add(jsonObjReq);
        });


        regtv.setOnClickListener(v->{
            Intent intent=new Intent(this,RegisterActivity.class);
            startActivity(intent);
        });

    }
    }
