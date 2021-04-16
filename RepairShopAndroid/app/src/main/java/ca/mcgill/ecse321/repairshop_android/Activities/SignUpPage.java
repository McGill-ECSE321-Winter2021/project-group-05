package ca.mcgill.ecse321.repairshop_android.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import ca.mcgill.ecse321.repairshop_android.Activities.Customer.CustomerMainActivity;
import ca.mcgill.ecse321.repairshop_android.Activities.Utility.HttpUtils;
import ca.mcgill.ecse321.repairshop_android.Activities.Utility.RepairShopUtil;
import ca.mcgill.ecse321.repairshop_android.R;
import cz.msebera.android.httpclient.Header;

public class SignUpPage extends AppCompatActivity {
    private String error= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
    }

    public void signup(View view){
        final TextView tv_email = (TextView) findViewById(R.id.email);
        final TextView tv_password = (TextView) findViewById(R.id.password);
        final TextView tv_username = (TextView) findViewById(R.id.username);

        String email = tv_email.getText().toString();
        String username = tv_username.getText().toString();
        String password = tv_password.getText().toString();

        RequestParams requestParams = new RequestParams();
        requestParams.add("email", email);
        requestParams.add("password",password);
        requestParams.add("username",username);

        HttpUtils.post("person/customer/register/app",requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                setCurrentCustomer(tv_email.getText().toString());
                tv_email.setText("");
                tv_username.setText("");
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
                //RepairShopUtil.refreshErrorMessage(view, R.id.error, error);
            }
            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  String responseString,
                                  Throwable throwable){
                Toast.makeText
                        (SignUpPage.this, responseString, Toast.LENGTH_SHORT)
                        .show();
            }

        });
    }

    // UPDATE THE MY ACCOUNT DEFAULT INFORMATION
    private void setCurrentCustomer(final String email){

        HttpUtils.get("person/customer/"+ email,new RequestParams(),  new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //refreshErrorMessage();
                try {
                    RepairShopUtil.setCurrentUser(response.getString("username"),email,"customer");
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
                //refreshErrorMessage();
            }
            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  String responseString,
                                  Throwable throwable){
                Toast.makeText
                        (SignUpPage.this, responseString, Toast.LENGTH_SHORT)
                        .show();
            }

        });
    }

    //NAVIGATES CUSTOMER TO RIGHT SCREEN
    private void goToCustomerHomePage(){
        Intent intent = new Intent(this, CustomerMainActivity.class);
        startActivity(intent);
        finish();
    }
}