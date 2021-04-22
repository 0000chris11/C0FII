package com.cofii2.methods;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * Class use for invoking a method from a class through the console (NOT FINISHED)S
 * 
 * @author C0FII
 */
public class MReflectScanner {

    private Method[] methods;
    //private String
    private String methodName;
    private StringBuilder messageSelect = new StringBuilder("Select: \n");
    private StringBuilder parametersSelect = new StringBuilder(methodName);

    public void useMethod(Class<?> classs, String methodName) {
        if (!methodName.equalsIgnoreCase("main")) {
            this.methodName = methodName;
        } else {
            throw new IllegalArgumentException("Not an accepted method");
        }

    }

    private void getParameters() {
        for (int a = 0; a < methods.length; a++) {
            if (methodName.equalsIgnoreCase(methods[a].getName())) {
                Class<?>[] parameterTypes = methods[a].getParameterTypes();

                for (int b = 0; b < parameterTypes.length; b++) {
                    String typeName = parameterTypes[a].getTypeName();
                    String parameterName = parameterTypes[b].getName();
                    parametersSelect.append(" -- " + typeName + ": " + parameterName);
                }
                System.out.println(parametersSelect.toString());
                Scanner sc = new Scanner(System.in);
                
                for (int b = 0; b < parameterTypes.length; b++) {
                    String typeName = parameterTypes[a].getTypeName();
                    String parameterName = parameterTypes[b].getName();
                    System.out.print("\t" + parameterName + " (" + typeName + "): ");
                    String line = sc.next();
                    //FINSISH LATER
                }
                sc.close();

            }
        }
    }

    // ---------------------------------------------------------
    public MReflectScanner(Class<?> classs) {
        methods = classs.getDeclaredMethods();
        for (int a = 0; a < methods.length; a++) {
            String name = methods[a].getName();
            if (!name.equalsIgnoreCase("main")) {
                messageSelect.append("\t" + name + "\n");
            }
        }
        // ---------------------------------------------
        Scanner sc = new Scanner(System.in);

        String line = "";
        while (!line.equalsIgnoreCase("exit")) {
            System.out.println(messageSelect.toString());
            line = sc.nextLine();
            if (!line.equalsIgnoreCase("main")) {
                getParameters();
            } else {
                System.out.println("NOT MAIN");
            }
        }

    }
}
