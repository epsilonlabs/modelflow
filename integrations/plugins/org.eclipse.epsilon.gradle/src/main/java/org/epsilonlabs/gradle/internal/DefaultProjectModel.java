package org.epsilonlabs.gradle.internal;

import javax.annotation.Nullable;

import org.gradle.internal.DisplayName;

public class DefaultProjectModel implements ProjectModel {

	private final DisplayName displayName;
	private final Object id;

	public DefaultProjectModel(DisplayName displayName, Object id) {
        this.displayName = displayName;
        this.id = id;
    }

	public DisplayName getDisplayName() {
		return displayName;
	}

    @Nullable
    public <T> T getCoordinates(Class<T> type) {
        if (type.isInstance(id)) {
            return type.cast(id);
        }
        return null;
    }

}
