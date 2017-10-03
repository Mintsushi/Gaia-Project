package com.example.round.gaia_18.Data;

import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

/**
 * Created by Round on 2017-09-06.
 */

public class OverlayPlant {

    private Plant plant;
    private WindowManager.LayoutParams params;
    private RelativeLayout overlayPlant;
    private ProgressBar overlayPlantHP;
    private ImageView overlayPlantWater;
    private RelativeLayout  overlayPlantLayout;

    public OverlayPlant(Plant plant, RelativeLayout overlayPlant, WindowManager.LayoutParams params) {
        this.plant = plant;
        this.overlayPlant = overlayPlant;
        this.params = params;

    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public RelativeLayout getOverlayPlant() {
        return overlayPlant;
    }

    public void setOverlayPlant(RelativeLayout overlayPlant) {
        this.overlayPlant = overlayPlant;
    }

    public WindowManager.LayoutParams getParams() {
        return params;
    }

    public void setParams(WindowManager.LayoutParams params) {
        this.params = params;
    }
}
