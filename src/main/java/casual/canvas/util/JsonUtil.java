package casual.canvas.util;


import casual.canvas.entity.Circle;
import casual.canvas.entity.Rectangle;
import casual.canvas.entity.Shape;
import casual.canvas.entity.Triangle;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.ArrayList;
import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/9/15
 */
public class JsonUtil {

    private JsonUtil() {}

    /**
     * @param object Java对象
     * @return Json格式字符串
     */
    public static String toJson(Object object) {
        return JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * @param jsonString Json格式字符串
     * @param tClass     Java对象的class，请传递引用的真正类型
     * @param <T> 范型
     * @return Java对象
     */
    public static <T> T toObject(String jsonString, Class<T> tClass) {
        return JSON.parseObject(jsonString, tClass);
    }

    /**
     *
     * @param jsonString json格式字符串
     * @return shape（实际类型包括其子类）的List
     */
    public static List<Shape> toShapesArray(String jsonString){
        JSONArray jsonArray = JSON.parseArray(jsonString);
        if (jsonArray == null || jsonArray.isEmpty()){
            return new ArrayList<>();
        }

        List<Shape> shapes = new ArrayList<>(jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            shapes.add(toShape(jsonObject.toJSONString()));
        }
        return shapes;
    }

    /**
     * 将Shape转换为其子类
     * @param shapeJson json格式字符串
     * @return shape（实际类型包括其子类）对象
     */
    public static Shape toShape(String shapeJson){
        if (shapeJson.contains("BLACK")){
            return toObject(shapeJson, Shape.class);
        } else if (shapeJson.contains("RED")){
            return toObject(shapeJson, Rectangle.class);
        } else if (shapeJson.contains("GREEN")){
            return toObject(shapeJson, Triangle.class);
        } else if (shapeJson.contains("BLUE")){
            return toObject(shapeJson, Circle.class);
        }

        LoggerUtil.getLogger().info(new Exception("anomaly shape"));
        return toObject(shapeJson, Shape.class);
    }
}
