package app.appsmatic.com.iwishcustomerapp.Adabters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import app.appsmatic.com.iwishcustomerapp.API.Models.ResMeals;
import app.appsmatic.com.iwishcustomerapp.Activites.Customization;
import app.appsmatic.com.iwishcustomerapp.SharedPrefs.SaveSharedPreference;
import app.appsmatic.com.iwishcustomerapp.URLS.BaseURL;
import app.appsmatic.com.iwishcustomerapp.R;

/**
 * Created by Mido PC on 12/19/2016.
 */
public class MealsAdb extends RecyclerView.Adapter<MealsAdb.vh2> {

    private ResMeals meals;
    private Context context;
    private static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";

    public MealsAdb(Context context, ResMeals meals) {
        this.context = context;
        this.meals = meals;
    }


    @Override
    public vh2 onCreateViewHolder(ViewGroup parent, int viewType) {
        return new vh2(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_meal,parent,false));
    }

    @Override
    public void onBindViewHolder(vh2 holder, final int position) {
        animate(holder);
        holder.title.setText(meals.getMessage().get(position).getName()+"");

        //put title font style
        Typeface face=Typeface.createFromAsset(context.getAssets(), "bbcfont.ttf");
        holder.title.setTypeface(face);


        holder.details.setText(meals.getMessage().get(position).getDescription()+"");
        holder.price.setText(meals.getMessage().get(position).getPrice()+" "+context.getResources().getString(R.string.rs));

        //Encoding Img URL
        String url = Uri.encode(BaseURL.IMGS+meals.getMessage().get(position).getImagePreview().toString(), ALLOWED_URI_CHARS);


        //Check Settings For Load images
        if(SaveSharedPreference.getImgLoadingSatatus(context)){
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.rotat)
                .fit()
                .into(holder.img);
        }else {

            Picasso.with(context)
                    .load(R.drawable.mealsplaceholder)
                    .fit()
                    .into(holder.img);
        }



        //Customize order button
        //Check Os Ver
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.btn.setBackgroundResource(R.drawable.ripple);
        }
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(new Intent(context, Customization.class)
                        .putExtra("price", meals.getMessage().get(position).getPrice())
                        .putExtra("mealId", meals.getMessage().get(position).getID())
                        .putExtra("mealname", meals.getMessage().get(position).getName()+"")
                        .putExtra("mealdec",meals.getMessage().get(position).getDescription()+"")
                        .putExtra("mealspic",meals.getMessage().get(position).getImagePreview()+""));




            }
        });

    }

    @Override
    public int getItemCount() {
        return meals.getMessage().size();
    }

    public void animate(RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.bounce_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }

    public static class vh2 extends RecyclerView.ViewHolder{
        private TextView title,details,price;
        private ImageView img,btn;
        public vh2(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.tv_meal_home_mealname);
            details=(TextView)itemView.findViewById(R.id.tv_meal_home_maildetails);
            price=(TextView)itemView.findViewById(R.id.tv_meal_home_price);
            img=(ImageView)itemView.findViewById(R.id.meal_home_mealpic);
            btn=(ImageView)itemView.findViewById(R.id.addmeal_home_meals_btn);
        }
    }
}
