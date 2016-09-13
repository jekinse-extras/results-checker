package org.jboss.qe.collector.filter;

import org.jboss.qe.collector.Colour;
import org.jboss.qe.collector.FailedTest;
import org.jboss.qe.collector.FilterResult;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jbilek on 13/09/16.
 * complex testing of matching of filters
 */
public class FilterTestCase {
   private List<FailedTest> failedTests;
   private List<FailedTest> filteredTests;

   @Test
   public void test1() throws JSONException {
      SetFailedTests1();
      Filter filter = new TestFilter1();

      // this cases should not be matched by a filter so final color should be red
      for (FailedTest ft : failedTests) {
         Assert.assertFalse("Test case " + ft.testName + " was matched, but it should not be", filter.filter(ft).isMatch());
      }

      // this cases should be matched by a filter so final color should be yellow
      for (FailedTest ft : filteredTests) {
         Assert.assertTrue("Test case " + ft.testName + " was not matched, but it should be", filter.filter(ft).isMatch());
      }
   }

   @Test
   @Ignore ("work in progress")
   public void test2() throws JSONException {
      SetFailedTests1();
      Filter filter = new TestFilter1();

      // this cases should not be matched by a filter so final color should be red
      for (FailedTest ft : failedTests) {
         Assert.assertFalse("Test case " + ft.testName + " was matched, but it should not be", filter.filter(ft).isMatch());
      }

      // this cases should be matched by a filter so final color should be yellow
      for (FailedTest ft : filteredTests) {
         Assert.assertTrue("Test case " + ft.testName + " was not matched, but it should be", filter.filter(ft).isMatch());
      }
   }

   private void SetFailedTests1() throws JSONException {

      filteredTests = new LinkedList<>();
      String errorDetails, errorStackTrace, className, buildUrl;

      errorDetails = "nejaky blaf";
      errorStackTrace = "nejaky blaf";
      className = "org.jboss.qe.cli.embed.common.ModularServerTestCase" + "#" + "multipleReloadOfRunningModeTest";
      buildUrl = "http://jenkins.mw.lab.eng.bos.redhat.com/hudson/job/eap-70x-acceptance-multinode-rhel/jdk=java18_default,label_exp=RHEL6%20&&%20x86%20&&%20eap-sustaining/";
      filteredTests.add(new FailedTest(className, "buildUrl: " + buildUrl, testInfoFactory(errorDetails, errorStackTrace)));

      errorDetails = "nejaky blaf";
      errorStackTrace = "nejaky blaf";
      className = "org.jboss.qe.cli.embed.common.NonModularServerTestCase" + "#" + "multipleReloadOfRunningModeTest";
      buildUrl = "http://jenkins.mw.lab.eng.bos.redhat.com/hudson/job/eap-70x-acceptance-multinode-rhel/jdk=openjdk1.8_local,label_exp=EAP-RHEL7%20&&%20eap-sustaining/";
      filteredTests.add(new FailedTest(className, "buildUrl: " + buildUrl, testInfoFactory(errorDetails, errorStackTrace)));

      errorDetails = "nejaky blaf";
      errorStackTrace = "nejaky blaf";
      className = "org.jboss.qe.cli.embed.common.NonModularServerTestCase" + "#" + "testMultipleInstances";
      buildUrl = "http://jenkins.mw.lab.eng.bos.redhat.com/hudson/job/eap-70x-acceptance-multinode-rhel/jdk=openjdk1.8_local,label_exp=EAP-RHEL7%20&&%20eap-sustaining/";
      filteredTests.add(new FailedTest(className, "buildUrl: " + buildUrl, testInfoFactory(errorDetails, errorStackTrace)));


      failedTests = new LinkedList<>();

      errorDetails = "nejaky blaf";
      errorStackTrace = "nejaky blaf";
      className = "org.jboss.qe.cli.embed.common.ModularServerTestCase" + "#" + "differentTest";
      buildUrl = "http://url.of.test.com/hudson/job/strasneHroznyNazevTestu";
      failedTests.add(new FailedTest(className, "buildUrl: " + buildUrl, testInfoFactory(errorDetails, errorStackTrace)));

      errorDetails = "nejaky blaf";
      errorStackTrace = "nejaky blaf";
      className = "org.jboss.qe.cli.different.class.path.common.ModularServerTestCase" + "#" + "multipleReloadOfRunningModeTest";
      buildUrl = "http://url.of.test.com/hudson/job/strasneHroznyNazevTestu";
      failedTests.add(new FailedTest(className, "buildUrl: " + buildUrl, testInfoFactory(errorDetails, errorStackTrace)));

      errorDetails = "nejaky blaf";
      errorStackTrace = "nejaky blaf";
      className = "org.jboss.qe.cli.different.class.path.common.DifferentModule" + "#" + "multipleReloadOfRunningModeTest";
      buildUrl = "http://url.of.test.com/hudson/job/strasneHroznyNazevTestu";
      failedTests.add(new FailedTest(className, "buildUrl: " + buildUrl, testInfoFactory(errorDetails, errorStackTrace)));

      errorDetails = "nejaky blaf";
      errorStackTrace = "nejaky blaf";
      className = "org.jboss.qe.cli.different.class.path.common.JustAbsolutelyDifferentModule" + "#" + "andTestOfcourse";
      buildUrl = "http://url.of.test.com/hudson/job/strasneHroznyNazevTestu";
      failedTests.add(new FailedTest(className, "buildUrl: " + buildUrl, testInfoFactory(errorDetails, errorStackTrace)));
   }

