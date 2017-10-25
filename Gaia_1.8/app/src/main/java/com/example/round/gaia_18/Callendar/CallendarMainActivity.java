package com.example.round.gaia_18.Callendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.round.gaia_18.Callendar.cal.OneDayView;
import com.example.round.gaia_18.Data.ScheduleItem;
import com.example.round.gaia_18.Dialog.Setting;
import com.example.round.gaia_18.MainActivity;
import com.example.round.gaia_18.R;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.round.gaia_18.MainActivity.mOverlayService;
import static com.example.round.gaia_18.OverlayService.dataBaseHelper;
import static com.example.round.gaia_18.OverlayService.dataList;

public class CallendarMainActivity extends FragmentActivity {
    private static final String TAG = com.example.round.gaia_18.Callendar.MConfig.TAG;
    private static final String NAME = "MainActivity";

    private static final String INTENT_ACTION = "com.example.round.gaia_18.Callendar";
    private final String CLASS = NAME + "@" + Integer.toHexString(hashCode());
    private TextView thisMonthTv;
    private OneDayView tempView;
    private ListView scheduleList;
    private AlarmManager mManager;

    private ImageView setting;
    //    private ImageView menu;
    public static TextView seed, fruit;
    public static ImageView weather;

    public static String AlarmLogTitle,AlarmLogMemo;
    Button button;
    public static ArrayList<ScheduleItem> schLists = new ArrayList();
    ArrayList<ScheduleItem> schTodayLists = new ArrayList();
    ScheduleItemAdapter scheduleItemAdapter;

