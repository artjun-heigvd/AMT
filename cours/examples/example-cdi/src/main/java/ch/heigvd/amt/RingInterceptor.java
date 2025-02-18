package ch.heigvd.amt;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Ring
@Interceptor
public class RingInterceptor {

    @AroundInvoke
    public Object trackBorrowing(InvocationContext ic) throws Exception {
        System.out.println("Ding dong!");
        return ic.proceed();
    }

}

