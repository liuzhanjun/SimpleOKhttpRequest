package liu.zhan.jun.simplerequest.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

class FieldUtil {
    public static List<Field> getAllFields(Class aClass) {
        ArrayList<Field> list=new ArrayList<>();
        for (Field field : aClass.getDeclaredFields()) {
            list.add(field);
        }
        return list;
    }
}