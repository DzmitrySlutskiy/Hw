package by.dzmitryslutskiy.hw.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * FragmentManagerUtils
 * Version information
 * 10.11.2014
 * Created by Dzmitry Slutskiy.
 */
public class FragmentUtils {


    private FragmentUtils() {/*   code    */}


    public static Fragment findFragmentByIdAndTag(FragmentManager manager, int containerId, String tag) {
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if ((fragment != null) &&
                        (fragment.getId() == containerId) && (tag.equals(fragment.getTag()))) {
                    return fragment;
                }
            }
        }
        return null;
    }
}
