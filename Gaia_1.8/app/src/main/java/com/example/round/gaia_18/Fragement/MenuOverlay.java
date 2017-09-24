package com.example.round.gaia_18.Fragement;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;

import static com.example.round.gaia_18.MainActivity.dataList;
import static com.example.round.gaia_18.MainActivity.mOverlayService;
import static com.example.round.gaia_18.OverlayService.weatherData;

/**
 * Created by Round on 2017-09-06.
 */

public class MenuOverlay extends Fragment {

    private static final String TAG =".MenuOverlay";

    //Layout / View
    private ListView plantList;
    public static PlantAdapter plantAdapter;

    //Data
    private ArrayList<Plant> plants = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        //Fragment 화면 활성화
        View view = inflater.inflate(R.layout.menu_overlay_fragment,container,false);

        //plant : 사용자가 가지고 있는 꽃 종류
        plants = dataList.getPlants();

        plantAdapter = new PlantAdapter(getContext(),R.layout.menu_overlay_item);
        plantList = (ListView)view.findViewById(R.id.menu_overlay_list);
        plantList.setAdapter(plantAdapter);

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
        public int getCount(){ return plants.size(); }

        @Override
        public View getView(int position, View view, ViewGroup parent){
            PlantViewHolder plantViewHolder;

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

            final Plant plant = plants.get(position);

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
                        if(plant.getState() == 0){
                            //overlay에 추가
                            mOverlayService.addPlantToOverlay(plant);
                            dataList.minusClickScore(plant.getFlower().getScore());
                            plant.getFlower().setWhere(1);
                        } //overlay에 있을 때
                        else{
                            //overlay에서 제거
                            mOverlayService.removePlant(plant.getPlantNo());
                            dataList.plusClickScore(plant.getFlower().getScore());
                            plant.setState(0);
                            plant.getFlower().setWhere(0);
                        }

                        plantAdapter.notifyDataSetChanged();
                    }
                });
            }

            return view;
        }
    }
}
