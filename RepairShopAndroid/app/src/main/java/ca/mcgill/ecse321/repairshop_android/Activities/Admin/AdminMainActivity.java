package ca.mcgill.ecse321.repairshop_android.Activities.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import ca.mcgill.ecse321.repairshop_android.Activities.Customer.ProfileFragment;
import ca.mcgill.ecse321.repairshop_android.R;

/*
Admin main activity 
*/
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
            bottomNavigationView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
    }

    private void setViews(){
        bottomNavigationView = findViewById(R.id.bottomNavigation);
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
}