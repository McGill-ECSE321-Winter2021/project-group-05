package ca.mcgill.ecse321.repairshop_android.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import ca.mcgill.ecse321.repairshop_android.Activities.Admin.AdminMainActivity;
import ca.mcgill.ecse321.repairshop_android.Activities.Customer.CustomerMainActivity;
import ca.mcgill.ecse321.repairshop_android.Activities.Technician.TechnicianMainActivity;
import ca.mcgill.ecse321.repairshop_android.Activities.Utility.HttpUtils;
import ca.mcgill.ecse321.repairshop_android.Activities.Utility.RepairShopUtil;
import ca.mcgill.ecse321.repairshop_android.Model.User;
import ca.mcgill.ecse321.repairshop_android.R;
import cz.msebera.android.httpclient.Header;


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

    /**
     * Logs a user into his/her respective account
     * @param v
     */
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
            HttpUtils.post("person/customer/login/app",requestParams, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    refreshErrorMessage();
                    setCurrentCustomer(tv_email.getText().toString());
                    goToCustomerHomePage();
                    tv_email.setText("");
                    tv_password.setText("");
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    try {
                        tv_password.setText("");
                        error += errorResponse.get("message").toString();
                    } catch (JSONException e) {
                        error += e.getMessage();
                    }

                    Toast.makeText
                            (MainActivity.this, "Login failed: Please check the password", Toast.LENGTH_SHORT)
                            .show();
                }
                @Override
                public void onFailure(int statusCode,
                                      Header[] headers,
                                      String responseString,
                                      Throwable throwable){
                    Toast.makeText
                            (MainActivity.this, responseString, Toast.LENGTH_SHORT)
                            .show();
                }

            });
        }
        // log in as admin
        else if (adminCheckbox.isChecked()){
            Log.d("tag1","is admin!");
            HttpUtils.post("person/administrator/login/app", requestParams, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("tag2","success");
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

                    Toast.makeText
                            (MainActivity.this, "Login failed: Please check the password", Toast.LENGTH_SHORT)
                            .show();
                }
                @Override
                public void onFailure(int statusCode,
                                      Header[] headers,
                                      String responseString,
                                      Throwable throwable){
                    Toast.makeText
                            (MainActivity.this, responseString, Toast.LENGTH_SHORT)
                            .show();
                }

            });
        }
        // log in as tech
        else if (techCheckbox.isChecked()){

            HttpUtils.post("person/technician/login/app",requestParams, new JsonHttpResponseHandler() {
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

                    Toast.makeText
                            (MainActivity.this, "Login failed: Please check the password", Toast.LENGTH_SHORT)
                            .show();
                }
                @Override
                public void onFailure(int statusCode,
                                      Header[] headers,
                                      String responseString,
                                      Throwable throwable){
                    Toast.makeText
                            (MainActivity.this, responseString, Toast.LENGTH_SHORT)
                            .show();
                }

            });
        }
        else{

            Toast.makeText
                    (MainActivity.this, "Please select a role", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void signup(View v){
        goToSignUpPage();
    }

    //NAVIGATES CUSTOMER TO RIGHT SCREEN
    private void goToCustomerHomePage(){
        TextView email = findViewById(R.id.email);
        TextView password = findViewById(R.id.password);
        User user = new User(email.getText().toString(), password.getText().toString());
        Intent intent = new Intent(this, CustomerMainActivity.class);
        intent.putExtra("user", Parcels.wrap(user));
        startActivity(intent);
        finish();
    }


    //NAVIGATES ADMIN TO RIGHT SCREEN
    private void goToAdminHomePage(){
        Intent intent = new Intent(this, AdminMainActivity.class);
        startActivity(intent);
        finish();
    }

    //NAVIGATES TECHNICIAN TO RIGHT SCREEN
    private void goToTechnicianHomePage(){
        Intent intent = new Intent(this, TechnicianMainActivity.class);
        startActivity(intent);
        finish();
    }

    //NAVIGATES NEW USERS TO SIGN UP PAGE
    private void goToSignUpPage(){
        Intent intent = new Intent(this, AdminMainActivity.class);
        startActivity(intent);
    }

    // UPDATE THE MY ACCOUNT DEFAULT INFORMATION
    private void setCurrentCustomer(final String email){

         HttpUtils.get("person/customer/"+ email,new RequestParams(),  new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                try {
                    RepairShopUtil.setCurrentUser(response.getString("username"),email,"customer");
                } catch (Exception e) {
                    Toast.makeText
                            (MainActivity.this, "failed to set current customer:\n"+e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    Toast.makeText
                            (MainActivity.this, "failed to set current customer:\n"+errorResponse.get("message").toString(), Toast.LENGTH_SHORT)
                            .show();
                } catch (JSONException e) {
                    Toast.makeText
                            (MainActivity.this, "failed to set current customer:\n"+e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }

            }
             @Override
             public void onFailure(int statusCode,
                                   Header[] headers,
                                   String responseString,
                                   Throwable throwable){
                 Toast.makeText
                         (MainActivity.this, responseString, Toast.LENGTH_SHORT)
                         .show();
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
                    RepairShopUtil.setCurrentUser(response.getString("username"),email, "admin");
                } catch (Exception e) {
                    Toast.makeText
                            (MainActivity.this, "failed to set current customer:\n"+e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    Toast.makeText
                            (MainActivity.this, "failed to set current customer:\n"+errorResponse.get("message").toString(), Toast.LENGTH_SHORT)
                            .show();
                } catch (JSONException e) {
                    Toast.makeText
                            (MainActivity.this, "failed to set current customer:\n"+e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }


            }
            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  String responseString,
                                  Throwable throwable){
                Toast.makeText
                        (MainActivity.this, responseString, Toast.LENGTH_SHORT)
                        .show();
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
                    RepairShopUtil.setCurrentUser(response.getString("username"),email,"technician");
                } catch (Exception e) {
                    Toast.makeText
                            (MainActivity.this, "failed to set current customer:\n"+e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    Toast.makeText
                            (MainActivity.this, "failed to set current customer:\n" + errorResponse.get("message").toString(), Toast.LENGTH_SHORT)
                            .show();
                } catch (JSONException e) {
                    Toast.makeText
                            (MainActivity.this, "failed to set current customer:\n" + e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }
            }
            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  String responseString,
                                  Throwable throwable){
                Toast.makeText
                        (MainActivity.this, responseString, Toast.LENGTH_SHORT)
                        .show();
            }

        });
    }

    /**
     * creates an action bar button
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dark_button, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * handles dark mode button activities
     * @param item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.dark_button) {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * black the '<' key
     */
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Toast.makeText(MainActivity.this,"please sign in first",Toast.LENGTH_LONG).show();
        return;
    }
}