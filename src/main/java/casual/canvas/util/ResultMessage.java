package casual.canvas.util;

/**
 * @author miaomuzhi
 * @since 2018/9/15
 */
public enum ResultMessage {
    /**
     * success
     */
    SUCCESS,
    /**
     * failure due to runtime exceptions and io exceptions.
     */
    FAILURE,
    /**
     * file names with wrong extension
     */
    WRONG_EXT,
    /**
     * absence of arguments
     */
    ARG_ABSENT
}
