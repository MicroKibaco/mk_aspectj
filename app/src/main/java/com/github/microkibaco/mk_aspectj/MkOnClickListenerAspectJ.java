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

/**
 * @author 杨正友(小木箱)于 2020/10/7 15 18 创建
 * @Email: yzy569015640@gmail.com
 * @Tel: 18390833563
 * @function description:
 */

@Aspect
public class MkOnClickListenerAspectJ {

    @Pointcut("execution(void android.view.View.OnClickListener.onClick(..))")
    public void fastClickViewPointcut(JoinPoint point) {

        Log.e("fastClickViewPointcut","------------------");

    }


    @Before("execution(void android.content.DialogInterface.OnClickListener.onClick(..))")
    public void fastClickViewBefore(JoinPoint point) {

        Log.e("fastClickViewBefore","------------------");
    }


    @After("execution(void android.support.v7.app.AppCompatViewInflater.DeclaredOnClickListener.onClick(..))")
    public void fastClickViewAfter(JoinPoint point) {

        Log.e("fastClickViewAfter","------------------");
    }


    @Around("execution(* android.view.View.OnClickListener.onClick(..))")
    public void fastClickViewAround(ProceedingJoinPoint point) {
        try {
            Log.e("fastClickViewAround","Something Before");
            Object proceed = point.proceed();
            Log.e("fastClickViewAround","Something After");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        Log.e("fastClickViewAround","------------------");
    }

    @AfterReturning("execution(@butterknife.OnClick * *(..))")
    public void fastClickViewAfterReturning(JoinPoint point) {
        Log.e("AfterReturning","------------------");
    }


    @AfterThrowing("execution(@butterknife.OnClick * *(..))")
    public void fastClickViewThrowing(JoinPoint point) {
        Log.e("fastClickViewThrowing","------------------");

    }

    // 如果有多个匹配范围，可以定义多个，多个规则之间通过 || 或 && 控制
    @Pointcut("execution(* *.on*ItemClick(..)) ")
    public void onItemClick(JoinPoint point) {
        // 匹配任意名字以on开头以ItemClick结尾的方法
        Log.e("onItemClick","------------------");
    }
    // 匹配View.OnClickListener中的onClick方法和View.OnLongClickListener中的OnLongClickListener方法
    // 定义匹配范围：执行指定方法时拦截
    @Pointcut("execution(* android.view.View.On*Listener.on*Click(..)) ")
    public void onClick(JoinPoint point) {
        Log.e("onClick","------------------");
    }


    @Before("execution(* * (..))")
    public void fastClickViewBefore1() {

     }
    @After("execution(* * (..))")
    public void fastClickViewBefore2() {
        Log.e("MainActivity","  @After");
     }

    @AfterReturning("execution(* * (..))")
    public void fastClickViewAfterReturning1(JoinPoint point) {
        Log.e("MainActivity"," @AfterReturning");
     }


    @AfterThrowing("execution(* * (..))")
    public void fastClickViewThrowing1(JoinPoint point) {
        Log.e("MainActivity"," @AfterThrowing");

    }



}

