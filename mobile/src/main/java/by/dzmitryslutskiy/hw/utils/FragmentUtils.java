package by.dzmitryslutskiy.hw.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * FragmentManagerUtils
 * Version information
 * 10.11.2014
 * Created by Dzmitry Slutskiy.
 */
public class FragmentUtils {


    private FragmentUtils() {/*   code    */}

    /**
     * Find fragment in in manager with specified container id and tag
     *
     * @param manager     manager
     * @param containerId matching container od
     * @param tag         matching fragment tag
     * @return Fragment if fragment with specified id AND tag exists, null otherwise
     */
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

    public static void addFragment(FragmentManager manager, int containerId, Fragment fragment, String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(containerId, fragment, tag);
        transaction.commit();
    }
}
