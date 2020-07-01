package com.example.sanitizerseller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import com.example.sanitizerseller.fragments.Delivered;
import com.example.sanitizerseller.fragments.OutForDelivery;
import com.example.sanitizerseller.fragments.Packed;
import com.example.sanitizerseller.fragments.Recieved;
import com.example.sanitizerseller.fragments.Shipped;

public class OrderActivity extends AppCompatActivity
{

    FragmentTransaction transaction;
    FragmentManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity);
        TabLayout tabLayout =findViewById(R.id.tablayout);
        final ViewPager viewPager=findViewById(R.id.viewpager);
        ViewPageAdapter viewPageAdapter=new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.addFragment(new Recieved(),"Recieved Order");
        viewPageAdapter.addFragment(new Packed(),"Packed Order");
        viewPageAdapter.addFragment(new Shipped(),"Shipeed Order");
        viewPageAdapter.addFragment(new OutForDelivery(),"Out For Delivery");
        viewPageAdapter.addFragment(new Delivered(),"Order Delivered");
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);

        manager=getSupportFragmentManager();
        transaction=manager.beginTransaction();
        transaction.replace(R.id.orderll,new Recieved());
        transaction.commit();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                if(tab.getText().toString().equals("Recieved Order"))
                {
                    transaction=manager.beginTransaction();
                    transaction.replace(R.id.orderll,new Recieved());
                    transaction.commit();
                }
                else if(tab.getText().toString().equals("Out For Delivery"))
                {

                    transaction=manager.beginTransaction();
                    transaction.replace(R.id.orderll,new OutForDelivery());
                    transaction.commit();
                }
                else if(tab.getText().toString().equals("Order Delivered"))
                {
                    transaction=manager.beginTransaction();
                    transaction.replace(R.id.orderll,new Delivered());
                    transaction.commit();
                }
                else if(tab.getText().toString().equals("Packed Order"))
                {
                    transaction=manager.beginTransaction();
                    transaction.replace(R.id.orderll,new Packed());
                    transaction.commit();
                }
                else if(tab.getText().toString().equals("Shipeed Order"))
                {
                    transaction=manager.beginTransaction();
                    transaction.replace(R.id.orderll,new Shipped());
                    transaction.commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {
            }
        });

    }
    class ViewPageAdapter extends FragmentPagerAdapter
    {
        private ArrayList<Fragment> fragments;
        private  ArrayList<String> titles;
        ViewPageAdapter(FragmentManager fragmentManager)
        {
            super(fragmentManager);
            this.fragments=new ArrayList<>();
            this.titles=new ArrayList<>();
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
        public void addFragment(Fragment fragment, String title)
        {
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
