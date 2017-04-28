package net.sxkeji.shixinandroiddemo2.view.sortlistview;

import java.util.Comparator;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<SortModel> {

	public int compare(SortModel o1, SortModel o2) {
		if (o1.getFirstLetters().equals("@")
				|| o2.getFirstLetters().equals("#")) {
			return -1;
		} else if (o1.getFirstLetters().equals("#")
				|| o2.getFirstLetters().equals("@")) {
			return 1;
		} else {
			return o1.getFirstLetters().compareTo(o2.getFirstLetters());
		}
	}

}
