package org.wikbook.template.freemarker;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.wikbook.template.processing.metamodel.MetaModel;

import javax.annotation.processing.Filer;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.Map;

/**
 * @author <a href="mailto:alain.defrance@exoplatform.com">Alain Defrance</a>
 * @version $Revision$
 */
public class FreemarkerRenderer {

  private FreemarkerModelBuilder builder;

  public FreemarkerRenderer() {
    builder = new FreemarkerModelBuilder();
  }

  public void render(String templateName, MetaModel model, OutputStream os, Filer filer) throws IOException {

    Map<String, Object> data = builder.build(model);
    Configuration cfg = new Configuration();

    try {

      cfg.setTemplateLoader(new FilerTemplateLoader(filer));
      cfg.setObjectWrapper(new DefaultObjectWrapper());

      Template temp = cfg.getTemplate(templateName);
      OutputStreamWriter osw = new OutputStreamWriter(os);
      
      temp.process(data, osw);

      osw.flush();
      osw.close();

    } catch (TemplateException e) {
      e.printStackTrace();
    }

  }

}