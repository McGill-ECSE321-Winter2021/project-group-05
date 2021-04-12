package ca.mcgill.ecse321.repairshop_android.Activities.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.RequestParams;

import ca.mcgill.ecse321.repairshop_android.R;

public class CreateService extends AppCompatActivity {

    private String error = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service);

    }

    public void create(View v){

        error="";
        final TextView tv_service_name = (TextView) findViewById(R.id.newServiceName);
        final TextView tv_service_cost = (TextView) findViewById(R.id.newServiceCost);
        final TextView tv_service_duration = (TextView) findViewById(R.id.newServiceDuration);

        RequestParams requestParams = new RequestParams();
        requestParams.add("newServiceName", tv_service_name.getText().toString());
        requestParams.add("newServiceCost",tv_service_cost.getText().toString());
        requestParams.add("newServiceDuration",tv_service_duration.getText().toString());
    }
}