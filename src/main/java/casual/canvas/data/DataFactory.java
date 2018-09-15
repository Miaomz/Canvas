package casual.canvas.data;

import lombok.Getter;

/**
 * @author miaomuzhi
 * @since 2018/9/15
 */
@Getter
public class DataFactory {
    private static DataFactory dataFactory;
    private DataService dataService = new DataServiceImpl();

    private DataFactory(){}

    public static DataFactory getDataFactory() {
        if (dataFactory == null){
            dataFactory = new DataFactory();
        }
        return dataFactory;
    }
}
