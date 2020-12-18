package com.mrozon.lintrepo

import com.android.tools.lint.detector.api.*
import com.android.utils.forEach
import com.sun.org.apache.xerces.internal.dom.AttrImpl
import com.sun.org.apache.xerces.internal.dom.AttrNSImpl
import org.w3c.dom.Attr
import org.w3c.dom.Element
import org.w3c.dom.Node

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

        val INTERESTED_TAGS = listOf(
            "color",
            "background",
            "background",
            "tint"
        )

    }

    override fun getApplicableElements(): Collection<String>? = XmlScannerConstants.ALL

    override fun visitElement(context: XmlContext, element: Element) {
        element.childNodes.forEach { node ->
            if(node.hasAttributes()){
                node.attributes.forEach { item ->
                    val name = (item as AttrImpl).name
                    INTERESTED_TAGS.forEach { tag ->
                        if (name.contains(tag, true)) {
                            item.nodeValue?.let { nodeName ->
                                if (nodeName.startsWith("#"))
                                    context.report(
                                        ISSUE_RAW_COLOR_RESOURCES,
                                        element,
                                        context.getLocation(item),
                                        "Using row color in tags"
                                    )
                            }
                        }
                    }
                }
            }
        }
    }


}