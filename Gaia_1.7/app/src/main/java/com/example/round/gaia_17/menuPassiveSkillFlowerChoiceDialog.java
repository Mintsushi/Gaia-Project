package com.example.round.gaia_17;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.round.gaia_17.MainActivity.dryFlowerList;
import static com.example.round.gaia_17.MainActivity.flowerActivityArray;
import static com.example.round.gaia_17.MainActivity.scoreCalculaters;

/**
 * Created by 리제 on 2017-09-03.
 */

public class menuPassiveSkillFlowerChoiceDialog  extends Dialog {
    public menuPassiveSkillFlowerChoiceDialog(Context context){super(context);}

    private static final String TAG = ".DialogPassiveSkillActivity";
    private ListView mList;
    private DialogFlowerAdapter mAdapter;
    private ArrayList<DialogFlowerInform> mDialogFlowerArray = new ArrayList<>();
    ImageButton back;
    ImageButton diaYesButton;
    ImageButton diaNoButton;
    int dialogtoActid;
    int cheakBoxInspection =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu_passive_skill_flower_choice_dialog);

        diaYesButton = (ImageButton)findViewById(R.id.dialogyesButton);
        diaNoButton = (ImageButton)findViewById(R.id.dialognoButton);

        mAdapter = new DialogFlowerAdapter(getContext(),R.layout.menu_passive_skill_dialog_item);
        mList = (ListView)findViewById(R.id.dialogFlowerChoiceList);
        mList.setAdapter(mAdapter);

        for(int i = 0; i<4; i++){
            Log.i("Error ; ",""+ flowerActivityArray.get(i));
            Log.i("Error ; ",""+flowerActivityArray.get(i).getFlowerLevel());
            Log.i("Error ; ",""+flowerActivityArray.get(i).getFlowerNo() );
            if(400 == flowerActivityArray.get(i).getFlowerLevel()){
                mDialogFlowerArray.add(new DialogFlowerInform(flowerActivityArray.get(i).getFlowerNo(), 0, flowerActivityArray.get(i).getFlowerImage(),
                        flowerActivityArray.get(i).getFlowerName(), "초당 "+ scoreCalculaters(dryFlowerList.get(i).getIncScore(),dryFlowerList.get(i).getCostPower()) + "획득."));
                Log.i("Error ; ",""+ flowerActivityArray.get(i).getFlowerName() +"//"+flowerActivityArray.get(i).getFlowerNo() );
            }
        }
        diaYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cheakBoxInspection==0){
                    dialogtoActid = -1;
                }
                dismiss();
            }
        });

        diaNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogtoActid = -1;
                dismiss();
            }
        });


        back = (ImageButton) findViewById(R.id.Diaback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }

        });
    }

        // 플라워 정보 포멧
        public class DialogFlowerInform{

            private int id;
            private int cheaktype;
            private String flowerImagePath;

            private String flowerName;
            private String flowerEffect;


            public DialogFlowerInform(int id, int cheaktype, String flowerImagePath, String flowerName, String flowerEffect){
                this.id = id;
                this.cheaktype = cheaktype;
                this.flowerImagePath = flowerImagePath;
                this.flowerName = flowerName;
                this.flowerEffect = flowerEffect;
            }

            public int getId(){return this.id;}
            public String getFlowerImagePath(){return this.flowerImagePath;}
            public String getFlowerName(){return this.flowerName;}
            public String getFlowerEffect(){return this.flowerEffect;}
            public int getCheaktype(){return this.cheaktype;}
            public void setCheaktype(int cheaktype){this.cheaktype = cheaktype;}
        }

    // 꽃선택창 리스트 포멧
    static class DialogFlowerViewHolder{
        ImageView dialogFlowerImage;
        TextView dialogFlowerNameText;
        TextView dialogFlowerEffectText;
        ImageView dialogFlowerCheakBox;
    }

        // 꽃선택창 리스트 어뎁터
        public class DialogFlowerAdapter extends ArrayAdapter<DialogFlowerInform> {
            private LayoutInflater mInflater = null;

            public DialogFlowerAdapter(Context context, int resource){
                super(context,resource);
                mInflater = LayoutInflater.from(context);
            }

            @Override
            public int getCount(){
                return mDialogFlowerArray.size();
            }

            @Override
            public View getView(int position, View v, ViewGroup parent){
                final DialogFlowerViewHolder viewHolder;


                final DialogFlowerInform info = mDialogFlowerArray.get(position);

                Log.i(TAG,"이름 : "+info.getFlowerName());
                if(v == null){

                    v=mInflater.inflate(R.layout.menu_passive_skill_dialog_item,parent,false);


                    viewHolder = new DialogFlowerViewHolder();
                    viewHolder.dialogFlowerImage=(ImageView) v.findViewById(R.id.dialogFlowerImage);
                    viewHolder.dialogFlowerNameText=(TextView)v.findViewById(R.id.dialogFlowerNameText);
                    viewHolder.dialogFlowerEffectText=(TextView)v.findViewById(R.id.dialogFlowerEffectText);
                    viewHolder.dialogFlowerCheakBox=(ImageView) v.findViewById(R.id.cheakBox);
                    v.setTag(viewHolder);
                }else{
                    viewHolder = (DialogFlowerViewHolder) v.getTag();
                }

                if(info != null){
                    if(info.getCheaktype()==0) {

                        v.setBackgroundResource(R.drawable.lock_background);
                        v.setOnClickListener(null);
                        viewHolder.dialogFlowerImage.setImageResource(R.mipmap.ic_launcher);
                        viewHolder.dialogFlowerNameText.setText(info.getFlowerName());
                        viewHolder.dialogFlowerEffectText.setText(info.getFlowerEffect());
                        viewHolder.dialogFlowerCheakBox.setVisibility(View.INVISIBLE);
                        if(cheakBoxInspection ==0) {
                            v.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    cheakBoxInspection = 1;
                                    info.setCheaktype(1);
                                    dialogtoActid = info.getId();
                                    mAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                    else{

                        v.setBackgroundResource(R.drawable.shape);
                        viewHolder.dialogFlowerImage.setImageResource(R.mipmap.ic_launcher);
                        viewHolder.dialogFlowerNameText.setText(info.getFlowerName());
                        viewHolder.dialogFlowerEffectText.setText(info.getFlowerEffect());
                        viewHolder.dialogFlowerCheakBox.setVisibility(View.VISIBLE);
                        v.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                cheakBoxInspection = 0;
                                dialogtoActid = -1;
                                info.setCheaktype(0);
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }

                return v;
            }

        }



    public void id(int id) {
        //name.setText(str);
    }
    int getDiaid(){
        return dialogtoActid;
    }
}
