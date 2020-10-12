package com.supplyingyourservice.ranjeet.singh.sys.Home.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

import com.supplyingyourservice.ranjeet.singh.sys.Addshop;
import com.supplyingyourservice.ranjeet.singh.sys.BeanlistBrands;
import com.supplyingyourservice.ranjeet.singh.sys.BeanlistGrooming;
import com.supplyingyourservice.ranjeet.singh.sys.BrandsRecyclerViewAdapter;
import com.supplyingyourservice.ranjeet.singh.sys.ChatsFragment;
import com.supplyingyourservice.ranjeet.singh.sys.ChildAnimationExample;
import com.supplyingyourservice.ranjeet.singh.sys.GroomingRecyclerViewAdapter;
import com.supplyingyourservice.ranjeet.singh.sys.ProductDetailActivity;
import com.supplyingyourservice.ranjeet.singh.sys.R;
import com.supplyingyourservice.ranjeet.singh.sys.SignIn;
import com.supplyingyourservice.ranjeet.singh.sys.SliderLayout;
import com.supplyingyourservice.ranjeet.singh.sys.SmallBang;
import com.supplyingyourservice.ranjeet.singh.sys.SmallBangListener;
import com.supplyingyourservice.ranjeet.singh.sys.TrendingRecyclerViewAdapter;
import com.supplyingyourservice.ranjeet.singh.sys.adapters.CategoryAdapter;
import com.supplyingyourservice.ranjeet.singh.sys.adapters.CategoryAdapter2;
import com.supplyingyourservice.ranjeet.singh.sys.addprod;
import com.supplyingyourservice.ranjeet.singh.sys.all_brands;
import com.supplyingyourservice.ranjeet.singh.sys.all_cateogories;
import com.supplyingyourservice.ranjeet.singh.sys.all_products;
import com.supplyingyourservice.ranjeet.singh.sys.aproduct;
import com.supplyingyourservice.ranjeet.singh.sys.books.bookstore;
import com.supplyingyourservice.ranjeet.singh.sys.books.mybooks;
import com.supplyingyourservice.ranjeet.singh.sys.browser;
import com.supplyingyourservice.ranjeet.singh.sys.commonloc.addcloc;
import com.supplyingyourservice.ranjeet.singh.sys.commonloc.commomloc;
import com.supplyingyourservice.ranjeet.singh.sys.editinfo;
import com.supplyingyourservice.ranjeet.singh.sys.elastic.SearchActivity;

import q.rorbin.badgeview.QBadgeView;

import com.squareup.picasso.Picasso;
import com.supplyingyourservice.ranjeet.singh.sys.homerv;
import com.supplyingyourservice.ranjeet.singh.sys.list_items;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static androidx.recyclerview.widget.RecyclerView.*;


public class DashboardFragment extends Fragment implements BaseSliderView.OnSliderClickListener{
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


    private TrendingRecyclerViewAdapter baseAdapter1;
    private ArrayList<BeanlistBrands> Bean2;
    private BrandsRecyclerViewAdapter baseAdapter2;


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


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_main, container, false);
        mAuth=FirebaseAuth.getInstance();



  TextView serch = (TextView) root.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.tvsearch);
        serch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productdetailsintent = new Intent (requireContext(), SearchActivity.class);

                startActivity(productdetailsintent);


            }
        });


        if(mAuth.getCurrentUser()!=null) {
            final String MY_PREFS_NAME = "BrackatPrefsFile";
            String intentcity = getActivity().getIntent().getStringExtra("city");
            SharedPreferences prefs = requireContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);


            String name = prefs.getString("city", null);
            ImageView add_shop=(ImageView)root.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.shop);
            add_shop.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent productdetailsintent = new Intent (requireContext()
                            , Addshop.class);

                    startActivity(productdetailsintent);

                }
            });

            DatabaseReference noticeref =FirebaseDatabase.getInstance().getReference().child("notice");

            final LinearLayout lin=(LinearLayout)root.findViewById(R.id.noticelin);
            final TextView notice=(TextView)root.findViewById(R.id.notice);
            final TextView noticeok=(TextView)root.findViewById(R.id.noticeok);
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
                                SharedPreferences.Editor editor =requireContext(). getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
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

                ((commomloc) getActivity().getApplication()).setloc(name);

            } else {

                if (intentcity != null) {
                    if (intentcity != null) {

                        if(name  !=null  && intentcity!=null) {
                            if (!intentcity.equals(name)) {
                                name = intentcity;
                                intentcity = name;
                            }

                        }}
                    SharedPreferences.Editor editor =requireActivity(). getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
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

                                    SharedPreferences.Editor editor =requireContext(). getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                    editor.putString("city", loc);
                                    editor.apply();

                                } else {

                                    Intent productdetailsintent = new Intent(requireContext(), addcloc.class);
                                    startActivity(productdetailsintent);
                                }
                            } else{


                                Intent productdetailsintent = new Intent (requireContext(), addcloc.class);

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

       sliderLayout= (SliderLayout)root.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.slider);



        shirt1 = (TextView) root.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.shirt1);
        jeans1 = (TextView) root.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.jeans1);
        shoes1 = (TextView) root.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.shoes1);
        slippers1 = (TextView) root.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.slippers1);
        goggles1 = (TextView) root.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.goggles1);
        groomingproducts = (TextView) root.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.groomingproducts);
        trendingproducts = (TextView) root.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.trendingproducts);
        topbrands = (TextView) root.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.topbrands);
     //   TextView Button2 = (TextView) root.findViewById(R.id.button2);
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


        goggles =(LinearLayout)root.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.goggles0);

