package com.farm.lucene.common;

import org.apache.lucene.document.Document;

public abstract interface ScoreDocFilterInter
{
  public abstract boolean doScan(Document paramDocument);
}
