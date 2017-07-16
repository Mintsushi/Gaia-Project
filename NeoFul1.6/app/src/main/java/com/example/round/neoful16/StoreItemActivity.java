package com.example.round.neoful16;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Round on 2017-07-16.
 */

public class StoreItemActivity extends Fragment{

    private ArrayList<ItemInfo> mArray = new ArrayList<>();
    private ListView mList;
    private ItemAdapter mAdapter;
    private ImageLoader mImageLoader;
    private RequestQueue requestQueue;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_store_item,container,false);

        Cache cache = new DiskBasedCache(getContext().getCacheDir(),1024*1024);
        Network network = new BasicNetwork(new HurlStack());

        requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.start();

        mAdapter = new ItemAdapter(getContext(),R.layout.item_item);
        mList = (ListView)view.findViewById(R.id.itemlv);
        mList.setAdapter(mAdapter);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                StoreItemInformationActivity itemInform = new StoreItemInformationActivity();
                // StoreItemInformationActivity class 에게 현제 구매한 아이템 넘김
                itemInform.setItem(mArray.get(i));
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.FL,itemInform)
                        .commit();
            }
        });

        mImageLoader = new ImageLoader(requestQueue,new LruBitmapCache(LruBitmapCache.getCacheSize(getContext())));

        requestItem();
        return view;

    }

    private void requestItem(){

        String url="http://202.31.200.143/item";

        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>(){

                    @Override
                    public void onResponse(JSONArray response){
                        Log.i("StoreItemActivity",response.toString());

                        for(int i=0;i<response.length();i++){

                            try{
                                JSONObject object = response.getJSONObject(i);

                                int id = object.getInt("itemID");
                                String name = object.getString("itemName");
                                String info = object.getString("itemExplain");
                                String path = object.getString("itemImagePath");
                                int cost = object.getInt("seedPrice");
                                mArray.add(new ItemInfo(id,name,info,path,cost));

                            }catch (JSONException e){
                                Log.i("StoreItemActivity",e.toString());
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.i("StoreItemActivity",error.toString());
            }
        });

        requestQueue.add(request);
    }

    // 아이템 기초 데이터 포멧
    public class ItemInfo{

        private int id;
        private String name;
        private String info;
        private String path;
        private int cost;

        public ItemInfo(int id,String name, String info, String path,int cost){
            this.id = id;
            this.name = name;
            this.info = info;
            this.path = path;
            this.cost = cost;
        }

        public String getName(){return this.name;}
        public String getInfo(){return this.info;}
        public String getPath(){return this.path;}
        public int getCost(){return this.cost;}
        public int getId(){return this.id;}
    }

    // 아이템 리스트 포멧
    static class ItemViewHolder{
        NetworkImageView itemImage;
        TextView itemName;
        TextView itemInfo;
        TextView itemCost;
    }

    public class ItemAdapter extends ArrayAdapter<ItemInfo> {
        private LayoutInflater mInflater = null;

        public ItemAdapter(Context context, int resource){
            super(context,resource);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount(){
            return mArray.size();
        }

        @Override
        public View getView(int position, View v, ViewGroup parent){
            ItemViewHolder viewHolder;

            if(v == null){
                v=mInflater.inflate(R.layout.item_item,parent,false);
                viewHolder = new ItemViewHolder();

                viewHolder.itemImage=(NetworkImageView) v.findViewById(R.id.item);
                viewHolder.itemName=(TextView) v.findViewById(R.id.itemName);
                viewHolder.itemInfo=(TextView)v.findViewById(R.id.itemInfo);
                viewHolder.itemCost=(TextView)v.findViewById(R.id.itemCost);

                v.setTag(viewHolder);
            }else{
                viewHolder = (ItemViewHolder)v.getTag();
            }

            ItemInfo info = mArray.get(position);

            if(info != null){
                int id = getResources().getIdentifier(info.getPath(),"drawable",getActivity().getPackageName());
                viewHolder.itemImage.setImageUrl("http://202.31.200.143/"+info.getPath(),mImageLoader);
                viewHolder.itemName.setText(info.getName());
                viewHolder.itemInfo.setText(info.getInfo());
                viewHolder.itemCost.setText(Integer.toString(info.getCost()));
            }

            return v;
        }
    }

}
