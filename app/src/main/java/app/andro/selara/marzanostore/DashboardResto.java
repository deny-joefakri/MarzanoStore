package app.andro.selara.marzanostore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.andro.selara.marzanostore.adater.MyRecyclerViewAdapter;
import app.andro.selara.marzanostore.helper.SQLiteHandler;
import app.andro.selara.marzanostore.helper.SessionManager;
import app.andro.selara.marzanostore.model.Resto;

/**
 * Created by kilatan on 1/7/16.
 */
public class DashboardResto extends AppCompatActivity {

    // Log tag
    private static final String TAG = DashboardResto.class.getSimpleName();

    // json url
    private static final String url = "http://pe.devtuwaga.com/page-test/";

    // JSON Node names
    private static final String TAG_judul = "test";
    private static final String TAG_name = "name";
    private static final String TAG_desc = "desc";
    private static final String TAG_type = "type";
    private static final String TAG_image = "image";

    public static String t_judul, t_desc, t_type, t_image;

    // contacts JSONArray
    JSONArray judul = null;

    private ProgressDialog pDialog;
    private ArrayList<Resto> restoList = new ArrayList<Resto>();

    private ListView listView;

    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;

    private SQLiteHandler db;
    private SessionManager session;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";

    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtName = (TextView) findViewById(R.id.name);
        txtEmail = (TextView) findViewById(R.id.email);
        //btnLogout = (Button) findViewById(R.id.btnLogout);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        if(savedInstanceState!=null){
            Log.d("STATE",savedInstanceState.toString());
        }

        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try{

                            JSONArray ja = response.getJSONArray(TAG_judul);

                            for(int i=0; i < ja.length(); i++){

                                JSONObject jsonObject = ja.getJSONObject(i);

                                // int id = Integer.parseInt(jsonObject.optString("id").toString());
                                t_judul = jsonObject.getString(TAG_name).replace("<p>", "").replace("</p>","").replace("&#8220;","'").replace("<\\/p>\\n","").replace("&#8211;", "-").replace("\\n", "\\b").replace("&#8221;","'").replace("n&#8217;","");
                                t_desc = jsonObject.getString(TAG_desc).replace("<p>", "").replace("</p>", "").replace("/","").replace("<br", "").replace(",", "").replace("...","").replace("<em><stron","").replace("<strong><e","").replace("<strong><s","").replace("style=","").
                                        replace("/", "").replace("<br />\\n"," ").replace("\\n","");
                                t_type = jsonObject.getString(TAG_type).replace("<p>", "").replace("</p>","");
                                t_image = jsonObject.getString(TAG_image);

                                //data += "Blog Number "+(i+1)+" \n Blog Name= "+title  +" \n URL= "+ url +" \n\n\n\n ";
                                //Toast.makeText(getApplicationContext(), t_judul + "  " + t_desc, Toast.LENGTH_LONG).show();
                                Resto hasil = new Resto(t_judul, t_desc, t_type, t_image);
                                //Toast.makeText(getApplicationContext(), String.valueOf(hasil), Toast.LENGTH_LONG).show();
                                restoList.add(i, hasil);

                            }
                            mAdapter = new MyRecyclerViewAdapter(restoList);
                            mRecyclerView.setAdapter(mAdapter);
                            Toast.makeText(getApplicationContext(), String.valueOf(restoList.size()), Toast.LENGTH_LONG).show();
                            ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                                    .MyClickListener() {
                                @Override
                                public void onItemClick(int position, View v) {
                                    Log.i(LOG_TAG, " Clicked on Item " + position);
                                }
                            });
                            //output.setText(data);
                        }catch(JSONException e){e.printStackTrace();}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley","Error");

                    }
                }
        );
        requestQueue.add(jor);




        if (!session.isLoggedIn()) {
            logoutUser();
        }


        // Logout button click event
        /*btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });*/
    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(DashboardResto.this, Login.class);
        startActivity(intent);
        finish();
    }


}