//     *******BOT BAR COLOR *********


goggles.setOnClickListener(new OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent productdetailsintent = new Intent (requireContext(),bookstore.class);
        startActivity(productdetailsintent);

    }
});


        LinearLayout acrepair = (LinearLayout) root.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.acrepair);


        acrepair.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productdetailsintent = new Intent (requireContext(),all_brands.class);
                productdetailsintent.putExtra("category","AC Service & Repair");
                startActivity(productdetailsintent);

            }
        });
   root.findViewById(R.id.carpenter).setOnClickListener(new OnClickListener() {
       @Override
       public void onClick(View v) {
           Intent productdetailsintent = new Intent (requireContext(),all_brands.class);
           productdetailsintent.putExtra("category","Plumbers & Carpenters");
           startActivity(productdetailsintent);
       }
   });
        root.findViewById(R.id.painters).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productdetailsintent = new Intent (requireContext(),all_brands.class);
                productdetailsintent.putExtra("category","Painters");
                startActivity(productdetailsintent);
            }
        });
        root.findViewById(R.id.electricians).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productdetailsintent = new Intent (requireContext(),all_brands.class);
                productdetailsintent.putExtra("category","Electricians");
                startActivity(productdetailsintent);
            }
        });
        root.findViewById(R.id.builder).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productdetailsintent = new Intent (requireContext(),all_brands.class);
                productdetailsintent.putExtra("category","Builder");
                startActivity(productdetailsintent);
            }
        });

        root.findViewById(R.id.appliance).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productdetailsintent = new Intent (requireContext(),all_brands.class);
                productdetailsintent.putExtra("category","Appliance Repair");
                startActivity(productdetailsintent);
            }
        });


















        messagesb=(ImageView)root.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.messages);
        messagesb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent productdetailsintent = new Intent (requireContext(), ChatsFragment.class);
                startActivity(productdetailsintent);

            }
        });


        Toolbar toolbar = (Toolbar) root.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.toolbar);
        ((AppCompatActivity)getActivity()).   setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) root.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, com.supplyingyourservice.ranjeet.singh.sys.R.string.navigation_drawer_open, com.supplyingyourservice.ranjeet.singh.sys.R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) root.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.nav_view);
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
                    Picasso.with(requireContext()).load(dp).into(imag);
                }
                if(dataSnapshot.hasChild("new_message")){
                    String dp= dataSnapshot.child("new_mess" +
                            "age").getValue().toString();


                    if(root!=null){

//                    if (dp.equals("new")) {
//                        new QBadgeView(requireContext()).bindTarget(messagesb).setBadgeText("new").setBadgePadding(4, true).setBadgeTextSize(10, true).setGravityOffset(1, 1, true).setBadgeGravity(Gravity.TOP | Gravity.END);
//
//                    }
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
                Intent productdetailsintent = new Intent (requireContext(), editinfo.class);
                startActivity(productdetailsintent);

            }
        });


        drawer.setDrawerElevation(4);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                DrawerLayout drawer = (DrawerLayout) root.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.drawer_layout);
              drawer.closeDrawer(GravityCompat.START);
                if (id == com.supplyingyourservice.ranjeet.singh.sys.R.id.categories) {
                    Intent productdetailsintent = new Intent (requireContext(),all_cateogories.class);
                    startActivity(productdetailsintent);


                } else if (id == R.id.offers) {

                    Intent productdetailsintent = new Intent (getContext(), offersFragment.class);
                    startActivity(productdetailsintent);



                }
                else if (id == com.supplyingyourservice.ranjeet.singh.sys.R.id.editprofile) {


                    Intent productdetailsintent = new Intent (requireContext(),editinfo.class);
                    startActivity(productdetailsintent);



                }
                else if (id == com.supplyingyourservice.ranjeet.singh.sys.R.id.book) {


                    Intent productdetailsintent = new Intent (requireContext(),mybooks.class);
                    startActivity(productdetailsintent);


                }

                else if (id == R.id.logout) {

                    mAuth=FirebaseAuth.getInstance();
                    mAuth.signOut();
                   getActivity(). finish();



                }

                return true;

            }
        });




        rv = (RecyclerView)root.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.rv);
        rv2 = (RecyclerView)root.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.rv2);
        rv3 = (RecyclerView)root.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.rv3);
        rv4 = (RecyclerView)root.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.rv4);


        LayoutManager mLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(mLayoutManager);


        LayoutManager mLayoutManager1 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        rv2.setLayoutManager(mLayoutManager1);
        rv2.setHasFixedSize(true);
        ArrayList<aproduct> list8 = new ArrayList<>();
        CategoryAdapter categoryAdapter = new CategoryAdapter(requireContext(), list8);
        rv2.setAdapter(categoryAdapter);


        CategoryAdapter2 categoryAdapter2 = new CategoryAdapter2(requireContext(), list8);
        rv.setAdapter(categoryAdapter2);

        LayoutManager mLayoutManager5 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        rv4.setLayoutManager(mLayoutManager5);

        LayoutManager mLayoutManager2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
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
                baseAdapter2 = new BrandsRecyclerViewAdapter(getContext(), Bean2);
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


