package edu.mobapde.bloodnet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import edu.mobapde.bloodnet.models.Pledge;

public class RequirementsActivity extends AppCompatActivity {

    Button btn_Ok, btn_Cancel;
    Toolbar tbEdit;

    Pledge pledge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirements);
        tbEdit = (Toolbar) findViewById(R.id.tb_edit);
        setSupportActionBar(tbEdit);
        getSupportActionBar().setTitle("Requirements");

        //pledge = getExtra();
        pledge = new Pledge(1,2, false);



        btn_Ok = (Button) findViewById(R.id.b_submit);
        btn_Cancel = (Button) findViewById(R.id.b_cancel);

        btn_Ok.setText("Ok");
        btn_Cancel.setText("Cancel");

        btn_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pledge.setDonated(true);
                finish();
            }
        });

        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_profile_menu, menu);
        return true;
    }

}
