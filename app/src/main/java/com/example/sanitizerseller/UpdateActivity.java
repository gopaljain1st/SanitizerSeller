package com.example.sanitizerseller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity
{
    EditText id;
    Button searchItem;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);
        id=findViewById(R.id.updateId);
        searchItem=findViewById(R.id.updateSearch);
        final SharedPreferences sp=getSharedPreferences("seller",MODE_PRIVATE);
        final String sellerId=sp.getString("id","");

        searchItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.getText().toString().trim().equals(""))
                        id.setError("id Required");
                else
                    {
                        final ProgressDialog pd = new ProgressDialog(UpdateActivity.this);
                        pd.setTitle("Sanitizer Seller");
                        pd.setMessage("Please Wait...");
                        pd.show();
                        String url = "https://digitalcafe.us/springbliss/searchItem.php";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response)
                            { pd.dismiss();
                                if(response.equals("Wrong Id"))
                                    Toast.makeText(UpdateActivity.this, "Wrong Id", Toast.LENGTH_SHORT).show();
                                else
                                {
                                    Intent in=new Intent(UpdateActivity.this,UpdateItemDataActivity.class);
                                    in.putExtra("id",id.getText().toString());
                                    startActivity(in);
                                    finish();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pd.dismiss();
                                Toast.makeText(UpdateActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();
                                map.put("id",id.getText().toString());
                                map.put("sellerId",sellerId);
                                return map;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(UpdateActivity.this, new HurlStack());
                        requestQueue.add(stringRequest);
                    }
            }
        });
    }
}