return root;
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

                    viewHolder.setImage(obj.getProductdp(),getContext().getApplicationContext());
                    viewHolder.ratingbar.setRating(obj.getRating()/2);
                   viewHolder.ratingbar=new ScaleRatingBar(requireContext());
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
                    viewHolder.mSmallBang = SmallBang.attach2Window(getActivity());


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
                            //Toast.makeText(requireContext() ,post_key,"product clicked",Toast.LENGTH_SHORT).show();
                            Intent productdetailsintent = new Intent (requireContext(), ProductDetailActivity.class);
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
//        BottomNavigationView bottomNavigationViewEx = (BottomNavigationView) root.findViewById(R.id.bottomNavViewBar);
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

        String s = ((commomloc) getActivity().getApplication()).getloc();
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
                    slide = (ImageView) requireActivity().findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Image1);
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
                                            Intent productdetailsintent = new Intent(requireContext(), ProductDetailActivity.class);
                                            productdetailsintent.putExtra("product_id", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("list")){

                                    if (dataSnapshot.hasChild("list_name")){
                                        String id=dataSnapshot.child("list_name").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(requireContext(), list_items.class);
                                            productdetailsintent.putExtra("product_id", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("brand")){

                                    if (dataSnapshot.hasChild("brand")){
                                        String id=dataSnapshot.child("brand").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(requireContext(), all_products.class);

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
                                            Intent productdetailsintent = new Intent(requireContext(), all_products.class);
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
                    slide = (ImageView) getActivity().findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Image2);
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
                                            Intent productdetailsintent = new Intent(requireContext(), ProductDetailActivity.class);
                                            productdetailsintent.putExtra("product_id", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("list")){

                                    if (dataSnapshot.hasChild("list_name")){
                                        String id=dataSnapshot.child("list_name").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(requireContext(), list_items.class);
                                            productdetailsintent.putExtra("product_id", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("brand")){

                                    if (dataSnapshot.hasChild("brand")){
                                        String id=dataSnapshot.child("brand").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(requireContext(), all_products.class);

                                            productdetailsintent.putExtra("brand", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("category")){

                                    if (dataSnapshot.hasChild("category")){
                                        String id=dataSnapshot.child("category").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(requireContext(), all_products.class);
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
                    slide = (ImageView) getActivity().findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Image3);
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
                                            Intent productdetailsintent = new Intent(requireContext(), ProductDetailActivity.class);
                                            productdetailsintent.putExtra("product_id", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("list")){

                                    if (dataSnapshot.hasChild("list_name")){
                                        String id=dataSnapshot.child("list_name").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(requireContext(), list_items.class);
                                            productdetailsintent.putExtra("product_id", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("brand")){

                                    if (dataSnapshot.hasChild("brand")){
                                        String id=dataSnapshot.child("brand").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(requireContext(), all_products.class);

                                            productdetailsintent.putExtra("brand", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("category")){

                                    if (dataSnapshot.hasChild("category")){
                                        String id=dataSnapshot.child("category").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(requireContext(), all_products.class);
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
                    slide = (ImageView) getActivity().findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Image4);
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
                                            Intent productdetailsintent = new Intent(requireContext(), ProductDetailActivity.class);
                                            productdetailsintent.putExtra("product_id", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("list")){

                                    if (dataSnapshot.hasChild("list_name")){
                                        String id=dataSnapshot.child("list_name").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(requireContext(), list_items.class);
                                            productdetailsintent.putExtra("product_id", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("brand")){

                                    if (dataSnapshot.hasChild("brand")){
                                        String id=dataSnapshot.child("brand").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(requireContext(), all_products.class);

                                            productdetailsintent.putExtra("brand", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("category")){

                                    if (dataSnapshot.hasChild("category")){
                                        String id=dataSnapshot.child("category").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(requireContext(), all_products.class);
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
                    slide = (ImageView) getActivity().findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Image5);
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
                                            Intent productdetailsintent = new Intent(requireContext(), ProductDetailActivity.class);
                                            productdetailsintent.putExtra("product_id", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("list")){

                                    if (dataSnapshot.hasChild("list_name")){
                                        String id=dataSnapshot.child("list_name").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(requireContext(), list_items.class);
                                            productdetailsintent.putExtra("product_id", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("brand")){

                                    if (dataSnapshot.hasChild("brand")){
                                        String id=dataSnapshot.child("brand").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(requireContext(), all_products.class);

                                            productdetailsintent.putExtra("brand", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("category")){

                                    if (dataSnapshot.hasChild("category")){
                                        String id=dataSnapshot.child("category").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(requireContext(), all_products.class);
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
                    slide = (ImageView) getActivity().findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Image6);
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
                                            Intent productdetailsintent = new Intent(requireContext(), ProductDetailActivity.class);
                                            productdetailsintent.putExtra("product_id", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("list")){

                                    if (dataSnapshot.hasChild("list_name")){
                                        String id=dataSnapshot.child("list_name").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(requireContext(), list_items.class);
                                            productdetailsintent.putExtra("product_id", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("brand")){

                                    if (dataSnapshot.hasChild("brand")){
                                        String id=dataSnapshot.child("brand").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(requireContext(), all_products.class);

                                            productdetailsintent.putExtra("brand", id);
                                            startActivity(productdetailsintent);
                                        }
                                    }
                                }
                                if(type.equals("category")){

                                    if (dataSnapshot.hasChild("category")){
                                        String id=dataSnapshot.child("category").getValue().toString();
                                        if(id!=null) {
                                            Intent productdetailsintent = new Intent(requireContext(), all_products.class);
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
                        Intent productdetailsintent = new Intent(requireContext(), ProductDetailActivity.class);
                        productdetailsintent.putExtra("product_id", id);
                        startActivity(productdetailsintent);
                    }

            }
            if(type.equals("list")){


                    if(list_name!=null) {
                        Intent productdetailsintent = new Intent(requireContext(), list_items.class);
                        productdetailsintent.putExtra("category", list_name);
                        startActivity(productdetailsintent);
                    }

            }
            if(type.equals("brand")){


                    if(brand!=null) {
                        Intent productdetailsintent = new Intent(requireContext(), all_products.class);

                        productdetailsintent.putExtra("brand", brand);
                        startActivity(productdetailsintent);
                    }

            }
            if(type.equals("category")){


                    if(category!=null) {
                        Intent productdetailsintent = new Intent(requireContext(), all_products.class);
                        productdetailsintent.putExtra("category", category);
                        startActivity(productdetailsintent);
                    }

            }

        }

    }

    private void showPhoto(String url,ImageView p) {
        if (url != null) {
            Picasso.with(requireContext()).load(url).into(p);
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

            TextSliderView textSliderView = new TextSliderView(requireContext());

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
    public void onSliderClick(BaseSliderView slider) {


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
         //   price = (TextView) itemView.root.findViewById(R.id.price);

            //cutprice = (TextView) itemView.root.findViewById(R.id.cutprice);
            //discount = (TextView) itemView.root.findViewById(R.id.discount);
            //ratingtex = (TextView) itemView.root.findViewById(R.id.ratingtext);
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



    }
