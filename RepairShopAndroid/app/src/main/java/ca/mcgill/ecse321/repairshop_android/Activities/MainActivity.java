package ca.mcgill.ecse321.repairshop_android.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ca.mcgill.ecse321.repairshop_android.Activities.Customer.CustomerHomePage;
import ca.mcgill.ecse321.repairshop_android.R;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button loginBtn;
    private String error = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
        loginUserHandler();
        refreshErrorMessage();
    }

    private void setViews(){
        loginBtn = findViewById(R.id.loginBtn);
    }

    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }

    // Login handler
    private void loginUserHandler(){
        //TODO: depending wheather its a customer, admin or technician logging in, redirect them to the right screen

        //CUSTOMER
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "hello");
                goToCustomerHomePage();
            }
        });
    }

    private void goToCustomerHomePage(){
        Intent intent = new Intent(this, CustomerHomePage.class);
        startActivity(intent);
        finish();
    }

}