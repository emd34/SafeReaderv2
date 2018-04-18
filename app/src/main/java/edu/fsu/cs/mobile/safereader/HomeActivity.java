package edu.fsu.cs.mobile.safereader;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
/*
    private List<Book> bookList;
    private RVAdapter theAdapter;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null)
                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }
        };

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );
/*
        RecyclerView rv = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        bookList = new ArrayList<>();
        theAdapter = new RVAdapter(this, bookList);

        rv.setAdapter(theAdapter);
        loadBooks();
*/
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Only handles the logout function for now, logs user out of google and returns them to login screen
                        int id = menuItem.getItemId();
                        if (id == R.id.nav_logout){
                            mAuth.signOut();
                            startActivity(new Intent(HomeActivity.this, MainActivity.class));
                        }

                        return true;
                    }
                });
    }
/*
    private void loadBooks(){
        Book testbook = new Book("book", 1, "auth", "pubdate");
        bookList.add(testbook);

        testbook = new Book("book", 1, "auth", "pubdate");
        bookList.add(testbook);

        testbook = new Book("book", 1, "auth", "pubdate");
        bookList.add(testbook);

        testbook = new Book("book", 1, "auth", "pubdate");
        bookList.add(testbook);

        theAdapter.notifyDataSetChanged();
    }
*/
}
