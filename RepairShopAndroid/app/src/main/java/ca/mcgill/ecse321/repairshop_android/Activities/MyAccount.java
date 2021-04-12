package ca.mcgill.ecse321.repairshop_android.Activities;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ca.mcgill.ecse321.repairshop_android.Activities.Utility.RepairShopUtil;
import ca.mcgill.ecse321.repairshop_android.R;

public class MyAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        setTextField(R.layout.activity_my_account);
    }

    private void setTextField(@LayoutRes int layoutResID){

        TextView tvUserName = (TextView) findViewById(R.id.editTextUserName);

        TextView tvEmail = (TextView) findViewById(R.id.editTextEmailAddress);

        tvUserName.setText(RepairShopUtil.loginUserName);
        tvEmail.setText(RepairShopUtil.loginUserEmail);


    }

}