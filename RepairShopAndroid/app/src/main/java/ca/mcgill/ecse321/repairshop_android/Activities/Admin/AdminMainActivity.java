package ca.mcgill.ecse321.repairshop_android.Activities.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ca.mcgill.ecse321.repairshop_android.Activities.Customer.ProfileFragment;
import ca.mcgill.ecse321.repairshop_android.R;

public class AdminMainActivity extends AppCompatActivity {
    public static BottomNavigationView bottomNavigationView;
    public final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);
        setViews();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                String title = "";
                switch (item.getItemId()) {
                    case R.id.action_admin_home:
                        fragment = new AdminHomeFragment();
                        title = "Home";
                        break;
                    case R.id.action_admin_profile:
                        fragment = new ProfileFragment();
                        title = "My Account";
                        break;
                    case R.id.action_admin_service:
                        fragment = new ServiceFragment();
                        title = "Create Service";
                        break;
                    default:
                        fragment = new AdminHomeFragment();
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
            bottomNavigationView.setSelectedItemId(R.id.action_admin_home);
    }

    private void setViews(){
        bottomNavigationView = findViewById(R.id.bottomNavigation);
    }
}