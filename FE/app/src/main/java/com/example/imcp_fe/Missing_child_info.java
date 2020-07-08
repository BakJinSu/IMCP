package com.example.imcp_fe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.imcp_fe.Network.AppHelper;
import com.example.imcp_fe.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Missing_child_info extends AppCompatActivity implements OnMapReadyCallback {


    public double x=0.0;
    public double y=0.0;
    private  GoogleMap mMap;
    private ImageButton btn_missingchild_info_back;
    private CircleImageView iv_missingchild_info_photo;
    private TextView tv_missingchild_info_name;
    private TextView tv_missingchild_info_age;
    private TextView tv_missingchild_info_phone;

    private String key;
    private String image;
    private String name;
    private String birth;
    private String phone;
    private Intent intent;
    private String url = "http://tomcat.comstering.synology.me/IMCP_Server/getChildGPS.jsp";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.missing_child_info);
        intent = getIntent();
        iv_missingchild_info_photo= findViewById(R.id.iv_missingchild_info_photo);
        tv_missingchild_info_name = findViewById(R.id.tv_missingchild_info_name);
        tv_missingchild_info_age =findViewById(R.id.tv_missingchild_info_age);
        tv_missingchild_info_phone =findViewById(R.id.tv_missingchild_info_phone);

        btn_missingchild_info_back = (ImageButton) findViewById(R.id.btn_missingchild_info_back);
        key = intent.getStringExtra("key");
        image =intent.getStringExtra("image");
        name=intent.getStringExtra("name");
        birth = intent.getStringExtra("birth");
        phone = intent.getStringExtra("phone");

        Picasso.with(getApplicationContext()).load(image).into(iv_missingchild_info_photo);
        tv_missingchild_info_name.setText(name);
        tv_missingchild_info_phone.setText(phone);

        Calendar cal = null;
        SimpleDateFormat formats;
        formats = new SimpleDateFormat ( "yyyy");

//Finalvar.birth_year의 값은 1950년 1월 20일
        int time2 = Integer.parseInt(formats.format(cal.getTime()));
        int ageSum = Integer.parseInt(birth.substring(0,4));

        tv_missingchild_info_age.setText(time2 - ageSum +1);




        loactionRequest(url);

    }

    @Override
    protected void onStart() {
        super.onStart();
        btn_missingchild_info_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mv_missingchild);
        mapFragment.getMapAsync(this);
//    loactionRequest();
    }

    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        LatLng loaction = new LatLng(x, y);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(loaction);
        markerOptions.title("현재 위치");


        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loaction));//카메라의 위도 경도를 설정, loaction으로 서버에서 위치를 받아온다.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));//카메라 확대 기능, 숫자가 높을수록 가까워짐 1단계일 경우 세계지도수준
    }


    public void loactionRequest(String url) {



            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                if(response.equals(null)==false) {
                                    JSONArray jarray = new JSONArray(response);
                                    int size = jarray.length();
                                    for (int i = 0; i < size; i++) {
                                        JSONObject row = jarray.getJSONObject(i);
                                        x = row.getDouble("x"); // x, y 좌표를 받아옴.
                                        y = row.getDouble("y");
                                    }
                                }else if(response.equals(null)==true){
                                    Log.e("volley", response);
                                    Toast.makeText(Missing_child_info.this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }
                    },
                    new Response.ErrorListener() { //에러발생시 호출될 리스너 객체
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("volley", error.toString());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("childKey", key);
                    return params;
                }
            };

            request.setShouldCache(false);
            AppHelper.requestQueue = Volley.newRequestQueue(this);
            AppHelper.requestQueue.add(request);
        }
}
