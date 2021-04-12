package ca.mcgill.ecse321.repairshop_android.Activities.Technician;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ca.mcgill.ecse321.repairshop_android.Activities.Customer.ProfileFragment;
import ca.mcgill.ecse321.repairshop_android.R;

public class TechnicianMainActivity extends AppCompatActivity {
    public static BottomNavigationView bottomNavigationView;
    public final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician_home_page);
        setViews();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_technician_home:
                        fragment = new TechnicianHomeFragment();
                        break;
                    case R.id.action_technician_profile:
                        fragment = new ProfileFragment();
                        break;
                    default:
                        fragment = new TechnicianHomeFragment();
                        break;
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
}