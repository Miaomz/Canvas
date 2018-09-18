package casual.canvas.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * @author miaomuzhi
 * @since 2018/9/17
 */
public class CircleTest {

    @Test
    public void classTest() throws Exception{
        Shape shape = new Circle(new Shape());
        assertEquals(Circle.class, shape.getClass());
    }
}