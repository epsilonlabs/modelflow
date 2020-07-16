/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ******************************************************************************/
package org.epsilonlabs.modelflow.mmc.epsilon.resource.wip;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;


public class TransformingTest {

	public interface ITransformer<OUT, IN> {
	    OUT transform(IN in);
	    
	    IN back(OUT in);
	}
	
	static abstract class Transformer<OUT, IN>  implements ITransformer<OUT, IN>{
		IN obj;
		public Transformer(IN obj) {
			this.obj = obj;
		}
		@Override
		public IN back(OUT in) {
			return obj;
		}
	}
	
	static class EpsilonEmf implements IEMF {
		
		Resource resW;
	
		IEMF emf = new EMF(this);
		ITransformer<IEMF, EpsilonEmf> transformer = new Transformer<TransformingTest.IEMF, TransformingTest.EpsilonEmf>(this) {
			@Override
			public IEMF transform(EpsilonEmf in) {
				return emf;
			}
			public EpsilonEmf back(IEMF in) {
				emf = new EMF(this.obj, in);
				return super.back(in);
			};
			
		};
		
		public IEMF transform(){
			return transformer.transform(this);
		}
		
		public EpsilonEmf from(IEMF emf){
			return transformer.back(emf);
		}

		@Override
		public Object get() {
			System.out.println("getting");
			return null;
		}
		
		@Override
		public void setModel(String file) {
			this.emf.setModel(file);
		}

		@Override
		public String getModel() {
			return this.emf.getModel();
		}
	}
	
	static class ATLEmf implements IEMF {
		
		Resource resW;
		String model;
		IEMF emf = new EMF(this);
		ITransformer<IEMF, ATLEmf> transformer = new Transformer<TransformingTest.IEMF, TransformingTest.ATLEmf>(this) {
			@Override
			public IEMF transform(ATLEmf in) {
				return new EMF(this.obj);
			}
			@Override
			public ATLEmf back(IEMF in) {
				// compare this.emf with in
				// clear
				emf = new EMF(this.obj, in);
				return super.back(in);
			}
		};
		
		public IEMF transform(){
			return transformer.transform(this);
		}
		
		public ATLEmf from(IEMF emf){
			return transformer.back(emf);
		}

		@Override
		public Object get() {
			System.out.println("getting");
			return null;
		}

		@Override
		public void setModel(String file) {
			this.emf.setModel(file);
		}

		@Override
		public String getModel() {
			return this.emf.getModel();
		}
		
		@Override
		public void load() {
			IEMF.super.load();
			//TODO
		}
	}
	
	public interface IEMF {
		default void load() {
			System.out.println(getClass().getName() + " loading");
		};
		
		void setModel(String file);
		String getModel();
		Object get();
	}
	
	static class EMF implements IEMF {
		String name = "Emf";
		Resource res;
		String model;
		
		IEMF object;
		public EMF(IEMF object){
			this.object= object;
		}
		public EMF(IEMF object, IEMF emf){
			this.object= object;
			// for all properties...
			this.model = emf.getModel();
		}
		
		@Override
		public Object get() {
			System.out.println("getting");
			return null;
		}
		@Override
		public void load() {
			IEMF.super.load(); // PRINT STATEMENT OF CLASS
			this.object.load();
		}
		@Override
		public void setModel(String file) {
			this.model = file;
		}

		@Override
		public String getModel() {
			return this.model;
		}

	}
	
	public static void main(String[] args) {
		List<Object> resources = new ArrayList<>();
		TransformingTest.EpsilonEmf epsilonEmf = new TransformingTest.EpsilonEmf();
		TransformingTest.ATLEmf atlEmf = new TransformingTest.ATLEmf();
		
		resources.add(epsilonEmf);
		resources.add(atlEmf);
		
		IEMF epsilon = epsilonEmf.transform();
		epsilon.setModel("epsilon.emf");
		System.out.println("File: "+ epsilon.getModel());
		epsilon.load();
		IEMF atl = atlEmf.transform();
		atl.setModel("atl.emf");
		System.out.println("File: "+ atl.getModel());
		atl.load();
		
		EpsilonEmf epsilonFromAtl = epsilonEmf.from(atl);
		System.out.println("File: "+ epsilonFromAtl.getModel());
		epsilonFromAtl.load();
		
		ATLEmf atlFromEpsilon = atlEmf.from(epsilonEmf);
		System.out.println("File: "+ atlFromEpsilon.getModel());
		atlFromEpsilon.load();
	}
	
}

