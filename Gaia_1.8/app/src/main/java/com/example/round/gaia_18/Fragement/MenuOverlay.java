package com.example.round.gaia_18.Fragement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.round.gaia_18.Data.Plant;
import com.example.round.gaia_18.R;

import static android.app.Activity.RESULT_OK;
import static com.example.round.gaia_18.Data.DataList.plantAdapter;
import static com.example.round.gaia_18.MainActivity.mOverlayService;
import static com.example.round.gaia_18.OverlayService.dataList;
import static com.example.round.gaia_18.OverlayService.weatherData;

/**
 * Created by Round on 2017-09-06.
 */

public class MenuOverlay extends Fragment {

    private static final String TAG =".MenuOverlay";
    private static final int REQUEST_CODE=1001;
    private View view;

    //Layout / View
    private ListView plantList;
    //GPS Setting
    private android.app.AlertDialog alertDialog = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        //Fragment 화면 활성화
        View view = inflater.inflate(R.layout.menu_overlay_fragment,container,false);

        //plant : 사용자가 가지고 있는 꽃 종류

        if(mOverlayService.enalbeOverlayService){
            plantAdapter = new PlantAdapter(getContext(),R.layout.menu_overlay_item);
            plantList = (ListView)view.findViewById(R.id.menu_overlay_list);
            plantList.setAdapter(plantAdapter);
        }
        else{
            this.view = view;
            setGPS();
        }

        return view;
    }

    // 플라워창 리스트 포멧
    class PlantViewHolder{
        ImageView plantImage;
        TextView plantName;
        TextView weatherScore;
        Button overlayButton;
    }

    public class PlantAdapter extends ArrayAdapter<Plant>{

        private LayoutInflater mInflater = null;

        public PlantAdapter(Context context, int resource){
            super(context,resource);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount(){ return dataList.getPlants().size(); }

        @Override
        public View getView(int position, View view, ViewGroup parent){
            final PlantViewHolder plantViewHolder;

            if( view == null){
                view = mInflater.inflate(R.layout.menu_overlay_item,parent,false);

                plantViewHolder= new PlantViewHolder();
                plantViewHolder.plantImage = (ImageView) view.findViewById(R.id.plantImage);
                plantViewHolder.plantName = (TextView) view.findViewById(R.id.plantName);
                plantViewHolder.weatherScore = (TextView)view.findViewById(R.id.weatherScore);
                plantViewHolder.overlayButton = (Button) view.findViewById(R.id.overlayButton);

                view.setTag(plantViewHolder);
            }else{
                plantViewHolder = (PlantViewHolder) view.getTag();
            }

            final Plant plant = dataList.getPlants().get(position);

            if(plant != null){

                //plantViewHolder.plantImage.setImageResource(plant.getFlower.getImage())
                plantViewHolder.plantImage.setImageResource(R.drawable.image);
                plantViewHolder.plantName.setText(plant.getFlower().getFlowerName());
                String weatherScore = "";

                if(weatherData.get(plant.getPlantNo()) > 0 ){
                    weatherScore = "클릭 당 점수 : + "+weatherData.get(plant.getPlantNo())+" %";
                }else{
                    weatherScore = "HP 감소율 : + "+weatherData.get(plant.getPlantNo())+" %";
                }
                plantViewHolder.weatherScore.setText(weatherScore);

                //overlay에 있을 때
                if(plant.getState() == 1){
                    plantViewHolder.overlayButton.setText("OFF");
                } //overlay에 없을 때
                else{
                    plantViewHolder.overlayButton.setText("ON");
                }

                plantViewHolder.overlayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //overlay에 없을 때
                        Log.i("overView","size"+ dataList.getPlants().size());
                        if(plant.getState() == 0){
                            if(mOverlayService.addPlantToOverlay(plant)) {
                                Log.i("overlay","SET overlay");
                                dataList.minusClickScore(plant.getFlower().getScore());
                                plant.getFlower().setWhere(1);
                                plant.setState(1);
                                dataList.getGoalDataByID(10).setGoalRate(1);
                            }
                        } //overlay에 있을 때
                        else{
                            //overlay에서 제거
                            Log.i("overlay","remove overlay");
                            mOverlayService.removePlant(plant.getPlantNo());
                            dataList.plusClickScore(plant.getFlower().getScore());
                            plant.setState(0);
                            plant.getFlower().setWhere(0);
                            plant.setState(0);
                        }

                        plantAdapter.notifyDataSetChanged();
                    }
                });
            }

            return view;
        }
    }

    private void setGPS() {
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_gps, null);

        TextView warn = (TextView) dialogView.findViewById(R.id.warn);
        warn.setText("GPS Setting이 되어 있지 않아. 외부기능 사용이 불가능합니다.\n 외부기능을 사용하고 싶으시면 확인버튼을 클릭하여 GPS를 실행시켜주세요.");
        Button cancel = (Button) dialogView.findViewById(R.id.cancel);
        Button submit = (Button) dialogView.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOverlayService.enalbeOverlayService = true;
                alertDialog.cancel();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        Log.i("GPS","requestCode : "+requestCode+" / resultCode : "+requestCode+" / RESULT OK : "+RESULT_OK);
        if(requestCode == REQUEST_CODE){
            plantAdapter = new PlantAdapter(getContext(),R.layout.menu_overlay_item);
            plantList = (ListView)view.findViewById(R.id.menu_overlay_list);
            plantList.setAdapter(plantAdapter);
        }
    }
}
