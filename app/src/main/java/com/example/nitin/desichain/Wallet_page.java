package com.example.nitin.desichain;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nitin.desichain.SubCategoryList.ShowCategoryAdapeter;
import com.example.nitin.desichain.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.nitin.desichain.MainActivity.arrayList;
import static com.example.nitin.desichain.R.id.helpcenter;
import static com.example.nitin.desichain.R.id.myaccount;
import static com.example.nitin.desichain.R.id.mycart;
import static com.example.nitin.desichain.R.id.ratedesichain;
import static com.example.nitin.desichain.R.id.subscribe;

/**
 * Created by Karan on 06-07-2017.
 */

public class Wallet_page extends AppCompatActivity implements View.OnClickListener{

    private TextView amountTextView;
    private Button shopButton;
    private int amount =2500;
    private Toolbar mToolbar;
    TextView txtViewCount;
    ImageView  toolbarcartimage;
    DrawerLayout drawer;
    View headerView;
    public static ArrayList<CategoryHolder> arrayList;
    public static ArrayList<String> Books;
    public static ArrayList<String> Poojaitem;
    public static ArrayList<String> Homecare;
    public static ArrayList<String> others;
    public static HashMap<String, ArrayList<String>> hashMap;
    private Helper listView;
    NestedScrollView nestedScrollView;
    LinearLayout myorder,mycart,myaccount,helpcenter,ratedesichain,productPage,policy,facebook,google,twitter,pinterest,youtube,instagram,aboutus,subscribe;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem=menu.findItem(R.id.my_cart);
        MenuItemCompat.setActionView(menuItem,R.layout.cart_icon_for_toolbar);
        RelativeLayout mycarttoolbar= (RelativeLayout) MenuItemCompat.getActionView(menuItem);
        txtViewCount = (TextView) mycarttoolbar.findViewById(R.id.badge_notification_1);
        toolbarcartimage= (ImageView) mycarttoolbar.findViewById(R.id.badge_notification_image);

        toolbarcartimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Wallet_page.this,MyCart.class));
            }
        });
        txtViewCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Wallet_page.this,MyCart.class));
            }
        });

        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                if(drawer.isDrawerOpen(Gravity.LEFT)) {
                    drawer.closeDrawer(Gravity.LEFT);
                }
        }

        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wallet);
        mToolbar=(Toolbar)findViewById(R.id.myToolBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Wallet");

        shopButton = (Button) findViewById(R.id.goShopButton);
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Wallet_page.this, MainActivity.class);
                startActivity(intent);

            }
        });

        amountTextView =(TextView) findViewById(R.id.walletTextView);
        amountTextView.setText("Amount: ₹"+ amount);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        refferencetonavigationcategory(navigationView);
        nestedScrollView= (NestedScrollView) navigationView.findViewById(R.id.scrollposition);
        listView= (Helper) navigationView.findViewById(R.id.parentcategoryList);
        initiaze();
        add();

    }

    public void refferencetonavigationcategory(View view)
    {
        myorder= (LinearLayout) view.findViewById(R.id.myorders);
        mycart= (LinearLayout) view.findViewById(R.id.mycart);
        myaccount= (LinearLayout) view.findViewById(R.id.myaccount);
        helpcenter= (LinearLayout) view.findViewById(R.id.helpcenter);
        ratedesichain= (LinearLayout) view.findViewById(R.id.ratedesichain);
        policy= (LinearLayout) view.findViewById(R.id.policy);
        facebook= (LinearLayout) view.findViewById(R.id.facebook);
        google=(LinearLayout) view.findViewById(R.id.googleplus);
        twitter= (LinearLayout) view.findViewById(R.id.twitter);
        pinterest= (LinearLayout) view.findViewById(R.id.pinterest);
        youtube= (LinearLayout) view.findViewById(R.id.youtube);
        instagram= (LinearLayout) view.findViewById(R.id.instagram);
        aboutus= (LinearLayout) view.findViewById(R.id.aboutus);
        subscribe= (LinearLayout) findViewById(R.id.subscribe);
        myorder.setOnClickListener(this);
        mycart.setOnClickListener(this);
        myaccount.setOnClickListener(this);
        helpcenter.setOnClickListener(this);
        ratedesichain.setOnClickListener(this);
        policy.setOnClickListener(this);
        facebook.setOnClickListener(this);
        google.setOnClickListener(this);
        twitter.setOnClickListener(this);
        pinterest.setOnClickListener(this);
        youtube.setOnClickListener(this);
        instagram.setOnClickListener(this);
        aboutus.setOnClickListener(this);
        subscribe.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        new Utility().openIntent(this,v.getId(),drawer);
    }

    public void initiaze(){
        arrayList=new ArrayList<>();
        hashMap=new HashMap<>();
        Books=new ArrayList<>();
        Poojaitem=new ArrayList<>();
        Homecare=new ArrayList<>();
        others=new ArrayList<>();

    }
    public void add()
    {
        arrayList.add(new CategoryHolder("Book and media",0,R.mipmap.book));
        arrayList.add(new CategoryHolder("Pooja Item",0,R.mipmap.pooja));
        arrayList.add(new CategoryHolder("Home Care",0,R.mipmap.homecare));
        arrayList.add(new CategoryHolder("Others",0,R.mipmap.other));
        Books.add("Bhagavad-Gita As It Is");
        Books.add("Paperback/ Hardbound");
        Books.add("Media");
        Poojaitem.add("Items for Worship");
        Poojaitem.add("Other Essentials");
        Poojaitem.add("Bells");
        Poojaitem.add("Agarbatti/ Dhoop");
        Poojaitem.add("Murtis/ Deities");
        Homecare.add("Home Decor");
        Homecare.add("Home Furnishing");
        Homecare.add("Kitchen n Dinning");
        others.add("Personal Care");
        others.add("Health & Food");
        others.add("Fshion Accessiories");
        others.add("Women");
        others.add("Men");
        others.add("BagsnStationery");
        others.add("MobileAccessiories");
        hashMap.put(arrayList.get(0).getPARENTCATEGORY(),Books);
        hashMap.put(arrayList.get(1).getPARENTCATEGORY(),Poojaitem);
        hashMap.put(arrayList.get(2).getPARENTCATEGORY(),Homecare);
        hashMap.put(arrayList.get(3).getPARENTCATEGORY(),others);
        navigationCategoryList();
    }
    public void navigationCategoryList() {
        final ShowCategoryAdapeter showCategoryAdapeter = new ShowCategoryAdapeter(Wallet_page.this, arrayList, hashMap, listView);
        listView.setAdapter(showCategoryAdapeter);
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (arrayList.get(groupPosition).getFLAG_INDICATOR() == 1) {
                    listView.collapseGroup(groupPosition);
                    arrayList.get(groupPosition).setFLAG_INDICATOR(0);

                } else {
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (arrayList.get(i).getFLAG_INDICATOR() == 1) {
                            listView.collapseGroup(i);
                            arrayList.get(i).setFLAG_INDICATOR(0);
                        }

                    }
                    listView.expandGroup(groupPosition);
                    listView.setSelectedGroup(groupPosition);
                    nestedScrollView.smoothScrollTo(0, groupPosition);
                    arrayList.get(groupPosition).setFLAG_INDICATOR(1);

                }
                return true;
            }
        });
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(Wallet_page.this, Childcategoru.class);
                intent.putExtra("get", hashMap.get(arrayList.get(groupPosition).getPARENTCATEGORY()).get(childPosition));
                intent.putExtra("getFilterName", String.valueOf(arrayList.get(groupPosition).getPARENTCATEGORY()));
                startActivity(intent);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                for (int i = 0; i < parent.getCount(); ++i) {
                    if (parent.isGroupExpanded(i)) {
                        parent.collapseGroup(i);
                    }
                }

                return true;
            }
        });

    }

}

