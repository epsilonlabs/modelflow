package org.epsilonlabs.modelflow.mmc.emf.task.custom;

import org.eclipse.emf.codegen.ecore.genmodel.generator.GenModelGeneratorAdapterFactory;
import org.eclipse.emf.common.notify.Adapter;

public class CustomGenModelGeneratorAdapterFactory extends GenModelGeneratorAdapterFactory {

	@Override
	public Adapter createGenModelAdapter() {
		if (genModelGeneratorAdapter == null) {
			genModelGeneratorAdapter = new CustomGenModelGeneratorAdapter(this);
		}
		return genModelGeneratorAdapter;
	}

	@Override
	public Adapter createGenPackageAdapter() {
		if (genPackageGeneratorAdapter == null) {
			genPackageGeneratorAdapter = new CustomGenPackageGeneratorAdapter(this);
		}
		return genPackageGeneratorAdapter;
	}

	@Override
	public Adapter createGenClassAdapter() {
		if (genClassGeneratorAdapter == null) {
			genClassGeneratorAdapter = new CustomGenClassGeneratorAdapter(this);
		}
		return genClassGeneratorAdapter;
	}

	@Override
	public Adapter createGenEnumAdapter() {
		if (genEnumGeneratorAdapter == null) {
			genEnumGeneratorAdapter = new CustomGenEnumGeneratorAdapter(this);
		}
		return genEnumGeneratorAdapter;
	}
}
