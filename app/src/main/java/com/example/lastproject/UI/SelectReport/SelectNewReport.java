package com.example.lastproject.UI.SelectReport;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.lastproject.DB.FireBaseHelper;
import com.example.lastproject.R;
import com.example.lastproject.UI.Map.Map;

/**
 * This activity allows users to select a new report from the list.
 */
public class SelectNewReport extends AppCompatActivity implements View.OnClickListener {
    private Button select, cancel;
    private TextView Dtext, Ttext, dateText, didText, yesButton;
    private ImageView image1;
    private ModuleSelect moduleSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_new_report);

        // Initialize UI elements
        yesButton = findViewById(R.id.yesb);
        yesButton.setOnClickListener(this);
        select = findViewById(R.id.select1);
        Dtext = findViewById(R.id.description);
        Ttext = findViewById(R.id.time1);
        didText = findViewById(R.id.did);
        dateText = findViewById(R.id.date1);
        image1 = findViewById(R.id.imageMarker);
        cancel = findViewById(R.id.cencle1);
        select.setOnClickListener(this);
        cancel.setOnClickListener(this);
        moduleSelect = new ModuleSelect(this);

        // Fetch marker information
        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Fetching Marker");
        pd.setCancelable(false);
        pd.show();
        moduleSelect.getMarkerInfo(getIntent().getStringExtra("id"), new FireBaseHelper.MarkerFetched() {
            @Override
            public void onMarkerFetched(String Description, String Time, String Date, Uri photo) {
                // Load image using Glide
                Glide.with(getBaseContext()).load(photo).placeholder(R.drawable.picture).addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        // Set UI elements with fetched data
                        Dtext.setText("location description:  " + Description);
                        Ttext.setText(Time);
                        dateText.setText(Date);
                        image1.setImageDrawable(resource);
                        pd.dismiss();
                        return false;
                    }
                }).into(image1);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == cancel) {
            // Navigate back to the map activity
            Intent intent = new Intent(SelectNewReport.this, Map.class);
            startActivity(intent);
        }

        if (v == select) {
            // Update UI for the selection
            didText.setText("did you finish cleaning the area?");
            yesButton.setText("yes");
            yesButton.setTextColor(Color.WHITE);
        }

        if (v == yesButton) {
            ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Resolving Report");
            pd.setCancelable(false);
            pd.show();
            // Delete the selected marker
            moduleSelect.deleteMarker(getIntent().getStringExtra("id"), new FireBaseHelper.DeletedMarker() {
                @Override
                public void onDelete() {
                    pd.dismiss();
                    // Navigate back to the map activity after resolving the report
                    Intent intent = new Intent(SelectNewReport.this, Map.class);
                    Toast.makeText(getBaseContext(), "thank you for cleaning the area, good job!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            });
        }
    }
}
