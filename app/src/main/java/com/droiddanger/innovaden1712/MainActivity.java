package com.droiddanger.innovaden1712;

import com.droiddanger.innovaden1712.app.AppController;
import android.content.DialogInterface;
        import android.content.Intent;
        import android.net.Uri;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.DefaultItemAnimator;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.view.View;
        import android.widget.Toast;

        import com.android.volley.DefaultRetryPolicy;
        import com.android.volley.Request;
        import com.android.volley.Response;
        import com.android.volley.RetryPolicy;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.droiddanger.innovaden1712.Adapters.Adapters;
        import com.droiddanger.innovaden1712.app.AppConfig;
        import com.droiddanger.innovaden1712.app.AppController;
        import com.droiddanger.innovaden1712.listeners.RecyclerItemClickListener;
        import com.droiddanger.innovaden1712.pojo.Datas;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private List<Datas> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private Adapters mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new Adapters(list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent homeIntent = new Intent(getApplicationContext(), EventActivity.class);
                        homeIntent.putExtra("title", list.get(position).getTitle());
                        homeIntent.putExtra("description", list.get(position).getDescription());
                        startActivity(homeIntent);
                    }
                })
        );



        getData();




    }






    public void getData(){


        String tag_string_req = "req_data";



        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();







                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0; i< jsonArray.length() ;i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                        String title1=jsonObject.getString("title");
                        String description1=jsonObject.getString("description");

                        Datas datas = new Datas(title1, description1);
                        list.add(datas);

                        //  Toast.makeText(MainActivity.this, items.get(i).getTitle(), Toast.LENGTH_SHORT).show();



                    }

                    mAdapter.notifyDataSetChanged();



                    // Check for error node in json

                } catch (JSONException e) {
                    // JSON error

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();



                return params;
            }

        };

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


    }
}