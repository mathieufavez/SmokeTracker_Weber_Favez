package com.example.smoketracker_weber_favez.smoketracker.viewmodel.Hour;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.DayEntity;
import com.example.smoketracker_weber_favez.smoketracker.db.db.entity.HourEntity;
import com.example.smoketracker_weber_favez.smoketracker.db.db.repository.DayRepository;
import com.example.smoketracker_weber_favez.smoketracker.db.db.repository.HourRepository;
import com.example.smoketracker_weber_favez.smoketracker.util.OnAsyncEventListener;
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Day.DayViewModel;

public class HourViewModel extends AndroidViewModel {

    private HourRepository repository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<HourEntity> observableClient;

    public HourViewModel(@NonNull Application application,
                        final String idDay, HourRepository hourRepository) {
        super(application);

        repository = hourRepository;

        applicationContext = application.getApplicationContext();

        observableClient = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableClient.setValue(null);

        LiveData<HourEntity> day = repository.getOneHour(idDay);

        // observe the changes of the client entity from the database and forward them
        observableClient.addSource(day, observableClient::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String id;

        private final HourRepository repository;

        public Factory(@NonNull Application application, String hourId) {
            this.application = application;
            this.id = hourId;
            repository = HourRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new HourViewModel(application, id, repository);
        }
    }


    public LiveData<HourEntity> getHour() {
        return observableClient;
    }

    public void createHour(HourEntity hour, OnAsyncEventListener callback) {
        repository.insert(hour, callback);
    }

    public void updateHour(HourEntity hour, OnAsyncEventListener callback) {
        repository.update(hour, callback);
    }

    public void deleteHour(HourEntity hour, OnAsyncEventListener callback) {
        repository.delete(hour, callback);
    }
}
