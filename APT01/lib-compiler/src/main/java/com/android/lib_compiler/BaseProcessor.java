package com.android.lib_compiler;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.util.Elements;

public abstract class BaseProcessor extends AbstractProcessor {
    // 代码生成相关的工具类
    protected Filer mFiler;
    // 打印相关的工具类
    protected Messager mMessager;
    // Elements操作相关的工具类
    protected Elements mElementUtils;

    // 初始化方法，这里可以获取一些工具类
    @Override
    public final synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mMessager = processingEnvironment.getMessager();
        mElementUtils = processingEnvironment.getElementUtils();
    }

    // 指定支持的源码版本
    @Override
    public final SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    // 指定支持的注解类型
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    protected abstract Set<Class<? extends Annotation>> getSupportedAnnotations();

}
