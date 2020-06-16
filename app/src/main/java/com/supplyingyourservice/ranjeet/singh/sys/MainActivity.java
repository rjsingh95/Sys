package com.supplyingyourservice.ranjeet.singh.sys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import com.supplyingyourservice.ranjeet.singh.sys.books.bookstore;
import com.supplyingyourservice.ranjeet.singh.sys.books.mybooks;
import com.supplyingyourservice.ranjeet.singh.sys.commonloc.addcloc;
import com.supplyingyourservice.ranjeet.singh.sys.commonloc.commomloc;
import com.supplyingyourservice.ranjeet.singh.sys.elastic.SearchActivity;

import q.rorbin.badgeview.QBadgeView;

import com.squareup.picasso.Picasso;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.*;


public class  MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener{
    SliderLayout sliderLayout;
    private String mPostId;
    private addprod mPost;
    private  ImageView mPostImage;

    private SmallBang mSmallBang;

    private DatabaseReference myref;
    private RecyclerView rv,rv2,rv3;
    private static final int ACTIVITY_NUM = 0;

    private ArrayList<BeanlistGrooming> Bean;
    private GroomingRecyclerViewAdapter baseAdapter;
    private Context mContext = MainActivity.this;

    private TrendingRecyclerViewAdapter baseAdapter1;
    private ArrayList<BeanlistBrands> Bean2;
    private BrandsRecyclerViewAdapter baseAdapter2;
    private Context context ;
    Typeface fonts1,fonts2,fonts3,fonts4;
    TextView eshop,shirt1,jeans1,shoes1,slippers1,goggles1,groomingproducts,trendingproducts,topbrands;

   LinearLayout goggles;
    int snum=0;


    //new
    FirebaseAuth mAuth;
    private DatabaseReference mref;





    //    *****Brands string*******

    List<addprod> list = new ArrayList<>();
    private DatabaseReference ref;
    private DatabaseReference Uid;
    private RecyclerView rv4;
    private DatabaseReference myref2;
    private DatabaseReference myref3;
    private String b;
    private ImageView fav1;
    private ImageView fav2;
    private CircleImageView imag;
    private ImageView messagesb;
    private String like_id;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.supplyingyourservice.ranjeet.singh.sys.R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();



  TextView serch = (TextView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.tvsearch);
        serch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productdetailsintent = new Intent (MainActivity.this, SearchActivity.class);

