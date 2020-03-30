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
import com.example.smoketracker_weber_favez.smoketracker.viewmodel.Day.DayListOneUserViewModel;

import java.util.List;

public class ListHourViewModel extends AndroidViewModel {

    private HourRepository repository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<HourEntity>> observableClients;

    public ListHourViewModel(@NonNull Application application, final int idDay, HourRepository hourRepository) {
        super(application);

        repository = hourRepository;

        applicationContext = application.getApplicationContext();

        observableClients = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableClients.setValue(null);

        LiveData<List<HourEntity>> days = repository.getAllHoursForOneDay(idDay, applicationContext);

        // observe the changes of the entities from the database and forward them
        observableClients.addSource(days, observableClients::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final int idDay;

        private final HourRepository hourRepository;

        public Factory(@NonNull Application application, int dayId) {
            this.application = application;
            this.idDay = dayId;
            hourRepository = HourRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ListHourViewModel(application, idDay, hourRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<HourEntity>> getAllHoursForOneDay() {
        return observableClients;
    }



}

