package com.example.sanitizerseller;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
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
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.sanitizerseller.modules.Item;

public class UpdateItemDataActivity extends AppCompatActivity
{
    ImageView updateImage;
    EditText updateName,updateMRP,updateOutPrice,updateStock,updateWeight,updateDescription;
    Spinner updateCategory;
    Button updateButton;
    Bitmap bitmap;
    String encodeImage=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);
        initComponent();
        final SharedPreferences sp=getSharedPreferences("seller",MODE_PRIVATE);
        final String sellerId=sp.getString("id","");

        final int id=Integer.parseInt(getIntent().getStringExtra("id"));
        final ProgressDialog pd = new ProgressDialog(UpdateItemDataActivity.this);
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
                    if (sucess.equals("1"))
                    {
                        pd.dismiss();
                        JSONObject object = jsonArray.getJSONObject(0);
                        String s=object.getString("item_image");
                        String u="https://digitalcafe.us/springbliss/images/"+s;
                        Item item=new Item(object.getString("id"),object.getString("item_name"),object.getString("item_mrp"),object.getString("item_outprice"),object.getString("item_weight"),object.getString("item_stock"),object.getString("item_description"),object.getString("item_category"),u);
                        Picasso.with(UpdateItemDataActivity.this).load(item.getItemImage()).resize(400,400).centerCrop().into(updateImage);
                        updateName.setText(item.getItemName());
                        updateMRP.setText(item.getItemMRP());
                        updateOutPrice.setText(item.getItemOutPrice());
                        updateStock.setText(item.getItemStock());
                        updateWeight.setText(item.getItemWeight());
                        updateDescription.setText(item.getItemDescription());
                        List<String> categorys = Arrays.asList(getResources().getStringArray(R.array.category_array));
                        int index=categorys.indexOf(item.getItemCategory());

                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(UpdateItemDataActivity.this,
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
                Toast.makeText(UpdateItemDataActivity.this, "" + error, Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(UpdateItemDataActivity.this, new HurlStack());
        requestQueue.add(stringRequest);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(UpdateItemDataActivity.this).setTitle("are you sure you want to update this item").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        final ProgressDialog pd = new ProgressDialog(UpdateItemDataActivity.this);
                        pd.setMessage("Uploading....");
                        pd.setTitle("Sanitizer Seller");
                        pd.show();
                        String url = "https://digitalcafe.us/springbliss/updateitem.php";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response)
                            {
                                pd.dismiss();
                                if(response.contains("item Updated"))
                                {
                                    Toast.makeText(UpdateItemDataActivity.this, "Item Updated Sucessfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else
                                    Toast.makeText(UpdateItemDataActivity.this, "Something Went Wrong Contact To Developer", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pd.dismiss();
                                Toast.makeText(UpdateItemDataActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();
                                map.put("id",""+id);
                                map.put("item_name",updateName.getText().toString().trim());
                                map.put("item_mrp",updateMRP.getText().toString().trim());
                                map.put("item_outprice",updateOutPrice.getText().toString().trim());
                                map.put("item_weight",updateWeight.getText().toString().trim());
                                map.put("item_stock",updateStock.getText().toString().trim());
                                map.put("item_category",updateCategory.getSelectedItem().toString());
                                map.put("item_description",updateDescription.getText().toString().trim());
                                if(encodeImage!=null)
                                map.put("item_image", encodeImage);
                                return map;
                            }
                        };
                        RequestQueue requestQueue= Volley.newRequestQueue(UpdateItemDataActivity.this);
                        requestQueue.add(stringRequest).setRetryPolicy(new RetryPolicy() {
                            @Override
                            public int getCurrentTimeout() {
                                return 50000;
                            }

                            @Override
                            public int getCurrentRetryCount() {
                                return 50000;
                            }

                            @Override
                            public void retry(VolleyError error) throws VolleyError {

                            }
                        });

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

        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Dexter.withActivity(UpdateItemDataActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Image"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null){

            Uri filepath=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                bitmap=getResizedBitmap(bitmap,1024);
                updateImage.setImageBitmap(bitmap);
                imageStore(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
    private void imageStore(Bitmap bitmap) {
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] imageBytes=stream.toByteArray();
        encodeImage= Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    void initComponent()
    {
        updateImage=findViewById(R.id.updateImage);
        updateName=findViewById(R.id.updateItemName);
        updateMRP=findViewById(R.id.updateMRP);
        updateOutPrice=findViewById(R.id.updateOutPrice);
        updateWeight=findViewById(R.id.updateWeight);
        updateStock=findViewById(R.id.updateStock);
        updateDescription=findViewById(R.id.updateDescription);
        updateCategory=findViewById(R.id.updateCategory);
        updateButton=findViewById(R.id.updateButton);
    }
}
