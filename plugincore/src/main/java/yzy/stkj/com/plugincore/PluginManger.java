package yzy.stkj.com.plugincore;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class PluginManger {
    private DexClassLoader mDexClassLoader;
    private Context mContext;
    private Resources mResources;

    private static PluginManger ourInstance;
    private PackageInfo mPackageInfo;

    public static PluginManger getInstance(Context pContext) {
        if(ourInstance==null) {
            ourInstance = new PluginManger(pContext);
        }
        return ourInstance;
    }

    private PluginManger(Context pContext) {
        mContext=pContext;
    }

    public void loadPlugin(File plugin){
        String path=plugin.getAbsolutePath();
        File vDex = mContext.getDir("dex", Context.MODE_PRIVATE);
        mDexClassLoader = new DexClassLoader(path,vDex.getAbsolutePath(),null,mContext.getClassLoader());
        try {
            //调用addAssetPath方法来加载插件中的资源，然后得到的vAssetManager里面装的就是插件的资源了。
            AssetManager vAssetManager = AssetManager.class.newInstance();
            Method vAddAssetPath = AssetManager.class.getMethod("addAssetPath", String.class);
            vAddAssetPath.setAccessible(true);
            vAddAssetPath.invoke(vAssetManager,path);
            //原本的vResources,需要用到一些屏幕信息和配置信息。
            Resources vResources = mContext.getResources();
            //此时就得到了一个加载了插件中资源的mResources
            mResources=new Resources(vAssetManager,vResources.getDisplayMetrics(),vResources.getConfiguration());
            //获取插件中的包信息
            PackageManager vPackageManager = mContext.getPackageManager();
            mPackageInfo = vPackageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        } catch (InstantiationException pE) {
            pE.printStackTrace();
        } catch (IllegalAccessException pE) {
            pE.printStackTrace();
        } catch (NoSuchMethodException pE) {
            pE.printStackTrace();
        } catch (InvocationTargetException pE) {
            pE.printStackTrace();
        }
    }

    public DexClassLoader getDexClassLoader() {
        return mDexClassLoader;
    }

    public Resources getResources() {
        return mResources;
    }

    public PackageInfo getPackageInfo() {
        return mPackageInfo;
    }
}
