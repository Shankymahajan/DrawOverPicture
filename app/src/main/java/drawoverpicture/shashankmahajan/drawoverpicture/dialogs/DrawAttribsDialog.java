package drawoverpicture.shashankmahajan.drawoverpicture.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import drawoverpicture.shashankmahajan.drawoverpicture.R;

/**
 * Created by shashankmahajan on 17/02/17.
 */

public class DrawAttribsDialog extends DialogFragment {


    // LISTENER
    private OnCustomViewDialogListener onCustomViewDialogListener;

    // VARS
    private Paint mPaint;


    public DrawAttribsDialog() {
    }

    public static DrawAttribsDialog newInstance() {
        return new DrawAttribsDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = LayoutInflater.from(getContext())
                .inflate(R.layout.layout_draw_attribs, null);

        final View previewColor = view.findViewById(R.id.preview_color);
        final AppCompatSeekBar seekBarRed = (AppCompatSeekBar) view.findViewById(R.id.acsb_red);
        final AppCompatSeekBar seekBarGreen = (AppCompatSeekBar) view.findViewById(R.id.acsb_green);
        final AppCompatSeekBar seekBarBlue = (AppCompatSeekBar) view.findViewById(R.id.acsb_blue);
        final TextView textViewRedValue = (TextView) view.findViewById(R.id.tv_current_red);
        final TextView textViewGreenValue = (TextView) view.findViewById(R.id.tv_current_green);
        final TextView textViewBlueValue = (TextView) view.findViewById(R.id.tv_current_blue);
        AppCompatSeekBar seekBarStrokeWidth = (AppCompatSeekBar) view.findViewById(R.id.acsb_stroke_width);
        final TextView textViewStrokeWidth = (TextView) view.findViewById(R.id.tv_stroke_width);
        AppCompatSeekBar seekBarOpacity = (AppCompatSeekBar) view.findViewById(R.id.acsb_opacity);
        final TextView textViewOpacity = (TextView) view.findViewById(R.id.tv_opacity);
        final AppCompatSeekBar seekBarFontSize = (AppCompatSeekBar) view.findViewById(R.id.acsb_font_size);
        final TextView textViewFontSize = (TextView) view.findViewById(R.id.tv_font_size);
        AppCompatCheckBox appCompatCheckBoxAntiAlias = (AppCompatCheckBox) view.findViewById(R.id.chb_anti_alias);
        AppCompatCheckBox appCompatCheckBoxDither = (AppCompatCheckBox) view.findViewById(R.id.chb_dither);
        AppCompatRadioButton appCompatRadioButtonFill = (AppCompatRadioButton) view.findViewById(R.id.rb_fill);
        AppCompatRadioButton appCompatRadioButtonFillStroke = (AppCompatRadioButton) view.findViewById(R.id.rb_fill_stroke);
        AppCompatRadioButton appCompatRadioButtonStroke = (AppCompatRadioButton) view.findViewById(R.id.rb_stroke);
        AppCompatRadioButton appCompatRadioButtonButt = (AppCompatRadioButton) view.findViewById(R.id.rb_butt);
        AppCompatRadioButton appCompatRadioButtonRound = (AppCompatRadioButton) view.findViewById(R.id.rb_round);
        AppCompatRadioButton appCompatRadioButtonSquare = (AppCompatRadioButton) view.findViewById(R.id.rb_square);
        AppCompatRadioButton appCompatRadioButtonDefault = (AppCompatRadioButton) view.findViewById(R.id.rb_default);
        AppCompatRadioButton appCompatRadioButtonMonospace = (AppCompatRadioButton) view.findViewById(R.id.rb_monospace);
        AppCompatRadioButton appCompatRadioButtonSansSerif = (AppCompatRadioButton) view.findViewById(R.id.rb_sans_serif);
        AppCompatRadioButton appCompatRadioButtonSerif = (AppCompatRadioButton) view.findViewById(R.id.rb_serif);


        //Show RGB color preview
        previewColor.setBackgroundColor(mPaint.getColor());


        //RGB color seekbar
        seekBarRed.setProgress(Color.red(mPaint.getColor()));
        seekBarGreen.setProgress(Color.green(mPaint.getColor()));
        seekBarBlue.setProgress(Color.blue(mPaint.getColor()));

        //Stroke width
        seekBarStrokeWidth.setProgress((int) mPaint.getStrokeWidth());

        //Opacity
        seekBarOpacity.setProgress((int) mPaint.getAlpha());

        //Font Size
        seekBarFontSize.setProgress((int) mPaint.getTextSize());


        //set TextView of RGB
        textViewRedValue.setText(String.valueOf(Color.red(mPaint.getColor())));
        textViewGreenValue.setText(String.valueOf(Color.green(mPaint.getColor())));
        textViewBlueValue.setText(String.valueOf(Color.blue(mPaint.getColor())));


        //set TextView of stroke
        textViewStrokeWidth.setText(getContext().getResources().getString(R.string.stroke_width, (int) mPaint.getStrokeWidth()));

        //set TextView of Opacity
        textViewOpacity.setText(getContext().getResources().getString(R.string.opacity, (int) mPaint.getAlpha()));

        //set TextView of Font Size
        textViewFontSize.setText(getContext().getResources().getString(R.string.font_size, 12));


        //checked by  default -Anti Alias ,Dither
        appCompatCheckBoxAntiAlias.setChecked(mPaint.isAntiAlias());
        appCompatCheckBoxDither.setChecked(mPaint.isDither());



        //check radio buttons for PAINT STYLE
        appCompatRadioButtonFill.setChecked(mPaint.getStyle() == Paint.Style.FILL);
        appCompatRadioButtonFillStroke.setChecked(mPaint.getStyle() == Paint.Style.FILL_AND_STROKE);
        appCompatRadioButtonStroke.setChecked(mPaint.getStyle() == Paint.Style.STROKE);



        //Check radio buttons for PAINT CAP
        appCompatRadioButtonButt.setChecked(mPaint.getStrokeCap() == Paint.Cap.BUTT);
        appCompatRadioButtonRound.setChecked(mPaint.getStrokeCap() == Paint.Cap.ROUND);
        appCompatRadioButtonSquare.setChecked(mPaint.getStrokeCap() == Paint.Cap.SQUARE);


        ////Check radio buttons for FONT
        appCompatRadioButtonDefault.setChecked(mPaint.getTypeface() == Typeface.DEFAULT);
        appCompatRadioButtonMonospace.setChecked(mPaint.getTypeface() == Typeface.MONOSPACE);
        appCompatRadioButtonSansSerif.setChecked(mPaint.getTypeface() == Typeface.SANS_SERIF);
        appCompatRadioButtonSerif.setChecked(mPaint.getTypeface() == Typeface.SERIF);


       //Mixing all 3 colors for preview
        AppCompatSeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mPaint.setColor(Color.rgb(seekBarRed.getProgress(), seekBarGreen.getProgress(), seekBarBlue.getProgress()));
                previewColor.setBackgroundColor(mPaint.getColor());


                //set value in textview for RGB
                textViewRedValue.setText(String.valueOf(seekBarRed.getProgress()));
                textViewGreenValue.setText(String.valueOf(seekBarGreen.getProgress()));
                textViewBlueValue.setText(String.valueOf(seekBarBlue.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        };

        seekBarRed.setOnSeekBarChangeListener(onSeekBarChangeListener);
        seekBarGreen.setOnSeekBarChangeListener(onSeekBarChangeListener);
        seekBarBlue.setOnSeekBarChangeListener(onSeekBarChangeListener);


        //Change or Value as per seekbar for Stroke width
        seekBarStrokeWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mPaint.setStrokeWidth(i);
                textViewStrokeWidth.setText(getContext().getResources().getString(R.string.stroke_width, i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        //check /change Value as per seekbar for Opacity
        seekBarOpacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mPaint.setAlpha(i);
                textViewOpacity.setText(getContext().getResources().getString(R.string.opacity, i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        //check or change Value as per seekbar for Font Size
        seekBarFontSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mPaint.setTextSize(i);
                textViewFontSize.setText(getContext().getResources().getString(R.string.font_size, i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        //Change or check Value as per seekbar for AntiAlias
        appCompatCheckBoxAntiAlias.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    mPaint.setAntiAlias(b);
            }
        });


        //check or change Value as per seekbar for Dither
        appCompatCheckBoxDither.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    mPaint.setDither(b);
            }
        });

        //check or change Value as per seekbar for Fill
        appCompatRadioButtonFill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    mPaint.setStyle(Paint.Style.FILL);
            }
        });
        //check or change Value as per seekbar for
        appCompatRadioButtonFillStroke.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            }
        });

        appCompatRadioButtonStroke.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    mPaint.setStyle(Paint.Style.STROKE);
            }
        });

        appCompatRadioButtonButt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    mPaint.setStrokeCap(Paint.Cap.BUTT);
            }
        });

        appCompatRadioButtonRound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    mPaint.setStrokeCap(Paint.Cap.ROUND);
            }
        });

        appCompatRadioButtonSquare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    mPaint.setStrokeCap(Paint.Cap.SQUARE);
            }
        });

        appCompatRadioButtonDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    mPaint.setTypeface(Typeface.DEFAULT);
            }
        });

        appCompatRadioButtonMonospace.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    mPaint.setTypeface(Typeface.MONOSPACE);
            }
        });

        appCompatRadioButtonSansSerif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    mPaint.setTypeface(Typeface.SANS_SERIF);
            }
        });

        appCompatRadioButtonSerif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    mPaint.setTypeface(Typeface.SERIF);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (onCustomViewDialogListener != null)
                            onCustomViewDialogListener.onRefreshPaint(mPaint);
                        dismiss();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                });
        return builder.create();
    }

    // METHODS
    public void setPaint(Paint paint) {
        this.mPaint = paint;
    }

    // INTERFACE
    public void setOnCustomViewDialogListener(OnCustomViewDialogListener onCustomViewDialogListener) {
        this.onCustomViewDialogListener = onCustomViewDialogListener;
    }

    public interface OnCustomViewDialogListener {
        void onRefreshPaint(Paint newPaint);
    }
}
