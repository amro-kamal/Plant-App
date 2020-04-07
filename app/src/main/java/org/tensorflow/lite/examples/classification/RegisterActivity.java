package org.tensorflow.lite.examples.classification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.examples.classification.utils.MyPreferences;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText username,useremail,userpassword;
    ProgressBar reg_pb;
    Button regBtn;
    RequestQueue requestqueue;
    TextView logintv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        username=(EditText) findViewById(R.id.user_name);
        useremail=(EditText) findViewById(R.id.user_email);
        userpassword=(EditText) findViewById(R.id.signup_password_et);
        regBtn=(Button) findViewById(R.id.singup_btn);
        reg_pb=(ProgressBar) findViewById(R.id.signup_Pb);
        logintv=(TextView) findViewById(R.id.reg_to_login);


        requestqueue = Volley.newRequestQueue(this);

        regBtn.setOnClickListener(view -> {
            register();
        });

        logintv.setOnClickListener(v->{
            Intent intent=new Intent(this,RegisterActivity.class);
            startActivity(intent);
        });
        }

public void register(){
//signup here
reg_pb.setVisibility(View.VISIBLE);

final String name=username.getText().toString().trim();
final String email=useremail.getText().toString().trim();
final String password=userpassword.getText().toString().trim();

final String url = NetworkingLab.END_POINT + "register";

        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("name", name);
        postParam.put("email", email);
        postParam.put("password", password);

    JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
        url, new JSONObject(postParam),
        new Response.Listener<JSONObject>() {

@Override
public void onResponse(JSONObject response) {
        try {
        String success=response.getString("message");
        if(success.equals("success")){
        Log.d("kkkk", "reg succes");

        Toast.makeText(RegisterActivity.this,"Log in success",Toast.LENGTH_LONG).show();
        boolean isFirstTime = MyPreferences.isFirstSelection(RegisterActivity.this);
        if (isFirstTime) {
        Intent intent=new Intent(getApplicationContext(), modelSelectionActivity.class);
        startActivity(intent);                    }
        else{
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        }

        }
        else if(success.equals("faild")){
        Log.d("kkkk", "reg success");
        Toast.makeText(RegisterActivity.this,"registeration faild",Toast.LENGTH_LONG).show();
        reg_pb.setVisibility(View.GONE);
        }


        } catch (JSONException e) {
        Log.d("kkkk", "reg error was catched");
        e.printStackTrace();
        reg_pb.setVisibility(View.GONE);
        Toast.makeText(RegisterActivity.this,"Registeration Error",Toast.LENGTH_LONG).show();
        }


        }
        }, new Response.ErrorListener()   {

  @Override
  public void onErrorResponse(VolleyError error) {
        Log.d("kkkk", "reg error");
        reg_pb.setVisibility(View.GONE);
        Toast.makeText(RegisterActivity.this,"Log in Error",Toast.LENGTH_LONG).show();
            }
        }) {


public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        return headers;
        }

        };

        requestqueue.add(jsonObjReq);
           }


}






//@Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("name", "amro");
//                        params.put("password", "889656");
//
//
//
//                        return params;
//                    }