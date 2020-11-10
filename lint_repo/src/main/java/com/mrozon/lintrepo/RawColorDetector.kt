package com.mrozon.lintrepo

import com.android.tools.lint.detector.api.*
import com.android.utils.forEach
import com.sun.org.apache.xerces.internal.dom.AttrImpl
import org.w3c.dom.Element

class RawColorDetector: ResourceXmlDetector() {

    companion object {

        val ISSUE_RAW_COLOR_RESOURCES = Issue.create(
            "RawColorInResources",
            "Using row color in tags - too bad((",
            "Needed using colors resource, but NOT use raw color in tags",
            Category.USABILITY,
            6,
            Severity.WARNING,
            Implementation(
                RawColorDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )

        )

    }

    override fun getApplicableElements(): Collection<String>? = XmlScannerConstants.ALL

    override fun visitElement(context: XmlContext, element: Element) {
        element.childNodes.forEach { node ->
            if(node.hasAttributes()){
                node.attributes.forEach { item ->
                    val name = (item as AttrImpl).name
                    if(name.contains("color", true)){
                        item.nodeValue?.let {
                            if(item.nodeValue[0] == '#')
                                context.report(
                                    ISSUE_RAW_COLOR_RESOURCES,
                                    element, context.getLocation(item), "Using row color in tags"
                                )
                        }
                    }
                }
            }
        }
    }


}