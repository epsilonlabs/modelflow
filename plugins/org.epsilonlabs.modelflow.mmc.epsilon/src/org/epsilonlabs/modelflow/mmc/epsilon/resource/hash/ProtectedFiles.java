/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.resource.hash;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.epsilon.egl.merge.output.Output;
import org.eclipse.epsilon.egl.merge.output.Region;
import org.eclipse.epsilon.egl.merge.partition.CommentBlockPartitioner;
import org.eclipse.epsilon.egl.merge.partition.CompositePartitioner;
import org.eclipse.epsilon.egl.util.FileUtil;
import org.epsilonlabs.modelflow.management.param.hash.Hasher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unchecked")
public class ProtectedFiles {

	private static final Logger LOG = LoggerFactory.getLogger(ProtectedFiles.class);

	private Map<String, String> filesWithHash = new HashMap<>();
	private CompositePartitioner partitioner = null;
	private List<String[]> comments = new ArrayList<>();

	public ProtectedFiles(Collection<String> files, CompositePartitioner partitioner) {
		files.forEach(f->this.filesWithHash.put(f, ""));
		this.partitioner = partitioner;
		try {
			Field field = this.partitioner.getClass().getDeclaredField("partitioners");
			field.setAccessible(true);
			Set<CommentBlockPartitioner> partitioners = (Set<CommentBlockPartitioner>) field.get(this.partitioner);
			partitioners.forEach(p->this.comments.add(new String[]{p.getStartComment(), p.getEndComment()}));
		} catch (Exception e) {
			LOG.error("Protected Files", e);
		}		
	}
	
	public ProtectedFiles(Map<String, String> files, List<String[]> comments) {
		this.filesWithHash = new HashMap<>(files);
		this.comments = new ArrayList<>(comments);
		this.partitioner = new CompositePartitioner();
		for (String[] c : comments) {
			this.partitioner.addPartitioner(new CommentBlockPartitioner(c[0], c[1]));
		}
	}

	public ProtectedFiles(Map<String, Object> trace) {
		this((Map<String, String>)trace.get("files"), (List<String[]>)trace.get("comments"));
	}

	public Set<String> getFiles() {
		return this.filesWithHash.keySet();
	}
	
	public void updateHash(String file, String hash){
		this.filesWithHash.put(file, hash);
	}

	public CompositePartitioner getPartitioner() {
		return this.partitioner;
	}

	public Map<String, Object> compute() {
		getFiles().forEach(fileLocation-> {
			try {
				String contents = FileUtil.readIfExists(new File(fileLocation));
				Output partition = getPartitioner().partition(contents);
				List<Region> regions = new ArrayList<>(partition.getRegions());
				partition.getLocatedRegions().forEach(lr-> {
					if (lr.isEnabled()) {
						regions.remove(lr);
					}
				});
				List<String> resultingContents = regions.stream().map(r->r.getContents()).collect(Collectors.toList());
				String outputToHash = String.join("",resultingContents);
				String hash = (String) Hasher.hash(outputToHash);
				updateHash(fileLocation, hash);
			} catch (IOException e) {
				LOG.error("Unable to read file", e);
			}
		});
		
		Map<String, Object> map = new HashMap<>();
		map.put("files", this.filesWithHash);
		map.put("comments", this.comments);
		return map;
	}

	@Override
	public String toString() {
		return "ProtectedFiles [filesWithHash=" + filesWithHash + ", comments=" + comments + "]";
	}

}
