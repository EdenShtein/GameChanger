package com.example.gamechanger;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.gamechanger.model.Model;
import com.example.gamechanger.model.User.User;

public class EditUserFragment extends Fragment {

    ImageView back_btn;
    Button save_btn;

    EditText fName;
    EditText lName;
    EditText phoneNum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_user, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Edit User");

        back_btn = view.findViewById(R.id.edituser_back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        fName = view.findViewById(R.id.edituser_fname_input);
        lName = view.findViewById(R.id.edituser_lname_input);
        phoneNum = view.findViewById(R.id.edituser_phonenumber_input);

        Model.instance.getUserData(Model.instance.getUserId(), new Model.userDataListener() {
            @Override
            public void onComplete(String fname, String lname, String phone) {
                fName.setText(fname);
                lName.setText(lname);
                phoneNum.setText(phone);
            }
        });

        save_btn = view.findViewById(R.id.edituser_save_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = fName.getText().toString();
                String lastName = lName.getText().toString();
                String phoneNumber = phoneNum.getText().toString();
                String email = EditUserFragmentArgs.fromBundle(getArguments()).getEmail();
                User user = new User(firstName,lastName,email,phoneNumber);

                Model.instance.updateUser(user, new Model.AddUserListener() {
                    @Override
                    public void onComplete() {
                        Navigation.findNavController(view).navigate(R.id.action_edituser_to_userProfile);
                    }
                });
            }
        });

        return view;
    }
}