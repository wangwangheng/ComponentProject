package debug;

import android.util.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class Test {
    @Around("execution(* com.xinye.main.splash.SplashActivity.on**(..))")
    public void activityLifecycle(ProceedingJoinPoint point) throws Throwable {
        String signature = point.getSignature().toLongString();
        long before = System.currentTimeMillis();
        point.proceed();
        Log.i("wangheng",signature + "#### time:" + (System.currentTimeMillis() - before));
    }
}
