package ca.mcgill.ecse321.repairshop_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.Scene;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

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

        /* for debugging
        String x = tv_email.getText().toString();
        String y = tv_password.getText().toString();

         */

        // log in as customer
        if (customerCheckBox.isChecked()){

            HttpUtils.post("person/customer/login",requestParams, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    refreshErrorMessage();
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
                    refreshErrorMessage();
                }

            });
        }
        // log in as admin
        else if (adminCheckbox.isChecked()){
            HttpUtils.post("person/administrator/login", requestParams, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    refreshErrorMessage();
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
                    refreshErrorMessage();

                }

            });
        }
        // log in as tech
        else if (techCheckbox.isChecked()){
            HttpUtils.post("person/technician/login",requestParams, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    refreshErrorMessage();
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
        error="";
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

        HttpUtils.post("person/customer/register/",requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
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
                refreshErrorMessage();
            }

        });


    }
}