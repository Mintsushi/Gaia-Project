package com.example.round.gaia_17.model;

/**
 * Created by Round on 2017-08-29.
 */

public class Coord {
    private double lat;
    private double lon;

    public Coord(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat(){return lat;}
    public void setLat(double lat){this.lat = lat;}

    public double getLon(){return lon;}
    public void setLon(double lon){this.lon = lon;}
}
