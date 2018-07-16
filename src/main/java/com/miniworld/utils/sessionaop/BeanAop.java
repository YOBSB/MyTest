package com.miniworld.utils.sessionaop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.miniworld.common.ReMessage;


/**
 * 通知类
 * @author nanshen
 *
 */
@Component
@Aspect
public class BeanAop {



    private final Logger log = LoggerFactory.getLogger(BeanAop.class);

    @Pointcut("within(com.miniworld.controller.FormReviewController "
    		+ "|| com.miniworld.controller.SeasonController "
    		+ "|| com.miniworld.controller.AdminController"
    		+ "|| com.miniworld.controller.WorksDataController) "
    		+ "&& !execution(* com.miniworld.controller.AdminController.login(..))"
    		+ "&& !execution(* com.miniworld.controller.AdminController.toLogin(..))"
    		+ "&& !execution(* com.miniworld.controller.AdminController.logout(..))")
    public void pointCut(){}
    
    
    
    @Pointcut("within(com.miniworld.controller.AdminController || com.miniworld.controller.SeasonController)"
    		+ "&& !execution(* com.miniworld.controller.AdminController.login(..)) "
    		+ "&& !execution(* com.miniworld.controller.AdminController.toLogin(..))"
    		+ "&& !execution(* com.miniworld.controller.AdminController.logout(..))"
    		+ "&& !execution(* com.miniworld.controller.AdminController.index(..))"
            + "|| execution(* com.miniworld.controller.HonorTopController.pri*(..))")
    public void authorityPointCut(){}
    //权限判断
    @Around("authorityPointCut()")
    public Object isSupperAdmin(ProceedingJoinPoint pjp) throws Throwable {
		Integer adminRoleId = (Integer) SysContent.getSession().getAttribute("adminRoleId");
        if(adminRoleId==null||adminRoleId==0) {
        	String returnType = ((MethodSignature)pjp.getSignature()).getReturnType().getSimpleName().toString();
			SysContent.getSession().invalidate();
        	if(returnType.equals("String")){
        		String redirectStr = "admin/login";
                return redirectStr;
            }else if(returnType.equals("Map")){
                Map<String,Object> resMap = new HashMap<>();
                resMap.put("resCode", "权限不足不能操作");
                return resMap;
            }else if(returnType.equals("ReMessage")){
            	return new ReMessage<>(1,"权限不足，无法执行该操作");
            }else{
                ModelAndView mv=new ModelAndView();
                mv.setViewName("admin/login");
                return mv;
            }
        }else {
        	Object args[] = pjp.getArgs();
        	Object result = null;
        	try {
        		result = pjp.proceed(args);
        		System.out.println(result);
        	} catch (Throwable e) {
        		e.printStackTrace();
        	}
        	return result;
        }
    }
    
    
    //登录判断
    @Around("pointCut()")
    public Object trackInfo(ProceedingJoinPoint pjp) throws Throwable {
        
        String  keyChain= (String)SysContent.getSession().getAttribute("keyChain");
        String adminLoginName=(String)SysContent.getSession().getAttribute("adminLoginName");
        Integer adminRoleId=(Integer)SysContent.getSession().getAttribute("adminRoleId");
        
        Object args[] = pjp.getArgs();
        Object result = null;
        if(adminRoleId==null||adminLoginName==null){
        	String ss = ((MethodSignature)pjp.getSignature()).getReturnType().getSimpleName().toString();
           	System.out.println(ss);
               String redirectStr = "admin/login";
               String returnType = ss;
               if(returnType.equals("String")){
                   return redirectStr;
               }else if(returnType.equals("Map")){
                   Map<String,Object> resMap = new HashMap<>();
                   resMap.put("resCode", "未登录不能操作");
                   return resMap;
               }else if(returnType.equals("ReMessage")){
               	return new ReMessage<>(1,"未登录不能操作");
               }else{
                   ModelAndView mv=new ModelAndView();
                   mv.setViewName("admin/login");
                   return mv;
               }
        }else{
        	if(adminRoleId==1){
        		if(keyChain!=null){
        			try {
   	                 result = pjp.proceed(args);
   	                 System.out.println(result);
   	             } catch (Throwable e) {
   	                 e.printStackTrace();
   	             }
        		}
        	}else if(adminRoleId==0){
        		try {
	                 result = pjp.proceed(args);
	                 System.out.println(result);
	             } catch (Throwable e) {
	                 e.printStackTrace();
	             }
        	}else{
        		String ss = ((MethodSignature)pjp.getSignature()).getReturnType().getSimpleName().toString();
	        	System.out.println(ss);
	            String redirectStr = "admin/login";
	            String returnType = ss;
	            if(returnType.equals("String")){
	                return redirectStr;
	            }else if(returnType.equals("Map")){
	                Map<String,Object> resMap = new HashMap<>();
	                resMap.put("resCode", "未登录不能操作");
	                return resMap;
	            }else if(returnType.equals("ReMessage")){
	            	return new ReMessage<>(1,"未登录不能操作");
	            }else{
	                ModelAndView mv=new ModelAndView();
	                mv.setViewName("admin/login");
	                return mv;
	            }
        		
        
        	}
        	
        
        }
		return result;
    }
}