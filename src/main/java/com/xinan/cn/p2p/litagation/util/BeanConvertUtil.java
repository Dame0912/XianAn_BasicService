package com.xinan.cn.p2p.litagation.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * bean转换
 */
public class BeanConvertUtil {

    private static Logger logger = LoggerFactory.getLogger(BeanConvertUtil.class);

    /**
     * bean转map
     */
    public static Map<String, Object> beanToMap(Object bean) {
        if (null == bean) {
            throw new RuntimeException("BeanConvertUtil.beanToMap，入参为null");
        }
        Class type = bean.getClass();
        Map<String, Object> returnMap = new HashMap<>();
        // 类和超类属性写入集合fieldNames
        Set<String> fieldNames = new HashSet<>();
        Field[] fields = type.getDeclaredFields();
        Field[] superFields = type.getSuperclass().getDeclaredFields();
        for (Field field : superFields) {
            fieldNames.add(field.getName());
        }
        for (Field field : fields) {
            fieldNames.add(field.getName());
        }

        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(type);
        } catch (IntrospectionException e) {
            logger.error("BeanConvertUtil.beanToMap，转换异常1{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            // boolean isSuccess转成success的问题
            String isParam = merge(descriptor.getPropertyType(), propertyName);
            boolean orgin = fieldNames.contains(propertyName);
            boolean exp = fieldNames.contains(isParam);
            if (orgin || exp) {
                propertyName = orgin ? propertyName : isParam;
                Method readMethod = descriptor.getReadMethod();
                Object result = null;
                try {
                    result = readMethod.invoke(bean, new Object[0]);
                } catch (IllegalArgumentException e) {
                    logger.error("BeanConvertUtil.beanToMap，转换{}异常2{}", propertyName, e.getMessage());
                    throw new RuntimeException(e.getMessage());
                } catch (IllegalAccessException e) {
                    logger.error("BeanConvertUtil.beanToMap，转换{}异常3{}", propertyName, e.getMessage());
                    throw new RuntimeException(e.getMessage());
                } catch (InvocationTargetException e) {
                    logger.error("BeanConvertUtil.beanToMap，转换{}异常4{}", propertyName, e.getMessage());
                    throw new RuntimeException(e.getMessage());
                }
                returnMap.put(propertyName, result);
                logger.debug("propertyName={}, value={}, convert success", propertyName, result);
            }
        }
        return returnMap;
    }

    /**
     * map转bean
     */
    public static Object mapToBean(Class type, Map map) {
        if (null == type || null == map) {
            throw new RuntimeException("BeanConvertUtil.mapToBean，入参为null");
        }
        // 获取类属性
        BeanInfo beanInfo = null;
        // 创建 JavaBean 对象
        Object obj = null;
        try {
            beanInfo = Introspector.getBeanInfo(type);
            obj = type.newInstance();
        } catch (IntrospectionException e) {
            logger.error("BeanConvertUtil.mapToBean，转换异常{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        } catch (InstantiationException e) {
            logger.error("BeanConvertUtil.mapToBean，转换异常1{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        } catch (IllegalAccessException e) {
            logger.error("BeanConvertUtil.mapToBean，转换异常2{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            // 属性名
            String propertyName = descriptor.getName();
            // map中包含该属性且属性值不为null
            if (map.containsKey(propertyName) && null != map.get(propertyName)) {
                Object value = map.get(propertyName);
                if (descriptor.getPropertyType().equals(Long.class)) {
                    value = new Long(value.toString());
                } else if (descriptor.getPropertyType().equals(long.class)) {
                    value = Long.parseLong(value.toString());
                }
                Object[] args = new Object[1];
                args[0] = value;
                try {
                    Method writeMethod = descriptor.getWriteMethod();
                    if (null == writeMethod) {
                        logger.warn("BeanConvertUtil.mapToBean，转换{} 找不到Set方法", propertyName);
                        continue;
                    }
                    writeMethod.invoke(obj, args);
                    logger.debug("propertyName={}, value={}, convert success", propertyName, value);
                } catch (IllegalArgumentException e) {
                    logger.error("BeanConvertUtil.mapToBean，转换{}异常3{}", propertyName, e.getMessage());
                    throw new RuntimeException(e.getMessage());
                } catch (IllegalAccessException e) {
                    logger.error("BeanConvertUtil.mapToBean，转换{}异常4{}", propertyName, e.getMessage());
                    throw new RuntimeException(e.getMessage());
                } catch (InvocationTargetException e) {
                    logger.error("BeanConvertUtil.mapToBean，转换{}异常5{}", propertyName, e.getMessage());
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
        return obj;
    }


    private static String merge(Class<?> propertyType, String propertyName) {
        String result = null;
        if (propertyType.equals(boolean.class) && Character.isLowerCase(propertyName.charAt(0))) {
            result = (new StringBuilder()).append("is").append(Character.toUpperCase(propertyName.charAt(0)))
                    .append(propertyName.substring(1)).toString();
        }
        return result;
    }

}
