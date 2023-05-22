package com.demo.firebasehw;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.demo.firebasehw.adapters.UsersAdapter;
import com.demo.firebasehw.pojo.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<User> users;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private UsersAdapter adapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);
        users = new ArrayList<>();
        adapter = new UsersAdapter();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        loadData();
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddUserActivity.class);
            startActivity(intent);
        });

        adapter.setOnUserClickListener(position -> {
            Toast.makeText(this, "Позиция №" + position, Toast.LENGTH_SHORT).show();
        });
    }

    private void clearList() {
        users.clear();
    }

    private void loadData() {
        db.collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        clearList();
                        if (value != null) {
                            for (QueryDocumentSnapshot documentSnapshot : value) {
                                User user = documentSnapshot.toObject(User.class);
                                users.add(user);
                            }
                            adapter.setUsers(users);
                        }
                    }
                });
    }
}