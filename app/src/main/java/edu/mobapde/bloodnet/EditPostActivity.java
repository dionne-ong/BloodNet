package edu.mobapde.bloodnet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Luisa Gilig on 18/03/2017.
 */

public class EditPostActivity extends AppCompatActivity{

    Button btnSave, btnCancel;
    TextView tvName, tvLocation, tvContactNumber, tvBloodType, tvQuantity, tvPhoto, tvAddress, tvSliderText;
    ImageView imgBarPicture;
    FloatingActionButton fab;
    SlideButton sb;
    public static final int REQUEST_CODE_TAKE_PHOTO = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        sb = (SlideButton) findViewById(R.id.unlockButton);
        tvSliderText = (TextView) findViewById(R.id.slider_text);
        btnCancel = (Button) findViewById(R.id.b_cancel);
        btnSave = (Button) findViewById(R.id.b_submit);
        tvName = (TextView) findViewById(R.id.tv_content_name);
        tvLocation = (TextView) findViewById(R.id.tv_content_location);
        tvContactNumber = (TextView) findViewById(R.id.tv_content_num);
        tvBloodType = (TextView) findViewById(R.id.tv_content_btype);
        tvQuantity = (TextView) findViewById(R.id.tv_content_quantity);
        tvAddress = (TextView) findViewById(R.id.tv_content_address);

        sb.setVisibility(View.GONE);
        tvSliderText.setVisibility(View.GONE);

        //whatever is in the database
        tvName.setHint("Luisa Gilig");
        tvLocation.setHint("Hospital A");
        tvAddress.setHint("2191 Something Street, Manila City");
        tvContactNumber.setHint("09172134385");
        tvBloodType.setHint("O+");
        tvQuantity.setHint("2");
        btnCancel.setText("Cancel");
        btnSave.setText("Save");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
