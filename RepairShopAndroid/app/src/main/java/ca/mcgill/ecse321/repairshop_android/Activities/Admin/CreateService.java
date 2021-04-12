package ca.mcgill.ecse321.repairshop_android.Activities.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import ca.mcgill.ecse321.repairshop_android.Activities.Utility.HttpUtils;
import ca.mcgill.ecse321.repairshop_android.R;
import cz.msebera.android.httpclient.Header;

public class CreateService extends AppCompatActivity {

    private String error = null;
    private String successMessage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service);

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

    private void refreshSuccessMessage() {
        TextView tvSuccess = (TextView) findViewById(R.id.successNotification);
        tvSuccess.setText(successMessage);

        if (successMessage == null || successMessage.length() == 0) {
            tvSuccess.setVisibility(View.GONE);
        } else {
            tvSuccess.setVisibility(View.VISIBLE);
        }
    }

    public void createService(View v){

        error="";
        final TextView tv_service_name = (TextView) findViewById(R.id.newServiceName);
        final TextView tv_service_cost = (TextView) findViewById(R.id.newServiceCost);
        final TextView tv_service_duration = (TextView) findViewById(R.id.newServiceDuration);

        RequestParams requestParams = new RequestParams();
        requestParams.add("newServiceName", tv_service_name.getText().toString());
        requestParams.add("newServiceCost",tv_service_cost.getText().toString());
        requestParams.add("newServiceDuration",tv_service_duration.getText().toString());

        HttpUtils.post("person/customer/login",requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                tv_service_name.setText("");
                tv_service_cost.setText("");
                tv_service_duration.setText("");
                refreshSuccessMessage();
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