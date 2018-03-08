/*
 * Copyright 2018 Thomas Krause.
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
package org.corpus_tools.graphannis.api;

import org.corpus_tools.graphannis.CAPI;

/**
 * An API for managing corpora stored in a common location on the file system.
 *    
 * @author Thomas Krause <thomaskrause@posteo.de>
 */
public class CorpusStorageManager
{
  private final CAPI.ANNIS_CorpusStorage instance;
  
  public CorpusStorageManager(String dbDir) {
    this.instance = CAPI.INSTANCE.annis_cs_new(dbDir);
  }
  
  public long count(String corpusName, String queryAsJSON) {
    return CAPI.INSTANCE.annis_cs_count(instance, corpusName, queryAsJSON);
  }

  @Override
  protected void finalize() throws Throwable
  {
    super.finalize();
    if(instance != null) {
      CAPI.INSTANCE.annis_cs_free(instance);
    }
  }
  
  
}
