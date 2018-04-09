/*
 * Copyright 2014 SFB 632.
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

package org.corpus_tools.annis.ql.model;

import annis.model.QueryNode;

/**
 *
 * @author Amir Zeldes
 */ 
public class Near extends RangedJoin
{
  private String segmentationName;
  
  public Near(QueryNode target)
  {
    this(target, 0, 0);
  }
  
  public Near(QueryNode target, String segmentationName)
  {
    this(target, 0, 0, segmentationName);
  }

  public Near(QueryNode target, int distance)
  {
    this(target, distance, distance);
  }
  
  public Near(QueryNode target, int distance, String segmentationName)
  {
    this(target, distance, distance, segmentationName);
  }
  
  public Near(QueryNode target, int minDistance, int maxDistance)
  {
    this(target, minDistance, maxDistance, null);
  }
  
  public Near(QueryNode target, int minDistance, int maxDistance, String segmentationName)
  {
    super(target, minDistance, maxDistance);
    this.segmentationName = segmentationName;
  }

  public String getSegmentationName()
  {
    return segmentationName;
  }

  public void setSegmentationName(String segmentationName)
  {
    this.segmentationName = segmentationName;
  }
  
  
  @Override
  public String toString()
  {
    return "near node " + target.getId() + " (" +  segmentationName + " " 
      + minDistance + ", "
      + maxDistance + ")";
  }

  @Override
  public String toAqlOperator()
  {
    String extraDistance = "";
    if(minDistance == 0 && maxDistance == 0 )
    {
      extraDistance = "*";
    }
    else if(minDistance > 1 && minDistance == maxDistance)
    {
      extraDistance = "" + minDistance;
    }
    else if(minDistance > 1 || maxDistance > 1)
    {
      extraDistance = "" + minDistance + "," + maxDistance;
    }
    
    if(segmentationName == null)
    {
      return "^" + extraDistance;
    }
    else
    {
      return "^" + segmentationName + " " + extraDistance;
    }
  }
  
  

  @Override
  public int hashCode()
  {
    int hash = 7;
    return hash;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj == null)
    {
      return false;
    }
    if (getClass() != obj.getClass())
    {
      return false;
    }
    final Near other = (Near) obj;
    
    if ((this.segmentationName == null) ? (other.segmentationName != null) : !this.segmentationName.equals(other.segmentationName))
    {
      return false;
    }
    return super.equals(obj);
  }
  
  
}
