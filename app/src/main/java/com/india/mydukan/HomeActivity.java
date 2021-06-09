package com.india.mydukan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.india.mydukan.Adpters.BannerAdapter;
import com.india.mydukan.Adpters.CatAdpter;
import com.india.mydukan.Adpters.SimpleVerticalAdapter;
import com.india.mydukan.Fragments.GoOutFragment;
import com.india.mydukan.Fragments.GoldFragment;
import com.india.mydukan.Fragments.OrdersFragment;
import com.india.mydukan.Fragments.VideosFragment;
import com.india.mydukan.Models.BannerModel;
import com.india.mydukan.Models.CategoryModel;
import com.india.mydukan.Models.SimpleVerticalModel;
import com.india.mydukan.RetrofitApi.ApiClient;
import com.india.mydukan.RetrofitApi.ApiInterface;
import com.india.mydukan.RetrofitApi.Users;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG ="HomeActivity" ;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    public static ApiInterface apiInterface;

    ////////////category slider start///////////

    private RecyclerView recyclerView;
    private CatAdpter catAdpter;
    private List<CategoryModel> categoryModelList;

    ///////////category slider end/////////////


    ////////////Banner slider start///////////

    private RecyclerView recyclerViewBanner;
    private BannerAdapter bannerAdapter;
    private List<BannerModel> bannerModelList;

    ///////////Bannner slider end/////////////


    ////////////Banner slider start///////////

    private RecyclerView recyclerViewSimple;
    private SimpleVerticalAdapter simpleVerticalAdapter;
    private List<SimpleVerticalModel> simpleVerticalModelList;

    ///////////vertical slider end/////////////






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /////////changing the color of status////
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        ///////status end//////

        //frameLayout =(FrameLayout) findViewById(R.id.frameLayout);

        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);


        bottomNavigationView =(BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigation);

        init();


    }
    private void init() {


        /////////////////////categorymodel list start //////////////////////////////////////////
        recyclerView =(RecyclerView) findViewById(R.id.recylerView2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        categoryModelList = new ArrayList<>();
        Call<Users> categoryCall= apiInterface.getCategories();
        categoryCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                categoryModelList=response.body().getCategory();
                Log.d(TAG, "onResponse: "+response);


                catAdpter = new CatAdpter(categoryModelList,HomeActivity.this);
                recyclerView.setAdapter(catAdpter);
                catAdpter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Api is going in wrong way", Toast.LENGTH_SHORT).show();
            }
        });

        /////////////////////////category model list end ///////////////////////////////////////////



        ///////////////////////bannner model list start////////////////////////////////////////////

        recyclerViewBanner =(RecyclerView) findViewById(R.id.recylerViewBanner);
        LinearLayoutManager layoutManagerBanner = new LinearLayoutManager(HomeActivity.this);
        layoutManagerBanner.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewBanner.setLayoutManager(layoutManagerBanner);

        bannerModelList = new ArrayList<>();
        Call<Users> bannerCall = apiInterface.getBanners();
        bannerCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                bannerModelList = response.body().getBanners();
                bannerAdapter=new BannerAdapter(bannerModelList,HomeActivity.this);
                recyclerViewBanner.setAdapter(bannerAdapter);
                bannerAdapter.notifyDataSetChanged();
                autoScroll();


            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });



//

        //////////////////////bannner model list end///////////////////////////////////////////////


        ///////////////////////SimpleVertical model list start////////////////////////////////////////////

        recyclerViewSimple =(RecyclerView) findViewById(R.id.recylerViewSimple);
        LinearLayoutManager layoutManagerSimple = new LinearLayoutManager(HomeActivity.this);
        layoutManagerSimple.setOrientation(RecyclerView.VERTICAL);
        recyclerViewSimple.setLayoutManager(layoutManagerSimple);

        simpleVerticalModelList = new ArrayList<>();
//        simpleVerticalModelList.add(new SimpleVerticalModel(R.drawable.arrow_right));
        simpleVerticalModelList.add(new SimpleVerticalModel(R.drawable.store,"Mug Dal","Mug dal in belgaonkar shop","20% off - use code darshan","Rs.250 per kg","well sanitize product","3.5"));
        simpleVerticalModelList.add(new SimpleVerticalModel(R.drawable.store,"Mug Dal","Mug dal in belgaonkar shop","20% off - use code darshan","Rs.250 per kg","well sanitize product","3.5"));
        simpleVerticalModelList.add(new SimpleVerticalModel(R.drawable.store,"Mug Dal","Mug dal in belgaonkar shop","20% off - use code darshan","Rs.250 per kg","well sanitize product","3.5"));
        simpleVerticalModelList.add(new SimpleVerticalModel(R.drawable.store,"Mug Dal","Mug dal in belgaonkar shop","20% off - use code darshan","Rs.250 per kg","well sanitize product","3.5"));
        simpleVerticalModelList.add(new SimpleVerticalModel(R.drawable.store,"Mug Dal","Mug dal in belgaonkar shop","20% off - use code darshan","Rs.250 per kg","well sanitize product","3.5"));
        simpleVerticalModelList.add(new SimpleVerticalModel(R.drawable.store,"Arhar Dal","Arhar dal in belgaum","20% off - use code darshan","Rs.250 per kg","well sanitize product","3.5"));
        simpleVerticalModelList.add(new SimpleVerticalModel(R.drawable.store,"Arhar Dal","Arhar dal in belgaum","20% off - use code darshan","Rs.250 per kg","well sanitize product","3.5"));
        simpleVerticalModelList.add(new SimpleVerticalModel(R.drawable.store,"Arhar Dal","Arhar dal in belgaum","20% off - use code darshan","Rs.250 per kg","well sanitize product","3.5"));
        simpleVerticalModelList.add(new SimpleVerticalModel(R.drawable.store,"Arhar Dal","Arhar dal in belgaum","20% off - use code darshan","Rs.250 per kg","well sanitize product","3.5"));


        simpleVerticalAdapter=new SimpleVerticalAdapter(simpleVerticalModelList,HomeActivity.this);
        recyclerViewSimple.setAdapter(simpleVerticalAdapter);
        simpleVerticalAdapter.notifyDataSetChanged();
/////////////////////vertical end///////////////////////////////////

    }

    public void autoScroll(){
        final int speedScroll =0;
        final Handler handler = new Handler();
        final Runnable runnable= new Runnable() {
            int count=0;
            @Override
            public void run() {
                if(count == bannerAdapter.getItemCount())
                    count=0;
                if(count < bannerAdapter.getItemCount()){
                    recyclerViewBanner.smoothScrollToPosition(++count);
                    handler.postDelayed(this,speedScroll);
                }

            }
        };
        handler.postDelayed(runnable,speedScroll);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigation =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.orders:
                            selectedFragment= new OrdersFragment();
                            break;

                        case R.id.goout:
                           selectedFragment=new GoOutFragment();
                            break;

                        case R.id.gold:
                            selectedFragment=new GoldFragment();
                            break;

                        case R.id.videos:
                            selectedFragment = new VideosFragment();
                            break;

                    }
                    /////////replacing by defalut fragment on home activity//////////
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                            selectedFragment).commit();
                    return true;
                }
            };
}