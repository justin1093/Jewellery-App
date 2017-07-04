package com.example.justin.jewellery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

public class MainActivity extends Activity implements BeaconConsumer {

    public static final String TAG = "BeaconsEverywhere";
    private BeaconManager beaconManager;

    ListView list;
    String[] itemname ={
            "Capital Alloy Jewel Set ",
            "Zaveri Pearls Zinc Jewel Set",
            "Meenaz Classic Solitaire Alloy Ring",
            "Yellow Chimes Copper Zircon Rhodium Bracelet",
            "Guru Diva Style Zircon Alloy Huggie Earring",
            "Zaveri Pearls Zinc Jewel Set ",
            "Meenaz Classic Solitaire Alloy Ring",
            "Atasi International Alloy Jewel Set ",
            "Guru Alloy Jewel Set",
            "Atasi International Alloy Jewel Set ",
            "Sukkhi Alloy Jewel Set",
            "Zaveri Pearls Zinc Jewel Set",
    };
    String[] itemdis ={
            "Capital Alloy Jewel Set ",
            "Zaveri Pearls Zinc Jewel Set",
            "Meenaz Classic Solitaire Alloy Ring",
            "Yellow Chimes Copper Zircon Rhodium Bracelet",
            "Guru Diva Style Zircon Alloy Huggie Earring",
            "Zaveri Pearls Zinc Jewel Set ",
            "Meenaz Classic Solitaire Alloy Ring",
            "Atasi International Alloy Jewel Set ",
            "Guru Alloy Jewel Set",
            "Atasi International Alloy Jewel Set ",
            "Sukkhi Alloy Jewel Set",
            "Zaveri Pearls Zinc Jewel Set",
    };

    Integer[] imgid={
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4,
            R.drawable.pic5,
            R.drawable.pic6,
            R.drawable.pic7,
            R.drawable.pic8,
            R.drawable.pic9,
            R.drawable.pic10,
            R.drawable.pic11,
            R.drawable.pic12,

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {











        beaconManager = BeaconManager.getInstanceForApplication(this);

        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));

        beaconManager.bind(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomListAdapter adapter=new CustomListAdapter(this, itemname, imgid);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem= itemname[+position];
        //        Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();



            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        final Region region = new Region("myBeacons", Identifier.parse("F001889B-7509-4C31-A905-1A27039C003C"), null, null);

        beaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                try {
                    Log.d(TAG, "didEnterRegion");
                    beaconManager.startRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void didExitRegion(Region region) {
                try {
                    Log.d(TAG, "didExitRegion");
                    beaconManager.stopRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void didDetermineStateForRegion(int i, Region region) {

            }
        });

        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                for(Beacon oneBeacon : beacons) {
                    Log.d(TAG, "distance: " + oneBeacon.getDistance() + " id:" + oneBeacon.getId1() + "/" + oneBeacon.getId2() + "/" + oneBeacon.getId3());


               //     Toast.makeText(getBaseContext(),Double.toString( oneBeacon.getDistance() ), Toast.LENGTH_LONG).show();


                }
            }
        });

        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    Log.i(TAG, "The first beacon I see is about " + beacons.iterator().next().getDistance() + " meters away.");


                    Intent intent = new Intent(getApplicationContext(), ObjectActivity.class);
                    // beacon identifier should give the object name
                    String ObjectId = "213";//beacon identifier
                    intent.putExtra("OBJECT_ID", ObjectId);
                    startActivity(intent);


                }
            }


        });
        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {    }


//        try {
//            beaconManager.startMonitoringBeaconsInRegion(region);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }

    }


}





