package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddEditCityFragment extends DialogFragment {
    interface AddEditCityDialogListener {
        void addCity(City city);
        void editCity(City city, int position);
    }
    private AddEditCityDialogListener listener;

    City city;
    int position;

    AddEditCityFragment(){
        super();
        this.city = null;
        this.position = -1;
    }

    AddEditCityFragment(City city, int position) {
        this.city = city;
        this.position = position;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof AddEditCityDialogListener) {
            listener = (AddEditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddEditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view =
                LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        if(this.city != null){
            editCityName.setText(city.getName());
            editProvinceName.setText(city.getProvince());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle(this.city == null ? "Add" : "Edit" + " a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton(this.city == null ? "Add" : "Edit", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    if(this.city == null) {
                        listener.addCity(new City(cityName, provinceName));
                    } else {
                        listener.editCity(new City(cityName, provinceName), position);
                    }
                })
                .create();
    }
}