                startActivity(productdetailsintent);


            }
        });


        if(mAuth.getCurrentUser()!=null) {
            final String MY_PREFS_NAME = "BrackatPrefsFile";
            String intentcity = getIntent().getStringExtra("city");
            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);


            String name = prefs.getString("city", null);
            ImageView add_shop=(ImageView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.shop);
            add_shop.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent productdetailsintent = new Intent (MainActivity.this, Addshop.class);

                    startActivity(productdetailsintent);

                }
            });
            setupBottomNavigationView();

            DatabaseReference noticeref =FirebaseDatabase.getInstance().getReference().child("notice");

            final LinearLayout lin=(LinearLayout)findViewById(R.id.noticelin);
            final TextView notice=(TextView)findViewById(R.id.notice);
            final TextView noticeok=(TextView)findViewById(R.id.noticeok);
             noticeok.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                   lin.setVisibility(GONE);
                }
            });

            noticeref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild("notice_num")){
                        if(dataSnapshot.hasChild("message")){
                            String msg=dataSnapshot.child("message").getValue().toString();
                            notice.setText(msg);
                        }



                        final String noticestring=dataSnapshot.child("notice_num").getValue().toString();
                        noticeok.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                editor.putString("notice", noticestring);
                                editor.apply();
                                lin.setVisibility(GONE);
                            }
                        });





                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });





            if (name != null) {
                Log.d("common",name);
                if(name  !=null&&intentcity!=null) {
                    if (!intentcity.equals(name)) {
                        name = intentcity;
                    }

                }

                ((commomloc) this.getApplication()).setloc(name);

            } else {

                if (intentcity != null) {
                    if (intentcity != null) {

                        if(name  !=null  && intentcity!=null) {
                            if (!intentcity.equals(name)) {
                                name = intentcity;
                                intentcity = name;
                            }

                        }}
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("city", intentcity);
                    editor.apply();

                } else {

                    myref = FirebaseDatabase.getInstance().getReference().child("customers");
                    myref.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {



                            if (dataSnapshot.hasChild("city")) {
                                String loc = dataSnapshot.child("city").getValue().toString();
                                if (loc != null) {

                                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                    editor.putString("city", loc);
                                    editor.apply();

                                } else {

                                    Intent productdetailsintent = new Intent(MainActivity.this, addcloc.class);
                                    startActivity(productdetailsintent);
                                }
                            } else{


                                Intent productdetailsintent = new Intent (MainActivity.this, addcloc.class);

                                startActivity(productdetailsintent);
                            }
                        }



                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        }



               mAuth =FirebaseAuth.getInstance();

       sliderLayout= (SliderLayout)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.slider);



        shirt1 = (TextView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.shirt1);
        jeans1 = (TextView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.jeans1);
        shoes1 = (TextView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.shoes1);
        slippers1 = (TextView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.slippers1);
        goggles1 = (TextView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.goggles1);
        groomingproducts = (TextView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.groomingproducts);
        trendingproducts = (TextView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.trendingproducts);
        topbrands = (TextView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.topbrands);
     //   TextView Button2 = (TextView) findViewById(R.id.button2);
       // Button2.setOnClickListener(new OnClickListener() {
         //   @Override
           // public void onClick(View view) {
             //   final DatabaseReference datbase = FirebaseDatabase.getInstance().getReference().child("products");
               // datbase.addValueEventListener(new ValueEventListener() {
                 //   @Override
                   // public void onDataChange(DataSnapshot dataSnapshot) {
                     //   int i= (int) dataSnapshot.getChildrenCount();
                       // Log.d("research", String.valueOf(i));
           //             for(DataSnapshot item_snapshot:dataSnapshot.getChildren()) {
             //               String pr = item_snapshot.getValue().toString();
               //             Log.d("research",pr);
                 //          item_snapshot.getRef().child("kuch").removeValue();
                  //      }
                    //    }
                  //  @Override
                //    public void onCancelled(DatabaseError databaseError) {

                  //  }
              //  });


      //      }
        //});


        goggles =(LinearLayout)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.goggles0);

//     *******BOT BAR COLOR *********


goggles.setOnClickListener(new OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent productdetailsintent = new Intent (MainActivity.this,bookstore.class);
        startActivity(productdetailsintent);

    }
});

        LinearLayout mobiles = (LinearLayout) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.mobile);

        mobiles.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productdetailsintent = new Intent (MainActivity.this,all_brands.class);
                productdetailsintent.putExtra("category","mobiles and smart phones");
                productdetailsintent.putExtra("extra_type","mobile accessories");
                startActivity(productdetailsintent);

                }
        });
        LinearLayout kitchen = (LinearLayout) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.kitchen);
        kitchen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productdetailsintent = new Intent (MainActivity.this,all_cateogories.class);
                productdetailsintent.putExtra("category","Kitchen Appliances");
                startActivity(productdetailsintent);



            }
        });
        LinearLayout computer = (LinearLayout) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.computer);


        computer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productdetailsintent = new Intent (MainActivity.this,all_brands.class);
                productdetailsintent.putExtra("category","Laptop");
                productdetailsintent.putExtra("extra_type","computer accessories");
                startActivity(productdetailsintent);




            }
        });

        LinearLayout homeapp = (LinearLayout) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.homeapp);

