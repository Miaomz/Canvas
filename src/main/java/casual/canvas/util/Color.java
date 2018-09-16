package casual.canvas.util;

/**
 * @author miaomuzhi
 * @since 2018/9/16
 */
public enum Color {
    /**
     * default color for unrecognized shape
     */
    BLACK,
    /**
     * Circle
     */
    BLUE,
    /**
     * rectangle
     */
    RED,
    /**
     * triangle
     */
    GREEN;

    public javafx.scene.paint.Color transform(){
        return javafx.scene.paint.Color.valueOf(this.toString());
    }
}
