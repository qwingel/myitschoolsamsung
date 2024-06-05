package com.example.myitschoolsamsung;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CalendarBottomSheet extends BottomSheetDialogFragment {
    Button cbs_close, clear_calendar;

    String selectedDate = "*";
    CalendarView cv_calendar;
    BottomSheetDialog dialog;
    BottomSheetBehavior<View> bsb;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_bottom, container, false);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        bsb = BottomSheetBehavior.from((View) view.getParent());
        bsb.setState(BottomSheetBehavior.STATE_EXPANDED);

        LinearLayout layout = dialog.findViewById(R.id.caledarSheetLayout);
        assert layout != null;
        layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);

        cbs_close = (Button) view.findViewById(R.id.cbs_close);
        clear_calendar = (Button) view.findViewById(R.id.clear_calendar);
        cv_calendar = (CalendarView) view.findViewById(R.id.calendarView);

        clear_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeScreen.setDate("*");
                dismiss();
            }
        });
        cbs_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        cv_calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayofMonth) {
                int mYear = year;
                int mMonth = month;
                int mDay = dayofMonth;
                selectedDate = mDay + "." + mMonth + "." + mYear;

                HomeScreen.setDate(selectedDate);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
