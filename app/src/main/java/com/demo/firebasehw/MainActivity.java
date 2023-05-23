package com.demo.firebasehw;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.demo.firebasehw.adapters.UsersAdapter;
import com.demo.firebasehw.pojo.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private UsersAdapter adapter;
    private FirebaseFirestore db;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);
        adapter = new UsersAdapter();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        loadData();
        swipe();
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddUserActivity.class);
            startActivity(intent);
        });

        adapter.setOnUserClickListener(position -> {
            Toast.makeText(this, "Позиция №" + position, Toast.LENGTH_SHORT).show();
        });
    }

    private void loadData() {
        db.collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null) {
                            List<User> users = value.toObjects(User.class);
                            adapter.setUsers(users);
                        }
                    }
                });
    }

    private void swipe() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                db.collection("users").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot querySnapshots) {
                                for (int i = 0; i < querySnapshots.getDocuments().size(); i++) {
                                    if (i == viewHolder.getAdapterPosition()) {
                                        userId = querySnapshots.getDocuments().get(i).getId();
                                        db.collection("users").document(userId).delete();
                                        return;
                                    }
                                }
                            }
                        });
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}