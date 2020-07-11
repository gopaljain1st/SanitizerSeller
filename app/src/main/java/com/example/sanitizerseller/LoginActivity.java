package com.example.sanitizerseller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText etLoginGmail,etLoginPassword;
    private Button loginButton;
    SharedPreferences sp=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp=getSharedPreferences("seller",MODE_PRIVATE);
        etLoginGmail = findViewById(R.id.etLogGmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        loginButton = findViewById(R.id.btnLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final String email = etLoginGmail.getText().toString().trim();
                final String password = etLoginPassword.getText().toString().trim();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Enter your Email and Password to login", Toast.LENGTH_SHORT).show();
                } else {
                    final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                    pd.setTitle("Sanitizer Seller");
                    pd.setMessage("Please Wait....");
                    pd.show();
                    String url="https://digitalcafe.us/springbliss/sellerLogin.php";
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response)
                        {
                            pd.dismiss();
                            if(response.equalsIgnoreCase("login failed"))
                            {
                                Toast.makeText(LoginActivity.this, "Please Enter Correct Id And Password", Toast.LENGTH_SHORT).show();
                            }
                           else {
                                String arr[] = response.split(",");
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("id", arr[0].trim());
                                editor.putString("name", arr[1].trim());
                                editor.putString("mobile", arr[2].trim());
                                editor.putString("shopName", arr[3].trim());
                                editor.putString("shopAddress", arr[4].trim());
                                editor.putString("email", email);
                                editor.putString("password", password);
                                editor.putString("valid", arr[5].trim());
                                if(!arr[6].equals("1"))
                                    editor.putString("token",arr[6]);
                                editor.commit();
                                if (arr[5].trim().equals("true"))
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                else
                                    startActivity(new Intent(LoginActivity.this, WaitingActivity.class));
                                finish();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            pd.dismiss();
                            Toast.makeText(LoginActivity.this, ""+error, Toast.LENGTH_SHORT).show();

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String ,String > map=new HashMap<>();
                            map.put("email",email);
                            map.put("password",password);
                            return map;
                        }
                    };
                    RequestQueue requestQueue= Volley.newRequestQueue(LoginActivity.this);
                    requestQueue.add(stringRequest);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        sp=getSharedPreferences("seller",MODE_PRIVATE);
        String seller_id=sp.getString("id","no");
        if(sp.getString("valid","").equals("true")&&!seller_id.equals("no"))
        {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
        else if(!seller_id.equals("no")&&!sp.getString("valid","").equals("true"))
        {
            startActivity(new Intent(LoginActivity.this,WaitingActivity.class));
            finish();
        }
    }
}
