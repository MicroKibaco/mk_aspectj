package com.github.microkibaco.mk_aspectj;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author 杨正友(小木箱)于 2020/10/7 15 18 创建
 * @Email: yzy569015640@gmail.com
 * @Tel: 18390833563
 * @function description:
 */

@Aspect
public class MkOnClickListenerAspectJ {

    private String executionStr = "execution(* *())";

    @Pointcut("execution(void android.view.View.OnClickListener.onClick(..))")
    public void fastClickViewPointcut(JoinPoint point) {

        Log.e("fastClickViewPointcut", "------------------");

    }


    @Before("execution(void android.content.DialogInterface.OnClickListener.onClick(..))")
    public void fastClickViewBefore(JoinPoint point) {

        Log.e("fastClickViewBefore", "------------------");
    }


    @After("execution(void android.support.v7.app.AppCompatViewInflater.DeclaredOnClickListener.onClick(..))")
    public void fastClickViewAfter(JoinPoint point) {

        Log.e("fastClickViewAfter", "------------------");
    }


    @Around("execution(* android.view.View.OnClickListener.onClick(..))")
    public void fastClickViewAround(ProceedingJoinPoint point) throws Throwable {


        long startNanoTime = System.nanoTime();

        Object proceed = point.proceed();
        Log.e("AspectJ", "proceed" );

        long stopNanoTime = System.nanoTime();
        MethodSignature signature = (MethodSignature) point.getSignature();

        // 方法名
        String name = signature.getName();
        Log.e("AspectJ", "proceed" + name);

        Method method = signature.getMethod();
        Log.e("AspectJ", method.toGenericString());

        // 返回值类型
        Class returnType = signature.getReturnType();
        Log.e("AspectJ", returnType.getSimpleName());

        Class declaringType = signature.getDeclaringType();
        Log.e("AspectJ", declaringType.getSimpleName());

        Class signatureDeclaringType = signature.getDeclaringType();
        Log.e("AspectJ", signatureDeclaringType.getSimpleName());

        Class declaringType1 = signature.getDeclaringType();
        Log.e("AspectJ", declaringType1.getSimpleName());

        Class[] parameterTypes = signature.getParameterTypes();

        for (Class parameterType : parameterTypes) {
            Log.e("AspectJ", parameterType.getSimpleName());
        }

        for (String parameterName : signature.getParameterNames()) {
            Log.e("AspectJ", parameterName);
        }

        Log.e("AspectJ", String.valueOf(stopNanoTime - startNanoTime));
    }

    @AfterReturning("execution(@butterknife.OnClick * *(..))")
    public void fastClickViewAfterReturning(JoinPoint point) {
        Log.e("AfterReturning", "------------------");
    }


    @AfterThrowing("execution(@butterknife.OnClick * *(..))")
    public void fastClickViewThrowing(JoinPoint point) {
        Log.e("fastClickViewThrowing", "------------------");

    }

    // 如果有多个匹配范围，可以定义多个，多个规则之间通过 || 或 && 控制
    @Pointcut("execution(* *.on*ItemClick(..)) ")
    public void onItemClick(JoinPoint point) {
        // 匹配任意名字以on开头以ItemClick结尾的方法
        Log.e("onItemClick", "------------------");
    }







}

