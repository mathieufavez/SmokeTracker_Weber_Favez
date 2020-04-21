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

import java.util.List;

public class DayListViewModel extends AndroidViewModel {

    private DayRepository repository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<DayEntity>> observableClients;

    public DayListViewModel(@NonNull Application application, DayRepository dayRepository) {
        super(application);

        repository = dayRepository;

        applicationContext = application.getApplicationContext();

        observableClients = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableClients.setValue(null);

        LiveData<List<DayEntity>> users = repository.getAllDays();

        // observe the changes of the entities from the database and forward them
        observableClients.addSource(users, observableClients::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;


        private final DayRepository dayRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            dayRepository = DayRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new DayListViewModel(application, dayRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<DayEntity>> getAllDays() {
        return observableClients;
    }
}
