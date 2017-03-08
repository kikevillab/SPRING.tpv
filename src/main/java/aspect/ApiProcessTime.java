package aspect;

import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ApiProcessTime {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void allResources() {
    }


    @Around("allResources()")
    public Object processTime(ProceedingJoinPoint pjp) throws Throwable {
        Calendar before = Calendar.getInstance();
        Object obj = pjp.proceed();
        Calendar now = Calendar.getInstance();
        LogManager.getLogger(pjp.getSignature().getDeclaringTypeName())
                .info("Processing time: " + (now.getTimeInMillis() - before.getTimeInMillis()) + "ms");
        return obj;
    }

}
