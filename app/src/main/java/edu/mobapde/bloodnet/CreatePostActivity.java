package edu.mobapde.bloodnet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import edu.mobapde.bloodnet.DBObjects.DBOPost;
import edu.mobapde.bloodnet.DBObjects.DBOUser;
import edu.mobapde.bloodnet.models.posts.Post;


public class CreatePostActivity extends AppCompatActivity {

    Button btnCreate, btnCancel;
    EditText etName, etLocation, etContactNumber, etQuantity, tvPhoto, etAddress;
    Spinner spBType;
    ArrayAdapter<CharSequence> adapterA;
    ImageView imgBarPicture;
    FloatingActionButton fab;
    public static final int REQUEST_CODE_TAKE_PHOTO = 101;

    DatabaseReference postRef, Ref, reference;
    String key;
    Post p;
    HashMap<String, Boolean> map;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        btnCancel = (Button) findViewById(R.id.b_cancel);
        btnCreate = (Button) findViewById(R.id.b_submit);
        etName = (EditText) findViewById(R.id.tv_content_name);
        etLocation = (EditText) findViewById(R.id.tv_content_location);
        etContactNumber = (EditText) findViewById(R.id.tv_content_num);
        etQuantity = (EditText) findViewById(R.id.tv_content_quantity);
        etAddress = (EditText) findViewById(R.id.tv_content_address);

        Ref = FirebaseDatabase.getInstance().getReference();
        postRef = Ref.child(DBOPost.POST_REF);


        etName.setHint("Patient Name");
        etLocation.setHint("Name of Hospital");
        etContactNumber.setHint("Contact Number");
        etQuantity.setHint("Number of Bags Needed");
        etAddress.setHint("Hospital Address");
        btnCancel.setText("Cancel");
        btnCreate.setText("Create");


        spBType = (Spinner) findViewById(R.id.s_bloodtype);
        adapterA = ArrayAdapter.createFromResource(this,
                R.array.bloodtype, android.R.layout.simple_spinner_item);
        adapterA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBType.setAdapter(adapterA);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                key = postRef.push().getKey();

                p = new Post();
                p.setPatientName(etName.getText().toString());
                p.setContactNum(etContactNumber.getText().toString());
                p.setDatePosted(new Date().getTime());
                p.setBloodType(spBType.getSelectedItem().toString());
                p.setHospitalName(etLocation.getText().toString());
                p.setHospitalAddress(etAddress.getText().toString());
                p.setNeededBags(Integer.parseInt(etQuantity.getText().toString()));
                p.setPledgedBags(0);
                p.setId(key);
                p.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());

                postRef.child(key).setValue(p).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {


                            map = new HashMap<String, Boolean>();
                            Ref = FirebaseDatabase.getInstance().getReference().child(p.getBloodType());
                            Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    map = (HashMap<String, Boolean>) dataSnapshot.getValue();
                                    if (map == null) {
                                        map = new HashMap<String, Boolean>();
                                    }

                                    map.put(p.getId(), true);
                                    Ref.setValue(map);

                                    DatabaseReference userPost = FirebaseDatabase.getInstance().getReference().child(DBOUser.REF_USER_POST);
                                    reference = userPost.child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            HashMap<String, Boolean> newMap = (HashMap<String, Boolean>) dataSnapshot.getValue();
                                            if (newMap == null) {
                                                newMap = new HashMap<String, Boolean>();
                                            }
                                            newMap.put(p.getId(), true);
                                            reference.setValue(newMap);


                                            Intent i = new Intent();
                                            i.putExtra(DBOPost.EXTRA_POST_ID, key);
                                            i.setClass(getBaseContext(), MyPostActivity.class);
                                            startActivity(i);
                                            finish();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            })
                            ;

                        } else {
                            Toast.makeText(getBaseContext(), "Create post failed.", Toast.LENGTH_SHORT);
                        }
                    }
                });


            }
        });

        imgBarPicture = (ImageView) findViewById(R.id.img_bar_picture_post);

        fab = (FloatingActionButton) findViewById(R.id.fab_edit_profile);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            fab.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture(view);
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

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
    }

}
