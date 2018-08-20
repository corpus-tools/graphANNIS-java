/*
   Copyright 2017 Thomas Krause <thomaskrause@posteo.de>

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package org.corpus_tools.graphannis.capi;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;

public class CAPI implements Library {

    static {
        Native.register(CAPI.class, "graphannis");
    }

    public static class AnnisCorpusStorage extends PointerType {
        public synchronized void dispose() {
            try {
                if (this.getPointer() != Pointer.NULL) {
                    CAPI.annis_cs_free(this.getPointer());
                }
            } finally {
                this.setPointer(Pointer.NULL);
            }
        }

        @Override
        protected void finalize() throws Throwable {
            this.dispose();
            super.finalize();
        }
    }

    public static class AnnisGraphUpdate extends AnnisPtr {
    }

    public static class AnnisGraphDB extends AnnisPtr {
    }

    public static class AnnisVec_AnnisCString extends AnnisPtr {
    }

    public static class AnnisVec_AnnisAnnotation extends AnnisPtr {
        
    }


    public static class AnnisIterPtr_AnnisNodeID extends AnnisPtr {
    }

    public static class AnnisComponentConst extends PointerType {
    }

    public static class AnnisVec_AnnisComponent extends AnnisPtr {
    }

    public static class AnnisVec_AnnisEdge extends AnnisPtr {
    }

    public static class AnnisMatrix_AnnisCString extends AnnisPtr {
    }

    public static class AnnisFrequencyTable_AnnisCString extends AnnisPtr {
    }

    // general functions

    protected static native void annis_free(Pointer ptr);

    public static native void annis_str_free(Pointer ptr);

    public static native NativeLong annis_error_size(Pointer ptr);
    public static native String annis_error_get_msg(Pointer ptr, NativeLong i);
    public static native String annis_error_get_kind(Pointer ptr, NativeLong i);

    public static native void annis_init_logging(String logfile, int level, AnnisErrorListRef err);

    // vector and iterator functions
    public static native NativeLong annis_vec_str_size(AnnisVec_AnnisCString ptr);

    public static native String annis_vec_str_get(AnnisVec_AnnisCString ptr, NativeLong i);

    public static native AnnisVec_AnnisCString annis_vec_str_new();

    public static native void annis_vec_str_push(AnnisVec_AnnisCString ptr, String v);

    public static native NativeLong annis_vec_annotation_size(AnnisVec_AnnisAnnotation ptr);

    public static native AnnisAnnotation.ByReference annis_vec_annotation_get(AnnisVec_AnnisAnnotation ptr,
            NativeLong i);

    public static native NativeLong annis_vec_component_size(AnnisVec_AnnisComponent ptr);

    public static native AnnisComponentConst annis_vec_component_get(AnnisVec_AnnisComponent ptr, NativeLong i);

    public static native NativeLong annis_vec_edge_size(AnnisVec_AnnisEdge ptr);

    public static native AnnisEdge annis_vec_edge_get(AnnisVec_AnnisEdge ptr, NativeLong i);

    public static native NodeIDByRef annis_iter_nodeid_next(AnnisIterPtr_AnnisNodeID ptr);

    public static native String annis_matrix_str_get(AnnisMatrix_AnnisCString ptr, NativeLong row, NativeLong col);

    public static native NativeLong annis_matrix_str_ncols(AnnisMatrix_AnnisCString ptr);

    public static native NativeLong annis_matrix_str_nrows(AnnisMatrix_AnnisCString ptr);

    public static native NativeLong annis_freqtable_str_count(AnnisFrequencyTable_AnnisCString ptr, NativeLong row);

    public static native String annis_freqtable_str_get(AnnisFrequencyTable_AnnisCString ptr, NativeLong row,
            NativeLong col);

    public static native NativeLong annis_freqtable_str_ncols(AnnisFrequencyTable_AnnisCString ptr);

    public static native NativeLong annis_freqtable_str_nrows(AnnisFrequencyTable_AnnisCString ptr);

    // corpus storage class

    public static native AnnisCorpusStorage annis_cs_new(String db_dir, boolean use_parallel);
    protected static native void annis_cs_free(Pointer ptr);

    public static native AnnisVec_AnnisCString annis_cs_list(AnnisCorpusStorage cs, AnnisErrorListRef err);

    public static native long annis_cs_count(AnnisCorpusStorage cs, String corpusName, String queryAsJSON,
            AnnisErrorListRef err);

    public static native AnnisCountExtra.ByValue annis_cs_count_extra(AnnisCorpusStorage cs, String corpusName,
            String queryAsJSON, AnnisErrorListRef err);

    public static native AnnisVec_AnnisCString annis_cs_find(AnnisCorpusStorage cs, String corpusName,
            String queryAsJSON, long offset, long limit, int order, AnnisErrorListRef err);

    public static native AnnisGraphDB annis_cs_subgraph(AnnisCorpusStorage cs, String corpusName,
            AnnisVec_AnnisCString node_ids, NativeLong ctx_left, NativeLong ctx_right, AnnisErrorListRef err);

    public static native AnnisGraphDB annis_cs_subcorpus_graph(AnnisCorpusStorage cs, String corpusName,
            AnnisVec_AnnisCString corpus_ids, AnnisErrorListRef err);

    public static native AnnisGraphDB annis_cs_corpus_graph(AnnisCorpusStorage cs, String corpusName,
            AnnisErrorListRef err);

    public static native AnnisGraphDB annis_cs_subgraph_for_query(AnnisCorpusStorage cs, String corpusName,
            String queryAsJSON, AnnisErrorListRef err);

    public static native AnnisFrequencyTable_AnnisCString annis_cs_frequency(AnnisCorpusStorage cs,
            String corpusName, String queryAsJSON, String frequencyQueryDefinition, AnnisErrorListRef err);

    public static native AnnisVec_AnnisComponent annis_cs_all_components_by_type(AnnisCorpusStorage cs,
            String corpusName, int ctype);

    public static native AnnisMatrix_AnnisCString annis_cs_list_node_annotations(AnnisCorpusStorage cs,
            String corpusName, boolean listValues, boolean onlyMostFrequentValues);

    public static native AnnisMatrix_AnnisCString annis_cs_list_edge_annotations(AnnisCorpusStorage cs,
            String corpusName, int component_type, String component_name, String component_layer, boolean listValues,
            boolean onlyMostFrequentValues);

    public static native void annis_cs_apply_update(AnnisCorpusStorage cs, String corpusName,
            AnnisGraphUpdate update, AnnisErrorListRef err);

    public static native void annis_cs_import_relannis(AnnisCorpusStorage cs, String corpusName, 
            String path, AnnisErrorListRef err);

    public static native boolean annis_cs_delete(AnnisCorpusStorage cs, String corpusName, AnnisErrorListRef err);

    // graph update class

    public static native AnnisGraphUpdate annis_graphupdate_new();

    public static native void annis_graphupdate_add_node(AnnisGraphUpdate ptr, String node_name, String node_type);

    public static native void annis_graphupdate_delete_node(AnnisGraphUpdate ptr, String node_name);

    public static native void annis_graphupdate_add_node_label(AnnisGraphUpdate ptr, String node_name, String anno_ns,
            String anno_name, String anno_value);

    public static native void annis_graphupdate_delete_node_label(AnnisGraphUpdate ptr, String node_name,
            String anno_ns, String anno_name);

    public static native void annis_graphupdate_add_edge(AnnisGraphUpdate ptr, String source_node, String target_node,
            String layer, String component_type, String component_name);

    public static native void annis_graphupdate_delete_edge(AnnisGraphUpdate ptr, String source_node,
            String target_node, String layer, String component_type, String component_name);

    public static native void annis_graphupdate_add_edge_label(AnnisGraphUpdate ptr, String source_node,
            String target_node, String layer, String component_type, String component_name, String anno_ns,
            String anno_name, String anno_value);

    public static native void annis_graphupdate_delete_edge_label(AnnisGraphUpdate ptr, String source_node,
            String target_node, String layer, String component_type, String component_name, String anno_ns,
            String anno_name);

    // GraphDB classes

    public static native AnnisString annis_component_layer(AnnisComponentConst component);

    public static native AnnisString annis_component_name(AnnisComponentConst component);

    public static native int annis_component_type(AnnisComponentConst component);

    public static native AnnisVec_AnnisAnnotation annis_graph_node_labels(AnnisGraphDB g, NodeID nodeID);

    public static native AnnisIterPtr_AnnisNodeID annis_graph_nodes_by_type(AnnisGraphDB g, String node_type);

    public static native AnnisVec_AnnisComponent annis_graph_all_components(AnnisGraphDB g);

    public static native AnnisVec_AnnisComponent annis_graph_all_components_by_type(AnnisGraphDB g, int ctype);

    public static native AnnisVec_AnnisEdge annis_graph_outgoing_edges(AnnisGraphDB g, NodeID source,
            AnnisComponentConst component);

    public static native AnnisVec_AnnisAnnotation annis_graph_edge_labels(AnnisGraphDB g, AnnisEdge.ByValue edge,
            AnnisComponentConst component);

    public static native AnnisString annis_graph_str(AnnisGraphDB g, StringID str_id);
}
