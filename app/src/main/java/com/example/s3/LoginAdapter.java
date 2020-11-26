package com.example.s3;

import android.content.Context;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginAdapter extends FragmentPagerAdapter {

    private Context context;
    int totaltabs;

  public LoginAdapter(FragmentManager fm,Context context,int totaltabs){
      super(fm);
      this.totaltabs=totaltabs;
      this.context=context;

  }


    @Override
    public Fragment getItem(int position) {
      switch(position){
          case 0:
              login_fragment logintabfragment=new login_fragment();
              return logintabfragment;
          case 1:
              MainActivity signuptabfragment =new MainActivity();
              return signuptabfragment;
          default:
              return null;
      }

    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Login";
            case 1:
                return "SignUp";
            default:
                return null;
        }
    }
        @Override
    public int getCount() {
        return 2;
    }
}
