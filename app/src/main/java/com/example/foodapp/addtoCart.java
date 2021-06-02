package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;

public class addtoCart extends FragmentActivity implements OnMapReadyCallback {

    private TextView mTitle,mDes, mDate, mQuantity;
    private Button addtoCart;
    private ImageView mimageView;
    private Bitmap bitmap;
    DatabaseHelper4 db;
    private static final float DEFAULT_ZOOM = 15f;
    public static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(com.example.foodapp.config.PAYPAL_CLIENT_ID);
    private Button paybtn;
    String amount = "1";
    ArrayList<LatLng> list;
    ArrayList<String> nameLs;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addto_cart);
        mTitle = findViewById(R.id.displayTitle);
        mDes = findViewById(R.id.displayDes);
        mDate = findViewById(R.id.displayDate);
        mQuantity = findViewById(R.id.displayQuan);
        mimageView = findViewById(R.id.imageView);
        addtoCart = findViewById(R.id.addcart);
        paybtn = findViewById(R.id.paybutton);
        db = new DatabaseHelper4(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent i =  getIntent();
        String title = i.getStringExtra("title");
        String des = i.getStringExtra("des");
        String date = i.getStringExtra("date");
        String quan = i.getStringExtra("quan");
        bitmap = i.getParcelableExtra("image");
        list = addpage.arrayList;
        nameLs = addpage.nameLs;


        mTitle.setText(title);
        mDes.setText(des);
        mDate.setText(date);
        mQuantity.setText(quan);
        mimageView.setImageBitmap(bitmap);

        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPay();
            }
        });

    }
    public void storecart(View view){
        if(!mTitle.getText().toString().isEmpty() &&  mimageView.getDrawable()!= null  && mQuantity != null) {

            db.storeCart(new Item(mTitle.getText().toString(),mQuantity.getText().toString(),bitmap));
            Toast.makeText(addtoCart.this,"Add to Cart Successfully",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(addtoCart.this, homepage.class);
            startActivity(i);
            finish();
        }
        else{
            Toast.makeText(addtoCart.this,"Missing informations",Toast.LENGTH_SHORT).show();
        }
    }
    private void processPay() {

        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal("1"),"AUD",
                "Free food",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent i = new Intent(this, PaymentActivity.class);
        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        i.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(i,PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PAYPAL_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation != null){
                    try{
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this,PaymentDetails.class)
                                .putExtra("PaymentDetail",paymentDetails)
                                .putExtra("PaymentAmount",amount)
                        );
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                else if(resultCode == Activity.RESULT_CANCELED)
                    Toast.makeText(this,"Payment cancel", Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
                Toast.makeText(this,"Invalid", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        for(int i = 0; i < list.size(); i ++){
            mMap.addMarker(new MarkerOptions().position(list.get(i)).title(nameLs.get(i)));
            moveCamera(list.get(i),DEFAULT_ZOOM);

        }
    }
    private void moveCamera(LatLng latLng, float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }
}