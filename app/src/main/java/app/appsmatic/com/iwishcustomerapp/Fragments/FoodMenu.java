package app.appsmatic.com.iwishcustomerapp.Fragments;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.HashMap;

import app.appsmatic.com.iwishcustomerapp.API.Models.ResCats;
import app.appsmatic.com.iwishcustomerapp.API.RetrofitUtilities.ClintAppApi;
import app.appsmatic.com.iwishcustomerapp.API.RetrofitUtilities.Genrator;
import app.appsmatic.com.iwishcustomerapp.Adabters.CategoriesAdb;
import app.appsmatic.com.iwishcustomerapp.R;
import app.appsmatic.com.iwishcustomerapp.Tools.ResturantId;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FoodMenu extends Fragment {

    private RecyclerView categoriesList;
    private CategoriesAdb categoriesAdb;
    private Animation anim;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        anim = AnimationUtils.loadAnimation(view.getContext(), R.anim.fadein);


        HashMap resid = new HashMap();
        resid.put("RestaurantID", ResturantId.resId);

        Genrator.createService(ClintAppApi.class).GetCategories(resid).enqueue(new Callback<ResCats>() {
            @Override
            public void onResponse(Call<ResCats> call, Response<ResCats> response) {
                if (response.isSuccessful()) {

                    //get code
                    String code = response.body().getCode() + "";

                    //Check message code
                    if (!code.equals("0")) {

                        categoriesList = (RecyclerView) getActivity().findViewById(R.id.catslist);
                        categoriesList.clearAnimation();
                        categoriesList.setAnimation(anim);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        categoriesAdb = new CategoriesAdb(getContext(), response.body());
                        categoriesList.setLayoutManager(layoutManager);
                        categoriesList.setAdapter(categoriesAdb);

                        categoriesList.setBackgroundResource(R.color.btn_press_color);

                        MealsFrag mealsFrag = new MealsFrag();
                        Bundle bundle = new Bundle();
                        String transId =response.body().getMessage().get(0).getID()+"";
                        bundle.putString("catid", transId);
                        mealsFrag.setArguments(bundle);
                        try {
                            FragmentManager fragmentManager = getActivity().getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.foodmealsfragmentcontener, mealsFrag).commit();
                        }catch (Exception e){

                        }

                    } else {

                       //message code 0 failed
                    }


                } else {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.Responsenotsucusess)
                            .setCancelable(false)
                            .setIcon(R.drawable.erroricon)
                            .setTitle(R.string.communicationerorr)
                            .setPositiveButton(R.string.Dissmiss, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }

            }

            @Override
            public void onFailure(Call<ResCats> call, Throwable t) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(t.getMessage().toString()+"")
                        .setCancelable(false)
                        .setIcon(R.drawable.erroricon)
                        .setTitle(R.string.connectionerorr)
                        .setPositiveButton(R.string.Dissmiss, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });







    }



























}
