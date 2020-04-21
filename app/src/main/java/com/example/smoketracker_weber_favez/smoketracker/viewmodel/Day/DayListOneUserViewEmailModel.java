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

public class DayListOneUserViewEmailModel extends AndroidViewModel {

    private DayRepository repository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<DayEntity>> observableClients;

    public DayListOneUserViewEmailModel(@NonNull Application application, final String id, DayRepository dayRepository) {
        super(application);

        repository = dayRepository;

        applicationContext = application.getApplicationContext();

        observableClients = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableClients.setValue(null);

        LiveData<List<DayEntity>> days = repository.getAllDaysForOneUser(id);

        // observe the changes of the entities from the database and forward them
        observableClients.addSource(days, observableClients::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String id;


        private final DayRepository dayRepository;

        public Factory(@NonNull Application application, String id) {
            this.application = application;
            this.id = id;
            dayRepository = DayRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new DayListOneUserViewEmailModel(application, id, dayRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<DayEntity>> getAllDaysForOneUser() {
        return observableClients;
    }
}

