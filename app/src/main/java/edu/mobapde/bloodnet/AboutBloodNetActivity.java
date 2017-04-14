package edu.mobapde.bloodnet;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutBloodNetActivity extends AppCompatActivity {

    Toolbar tbEdit;
    TextView tvCHospital, tvCBloodBag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_blood_net);

        TextView tv=(TextView)findViewById(R.id.tv_app_name);
        tvCHospital = (TextView) findViewById(R.id.tv_credithospital);
        tvCHospital.setMovementMethod(LinkMovementMethod.getInstance());
        tvCBloodBag = (TextView) findViewById(R.id.tv_creditblood);
        tvCBloodBag.setMovementMethod(LinkMovementMethod.getInstance());
        Typeface face= Typeface.createFromAsset(getAssets(),"fonts/Raleway Thin.ttf");
        tv.setTypeface(face);

        tbEdit = (Toolbar) findViewById(R.id.tb_edit);
        setSupportActionBar(tbEdit);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                // User chose the "Save" item, save the data...
                new AlertDialog.Builder(this)
                        .setTitle("Save Changes")
                        .setMessage("Are you sure you want to save all changes?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(R.drawable.ic_person_black_24dp)
                        .show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
