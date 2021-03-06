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
import org.wikbook.text.TextArea;

import java.util.LinkedHashMap;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class TypeSource extends CodeSource
{

   /** . */
   final TextArea source;

   /** . */
   private final String name;

   /** . */
   private final LinkedHashMap<MemberKey, SignedMemberSource> methods;

   /** . */
   private final LinkedHashMap<String, NamedMemberSource> fields;

   TypeSource(TextArea source, String name, Clip clip, String javaDoc)
   {
      super(clip, javaDoc);

      //
      this.source = source;
      this.name = name;
      this.methods = new LinkedHashMap<MemberKey, SignedMemberSource>();
      this.fields = new LinkedHashMap<String, NamedMemberSource>();
   }

   void addMember(MemberSource member)
   {
      if (member instanceof SignedMemberSource)
      {
         SignedMemberSource signedMember = (SignedMemberSource)member;
         if (signedMember.type != null)
         {
            throw new IllegalArgumentException();
         }

         //
         methods.put(signedMember.key, signedMember);
         signedMember.type = this;
      }
      else if (member instanceof NamedMemberSource)
      {
         NamedMemberSource namedMember = (NamedMemberSource)member;
         if (namedMember.type != null)
         {
            throw new IllegalArgumentException();
         }

         //
         fields.put(namedMember.name, namedMember);
         namedMember.type = this;
      }
      else
      {
         throw new IllegalArgumentException("Not accepted " + member);
      }
   }

   public String getName()
   {
      return name;
   }

   @Override
   protected TypeSource getType()
   {
      return this;
   }

   public MemberSource findMember(String member)
   {
      if (member == null)
      {
         throw new NullPointerException();
      }

      //
      MemberKey key = MemberKey.parse(member);

      //
      if (key.signature == null)
      {
         NamedMemberSource field = fields.get(key.name);
         if (field != null)
         {
            return field;
         }
         else
         {
            key = new MemberKey(key.name, new Signature());
         }
      }

      //
      return methods.get(key);
   }

   @Override
   public String toString()
   {
      return "TypeSource[fqn=" + name + "]";
   }
}
