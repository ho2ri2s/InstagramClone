package com.kurus.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Button btnSave, btngetAllData, btnTransition;
    private EditText edtName, edtPunchSpeed, edtPunchPower, edtKickSpeed, edtKickPower;
    private TextView txtGetData;

    private String allKickBoxers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(SignUp.this);
        btngetAllData = findViewById(R.id.btnGetAllData);
        btnTransition = findViewById(R.id.btnNextActivity);

        edtName = findViewById(R.id.edtName);
        edtPunchSpeed = findViewById(R.id.edtPunchSpeed);
        edtPunchPower = findViewById(R.id.edtPunchPower);
        edtKickSpeed = findViewById(R.id.edtKickSpeed);
        edtKickPower = findViewById(R.id.edtKickPower);

        txtGetData = findViewById(R.id.txtGetData);
        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");
                parseQuery.getInBackground("yfDGbKqAqd", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {

                        if(object != null && e == null){
                            txtGetData.setText(object.get("name") + "-" + "Punch Power: " + object.get("punch_power"));
                        }

                    }
                });
            }
        });

        btngetAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                allKickBoxers = "";

                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");

                queryAll.whereGreaterThan("punch_power", 1500);
                queryAll.whereGreaterThanOrEqualTo("punch_power", 2000);
                queryAll.setLimit(1);

                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if(e == null){

                            if(objects.size() > 0){

                                for(ParseObject kickBoxer : objects){
                                    allKickBoxers = allKickBoxers + kickBoxer.get("name") + "\n";
                                }

                                FancyToast.makeText(SignUp.this, allKickBoxers, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();

                            }else
                                FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

                        }

                    }
                });

            }
        });

        btnTransition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new  Intent(SignUp.this, SignUpLoginActivity.class);
                startActivity(intent);

            }
        });

    }


    @Override
    public void onClick(View v) {
        try {
            final ParseObject kickBoxer = new ParseObject("KickBoxer");
            kickBoxer.put("name", edtName.getText().toString());
            kickBoxer.put("punch_speed", Integer.parseInt(edtPunchSpeed.getText().toString()));
            kickBoxer.put("punch_power", Integer.parseInt(edtPunchPower.getText().toString()));
            kickBoxer.put("kick_speed", Integer.parseInt(edtKickSpeed.getText().toString()));
            kickBoxer.put("kick_power", Integer.parseInt(edtKickPower.getText().toString()));
            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        FancyToast.makeText(SignUp.this, kickBoxer.get("name") + " is saved to server", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    } else {
                        FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    }
                }
            });
        } catch (Exception e){

            FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();


        }
    }
}
