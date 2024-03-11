package com.example.admindeskboard;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admindeskboard.databinding.FragmentUpdateBinding;


public class UpdateFragment extends Fragment {

    FragmentUpdateBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUpdateBinding.inflate(getLayoutInflater(), container, false);

        Bundle bundle = getArguments();
        String id = bundle.getString("id_key");

        binding.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = binding.titleEDText.getText().toString();
                String description = binding.descriptionEDText.getText().toString();
                String imagelink = binding.imagelinkEDTExt.getText().toString().trim().replaceAll(" ", "");
                String url = "https://testserverleo.000webhostapp.com/apps/DemoUpdateData.php?t=" + title + "&d=" + description + "&i=" + imagelink + "&id=" + id;

                binding.progressBar.setVisibility(View.VISIBLE);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        binding.progressBar.setVisibility(View.GONE);

                        new AlertDialog.Builder(getActivity())
                                .setTitle("Server Response")
                                .setMessage(response)
                                .show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        binding.progressBar.setVisibility(View.GONE);
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Server Response")
                                .setMessage("Please Check Internet Connection")
                                .show();
                    }
                });

                if (title.length() > 0 && description.length() > 0 && imagelink.length() > 0) {
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    requestQueue.add(stringRequest);
                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error!")
                            .setMessage("Please Insert All Required Data")
                            .show();
                    binding.progressBar.setVisibility(View.GONE);
                }
            }

        });


        return binding.getRoot();
    }
}