/*
 * Copyright 2015 Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onosproject.incubator.net.domain;

import com.google.common.annotations.Beta;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Intent domain identifier.
 */
@Beta
public class IntentDomainId {

    private final String id;

    /**
     * Creates an intent domain identifier from the specified string representation.
     *
     * @param value string value
     * @return intent identifier
     */
    public static IntentDomainId valueOf(String value) {
        return new IntentDomainId(value);
    }

    /**
     * Constructor for serializer.
     */
    IntentDomainId() {
        this.id = null;
    }

    /**
     * Constructs the ID corresponding to a given string value.
     *
     * @param value the underlying value of this ID
     */
    IntentDomainId(String value) {
        this.id = checkNotNull(value, "Intent domain ID cannot be null.");
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof IntentDomainId)) {
            return false;
        }
        IntentDomainId that = (IntentDomainId) obj;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public String toString() {
        return id;
    }
}
