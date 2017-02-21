package drawoverpicture.shashankmahajan.drawoverpicture.ui.adapter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.byox.drawview.enums.DrawingCapture;
import com.byox.drawview.enums.DrawingMode;
import com.byox.drawview.views.DrawView;

import drawoverpicture.shashankmahajan.drawoverpicture.R;
import drawoverpicture.shashankmahajan.drawoverpicture.dialogs.DrawAttribsDialog;
import drawoverpicture.shashankmahajan.drawoverpicture.dialogs.SaveBitmapDialog;
import drawoverpicture.shashankmahajan.drawoverpicture.dialogs.SelectChoiceDialog;

public class finalLayout extends AppCompatActivity {

    private Toolbar mToolbar;
    private DrawView mDrawView;

    private View mFadeView;


    private MenuItem mMenuItemRedo;
    private MenuItem mMenuItemUndo;

    private FloatingActionButton fabSend;
    private ImageView imageSave;
    private TextView textviewTextAa;
    private ImageView penAttributes;
    private TextView textviewClear;

    private final int STORAGE_PERMISSIONS = 1000;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //make it full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams
                .FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_final_layout);

        fabSend = (FloatingActionButton) findViewById(R.id.fab_actions_send);
        imageSave = (ImageView) findViewById(R.id.imageview_save);
        textviewTextAa = (TextView) findViewById(R.id.tv_text_Aa);
        penAttributes = (ImageView) findViewById(R.id.pen);
        textviewClear = (TextView) findViewById(R.id.tv_clear);


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setupDrawView();


        fabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
            }
        });


        imageSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
            }
        });

        textviewClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDraw();
            }
        });

        textviewTextAa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelectChoiceDialog selectChoiceDialog = null;
                mDrawView.setDrawingMode(DrawingMode.TEXT);

                selectChoiceDialog.show(getSupportFragmentManager(), "choiceDialog");
            }
        });

        penAttributes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDrawAttribs();
            }
        });
    }

    private void clearDraw(){
        mDrawView.restartDrawing();
    }


    private void setupDrawView() {
        mDrawView = (DrawView) findViewById(R.id.draw_view);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mMenuItemUndo = menu.getItem(0);
        mMenuItemRedo = menu.getItem(1);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case STORAGE_PERMISSIONS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            saveDraw();
                        }
                    }, 600);
                }
                break;
        }
    }
    private void saveDraw(){
        SaveBitmapDialog saveBitmapDialog = SaveBitmapDialog.newInstance();
        saveBitmapDialog.setPreviewBitmap((Bitmap) mDrawView.createCapture(DrawingCapture.BITMAP));
        saveBitmapDialog.setOnSaveBitmapListener(new SaveBitmapDialog.OnSaveBitmapListener() {
            @Override
            public void onSaveBitmapCompleted() {
                Snackbar.make(fabSend, "Capture saved succesfully!", 2000).show();
            }

            @Override
            public void onSaveBitmapCanceled() {
                Snackbar.make(fabSend, "Capture saved canceled.", 2000).show();
            }
        });
        saveBitmapDialog.show(getSupportFragmentManager(), "saveBitmap");
    }


    private void changeDrawAttribs(){
        DrawAttribsDialog drawAttribsDialog = DrawAttribsDialog.newInstance();
        drawAttribsDialog.setPaint(mDrawView.getCurrentPaintParams());
        drawAttribsDialog.setOnCustomViewDialogListener(new DrawAttribsDialog.OnCustomViewDialogListener() {
            @Override
            public void onRefreshPaint(Paint newPaint) {
                mDrawView.setDrawColor(newPaint.getColor())
                        .setPaintStyle(newPaint.getStyle())
                        .setDither(newPaint.isDither())
                        .setDrawWidth((int) newPaint.getStrokeWidth())
                        .setDrawAlpha(newPaint.getAlpha())
                        .setAntiAlias(newPaint.isAntiAlias())
                        .setLineCap(newPaint.getStrokeCap())
                        .setFontFamily(newPaint.getTypeface())
                        .setFontSize(newPaint.getTextSize());

//                If you prefer, you can easily refresh new attributes using this method
//                mDrawView.refreshAttributes(newPaint);
            }
        });
        drawAttribsDialog.show(getSupportFragmentManager(), "drawAttribs");
    }

    private void requestPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(finalLayout.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(finalLayout.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(finalLayout.this,
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        STORAGE_PERMISSIONS);
            } else {
                saveDraw();
            }
        } else {
            saveDraw();
        }
    }
}
