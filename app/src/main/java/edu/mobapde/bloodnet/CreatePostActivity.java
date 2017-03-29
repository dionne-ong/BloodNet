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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.mobapde.bloodnet.DBObjects.DBOPost;
import edu.mobapde.bloodnet.models.Post;

public class CreatePostActivity extends AppCompatActivity {

    Button btnCreate, btnCancel;
    EditText tvName, tvLocation, tvContactNumber, tvBloodType, tvQuantity, tvPhoto, tvAddress;

    ImageView imgBarPicture;
    FloatingActionButton fab;
    public static final int REQUEST_CODE_TAKE_PHOTO = 101;

    DatabaseReference postRef;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        btnCancel = (Button) findViewById(R.id.b_cancel);
        btnCreate = (Button) findViewById(R.id.b_submit);
        tvName = (EditText) findViewById(R.id.tv_content_name);
        tvLocation = (EditText) findViewById(R.id.tv_content_location);
        tvContactNumber = (EditText) findViewById(R.id.tv_content_num);
        tvBloodType = (EditText) findViewById(R.id.tv_content_btype);
        tvQuantity = (EditText) findViewById(R.id.tv_content_quantity);
        tvAddress = (EditText) findViewById(R.id.tv_content_address);

        postRef = FirebaseDatabase.getInstance().getReference().child(DBOPost.POST_REF);

        tvName.setHint("Patient Name");
        tvLocation.setHint("Name of Hospital");
        tvContactNumber.setHint("Contact Number");
        tvBloodType.setHint("Blood Type");
        tvQuantity.setHint("Number of Bags Needed");
        tvAddress.setHint("Hospital Address");
        btnCancel.setText("Cancel");
        btnCreate.setText("Create");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                key  = postRef.push().getKey();
                Post p = new Post();
                p.setBloodType(tvBloodType.getText().toString());
                p.setContactNum(tvContactNumber.getText().toString());
                p.setDatePosted(new Date().getTime());
                p.setHospitalName(tvLocation.getText().toString());
                p.setHospitalAddress(tvAddress.getText().toString());
                p.setNeededBags(Integer.parseInt(tvQuantity.getText().toString()));
                p.setPledgedBags(0);
                p.setId(key);
                p.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());

                postRef.child(key).setValue(p).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent i = new Intent();
                            i.putExtra(DBOPost.EXTRA_POST_ID, key);
                            i.setClass(getBaseContext(), MyPostActivity.class);
                            startActivity(i);
                            finish();
                        }else{
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
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
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

}
