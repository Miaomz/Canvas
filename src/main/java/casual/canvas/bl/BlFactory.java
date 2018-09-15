package casual.canvas.bl;

import lombok.Getter;

/**
 * @author miaomuzhi
 * @since 2018/9/15
 */
@Getter
public class BlFactory {
    private static BlFactory ourInstance = new BlFactory();
    private BlService blService = new BlController();

    private BlFactory() {}

    public static BlFactory getInstance() {
        return ourInstance;
    }
}
