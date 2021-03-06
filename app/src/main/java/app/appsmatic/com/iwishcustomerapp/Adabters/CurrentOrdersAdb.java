package app.appsmatic.com.iwishcustomerapp.Adabters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.appsmatic.com.iwishcustomerapp.API.Models.UserOrder;
import app.appsmatic.com.iwishcustomerapp.Activites.OrderDetails;
import app.appsmatic.com.iwishcustomerapp.R;
import app.appsmatic.com.iwishcustomerapp.SharedPrefs.SaveSharedPreference;

/**
 * Created by Mido PC on 3/14/2017.
 */
public class CurrentOrdersAdb extends RecyclerView.Adapter<CurrentOrdersAdb.vh501> {


    private List<UserOrder> userOrders;
    private Context context;
    private String flag;




    public CurrentOrdersAdb(Context context, List<UserOrder> userOrders,String flag) {
        this.context = context;
        this.userOrders = userOrders;
        this.flag=flag;
    }

    @Override
    public vh501 onCreateViewHolder(ViewGroup parent, int viewType) {
        return new vh501(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_current_previous_order,parent,false));
    }

    @Override
    public void onBindViewHolder(vh501 holder, final int position) {

        animate(holder);

        holder.no.setText(context.getResources().getString(R.string.ordernoorderdetails) + userOrders.get(position).getOrderID() + "");
        if (userOrders.get(position).getOrderType().equals("توصيل")) {
            holder.brunch.setText(context.getResources().getString(R.string.addressorderdetails) + userOrders.get(position).getAddress());
        } else {
            holder.brunch.setText(context.getResources().getString(R.string.bruncheorderdetails) + userOrders.get(position).getBranchName());
        }
        holder.status.setText(context.getResources().getString(R.string.statusorderdetails) + userOrders.get(position).getStatus() + "");
        holder.type.setText(context.getResources().getString(R.string.typeorderdetails) + userOrders.get(position).getOrderType());
        holder.date.setText(userOrders.get(position).getOrderDate() + "");

        //Set image language for details button
        if(SaveSharedPreference.getLangId(context).equals("ar")){
            holder.detailsBtn.setImageResource(R.drawable.ardetailsbtn);
        }else{
            holder.detailsBtn.setImageResource(R.drawable.detailsbtn);
        }


        //Action details button
        holder.detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, OrderDetails.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra("id", userOrders.get(position).getOrderID())
                        .putExtra("date",userOrders.get(position).getOrderDate())
                        .putExtra("flag",flag));

            }
        });

    }

    @Override
    public int getItemCount() {
        return userOrders.size();
    }

    public void animate(RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.bounce_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }
    public static class vh501 extends RecyclerView.ViewHolder{
        private TextView no,brunch,status,type,date;
        private ImageView detailsBtn;
        public vh501(View itemView) {
            super(itemView);

            no=(TextView)itemView.findViewById(R.id.curntorder_itemlayout_ordernum);
            brunch=(TextView)itemView.findViewById(R.id.brunchtv);
            status=(TextView)itemView.findViewById(R.id.status_tv);
            type=(TextView)itemView.findViewById(R.id.type_tv);
            date=(TextView)itemView.findViewById(R.id.curntorder_itemlayout_date);
            detailsBtn=(ImageView)itemView.findViewById(R.id.current_order_details_btn);
        }
    }
}