//     *******BOT BAR COLOR *********


        homeapp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productdetailsintent = new Intent (MainActivity.this,browser.class);
                productdetailsintent.putExtra("category","Electronics Home Appliances");

                startActivity(productdetailsintent);



            }
        });













        messagesb=(ImageView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.messages);
        messagesb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent productdetailsintent = new Intent (MainActivity.this,ChatsFragment.class);
                startActivity(productdetailsintent);

            }
        });


        Toolbar toolbar = (Toolbar) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, com.supplyingyourservice.ranjeet.singh.sys.R.string.navigation_drawer_open, com.supplyingyourservice.ranjeet.singh.sys.R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.nav_view);
        final View headerview = navigationView.getHeaderView(0);
        TextView profilename = (TextView) headerview.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.name);
         imag=(CircleImageView) headerview.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.banar1);
         if(mAuth.getCurrentUser() !=null){
        myref = FirebaseDatabase.getInstance().getReference().child("customers").child(mAuth.getCurrentUser().getUid());


        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("customer_thumb")){
                    String dp= dataSnapshot.child("customer_thumb").getValue().toString();
                    Picasso.with(MainActivity.this).load(dp).into(imag);
                }
                if(dataSnapshot.hasChild("new_message")){
                    String dp= dataSnapshot.child("new_message").getValue().toString();




                    if (dp.equals("new")) {
                        new QBadgeView(MainActivity.this).bindTarget(messagesb).setBadgeText("new").setBadgePadding(4, true).setBadgeTextSize(10, true).setGravityOffset(1, 1, true).setBadgeGravity(Gravity.TOP | Gravity.END);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });}

        headerview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productdetailsintent = new Intent (MainActivity.this,editinfo.class);
                startActivity(productdetailsintent);

            }
        });


        drawer.setDrawerElevation(4);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                DrawerLayout drawer = (DrawerLayout) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.drawer_layout);
              drawer.closeDrawer(GravityCompat.START);
                if (id == com.supplyingyourservice.ranjeet.singh.sys.R.id.categories) {
                    Intent productdetailsintent = new Intent (MainActivity.this,all_cateogories.class);
                    startActivity(productdetailsintent);


                } else if (id == R.id.offers) {

                    Intent productdetailsintent = new Intent (MainActivity.this,offers.class);
                    startActivity(productdetailsintent);



                }
                else if (id == com.supplyingyourservice.ranjeet.singh.sys.R.id.editprofile) {


                    Intent productdetailsintent = new Intent (MainActivity.this,editinfo.class);
                    startActivity(productdetailsintent);



                }
                else if (id == com.supplyingyourservice.ranjeet.singh.sys.R.id.book) {


                    Intent productdetailsintent = new Intent (MainActivity.this,mybooks.class);
                    startActivity(productdetailsintent);


                }

                else if (id == R.id.logout) {

                    mAuth=FirebaseAuth.getInstance();
                    mAuth.signOut();
                    finish();



                }

                return true;

            }
        });





        fonts1 =  Typeface.createFromAsset(getAssets(),
                "fonts/OpenSans-Regular.ttf");
        fonts2 =  Typeface.createFromAsset(getAssets(),
                "fonts/OpenSans-Semibold.ttf");

        fonts3 =  Typeface.createFromAsset(getAssets(),
                "fonts/Roboto-Medium.ttf");

        fonts4 =  Typeface.createFromAsset(getAssets(),
                "fonts/Roboto-Regular.ttf");


        shirt1.setTypeface(fonts4);
        jeans1.setTypeface(fonts4);
        shoes1.setTypeface(fonts4);
        slippers1.setTypeface(fonts4);
        goggles1.setTypeface(fonts4);
        groomingproducts.setTypeface(fonts2);
        trendingproducts.setTypeface(fonts2);
        topbrands.setTypeface(fonts2);


