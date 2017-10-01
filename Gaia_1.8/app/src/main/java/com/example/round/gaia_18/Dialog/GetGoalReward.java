package com.example.round.gaia_18.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.round.gaia_18.MemUtils;
import com.example.round.gaia_18.R;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.round.gaia_18.OverlayService.dataList;
import static com.example.round.gaia_18.MainActivity.fruit;
import static com.example.round.gaia_18.MainActivity.mOverlayService;
import static com.example.round.gaia_18.MainActivity.seed;

public class GetGoalReward extends Dialog {

    private static final float BYTES_PER_PX = 4.0f;

    private int rewardType;
    private final ConcurrentHashMap<Integer, Integer> reward;

    public GetGoalReward(Context context, int rewardType, ConcurrentHashMap<Integer, Integer> reward){
        super(context);
        this.rewardType = rewardType;
        this.reward = reward;
        Log.i("GetGoalReward","reward : "+reward.toString()+" / rewardType : " +rewardType);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.menu_dry_flower_add_dialog);

        ImageView rewardImage = (ImageView)findViewById(R.id.image);
        TextView rewardScore = (TextView)findViewById(R.id.cost);
        TextView confirmMessage = (TextView)findViewById(R.id.confirmMessage);

        int resourceId = getContext().getResources().getIdentifier("reward" + rewardType, "drawable", getContext().getPackageName());
        loadImage(rewardImage,resourceId);
        rewardScore.setText(" X "+dataList.getAllScore(this.reward));
        confirmMessage.setText("보상을 받으시겠습니까??");

        ImageButton confirm = (ImageButton)findViewById(R.id.buy);
        ImageButton cancel = (ImageButton)findViewById(R.id.cancel);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rewardFromGoal();
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    private void loadImage(ImageView image,int resourceId){
        if(readBitmapInfo(resourceId)*100 > MemUtils.megabytesFree()){
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

    private void rewardFromGoal(){
        switch (rewardType){
            //현금성 재화
            case 1:
                updateGoalReward(rewardType);
                break;
            //점수
            case 2:
                updateGoalReward(rewardType);
                break;
            //체력포션
            case 3:
                updataGoalRewardItem(1);
                break;
            //부활포션
            case 4:
                updataGoalRewardItem(2);
                break;
            //물
            case 5:
                updataGoalRewardItem(3);
                break;
        }
    }

    private void updateGoalReward(int rewardType){

        Log.i("GetGoalReward","reward : "+reward.toString()+" / rewardType : " +rewardType);
        Iterator<Integer> iterator = this.reward.keySet().iterator();
        ConcurrentHashMap<Integer, Integer> reward;

        if(rewardType == 1){
            reward = dataList.getFruitHashMap();
        }else{
            reward = dataList.getScoreHashMap();
        }

        while(iterator.hasNext()){
            int key = iterator.next();
            int value = this.reward.get(key);

            dataList.plusScore(key, value,reward);
        }

        if(rewardType == 1){
            fruit.setText(dataList.getAllScore(dataList.getFruitHashMap()));
        }else{
            seed.setText(dataList.getAllScore(dataList.getScoreHashMap()));
            mOverlayService.setSeed();
        }
    }

    private void updataGoalRewardItem(int rewardItemType){
        dataList.setIncItemNumber(rewardItemType,Integer.parseInt(dataList.getAllScore(reward)));
    }
}

