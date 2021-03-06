package app.appsmatic.com.iwishcustomerapp.Adabters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.appsmatic.com.iwishcustomerapp.Activites.Home;
import app.appsmatic.com.iwishcustomerapp.Activites.ShoppingCart;
import app.appsmatic.com.iwishcustomerapp.CartStructure.CartMeal;
import app.appsmatic.com.iwishcustomerapp.R;
import app.appsmatic.com.iwishcustomerapp.SharedPrefs.SaveSharedPreference;
import app.appsmatic.com.iwishcustomerapp.URLS.BaseURL;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mido PC on 1/21/2017.
 */
public class CartAdb extends RecyclerView.Adapter<CartAdb.VH1001> {

    private List<CartMeal>cartMeals=new ArrayList<>();
    private Context context;
    private static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";

    public CartAdb(List<CartMeal> cartMeals, Context context) {
        this.cartMeals = cartMeals;
        this.context = context;
    }

    @Override
    public VH1001 onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH1001(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_order_cart,parent,false));
    }

    @Override
    public void onBindViewHolder(final VH1001 holder, final int position) {
        holder.name.setText(cartMeals.get(position).getMealName()+"");
        //put title font style
        Typeface face=Typeface.createFromAsset(context.getAssets(), "bbcfont.ttf");
        holder.name.setTypeface(face);
        holder.mealcount.setText(cartMeals.get(position).getMealCount()+"");
        holder.mealdec.setText(cartMeals.get(position).getMealDecription());
        //Calc meal Count * price and Show it
        holder.mealprice.setText(cartMeals.get(position).getMealPrice()*cartMeals.get(position).getMealCount()+"");

        //Encoding Img URL
        String url = Uri.encode(BaseURL.IMGS + cartMeals.get(position).getMealPic().toString(), ALLOWED_URI_CHARS);
        //Check Settings For Load images
        if(SaveSharedPreference.getImgLoadingSatatus(context)){
            Picasso.with(context)
                    .load(url)
                    .placeholder(R.drawable.rotat)
                    .fit()
                    .into(holder.mealpic);
        }else {

            Picasso.with(context)
                    .load(R.drawable.mealsplaceholder)
                    .fit()
                    .into(holder.mealpic);
        }

        //Setup Addition list
        if(cartMeals.get(position).getMealAdditions().isEmpty()){
           holder.tvAdd.setVisibility(View.INVISIBLE);
        }else {
            holder.tvAdd.setVisibility(View.VISIBLE);
            holder.additinsList.setAdapter(new CartMealsAdditionsAdb(cartMeals.get(position).getMealAdditions(), context));
            holder.additinsList.setLayoutManager(new LinearLayoutManager(context));
        }


        //Setup Customizations List
        if(cartMeals.get(position).getCustomization()==null||cartMeals.get(position).getCustomization().isEmpty()){
            holder.tvCust.setVisibility(View.INVISIBLE);
        }else {
            holder.tvCust.setVisibility(View.VISIBLE);
            holder.customizatinsList.setAdapter(new CartCustomizationsAdb(cartMeals.get(position).getCustomization(),context));
            holder.customizatinsList.setLayoutManager(new LinearLayoutManager(context));
        }


        //Increment Count of meal
        holder.upcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get Current count
                int num=cartMeals.get(position).getMealCount();
                //Increment It
                num++;
                //Multiply to meal price and show it
                holder.mealprice.setText(cartMeals.get(position).getMealPrice() * num + "");
                //show count
                holder.mealcount.setText(num + "");
                //update cart with new values
                cartMeals.get(position).setMealCount(num);
                //update price
                ShoppingCart.updateCartPrice(context);
                //Update prefs
                SaveSharedPreference.setCartOrders(context, Home.cartMeals);

            }
        });

        //Decrement Count Of meal
        holder.downcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Current count
                int num=cartMeals.get(position).getMealCount();
                //Decrement It
                num--;
                //Multiply to meal price and show it
                holder.mealprice.setText(cartMeals.get(position).getMealPrice()*num+"");
                //update cart with new values
                cartMeals.get(position).setMealCount(num);
                //show count
                holder.mealcount.setText(num + "");
                //Check if count 0 delete meal from cart
                if(num==0){
                    cartMeals.remove(position);
                    notifyDataSetChanged();
                    //check if cart is empty set badge 0 and close cart if not update badge only
                    if(Home.cartMeals.isEmpty()){
                        Home.icon = (LayerDrawable)Home.itemCart.getIcon();
                        Home.setBadgeCount(context, Home.icon, 0 + "");
                        //Update prefs
                        SaveSharedPreference.setCartOrders(context, Home.cartMeals);
                        //close
                        ((Activity)context).finish();
                    }else {
                        Home.icon = (LayerDrawable)Home.itemCart.getIcon();
                        Home.setBadgeCount(context, Home.icon, Home.cartMeals.size() + "");
                    }

                }
                ShoppingCart.updateCartPrice(context);
                //Update prefs
                SaveSharedPreference.setCartOrders(context, Home.cartMeals);
            }
        });


        //Delete Meal button Action
        holder.deleteMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(R.string.deletemeal)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                cartMeals.remove(position);
                                notifyDataSetChanged();
                                //update cart badge count
                                if(Home.cartMeals.isEmpty()){
                                    Home.icon = (LayerDrawable)Home.itemCart.getIcon();
                                    Home.setBadgeCount(context, Home.icon, 0 + "");
                                    //Update prefs
                                    SaveSharedPreference.setCartOrders(context, Home.cartMeals);
                                    //close
                                    ((Activity)context).finish();
                                }else {
                                    Home.icon = (LayerDrawable)Home.itemCart.getIcon();
                                    Home.setBadgeCount(context, Home.icon, Home.cartMeals.size() + "");
                                }
                                ShoppingCart.updateCartPrice(context);
                                //Update prefs
                                SaveSharedPreference.setCartOrders(context, Home.cartMeals);

                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        }).setIcon(android.R.drawable.alert_light_frame);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });





    }

    @Override
    public int getItemCount() {
        return cartMeals.size();
    }



    public static class VH1001 extends RecyclerView.ViewHolder{

        private TextView name,mealdec,mealprice,mealcount,tvAdd,tvCust;
        private ImageView upcount,downcount,deleteMeal;
        private RecyclerView additinsList;
        private RecyclerView customizatinsList;
        private CircleImageView mealpic;

        public VH1001(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.cart_meal_name);
            mealdec=(TextView)itemView.findViewById(R.id.cart_tv_meal_info);
            mealprice=(TextView)itemView.findViewById(R.id.cart_tv_meal_price);
            mealcount=(TextView)itemView.findViewById(R.id.cart_meal_count);
            tvAdd=(TextView)itemView.findViewById(R.id.add_tv_cart);
            tvCust=(TextView)itemView.findViewById(R.id.cust_tv_cart);

            upcount=(ImageView)itemView.findViewById(R.id.cart_meal_inc);
            downcount=(ImageView)itemView.findViewById(R.id.cart_meal_dec);
            mealpic=(CircleImageView)itemView.findViewById(R.id.cart_meal_pic);
            deleteMeal=(ImageView)itemView.findViewById(R.id.delete_meal_btn);

            additinsList=(RecyclerView)itemView.findViewById(R.id.cart_additions_list);
            customizatinsList=(RecyclerView)itemView.findViewById(R.id.cart_customization_list);





        }
    }
}
