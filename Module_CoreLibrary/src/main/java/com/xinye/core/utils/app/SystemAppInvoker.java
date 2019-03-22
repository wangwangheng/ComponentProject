package com.xinye.core.utils.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.xinye.core.log.Logger;

/**
 * 系统App调用
 *
 * @author wangheng
 */
public class SystemAppInvoker {
    private static final String TAG = "SystemAppInvoker";
    /**
     * 打开拨号盘
     * @param context activity
     * @param phone phone
     */
    public static boolean openDialUI(Context context,String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        try {
            if (canResolveActivity(context,intent)) {
                if(!(context instanceof Activity)){
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                context.startActivity(intent);
                return true;
            }
        }catch (Exception e){
            Logger.e(TAG,"打开拨号盘失败:" + e);
        }
        return false;
    }

    private static boolean canResolveActivity(Context context ,Intent intent){
        if(intent == null || context == null){
            return false;
        }
        return intent.resolveActivity(context.getPackageManager()) != null;
    }
}
