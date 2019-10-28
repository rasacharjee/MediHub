package com.gamecodeschool.medihub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Button Emergency,ambulance;
    private Button cameraButton;
    private final static int REQUEST_CAMERA_CAPTURE = 124;
    private FirebaseVisionTextRecognizer textRecognizer;
    FirebaseVisionImage image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        Emergency=findViewById(R.id.btnemergency);
        ambulance=findViewById(R.id.btnambulance);
        cameraButton = findViewById(R.id.camera_button);

        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentd=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:102"));
                startActivity(intentd);
            }
        });

        Emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Webify.class);
                startActivity(intent);
            }
        });


        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(takePictureIntent, REQUEST_CAMERA_CAPTURE);
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CAMERA_CAPTURE&& resultCode==RESULT_OK)
        {
            Bundle extras= data.getExtras();
            if(extras!=null)
            {
                Bitmap bitmap=(Bitmap) extras.get("data");
                recognizeMyText(bitmap);
            }


        }
    }

    private void recognizeMyText(Bitmap bitmap) {
        try {
            image= FirebaseVisionImage.fromBitmap(bitmap);
            textRecognizer=FirebaseVision
                           .getInstance()
                           .getOnDeviceTextRecognizer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        textRecognizer.processImage(image)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {
                        String result=firebaseVisionText.getText();
                        if(result.isEmpty())
                        {
                            Toast.makeText(MainActivity.this,"No Text Detected",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Intent intent=new Intent(MainActivity.this,Result.class);
                            intent.putExtra(TextRecognization.RESULT_TEXT,result);
                            startActivity(intent);
                        }


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Could not recognize image", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}



