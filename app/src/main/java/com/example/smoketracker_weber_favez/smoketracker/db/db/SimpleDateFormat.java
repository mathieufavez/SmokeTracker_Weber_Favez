package com.example.smoketracker_weber_favez.smoketracker.db.db;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;

public class SimpleDateFormat extends DateFormat {

    @NonNull
    @Override
    public StringBuffer format(@NonNull Date date, @NonNull StringBuffer toAppendTo, @NonNull FieldPosition fieldPosition) {
        return null;
    }

    @Nullable
    @Override
    public Date parse(@NonNull String source, @NonNull ParsePosition pos) {
        return null;
    }
}
