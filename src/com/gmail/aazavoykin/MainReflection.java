package com.gmail.aazavoykin;

import com.gmail.aazavoykin.model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException {
        Resume resume = new Resume("Petrov");
        Class someClass = resume.getClass();
        try {
            Method hashCodeReflecting = someClass.getMethod("toString");
            try {
                System.out.println(hashCodeReflecting.invoke(resume));
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

}
