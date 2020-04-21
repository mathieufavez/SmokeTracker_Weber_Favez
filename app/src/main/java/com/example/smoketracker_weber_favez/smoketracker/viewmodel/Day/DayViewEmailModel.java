package com.example.smoketracker_weber_favez.smoketracker.viewmodel.Day;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.DayEntity;
import com.example.smoketracker_weber_favez.smoketracker.db.db.repository.DayRepository;
import com.example.smoketracker_weber_favez.smoketracker.util.OnAsyncEventListener;

public class DayViewEmailModel extends AndroidViewModel {

    private DayRepository repository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<DayEntity> observableClient;

    public DayViewEmailModel(@NonNull Application application,
                        final String email, DayRepository dayRepository) {
        super(application);

        repository = dayRepository;

        applicationContext = application.getApplicationContext();

        observableClient = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableClient.setValue(null);

        LiveData<DayEntity> day = repository.getOneDayWithEmail(email);

        // observe the changes of the client entity from the database and forward them
        observableClient.addSource(day, observableClient::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String email;

        private final DayRepository repository;

        public Factory(@NonNull Application application, String userEmail) {
            this.application = application;
            this.email = userEmail;
            repository = DayRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new DayViewEmailModel(application, email, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<DayEntity> getDay() {
        return observableClient;
    }

    public void createDay(DayEntity day, String idUser,OnAsyncEventListener callback) {
        repository.insert(day, idUser, callback);
    }

    public void updateDay(DayEntity day, String userId, String dayId, OnAsyncEventListener callback) {
        repository.update(day,userId,dayId,callback);
    }

    public void deleteDay(DayEntity day, OnAsyncEventListener callback) {
        repository.delete(day, callback);
    }
}



