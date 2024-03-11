package com.example.admindeskboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.example.admindeskboard.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity.this, binding.drawerLayout, binding.toolBar, R.string.drawer_open, R.string.drawer_close
        );
        binding.drawerLayout.addDrawerListener(toggle);

        Fragment fragment = new First_Fragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment).commit();
        binding.navigationView.setCheckedItem(R.id.firstCulum);
        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemPossition = item.getItemId();

                if (itemPossition == R.id.firstCulum) {
                    Fragment fragment = new First_Fragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment).addToBackStack(null).commit();
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                } else if (itemPossition==R.id.secondCulum) {
                    Fragment fragment = new Second_Fragment();
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.frameLayout,fragment).addToBackStack(null).commit();
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    binding.navigationView.setCheckedItem(R.id.secondCulum);
                }else {
                    Fragment fragment = new Thired_Fragment();
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.frameLayout,fragment).addToBackStack(null).commit();
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    binding.navigationView.setCheckedItem(R.id.thirdCulum);
                }

                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}