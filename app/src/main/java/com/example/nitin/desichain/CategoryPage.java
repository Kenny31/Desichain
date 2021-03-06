package com.example.nitin.desichain;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nitin.desichain.Adapters.BookCategoryAdapter;
import com.example.nitin.desichain.Adapters.CategoryAdapter;
import com.example.nitin.desichain.Contents.BookCategoryList;
import com.example.nitin.desichain.Contents.CategoryList;
import com.example.nitin.desichain.Internet.FetchingFromUrl;
import com.example.nitin.desichain.ParsingJson.GetCategoryId;
import com.example.nitin.desichain.ParsingJson.GetParticularBookList;
import com.example.nitin.desichain.ParsingJson.GetParticularCategoryList;
import com.example.nitin.desichain.SubCategoryList.ShowCategoryAdapeter;
import com.example.nitin.desichain.Utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class CategoryPage extends AppCompatActivity implements View.OnClickListener {


    GridView listView1;
    TextView SORT_OPTION,FILTER_OPTION;
    private Toolbar mToolbar;
    private ArrayList<CategoryList> arrayList1;
    private ArrayList<BookCategoryList> arrayList2;

    private Helper listView;
    View headerView;
    private String JSON_RESPONSE;
    CategoryAdapter categoryAdapterOthers;
    BookCategoryAdapter categoryAdapter;
    DrawerLayout drawer;
    NestedScrollView nestedScrollView;
    public static ArrayList<String> Poojaitem;
    private Intent intent1;
    private int CATEGORY_ID;
    public  static ArrayList<CategoryHolder> arrayList;
    public static  ArrayList<String> Books;
    public static ArrayList<String> Homecare;
    public static   ArrayList<String> others;
    public ArrayList<CategoryList> mList;
    public  static HashMap<String,ArrayList<String>> hashMap;
    LinearLayout subscribe;
    LinearLayout myorder,mycart,myaccount,helpcenter,ratedesichain,productPage,policy,facebook,google,twitter,pinterest,youtube,instagram,aboutus;

    final CharSequence[] sortoption={"Relevance","Popularity","Price Low To High","Price High To Low","Discount","Fresh Arrivals"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mList = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getIntent().getStringExtra("Topic"));

        FILTER_OPTION=(TextView)findViewById(R.id.filter);
        listView1= (GridView) findViewById(R.id.categorygridview);
        SORT_OPTION= (TextView) findViewById(R.id.sort);

        mList = (ArrayList<CategoryList>)getIntent().getSerializableExtra("featuredProdKey");


        if (mList!=null){
            CategoryAdapter categoryAdapter = new CategoryAdapter(this,mList);
            listView1.setAdapter(categoryAdapter);
        }

        intent1=getIntent();
        String CHILDCATEGORYNAME=intent1.getStringExtra("Topic");
        int CATEGORY_FLAG=intent1.getIntExtra("CATEGORYFLAG",-1);


        if(CATEGORY_FLAG==1)
            {

               JSON_RESPONSE=load("http://dc.desichain.in/DesiChainWeService.asmx/BooksCategory");
                if(JSON_RESPONSE!=null)
                {
                    CATEGORY_ID=new GetCategoryId(JSON_RESPONSE,CHILDCATEGORYNAME,CategoryPage.this).getCategoryId();

                    if(CATEGORY_ID==-1)
                    {
                        Toast.makeText(CategoryPage.this,"No Such Products Exist",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        JSON_RESPONSE=load("http://dc.desichain.in/DesiChainWeService.asmx/ProductsOfBookCategory?catid="+CATEGORY_ID);
                        if(JSON_RESPONSE!=null)
                        {

                          arrayList2=new GetParticularBookList(JSON_RESPONSE,CategoryPage.this).getParticularBookList();
                            if(arrayList2==null)
                            {
                                Toast.makeText(CategoryPage.this,"No Such Products Exist",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {


                            categoryAdapter=new BookCategoryAdapter(this,arrayList2);
                                categoryAdapter.notifyDataSetChanged();
                                listView1.setAdapter(categoryAdapter);

                            }
                        }

                    }

                }
            }
          else if(CATEGORY_FLAG==2)
            {
                    JSON_RESPONSE=load("http://dc.desichain.in/DesiChainWeService.asmx/OthersCategory");
                    if(JSON_RESPONSE!=null)
                    {
                        CATEGORY_ID=new GetCategoryId(JSON_RESPONSE,CHILDCATEGORYNAME,CategoryPage.this).getCategoryId();
                        if(CATEGORY_ID==-1)
                        {
                            Toast.makeText(CategoryPage.this,"No Such Category Exist",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            JSON_RESPONSE=load("http://dc.desichain.in/DesiChainWeService.asmx/ProductsOfOtherCategory?catid="+CATEGORY_ID);
                            if(JSON_RESPONSE!=null){
                                arrayList1=new GetParticularCategoryList(JSON_RESPONSE,CategoryPage.this).getParticularCategoryList();
                                if(arrayList1==null)
                                {
                                    Toast.makeText(CategoryPage.this,"No Such Products EXis",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    categoryAdapterOthers=new CategoryAdapter(this,arrayList1);
                                    listView1.setAdapter(categoryAdapterOthers);
                                }
                            }
                        }
                    }



        }
//
//if(categoryAdapterOthers!=null)
//{
//    listView1.setAdapter(categoryAdapter);
//}
//        else if(categoryAdapter!=null)
//{
//    listView1.setAdapter(categoryAdapter);
//}

        SORT_OPTION.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertdialog=new AlertDialog.Builder(CategoryPage.this);
                alertdialog.setTitle("Sort");
                alertdialog.setSingleChoiceItems(sortoption, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(CategoryPage.this,"clicked",Toast.LENGTH_SHORT).show();
                    }
                });
                alertdialog.create().show();

            }
        });

        FILTER_OPTION.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mGetFilterName = intent1.getStringExtra("getFilterName");
                Intent intent = new Intent(CategoryPage.this,Filters.class);
                intent.putExtra("getFilterData",mGetFilterName);
                startActivity(intent);
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        refferencetonavigationcategory(navigationView);
        nestedScrollView= (NestedScrollView) navigationView.findViewById(R.id.scrollposition);
        listView= (Helper) navigationView.findViewById(R.id.parentcategoryList);
        initiaze();
        add();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.my_cart){
            startActivity(new Intent(CategoryPage.this,MyCart.class));
        }
        else if (item.getItemId()==R.id.search_item){
            startActivity(new Intent(this,SearchActivity.class));
        }
        else if (item.getItemId()==R.id.my_orders){
            startActivity(new Intent(this, MyOrders.class));
        }
        else if (item.getItemId()==R.id.notifications){
            startActivity(new Intent(this, NotificationPage.class));

        }
        if (item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
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

    public void initiaze(){
        arrayList=new ArrayList<>();
        hashMap=new HashMap<>();
        Books=new ArrayList<>();
        Poojaitem=new ArrayList<>();
        Homecare=new ArrayList<>();
        others=new ArrayList<>();

    }
    public void navigationCategoryList(){
        final ShowCategoryAdapeter showCategoryAdapeter=new ShowCategoryAdapeter(CategoryPage.this,arrayList,hashMap,listView);
        listView.setAdapter(showCategoryAdapeter);
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if(arrayList.get(groupPosition).getFLAG_INDICATOR()==1)
                {
                    listView.collapseGroup(groupPosition);
                    arrayList.get(groupPosition).setFLAG_INDICATOR(0);

                }
                else{
                    for(int i=0;i<arrayList.size();i++)
                    {
                        if(arrayList.get(i).getFLAG_INDICATOR()==1)
                        {
                            listView.collapseGroup(i);
                            arrayList.get(i).setFLAG_INDICATOR(0);
                        }

                    }
                    listView.expandGroup(groupPosition);
                    listView.setSelectedGroup(groupPosition);
                    nestedScrollView.smoothScrollTo(0,groupPosition);
                    arrayList.get(groupPosition).setFLAG_INDICATOR(1);

                }
                return true;
            }
        });
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent=new Intent(CategoryPage.this,Childcategoru.class);
                intent.putExtra("get",hashMap.get(arrayList.get(groupPosition).getPARENTCATEGORY()).get(childPosition));
                intent.putExtra("getFilterName",String.valueOf(arrayList.get(groupPosition).getPARENTCATEGORY()));
                startActivity(intent);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                for (int i=0; i<parent.getCount(); ++i) {
                    if (parent.isGroupExpanded(i)) {
                        parent.collapseGroup(i);
                    }
                }
                return true;
            }
        });

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
        myaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("myPref",MODE_PRIVATE);
                String email = preferences.getString("emailId","none");
                String pwd = preferences.getString("password","none");

                if (email.equals("none") && pwd.equals("none")){
                    startActivity(new Intent(CategoryPage.this,LoginActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(CategoryPage.this,MyAccount.class));

                }
            }
        });
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
    public String load(String Url) {
        try {
            JSON_RESPONSE = new FetchingFromUrl().execute(Url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return JSON_RESPONSE;

    }
}
