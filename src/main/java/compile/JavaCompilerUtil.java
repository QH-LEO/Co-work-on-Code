package compile;

/**
 * @Author: Hao Qin
 * @Date: 19-12-21  下午9:05
 * @Version 1.0
 */
import java.io.*;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import static org.junit.Assert.assertEquals;

public class JavaCompilerUtil {
    private static JavaCompiler javaCompiler;

    private JavaCompilerUtil() {
    }

    ;

    private static JavaCompiler getJavaCompiler() {
        if (javaCompiler == null) {
            synchronized (JavaCompilerUtil.class) {
                if (javaCompiler == null) {
                    // 讲道理，根据JavaCompiler 的获取方式来看，应该是采用了单例模式的，但是这里为了顺便复习一下单例模式，以及确保一下单例吧
                    javaCompiler = ToolProvider.getSystemJavaCompiler();
                }
            }
        }

        return javaCompiler;
    }

    public static boolean CompilerJavaFile(String sourceFileInputPath,
                                           String classFileOutputPath) {
        // 设置编译选项，配置class文件输出路径
        Iterable<String> options = Arrays.asList("-d", classFileOutputPath);
        StandardJavaFileManager fileManager = getJavaCompiler()
                .getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> compilationUnits = fileManager
                .getJavaFileObjectsFromFiles(Arrays.asList(new File(
                        sourceFileInputPath)));

        return getJavaCompiler().getTask(null, fileManager, null, options,
                null, compilationUnits).call();
    }


    public static void main(String[] args) throws IOException {
//        File f=new File("/home/qhwd/Desktop/out.txt");
//        f.createNewFile();
//        FileOutputStream fileOutputStream = new FileOutputStream(f);
//        PrintStream printStream = new PrintStream(fileOutputStream);
//
//        //JavaCompilerUtil.CompilerJavaFile("/home/qhwd/Desktop/test.java","/home/qhwd/Desktop/");
//      int a=JavaCompilerUtil.getJavaCompiler().run(null,null,printStream,"/home/qhwd/Desktop/test.java");
//     assertEquals(0, a);
        String code = "class reverse {\n" +
                "    public int reverse(int x) {\n" +
                "        int rev = 0;\n" +
                "        while (x != 0) {\n" +
                "            int pop = x % 10;\n" +
                "            x /= 10;\n" +
                "            if (rev > Integer.MAX_VALUE/10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) return 0;\n" +
                "            if (rev < Integer.MIN_VALUE/10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) return 0;\n" +
                "            rev = rev * 10 + pop;\n" +
                "        }\n" +
                "        return rev;\n" +
                "\n" +
                "    }\n" +
                "}";
        CustomStringJavaCompiler compiler = new CustomStringJavaCompiler(code);
        boolean res = compiler.compiler();
        if (res) {
            System.out.println("编译成功");
            System.out.println("compilerTakeTime：" + compiler.getCompilerTakeTime());
            try {
                compiler.runMainMethod();
                System.out.println("runTakeTime：" + compiler.getRunTakeTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(compiler.getRunResult());
           // System.out.println(compiler.getCompilerMessage());
        } else {
            System.out.println("编译失败");
            System.out.println(compiler.getCompilerMessage());
        }


    }
}