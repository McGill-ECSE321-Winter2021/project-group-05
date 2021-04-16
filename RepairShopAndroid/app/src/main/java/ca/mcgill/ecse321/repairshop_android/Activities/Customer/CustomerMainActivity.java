package ca.mcgill.ecse321.repairshop_android.Activities.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.JsonHttpResponseHandler;

import ca.mcgill.ecse321.repairshop_android.Activities.MainActivity;
import ca.mcgill.ecse321.repairshop_android.Activities.Utility.RepairShopUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ca.mcgill.ecse321.repairshop_android.Activities.Utility.HttpUtils;
import ca.mcgill.ecse321.repairshop_android.Activities.Utility.RepairShopUtil;
import ca.mcgill.ecse321.repairshop_android.Model.Appointment;
import ca.mcgill.ecse321.repairshop_android.Model.User;

import ca.mcgill.ecse321.repairshop_android.R;
import cz.msebera.android.httpclient.Header;

public class CustomerMainActivity extends AppCompatActivity {
    public static BottomNavigationView bottomNavigationView;
    public final FragmentManager fragmentManager = getSupportFragmentManager();
    private static List<String> allServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home_page);
        setViews();
        queryServices();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                String title = "";
                switch (item.getItemId()) {
                    case R.id.action_customer_home:
                        fragment = new CustomerHomeFragment();
                        title = "Home";
                        break;
                    case R.id.action_customer_profile:
                        fragment = new ProfileFragment();
                        title = "My Account";
                        break;
                    case R.id.action_customer_compose:
                        fragment = new BookAppointmentFragment();
                        title = "Book Appointment";
                        break;
                    default:
                        fragment = new CustomerHomeFragment();
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
        bottomNavigationView.setSelectedItemId(R.id.action_customer_home);
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

    /**
     * returns all the services fetch from the data base
     * @return
     */
    public static List<String> getAllServices(){
        return allServices;
    }

    /**
     * Fetches the all the services available in the system
     */
    private void queryServices(){
        HttpUtils.get("bookableServices", null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                try {
                    allServices = getServiceNames(response);
                    Log.d("app", "as");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  String responseString,
                                  Throwable throwable){
                Toast.makeText
                        (CustomerMainActivity.this, responseString, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    /**
     * formats all the services fetch from the data base
     * @param jsonArray
     * @return
     * @throws JSONException
     */
    public static List<String> getServiceNames(JSONArray jsonArray) throws JSONException {
        List<String> services = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            services.add(jsonObject.getString("name"));
        }
        return services;
    }

    /**
     * converts a string into a date
     * @param dateString
     * @return
     */
    public static Date convertToDate(String dateString){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateString);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


}
