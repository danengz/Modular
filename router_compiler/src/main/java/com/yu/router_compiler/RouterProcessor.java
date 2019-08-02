package com.yu.router_compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.yu.router_annotation.Router;
import com.yu.router_compiler.utils.EmptyUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;


// AutoService则是固定的写法，加个注解即可
@AutoService(Processor.class)
// 需要处理的注解
@SupportedAnnotationTypes({"com.yu.router_annotation.Router"})
// 指定JDK编译版本
@SupportedSourceVersion(SourceVersion.RELEASE_7)
// 注解处理器接收的参数
@SupportedOptions({"moduleName", "packageNameForAPT"})
public class RouterProcessor extends AbstractProcessor {

    // 操作Element工具类 (类、函数、属性都是Element)
    private Elements elementUtils;

    // type(类信息)工具类，包含用于操作TypeMirror的工具方法
    private Types typeUtils;

    // Messager用来报告错误，警告和其他提示信息
    private Messager messager;

    // 文件生成器 类/资源，Filter用来创建新的类文件，class文件以及辅助文件
    private Filer filer;

    // 子模块名，如：app/order/personal。需要拼接类名时用到（必传）Router$$Group$$order
    private String moduleName;

    // 包名，用于存放APT生成的类文件
    private String packageNameForAPT;


    // 该方法主要用于一些初始化的操作，通过该方法的参数ProcessingEnvironment可以获取一些列有用的工具类
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        elementUtils = processingEnvironment.getElementUtils();
        typeUtils = processingEnvironment.getTypeUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();

        // 通过ProcessingEnvironment去获取对应的参数
        Map<String, String> options = processingEnvironment.getOptions();
        if (!EmptyUtils.isEmpty(options)) {
            moduleName = options.get("moduleName");
            packageNameForAPT = options.get("packageNameForAPT");
            messager.printMessage(Diagnostic.Kind.NOTE, "moduleName >>> " + moduleName);
            messager.printMessage(Diagnostic.Kind.NOTE, "packageNameForAPT >>> " + packageNameForAPT);
        }

    }

    // 处理注解的函数
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // 一旦有类之上使用@Router注解
        if (!EmptyUtils.isEmpty(set)) {
            // 获取所有被 @Router 注解的 元素集合
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Router.class);

            if (!EmptyUtils.isEmpty(elements)) {
                // 解析元素
                try {
                    parseElements(elements);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 坑：必须写返回值，表示处理@Router注解完成
            return true;
        }
        return false;
    }

    private void parseElements(Set<? extends Element> elements) throws IOException {

        TypeName methodReturns = ParameterizedTypeName.get(
                ClassName.get(Map.class), // Map
                ClassName.get(String.class), // Map<String,
                ClassName.get(Class.class) // Map<String, RouterBean>
        );
        MethodSpec.Builder methodBuidler = MethodSpec.methodBuilder("loadPath") // 方法名
                .addAnnotation(Override.class) // 重写注解
                .addModifiers(Modifier.PUBLIC) // public修饰符
                .returns(methodReturns); // 方法返回值

        // 初始化Map：Map<String, RouterBean> pathMap = new HashMap<>();
        methodBuidler.addStatement("$T<$T, $T> $N = new $T<>()",
                ClassName.get(Map.class),
                ClassName.get(String.class),
                ClassName.get(Class.class),
                "pathMap",
                HashMap.class);

        // 便利Activity，存入Map
        for (Element element : elements) {

            Router router = element.getAnnotation(Router.class);

            methodBuidler.addStatement(
                    "$N.put($S, $T.class)",
                    "pathMap",
                    router.path(), // "/app/MainActivity"
                    ClassName.get((TypeElement) element) // MainActivity.class

            );

        }
        methodBuidler.addStatement("return $N", "pathMap");

        // 最终生成的类文件名
        String finalClassName = "Router$$Path$$" + moduleName;
        messager.printMessage(Diagnostic.Kind.NOTE, "APT生成路由Path类文件：" +
                packageNameForAPT + "." + finalClassName);

        // 生成类文件：Router$$Path$$app
        JavaFile.builder(packageNameForAPT, // 包名
                TypeSpec.classBuilder(finalClassName) // 类名
                        .addSuperinterface(ClassName.get(elementUtils.getTypeElement("com.yu.router_api.RouterLoadPath"))) // 实现RouterLoadPath接口
                        .addModifiers(Modifier.PUBLIC) // public修饰符
                        .addMethod(methodBuidler.build()) // 方法的构建（方法参数 + 方法体）
                        .build()) // 类构建完成
                .build() // JavaFile构建完成
                .writeTo(filer); // 文件生成器开始生成类文件

    }
}
