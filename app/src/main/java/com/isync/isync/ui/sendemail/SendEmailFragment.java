package com.isync.isync.ui.sendemail;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.isync.isync.DataObject.EmailTemplateData;
import com.isync.isync.DataObject.UserData;
import com.isync.isync.MainActivity;
import com.isync.isync.R;
import com.isync.isync.databinding.FragmentSendemailBinding;
import com.isync.isync.helper.Global;
import com.isync.isync.ui.setting.SettingViewModel;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SendEmailFragment extends Fragment {

    private SendEmailViewModel sendEmailViewModel;
    private FragmentSendemailBinding binding;

    EditText editEmail, editContent, editWebsite;
    Spinner spinnerSubject;
    Button btnSend;
    int nSelected = 0;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        sendEmailViewModel =
                new ViewModelProvider(this).get(SendEmailViewModel.class);

        binding = FragmentSendemailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        editEmail = binding.editEmail;
        editContent = binding.editContent;
        editWebsite = binding.editWebsite;
        spinnerSubject = binding.spinnerSubject;
        btnSend = binding.btnSend;

        ArrayList<String> subjects = new ArrayList<>();
        for(int i = 0; i < Global.emailTemplate.data.length; i++){
            subjects.add(Global.emailTemplate.data[i].email_subject);
        }

        ArrayAdapter<String> spinnerArrayAdapter= new ArrayAdapter<>(getContext(),
                R.layout.spinner_item,
                subjects);
        spinnerSubject.setAdapter(spinnerArrayAdapter);

        spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nSelected = position;
                updateContent();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        updateContent();

        btnSend.setOnClickListener(v -> {
            if(editEmail.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "Please input the email address", Toast.LENGTH_SHORT).show();
                return;
            }
            if(editWebsite.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "Please input the website url", Toast.LENGTH_SHORT).show();
                return;
            }
            if(nSelected < Global.emailTemplate.data.length){
                MainActivity.getInstance().sendEmail(editEmail.getText().toString(), Global.emailTemplate.data[nSelected].id, editWebsite.getText().toString());
            }

        });
        return root;
    }

    void updateContent(){
        if(nSelected < Global.emailTemplate.data.length)
            editContent.setText(Html.fromHtml(Global.emailTemplate.data[nSelected].email_body));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}