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
package org.onosproject.cli.cfg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.onosproject.cli.AbstractShellCommand;
import org.onosproject.net.config.Config;
import org.onosproject.net.config.NetworkConfigService;
import org.onosproject.net.config.SubjectFactory;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Manages network configuration.
 */
@Command(scope = "onos", name = "netcfg",
        description = "Manages network configuration")
public class NetworkConfigCommand extends AbstractShellCommand {

<<<<<<< HEAD
    @Argument(index = 0, name = "subjectClassKey", description = "Subject class key",
            required = false, multiValued = false)
    String subjectClassKey = null;

    @Argument(index = 1, name = "subjectKey", description = "Subject key",
            required = false, multiValued = false)
    String subjectKey = null;
=======
    @Argument(index = 0, name = "subjectKey", description = "Subject key",
            required = false, multiValued = false)
    String subjectKey = null;

    @Argument(index = 1, name = "subject", description = "Subject",
            required = false, multiValued = false)
    String subject = null;
>>>>>>> 3d268c483e83ad1594aa035f9bec8a671ad42e76

    @Argument(index = 2, name = "configKey", description = "Config key",
            required = false, multiValued = false)
    String configKey = null;

    private final ObjectMapper mapper = new ObjectMapper();
    private NetworkConfigService service;

    @Override
    protected void execute() {
        service = get(NetworkConfigService.class);
        JsonNode root = mapper.createObjectNode();
<<<<<<< HEAD
        if (isNullOrEmpty(subjectClassKey)) {
            addAll((ObjectNode) root);
        } else {
            SubjectFactory subjectFactory = service.getSubjectFactory(subjectClassKey);
            if (isNullOrEmpty(subjectKey)) {
                addSubjectClass((ObjectNode) root, subjectFactory);
            } else {
                Object s = subjectFactory.createSubject(subjectKey);
                if (isNullOrEmpty(configKey)) {
                    addSubject((ObjectNode) root, s);
                } else {
                    root = getSubjectConfig(getConfig(s, subjectClassKey, configKey));
=======
        if (isNullOrEmpty(subjectKey)) {
            addAll((ObjectNode) root);
        } else {
            SubjectFactory subjectFactory = service.getSubjectFactory(subjectKey);
            if (isNullOrEmpty(subject)) {
                addSubjectClass((ObjectNode) root, subjectFactory);
            } else {
                Object s = subjectFactory.createSubject(subject);
                if (isNullOrEmpty(configKey)) {
                    addSubject((ObjectNode) root, s);
                } else {
                    root = getSubjectConfig(getConfig(s, subjectKey, configKey));
>>>>>>> 3d268c483e83ad1594aa035f9bec8a671ad42e76
                }
            }
        }

        try {
            print("%s", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error writing JSON to string", e);
        }
    }

    @SuppressWarnings("unchecked")
    private void addAll(ObjectNode root) {
        service.getSubjectClasses()
                .forEach(sc -> {
                    SubjectFactory sf = service.getSubjectFactory(sc);
<<<<<<< HEAD
                    addSubjectClass(newObject(root, sf.subjectClassKey()), sf);
=======
                    addSubjectClass(newObject(root, sf.subjectKey()), sf);
>>>>>>> 3d268c483e83ad1594aa035f9bec8a671ad42e76
                });
    }

    @SuppressWarnings("unchecked")
    private void addSubjectClass(ObjectNode root, SubjectFactory sf) {
        service.getSubjects(sf.subjectClass())
<<<<<<< HEAD
                .forEach(s -> addSubject(newObject(root, sf.subjectKey(s)), s));
=======
                .forEach(s -> addSubject(newObject(root, s.toString()), s));
>>>>>>> 3d268c483e83ad1594aa035f9bec8a671ad42e76
    }

    private void addSubject(ObjectNode root, Object s) {
        service.getConfigs(s).forEach(c -> root.set(c.key(), c.node()));
    }

    private JsonNode getSubjectConfig(Config config) {
        return config != null ? config.node() : null;
    }

    private Config getConfig(Object s, String subjectKey, String ck) {
        Class<? extends Config> configClass = service.getConfigClass(subjectKey, ck);
        return configClass != null ? service.getConfig(s, configClass) : null;
    }

    private ObjectNode newObject(ObjectNode parent, String key) {
        ObjectNode node = mapper.createObjectNode();
        parent.set(key, node);
        return node;
    }
}
