package com.example.sanitizerseller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private Button registerBtn;
    TextView gotoLoginBtn;
    SharedPreferences sp;
    private EditText regName,regPhone,regGmail,regPassword,regShopName,regShopAddress,cpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sp=getSharedPreferences("seller",MODE_PRIVATE);

        registerBtn = findViewById(R.id.btnRegLogin);
        gotoLoginBtn = findViewById(R.id.btnGotoLogin);
        regName = findViewById(R.id.etRegName);
        regPhone = findViewById(R.id.etRegPhone);
        regGmail = findViewById(R.id.etRegGmail);
        regPassword = findViewById(R.id.etRegPassword);
        regShopName = findViewById(R.id.etShopeName);
        regShopAddress = findViewById(R.id.etShopAddress);
        cpassword=findViewById(R.id.cpassword);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final String fname = regName.getText().toString().trim();
                final String fPhone = regPhone.getText().toString().trim();
                final String fGmail = regGmail.getText().toString().trim();
                final String fPassword = regPassword.getText().toString().trim();
                final String fShopName = regShopName.getText().toString().trim();
                final String fShopAddress = regShopAddress.getText().toString().trim();
                if(!cpassword.getText().toString().trim().equals(fPassword))
                    cpassword.setError("Password Not Matched");
                else if (fname.isEmpty() || fPassword.isEmpty() || fGmail.isEmpty() || fPhone.isEmpty() || fShopAddress.isEmpty() || fShopName.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                } else {
                    final ProgressDialog pd=new ProgressDialog(RegisterActivity.this);
                    pd.setTitle("Sanitizer Seller");
                    pd.setMessage("Please Wait...");
                    pd.show();
                    String url="https://digitalcafe.us/springbliss/registerSeller.php";
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response)
                        {
                            pd.dismiss();
                            if(response.equals("register done"))
                            {
                                Toast.makeText(RegisterActivity.this, "Registration Successfully Now Login In Your Account", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                finish();
                            }
                            else
                                Toast.makeText(RegisterActivity.this, ""+response, Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pd.dismiss();
                            Toast.makeText(RegisterActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String ,String > map=new HashMap<>();
                            map.put("name",fname);
                            map.put("mobile",fPhone);
                            map.put("gmail",fGmail);
                            map.put("shopName",fShopName);
                            map.put("shopAddress",fShopAddress);
                            map.put("password",fPassword);
                            map.put("valid","false");
                            map.put("token", FirebaseInstanceId.getInstance().getToken());
                            return map;
                        }
                    };
                    RequestQueue requestQueue= Volley.newRequestQueue(RegisterActivity.this);
                    requestQueue.add(stringRequest);
            }
            }
        });

        gotoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
