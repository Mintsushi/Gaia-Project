package com.example.round.gaia_18.Data;

import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by Round on 2017-09-06.
 */

public class OverlayPlant {

    private Plant plant;
    private ImageView overlayPlant;
    private WindowManager.LayoutParams params;

    public OverlayPlant(Plant plant, ImageView overlayPlant, WindowManager.LayoutParams params) {
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

    public ImageView getOverlayPlant() {
        return overlayPlant;
    }

    public void setOverlayPlant(ImageView overlayPlant) {
        this.overlayPlant = overlayPlant;
    }

    public WindowManager.LayoutParams getParams() {
        return params;
    }

    public void setParams(WindowManager.LayoutParams params) {
        this.params = params;
    }
}
