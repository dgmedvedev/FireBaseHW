package com.demo.firebasehw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.firebasehw.pojo.User;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddUserActivity extends AppCompatActivity {

    private Button buttonSave;

    private EditText editTextLastName;
    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextSex;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        db = FirebaseFirestore.getInstance();
        buttonSave = findViewById(R.id.buttonSave);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextSex = findViewById(R.id.editTextSex);

        buttonSave.setOnClickListener(view -> {
            if (isFilled()) {
                addUser();
                Intent intent = new Intent(AddUserActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
            }
        });

        editTextSex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null && charSequence.length() > 1) {
                    editTextSex.setText(charSequence.toString().substring(0, 1));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void addUser() {
        String lastname = editTextLastName.getText().toString();
        String name = editTextName.getText().toString();
        int age = Integer.parseInt(editTextAge.getText().toString());
        String sex = editTextSex.getText().toString();
        User user = new User(name, lastname, age, sex);
        db.collection("users").add(user);
    }

    private boolean isFilled() {
        return !editTextLastName.getText().toString().isEmpty() &&
                !editTextName.getText().toString().isEmpty() &&
                !editTextAge.getText().toString().isEmpty() &&
                !editTextSex.getText().toString().isEmpty();
    }
}