    @Override
    protected void onResume(){
        super.onResume();

        if(mOverlayService != null){
            mOverlayService.invisible();

            weather.setImageDrawable(MainActivity.weather.getDrawable());
            seed.setText(dataList.getAllScore(dataList.getScoreHashMap()));
            fruit.setText(dataList.getAllScore(dataList.getFruitHashMap()));
            mOverlayService.setSeed();

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cal_activity_main);

        seed = (TextView)findViewById(R.id.seed);
        fruit = (TextView)findViewById(R.id.fruit);
        weather = (ImageView)findViewById(R.id.weather);
        setting = (ImageView)findViewById(R.id.setting);

        weather.setImageDrawable(MainActivity.weather.getDrawable());

        seed.setText(dataList.getAllScore(dataList.getScoreHashMap()));
        fruit.setText(dataList.getAllScore(dataList.getFruitHashMap()));
        mOverlayService.setSeed();

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting dialog = new Setting(CallendarMainActivity.this);
                dialog.show();
            }
        });

        Button addButton = (Button) findViewById(R.id.main_add_bt);
        thisMonthTv = (TextView) findViewById(R.id.this_month_tv);

        schLists = dataBaseHelper.getAllDataForamt();

        scheduleList = (ListView)findViewById(R.id.scheduleList);
        scheduleList.setVisibility(View.INVISIBLE);
        com.example.round.gaia_18.Callendar.MonthlyFragment mf = (com.example.round.gaia_18.Callendar.MonthlyFragment) getSupportFragmentManager().findFragmentById(R.id.monthly);
        mf.setOnMonthChangeListener(new com.example.round.gaia_18.Callendar.MonthlyFragment.OnMonthChangeListener() {
            
            @Override
            public void onChange(int year, int month) {
                thisMonthTv.setText(year + "." + (month + 1));
            }

            @Override
            public void onDayClick(OneDayView dayView) {
                if(tempView!=null){
                    tempView.setViewDraw(0);
                }

                tempView = dayView;
                dayView.setViewDraw(2);
                //Toast.makeText(CallendarMainActivity.this, "Click  " + (dayView.get(Calendar.MONTH)+1) + "/" + dayView.get(Calendar.DAY_OF_MONTH), Toast.LENGTH_SHORT).show();

                schTodayLists.clear();
                if(getSchList().size()!=0){
                    for(int i = 0; i<getSchList().size(); i++){
                        if(getSchList().get(i).getToYears() ==tempView.get(Calendar.YEAR) && getSchList().get(i).getToMonths() == (1+tempView.get(Calendar.MONTH)) && getSchList().get(i).getToDays() == tempView.get(Calendar.DAY_OF_MONTH)) {
                            schTodayLists.add(getSchList().get(i));
                        }
                    }

                    scheduleList = (ListView)findViewById(R.id.scheduleList);

                    if(schTodayLists.size()!=0){
                        scheduleItemAdapter = new ScheduleItemAdapter(CallendarMainActivity.this, R.layout.cal_schedule_list_item, schTodayLists);
                        scheduleList.setAdapter(scheduleItemAdapter);
                        scheduleList.setVisibility(View.VISIBLE);
                    }
                    else{

                        scheduleList.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                if(schTodayLists.size()!=0){
                    dataBaseHelper.DataFormatDelete(schTodayLists.get(0).getId(),schTodayLists.get(0).getToYears(),schTodayLists.get(0).getToMonths(),schTodayLists.get(0).getToDays());
                    schLists.remove(schTodayLists.get(0));
                    schTodayLists.remove(schTodayLists.get(0));
                    scheduleItemAdapter = new ScheduleItemAdapter(CallendarMainActivity.this, R.layout.cal_schedule_list_item, schTodayLists);
                    scheduleList.setAdapter(scheduleItemAdapter);
                    if(schTodayLists.size()==0){
                        tempView.setEventDraw(0);
                        scheduleList.setVisibility(View.INVISIBLE);
                    }

                }
                else{
                    Toast.makeText(CallendarMainActivity.this, "Delete Error  " + "일정이 없습니다. ^^", Toast.LENGTH_SHORT).show();

                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tempView!=null){
                    // 커스텀 다이얼로그 생성
                final ScheduleDialog dialog = new ScheduleDialog(view.getContext());
                    // 다이얼로그 생성시 다이얼로그창에 정보 보내는거
                    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            int yy = tempView.get(Calendar.YEAR);
                            int mm = (tempView.get(Calendar.MONTH)+1);
                            int dd = tempView.get(Calendar.DAY_OF_MONTH);
                            dialog.setToday(yy,mm,dd);
                        }
                    });
                    // 다이얼로그 종료시 동작하는 부분
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dia) {
                            if(dialog.getDismissType()==1){

                                schLists.add(dialog.getScheduleISet());
                                schTodayLists.add(dialog.getScheduleISet());
                                dataBaseHelper.DataFormatInsert(schLists.size(), dialog.getScheduleISet());
                                scheduleItemAdapter = new ScheduleItemAdapter(CallendarMainActivity.this, R.layout.cal_schedule_list_item, schTodayLists);
                                scheduleList.setAdapter(scheduleItemAdapter);
                                tempView.setEventDraw(1);
                                scheduleList.setVisibility(View.VISIBLE);

                                new AlarmHATT(getApplicationContext()).Alarm(dialog.getScheduleISet());

                            }
                        }
                    });
                    dialog.show();
                }
            }

        });


    }

    public ArrayList<ScheduleItem> getSchList(){
        return schLists;
    }



    public class ScheduleItemViewHolder{
        TextView schItemTitleText;
        TextView schItemMemoText;
    }

    public class ScheduleItemAdapter extends ArrayAdapter<ScheduleItem> {
        private LayoutInflater mInflater = null;
        ArrayList<ScheduleItem> todayInfo;
        public ScheduleItemAdapter(Context context, int resource,  ArrayList<ScheduleItem> info) {

            super(context, resource);
            Log.i("main ","Adapter Click10");
            mInflater = LayoutInflater.from(context);
            Log.i("main ","Adapter Click11");
            todayInfo = info;
            Log.i("main ","Adapter Click12");
        }

        public  ArrayList<ScheduleItem> getTodayList(){
            return todayInfo;
        }

        @Override
        public int getCount() {
            return getTodayList().size();
        }

        @Override
        public View getView(int position, View view, final ViewGroup parent) {
            final ScheduleItem info = getTodayList().get(position);
            final ScheduleItemViewHolder viewholder;

            if(view == null){

                view = mInflater.inflate(R.layout.cal_schedule_list_item,parent,false);

                viewholder = new ScheduleItemViewHolder();
                viewholder.schItemTitleText = (TextView)view.findViewById(R.id.schItemTitleText);
                viewholder.schItemMemoText = (TextView)view.findViewById(R.id.schItemMemoText);

                view.setTag(viewholder);
            }else{
                viewholder = (ScheduleItemViewHolder)view.getTag();
            }

            if(info != null){
                String thour, tminute;
                if(info.getTimeHours()<10){ thour = "0"+info.getTimeHours(); }
                else{ thour = ""+info.getTimeHours(); }
                if(info.getTimeMinutes()<10){ tminute = "0"+info.getTimeMinutes(); }
                else{ tminute = ""+info.getTimeMinutes(); }
                viewholder.schItemTitleText.setText(info.getTitle() + "  ( "+ thour + " : " + tminute + " )");
                viewholder.schItemMemoText.setText(info.getMemo());

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 이밴트
                    }
                });
            }

            return view;
        }
    }

    public class AlarmHATT {
        private Context context;
        public AlarmHATT(Context context) {
            this.context=context;
        }
        public void Alarm(ScheduleItem scheduleItem) {
            AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, BrodcastD.class);

            PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);

            Calendar calendar = Calendar.getInstance();
            //알람시간 calendar에 set해주기

            AlarmLogTitle = scheduleItem.getTitle();
            AlarmLogMemo = scheduleItem.getMemo();
            long after;

            //알람시간 calendar에 set해주기
            Log.i("Alram Set : ",""+scheduleItem.getToYears()+" / "+scheduleItem.getToMonths()+" / "+scheduleItem.getToDays()+" / "+scheduleItem.getTimeHours()+" / "+scheduleItem.getTimeMinutes()+" / "+scheduleItem.getAlarms());
            long t = SystemClock.elapsedRealtime();
            if(scheduleItem.getAlarms()==1){
                //10분
                calendar.set(scheduleItem.getToYears(),scheduleItem.getToMonths(),scheduleItem.getToDays(),scheduleItem.getTimeHours(),(scheduleItem.getTimeMinutes()-10));
                Log.i("Time 1", ""+calendar.getTime());

                am.setExact (AlarmManager.RTC_WAKEUP,  calendar.getTimeInMillis(), sender);
            }
            else if(scheduleItem.getAlarms()==2){
                //30분
                calendar.set(scheduleItem.getToYears(),scheduleItem.getToMonths(),scheduleItem.getToDays(),scheduleItem.getTimeHours(),(scheduleItem.getTimeMinutes()-30));
                Log.i("Time 1", ""+calendar.getTime());
                am.setExact (AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
            }
            else if(scheduleItem.getAlarms()==3){
                //1시간
                calendar.set(scheduleItem.getToYears(),scheduleItem.getToMonths(),scheduleItem.getToDays(),(scheduleItem.getTimeHours()-1),(scheduleItem.getTimeMinutes()));
                Log.i("Time 1", ""+calendar.getTime());
                am.setExact (AlarmManager.RTC_WAKEUP, (t*60*60*1000), sender);
            }
            else if(scheduleItem.getAlarms()==4){
                //2시간
                calendar.set(scheduleItem.getToYears(),scheduleItem.getToMonths(),scheduleItem.getToDays(),(scheduleItem.getTimeHours()-2),(scheduleItem.getTimeMinutes()));
                Log.i("Time 1", ""+calendar.getTime());
                am.setExact (AlarmManager.RTC_WAKEUP, (t*60*60*2000), sender);
            }
            else if(scheduleItem.getAlarms()==5){
                //하루
                calendar.set(scheduleItem.getToYears(),scheduleItem.getToMonths(),(scheduleItem.getToDays()-1),(scheduleItem.getTimeHours()),(scheduleItem.getTimeMinutes()));
                Log.i("Time 1", ""+calendar.getTime());
                am.setExact (AlarmManager.RTC_WAKEUP, (t*60*10000), sender);
            }else{

                calendar.set(scheduleItem.getToYears(),scheduleItem.getToMonths(),scheduleItem.getToDays(),scheduleItem.getTimeHours(),(scheduleItem.getTimeMinutes()));
                calendar.set(Calendar.HOUR_OF_DAY,scheduleItem.getTimeHours() ); calendar.set(Calendar.MINUTE, scheduleItem.getTimeMinutes());

                long s = calendar.getTimeInMillis();
                Log.i("Time 1", ""+t +" / "+ s + " / " + (t*5000));

                am.setExact (AlarmManager.RTC_WAKEUP, (t*5000), sender);
            }
        }
    }



}