   private class TestFilter1 extends AbstractFilter {
      @Override
      public FilterResult filter(FailedTest failedTest) {
         FilterItem[] items = new FilterItem[]{

               new FilterItem(Colour.YELLOW).setErrorText("JBEAP-5382 - Embedded server started non-modular use only first --jboss-home for FS paths")
                     .addTest("org.jboss.qe.cli.embed.common.NonModularServerTestCase#testMultipleInstances"),

               new FilterItem(Colour.YELLOW).setErrorText("JBEAP-5390 - Demote the ERROR message about unavailable HTTP/2 on IBM JDK")
                     .addTest("org.jboss.qe.cli.embed.common.ModularServerTestCase#multipleReloadOfRunningModeTest")
                     .addTest("org.jboss.qe.cli.embed.common.NonModularServerTestCase#multipleReloadOfRunningModeTest")

         };

         return coreFilter(failedTest, items);
      }
   }

   private void SetFailedTests2() throws JSONException {

      filteredTests = new LinkedList<>();
      String errorDetails, errorStackTrace, className, buildUrl;

      errorDetails = "nejaky blaf";
      errorStackTrace = "nejaky blaf";
      className = "org.jboss.qe.cli.embed.common.ModularServerTestCase" + "#" + "multipleReloadOfRunningModeTest";
      buildUrl = "http://jenkins.mw.lab.eng.bos.redhat.com/hudson/job/eap-70x-acceptance-multinode-rhel/jdk=java18_default,label_exp=RHEL6%20&&%20x86%20&&%20eap-sustaining/";
      filteredTests.add(new FailedTest(className, "buildUrl: " + buildUrl, testInfoFactory(errorDetails, errorStackTrace)));


      failedTests = new LinkedList<>();

      errorDetails = "nejaky blaf";
      errorStackTrace = "nejaky blaf";
      className = "org.jboss.qe.cli.embed.common.ModularServerTestCase" + "#" + "differentTest";
      buildUrl = "http://url.of.test.com/hudson/job/strasneHroznyNazevTestu";
      failedTests.add(new FailedTest(className, "buildUrl: " + buildUrl, testInfoFactory(errorDetails, errorStackTrace)));
   }

   private class TestFilter2 extends AbstractFilter {
      @Override
      public FilterResult filter(FailedTest failedTest) {
         FilterItem[] items = new FilterItem[]{
               new FilterItem(Colour.YELLOW)
                     .addTest("testFailedJob.*")
                     .setErrorText("Testing filter by name"),
         };

         return coreFilter(failedTest, items);
      }
   }

   private JSONObject testInfoFactory(String errorDetails, String errorStackTrace) throws JSONException {
      JSONObject testInfo = new JSONObject();
      testInfo.put("status","FAILED");
      testInfo.put("errorDetails",errorDetails);
      testInfo.put("errorStackTrace",errorStackTrace);
      return testInfo;
   }
}