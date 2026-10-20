package Listeners;

import Utilities.LogsUtils;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ITestResultListenerClass implements ITestListener {
    public void onTestStart(ITestResult result) {
        // بيتنفذ أول ما التست كيس تبدأ
        LogsUtils.info("Test Case " + result.getName() + " started");
    }

    public void onTestSuccess(ITestResult result) {
        // بيتنفذ لو التست كيس نجحت (Passed)
        LogsUtils.info("Test Case " + result.getName() + " passed");
    }

    public void onTestSkipped(ITestResult result) {
        // بيتنفذ لو التست كيس حصلها Skip
        LogsUtils.info("Test Case " + result.getName() + " skipped");
    }

}
