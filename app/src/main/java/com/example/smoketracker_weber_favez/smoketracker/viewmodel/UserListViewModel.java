package com.example.smoketracker_weber_favez.smoketracker.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.User;
import com.example.smoketracker_weber_favez.smoketracker.db.db.repository.UserRepository;

import java.util.List;

public class UserListViewModel extends AndroidViewModel {

    private UserRepository repository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<User>> observableClients;

    public UserListViewModel(@NonNull Application application, UserRepository userRepository) {
        super(application);

        repository = userRepository;

        applicationContext = application.getApplicationContext();

        observableClients = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableClients.setValue(null);

        LiveData<List<User>> clients = repository.getAllUsers(applicationContext);

        // observe the changes of the entities from the database and forward them
        observableClients.addSource(clients, observableClients::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final UserRepository clientRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            clientRepository = UserRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new UserListViewModel(application, clientRepository);
        }
    }

    /**
     * Expose the LiveData UserEntities query so the UI can observe it.
     */
    public LiveData<List<User>> getAllUsers() {
        return observableClients;
    }
}

