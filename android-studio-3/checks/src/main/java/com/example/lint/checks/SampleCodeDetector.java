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

import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.*;
import com.android.tools.lint.detector.api.Detector.UastScanner;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.uast.UCallExpression;
import org.jetbrains.uast.UClass;
import org.jetbrains.uast.UElement;

import java.util.Collections;
import java.util.List;

/**
 * Sample detector showing how to analyze Kotlin/Java code.
 * This example flags all string literals in the code that contain
 * the word "lint".
 */
public class SampleCodeDetector extends Detector implements UastScanner {

    private static final String CLASSNAME = "test.pkg.TestClass2";
    private static final String MESSAGE = "Found usage of TestClass";
    private static final String METHOD_NAME = "test";

    /** Issue describing the problem and pointing to the detector implementation */
    public static final Issue ISSUE = Issue.create(
            // ID: used in @SuppressLint warnings etc
            "ShortUniqueId",

            // Title -- shown in the IDE's preference dialog, as category headers in the
            // Analysis results window, etc
            "Lint Mentions",

            // Full explanation of the issue; you can use some markdown markup such as
            // `monospace`, *italic*, and **bold**.
            "This check highlights string literals in code which mentions " +
                    "the word `lint`. Blah blah blah.\n" +
                    "\n" +
                    "Another paragraph here.\n",
            Category.CORRECTNESS,
            6,
            Severity.WARNING,
            new Implementation(
                    SampleCodeDetector.class,
                    Scope.JAVA_FILE_SCOPE));

    @Override
    public List<String> getApplicableMethodNames() {
        return Collections.singletonList(METHOD_NAME);
    }

    @Override public void visitMethodCall(@NotNull JavaContext context, @NotNull UCallExpression node, @NotNull PsiMethod method) {
        PsiClass containingClass = method.getContainingClass();
        if (containingClass != null && CLASSNAME.equals(containingClass.getQualifiedName())) {
            context.report(ISSUE, node, context.getLocation(node), MESSAGE);
        }
    }
}
