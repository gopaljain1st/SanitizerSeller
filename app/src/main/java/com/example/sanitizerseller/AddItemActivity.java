package com.example.sanitizerseller;

import android.Manifest;
import android.app.ProgressDialog;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AddItemActivity extends AppCompatActivity
{
    EditText itemName,itemMRP,itemOutPrice,itemWeight,itemStock,itemDescription;
    Spinner itemCategory;
    ImageView itemImage;
    Button addItem;
    Bitmap bitmap;
    String encodeImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);
        initComponent();
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Dexter.withActivity(AddItemActivity.this)
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


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemCategory.setAdapter(adapter);
        itemCategory.setSelection(0);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp=getSharedPreferences("seller",MODE_PRIVATE);
                final String seller_id=sp.getString("id","");
                if (itemName.getText().toString().trim().equals(""))
                    itemName.setError("Name Required");
                if (itemMRP.getText().toString().trim().equals(""))
                    itemMRP.setError("MRP Required");
                if (itemOutPrice.getText().toString().trim().equals(""))
                    itemOutPrice.setError("Out Price Required");
                if (itemWeight.getText().toString().trim().equals(""))
                    itemWeight.setError("Weight Required");
                if (itemStock.getText().toString().trim().equals(""))
                    itemStock.setError("Stock Required");
                if (itemDescription.getText().toString().trim().equals(""))
                    itemDescription.setError("Description Required");
                if(encodeImage==null)
                    Toast.makeText(AddItemActivity.this, "Image Required", Toast.LENGTH_SHORT).show();
                 if (!itemName.getText().toString().trim().equals("") && !itemMRP.getText().toString().trim().equals("") && !itemOutPrice.getText().toString().trim().equals("")
                        && !itemWeight.getText().toString().trim().equals("") && !itemStock.getText().toString().trim().equals("") && !itemDescription.getText().toString().trim().equals("")
                        && encodeImage!=null)
                {
                    final ProgressDialog pd = new ProgressDialog(AddItemActivity.this);
                    pd.setTitle("Sanitizer Seller");
                    pd.setMessage("Please Wait...");
                    pd.show();
                    String url = "https://digitalcafe.us/springbliss/addItem.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("item Inserted"))
                            {
                                pd.dismiss();
                                Toast.makeText(AddItemActivity.this, "Item Added Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else
                                Toast.makeText(AddItemActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pd.dismiss();
                            Toast.makeText(AddItemActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("item_name", itemName.getText().toString().trim());
                            map.put("item_mrp", itemMRP.getText().toString().trim());
                            map.put("item_outprice", itemOutPrice.getText().toString().trim());
                            map.put("item_weight", itemWeight.getText().toString().trim());
                            map.put("item_stock", itemStock.getText().toString().trim());
                            map.put("item_category", itemCategory.getSelectedItem().toString());
                            map.put("item_description", itemDescription.getText().toString().trim());
                            map.put("item_image", encodeImage);
                            map.put("id",seller_id);
                            return map;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(AddItemActivity.this, new HurlStack());
                    requestQueue.add(stringRequest).setRetryPolicy(new RetryPolicy() {
                        @Override
                        public int getCurrentTimeout() {
                            return 5000;
                        }

                        @Override
                        public int getCurrentRetryCount() {
                            return 0; //retry turn off
                        }

                        @Override
                        public void retry(VolleyError error) throws VolleyError {

                        }
                    });

                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null){

            Uri filepath=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                bitmap=getResizedBitmap(bitmap,1024);
                itemImage.setImageBitmap(bitmap);
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
        itemName=findViewById(R.id.itemName);
        itemMRP=findViewById(R.id.MRP);
        itemOutPrice=findViewById(R.id.outPrice);
        itemWeight=findViewById(R.id.weight);
        itemStock=findViewById(R.id.stock);
        itemDescription=findViewById(R.id.description);
        itemCategory=findViewById(R.id.selectCategory);
        itemImage=findViewById(R.id.addImage);
        addItem=findViewById(R.id.itemAddButton);
    }
}
