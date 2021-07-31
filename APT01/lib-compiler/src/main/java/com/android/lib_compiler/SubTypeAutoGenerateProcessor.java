package com.android.lib_compiler;

import com.android.lib_annotations.SubTypeAutoGenerate;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

// 用这个@AutoService注解可以省去一些注解处理器的配置
@AutoService(Processor.class)
public class SubTypeAutoGenerateProcessor extends BaseProcessor {

    @Override
    protected Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(SubTypeAutoGenerate.class);
        return annotations;
    }

    // 注解处理器的核心方法
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // 获取标记有注解的Element
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(SubTypeAutoGenerate.class);
        for (Element element : elements) {
            if (!(element instanceof TypeElement)) {
                continue;
            }
            // 强转为TypeElement
            TypeElement typeElement = (TypeElement) element;

            // 获取需要生成的全类名、包名、简单类名
            SubTypeAutoGenerate subTypeAutoGenerate = typeElement.getAnnotation(SubTypeAutoGenerate.class);
            String subTypeQualifiedName = subTypeAutoGenerate.value();
            String subTypePackage = Utils.getPackage(subTypeQualifiedName);
            String subTypeSimpleName = Utils.getSimpleName(subTypeQualifiedName);

            // 利用JavaPoet的API去生成代码。也可以直接用StringBuilder来拼接代码
            TypeSpec.Builder classBuilder = TypeSpec.classBuilder(subTypeSimpleName)
                    .addModifiers(Modifier.PUBLIC)
                    .superclass(ClassName.get(typeElement));
            try {
                JavaFile.builder(subTypePackage, classBuilder.build())
                        .addFileComment("whatComment")
                        .build()
                        .writeTo(mFiler);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return false;
    }
}

/**
 * // whatComment
 * package com.android.apt.wxapi;
 *
 * import com.android.apt.MainActivity;
 *
 * public class WXEntryActivity extends MainActivity {
 * }
 */
