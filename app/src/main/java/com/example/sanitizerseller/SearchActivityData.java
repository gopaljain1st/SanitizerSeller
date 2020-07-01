package com.example.sanitizerseller;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.sanitizerseller.modules.Item;

public class SearchActivityData extends AppCompatActivity
{
    ImageView searchImage;
    EditText searchName,searchMRP,searchOutPrice,searchStock,searchWeight,searchDescription;
    Spinner searchCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_item_data);
        initComponent();
        final SharedPreferences sp=getSharedPreferences("seller",MODE_PRIVATE);
        final String sellerId=sp.getString("id","");

        final int id=Integer.parseInt(getIntent().getStringExtra("id"));
        final ProgressDialog pd = new ProgressDialog(SearchActivityData.this);
        pd.setTitle("Sanitizer Seller");
        pd.setMessage("Please Wait...");
        pd.show();
        String url = "https://digitalcafe.us/springbliss/searchItem.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String sucess = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (sucess.equals("1")) {
                        pd.dismiss();
                            JSONObject object = jsonArray.getJSONObject(0);
                        String s=object.getString("item_image");
                        String u="https://digitalcafe.us/springbliss/images/"+s;
                        Item item=new Item(object.getString("id"),object.getString("item_name"),object.getString("item_mrp"),object.getString("item_outprice"),object.getString("item_weight"),object.getString("item_stock"),object.getString("item_description"),object.getString("item_category"),u);
                        Picasso.with(SearchActivityData.this).load(item.getItemImage()).resize(400,400).centerCrop().into(searchImage);
                        searchName.setText(item.getItemName());
                        searchMRP.setText(item.getItemMRP());
                        searchOutPrice.setText(item.getItemOutPrice());
                        searchStock.setText(item.getItemStock());
                        searchWeight.setText(item.getItemWeight());
                        searchDescription.setText(item.getItemDescription());
                        List<String> categorys = Arrays.asList(getResources().getStringArray(R.array.category_array));
                        int index=categorys.indexOf(item.getItemCategory());

                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(SearchActivityData.this,
                                R.array.category_array, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        searchCategory.setAdapter(adapter);
                        searchCategory.setSelection(index);
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
                Toast.makeText(SearchActivityData.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id",""+id);
                map.put("sellerId",sellerId);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SearchActivityData.this, new HurlStack());
        requestQueue.add(stringRequest);
    }

    void initComponent()
    {
        searchImage=findViewById(R.id.searchImage);
        searchName=findViewById(R.id.searchItemName);
        searchMRP=findViewById(R.id.searchMRP);
        searchOutPrice=findViewById(R.id.searchOutPrice);
        searchWeight=findViewById(R.id.searchWeight);
        searchStock=findViewById(R.id.searchStock);
        searchDescription=findViewById(R.id.searchDescription);
        searchCategory=findViewById(R.id.searchCategory);
    }
}
