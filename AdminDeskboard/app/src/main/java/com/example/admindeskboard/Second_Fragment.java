package com.example.admindeskboard;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admindeskboard.databinding.FragmentSecondBinding;


public class Second_Fragment extends Fragment {

   FragmentSecondBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSecondBinding.inflate(getLayoutInflater(), container, false);

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("Warning!")
                        .setMessage("Are You going to send data?")
                        .setCancelable(false)
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String title = binding.titleEDText.getText().toString();
                                String descrioption = binding.descriptionEDText.getText().toString();
                                String imagelink = binding.imagelinkEDTExt.getText().toString().trim().replaceAll(" ", "");
                                String url = "https://testserverleo.000webhostapp.com/apps/DemoUploadDataTwo.php?ti=" + title + "&de=" + descrioption + "&im=" + imagelink;

                                binding.progressBar.setVisibility(View.VISIBLE);
                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        binding.progressBar.setVisibility(View.GONE);
                                        new AlertDialog.Builder(getActivity())
                                                .setTitle("Rerver Response")
                                                .setMessage(response)
                                                .show();

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        binding.progressBar.setVisibility(View.GONE);
                                        new AlertDialog.Builder(getActivity())
                                                .setTitle("Error!")
                                                .setMessage("Something Went Wrong")
                                                .show();
                                    }

                                });
                                if (title.length() > 0 && descrioption.length() > 0 && imagelink.length() > 0) {
                                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                                    requestQueue.add(stringRequest);

                                } else {
                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("Error!")
                                            .setMessage("Something Went Wrong!")
                                            .show();
                                    binding.progressBar.setVisibility(View.GONE);
                                }
                            }

                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })

                        .show();
            }

        });


        binding.wantToShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Second_View_Fragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment).addToBackStack(null).commit();
            }
        });

        return binding.getRoot();
    }
}