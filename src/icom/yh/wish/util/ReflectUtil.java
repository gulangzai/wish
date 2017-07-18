package icom.yh.wish.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
 
/**
 * @author : ����
 * @Date : 2015-05-27 ����9:14:15
 * @Comments : ���乤����
 * @Version : 1.0.0
 */
public class ReflectUtil {
/*    @SuppressWarnings("all")
    public static void main(String[] args) throws Exception {
        User user = new User();
        user.setUsername("miui");
        user.setPhone("128379230948");
        BeanUtils.setProperty(user, "password", "123456");
        DateConverter converter = new DateConverter(null);
        converter.setPattern("yyyy-MM-dd HH:mm:ss");
        ConvertUtils.register(converter, Date.class);
        ConvertUtils.register(converter, String.class);
        BeanUtils.setProperty(user, "birthday", "2014-01-02 14:23:01");
        String username = BeanUtils.getProperty(user, "username");
        System.out.println(BeanUtils.getProperty(user, "birthday"));
        User user1 = (User) BeanUtils.cloneBean(user); // ��¡bean
        System.out.println(user1.getPassword());
        Map<String, Object> map = BeanUtils.describe(user1);// beanתmap
        System.out.println(map.get("phone"));
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("phone", "3482394");
        map1.put("birthday", new Date());
        User user2 = new User();
        BeanUtils.populate(user2, map1);// mapת-bean
        System.out.println(BeanUtils.getProperty(user2, "birthday"));
        // BeanUtils.getNestedProperty(user2,"department.name");//��ȡbeanǶ��ֵ
    }*/
 /*
    @Test
    public void test1() throws Exception {
        User user = new User();
        Department dept = new Department();
        dept.setName("�г���");
        setProperty(user, "department", dept);
        setProperty(user, "username", "mini");
        setProperty(user, "birthday", "2012-01-02 12:09:12");
        setProperty(user, "inputTime", new Date());
        System.out.println(getNestedProperty(user, "inputTime"));
        setProperty(user, "id", 1);
        System.out.println(user.getUsername());
        System.out.println(user.getId());
        System.out.println(getNestedProperty(user, "birthday"));
        System.out.println(getNestedProperty(user, "department.name"));
    }*/
    /**
     * @description ���ö�������ֵ
     * @param obj
     *            ʵ�����
     * @param fieldName
     *            ������
     * @param value
     *            ����ֵ
     */
    public static void setProperty(Object obj, String fieldName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            if (field != null) {
                Class<?> fieldType = field.getType();
                field.setAccessible(true);
                // �����ֶ����͸��ֶθ�ֵ
                if (String.class == fieldType) {
                    field.set(obj, String.valueOf(value));
                } else if ((Integer.TYPE == fieldType)
                        || (Integer.class == fieldType)) {
                    field.set(obj, Integer.parseInt(value.toString()));
                } else if ((Long.TYPE == fieldType)
                        || (Long.class == fieldType)) {
                    field.set(obj, Long.valueOf(value.toString()));
                } else if ((Float.TYPE == fieldType)
                        || (Float.class == fieldType)) {
                    field.set(obj, Float.valueOf(value.toString()));
                } else if ((Short.TYPE == fieldType)
                        || (Short.class == fieldType)) {
                    field.set(obj, Short.valueOf(value.toString()));
                } else if ((Double.TYPE == fieldType)
                        || (Double.class == fieldType)) {
                    field.set(obj, Double.valueOf(value.toString()));
                } else if (Character.TYPE == fieldType) {
                    if ((value != null) && (value.toString().length() > 0)) {
                        field.set(obj,
                                Character.valueOf(value.toString().charAt(0)));
                    }
                } else if (Date.class == fieldType) {
                    if (value instanceof Date) {
                        field.set(obj, value);
                    } else if (value instanceof String) {
                        field.set(obj, new SimpleDateFormat(
                                "yyyy-MM-dd HH:mm:ss").parse(value.toString()));
                    }
                } else {
                    field.set(obj, value);
                }
                field.setAccessible(false);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
 
    /**
     * @description ��ȡ��������ֵ
     * @param obj
     *            ʵ�����
     * @param fieldName
     *            ������
     * @param value
     *            ����ֵ
     */
    public static Object getProperty(Object obj, String fieldName) {
        Field field = getFieldName(obj, fieldName);
        Object value = null;
        try {
            if (field != null) {
                Class<?> fieldType = field.getType();
                field.setAccessible(true);
                // �����ֶ����͸��ֶθ�ֵ
                if (Date.class == fieldType) {
                    Object o = field.get(obj);
                    if (o != null) {
                        value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .format(o);
                    }
                } else {
                    value = field.get(obj);
                }
                field.setAccessible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
    // ��ȡdepartment.name ����ֵ
    public static Object getNestedProperty(Object obj, String fieldName) {
        Object value = null;
        String[] attributes = fieldName.split("\\.");
        try {
            value = getProperty(obj, attributes[0]);
            for (int i = 1; i < attributes.length; i++) {
                value = getProperty(value, attributes[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
 
    // ��ȡ����
    public static Field getFieldName(Object obj, String fieldName) {
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
            }
        }
        return null;
    }
    /**
     * ��ȡ���������ֶε�����
     * @param obj Ŀ�����
     * @return �ֶ����ֵ�����
     */
    public static String[] getFieldNames(Object obj) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<String> fieldNames = new ArrayList<String>();
        for (int i = 0; i < fields.length; i++) {
            if ((fields[i].getModifiers() & Modifier.STATIC) == 0) {
                fieldNames.add(fields[i].getName());
            }
        }
        return fieldNames.toArray(new String[fieldNames.size()]);
    }
    public void say(String name){
         
    }
}