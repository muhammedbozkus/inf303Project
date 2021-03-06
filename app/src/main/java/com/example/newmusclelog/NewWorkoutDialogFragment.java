package com.example.newmusclelog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.newmusclelog.custom.NewWorkoutDialog;
import com.example.newmusclelog.data.Workout;
import com.example.newmusclelog.data.WorkoutHistory;

import java.io.IOException;

public class NewWorkoutDialogFragment extends Fragment {

    private AlertDialog dialog;
    private EditText input;

    public NewWorkoutDialogFragment(final MainActivity mainActivity) throws IOException {

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle("Create new workout");
        final View view = buildView(mainActivity);
        input = view.findViewById(R.id.input);
        builder.setView(view);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String userInput = input.getText().toString();
                dialog.dismiss();

                try {

                    mainActivity.launchNewWorkoutWizard(userInput);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog = builder.create();

        // sets up keyboard to show up when dialog opens and close when
        // dialog closes
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public void show()
    {
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        // if text in EditText, enable OK button. else, disabled
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                } else {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
            }
        });
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view =  inflater.inflate(R.layout.fragment_new_workout_dialog, container, false);
        return view;
    }

    private View buildView(Activity activity) {
        // constructs view from dialog_create_new_workout.xml
        return LayoutInflater.from(activity).inflate(R.layout.fragment_new_workout_dialog, null);
    }
}