//*********RECYCLERVIEWS*********

        rv = (RecyclerView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.rv);
        rv2 = (RecyclerView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.rv2);
        rv3 = (RecyclerView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.rv3);
        rv4 = (RecyclerView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.rv4);


        LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(mLayoutManager);


        LayoutManager mLayoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rv2.setLayoutManager(mLayoutManager1);
        rv2.setHasFixedSize(true);


        LayoutManager mLayoutManager5 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rv4.setLayoutManager(mLayoutManager5);

        LayoutManager mLayoutManager2 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rv3.setLayoutManager(mLayoutManager2);
        rv3.setHasFixedSize(true);

        Bean2 = new ArrayList<BeanlistBrands>();
        DatabaseReference home_brand = FirebaseDatabase.getInstance().getReference().child("homerv").child("home_brand");
        home_brand.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot item_snapshot:dataSnapshot.getChildren()){
                    BeanlistBrands obj=new BeanlistBrands();

                    obj = item_snapshot.getValue(BeanlistBrands.class);

                    if(!Bean2.contains(obj)){
                    Bean2.add(obj);}
                }
                baseAdapter2 = new BrandsRecyclerViewAdapter(MainActivity.this, Bean2);
                rv3.setAdapter(baseAdapter2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
if(mAuth.getCurrentUser()!=null) {
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    database.child("delivery").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            int i = (int) dataSnapshot.getChildrenCount();
           // addBadgeAt(2, i);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
}






        setSlider("Image1");
        setSlider("Image2");
        setSlider("Image3");
        setSlider("Image4");
        setSlider("Image5");
        setSlider("Image6");
        setSlider("banner1");
        setSlider("banner2");
        setSlider("banner3");

        //         ********Slider*********



    }
    @Override
    protected void onStart() {
        super.onStart();
        String s = ((commomloc) this.getApplication()).getloc();
        mref= FirebaseDatabase.getInstance().getReference().child("products");

        if(s!=null) {
            myref2= FirebaseDatabase.getInstance().getReference().child("homerv").child(s).child("top");

            homerv(rv4, myref2, mref);

        }
        if(s!=null){
            myref = FirebaseDatabase.getInstance().getReference().child("in_city").child(s);

            homerv(rv2,myref,mref);

        }


        if(s!=null) {

            myref3 = FirebaseDatabase.getInstance().getReference().child("homerv").child(s).child("middle");

            homerv(rv, myref3, mref);

        }



        mAuth=FirebaseAuth.getInstance();


        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){

//
            finish();
            startActivity(new Intent(MainActivity.this,SignIn.class));

        } else {
            DatabaseReference muserRef = FirebaseDatabase.getInstance().getReference().child("customers").child(mAuth.getCurrentUser().getUid());

            muserRef.child("online").setValue("true");

        }






    }


   

    private void homerv(RecyclerView rvhome1, DatabaseReference hrv, final DatabaseReference hprv){

    FirebaseRecyclerAdapter<homerv,groomingholder> adapter = new FirebaseRecyclerAdapter<homerv, groomingholder>(homerv.class, com.supplyingyourservice.ranjeet.singh.sys.R.layout.list, groomingholder.class,hrv)
    {
        @Override
        protected void populateViewHolder(final groomingholder viewHolder, final homerv model, int position) {

            final String post_key=  getRef(position).getKey();

            hprv.child(post_key).addValueEventListener(new ValueEventListener() {
                public DatabaseReference dref;

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){

                     aproduct obj=new aproduct();

                    obj = dataSnapshot.getValue(aproduct.class);

                    viewHolder.setTitle(obj.getTitle());

                    viewHolder.setImage(obj.getProductdp(),getApplicationContext());
                    viewHolder.ratingbar.setRating(obj.getRating()/2);
                   viewHolder.ratingbar=new ScaleRatingBar(MainActivity.this);
                   viewHolder.ratingbar.setClickable(false);
                       viewHolder. ratingbar.setIsIndicator(false);
                     viewHolder.   ratingbar.setClickable(false);
                    viewHolder.    ratingbar.setScrollable(false);
                     viewHolder.   ratingbar.setClearRatingEnabled(false);
                    dref=FirebaseDatabase.getInstance().getReference();
                    like_id = mAuth.getCurrentUser().getUid();
                    if(like_id==null){
                     like_id="";
                    }
                    String prices= String.valueOf(obj.getPrice());
                   // viewHolder.price.setText("MRP  "+prices);

                    dref.child("Likes").child(like_id).child(post_key);
                    dref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                           if( dataSnapshot.hasChild("like")){
                                viewHolder.fav2.setVisibility(View.VISIBLE);
                                viewHolder.fav1.setVisibility(View.GONE);

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    viewHolder.mSmallBang = SmallBang.attach2Window(MainActivity.this);


                    viewHolder.fav1.setOnClickListener(new View.OnClickListener() {
                        public DatabaseReference dref;

                        @Override
                        public void onClick(final View v) {

                               dref=FirebaseDatabase.getInstance().getReference();

                               dref.child("Likes").child(mAuth.getCurrentUser().getUid()).child(post_key).child("like").setValue("set").addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                       viewHolder.fav2.setVisibility(View.VISIBLE);
                                       viewHolder.fav1.setVisibility(View.GONE);
                                       like(v);
                                   }
                               });


                        }

                        public void like(View view){
                            viewHolder.fav2.setImageResource(com.supplyingyourservice.ranjeet.singh.sys.R.drawable.f4);
                            viewHolder.mSmallBang.bang(view);
                            viewHolder.mSmallBang.setmListener(new SmallBangListener() {
                                @Override
                                public void onAnimationStart() {

                                }

                                @Override
                                public void onAnimationEnd() {

                                }


                            });
                        }

                    });

                    viewHolder.fav2.setOnClickListener(new View.OnClickListener() {
                        DatabaseReference dref;

                        @Override
                        public void onClick(View v) {

                            dref=FirebaseDatabase.getInstance().getReference();



                            dref.child("Likes").child(like_id).child(post_key).child("like").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    viewHolder.fav2.setVisibility(View.GONE);
                                    viewHolder.fav1.setVisibility(View.VISIBLE);

                                }
                            });



                        }
                    });

  viewHolder.mView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(MainActivity.this ,post_key,"product clicked",Toast.LENGTH_SHORT).show();
                            Intent productdetailsintent = new Intent (MainActivity.this,ProductDetailActivity.class);
                            productdetailsintent.putExtra("product_id",post_key);
                            startActivity(productdetailsintent);
                        }
                    });
                }}

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    };
    rvhome1.setAdapter(adapter);

}

