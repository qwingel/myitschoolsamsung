package com.example.myitschoolsamsung;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.w3c.dom.Text;

public class MyBottomSheet extends BottomSheetDialogFragment {
    Button mbs_close, select,  adult_plus, adult_minus, kid_plus, kid_minus, child_plus, child_minus;
    RadioButton rb_econom, rb_comfort, rb_business, rb_thefirstclass;
    String sz_classes = "ECO";
    int countTickets = 1; int countKidTickets = 0; int countChildTickets = 0;
    boolean isWithKid = false, isWithChild = false;
    String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    TextView adultTicketsCount, kidTicketsCount, childTicketsCount;
    BottomSheetDialog dialog;
    BottomSheetBehavior<View> bsb;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kolvochelovek, container, false);
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

        LinearLayout layout = dialog.findViewById(R.id.bottomSheetLayout);
        assert layout != null;
        layout.setMinimumHeight(Resources.getSystem().getDisplayMetrics().heightPixels);

        adult_plus = (Button) view.findViewById(R.id.adult_plus);
        kid_plus = (Button) view.findViewById(R.id.kid_plus);
        child_plus = (Button) view.findViewById(R.id.child_plus);
        adult_minus = (Button) view.findViewById(R.id.adult_minus);
        kid_minus = (Button) view.findViewById(R.id.kid_minus);
        child_minus = (Button) view.findViewById(R.id.child_minus);

        select = (Button) view.findViewById(R.id.select_filters);
        mbs_close = (Button) view.findViewById(R.id.mbs_close);

        rb_econom = (RadioButton) view.findViewById(R.id.rb_econom);
        rb_econom.setOnClickListener(radioButtonClickListener);
        rb_comfort = (RadioButton) view.findViewById(R.id.rb_comfort);
        rb_comfort.setOnClickListener(radioButtonClickListener);
        rb_business = (RadioButton) view.findViewById(R.id.rb_business);
        rb_business.setOnClickListener(radioButtonClickListener);
        rb_thefirstclass = (RadioButton) view.findViewById(R.id.rb_thefirstclass);
        rb_thefirstclass.setOnClickListener(radioButtonClickListener);

        adultTicketsCount = (TextView) view.findViewById(R.id.adultTicketsCount);
        kidTicketsCount = (TextView) view.findViewById(R.id.kidTicketsCount);
        childTicketsCount = (TextView) view.findViewById(R.id.childTicketsCount);


        adult_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countTickets < 9){
                    countTickets++;
                    adultTicketsCount.setText(numbers[countTickets]);
                }
            }
        });

        adult_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(countTickets > 0){
                    countTickets--;
                    adultTicketsCount.setText(numbers[countTickets]);
                }
            }
        });
        kid_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isWithKid = true;
                if(countKidTickets < 9){
                    countKidTickets++;
                    kidTicketsCount.setText(numbers[countKidTickets]);
                }
            }
        });
        kid_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(countKidTickets > 0) {
                    countKidTickets--;
                    kidTicketsCount.setText(numbers[countKidTickets]);
                    if (countKidTickets == 0){
                        isWithKid = false;
                    }
                }
            }
        });
        child_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isWithChild = true;
                if(countChildTickets < 9){
                    countChildTickets++;
                    childTicketsCount.setText(numbers[countChildTickets]);
                }
            }
        });

        child_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(countChildTickets > 0){
                    countChildTickets--;
                    childTicketsCount.setText(numbers[countChildTickets]);
                    if(countChildTickets == 0){
                        isWithChild = false;
                    }
                }
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mbs_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        HomeScreen.setFilters(sz_classes, (countTickets + countKidTickets + countChildTickets), isWithKid, isWithChild);
        super.onDismiss(dialog);
    }

    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            RadioButton rb = (RadioButton)v;
            boolean isChecked = rb.isEnabled();
            switch (rb.getId()) {
                case R.id.rb_econom:
                    rb_econom.setChecked(true);
                    rb_comfort.setChecked(false);
                    rb_business.setChecked(false);
                    rb_thefirstclass.setChecked(false);
                    sz_classes = "ECO";
                    break;
                case R.id.rb_comfort:
                    rb_econom.setChecked(false);
                    rb_comfort.setChecked(true);
                    rb_business.setChecked(false);
                    rb_thefirstclass.setChecked(false);
                    sz_classes = "CMF";
                    break;
                case R.id.rb_business:
                    rb_econom.setChecked(false);
                    rb_comfort.setChecked(false);
                    rb_business.setChecked(true);
                    rb_thefirstclass.setChecked(false);
                    sz_classes = "BUS";
                    break;
                case R.id.rb_thefirstclass:
                    rb_econom.setChecked(false);
                    rb_comfort.setChecked(false);
                    rb_business.setChecked(false);
                    rb_thefirstclass.setChecked(true);
                    sz_classes = "TFC";
                    break;

                default:
                    break;
            }
        }
    };
}
