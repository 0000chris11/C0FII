package com.cofii2.xml;

import org.w3c.dom.Document;

@FunctionalInterface
public interface DocumentAction {
    public Document action(Document doc);
}
