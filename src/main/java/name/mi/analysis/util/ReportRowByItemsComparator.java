package name.mi.analysis.util;
import name.mi.analysis.derivative.ReportRow;

import java.util.Comparator;

public class ReportRowByItemsComparator implements Comparator<ReportRow>
{
  @Override
  public int compare(ReportRow x, ReportRow y) {
    // TODO: Handle null x or y values
      if (x == null)
          return 1;
      if (y == null)
          return -1;
      double xx = x.getCompareData();
      double yy = y.getCompareData();
      return compare(xx,yy);
    //int startComparison = compare();
    //return startComparison != 0 ? startComparison
      //                          : compare(x.timeEnded, y.timeEnded);
  }

  // I don't know why this isn't in Long...
  private static int compare(double a, double b) {
    return a < b ? -1
         : a > b ? 1
         : 0;
  }
}

