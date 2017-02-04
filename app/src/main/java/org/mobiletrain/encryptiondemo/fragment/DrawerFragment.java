package org.mobiletrain.encryptiondemo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.mobiletrain.encryptiondemo.R;

public class DrawerFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    private MD5Fragment md5Fragment;
    private FragmentManager fragmentManager;
    private Base64Fragment base64Fragment;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getFragmentManager();
        md5Fragment = new MD5Fragment();
        base64Fragment = new Base64Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drawer, container, false);
        navigationView = (NavigationView) rootView.findViewById(R.id.drawer_fragment);
        navigationView.setNavigationItemSelectedListener(this);
        return rootView;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (item.getItemId()) {
            case R.id.md5_item:
                fragmentTransaction.replace(R.id.content_container, md5Fragment);
                break;
            case R.id.base64_item:
                fragmentTransaction.replace(R.id.content_container, base64Fragment);
                break;
            case R.id.des_item:
                fragmentTransaction.replace(R.id.content_container, new DESFragment());
                break;
            case R.id.rsa_item:
                fragmentTransaction.replace(R.id.content_container, new RSAFragment());
                break;
            case R.id.sign_item:
                fragmentTransaction.replace(R.id.content_container, new SignFragment());
                break;
        }
        fragmentTransaction.commit();
        listener.onDrawerClose();
        return false;
    }

    public interface OnDrawerCloseListener {
        public void onDrawerClose();
    }

    private OnDrawerCloseListener listener;

    public void setListener(OnDrawerCloseListener listener) {
        this.listener = listener;
    }
}
