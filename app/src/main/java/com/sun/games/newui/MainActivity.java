package com.sun.games.newui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    int[] arr = {R.mipmap.p10, R.mipmap.p11, R.mipmap.p12, R.mipmap.p13, R.mipmap.p14, R.mipmap.p15, R.mipmap.p16, R.mipmap.p17, R.mipmap.p18, R.mipmap.p19};
    List<Integer> mRandomList = new ArrayList<>();
    Handler mHandler = new Handler();

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer)
    DrawerLayout mDrawer;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.action_button)
    FloatingActionButton mActionButton;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeLayout;
    private Snackbar mSnackbar;
    private NewAdapter mNewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mToolbar.setTitle("first");
        setSupportActionBar(mToolbar);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.mipmap.eight);
//        }

//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.app_name, R.string.example);
        actionBarDrawerToggle.syncState();
        mDrawer.addDrawerListener(actionBarDrawerToggle);

        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSnackbar != null && mSnackbar.isShown()) {
                    return;
                }
                mSnackbar = Snackbar.make(v, "click actionbutton", Snackbar.LENGTH_SHORT).setAction("undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "我也不知道是什么", Toast.LENGTH_SHORT).show();
                    }
                });
                mSnackbar.getView().setBackgroundColor(Color.parseColor("#ff0000"));
                mSnackbar.show();
            }
        });


        mNavView.setCheckedItem(R.id.nav_call);
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_call:
                        Toast.makeText(MainActivity.this, "click call", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_friend:
                        Toast.makeText(MainActivity.this, "click friend", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_location:
                        Toast.makeText(MainActivity.this, "click location", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_mail:
                        Toast.makeText(MainActivity.this, "click mail", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_task:
                        Toast.makeText(MainActivity.this, "click task", Toast.LENGTH_SHORT).show();
                        break;
                }

                mDrawer.closeDrawers();
                return true;
            }
        });

        getRandomList();
        mRecyclerview.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        mNewAdapter = new NewAdapter();
        mRecyclerview.setAdapter(mNewAdapter);

        mSwipeLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimaryDark), getResources().getColor(R.color.colorAccent));
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ReListRandom();
                        mNewAdapter.notifyDataSetChanged();
                        mSwipeLayout.setRefreshing(false);
                    }
                },3000);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(MainActivity.this, "click backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(MainActivity.this, "click delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(MainActivity.this, "click settings", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    private int getRandomNum() {
        Random random = new Random();
        return random.nextInt(10);
    }

    private List<Integer> getRandomList() {
        for (int i = 0; i < 30; i++) {
            mRandomList.add(getRandomNum());
        }
        return mRandomList;
    }

    private void ReListRandom() {
        mRandomList.clear();
        getRandomList();
    }

    class CardHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;

        public CardHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.item_iv);
            mTextView = itemView.findViewById(R.id.item_tv);
        }
    }

    class NewAdapter extends RecyclerView.Adapter<CardHolder> {
        @Override
        public CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.card_item, parent, false);
            return new CardHolder(view);
        }

        @Override
        public void onBindViewHolder(CardHolder holder, final int position) {
            holder.mImageView.setImageResource(arr[mRandomList.get(position)]);
            holder.mTextView.setText("这是第" + position + "个");

            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position != 0) {
                        return;
                    }
                    startActivity(new Intent(MainActivity.this,CollapsingActivity.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mRandomList.size();
        }
    }

}
