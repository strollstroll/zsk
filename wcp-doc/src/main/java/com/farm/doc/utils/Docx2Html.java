package com.farm.doc.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
//import java.io.OutputStream;
//import java.io.PrintStream;
import org.apache.poi.xwpf.converter.core.IImageExtractor;
import org.apache.poi.xwpf.converter.core.IURIResolver;
//import org.apache.poi.xwpf.converter.core.IXWPFConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;

public class Docx2Html
{
  public static void main(String[] args)
    throws IOException
  {
    String html = new Docx2Html().doGenerateHTMLFile(new File("D:\\2.docx"), new IImageExtractor()
      {
        public void extract(String imagePath, byte[] imageData)
          throws IOException
        {
          System.out.println(imagePath);
        }
      }, new IURIResolver()
      {
        public String resolve(String uri)
        {
          System.out.println("#####" + uri);
          return "demo";
        }
      });
    System.out.println(formatHtmlForWCP(html));
  }
  
  public static String formatHtmlForWCP(String html)
  {
    Document jsoupdoc = Jsoup.parse(html);
    for (Element node : jsoupdoc.getElementsByTag("div")) {
      node.removeAttr("style");
    }
    for (Element node : jsoupdoc.getElementsByTag("a")) {
      node.removeAttr("href");
    }
    for (Element node : jsoupdoc.getElementsByTag("p")) {
      if (node.hasAttr("class")) {
        node.attr("style", "font-weight:bold;");
      }
    }
    for (Element node : jsoupdoc.getElementsByClass("X1"))
    {
      String newTag = node.toString().replaceAll("<p ", "<h1 ").replaceAll("</p> ", "</h1>");
      node.after(newTag);
      node.remove();
    }
    for (Element node : jsoupdoc.getElementsByClass("X2"))
    {
      String newTag = node.toString().replaceAll("<p ", "<h2 ").replaceAll("</p> ", "</h2>");
      node.after(newTag);
      node.remove();
    }
    for (Element node : jsoupdoc.getElementsByClass("X3"))
    {
      String newTag = node.toString().replaceAll("<p ", "<h3 ").replaceAll("</p> ", "</h3>");
      node.after(newTag);
      node.remove();
    }
    return jsoupdoc.toString();
  }
  
  public String doGenerateHTMLFile(File wordFile, IImageExtractor doimgsave, IURIResolver getimgurl)
    throws IOException
  {
    String str = null;
    XWPFDocument document = new XWPFDocument(new FileInputStream(wordFile));
    try
    {
      XHTMLOptions options = XHTMLOptions.create();
      
      options.setExtractor(doimgsave);
      options.URIResolver(getimgurl);
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      XHTMLConverter.getInstance().convert(document, out, options);
      str = out.toString();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new RuntimeException("文档转换工具无法转换该文件，请检查文档是否损坏、文档是否是docx格式");
    }
    finally
    {
      document.close();
    }
    String htmlstr = Jsoup.parse(str).getElementsByTag("body").toString().replaceAll("</body>", "").replaceAll("<body>", "");
    String regstr = "&lt;";
    return htmlstr.replaceAll(regstr, "(");
  }
}
