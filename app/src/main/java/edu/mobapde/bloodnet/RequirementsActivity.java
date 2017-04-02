package edu.mobapde.bloodnet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import edu.mobapde.bloodnet.models.pledges.Pledge;

public class RequirementsActivity extends AppCompatActivity {

    Button btn_Ok, btn_Cancel;
    Toolbar tbEdit;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6;
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

        checkBox1 = (CheckBox) findViewById(R.id.checkBox);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) findViewById(R.id.checkBox4);
        checkBox5 = (CheckBox) findViewById(R.id.checkBox5);
        checkBox6 = (CheckBox) findViewById(R.id.checkBox6);


        btn_Ok = (Button) findViewById(R.id.b_submit);
        btn_Cancel = (Button) findViewById(R.id.b_cancel);

        btn_Ok.setText("Finish");
        btn_Cancel.setText("Cancel");

        btn_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked() && checkBox4.isChecked() && checkBox5.isChecked() && checkBox6.isChecked()){
                    pledge.setDonated(true);
                    Intent i = new Intent();
                    i.putExtra("legible", true);
                    i.setClass(getBaseContext(), AfterRequirements.class);
                    startActivity(i);
                    finish();

                }else{
                    Intent i = new Intent();
                    i.putExtra("legible", false);
                    i.setClass(getBaseContext(), AfterRequirements.class);
                    startActivity(i);
                    finish();
                }
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
