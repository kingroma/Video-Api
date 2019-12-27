package com.mydefault.app.common.util;

import java.util.Map;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

@SuppressWarnings({"rawtypes","unchecked"})
public class ApplicationContextUtils {

	public static <T> T getBeanByType(ApplicationContext applicationContext, Class<T> clazz) {
		return getBeanBy(applicationContext, clazz, 2, 0);
	}
		
	/** KISA 재귀함수 방지 */
	public static <T> T getBeanBy(ApplicationContext applicationContext, Class<T> clazz, int maxCnt, int loopCnt ) {
		Map beanMap = null;
		try{
			beanMap = applicationContext.getBeansOfType(clazz);
			int size = beanMap.size();
			if (size == 0) {
				if (maxCnt >= loopCnt && applicationContext.getParent() != null){
					return getBeanBy(applicationContext.getParent(), clazz, maxCnt, loopCnt++);
				}else{
					throw new NoSuchBeanDefinitionException(clazz.getSimpleName());
				}
			}
			if (size > 1){
				throw new NoSuchBeanDefinitionException("No unique bean definition type [" + clazz.getSimpleName() + "]");
			}
		
		} catch(NoSuchBeanDefinitionException ebe){ 
			StringUtil.exceptionMsg(ApplicationContextUtils.class, ebe);
			throw ebe;
		} catch (Exception e) {
			StringUtil.exceptionMsg(ApplicationContextUtils.class, e);
			try {
				throw e;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}	
		return (T) beanMap.values().iterator().next();	
	}
}
