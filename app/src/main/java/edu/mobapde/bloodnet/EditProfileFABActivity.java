package edu.mobapde.bloodnet;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.antonioleiva.materializeyourapp.widgets.SquareImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.mobapde.bloodnet.DBObjects.DBOUser;
import edu.mobapde.bloodnet.fragments.DatePickerFragment;
import edu.mobapde.bloodnet.models.User;

public class EditProfileFABActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener  {

    ImageView imgBarPicture;
    FloatingActionButton fab;
    Button bSave, bCancel;
    FirebaseAuth auth;
    DatabaseReference userRef;
    EditText etName, etEmail, etContact, etBirthdate;
    Spinner spGender, spBType;
    ArrayAdapter<CharSequence> adapter, adapterB;
    Calendar birthdate;
    SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");
    public static final int REQUEST_CODE_TAKE_PHOTO = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_fab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bSave = (Button) findViewById(R.id.btn_save);
        bCancel = (Button) findViewById(R.id.btn_cancel);
        imgBarPicture = (ImageView) findViewById(R.id.img_bar_picture);

        fab = (FloatingActionButton) findViewById(R.id.fab_edit_profile);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            fab.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
        auth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference()
                    .child(DBOUser.REF_USER)
                    .child(auth.getCurrentUser().getUid());

        spGender = (Spinner) findViewById(R.id.s_gender);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(adapter);

        spBType = (Spinner) findViewById(R.id.s_bloodtype);
        adapterB = ArrayAdapter.createFromResource(this,
                R.array.bloodtype, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBType.setAdapter(adapterB);


        etName = (EditText) findViewById(R.id.tv_content_name);
        etEmail = (EditText) findViewById(R.id.tv_content_email);
        etContact = (EditText) findViewById(R.id.tv_content_num);
        etBirthdate = (EditText) findViewById(R.id.tv_content_bday);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);
                etName.setText(u.getName());
                etEmail.setText(auth.getCurrentUser().getEmail());
                etContact.setText(u.getContactNum());
                String date = "";
                if(u.getBirthdate()!=-1){
                    birthdate = new GregorianCalendar();
                    birthdate.setTimeInMillis(u.getBirthdate());
                    date = format.format(birthdate.getTime());
                }
                etBirthdate.setText(date);
                for(int i=0; i<adapter.getCount(); i++){
                    if(adapter.getItem(i).equals(u.getGender())){
                        spGender.setSelection(i);
                    }
                }
                for(int i=0; i<adapterB.getCount(); i++){
                    if(adapterB.getItem(i).equals(u.getBloodType())){
                        spBType.setSelection(i);
                    }
                }
               // etGender.setText(u.getGender());
               // etBType.setText(u.getBloodType());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture(view);
            }
        });

        etBirthdate = ((EditText) findViewById(R.id.tv_content_bday));
        etBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User newData = new User();
                newData.setName(etName.getText().toString());
                if(birthdate!=null) {
                    newData.setBirthdate(birthdate.getTimeInMillis());
                }
                newData.setBloodType(spBType.getSelectedItem().toString());
                newData.setContactNum(etContact.getText().toString());
                newData.setGender(spGender.getSelectedItem().toString());
                Log.i("DB", "[FIREBASE] "+newData.toString());
                userRef.setValue(newData, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError!=null)
                            Log.e("DB", "[FIREBASE] Error updating data. "+databaseError.getMessage());
                        else{
                            Log.i("DB", "[FIREBASE] Updates data.");
                        }
                    }
                });
                finish();
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                fab.setEnabled(true);
            }
        }
    }

    Uri file;

    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                imgBarPicture.setImageURI(file);
            }
        }
    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //do some stuff for example write on log and update TextField on activity
        Log.w("DatePicker","Date = " + year);
        birthdate = new GregorianCalendar(year, month, day);
        etBirthdate.setText(format.format(birthdate.getTime()));
        etBirthdate.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.textPrimaryColor));
    }
}

