package com.ray.ByteExplorer;
import javassist.*;
import javassist.expr.*;

import java.io.File;
import java.util.Arrays;

public class Modifier {

    public static void main(String[] args) throws Exception{
        ClassPool pool = ClassPool.getDefault();

        pool.insertClassPath("/Users/ray/Development/byte-explorer/target/classes");

        CtClass evenOddClass = pool.get("com.ray.ByteExplorer.EvenOrOdd");
        CtMethod[] methods = evenOddClass.getMethods();

        for(CtMethod method : methods){
            if(method.getName().equals("isEvenOrOdd")){
                System.out.println(method.getMethodInfo().getLineNumber(0));
                method.instrument(new ExprEditor(){

                    @Override
                    public void edit(NewExpr e) throws CannotCompileException {
                        String marker = String.valueOf(e.getLineNumber());
                        String log = sendLineNumber(marker);
                        e.replace("{ $_ = $proceed($$); "+log+"}");
                    }

                    @Override
                    public void edit(NewArray a) throws CannotCompileException {
                        String marker = String.valueOf(a.getLineNumber());
                        String log = sendLineNumber(marker);
                        a.replace("{ $_ = $proceed($$); "+log+"}");
                    }

                    @Override
                    public void edit(MethodCall m) throws CannotCompileException {
                        String marker = String.valueOf(m.getLineNumber());
                        String log = sendLineNumber(marker);
                        m.replace("{ $_ = $proceed($$); "+log+"}");
                    }

                    @Override
                    public void edit(ConstructorCall c) throws CannotCompileException {
                        String marker = "Line number "+c.getLineNumber()+" was executed";
                        String log = sendLineNumber(marker);
                        c.replace("{ $_ = $proceed($$); "+log+"}");
                    }

                    @Override
                    public void edit(FieldAccess f) throws CannotCompileException {
                        String marker = String.valueOf(f.getLineNumber());
                        String log = sendLineNumber(marker);
                        f.replace("{ $_ = $proceed($$); "+log+"}");
                    }

                    @Override
                    public void edit(Instanceof i) throws CannotCompileException {
                        String marker = String.valueOf(i.getLineNumber());
                        String log = sendLineNumber(marker);
                        i.replace("{ $_ = $proceed($$); "+log+"}");
                    }

                    @Override
                    public void edit(Cast c) throws CannotCompileException {
                        String marker = String.valueOf(c.getLineNumber());
                        String log = sendLineNumber(marker);
                        c.replace("{ $_ = $proceed($$); "+log+"}");
                    }

                    @Override
                    public void edit(Handler h) throws CannotCompileException {
                        String marker = String.valueOf(h.getLineNumber());
                        String log = sendLineNumber(marker);
                        h.replace("{ $_ = $proceed($$); "+log+"}");
                    }
                });
            }
        }

        evenOddClass.toClass();
        new EvenOrOdd().isEvenOrOdd();
    }

    private static String sendLineNumber(String lineNumber){
        return String.format("writer.println(\"%s\");",lineNumber);
    }

}
