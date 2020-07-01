package com.example.sanitizerseller.fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.sanitizerseller.R;
import com.example.sanitizerseller.adapters.OutForDeliveryAdapter;
import com.example.sanitizerseller.modules.Order;

public class OutForDelivery extends Fragment {
    RecyclerView rv;
    ArrayList<Order> al;
    RecyclerView.Adapter<OutForDeliveryAdapter.orderAdapterViewHolder>adapter;
    LinearLayoutManager linearLayoutManager;
    SharedPreferences sp=null;
    private static final String apiurl="https://digitalcafe.us/springbliss/orderList.php";
    TextView displayText;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.order_items,container,false);
        rv=view.findViewById(R.id.allItemsRv);
        linearLayoutManager=new LinearLayoutManager(getContext());
        rv.setLayoutManager(linearLayoutManager);
        al=new ArrayList<>();
        sp=getActivity().getSharedPreferences("seller",getActivity().MODE_PRIVATE);
        displayText=view.findViewById(R.id.displayText);


        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Sanitizer Seller");
        pd.setMessage("please wait...");
        pd.show();
        StringRequest request=new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String sucess = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if(jsonArray.length()==0)
                        displayText.setVisibility(View.VISIBLE);

                    if (sucess.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            al.add(new Order(object.getString("id"),object.getString("orderId"),object.getString("itemId"),object.getString("name"),object.getString("address"),object.getString("mobile"),object.getString("itemName"),object.getString("totalAmount"),object.getString("orderStatus"),object.getString("timeOfOrder"),object.getString("dateOfOrder")));
                        }
                        adapter=new OutForDeliveryAdapter(getContext(),al);
                        rv.setAdapter(adapter);
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
                Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("status","Out_For_Delivery");
                map.put("sellerId",sp.getString("id",""));
                return map;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(request);
        return view;
    }
}
