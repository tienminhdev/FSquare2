package dev.tienminh.fsquare.MyTimeLine;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Random;

import dev.tienminh.fsquare.R;


public class MyTimeLine extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private ImageView iv_cover,iv_avatar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TabPagerAdapter tabPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    FirebaseAuth   auth;
    DrawerLayout   drawerLayout;
    NavigationView nav;
    ImageButton    ivbtnRight;
    int RESULT_LOAD_IMAGE = 1;
    int CAMERA_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_time_line);

        auth = FirebaseAuth.getInstance();

        iv_cover = (ImageView) findViewById(R.id.iv_cover);
        iv_avatar= (ImageView) findViewById(R.id.iv_avatar);
        toolbar  = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            Glide.with(getApplicationContext()).load(user.getPhotoUrl()).into(iv_avatar);
            actionBar.setTitle(user.getDisplayName());
            Log.d("AAA",""+user.getDisplayName());
        }else {
            actionBar.setTitle("");
            actionBar.setLogo(R.drawable.ic_person_black_36dp);
        }

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.detail_tabs);
        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        tabPagerAdapter.AddFragment(new SampleFragment(),"About");
        tabPagerAdapter.AddFragment(new Card_layout2(),"Photos");
        mViewPager.setAdapter(tabPagerAdapter);
        mTabLayout.setTabsFromPagerAdapter(tabPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
       // collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));

        dynamicToolbarColor();
        toolbarTextAppernce();
        toolbarLogoExpand();
        initnav();
        ivbtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(GravityCompat.END)){
                    drawerLayout.closeDrawer(GravityCompat.END);
                }else {
                    drawerLayout.openDrawer(GravityCompat.END);
                }
            }
        });
    }
    private void dynamicToolbarColor() {

        TypedArray typedArray = getResources().obtainTypedArray(R.array.cover);
        Random random = new Random();
        int iRD = random.nextInt(typedArray.length());
        iv_cover.setImageResource(typedArray.getResourceId(iRD,-1));

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
               typedArray.getResourceId(iRD,-1));
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(R.attr.colorPrimary));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(R.attr.colorPrimaryDark));
            }
        });
    }
      private void initnav() {
                 nav = (NavigationView) findViewById(R.id.navigation_view);
                 nav.setNavigationItemSelectedListener(this);
         drawerLayout= (DrawerLayout) findViewById(R.id.drawer);
           ivbtnRight= (ImageButton) findViewById(R.id.menuRight);
    }

            private void toolbarTextAppernce() {
                collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
                collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
                collapsingToolbarLayout.setExpandedTitleMargin(350, 0, 0, 130);
            }

            private void toolbarLogoExpand() {
                collapsingToolbarLayout.childDrawableStateChanged(iv_avatar);
                // collapsingToolbarLayout.childHasTransientStateChanged(iv_avatar,true);
            }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.info :
                info();
                Toast.makeText(getApplicationContext(),""+item.getTitle(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.avatar : selectimage();
                break;
            case R.id.cover: selectimage();
                break;
        }
       // drawerLayout.closeDrawer(GravityCompat.START);
        drawerLayout.closeDrawer(GravityCompat.END);
        return true;
    }
    private void info(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.info);
        builder.setPositiveButton("Change info", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setNegativeButton("Close",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void selectimage(){
        final CharSequence[] items = {"Take Photo","Choose from Gallery","Cancel"};
        final AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Add Photo");
        adb.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                if (items[position].equals("Take Photo")){

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,CAMERA_REQUEST);
                }else if (items[position].equals("Choose from Gallery")){
                    Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 8888);
                }else if (items[position].equals("Cancel")){
                    dialogInterface.dismiss();
                }

            }
        });
        adb.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            iv_cover.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            iv_cover.setImageBitmap(photo);
        }
    }


    class TabPagerAdapter extends FragmentStatePagerAdapter {

                ArrayList<Fragment> mFragmentList = new ArrayList<>();
                ArrayList<String> mTitleList = new ArrayList<>();

                public TabPagerAdapter(FragmentManager fm) {
                    super(fm);
                }

                @Override
                public Fragment getItem(int position) {

                    return mFragmentList.get(position);
                }

                @Override
                public int getCount() {
                    return mTitleList.size();
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    return mTitleList.get(position);
                }

                public void AddFragment(Fragment fragment, String title) {
                    mFragmentList.add(fragment);
                    mTitleList.add(title);
                }
            }
}