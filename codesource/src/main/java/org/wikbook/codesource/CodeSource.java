/*
 * Copyright (C) 2010 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.wikbook.codesource;

import org.wikbook.text.Clip;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public abstract class CodeSource
{

   /** . */
   private final Clip clip;

   /** . */
   private final String javaDoc;

   protected CodeSource(Clip clip, String javaDoc)
   {
      if (clip == null)
      {
         throw new NullPointerException();
      }

      //
      this.clip = clip;
      this.javaDoc = javaDoc;
   }

   public final String getClip()
   {
      return getType().source.clip(clip);
   }

   public final String getJavaDoc()
   {
      return javaDoc;
   }

   protected abstract TypeSource getType();
}
