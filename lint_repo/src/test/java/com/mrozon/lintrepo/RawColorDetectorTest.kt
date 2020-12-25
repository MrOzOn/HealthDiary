package com.mrozon.lintrepo

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.xml
import com.android.tools.lint.checks.infrastructure.TestLintTask
import org.junit.Test

class RawColorDetectorTest {

    @Test
    fun `incorrect xml resource`() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                xml(
                    "/res/layout/custom_layout.xml",
                    """
                        <layout xmlns:android="http://schemas.android.com/apk/res/android"
                            xmlns:app="http://schemas.android.com/apk/res-auto">
                                    <EditText
                                        android:id="@+id/etUserName"
                                        android:layout_width="0dp"
                                        android:layout_height="wrap_content"
                                        android:layout_marginStart="32dp"
                                        android:layout_marginEnd="32dp"
                                        android:ems="10"
                                        android:textColor="#444444"
                                        android:gravity="center"
                                        android:hint="@string/etUsername"
                                        android:inputType="textEmailAddress"
                                        app:layout_constraintEnd_toEndOf="parent"
                                        app:layout_constraintStart_toStartOf="parent"
                                        app:layout_constraintTop_toTopOf="@+id/guideline" />
                        </layout>
                    """
                )
            )
            .issues(RawColorDetector.ISSUE_RAW_COLOR_RESOURCES)
            .run()
            .expect("""
                res/layout/custom_layout.xml:11: Warning: Using row color in tags [RawColorInResources]
                                                        android:textColor="#444444"
                                                        ~~~~~~~~~~~~~~~~~~~~~~~~~~~
                0 errors, 1 warnings
            """.trimIndent())
    }

    @Test
    fun `correct xml resource`() {
        TestLintTask.lint()
            .allowMissingSdk()
            .files(
                xml(
                    "/res/layout/custom_layout.xml",
                    """
                        <layout xmlns:android="http://schemas.android.com/apk/res/android"
                            xmlns:app="http://schemas.android.com/apk/res-auto">
                                    <EditText
                                        android:id="@+id/etUserName"
                                        android:layout_width="0dp"
                                        android:layout_height="wrap_content"
                                        android:layout_marginStart="32dp"
                                        android:layout_marginEnd="32dp"
                                        android:ems="10"
                                        android:textColor="@color/color_snack_error"
                                        android:gravity="center"
                                        android:hint="@string/etUsername"
                                        android:inputType="textEmailAddress"
                                        app:layout_constraintEnd_toEndOf="parent"
                                        app:layout_constraintStart_toStartOf="parent"
                                        app:layout_constraintTop_toTopOf="@+id/guideline" />
                        </layout>
                    """
                )
            )
            .issues(RawColorDetector.ISSUE_RAW_COLOR_RESOURCES)
            .run()
            .expectClean()
    }


}