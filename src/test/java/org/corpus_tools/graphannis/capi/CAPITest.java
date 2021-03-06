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
package org.corpus_tools.graphannis.capi;

import com.google.common.io.Files;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author thomas
 */
public class CAPITest
{
  
  
  
  public CAPITest()
  {
  }
  
  @BeforeClass
  public static void setUpClass()
  {
  }
  
  @AfterClass
  public static void tearDownClass()
  {
  }
  
  @Before
  public void setUp()
  {

  }
  
  @After
  public void tearDown()
  {
  }

  @Test
  public void testDispose()
  {
      File tmpDir = Files.createTempDir();
      
      CAPI.AnnisCorpusStorage cs = CAPI.annis_cs_with_auto_cache_size(tmpDir.getAbsolutePath(), false, null);
      
      CAPI.AnnisVec_AnnisCString list = CAPI.annis_cs_list(cs, null);
      
      // dispose in "wrong" order
      cs.dispose();
      list.dispose();
  }
  
}
