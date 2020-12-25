package com.mrozon.lintrepo

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.mrozon.lintrepo.VerySimpleNamingVariablesDetector.Companion.ISSUE_VERY_SIMPLE_NAMING_VARIABLES
import org.junit.Test

class VerySimpleNamingVariablesDetectorTest {

    @Test
    fun `incorrect naming in kt file`() {
        lint()
            .allowMissingSdk()
            .files(
                kotlin(
                    """
                        class TestClass() {
                            
                            var d2 = 1
                            var a = 1

                            fun doAction(savedInstanceState: Bundle?) {
                                d2 += 1
                            }
                        }
                    """
                )
            )
            .issues(ISSUE_VERY_SIMPLE_NAMING_VARIABLES)
            .run()
            .expect("""
                src/TestClass.kt:5: Warning: Incorrect named variable [VerySimpleNamingVariables]
                                            var a = 1
                                            ~~~~~~~~~
                0 errors, 1 warnings
            """.trimIndent())
    }

    @Test
    fun `incorrect naming in inner fun`() {
        lint()
            .allowMissingSdk()
            .files(
                kotlin(
                    """
                        class TestClass() {
                            
                            var d2 = 1

                            fun doAction(savedInstanceState: Bundle?) {
                                val t = "dsjfkh"
                                d2 += 1
                            }
                        }
                    """
                )
            )
            .issues(ISSUE_VERY_SIMPLE_NAMING_VARIABLES)
            .run()
            .expect("""
                src/TestClass.kt:7: Warning: Incorrect named variable [VerySimpleNamingVariables]
                                                val t = "dsjfkh"
                                                ~~~~~~~~~~~~~~~~
                0 errors, 1 warnings
            """.trimIndent())
    }

    @Test
    fun `correct naming in kt file`() {
        lint()
            .allowMissingSdk()
            .files(
                kotlin(
                    """
                        class TestClass() {
                            
                            var d2 = 1
                            var arty = 1

                            fun doAction(savedInstanceState: Bundle?) {
                                d2 += 1
                            }
                        }
                    """
                )
            )
            .issues(ISSUE_VERY_SIMPLE_NAMING_VARIABLES)
            .run()
            .expectClean()
    }
}