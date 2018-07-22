package com.tcltd.corp.tradecrop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chint on 12/27/2017.
 */

public class MainScreenFragment extends Fragment {

    CardView vegetables, fruits, dairyProducts, sweets, pickles, grocery;
    Intent intent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);

        //ButterKnife.bind(getActivity());
        vegetables = view.findViewById(R.id.vegetableCard);
        fruits = view.findViewById(R.id.fruitsCard);
        dairyProducts = view.findViewById(R.id.dairyProductsCard);
        sweets = view.findViewById(R.id.sweetsCard);
        pickles = view.findViewById(R.id.picklesCard);
        grocery = view.findViewById(R.id.groceryCard);

        CardView[] allViews = {vegetables, fruits, dairyProducts, sweets, pickles, grocery};
        for(CardView cardView : allViews) {
            cardView.setBackgroundResource(R.drawable.main_screen_fragment_background);
        }

        intent = new Intent(getActivity(), ItemsListActivity.class);
        vegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("Title", "Vegetables");
                startActivity(intent);
            }
        });

        fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("Title", "Fruits");
                startActivity(intent);
            }
        });

        dairyProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("Title", "DairyProducts");
                startActivity(intent);
            }
        });

        sweets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("Title", "Sweets");
                startActivity(intent);
            }
        });

        pickles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("Title", "Pickles");
                startActivity(intent);
            }
        });

        grocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("Title", "Grocery");
                startActivity(intent);
            }
        });
        return view;
    }


}
