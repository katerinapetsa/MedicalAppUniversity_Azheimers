package com.example.ehealth.lab.university.medications;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ehealth.lab.university.R;

/**
 * The dialog box of Refill Button.
 *
 * @author Stavroula Kousparou
 */

public class DialogRefill extends AppCompatDialogFragment {

    private EditText refillNum;
    private TextView numOfMeds;
    private DialogRefillListener listener;
    private String nowReffill = "0";


    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_refill_dialog, null);

        numOfMeds = (TextView)view.findViewById(R.id.numOfMeds);
        numOfMeds.setText(" " + nowReffill);

        builder.setView(view)
                .setTitle(getString(R.string.refillTitle))
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String refillMeds = refillNum.getText().toString();
                        listener.applyTexts(refillMeds);
                    }
                });

        refillNum = view.findViewById(R.id.refillNum);

        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            listener = (DialogRefillListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DialogRefillListener");
        }

    }

    public void takeNumRefill(String numRefill){
        nowReffill = numRefill;
    }


    public interface DialogRefillListener{
        void applyTexts(String refillMeds);
    }

}
