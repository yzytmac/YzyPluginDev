package yzy.stkj.com.plugincore;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

public class ProxyActivity extends AppCompatActivity {


    private PluginInterface mPluginInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy);
        Intent vIntent = getIntent();
        String className = vIntent.getStringExtra("className");
        launchActivity(className);
    }

    private void launchActivity(String className){
        try {
            Log.e("yzy", "launchActivity: " + className);
            Class<?> activityClass = PluginManger.getInstance(this).getDexClassLoader().loadClass(className);
            Constructor<?> vConstructor = activityClass.getConstructor(new Class[]{});
            Object activity = vConstructor.newInstance(new Object[]{});
            mPluginInterface = (PluginInterface) activity;
            mPluginInterface.onattach(this);//相当于初始化that上下文
            Bundle vBundle = new Bundle();
            mPluginInterface.onCreate(vBundle);
        } catch (ClassNotFoundException pE) {
            pE.printStackTrace();
        } catch (NoSuchMethodException pE) {
            pE.printStackTrace();
        } catch (IllegalAccessException pE) {
            pE.printStackTrace();
        } catch (InstantiationException pE) {
            pE.printStackTrace();
        } catch (InvocationTargetException pE) {
            pE.printStackTrace();
        }

    }

    /**
     * 重写此方法，得到的Resources就是插件中的了
     * @return
     */
    @Override
    public Resources getResources() {
        return PluginManger.getInstance(this).getResources();
    }

    @Override
    protected void onResume() {
        mPluginInterface.onResume();
    }

    @Override
    protected void onRestart() {
        mPluginInterface.onRestart();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        mPluginInterface.onNewIntent(intent);
    }

    @Override
    protected void onStart() {
        mPluginInterface.onStart();
    }

    @Override
    protected void onPause() {
        mPluginInterface.onPause();
    }

    @Override
    protected void onStop() {
        mPluginInterface.onStop();
    }

    @Override
    protected void onDestroy() {
        mPluginInterface.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mPluginInterface.onSaveInstanceState(outState);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mPluginInterface.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        mPluginInterface.onBackPressed();
    }
}
