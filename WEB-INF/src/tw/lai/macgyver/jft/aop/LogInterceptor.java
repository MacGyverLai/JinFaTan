package tw.lai.macgyver.jft.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import tw.lai.macgyver.jft.util.MacLogger;
import tw.lai.macgyver.jft.util.VoToString;

@Aspect
@Service
public class LogInterceptor {
	
	private static String DA = ".";
	
	private String indent = "";

	@Around(value = "execution(* tw.lai.macgyver.jft.module..*.*(..))")
	public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
		Object result = null;
		MacLogger macLog = new MacLogger();
		
		String invokeMethod = pjp.getSignature().getDeclaringType()
				.getSimpleName() + DA + pjp.getSignature().getName();
		
		macLog.log(this.indent + "into " + invokeMethod + "....");
		this.indent += "    ";
		macLog.log(this.indent + "Argument List :");
		Object[] arguments = pjp.getArgs();
		for (Object argument : arguments)
			if (argument != null)
				macLog.log(this.indent + VoToString.toString(argument));
		try {
			result = pjp.proceed();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			macLog.log(ex);
			throw ex;
		} finally {
			if ((this.indent.length() - 4) < 0)
				this.indent = "";
			else
				this.indent = this.indent.substring(0, this.indent.length() - 4);
			macLog.log(this.indent + "end method..." + invokeMethod);
		}
		
		return result;
	}
}
