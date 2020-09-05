package com.aneonex.bitcoinchecker.datamodule.util

import org.w3c.dom.Document
import org.w3c.dom.Node

object XmlParserUtils {
    fun getFirstElementByTagName(doc: Document, name: String?): Node? {
        val nodes = doc.getElementsByTagName(name)
        return if (nodes != null && nodes.length > 0) nodes.item(0) else null
    }

    @Throws(Exception::class)
    fun getDoubleNodeValue(node: Node?): Double {
        return getTextNodeValue(node).toDouble()
    }

    @Throws(Exception::class)
    fun getTextNodeValue(node: Node?): String {
        var child: Node?
        if (node != null) {
            if (node.hasChildNodes()) {
                child = node.firstChild
                while (child != null) {
                    if (child.nodeType == Node.TEXT_NODE) {
                        return child.nodeValue
                    }
                    child = child.nextSibling
                }
            }
        }
        return ""
    }
}