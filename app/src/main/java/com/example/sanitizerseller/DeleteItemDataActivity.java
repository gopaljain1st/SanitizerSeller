package com.example.sanitizerseller;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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

public class DeleteItemDataActivity extends AppCompatActivity
{
    ImageView updateImage;
    EditText updateName,updateMRP,updateOutPrice,updateStock,updateWeight,updateDescription;
    Spinner updateCategory;
    Button updateButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_data);
        initComponent();
        final SharedPreferences sp=getSharedPreferences("seller",MODE_PRIVATE);
        final String sellerId=sp.getString("id","");

        final int id=Integer.parseInt(getIntent().getStringExtra("id"));
        final ProgressDialog pd = new ProgressDialog(DeleteItemDataActivity.this);
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
                        Picasso.with(DeleteItemDataActivity.this).load(item.getItemImage()).resize(400,400).centerCrop().into(updateImage);
                        updateName.setText(item.getItemName());
                        updateMRP.setText(item.getItemMRP());
                        updateOutPrice.setText(item.getItemOutPrice());
                        updateStock.setText(item.getItemStock());
                        updateWeight.setText(item.getItemWeight());
                        updateDescription.setText(item.getItemDescription());
                        List<String> categorys = Arrays.asList(getResources().getStringArray(R.array.category_array));
                        int index=categorys.indexOf(item.getItemCategory());

                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(DeleteItemDataActivity.this,
                                R.array.category_array, android.R.layout.simple_spinner_item);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        updateCategory.setAdapter(adapter);
                        updateCategory.setSelection(index);
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
                Toast.makeText(DeleteItemDataActivity.this, "" + error, Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(DeleteItemDataActivity.this, new HurlStack());
        requestQueue.add(stringRequest);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                AlertDialog alertDialog = new AlertDialog.Builder(DeleteItemDataActivity.this).setTitle("are you sure you want to delete this item").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                        final ProgressDialog pd = new ProgressDialog(DeleteItemDataActivity.this);
                        pd.setTitle("Sanitizer Seller ");
                        pd.setMessage("Please Wait...");
                        pd.show();
                        String url = "https://digitalcafe.us/springbliss/delete_item.php";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response)
                            {
                                if(response.contains("not deleted"))
                                    Toast.makeText(DeleteItemDataActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                                else
                                {
                                    Toast.makeText(DeleteItemDataActivity.this, "Item deleted Successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pd.dismiss();
                                Toast.makeText(DeleteItemDataActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();
                                map.put("id",""+id);
                                return map;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(DeleteItemDataActivity.this, new HurlStack());
                        requestQueue.add(stringRequest);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                       finish();
                    }
                }).show();
            }
        });
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    void initComponent()
    {
        updateImage=findViewById(R.id.deleteImage);
        updateName=findViewById(R.id.deleteItemName);
        updateMRP=findViewById(R.id.deleteMRP);
        updateOutPrice=findViewById(R.id.deleteOutPrice);
        updateWeight=findViewById(R.id.deleteWeight);
        updateStock=findViewById(R.id.deleteStock);
        updateDescription=findViewById(R.id.deleteDescription);
        updateCategory=findViewById(R.id.deleteCategory);
        updateButton=findViewById(R.id.deleteButton);
    }
}
