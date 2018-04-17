package edu.fsu.cs.mobile.safereader;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayFragment extends Fragment implements SensorEventListener {

    //variables to use during program execution
    private boolean nightmode, brightness, scale;
    private TextView textDisplay;
    private SensorManager sensorManager;
    private Sensor sensor;
    private static final int DEFAULT_TEXT_SIZE = 36;
    private static final int MULTIPLIER = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display, container, false);
        Bundle bundle = this.getArguments();
        String selection = bundle.getString("text");
        textDisplay = view.findViewById(R.id.textSelection);
        textDisplay.setText(selection);
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //if(night){}
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            float prox, newSize;
            prox = event.values[0];

            if(scale) {
                newSize = DEFAULT_TEXT_SIZE + (prox * MULTIPLIER);
                textDisplay.setTextSize(newSize / getResources().getDisplayMetrics().density);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_options, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.night:
                if(nightmode) {
                    Toast.makeText(getContext(), "NIGHTMODE DISABLED", Toast.LENGTH_SHORT).show();
                    textDisplay.setTextColor(Color.BLACK);
                    this.getView().setBackgroundColor(Color.WHITE);
                    nightmode = false;
                }
                else{
                    Toast.makeText(getContext(), "NIGHTMODE ENABLED", Toast.LENGTH_SHORT).show();
                    textDisplay.setTextColor(Color.WHITE);
                    this.getView().setBackgroundColor(Color.BLACK);
                    nightmode = true;
                }
                break;
            case R.id.brightness:
                if(brightness){
                    Toast.makeText(getContext(), "AUTO-BRIGHTNESS DISABLED", Toast.LENGTH_SHORT).show();
                    brightness = false;
                }
                else{
                    Toast.makeText(getContext(), "AUTO-BRIGHTNESS ENABLED", Toast.LENGTH_SHORT).show();
                    brightness = true;
                }
                break;
            case R.id.autoscale:
                if(scale){
                    Toast.makeText(getContext(), "AUTO-TEXTSCALING DISABLED", Toast.LENGTH_SHORT).show();
                    scale = false;
                }
                else{
                    Toast.makeText(getContext(), "AUTO-TEXTSCALING ENABLED", Toast.LENGTH_SHORT).show();
                    scale = true;
                }
                break;
        }
        return true;
    }
}
