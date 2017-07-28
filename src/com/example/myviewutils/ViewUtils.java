package com.example.myviewutils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class ViewUtils {
	
	public static void inject(Activity activity){
		bindView(activity);
		bindClick(activity);
	}

	private static void bindView(Activity activity) {
		/**
		 * 1. 获取Activity的字节码
		 */
		Class<Activity> clazz = (Class<Activity>) activity.getClass();
		/**
		 * 2. 获取字节码中的所有字段
		 */
		Field[] fields = clazz.getDeclaredFields();
		/**
		 * 3. 遍历fields，找出字段上有注解的字段
		 */
		for(Field field : fields){
			/**
			 * 4. 判断field上有没有ViewInject注解对象
			 */
			ViewInject viewInject = field.getAnnotation(ViewInject.class);
			/**
			 * 5. 如果viewInject==null，则不用处理当前字段，否则则需要处理
			 */
			if (viewInject!=null) {
				/**
				 * 6. 获取ViewInject对象的值（resId）
				 */
				int resId = viewInject.value();
				/**
				 * 7. 根据 resId找到对应的控件
				 */
				View view = activity.findViewById(resId);
				
				/**
				 * 8. 设置field可访问（暴力反射）
				 */
				field.setAccessible(true);
				/**
				 * 9. 将控件view设置给field
				 */
				try {
					field.set(activity, view);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void bindClick(final Activity activity) {
		/**
		 * 1. 获取对象的字节码
		 */
		Class<Activity> clazz = (Class<Activity>) activity.getClass();
		/**
		 * 2. 获取字节码中的所有方法
		 */
		Method[] methods = clazz.getDeclaredMethods();
		/**
		 * 3. 遍历方法们
		 */
		for(final Method method : methods){
			/**
			 * 4. 获取method上的OnClick注解
			 */
			OnClick onClick = method.getAnnotation(OnClick.class);
			/**
			 * 5.判断OnClick是否为空
			 */
			if (onClick!=null) {
				/**
				 * 6. 获取onClick对象的值（resIds）
				 */
				int[] resIds = onClick.value();
				/**
				 * 7. 遍历resIds
				 */
				for(int resId : resIds){
					/**
					 * 8. 从Activity身上找到resId对应的控件
					 */
					View view = activity.findViewById(resId);
					/**
					 * 9. 给view绑定点击监听器
					 */
					view.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							/**
							 * 10. 让method支持暴力反射
							 */
							method.setAccessible(true);
							/**
							 * 11. 反射调用method方法
							 */
							try {
								method.invoke(activity, v);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
				
			}
		}
		
	}
}
