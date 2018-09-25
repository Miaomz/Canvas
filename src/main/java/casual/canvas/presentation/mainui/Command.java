package casual.canvas.presentation.mainui;

import casual.canvas.entity.Shape;
import casual.canvas.util.LoggerUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * To implement undo and redo
 * @author miaomuzhi
 * @since 2018/9/21
 */
@Getter
class Command {

    private final List<Memo> memos = new ArrayList<>();
    private final DisplayedData displayedData = DisplayedData.getInstance();

    void undo(){
        if (displayedData.getDisplayedShapes().isEmpty()){//precondition
            LoggerUtil.getLogger().warning(new Exception("menu interaction error"));
            return;
        }

        memos.add(new Memo(displayedData.getDisplayedShapes()));
        displayedData.getDisplayedShapes().remove(displayedData.getDisplayedShapes().size()-1);//undo is delete
    }

    void redo(){
        if (memos.isEmpty()){
            LoggerUtil.getLogger().warning(new Exception("menu interaction error"));
            return;
        }

        displayedData.getDisplayedShapes().clear();
        displayedData.getDisplayedShapes().addAll(memos.get(memos.size()-1).getShapes());
        memos.remove(memos.size()-1);
    }

    @Setter
    @Getter
    private class Memo {
        List<Shape> shapes;

        Memo(List<Shape> shapes){//deep copy
            this.shapes = new ArrayList<>(shapes.size());
            this.shapes.addAll(shapes);
        }
    }
}
