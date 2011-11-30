/*
 * Copyright (C) 2003-2011 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.wikbook.template.test.freemarker;

import org.wikbook.template.freemarker.ChildrenCallerMethod;
import org.wikbook.template.freemarker.JavadocCallerMethod;
import org.wikbook.template.freemarker.SiblingCallerMethod;
import org.wikbook.template.processing.metamodel.MetaModel;
import org.wikbook.template.test.AbstractFreemarkerTestCase;
import org.wikbook.template.test.AbstractProcessorTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:alain.defrance@exoplatform.com">Alain Defrance</a>
 * @version $Revision$
 */
public class FreemarkerChildrenTestCase extends AbstractFreemarkerTestCase {

  private ChildrenCallerMethod childrenCaller;

  private List<Map<String, Object>> posts;
  private List<Map<String, Object>> paths;

  private JavadocCallerMethod postDocCaller;
  private JavadocCallerMethod pathDocCaller;

  private SiblingCallerMethod postSiblingCaller;
  private SiblingCallerMethod pathSiblingCaller;

  @Override
  public void setUp() throws Exception {

    super.setUp();
    annotations = "javax.ws.rs.Path,javax.ws.rs.POST";

    Map<String, Object> data = buildModel("D");

    childrenCaller = (ChildrenCallerMethod) ((Map<String, Object>) data.get("@Path")).get("children");

    posts = (List<Map<String, Object>>) childrenCaller.exec(Arrays.asList("@POST"));
    paths = (List<Map<String, Object>>) childrenCaller.exec(Arrays.asList("@Path"));

    postDocCaller = (JavadocCallerMethod) posts.get(0).get("doc");
    pathDocCaller = (JavadocCallerMethod) paths.get(0).get("doc");

    postSiblingCaller = (SiblingCallerMethod) posts.get(0).get("sibling");
    pathSiblingCaller = (SiblingCallerMethod) paths.get(0).get("sibling");

  }

  public void testExists() throws Exception {

    assertNotNull(childrenCaller.exec(Arrays.asList("@POST")));
    assertNotNull(childrenCaller.exec(Arrays.asList("@Path")));

  }

  public void testElementName() throws Exception {

    assertEquals("m", posts.get(0).get("elementName"));
    assertEquals("m", paths.get(0).get("elementName"));

  }

  public void testAnnotationName() throws Exception {

    assertEquals("POST", posts.get(0).get("name"));
    assertEquals("Path", paths.get(0).get("name"));

  }

  public void testJavadocGeneralComment() throws Exception {

    assertEquals("General comment.", postDocCaller.exec(new ArrayList()).toString());
    assertEquals("General comment.", pathDocCaller.exec(new ArrayList()).toString());

  }

  public void testJavadocSingleValue() throws Exception {

    assertEquals("1.0", postDocCaller.exec(Arrays.asList("since")).toString());
    assertEquals("1.0", pathDocCaller.exec(Arrays.asList("since")).toString());

  }

  public void testJavadocMultipleValue() throws Exception {

    assertEquals("foo, bar", postDocCaller.exec(Arrays.asList("author")).toString());
    assertEquals("foo, bar", pathDocCaller.exec(Arrays.asList("author")).toString());

  }

  public void testJavadocNoValue() throws Exception {

    assertEquals("deprecated", postDocCaller.exec(Arrays.asList("deprecated")).toString());
    assertEquals("deprecated", pathDocCaller.exec(Arrays.asList("deprecated")).toString());

  }

  public void testSibling() throws Exception {

    assertEquals(paths.get(0), postSiblingCaller.exec(Arrays.asList("@Path")));
    assertEquals(posts.get(0), pathSiblingCaller.exec(Arrays.asList("@POST")));

  }
  
}