package com.android.lib_compiler;

public class Utils {

    public static String getPackage(String qualifiedName) {
        return qualifiedName.substring(0, qualifiedName.lastIndexOf("."));
    }

    public static String getSimpleName(String qualifiedName) {
        return qualifiedName.substring(qualifiedName.lastIndexOf(".") + 1);
    }

  /*  作者：小楠总
    链接：https://juejin.cn/post/6925283352698159117
    来源：掘金
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。*/
}
