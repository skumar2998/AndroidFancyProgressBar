package com.troubadorian.mobile.android.androidfancyprogressbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import android.graphics.drawable.*;
import android.graphics.drawable.shapes.RoundRectShape;

public class AndroidFancyProgressBarActivity extends Activity
{
    private static final String TAG = "AndroidFancyProgressBarActivity";

    ProgressDialog myProgress;

    ProgressBar myProgressBar;
    
    ProgressBar myProgressBarRoundedCorners;
    
    RoundProgressBar myProgressBarOrange;
    
    RoundProgressBar myProgressBarBlue;
    
    RoundProgressCandyCane myProgressBarBlueCandyCane;
    
    RoundProgressCandyCaneAnimated myProgressBlueCandyCaneAnimated;
    
    RoundProgressCandyCaneAnimatedInterpolated myProgressBlueCandyCaneAnimatedInterpolated;

    protected TextView percentField;

    protected Button cancelButton;

    protected InitTask initTask;

    AssetManager assetManager;

    String[] files;

    protected class InitTask extends AsyncTask<Context, Integer, Integer>
    {
        int myProgressCounter;

        @Override
        protected void onPostExecute(Integer result)
        {
            super.onPostExecute(result);
            Log.i(TAG, "--------------------------------------------------onPostExecute(): " + result);
            percentField.setText(result.toString());
            percentField.setTextColor(0xFF69adea);
            cancelButton.setVisibility(View.INVISIBLE);
            // setContentView(R.layout.main);
        }

        @Override
        protected void onPreExecute()
        {
            Log.d(TAG, "--------------------------------------------------onPreExecute() was called");
            myProgressCounter = 0;
        }

        @Override
        protected Integer doInBackground(Context... params)
        {
            /* start of copying files from assets to cache */
            assetManager = getAssets();
            files = null;
            try
            {
                files = assetManager.list("cachefiles");
                Log.d(TAG, "-----------how many files are in the folder" + files.length);
            } catch (IOException e)
            {
                Log.e(TAG, "---------was there a problem" + e.getMessage());
            }
            for (int i = 0; i < files.length; i++)
            {
                InputStream in = null;
                OutputStream out = null;
                /*
                 * String path = Environment.getExternalStorageDirectory()
                 * .getAbsolutePath() + "/Android/data/" + "/files/";
                 */
                /* getFilesDir() will create the /files folder */
                File filepath = getFilesDir();
                Log.d(TAG, "---------------the file path is " + filepath);
                try
                {
                    in = assetManager.open("cachefiles/" + files[i]);
                    out = new FileOutputStream(filepath + "/" + files[i]);
                    Log.d(TAG, "-------what's really happening here-----" + out.toString());
                    copyFile(in, out);
                    try
                    {
                        Thread.sleep(500);
                    } catch (InterruptedException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    in.close();
                    in = null;
                    out.flush();
                    out.close();
                    out = null;
                    publishProgress(i);
                } catch (IOException ex)
                {
                    Log.e(TAG, "------------------------------bad things" + ex.getMessage());
                }
            }
            /* end of copying files from assets to cache */
            return 100;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
            Log.i("==========================makemachine", "onProgressUpdate(): " + String.valueOf(values[0]));
            percentField.setText((values[0] * 2) + "%");
//            percentField.setTextSize(values[0]);
            myProgressBar.setSecondaryProgress((values[0]));
            myProgressBarOrange.setProgress(values[0]);
            myProgressBarBlue.setProgress(values[0]);
            myProgressBarBlueCandyCane.setProgress(values[0]);
            myProgressBarRoundedCorners.setSecondaryProgress(values[0]);
            
//            for (int j=0; j< values[0]; j++) {
//              myProgressBar.setProgress(j);  
//            }
//            myProgressBar.setProgress(values[0] * 2);
            Log.d(TAG, "--------------------------------------------------onProgressUpdate() was called");
        }

        // -- called if the cancel button is pressed
        @Override
        protected void onCancelled()
        {
            super.onCancelled();
            Log.i(TAG, "--------------------------------------------------------------was onCancelled called--------------");
            percentField.setText("Cancelled!");
            percentField.setTextColor(0xFFFF0000);
            try {
                initTask.cancel(true);    
            }
            catch (Exception e) {
                Log.i(TAG, "--------------------------------------------------------------there was an exception running initTask.cancel()--------------" + e.toString());
            }
            
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException
    {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, read);
        }
    }

