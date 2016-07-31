package ch.abertschi.toggleninja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView ninjaLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.ninjaLogo = (ImageView) findViewById(R.id.imageView);
        animateLogo();


//        ScaleAnimation zoomAnimation = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f, 0.5f,0.5f);
//        zoomAnimation.
//        ImageView imageView = (ImageView) findViewById(R.id.imageView);
//        zoomAnimation.setDuration(100);
//        zoomAnimation.setStartOffset(0);
//        imageView.startAnimation(zoomAnimation);
    }

    public void sendMessage(View view)
    {
        Intent intent = new Intent(MainActivity.this, ToggleActivity.class);
        startActivity(intent);
    }

    private void animateLogo() {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.image_center_scale);
        a.reset();
        ninjaLogo.clearAnimation();
        ninjaLogo.startAnimation(a);
    }
}
