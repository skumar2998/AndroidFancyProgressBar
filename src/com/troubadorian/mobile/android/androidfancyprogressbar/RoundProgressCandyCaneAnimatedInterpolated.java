package com.troubadorian.mobile.android.androidfancyprogressbar;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ClipDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class RoundProgressCandyCaneAnimatedInterpolated extends RelativeLayout implements Runnable {

    private ImageView progressDrawableImageView;
    private ImageView trackDrawableImageView;
    private Context context;
    private ArrayList<ImageView> imageHolders;
    private ArrayList<String> images;
    private Thread animationThread;
    private boolean stopped = true;
    private double max = 100;
    
    public int getMax() {
        Double d = new Double(max);
        return d.intValue();
    }
    
    public double getMaxDouble() {
        return max;
    }

    public void setMax(int max) {
        Integer maxInt = new Integer(max);
        maxInt.doubleValue();
        this.max = max;
    }
    
    public void setMax(double max) {
        this.max = max;
    }


    public RoundProgressCandyCaneAnimatedInterpolated(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.roundprogressbarcandycaneanimatedinterpolated, null);
        addView(view);

        imageHolders = new ArrayList<ImageView>();
        imageHolders.add((ImageView) view.findViewById(R.id.imgOne));
        imageHolders.add((ImageView) view.findViewById(R.id.imgTwo));
        imageHolders.add((ImageView) view.findViewById(R.id.imgThree));

        // Prepare an array list of images to be animated
        images = new ArrayList<String>();

        images.add("progressbar_blue_1");
        images.add("progressbar_blue_2");
        images.add("progressbar_blue_3");
        images.add("progressbar_blue_4");
        
//        setup(context, attrs);
   
    }

    public RoundProgressCandyCaneAnimatedInterpolated(Context context) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.roundprogressbarcandycaneanimatedinterpolated, null);
        addView(view);

        imageHolders = new ArrayList<ImageView>();
        imageHolders.add((ImageView) view.findViewById(R.id.imgOne));
        imageHolders.add((ImageView) view.findViewById(R.id.imgTwo));
        imageHolders.add((ImageView) view.findViewById(R.id.imgThree));

        // Prepare an array list of images to be animated
        images = new ArrayList<String>();

        images.add("progressbar_blue_1");
        images.add("progressbar_blue_2");
        images.add("progressbar_blue_3");
        images.add("progressbar_blue_4");

    }

    protected void setup(Context context, AttributeSet attrs)
    {
        TypedArray a = context.obtainStyledAttributes(attrs,
            R.styleable.RoundProgress);
        final String xmlns="http://schemas.android.com/apk/res/com.troubadorian.mobile.android.androidfancyprogressbar";
        int bgResource = attrs.getAttributeResourceValue(xmlns,
                "progressDrawable", 0);
        progressDrawableImageView = (ImageView) findViewById(
                R.id.progress_drawable_image_view);
        progressDrawableImageView.setBackgroundResource(bgResource);

        int trackResource = attrs.getAttributeResourceValue(xmlns, "track", 0);
        trackDrawableImageView = (ImageView) findViewById(R.id.track_image_view);
        trackDrawableImageView.setBackgroundResource(trackResource);

        int progress = attrs.getAttributeIntValue(xmlns, "progress", 0);
        setProgress(progress);
        int max = attrs.getAttributeIntValue(xmlns, "max", 100);
        setMax(max);

        int numTicks = attrs.getAttributeIntValue(xmlns, "numTicks", 0);

        a.recycle();

        ProgressBarOutline outline = new ProgressBarOutline(context);
        addView(outline);
    }
    
    /**
     * This is called when you want the dialog to be dismissed
     */
    public void dismiss() {
        stopped = true;
        setVisibility(View.GONE);
    }

    public void setProgress(Integer value)
    {
        setProgress((double) value);
    }
    
    public void setProgress(double value) {
        ClipDrawable drawable = (ClipDrawable)
                progressDrawableImageView.getBackground();
        double percent = (double) value/ (double)max;
        int level = (int)Math.floor(percent*10000);

        drawable.setLevel(level);
    }

    /**
     * Starts the animation thread
     */
    public void startAnimation() {
        setVisibility(View.VISIBLE);
        animationThread = new Thread(this, "Progress");
        animationThread.start();
    }

    @Override
    public void run() {
        while (stopped) {
            try {
                // Sleep for 0.3 secs and after that change the images
//                Thread.sleep(300);
                Thread.sleep(100);
                handler.sendEmptyMessage(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int currentImage = 0;
            int nextImage = 0;
            // Logic to change the images
            for (ImageView imageView : imageHolders) {
                currentImage = Integer.parseInt(imageView.getTag().toString());
                if (currentImage < 4) {
                    nextImage = currentImage + 1;
                } else {
                    nextImage = 1;
                }
                imageView.setTag("" + nextImage);
                imageView.setImageResource(getResources().getIdentifier(
                        images.get(nextImage - 1), "drawable",
                        "com.troubadorian.mobile.android.androidfancyprogressbar"));
            }
            super.handleMessage(msg);
        }

    };

}
