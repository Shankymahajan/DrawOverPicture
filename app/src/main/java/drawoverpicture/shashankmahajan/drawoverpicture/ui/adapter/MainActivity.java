package drawoverpicture.shashankmahajan.drawoverpicture.ui.adapter;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.OutputStream;

import drawoverpicture.shashankmahajan.drawoverpicture.R;
import drawoverpicture.shashankmahajan.drawoverpicture.dialogs.RequestTextDialog;
import drawoverpicture.shashankmahajan.drawoverpicture.utils.FontProvider;
import drawoverpicture.shashankmahajan.drawoverpicture.viewmodel.Font;
import drawoverpicture.shashankmahajan.drawoverpicture.viewmodel.Layer;
import drawoverpicture.shashankmahajan.drawoverpicture.viewmodel.TextLayer;
import drawoverpicture.shashankmahajan.drawoverpicture.widget.MotionView;
import drawoverpicture.shashankmahajan.drawoverpicture.widget.entity.ImageEntity;
import drawoverpicture.shashankmahajan.drawoverpicture.widget.entity.TextEntity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    MyImageView chooseImageView;
    ImageView choosePicture;
    ImageView savePicture;
    TextView addTextToImage;

    Canvas canvas;
    FloatingActionButton fabSend;

    Bitmap bmp;
    Bitmap alteredBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        chooseImageView= (MyImageView) this.findViewById(R.id.choose_image_view);


        choosePicture = (ImageView) findViewById(R.id.image_view_add_picture);
        savePicture = (ImageView) findViewById(R.id.imageview_save);

        addTextToImage = (TextView) findViewById(R.id.tv_text_Aa);

        // fabSend = (FloatingActionButton) findViewById(R.id.fab_actions_send);



        //Save Image
        savePicture.setOnClickListener(this);


        choosePicture.setOnClickListener(this);
        //fabSend.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == choosePicture) {
            Intent choosePictureIntent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(choosePictureIntent, 0);
        }
        else if (v == savePicture) {
            if (alteredBitmap != null) {
                ContentValues contentValues = new ContentValues(3);
                contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "Draw On Me");

                Uri imageFileUri = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                try {
                    OutputStream imageFileOS = getContentResolver()
                            .openOutputStream(imageFileUri);
                    alteredBitmap
                            .compress(Bitmap.CompressFormat.JPEG, 90, imageFileOS);
                    Snackbar.make(savePicture, "Capture saved succesfully!", 2000).show();

                } catch (Exception e) {
                    Log.v("EXCEPTION", e.getMessage());
                }
            }
        }
        else if(v == addTextToImage){
            onRequestText();
        }

        //else if(v == fabSend){
//            paint.setColor(Color.BLUE);
//            paint.setStrokeWidth(10); }
    }


        @Override
        public void onRequestText() {
            RequestTextDialog requestTextDialog =
                    RequestTextDialog.newInstance("");
            requestTextDialog.setOnRequestTextListener(new RequestTextDialog.OnRequestTextListener() {
                @Override
                public void onRequestTextConfirmed(String requestedText) {
                    chooseImageView.refreshLastText(requestedText);
                }

                @Override
                public void onRequestTextCancelled() {
                    chooseImageView.cancelTextRequest();
                }

            requestTextDialog.show(getSupportFragmentManager(), "requestText");
        });
        }

    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_OK) {
            Uri imageFileUri = intent.getData();
            try {
                BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
                bmpFactoryOptions.inJustDecodeBounds = true;
                bmp = BitmapFactory
                        .decodeStream(
                                getContentResolver().openInputStream(
                                        imageFileUri), null, bmpFactoryOptions);

                bmpFactoryOptions.inJustDecodeBounds = false;
                bmp = BitmapFactory
                        .decodeStream(
                                getContentResolver().openInputStream(
                                        imageFileUri), null, bmpFactoryOptions);

                alteredBitmap = Bitmap.createBitmap(bmp.getWidth(),
                        bmp.getHeight(), bmp.getConfig());


                chooseImageView.setNewImage(alteredBitmap, bmp);
            } catch (Exception e) {
                Log.v("ERROR", e.toString());
            }
        }
    }



}

