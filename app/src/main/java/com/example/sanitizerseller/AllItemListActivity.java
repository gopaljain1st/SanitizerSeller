package com.example.sanitizerseller;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.sanitizerseller.adapters.ItemListAdapter;
import com.example.sanitizerseller.modules.Item;

public class AllItemListActivity extends AppCompatActivity
{
    RecyclerView allItemsRV;
    RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder>adapter;
    LinearLayoutManager manager;
    ArrayList<Item>al;
    TextView displayText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_items_list);
        allItemsRV=findViewById(R.id.allItemsRv);
        al=new ArrayList<>();
        manager=new LinearLayoutManager(this);
        displayText=findViewById(R.id.displayText);

        SharedPreferences sp=getSharedPreferences("seller",MODE_PRIVATE);
        final String seller_id=sp.getString("id","");
        final ProgressDialog pd = new ProgressDialog(AllItemListActivity.this);
        pd.setTitle("Sanitizer Seller");
        pd.setMessage("Please Wait...");
        pd.show();
        String url = "https://digitalcafe.us/springbliss/fetchSellerItem.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String sucess = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if(jsonArray.length()==0)
                        displayText.setVisibility(View.VISIBLE);

                    if (sucess.equals("1")) {
                        pd.dismiss();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String s=object.getString("item_image");
                            String u="https://digitalcafe.us/springbliss/images/"+s;
                            al.add(new Item(object.getString("id"),object.getString("item_name"),object.getString("item_mrp"),object.getString("item_outprice"),object.getString("item_weight"),object.getString("item_stock"),object.getString("item_description"),object.getString("item_category"),u));
                        }
                        adapter=new ItemListAdapter(AllItemListActivity.this,al);
                        allItemsRV.setLayoutManager(manager);
                        allItemsRV.setAdapter(adapter);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(AllItemListActivity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("sellerId",seller_id);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AllItemListActivity.this, new HurlStack());
        requestQueue.add(stringRequest);
    }
}
