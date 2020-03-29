package com.example.smoketracker_weber_favez.smoketracker.viewmodel.User;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.smoketracker_weber_favez.smoketracker.db.db.AppDatabase;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.UserEntity;
import com.example.smoketracker_weber_favez.smoketracker.db.db.repository.UserRepository;
import com.example.smoketracker_weber_favez.smoketracker.util.OnAsyncEventListener;


public class UserViewModel extends AndroidViewModel {

    private UserRepository repository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<UserEntity> observableClient;

    public UserViewModel(@NonNull Application application,
                         final String email, UserRepository userRepository) {
        super(application);

        repository = userRepository;

        applicationContext = application.getApplicationContext();

        observableClient = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableClient.setValue(null);

        LiveData<UserEntity> user = repository.getUser(email, applicationContext);

        //observe the changes of the client entity from the database and forward them
        observableClient.addSource(user, observableClient::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String email;

        private final UserRepository repository;

        public Factory(@NonNull Application application, String userEmail) {
            this.application = application;
            this.email = userEmail;
            repository = UserRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new UserViewModel(application, email, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<UserEntity> getUser() {
        return observableClient;
    }


    public int getIdUserByEmail(String email, Context context) {
        return repository.getIdUserByEmail(email, context);
    }

    public void createUser(UserEntity user, OnAsyncEventListener callback) {
        repository.insert(user, callback, applicationContext);
    }

    public void updateUser(UserEntity user, OnAsyncEventListener callback) {
        repository.update(user, callback, applicationContext);
    }

    public void deleteUser(UserEntity user, OnAsyncEventListener callback) {
        repository.delete(user, callback, applicationContext);
    }
}


