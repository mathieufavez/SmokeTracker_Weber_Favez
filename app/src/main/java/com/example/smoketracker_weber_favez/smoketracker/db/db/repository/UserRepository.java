package com.example.smoketracker_weber_favez.smoketracker.db.db.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.UserEntity;
import com.example.smoketracker_weber_favez.smoketracker.db.db.firebase.UserListLiveData;
import com.example.smoketracker_weber_favez.smoketracker.db.db.firebase.UserLiveData;
import com.example.smoketracker_weber_favez.smoketracker.util.OnAsyncEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UserRepository{

    private static final String TAG = "UserRepository";

    private static UserRepository instance;

    private UserRepository(){

    }
    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<UserEntity> getUser(final String id){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(id);
        return new UserLiveData(reference);
    }


    /*public void signIn(final String email, final String password,
                       final OnCompleteListener<AuthResult> listener) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);
    }*/

    public LiveData<List<UserEntity>> getAllUsers() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("users");
        return new UserListLiveData(reference);
    }

   /* public void register(final UserEntity user, final OnAsyncEventListener callback) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                user.getUser_email(),
                user.getUser_password()
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                user.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                insert(user, callback);
            } else {
                callback.onFailure(task.getException());
            }
        });
    }*/

    /*public int getIdUserByEmail(String email, Context context) {
        return AppDatabase.getInstance(context).userDao().getIdUserByEmail(email);
    }*/

    public String insert(final UserEntity user, final OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("users").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(id)
                .setValue(user, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
        return id;
    }

    public void update(final UserEntity user, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(user.getId())
                .updateChildren(user.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final UserEntity user, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(user.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }
}
