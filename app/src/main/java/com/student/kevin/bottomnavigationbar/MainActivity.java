package com.student.kevin.bottomnavigationbar;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.LinearLayout;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.behaviour.BottomNavBarFabBehaviour;
import com.student.kevin.bottomnavigationbar.fragment.FragmentFour;
import com.student.kevin.bottomnavigationbar.fragment.FragmentOne;
import com.student.kevin.bottomnavigationbar.fragment.FragmentThree;
import com.student.kevin.bottomnavigationbar.fragment.FragmentTwo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {
    private BottomNavigationBar mBottomNavigationBar;
    private FragmentOne mFragmentOne;
    private FragmentTwo mFragmentTwo;
    private FragmentThree mFragmentThree;
    private FragmentFour mFragmentFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        /*** the setting for BadgeItem ***/

        BadgeItem badgeItem = new BadgeItem();
        badgeItem.setHideOnSelect(false)
                .setText("10")
                .setBackgroundColorResource(R.color.orange)
                .setBorderWidth(0);

        /*** the setting for BottomNavigationBar ***/

//        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
//        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        mBottomNavigationBar.setBarBackgroundColor(R.color.blue);//set background color for navigation bar
        mBottomNavigationBar.setInActiveColor(R.color.white);//unSelected icon color
        mBottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.icon_one, R.string.tab_one).setActiveColorResource(R.color.green).setBadgeItem(badgeItem))
                .addItem(new BottomNavigationItem(R.drawable.icon_two, R.string.tab_two).setActiveColorResource(R.color.orange))
                .addItem(new BottomNavigationItem(R.drawable.icon_three, R.string.tab_three).setActiveColorResource(R.color.lime))
                .addItem(new BottomNavigationItem(R.drawable.icon_four, R.string.tab_four))
                .setFirstSelectedPosition(0)    //设置默认选择item
                .initialise();    //初始化

        mBottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();


        Thread mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    try{
                        Thread.sleep(1000);
                        Log.d("qb", "  线程： " + Thread.currentThread().getName());
                        DatagramSocket theSocket = null;
                        int mPort = 8904;
                        String broadCastIP = "255.255.255.255";
                        try {
                            InetAddress server = InetAddress.getByName(broadCastIP);
                            theSocket = new DatagramSocket();
                            String data = "Hello";
                            DatagramPacket theOutput = new DatagramPacket(data.getBytes(), data.length(), server, mPort);
                            /*这一句就是发送广播了，其实255就代表所有的该网段的IP地址，是由路由器完成的工作*/
                            theSocket.send(theOutput);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (theSocket != null)
                                theSocket.close();
                        }
                        Thread.sleep(5000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
        mThread.start();    //启动线程
        Log.d("qb", "1111 ");



    }

    /**
     * set the default fragment
     */
    private void setDefaultFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mFragmentOne = FragmentOne.newInstance("First Fragment");
        transaction.replace(R.id.ll_content, mFragmentOne).commit();
    }

    @Override
    public void onTabSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                if (mFragmentOne == null) {
                    mFragmentOne = FragmentOne.newInstance("First Fragment");
                }
                transaction.replace(R.id.ll_content, mFragmentOne);
                break;
            case 1:
                if (mFragmentTwo == null) {
                    mFragmentTwo = FragmentTwo.newInstance("Second Fragment");
                }
                transaction.replace(R.id.ll_content, mFragmentTwo);
                break;
            case 2:
                if (mFragmentThree == null) {
                    mFragmentThree = FragmentThree.newInstance("Third Fragment");
                }
                transaction.replace(R.id.ll_content, mFragmentThree);
                break;
            case 3:
                if (mFragmentFour == null) {
                    mFragmentFour = FragmentFour.newInstance("Forth Fragment");
                }
                transaction.replace(R.id.ll_content, mFragmentFour);
                break;
            default:
                if (mFragmentOne == null) {
                    mFragmentOne = FragmentOne.newInstance("First Fragment");
                }
                transaction.replace(R.id.ll_content, mFragmentOne);
                break;
        }
        transaction.commit();

    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