    protected class CancelButtonListener implements View.OnClickListener
    {
        public void onClick(View v)
        {
            initTask.cancel(true);
        }
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        percentField = (TextView) findViewById(R.id.percent_field);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new CancelButtonListener());
        myProgressBar = (ProgressBar) findViewById(R.id.custom_progressbar);
        myProgressBarRoundedCorners = (ProgressBar) findViewById(R.id.custom_progressbar_rounded_corners);
        myProgressBarOrange = (RoundProgressBar) findViewById(R.id.orange_progress_bar);
        myProgressBarBlue = (RoundProgressBar) findViewById(R.id.blue_progress_bar);
        myProgressBarBlueCandyCane = (RoundProgressCandyCane) findViewById(R.id.blue_progress_bar_candycane);
        myProgressBlueCandyCaneAnimated = (RoundProgressCandyCaneAnimated) findViewById(R.id.blue_progress_bar_candycane_animated);
        myProgressBlueCandyCaneAnimatedInterpolated = (RoundProgressCandyCaneAnimatedInterpolated) findViewById(R.id.blue_progress_bar_candycane_animated_interpolated);
        
        myProgressBlueCandyCaneAnimated.startAnimation();
        myProgressBlueCandyCaneAnimatedInterpolated.startAnimation();
        assetManager = getAssets();
        files = null;
        try
        {
            files = assetManager.list("cachefiles");
            Log.d(TAG, "-----------how many files are in the folder" + files.length);
        } catch (IOException e)
        {
            Log.e(TAG, "---------was there a problem" + e.getMessage());
        }
        myProgressBar.setMax(files.length-1);
        myProgressBarRoundedCorners.setMax(files.length-1);
        myProgressBarOrange.setMax(files.length-1);
        myProgressBarBlue.setMax(files.length-1);
        myProgressBarBlueCandyCane.setMax(files.length-1);
        ImageView img = (ImageView)findViewById(R.id.simple_anim);
//        img.setBackgroundResource(R.drawable.progressanimation);
        
//        myProgressBar.setProgressDrawable(AndroidFancyProgressBarActivity.this.getResources().getDrawable(R.drawable.progress_bar_states));
//        myProgressBar.setProgressDrawable(AndroidFancyProgressBarActivity.this.getResources().getDrawable(R.drawable.progressbar_blue_states));
//        myProgressBar.setProgressDrawable(AndroidFancyProgressBarActivity.this.getResources().getDrawable(R.drawable.progressbar_two_color_states));
//        myProgressBar.setProgressDrawable(AndroidFancyProgressBarActivity.this.getResources().getDrawable(R.drawable.seekbar_progress));
//        myProgressBar.setSecondaryProgressDrawable(AndroidFancyProgressBarActivity.this.getResources().getDrawable(R.drawable.seekbar_progress));
        // myProgressBar.setMax(100);
        // myProgressBar.setProgress(50);
        // final float[] roundedCorners = new float[] { 5, 5, 5, 5, 5, 5, 5, 5
        // };
        // ShapeDrawable pgDrawable = new ShapeDrawable(new
        // RoundRectShape(roundedCorners, null,null));
        // String MyColor = "#FF00FF";
        // pgDrawable.getPaint().setColor(Color.parseColor(MyColor));
        // ClipDrawable progress = new ClipDrawable(pgDrawable, Gravity.LEFT,
        // ClipDrawable.HORIZONTAL);
        // pg.setProgressDrawable(progress);
        // pg.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.progress_horizontal));
        initTask = new InitTask();
        initTask.execute(this);
    }
}