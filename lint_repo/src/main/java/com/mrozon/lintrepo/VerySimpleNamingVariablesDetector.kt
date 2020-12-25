package com.mrozon.lintrepo

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.*
import org.jetbrains.uast.kotlin.KotlinUAnnotatedLocalVariable
import org.jetbrains.uast.visitor.AbstractUastVisitor
import java.util.*

class VerySimpleNamingVariablesDetector : Detector(), Detector.UastScanner {

    companion object {
        val ISSUE_VERY_SIMPLE_NAMING_VARIABLES = Issue.create(
            "VerySimpleNamingVariables",
            "One letter - too bad((",
            "You named your variable use only ONE letter",
            Category.USABILITY,
            6,
            Severity.WARNING,
            Implementation(
                VerySimpleNamingVariablesDetector::class.java,
                EnumSet.of(Scope.JAVA_FILE)
            )

        )
    }

    override fun getApplicableUastTypes() = listOf(UClass::class.java)

    override fun createUastHandler(context: JavaContext) = VerySimpleNamingVariablesDetectorVisitor(context)

    class VerySimpleNamingVariablesDetectorVisitor (private val context: JavaContext):
        UElementHandler() {

        override fun visitClass(node: UClass) {
//            print(node.asRecursiveLogString())

            node.accept(object: AbstractUastVisitor() {

                override fun visitField(node: UField): Boolean {
                    if (node.name.length < 2) {
                        context.report(
                            ISSUE_VERY_SIMPLE_NAMING_VARIABLES,
                            node, context.getLocation(node), "Incorrect named variable"
                        )
                    }
                    return super.visitField(node)
                }

                override fun visitDeclarationsExpression(node: UDeclarationsExpression): Boolean {
                    node.declarations.forEach { declaration ->
                        if(declaration is KotlinUAnnotatedLocalVariable){
                            declaration.name?.let {
                                if (it.length < 2) {
                                    context.report(
                                        ISSUE_VERY_SIMPLE_NAMING_VARIABLES,
                                        node, context.getLocation(node), "Incorrect named variable"
                                    )
                                }
                            }
                        }
                    }
                    return super.visitDeclarationsExpression(node)
                }

            })
        }
    }

}