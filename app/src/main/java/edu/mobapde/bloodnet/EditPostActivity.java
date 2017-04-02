package edu.mobapde.bloodnet;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.mobapde.bloodnet.DBObjects.DBOPost;
import edu.mobapde.bloodnet.DBObjects.DBOUser;
import edu.mobapde.bloodnet.fragments.DatePickerFragment;
import edu.mobapde.bloodnet.models.User;
import edu.mobapde.bloodnet.models.posts.Post;

import static android.R.attr.format;

/**
 * Created by Luisa Gilig on 18/03/2017.
 */

public class EditPostActivity extends AppCompatActivity {


    Button btnSave, btnCancel;
    EditText etName, etLocation, etContactNumber, etQuantity, tvPhoto, etAddress;
    String key;
    Spinner spBType;
    ArrayAdapter<CharSequence> adapterB;
    ImageView imgBarPicture;
    FloatingActionButton fab;
    FirebaseAuth auth;
    DatabaseReference postRef;
    TextView tvNumOfPledges;

    public static final int REQUEST_CODE_TAKE_PHOTO = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnSave = (Button) findViewById(R.id.b_submit);
        btnCancel = (Button) findViewById(R.id.b_cancel);
        btnSave.setText("Save");
        btnCancel.setText("Cancel");
        imgBarPicture = (ImageView) findViewById(R.id.img_bar_picture);
        fab = (FloatingActionButton) findViewById(R.id.fab_edit_profile);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            fab.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
        auth = FirebaseAuth.getInstance();
        postRef = FirebaseDatabase.getInstance().getReference()
                .child(DBOPost.POST_REF);


        spBType = (Spinner) findViewById(R.id.s_bloodtype);
        adapterB = ArrayAdapter.createFromResource(this,
                R.array.bloodtype, android.R.layout.simple_spinner_item);
        adapterB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBType.setAdapter(adapterB);


        etName = (EditText) findViewById(R.id.tv_content_name);
        etContactNumber = (EditText) findViewById(R.id.tv_content_num);
        etLocation = (EditText) findViewById(R.id.tv_content_location);
        etAddress = (EditText) findViewById(R.id.tv_content_address);
        etQuantity = (EditText) findViewById(R.id.tv_content_quantity);
        tvNumOfPledges = (TextView) findViewById(R.id.tv_posteddate);

        Intent i = getIntent();
        key = i.getStringExtra(DBOPost.EXTRA_POST_ID);


        postRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Post p = dataSnapshot.getValue(Post.class);
                etName.setText(p.getPatientName());
                etContactNumber.setText(p.getContactNum());
                etLocation.setText(p.getHospitalName());
                etAddress.setText(p.getHospitalAddress());
                for(int i=0; i<adapterB.getCount(); i++){
                    if(adapterB.getItem(i).equals(p.getBloodType())){
                        spBType.setSelection(i);
                    }
                }
                etQuantity.setText(p.getNeededBags()+"");
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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post newData = new Post();
                newData.setPatientName(etName.getText().toString());
                newData.setBloodType(spBType.getSelectedItem().toString());
                newData.setContactNum(etContactNumber.getText().toString());
                newData.setHospitalName(etLocation.getText().toString());
                newData.setHospitalAddress(etAddress.getText().toString());
                newData.setNeededBags(Integer.parseInt(etQuantity.getText().toString()));
                Log.i("DB", "[FIREBASE] "+newData.toString());
                postRef.child(key).setValue(newData, new DatabaseReference.CompletionListener() {
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

        btnCancel.setOnClickListener(new View.OnClickListener() {
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

}

