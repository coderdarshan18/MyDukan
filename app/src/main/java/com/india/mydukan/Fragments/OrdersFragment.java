package com.india.mydukan.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.navigation.NavigationView;
import com.india.mydukan.Adpters.BannerAdapter;
import com.india.mydukan.Adpters.CatAdpter;
import com.india.mydukan.Adpters.SimpleVerticalAdapter;
import com.india.mydukan.HomeActivity;
import com.india.mydukan.MainActivity;
import com.india.mydukan.Models.BannerModel;
import com.india.mydukan.Models.CategoryModel;
import com.india.mydukan.Models.SimpleVerticalModel;
import com.india.mydukan.R;
import com.india.mydukan.RetrofitApi.ApiClient;
import com.india.mydukan.RetrofitApi.ApiInterface;
import com.india.mydukan.RetrofitApi.Users;
import com.india.mydukan.Sessions.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrdersFragment() {
        // Required empty public constructor
    }

    DrawerLayout drawerLayout;
    ImageView navigationBar, iv_logout;
    LinearLayout ll_First, ll_Second, ll_Third, ll_Fourth,ll_Fifth, ll_Sixth, ll_Seventh;
    NavigationView navigationView;
    TextView tv_logout;
    private View view;
    private RelativeLayout loginSignUp,bookmarks,mydukanGold,logout;
    private TextView your_orders,faviourite_orders,address_book,online_orders,send_feed,emergency,playstore;
    SessionManager sessionManager;
    ////////////category slider start///////////

    private RecyclerView recyclerView;
    private CatAdpter catAdpter;
    private List<CategoryModel> categoryModelList;

    public static ApiInterface apiInterface;
    ///////////category slider end/////////////


    ////////////Banner slider start///////////

    private RecyclerView recyclerViewBanner;
    private BannerAdapter bannerAdapter;
    private List<BannerModel> bannerModelList;

    ///////////Bannner slider end/////////////

    private RecyclerView recyclerViewSimple;
    private SimpleVerticalAdapter simpleVerticalAdapter;
    private List<SimpleVerticalModel> simpleVerticalModelList;

    ///////////vertical slider end/////////////



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdersFragment newInstance(String param1, String param2) {
        OrdersFragment fragment = new OrdersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_orders, container, false);
        sessionManager = new SessionManager(getContext());



        onSetNavigationDrawerEvents();
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        init();

        return view;

    }

    private void init() {


        /////////////////////categorymodel list start //////////////////////////////////////////
        recyclerView =(RecyclerView) view.findViewById(R.id.recylerView2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        categoryModelList = new ArrayList<>();
        Call<Users> categoryCall =apiInterface.getCategories();
        categoryCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                categoryModelList = response.body().getCategory();

                catAdpter = new CatAdpter(categoryModelList,getContext());
                recyclerView.setAdapter(catAdpter);
                catAdpter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });



        /////////////////////////category model list end ///////////////////////////////////////////



        ///////////////////////bannner model list start////////////////////////////////////////////

        recyclerViewBanner =(RecyclerView) view.findViewById(R.id.recylerViewBanner);
        LinearLayoutManager layoutManagerBanner = new LinearLayoutManager(getContext());
        layoutManagerBanner.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewBanner.setLayoutManager(layoutManagerBanner);

        bannerModelList = new ArrayList<>();


        bannerAdapter=new BannerAdapter(bannerModelList,getContext());
        recyclerViewBanner.setAdapter(bannerAdapter);
        bannerAdapter.notifyDataSetChanged();

        autoScroll();


        //////////////////////bannner model list end///////////////////////////////////////////////



        ///////////////////////SimpleVertical model list start////////////////////////////////////////////

        recyclerViewSimple =(RecyclerView) view.findViewById(R.id.recylerViewSimple);
        LinearLayoutManager layoutManagerSimple = new LinearLayoutManager(getContext());
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


        simpleVerticalAdapter=new SimpleVerticalAdapter(simpleVerticalModelList,getContext());
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


    private void onSetNavigationDrawerEvents() {

        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) view.findViewById(R.id.navigationView);

        drawerLayout.openDrawer(GravityCompat.START);

        navigationBar = (ImageView) view.findViewById(R.id.navigationBar);

        loginSignUp =(RelativeLayout) view.findViewById(R.id.relativeLayout2);
        logout =(RelativeLayout) view.findViewById(R.id.logout);
        bookmarks =(RelativeLayout) view.findViewById(R.id.relativeLayout3);
        mydukanGold =(RelativeLayout) view.findViewById(R.id.relativeLayout4);

        your_orders =(TextView) view.findViewById(R.id.your_orders);
        faviourite_orders =(TextView) view.findViewById(R.id.faviourite_orders);
        address_book =(TextView) view.findViewById(R.id.address_book);
        online_orders =(TextView) view.findViewById(R.id.online_orders);
        send_feed =(TextView) view.findViewById(R.id.send_feed);
        emergency =(TextView) view.findViewById(R.id.emergency);
        playstore =(TextView) view.findViewById(R.id.playstore);




        navigationBar.setOnClickListener(this);
        loginSignUp.setOnClickListener(this);
        logout.setOnClickListener(this);
        bookmarks.setOnClickListener(this);
        mydukanGold.setOnClickListener(this);

        your_orders.setOnClickListener(this);
        online_orders.setOnClickListener(this);
        address_book.setOnClickListener(this);
        faviourite_orders.setOnClickListener(this);
        send_feed.setOnClickListener(this);
        emergency.setOnClickListener(this);
        playstore.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navigationBar:
                drawerLayout.openDrawer(navigationView, true);
                break;

            case R.id.relativeLayout2:
                Toast.makeText(getContext(), "loginSignUp", Toast.LENGTH_SHORT).show();
                break;

            case R.id.logout:
                Logout();
                break;

            case R.id.relativeLayout3:
                Toast.makeText(getContext(), "bookmarks", Toast.LENGTH_SHORT).show();
                break;

            case R.id.relativeLayout4:
                Toast.makeText(getContext(), "mydukanGold", Toast.LENGTH_SHORT).show();
                break;

            case R.id.your_orders:
                Toast.makeText(getContext(), "your_orders", Toast.LENGTH_SHORT).show();
                break;
            case R.id.online_orders:
                Toast.makeText(getContext(), "online_orders", Toast.LENGTH_SHORT).show();
                break;

            case R.id.address_book:
                Toast.makeText(getContext(), "address_book", Toast.LENGTH_SHORT).show();
                break;

            case R.id.faviourite_orders:
                Toast.makeText(getContext(), "faviourite_orders", Toast.LENGTH_SHORT).show();
                break;

            case R.id.send_feed:
                Toast.makeText(getContext(), "send_feed", Toast.LENGTH_SHORT).show();
                break;

            case R.id.emergency:
                Toast.makeText(getContext(), "emergency", Toast.LENGTH_SHORT).show();
                break;

            case R.id.playstore:
                Toast.makeText(getContext(), "playstore", Toast.LENGTH_SHORT).show();
                break;


        }
    }

    private void Logout() {

        sessionManager.editor.clear();
        sessionManager.editor.commit();

        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
        Animatoo.animateSwipeRight(getContext());
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!sessionManager.isLogin()){
            sessionManager.editor.clear();
            sessionManager.editor.commit();

            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
            Animatoo.animateSwipeRight(getContext());
        }
    }
}