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
import com.google.common.collect.ComparisonChain;
import com.google.common.io.LineProcessor;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author thomas
 */
public class QueryLogParser implements LineProcessor<List<Query>>
{

  private final Collection<Query> queries;

  private StringBuilder currentAQL;

  private static final Pattern COMPLETE_LINE = Pattern.compile(
    "^[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9](.*)function: COUNT, query: (?<query>.*), corpus: \\[(?<corpus>[^\\]]+)\\], runtime: (?<time>[0-9]+) ms$");

  private static final Pattern INCOMPLETE_START = Pattern.compile(
    "^[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9](.*)function: COUNT, query: (?<query>.*)$");

  private static final Pattern INCOMPLETE_END = Pattern.compile(
    "^(?<query>.*), corpus: \\[(?<corpus>[^\\]]+)\\], runtime: (?<time>[0-9]+) ms$");

  public QueryLogParser()
  {
    this(true);
  }

  public QueryLogParser(boolean uniqueOnly)
  {
    if (uniqueOnly)
    {
      Joiner j = Joiner.on(",");
      queries = new TreeSet<>((Query q1, Query q2)
        -> 
        {
          return ComparisonChain.start().compare(q1.getAql(), q2.getAql())
            .compare(j.join(q1.getCorpora()), j.join(q2.getCorpora())).result();
      });
    }
    else
    {
      queries = new LinkedList<>();
    }
  }

  private void addQuery(String aql, String corpora, String time)
  {
    Query q = new Query();
    q.setAql(aql);
    q.setCorpora(new LinkedHashSet<>(Splitter.on(',').omitEmptyStrings().
      trimResults().splitToList(corpora)));
    q.setExecutionTime(Optional.of(Long.parseLong(time)));

    
    q.setName(String.format("%05d",queries.size()));
    
    queries.add(q);
  }

  @Override
  public boolean processLine(String line) throws IOException
  {
    if (currentAQL == null)
    {
      Matcher mComplete = COMPLETE_LINE.matcher(line);
      Matcher mStart = INCOMPLETE_START.matcher(line);
      if (mComplete.matches())
      {
        addQuery(mComplete.group("query"), mComplete.group("corpus"), mComplete.group("time"));
      }
      else if (mStart.matches())
      {
        currentAQL = new StringBuilder();
        currentAQL.append(mStart.group("query"));
      }
    }
    else
    {
      // we have a query we need to complete
      Matcher mEnd = INCOMPLETE_END.matcher(line);
      if (mEnd.matches())
      {
        // query is finished
        currentAQL.append(mEnd.group("query"));
        
        addQuery(currentAQL.toString(), mEnd.group("corpus"), mEnd.group("time"));
        currentAQL = null;
      }
      else
      {
        currentAQL.append(line);
        // instead of a newline better use space
        currentAQL.append(" ");
      }
    }
    return true;
  }

  @Override
  public List<Query> getResult()
  {
    return new ArrayList<>(queries);
  }

}
