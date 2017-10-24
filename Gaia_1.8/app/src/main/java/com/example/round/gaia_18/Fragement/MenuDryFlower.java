package com.example.round.gaia_18.Fragement;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.round.gaia_18.Data.DryFlower;
import com.example.round.gaia_18.Dialog.AddDryFlower;
import com.example.round.gaia_18.Dialog.AddDryFlowerItemDialog;
import com.example.round.gaia_18.MemUtils;
import com.example.round.gaia_18.R;

import static com.example.round.gaia_18.Data.DataList.dryFlowerAdapter;
import static com.example.round.gaia_18.MainActivity.fruit;
import static com.example.round.gaia_18.MainActivity.mOverlayService;
import static com.example.round.gaia_18.MainActivity.relativeLayout;
import static com.example.round.gaia_18.OverlayService.dataList;
import static com.example.round.gaia_18.OverlayService.user;

public class MenuDryFlower extends Fragment {

    private static final String TAG = ".MenuDryFlower";
    private ListView dryFlowerList;
    private static final float BYTES_PER_PX = 4.0f;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        //Fragement 활성화
        View view = inflater.inflate(R.layout.menu_dry_flower_fragment,container,false);

        dryFlowerAdapter = new DryFlowerAdapter(getContext(),R.layout.menu_dry_flower_item);
        dryFlowerList = (ListView)view.findViewById(R.id.menu_dry_flower_list);
        dryFlowerList.setAdapter(dryFlowerAdapter);

        return view;
    }

    private class DryFlowerViewHolder{
        LinearLayout background;
        ImageView dryFlowerImage;
        TextView dryFlowerName;
        TextView dryFlowerEffect;
        ImageView dryFlowerInfoButton;
    }

    public class DryFlowerAdapter extends ArrayAdapter<DryFlower>{
        private LayoutInflater mInflater = null;

        public DryFlowerAdapter(Context context, int resource) {
            super(context, resource);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount(){ return 5; }

        @Override
        public View getView(int position, View view, ViewGroup parent){

            final DryFlowerViewHolder viewHolder;

            if(view == null) {
                view = mInflater.inflate(R.layout.menu_dry_flower_item, parent, false);

                viewHolder = new DryFlowerViewHolder();
                viewHolder.background = (LinearLayout) view.findViewById(R.id.dryFlowerBackground);
                viewHolder.dryFlowerImage = (ImageView) view.findViewById(R.id.dryFlowerImage);
                viewHolder.dryFlowerName = (TextView) view.findViewById(R.id.dryFlowerName);
                viewHolder.dryFlowerEffect = (TextView) view.findViewById(R.id.dryFlowerEffect);
                viewHolder.dryFlowerInfoButton = (ImageView) view.findViewById(R.id.dryFlowerInfoButton);

                view.setTag(viewHolder);
            }else{
                viewHolder = (DryFlowerViewHolder) view.getTag();
            }

            //꽃을 넣을 수 있는 곳
            if(position < user.getDryFlowerItem()){
                viewHolder.dryFlowerInfoButton.setVisibility(View.VISIBLE);

                //꽃이 있는 곳
                if(position<dataList.getDryPlantSize()){
                    final DryFlower dryFlower = dataList.getDryFlower(position);
                    viewHolder.dryFlowerInfoButton.setVisibility(View.VISIBLE);
                    viewHolder.background.setBackgroundResource(R.drawable.flower_buy_item);
                    //후에 식물 이미지로 변경
                    int resourceId = getContext().getResources().getIdentifier("flower" + dryFlower.getDryFlowerNo(), "drawable", getContext().getPackageName());
                    loadImage(viewHolder.dryFlowerImage,resourceId);

                    viewHolder.dryFlowerName.setText(dryFlower.getDryFlowerName());
                    viewHolder.dryFlowerEffect.setText("초당 "+dataList.getAllScore(dryFlower.getScore())+" 점수 획득");
                }
                //꽃이 없는 곳
                else{
                    viewHolder.dryFlowerInfoButton.setVisibility(View.INVISIBLE);
                    viewHolder.background.setBackgroundResource(R.drawable.flower_buy_available);
                    viewHolder.background.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            final AddDryFlower dialog = new AddDryFlower(getContext());

                            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    Log.i(" MainActivity. dialog : ",""+dialog.getDryFlower().size());

                                    for(int i =0 ; i<dialog.getDryFlower().size();i++){
                                        dataList.setDryPlats(dialog.getDryFlower().get(i));
                                        dataList.resetFlower(dialog.getDryFlower().get(i).getDryFlowerNo());
                                        //App에 있을 때
                                        if(dialog.getDryFlower().get(i).getPlant().getState() == 0){
                                            Log.i(" MainActivity.delPlant sze : ",""+dataList.getPlants().size());

                                            relativeLayout.removeView(dialog.getDryFlower().get(i).getPlant().getPlantLayout());
                                            dataList.delsPlant(dialog.getDryFlower().get(i).getIndex());
                                            Log.i(" MainActivity.delPlant sze : ",""+dataList.getPlants().size());

                                        }else{//overlay에 있을 때
                                           mOverlayService.removePlant(dialog.getDryFlower().get(i).getDryFlowerNo());
                                        }

                                    }
                                    Log.i(" MainActivity.delPlant sze : ",""+dataList.getPlants().size());
                                    dataList.getGoalDataByID(14).setGoalRate(dialog.getDryFlower().size());
                                    dryFlowerAdapter.notifyDataSetChanged();
                                }
                            });
                            dialog.show();
                        }
                    });
                }

            }
            //꽃을 넣을 수 없는 곳
            else{
                viewHolder.dryFlowerInfoButton.setVisibility(View.INVISIBLE);
                viewHolder.background.setBackgroundResource(R.drawable.flower_item_lock);

                //지속스킬 칸을 늘리기 위한 구입
                viewHolder.background.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AddDryFlowerItemDialog dialog = new AddDryFlowerItemDialog(getContext());
                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                fruit.setText(dataList.getAllScore(dataList.getFruitHashMap()));
                                dryFlowerAdapter.notifyDataSetChanged();
                            }
                        });

                        dialog.show();
                    }
                });
            }

            return view;
        }
    }

    private void loadImage(ImageView image,int resourceId){
        if(readBitmapInfo(resourceId) * 100> MemUtils.megabytesAvailable()){
            Log.i("LoadImage","Big Image");
            subImage(32,resourceId,image);
        }else{
            Log.i("LoadImage","Small Image");
            image.setImageResource(resourceId);
        }
    }

    private float readBitmapInfo(int resourceId){
        final Resources res = getContext().getResources();
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res,resourceId,options);

        final float imageHeight = options.outHeight;
        final float imageWidth = options.outWidth;
        final String imageMimeType = options.outMimeType;

        return imageWidth*imageHeight*BYTES_PER_PX / MemUtils.BYTE_IN_MB;
    }

    private void subImage(int powerOf2,int resourceId,ImageView image){
        if(powerOf2 < 1 || powerOf2 > 32){
            return;
        }

        final Resources res = getContext().getResources();
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = powerOf2;

        final Bitmap bitmap = BitmapFactory.decodeResource(res,resourceId,options);
        image.setImageBitmap(bitmap);
    }
}
