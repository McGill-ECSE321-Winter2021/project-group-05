package ca.mcgill.ecse321.repairshop_android.Activities.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ca.mcgill.ecse321.repairshop_android.Activities.MainActivity;
import ca.mcgill.ecse321.repairshop_android.Activities.Utility.RepairShopUtil;
import ca.mcgill.ecse321.repairshop_android.R;

public class CustomerMainActivity extends AppCompatActivity {
    public static BottomNavigationView bottomNavigationView;
    public final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home_page);
        setViews();
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
        getMenuInflater().inflate(R.menu.logout, menu);
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

        if (id == R.id.logout) {
            RepairShopUtil.setCurrentUser("", "", "");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}