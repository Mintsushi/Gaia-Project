package com.example.round.gaia_17;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import static com.example.round.gaia_17.menuFlowerActivity.mFlowerArray;

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
    int dialogtoActid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu_passive_skill_flower_choice_dialog);

        mAdapter = new DialogFlowerAdapter(getContext(),R.layout.menu_passive_skill_dialog_item);
        mList = (ListView)findViewById(R.id.dialogFlowerChoiceList);
        mList.setAdapter(mAdapter);

        for(int i = 0; i<8; i++){
            if(500 == mFlowerArray.get(i).getFlowerLv()){
                mDialogFlowerArray.add(new DialogFlowerInform(mFlowerArray.get(i).getId(), mFlowerArray.get(i).getFlowerImagePath(),
                        mFlowerArray.get(i).getFlowerName(), mFlowerArray.get(i).getFlowerName()));
            }
        }

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
            private String flowerImagePath;

            private String flowerName;
            private String flowerEffect;


            public DialogFlowerInform(int id, String flowerImagePath, String flowerName, String flowerEffect){
                this.id = id;
                this.flowerImagePath = flowerImagePath;
                this.flowerName = flowerName;
                this.flowerEffect = flowerEffect;
            }

            public int getId(){return this.id;}
            public String getFlowerImagePath(){return this.flowerImagePath;}
            public String getFlowerName(){return this.flowerName;}
            public String getFlowerEffect(){return this.flowerEffect;}

        }

    // 꽃선택창 리스트 포멧
    static class DialogFlowerViewHolder{
        ImageView dialogFlowerImage;
        TextView dialogFlowerNameText;
        TextView dialogFlowerEffectText;
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

                    v.setTag(viewHolder);
                }else{
                    viewHolder = (DialogFlowerViewHolder) v.getTag();
                }

                if(info != null){
                    viewHolder.dialogFlowerImage.setImageResource(R.mipmap.ic_launcher);
                    viewHolder.dialogFlowerNameText.setText(info.getFlowerName());
                    viewHolder.dialogFlowerEffectText.setText(info.getFlowerEffect());
                    v.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            dialogtoActid = info.getId();
                            Log.i("getid",""+info.getId());
                            dismiss();
                        }
                    });
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
