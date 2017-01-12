/*
 * Copyright 2016 Thomas Krause.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.corpus_tools.annis.benchmark.generator;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import java.util.LinkedHashSet;
import java.util.Set;
import javafx.util.StringConverter;

/**
 *
 * @author thomas
 */
public class StringSetConverter extends StringConverter<Set<String>>
{

  @Override
  public String toString(Set<String> object)
  {
    return Joiner.on(",").join(object);
  }

  @Override
  public Set<String> fromString(String string)
  {
    return new LinkedHashSet<>(Splitter.on(",").omitEmptyStrings().trimResults().splitToList(string));
  }
  
}
