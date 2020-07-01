package com.example.sanitizerseller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import java.util.Timer;
import java.util.TimerTask;

import com.example.sanitizerseller.adapters.ViewPagerAdapter;

public class HomeActivity extends AppCompatActivity
{
    private int dotscount;
    private ImageView[] dots;
    ViewPagerAdapter viewPagerAdapter;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    Handler handler;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 3000;
    int currentPage=0;

    // username grocery pass grocery@123
    CardView addItem,seeItems,updateItem,deleteItem,orderList,searchItem;
    SharedPreferences sp=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        sp=getSharedPreferences("seller",MODE_PRIVATE);
        viewPager =  findViewById(R.id.flipper);

        sliderDotspanel =  findViewById(R.id.orderSliderDots);

        viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(this);

            dots[i].setImageResource(R.drawable.nonactive_dot);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }


        dots[0].setImageResource(R.drawable.active_dot);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageResource(R.drawable.nonactive_dot);
                }


                dots[position].setImageResource(R.drawable.active_dot);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        handler=new Handler();
        final  Runnable update=new Runnable() {
            @Override
            public void run() {
                if(currentPage == viewPagerAdapter.getCount())
                    currentPage=0;
                viewPager.setCurrentItem(currentPage++,true);
            }
        };
        timer =new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },DELAY_MS,PERIOD_MS);
        addItem=findViewById(R.id.addItem);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, AddItemActivity.class));
            }
        });

        seeItems=findViewById(R.id.seeItem);
        seeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, AllItemListActivity.class));
            }
        });

        updateItem=findViewById(R.id.updateItem);
        deleteItem=findViewById(R.id.deleteItem);
        updateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, UpdateActivity.class));
            }
        });
        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, DeleteActivity.class));
            }
        });
        searchItem=findViewById(R.id.searchItem);
        searchItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
            }
        });
        orderList=findViewById(R.id.seeOrder);
        orderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, OrderActivity.class));
            }
        });


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
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}