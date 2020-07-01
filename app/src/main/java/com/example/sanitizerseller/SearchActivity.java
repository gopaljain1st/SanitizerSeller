package com.example.sanitizerseller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class SearchActivity extends AppCompatActivity
{
    EditText searchId;
    Button searchButton;
    String sellerId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        searchId = findViewById(R.id.searchId);
        searchButton = findViewById(R.id.searchButton);
        final SharedPreferences sp=getSharedPreferences("seller",MODE_PRIVATE);
        sellerId=sp.getString("id","");
        Log.e("id",sellerId);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchId.getText().toString().trim().equals(""))
                    searchId.setError("id Required");
                else {
                    final ProgressDialog pd = new ProgressDialog(SearchActivity.this);
                    pd.setTitle("Sanitizer Seller");
                    pd.setMessage("Please Wait...");
                    pd.show();
                    String url = "https://digitalcafe.us/springbliss/searchItem.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response)
                        {
                            pd.dismiss();
                            if(response.equals("Wrong Id"))
                                Toast.makeText(SearchActivity.this, "Wrong Id", Toast.LENGTH_SHORT).show();
                            else
                            {
                                Intent in = new Intent(SearchActivity.this, SearchActivityData.class);
                                in.putExtra("id", searchId.getText().toString());
                                startActivity(in);
                                finish();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pd.dismiss();
                            Toast.makeText(SearchActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("id",searchId.getText().toString());
                            map.put("sellerId",sellerId);
                            return map;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(SearchActivity.this, new HurlStack());
                    requestQueue.add(stringRequest);
                }
            }
        });
    }
}

