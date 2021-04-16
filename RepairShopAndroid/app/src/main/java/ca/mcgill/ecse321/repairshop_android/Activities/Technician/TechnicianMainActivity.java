package ca.mcgill.ecse321.repairshop_android.Activities.Technician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.repairshop_android.Activities.Customer.ProfileFragment;
import ca.mcgill.ecse321.repairshop_android.Activities.MainActivity;
import ca.mcgill.ecse321.repairshop_android.Activities.Utility.HttpUtils;
import ca.mcgill.ecse321.repairshop_android.Activities.Utility.RepairShopUtil;
import ca.mcgill.ecse321.repairshop_android.R;
import cz.msebera.android.httpclient.Header;

public class TechnicianMainActivity extends AppCompatActivity {
    public static BottomNavigationView bottomNavigationView;
    public final FragmentManager fragmentManager = getSupportFragmentManager();
    private static List<AppointmentDto> allAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician_home_page);
        setViews();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                String title = "";
                switch (item.getItemId()) {
                    case R.id.action_technician_home:
                        fragment = new TechnicianHomeFragment();
                        title = "Home";
                        break;
                    case R.id.action_technician_profile:
                        fragment = new ProfileFragment();
                        title = "My Account";
                        break;
                    default:
                        fragment = new TechnicianHomeFragment();
                        title = "Home";
                        break;
                }
                if (fragment != null) {
                    getSupportActionBar().setTitle(title);
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.action_technician_home);
    }

    private void setViews(){
        bottomNavigationView = findViewById(R.id.bottomNavigation);
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dark_button, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle dark button activities
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


    public static List<AppointmentDto> getAllAppointments(){
        return allAppointments;
    }

    private void queryAppointments(){
        RequestParams requestParams = new RequestParams();
        requestParams.add("email", RepairShopUtil.getLoginUserEmail() );

        HttpUtils.get("appointmentOfTechnician", requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    allAppointments = fromJsonArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(TechnicianMainActivity.this,"failed to retrieve any appointment for you",Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    /**
     * encapsulate all info for an appointment
     */
    public static class AppointmentDto{
        private List<String> services;
        private String customer;
        //private TimeSlotDto timeslot;

        public List<String> getServices() {
            return services;
        }

        public void setServices(List<String> services) {
            this.services = services;
        }

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }




    }

    public static List<AppointmentDto> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<AppointmentDto> appointments = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            List<String> services = new ArrayList<>();

            AppointmentDto appointmentDto = new AppointmentDto();

            JSONObject jsonObject = jsonArray.getJSONObject(i);

            // ADD THE CUSTOMER NAME
            appointmentDto.setCustomer(jsonObject.getJSONObject("customer").getString("email"));

            JSONArray servicesArray = jsonObject.getJSONArray("services");
            // ADD ALL SERVICES
            for (int j=0; j< servicesArray.length(); j++){

                services.add(servicesArray.getJSONObject(i).getString("name"));

          }

            appointments.add(appointmentDto);
        }
        return appointments;
    }
}