//    private Badge addBadgeAt(int position, int number) {
//        BottomNavigationView bottomNavigationViewEx = (BottomNavigationView) findViewById(R.id.bottomNavViewBar);
//        // add badge
//        return new QBadgeView(this)
//                .setBadgeNumber(number)
//                .setGravityOffset(12, 2, true)
//                .bindTarget(bottomNavigationViewEx.getChildAt(position))
//                .setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
//                    @Override
//                    public void onDragStateChanged(int dragState, Badge badge, View targetView) {
//                        if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState){
//
//                        }
//
//                    }
//                });
//    }

    private void setSlider(final String Image) {

        String s = ((commomloc) this.getApplication()).getloc();
if(s!=null) {
    ref = FirebaseDatabase.getInstance().getReference().child("SliderAdds").child(s).child(Image);
    Log.d("home", String.valueOf(ref));
    ref.addListenerForSingleValueEvent(new ValueEventListener() {
        public String p_category;
        public String p_brand;
        public String list_name;
        public String p_id;
        public String type;

        @Override
        public void onDataChange(final DataSnapshot dataSnapshot) {
            if (dataSnapshot.hasChild("pic")) {
                String title = dataSnapshot.child("pic").getValue().toString();



                ImageView slide;
                String a = null,b=null,d = null;
                if(dataSnapshot.hasChild("type")){
                    type=dataSnapshot.child("type").getValue().toString();}
                if (dataSnapshot.hasChild("id")){
                    p_id=dataSnapshot.child("id").getValue().toString();
                }
                if (dataSnapshot.hasChild("list_name")){
                    list_name=dataSnapshot.child("list_name").getValue().toString();}
                if (dataSnapshot.hasChild("brand")){
                   p_brand=dataSnapshot.child("brand").getValue().toString();}
                if (dataSnapshot.hasChild("category")){
                    p_category=dataSnapshot.child("category").getValue().toString();}
                if (Image.equals("banner1")) {

                    a=title;

                    setGroup(a,null,null,type,p_id,list_name,p_brand,p_category);
                }
                if (Image.equals("banner2")) {

                  b=title;

                    setGroup(null,b,null,type,p_id,list_name,p_brand,p_category);


                }
                if (Image.equals("banner3")) {

                   d=title;
                    setGroup(null,null,d,type,p_id,list_name,p_brand,p_category);


                }


                if (Image.equals("Image1")) {
                    slide = (ImageView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Image1);
                    showPhoto(title, slide);
                    slide.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(dataSnapshot.hasChild("type")){
                                String type=dataSnapshot.child("type").getValue().toString();
                                if(type.equals("single")){
                                    if (dataSnapshot.hasChild("id")){
                                       String id=dataSnapshot.child("id").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(MainActivity.this, ProductDetailActivity.class);
                                            productdetailsintent.putExtra("product_id", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("list")){

                                    if (dataSnapshot.hasChild("list_name")){
                                        String id=dataSnapshot.child("list_name").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(MainActivity.this, list_items.class);
                                            productdetailsintent.putExtra("product_id", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("brand")){

                                    if (dataSnapshot.hasChild("brand")){
                                        String id=dataSnapshot.child("brand").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(MainActivity.this, all_products.class);

                                            productdetailsintent.putExtra("brand", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }


                                if(type.equals("category"))
                                {

                                    if (dataSnapshot.hasChild("category")){
                                        String id=dataSnapshot.child("category").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(MainActivity.this, all_products.class);
                                            productdetailsintent.putExtra("category", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }

                            }

                        }
                    });


                }
                if (Image.equals("Image2")) {
                    slide = (ImageView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Image2);
                    showPhoto(title, slide);
                    slide.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(dataSnapshot.hasChild("type")){
                                String type=dataSnapshot.child("type").getValue().toString();
                                if(type.equals("single")){
                                    if (dataSnapshot.hasChild("id")){
                                        String id=dataSnapshot.child("id").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(MainActivity.this, ProductDetailActivity.class);
                                            productdetailsintent.putExtra("product_id", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("list")){

                                    if (dataSnapshot.hasChild("list_name")){
                                        String id=dataSnapshot.child("list_name").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(MainActivity.this, list_items.class);
                                            productdetailsintent.putExtra("product_id", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("brand")){

                                    if (dataSnapshot.hasChild("brand")){
                                        String id=dataSnapshot.child("brand").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(MainActivity.this, all_products.class);

                                            productdetailsintent.putExtra("brand", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("category")){

                                    if (dataSnapshot.hasChild("category")){
                                        String id=dataSnapshot.child("category").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(MainActivity.this, all_products.class);
                                            productdetailsintent.putExtra("category", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }

                            }

                        }
                    });



                }
                if (Image.equals("Image3")) {
                    slide = (ImageView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Image3);
                    slide.setVisibility(VISIBLE);
                    showPhoto(title, slide);
                    slide.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(dataSnapshot.hasChild("type")){
                                String type=dataSnapshot.child("type").getValue().toString();
                                if(type.equals("single")){
                                    if (dataSnapshot.hasChild("id")){
                                        String id=dataSnapshot.child("id").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(MainActivity.this, ProductDetailActivity.class);
                                            productdetailsintent.putExtra("product_id", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("list")){

                                    if (dataSnapshot.hasChild("list_name")){
                                        String id=dataSnapshot.child("list_name").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(MainActivity.this, list_items.class);
                                            productdetailsintent.putExtra("product_id", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("brand")){

                                    if (dataSnapshot.hasChild("brand")){
                                        String id=dataSnapshot.child("brand").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(MainActivity.this, all_products.class);

                                            productdetailsintent.putExtra("brand", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("category")){

                                    if (dataSnapshot.hasChild("category")){
                                        String id=dataSnapshot.child("category").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(MainActivity.this, all_products.class);
                                            productdetailsintent.putExtra("category", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }

                            }

                        }
                    });


                }
                if (Image.equals("Image4")) {
                    slide = (ImageView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Image4);
                    showPhoto(title, slide);

                    slide.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(dataSnapshot.hasChild("type")){
                                String type=dataSnapshot.child("type").getValue().toString();
                                if(type.equals("single")){
                                    if (dataSnapshot.hasChild("id")){
                                        String id=dataSnapshot.child("id").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(MainActivity.this, ProductDetailActivity.class);
                                            productdetailsintent.putExtra("product_id", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("list")){

                                    if (dataSnapshot.hasChild("list_name")){
                                        String id=dataSnapshot.child("list_name").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(MainActivity.this, list_items.class);
                                            productdetailsintent.putExtra("product_id", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("brand")){

                                    if (dataSnapshot.hasChild("brand")){
                                        String id=dataSnapshot.child("brand").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(MainActivity.this, all_products.class);

                                            productdetailsintent.putExtra("brand", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("category")){

                                    if (dataSnapshot.hasChild("category")){
                                        String id=dataSnapshot.child("category").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(MainActivity.this, all_products.class);
                                            productdetailsintent.putExtra("category", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }

                            }

                        }
                    });

                }
                if (Image.equals("Image5")) {
                    slide = (ImageView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Image5);
                    showPhoto(title, slide);
                    slide.setVisibility(VISIBLE);
                    slide.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(dataSnapshot.hasChild("type")){
                                String type=dataSnapshot.child("type").getValue().toString();
                                if(type.equals("single")){
                                    if (dataSnapshot.hasChild("id")){
                                        String id=dataSnapshot.child("id").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(MainActivity.this, ProductDetailActivity.class);
                                            productdetailsintent.putExtra("product_id", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("list")){

                                    if (dataSnapshot.hasChild("list_name")){
                                        String id=dataSnapshot.child("list_name").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(MainActivity.this, list_items.class);
                                            productdetailsintent.putExtra("product_id", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("brand")){

                                    if (dataSnapshot.hasChild("brand")){
                                        String id=dataSnapshot.child("brand").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(MainActivity.this, all_products.class);

                                            productdetailsintent.putExtra("brand", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("category")){

                                    if (dataSnapshot.hasChild("category")){
                                        String id=dataSnapshot.child("category").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(MainActivity.this, all_products.class);
                                            productdetailsintent.putExtra("category", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }

                            }

                        }
                    });

                }
                if (Image.equals("Image6")) {
                    slide = (ImageView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Image6);
                    showPhoto(title, slide);
                    slide.setVisibility(VISIBLE);
                    slide.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(dataSnapshot.hasChild("type")){
                                String type=dataSnapshot.child("type").getValue().toString();
                                if(type.equals("single")){
                                    if (dataSnapshot.hasChild("id")){
                                        String id=dataSnapshot.child("id").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(MainActivity.this, ProductDetailActivity.class);
                                            productdetailsintent.putExtra("product_id", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("list")){

                                    if (dataSnapshot.hasChild("list_name")){
                                        String id=dataSnapshot.child("list_name").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(MainActivity.this, list_items.class);
                                            productdetailsintent.putExtra("product_id", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("brand")){

                                    if (dataSnapshot.hasChild("brand")){
                                        String id=dataSnapshot.child("brand").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(MainActivity.this, all_products.class);

                                            productdetailsintent.putExtra("brand", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("category")){

                                    if (dataSnapshot.hasChild("category")){
                                        String id=dataSnapshot.child("category").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(MainActivity.this, all_products.class);
                                            productdetailsintent.putExtra("category", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }

                            }

                        }
                    });

                }
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
}

    }

    private void loaditem(String type,String id,String list_name,String brand,String category) {
        if(type!=null){

            if(type.equals("single")){

                    if(id!=null) {
                        Intent productdetailsintent = new Intent(MainActivity.this, ProductDetailActivity.class);
                        productdetailsintent.putExtra("product_id", id);
                        startActivity(productdetailsintent);
                    }

            }
            if(type.equals("list")){


                    if(list_name!=null) {
                        Intent productdetailsintent = new Intent(MainActivity.this, list_items.class);
                        productdetailsintent.putExtra("category", list_name);
                        startActivity(productdetailsintent);
                    }

            }
            if(type.equals("brand")){


                    if(brand!=null) {
                        Intent productdetailsintent = new Intent(MainActivity.this, all_products.class);

                        productdetailsintent.putExtra("brand", brand);
                        startActivity(productdetailsintent);
                    }

            }
            if(type.equals("category")){


                    if(category!=null) {
                        Intent productdetailsintent = new Intent(MainActivity.this, all_products.class);
                        productdetailsintent.putExtra("category", category);
                        startActivity(productdetailsintent);
                    }

            }

        }

    }

    private void showPhoto(String url,ImageView p) {
        if (url != null) {
            Picasso.with(MainActivity.this).load(url).into(p);
        }
    }
     private String group,c;

    HashMap<String, String> HashMapForURL ;
    public void setGroup(String group, String b, String c, final String type, final String id, final String list, final String brand, final String category){
        this.group = group;
        this.b=b;
        this.c =c;
        HashMapForURL = new HashMap<String, String>();
        if(group!=null) {
            HashMapForURL.put("", group);
        }
        if(b!=null) {
            HashMapForURL.put("", b);
        }if(c!=null) {
            HashMapForURL.put("", c);
        }

        for(String name : HashMapForURL.keySet()){

            TextSliderView textSliderView = new TextSliderView(this);

            textSliderView
                    //.description(name)
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .image(HashMapForURL.get(name)).setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    loaditem(type,id,list,brand,category);

                }
            });




            textSliderView.bundle(new Bundle());

            textSliderView.getBundle()
                    .putString("extra",name);

            sliderLayout.addSlider(textSliderView);}sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
         sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
         sliderLayout.setCustomAnimation(new ChildAnimationExample());
         sliderLayout.setDuration(3600);
         sliderLayout.addOnPageChangeListener(this);


    }
    @Override
    protected void onStop() {
        super.onStop();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {
            DatabaseReference muserRef = FirebaseDatabase.getInstance().getReference().child("customers").child(mAuth.getCurrentUser().getUid());

            muserRef.child("online").setValue(ServerValue.TIMESTAMP);

        }

    }



    @Override
    public void onSliderClick(BaseSliderView slider) {


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    public static class groomingholder extends RecyclerView.ViewHolder{



        private ScaleRatingBar ratingbar;
        private  ImageView fav1,fav2;

        View mView;
        public SmallBang mSmallBang;

        public groomingholder(View itemView) {
            super(itemView);

            fav1 = (ImageView)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.fav1);
            fav2 = (ImageView)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.fav2);
            ratingbar = (ScaleRatingBar) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.ratingbar);
         //   price = (TextView) itemView.findViewById(R.id.price);

            //cutprice = (TextView) itemView.findViewById(R.id.cutprice);
            //discount = (TextView) itemView.findViewById(R.id.discount);
            //ratingtex = (TextView) itemView.findViewById(R.id.ratingtext);
            mView =itemView;

        }
        public void  setTitle(String t){
            TextView title = (TextView) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.title);
            title.setText(t);
        }
        public void setImage(String i, Context context){
            ImageView image = (ImageView) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Image);
           Picasso.with(context).load(i).placeholder(R.drawable.loading).into(image);
         //   Glide.with(context).load(i).into(image);
        }


    }

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setupBottomNavigationView() {

        BottomNavigationView bottomNavigationViewEx = (BottomNavigationView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.bottomNavViewBar);
         bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){


                    case com.supplyingyourservice.ranjeet.singh.sys.R.id.ic_house:
                        Intent intent1 = new Intent(MainActivity.this, MainActivity.class);//ACTIVITY_NUM = 0
                        context.startActivity(intent1);
                        finish();
                      overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                        break;



                    case com.supplyingyourservice.ranjeet.singh.sys.R.id.ic_circle:
                        Intent intent3 = new Intent(MainActivity.this,WatchListFragment.class);//ACTIVITY_NUM = 2
                        context.startActivity(intent3);
                       finish();
                   overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                        break;

                    case com.supplyingyourservice.ranjeet.singh.sys.R.id.ic_alert:
                        Intent intent4 = new Intent(MainActivity.this, orders.class);//ACTIVITY_NUM = 3

                        context.startActivity(intent4);
                        finish();
                     overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                        break;

                    case com.supplyingyourservice.ranjeet.singh.sys.R.id.ic_android:
                        Intent intent5 ;//ACTIVITY_NUM = 4
                        intent5 = new Intent(MainActivity.this, offers.class);
                        context.startActivity(intent5);
                      finish();
                     overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


                        break;
                }


                return false;
            }
        });
    }






    }
