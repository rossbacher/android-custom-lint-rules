/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.lint.checks;

import com.android.tools.lint.checks.infrastructure.LintDetectorTest;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;

import java.util.Collections;
import java.util.List;

public class SampleCodeDetectorTest extends LintDetectorTest {
    public void testBroken() {
        lint().files(
                java( "src/test/pkg/TestClass1.java", "package test.pkg;\n" +
                        "\n" +
                        "public class TestClass1 {\n" +
                        "\n" +
                        "    public void foo() {\n" +
                        "        TestClass2.test();\n" +
                        "    }\n" +
                        "\n" +
                        "}"),
                java("src/pkg/TestClass2.java","package test.pkg;\n" +
                        "\n" +
                        "public class TestClass2 {\n" +
                        "\n" +
                        "    public static void test(){\n" +
                        "\n" +
                        "    }\n" +
                        "\n" +
                        "}\n"))
                .run()
                .expect("src/test/pkg/TestClass1.java:6: Warning: Found usage of TestClass [ShortUniqueId]\n" +
                        "        TestClass2.test();\n" +
                        "        ~~~~~~~~~~~~~~~~~\n" +
                        "0 errors, 1 warnings");
    }

    public void testWorking() {
        lint().files(
                java( "src/test/pkg/TestClass1.java", "package test.pkg;\n" +
                        "\n" +
                        "public class TestClass1 {\n" +
                        "\n" +
                        "    public void foo() {\n" +
                        "        TestClass2.test();\n" +
                        "    }\n" +
                        "\n" +
                        "}"),
                java("src/test/pkg/TestClass2.java","package test.pkg;\n" +
                        "\n" +
                        "public class TestClass2 {\n" +
                        "\n" +
                        "    public static void test(){\n" +
                        "\n" +
                        "    }\n" +
                        "\n" +
                        "}\n"))
                .run()
                .expect("src/test/pkg/TestClass1.java:6: Warning: Found usage of TestClass [ShortUniqueId]\n" +
                        "        TestClass2.test();\n" +
                        "        ~~~~~~~~~~~~~~~~~\n" +
                        "0 errors, 1 warnings");
    }

    @Override
    protected Detector getDetector() {
        return new SampleCodeDetector();
    }

    @Override
    protected List<Issue> getIssues() {
        return Collections.singletonList(SampleCodeDetector.ISSUE);
    }
}