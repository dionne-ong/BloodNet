package edu.mobapde.bloodnet;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AfterRequirements extends AppCompatActivity {
    Button button;
    ImageView ivPoster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_requirements);
        button = (Button) findViewById(R.id.b_ok);
        ivPoster = (ImageView) findViewById(R.id.iv_poster);

        Intent in= getIntent();
        final Bundle b = in.getExtras();

        if(b.getBoolean("legible")){
            ivPoster.setImageResource(R.drawable.thankyou);
        }else{
            ivPoster.setImageResource(R.drawable.nottoday);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                if(b.getBoolean("legible")){
                    i.setClass(getBaseContext(), FinishedPledgeActivity.class);
                }else{
                    i.setClass(getBaseContext(), MyPledgeActivity.class);
                }
                startActivity(i);

            }
        });
    }
}
