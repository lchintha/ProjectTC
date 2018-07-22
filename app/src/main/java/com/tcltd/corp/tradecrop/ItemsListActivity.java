package com.tcltd.corp.tradecrop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemsListActivity extends AppCompatActivity {

    @BindView(R.id.appbar) AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.header) ImageView headerImage;
    @BindView(R.id.anim_toolbar) Toolbar toolbar;
    @BindView(R.id.recycleView) RecyclerView recyclerView;
    String category;
    Bitmap bitmap;
    int mutedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);
        ButterKnife.bind(this);

        category = getIntent().getStringExtra("Title");

        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setTitle(category);

        setHeaderImage();
        bitmap = getBitmap();
        setCollapsingToolbarLayoutColor(bitmap);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void setHeaderImage(){
        switch (category) {
            case "Vegetables":
                headerImage.setImageResource(R.drawable.vegetable_header);
                break;
            case "Fruits":
                headerImage.setImageResource(R.drawable.fruits_header);
                break;
            case "DairyProducts":
                headerImage.setImageResource(R.drawable.dairy_products_header);
                break;
            case "Sweets":
                headerImage.setImageResource(R.drawable.sweets_header);
                break;
            case "Pickles":
                headerImage.setImageResource(R.drawable.pickles_header);
                break;
            case "Grocery":
                headerImage.setImageResource(R.drawable.grocery_header);
                break;
        }
    }

    public Bitmap getBitmap(){
        switch (category){
            case "Vegetables":
            case "Fruits":
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.vegetable_header);
                break;
            case "DairyProducts":
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.vegetable_header);
                break;
            case "Sweets":
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.vegetable_header);
                break;
            case "Pickles":
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.vegetable_header);
                break;
            case "Grocery":
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.vegetable_header);
                break;

        }
        return bitmap;
    }

    public void setCollapsingToolbarLayoutColor(Bitmap bitmap){
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //getWindow().setStatusBarColor(mutedColor);
                }
                collapsingToolbarLayout.setContentScrimColor(mutedColor);
            }
        });
    }
}
