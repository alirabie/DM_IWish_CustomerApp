package app.appsmatic.com.iwishcustomerapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import app.appsmatic.com.iwishcustomerapp.Activites.Home;
import app.appsmatic.com.iwishcustomerapp.R;


public class MyAccount extends Fragment {

    private TextView customerNameTv,phoneNumber;
    private ImageView editphone,editname,editpassword;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_account, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Home.setUserProfileInfo(getContext());
        customerNameTv=(TextView)view.findViewById(R.id.cuustomer_tv_name);
        phoneNumber=(TextView)view.findViewById(R.id.account_tv_phone);
        editname=(ImageView)view.findViewById(R.id.edit_name_btn);
        editphone=(ImageView)view.findViewById(R.id.edit_num_btn);
        editpassword=(ImageView)view.findViewById(R.id.edit_pass_btn);
        customerNameTv.setText(Home.userProfile.getFirstName()+" "+Home.userProfile.getLastName());
        phoneNumber.setText(Home.userProfile.getMobileNo()+"");


        editname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Soon Message
                Toast.makeText(getContext(), getResources().getString(R.string.soon), Toast.LENGTH_LONG).show();

            }
        });

        editpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Soon Message
                Toast.makeText(getContext(), getResources().getString(R.string.soon), Toast.LENGTH_LONG).show();

            }
        });

        editphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Soon Message
                Toast.makeText(getContext(), getResources().getString(R.string.soon), Toast.LENGTH_LONG).show();

            }
        });





    }
}
