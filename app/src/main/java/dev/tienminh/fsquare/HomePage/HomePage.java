package dev.tienminh.fsquare.HomePage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dev.tienminh.fsquare.R;
import dev.tienminh.fsquare.Setting.Settings;
import dev.tienminh.fsquare.Start.Login;
import dev.tienminh.fsquare.TabManager.Tab_Home;
import dev.tienminh.fsquare.TabManager.Tab_Message;
import dev.tienminh.fsquare.TabManager.Tab_Profile;
import dev.tienminh.fsquare.TabManager.Tab_Search;
import dev.tienminh.fsquare.TabManager.ViewPagerAdapter;


public class HomePage extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager vp_home;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    Toolbar toolbar;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        fragmentManager = getSupportFragmentManager();
        tabLayout = (TabLayout) findViewById(R.id.tab_msgLayout);
        vp_home = (ViewPager) findViewById(R.id.vp_home);
        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordibator);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));

        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                }else {
                    String name = user.getDisplayName();
                    String email = user.getEmail();
                    Uri photoUrl = user.getPhotoUrl();
                    String uid = user.getUid();
                }
            }
        };

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new Tab_Home(), "Home");
        adapter.AddFragment(new Tab_Search(), "Search");
        adapter.AddFragment(new Tab_Message(), "Message");
        adapter.AddFragment(new Tab_Profile(), "Profile");
        vp_home.setAdapter(adapter);
        tabLayout.setupWithViewPager(vp_home);

        tabLayout.getTabAt(0).setIcon(R.drawable.tab_home_selected);
        tabLayout.getTabAt(1).setIcon(R.drawable.tab_search_selected);
        tabLayout.getTabAt(2).setIcon(R.drawable.tab_chat_selected);
        tabLayout.getTabAt(3).setIcon(R.drawable.tab_person_selected);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.toolbar));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),HomePage.class));
//                        FragmentTransaction transaction = fragmentManager.beginTransaction();
//                        Tab_Home tab_home = new Tab_Home();
//                        transaction.replace(R.id.tab_msgLayout,tab_home);
//                        transaction.commit();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.chat:
//                        FragmentTransaction transaction1 = fragmentManager.beginTransaction();
//                        Tab_Message tab_message = new Tab_Message();
//                        transaction1.replace(R.id.tab_msgLayout,tab_message);
//                        transaction1.commit();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.friends :
                        Snackbar sbfriend =  Snackbar.make(coordinatorLayout,"You have no friend to display!!!",Snackbar.LENGTH_LONG);
                        sbfriend.show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.income :
                        Snackbar sbfriendrq =  Snackbar.make(coordinatorLayout,"You have no friend request now !!!",Snackbar.LENGTH_LONG);
                        sbfriendrq.show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.outgo :
                        Snackbar sbfriendoutgo =  Snackbar.make(coordinatorLayout,"You are not sent any request before !!!",Snackbar.LENGTH_LONG);
                        sbfriendoutgo.show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), Settings.class));
                        break;
                    case R.id.about:
                        Toast.makeText(getApplicationContext(), "about", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout:
                        signOut();


                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        ImageView iv_profile = (ImageView) header.findViewById(R.id.iv_profile);
        if (user.getPhotoUrl()!=null){
            Glide.with(getApplicationContext()).load(user.getPhotoUrl()).into(iv_profile);
           // Picasso.with(getApplicationContext()).load(user.getPhotoUrl()).into(iv_profile);
        }else {
            iv_profile.setImageResource(R.drawable.ic_person_white_48dp);
        }

        TextView tv_email = (TextView) header.findViewById(R.id.tv_email);
        tv_email.setText(user.getEmail());


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();



    }

    public void signOut() {
        auth.signOut();
        AuthUI.getInstance().signOut(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
