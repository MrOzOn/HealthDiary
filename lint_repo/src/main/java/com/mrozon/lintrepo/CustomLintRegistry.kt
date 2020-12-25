package com.mrozon.lintrepo

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.Issue

class CustomLintRegistry : IssueRegistry() {

    override val issues: List<Issue>
        get() = listOf(
            VerySimpleNamingVariablesDetector.ISSUE_VERY_SIMPLE_NAMING_VARIABLES,
            RawColorDetector.ISSUE_RAW_COLOR_RESOURCES
        )
}