package com.example.sanitizerseller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
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

public class WaitingActivity extends AppCompatActivity
{
    SharedPreferences sp=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting_layout);
        sp=getSharedPreferences("seller",MODE_PRIVATE);
        final ProgressDialog pd = new ProgressDialog(WaitingActivity.this);
        pd.setTitle("Sanitizer Seller");
        pd.setMessage("Please Wait...");
        pd.show();
        String url="https://digitalcafe.us/springbliss/checkValidation.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                pd.dismiss();
                if(response.equals("Valid"))
                {
                    Toast.makeText(WaitingActivity.this, "Congratulation Your Id Verified", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("valid","true");
                    editor.commit();
                    startActivity(new Intent(WaitingActivity.this,HomeActivity.class));
                    finish();
                }else
                {
                    Toast.makeText(WaitingActivity.this, "Ask Admin To Verified Your Id", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                pd.dismiss();
                Toast.makeText(WaitingActivity.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String > map=new HashMap<>();
                map.put("id",sp.getString("id",""));
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(WaitingActivity.this);
        requestQueue.add(stringRequest);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                SharedPreferences.Editor edit = sp.edit();
                edit.remove("id");
                edit.remove("name");
                edit.remove("mobile");
                edit.remove("email");
                edit.remove("password");
                edit.remove("shopName");
                edit.remove("shopAddress");
                edit.remove("valid");
                edit.commit();
                startActivity(new Intent(WaitingActivity.this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
