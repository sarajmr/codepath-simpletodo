package com.sarajmudigonda.simpletodo;

import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.TextView;

import static java.lang.String.valueOf;

public class EditNameDialogFragment extends DialogFragment implements TextView.OnEditorActionListener {

    int pos;
    Boolean statusDelete = false;
    AlertDialog.Builder builder;
    AlertDialog alert;
    EditNameDialogListener listener;
    private TextView mEditText;

    public EditNameDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditNameDialogFragment newInstance(String title) {
        EditNameDialogFragment frag = new EditNameDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.activity_edit_name_dialog_fragment, container);
        return getActivity().getLayoutInflater().inflate(R.layout.activity_edit_name_dialog_fragment, container);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditText = (TextView) view.findViewById(R.id.tvTaskNameValue);
        // Fetch arguments from bundle and set title
        //String title = getArguments().getString("title", "Enter Name");

        String task_name = valueOf(getArguments().getString("task_name"));
        mEditText.setText(task_name);

        TextView mTaskDate = (TextView) view.findViewById(R.id.tvDateValue);
        String task_date = valueOf(getArguments().getString("task_date"));
        mTaskDate.setText(task_date);

        TextView mTaskNotes = (TextView) view.findViewById(R.id.tvTaskNotesValue);
        String task_notes = valueOf(getArguments().getString("task_notes"));
        mTaskNotes.setText(task_notes);

        TextView mTaskPriority = (TextView) view.findViewById(R.id.tvPriorityLevelValue);
        String task_priority = valueOf(getArguments().getString("task_priority"));
        mTaskPriority.setText(task_priority);

        TextView mTaskStatus = (TextView) view.findViewById(R.id.tvStatusValue);
        String task_status = valueOf(getArguments().getString("task_status"));
        mTaskStatus.setText(task_status);

        pos = getArguments().getInt("position");
        //getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        /*
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        */
        mEditText.setOnEditorActionListener(this);

        // Watch for button clicks.
        ImageButton mBtnDelete = (ImageButton) view.findViewById(R.id.btnDelete);
        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // When button is clicked, call up to owning activity.
                listener = (EditNameDialogListener) getActivity();

                //Delete Alert
            /*
                builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");
                builder.setIcon(R.drawable.ic_delete);

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        statusDelete = true;
                        // Do nothing but close the dialog
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        statusDelete = false;
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                alert = builder.create();
                alert.show();
            */

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());

                // Setting Dialog Title
                alertDialog.setTitle("Confirm Delete");

                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want delete this task?");

                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.ic_alert);

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // Write your code here to invoke YES event
                        //Toast.makeText(view.getContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                        statusDelete = false;
                        listener.onFinishEditDialog("REMOVE", pos);
                        dismiss();
                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        //Toast.makeText(view.getContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();

            }
        });

        // Watch for button clicks.
        ImageButton mBtnEdit = (ImageButton) view.findViewById(R.id.btnEdit);
        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // When button is clicked, call up to owning activity.
                EditNameDialogListener listener = (EditNameDialogListener) getActivity();
                listener.onFinishEditDialog("EDIT", pos);
                dismiss();
            }
        });
    }

    // Fires whenever the textfield has an action performed
    // In this case, when the "Done" button is pressed
    // REQUIRES a 'soft keyboard' (virtual keyboard)
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text back to activity through the implemented listener
            EditNameDialogListener listener = (EditNameDialogListener) getActivity();
            listener.onFinishEditDialog(mEditText.getText().toString(), pos);

            // Close the dialog and return back to the parent activity
            dismiss();
            return true;
        }
        return false;
    }

    public void onResume() {
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        //window.setLayout((int) (size.x * 0.75), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setLayout((int) (size.x * 0.95), (int) (size.y * 0.65));
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
        super.onResume();
    }

    // 1. Defines the listener interface with a method passing back data result.
    public interface EditNameDialogListener {
        void onFinishEditDialog(String inputText, int position);
    }


}