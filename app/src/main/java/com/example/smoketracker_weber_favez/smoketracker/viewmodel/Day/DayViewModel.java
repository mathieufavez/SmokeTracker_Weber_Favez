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

public class DayViewModel extends AndroidViewModel {

    private DayRepository repository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<DayEntity> observableClient;

    public DayViewModel(@NonNull Application application,
                         final String id, DayRepository dayRepository) {
        super(application);

        repository = dayRepository;

        applicationContext = application.getApplicationContext();

        observableClient = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableClient.setValue(null);

        LiveData<DayEntity> day = repository.getOneDay(id);

        // observe the changes of the client entity from the database and forward them
        observableClient.addSource(day, observableClient::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String id;

        private final DayRepository repository;

        public Factory(@NonNull Application application, String dayId) {
            this.application = application;
            this.id = dayId;
            repository = DayRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new DayViewModel(application, id, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<DayEntity> getDay() {
        return observableClient;
    }

    public void createDay(DayEntity day, OnAsyncEventListener callback) {
        repository.insert(day, callback);
    }

    public void updateDay(DayEntity day, OnAsyncEventListener callback) {
        repository.update(day, callback);
    }

    public void deleteDay(DayEntity day, OnAsyncEventListener callback) {
        repository.delete(day, callback);
    }
}


