package com.example.admindeskboard;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admindeskboard.databinding.FirstRvViewBinding;
import com.example.admindeskboard.databinding.FragmentSecondBinding;
import com.example.admindeskboard.databinding.FragmentSecondViewBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Second_View_Fragment extends Fragment {

    FragmentSecondViewBinding binding;
    HashMap<String, String> hashMap;
    ArrayList<HashMap<String, String>> myArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSecondViewBinding.inflate(getLayoutInflater(), container, false);

        loadData();


        return binding.getRoot();
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private class MyViewHolder extends RecyclerView.ViewHolder {
            FirstRvViewBinding binding;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                binding = FirstRvViewBinding.bind(itemView);
            }
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.first_rv_view, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            hashMap = myArrayList.get(position);
            String id = hashMap.get("id_key");
            String title = hashMap.get("title_key");
            String description = hashMap.get("description_key");
            String imageLink = hashMap.get("imagelink_key");

            holder.binding.titleID.setText(title);
            holder.binding.descriptionID.setText(description);
            Picasso.get()
                    .load(imageLink)
                    .into(holder.binding.imageID);

            holder.binding.UpdateButtonID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putString("id_key_two", id);
                    Fragment fragment = new UpdateFragment_Two();
                    fragment.setArguments(bundle);

                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment).addToBackStack(null).commit();


                }
            });

            holder.binding.DeleteButtonID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new AlertDialog.Builder(getActivity())
                            .setTitle("Warning!")
                            .setMessage("Confirm to Delete ?")
                            .setCancelable(false)
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    binding.progressBar.setVisibility(View.VISIBLE);
                                    String url = "https://testserverleo.000webhostapp.com/apps/DemoDeleteDataTwo.php?id=" + id;

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
                                            new AlertDialog.Builder(getActivity())
                                                    .setTitle("Server Response")
                                                    .setMessage("Something Went Wrong!")
                                                    .show();
                                        }
                                    });
                                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                                    requestQueue.add(stringRequest);

                                }
                            })
                            .show();


                }
            });


        }

        @Override
        public int getItemCount() {
            return myArrayList.size();
        }

    }

    public void loadData() {

        myArrayList = new ArrayList<>();

        String url = "https://testserverleo.000webhostapp.com/apps/DemoLoadDataTwo.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            binding.progressBar.setVisibility(View.GONE);

            for (int x = 0; x < response.length(); x++) {
                try {
                    JSONObject jsonObject = response.getJSONObject(x);
                    String id = jsonObject.getString("id");
                    String title = jsonObject.getString("itemlist");
                    String description = jsonObject.getString("description");
                    String imagelink = jsonObject.getString("imagelink");

                    hashMap = new HashMap<>();
                    hashMap.put("id_key", id);
                    hashMap.put("title_key", title);
                    hashMap.put("description_key", description);
                    hashMap.put("imagelink_key", imagelink);
                    myArrayList.add(hashMap);


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            MyAdapter myAdapter = new MyAdapter();
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            binding.secondRvView.setLayoutManager(layoutManager);
            binding.secondRvView.setAdapter(myAdapter);


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);
    }
}