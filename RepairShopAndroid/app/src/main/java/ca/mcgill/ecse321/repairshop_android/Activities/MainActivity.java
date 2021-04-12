package ca.mcgill.ecse321.repairshop_android.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import ca.mcgill.ecse321.repairshop_android.Activities.Admin.AdminHomePage;
import ca.mcgill.ecse321.repairshop_android.Activities.Admin.EditDeleteService;
import ca.mcgill.ecse321.repairshop_android.Activities.Customer.CustomerHomePage;
import ca.mcgill.ecse321.repairshop_android.Activities.Technician.TechnicianHomePage;
import ca.mcgill.ecse321.repairshop_android.Activities.Utility.HttpUtils;
import ca.mcgill.ecse321.repairshop_android.Activities.Utility.RepairShopUtil;
import ca.mcgill.ecse321.repairshop_android.R;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.util.EntityUtils;


public class MainActivity extends AppCompatActivity {

    private String error = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshErrorMessage();
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

    public void login(View v){

        error="";
        final TextView tv_email = (TextView) findViewById(R.id.email);
        final TextView tv_password = (TextView) findViewById(R.id.password);

        RadioButton customerCheckBox = (RadioButton) findViewById(R.id.customerCheckBox);
        RadioButton adminCheckbox = (RadioButton) findViewById(R.id.adminCheckBox);
        RadioButton techCheckbox = (RadioButton) findViewById(R.id.techCheckBox);

        RequestParams requestParams = new RequestParams();
        requestParams.add("email", tv_email.getText().toString());
        requestParams.add("password",tv_password.getText().toString());

        // log in as customer
        if (customerCheckBox.isChecked()){

            HttpUtils.post("person/customer/login",requestParams, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    refreshErrorMessage();
                    setCurrentCustomer(tv_email.getText().toString());
                    tv_email.setText("");
                    tv_password.setText("");
                    goToCustomerHomePage();

                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    try {
                        tv_password.setText("");
                        error += errorResponse.get("message").toString();
                    } catch (JSONException e) {
                        error += e.getMessage();
                    }
                    refreshErrorMessage();
                }

            });
        }
        // log in as admin
        else if (adminCheckbox.isChecked()){
            HttpUtils.post("person/administrator/login", requestParams, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    setCurrentAdmin(tv_email.getText().toString());
                    refreshErrorMessage();
                    tv_email.setText("");
                    tv_password.setText("");
                    goToAdminHomePage();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    try {

                        tv_password.setText("");
                        error += errorResponse.get("message").toString();
                    } catch (JSONException e) {
                        error += e.getMessage();
                    }
                    refreshErrorMessage();

                }

            });
        }
        // log in as tech
        else if (techCheckbox.isChecked()){
            HttpUtils.post("person/technician/login",requestParams, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    setCurrentTechnician(tv_email.getText().toString());
                    refreshErrorMessage();
                    tv_email.setText("");
                    tv_password.setText("");
                    goToTechnicianHomePage();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    try {
                        tv_password.setText("");
                        error += errorResponse.get("message").toString();
                    } catch (JSONException e) {
                        error += e.getMessage();
                    }
                    refreshErrorMessage();
                }

            });
        }
        else{
            error+="You need to select the role!";
            refreshErrorMessage();
        }
    }

    public void signup(View v){
        goToSignUpPage();
    }

    //NAVIGATES CUSTOMER TO RIGHT SCREEN
    private void goToCustomerHomePage(){
        Intent intent = new Intent(this, CustomerHomePage.class);
        startActivity(intent);
        finish();
    }


    //NAVIGATES ADMIN TO RIGHT SCREEN
    private void goToAdminHomePage(){
        Intent intent = new Intent(this, AdminHomePage.class);
        startActivity(intent);
        finish();
    }


    //NAVIGATES TECHNICIAN TO RIGHT SCREEN
    private void goToTechnicianHomePage(){
        Intent intent = new Intent(this, TechnicianHomePage.class);
        startActivity(intent);
        finish();
    }

    //NAVIGATES NEW USERS TO SIGN UP PAGE
    private void goToSignUpPage(){
        Intent intent = new Intent(this, EditDeleteService.class);
        startActivity(intent);
    }

    // UPDATE THE MY ACCOUNT DEFAULT INFORMATION
    private void setCurrentCustomer(final String email){

         HttpUtils.get("person/customer/"+ email,new RequestParams(),  new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                try {
                    RepairShopUtil.setCurrentUser(response.getString("username"),email);
                } catch (Exception e) {
                    error += e.getMessage();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {

                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }

        });
    }

    // UPDATE THE MY ACCOUNT DEFAULT INFORMATION
    private void setCurrentAdmin(final String email) {
        HttpUtils.get("person/administrator/"+ email,new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                try {
                    RepairShopUtil.setCurrentUser(response.getString("username"),email);
                } catch (Exception e) {
                    error += e.getMessage();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {

                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }

        });
    }

    // UPDATE THE MY ACCOUNT DEFAULT INFORMATION
    private void setCurrentTechnician(final String email) {
        HttpUtils.get("person/technician/"+ email,new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                try {
                    RepairShopUtil.setCurrentUser(response.getString("username"),email);
                } catch (Exception e) {
                    error += e.getMessage();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {

                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }

        });
    }
}