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
package org.onosproject.rest.resources;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.onosproject.net.config.NetworkConfigService;
import org.onosproject.net.config.SubjectFactory;
import org.onosproject.rest.AbstractWebResource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

/**
 * Manage network configurations.
 */
@Path("network/configuration")
public class NetworkConfigWebResource extends AbstractWebResource {

    /**
     * Get entire network configuration base.
     *
     * @return network configuration JSON
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Response download() {
        NetworkConfigService service = get(NetworkConfigService.class);
        ObjectNode root = mapper().createObjectNode();
<<<<<<< HEAD
        service.getSubjectClasses().forEach(sc -> {
            SubjectFactory subjectFactory = service.getSubjectFactory(sc);
            produceJson(service, newObject(root, subjectFactory.subjectClassKey()),
                        subjectFactory, sc);
        });
=======
        service.getSubjectClasses().forEach(sc ->
            produceJson(service, newObject(root, service.getSubjectFactory(sc).subjectKey()), sc));
>>>>>>> 3d268c483e83ad1594aa035f9bec8a671ad42e76
        return ok(root).build();
    }

    /**
     * Get all network configuration for a subject class.
     *
<<<<<<< HEAD
     * @param subjectClassKey subject class key
     * @return network configuration JSON
     */
    @GET
    @Path("{subjectClassKey}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Response download(@PathParam("subjectClassKey") String subjectClassKey) {
        NetworkConfigService service = get(NetworkConfigService.class);
        ObjectNode root = mapper().createObjectNode();
        SubjectFactory subjectFactory = service.getSubjectFactory(subjectClassKey);
        produceJson(service, root, subjectFactory, subjectFactory.subjectClass());
=======
     * @param subjectKey subject class key
     * @return network configuration JSON
     */
    @GET
    @Path("{subjectKey}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Response download(@PathParam("subjectKey") String subjectKey) {
        NetworkConfigService service = get(NetworkConfigService.class);
        ObjectNode root = mapper().createObjectNode();
        produceJson(service, root, service.getSubjectFactory(subjectKey).subjectClass());
>>>>>>> 3d268c483e83ad1594aa035f9bec8a671ad42e76
        return ok(root).build();
    }

    /**
<<<<<<< HEAD
     * Get all network configuration for a subjectKey.
     *
     * @param subjectClassKey subjectKey class key
     * @param subjectKey      subjectKey key
     * @return network configuration JSON
     */
    @GET
    @Path("{subjectClassKey}/{subjectKey}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Response download(@PathParam("subjectClassKey") String subjectClassKey,
                             @PathParam("subjectKey") String subjectKey) {
        NetworkConfigService service = get(NetworkConfigService.class);
        ObjectNode root = mapper().createObjectNode();
        SubjectFactory subjectFactory = service.getSubjectFactory(subjectClassKey);
        produceSubjectJson(service, root, subjectFactory.createSubject(subjectKey));
=======
     * Get all network configuration for a subject.
     *
     * @param subjectKey subject class key
     * @param subject    subject key
     * @return network configuration JSON
     */
    @GET
    @Path("{subjectKey}/{subject}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Response download(@PathParam("subjectKey") String subjectKey,
                             @PathParam("subject") String subject) {
        NetworkConfigService service = get(NetworkConfigService.class);
        ObjectNode root = mapper().createObjectNode();
        produceSubjectJson(service, root,
                           service.getSubjectFactory(subjectKey).createSubject(subject));
>>>>>>> 3d268c483e83ad1594aa035f9bec8a671ad42e76
        return ok(root).build();
    }

    /**
<<<<<<< HEAD
     * Get specific network configuration for a subjectKey.
     *
     * @param subjectClassKey subjectKey class key
     * @param subjectKey      subjectKey key
     * @param configKey       configuration class key
     * @return network configuration JSON
     */
    @GET
    @Path("{subjectClassKey}/{subjectKey}/{configKey}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Response download(@PathParam("subjectClassKey") String subjectClassKey,
                             @PathParam("subjectKey") String subjectKey,
                             @PathParam("configKey") String configKey) {
        NetworkConfigService service = get(NetworkConfigService.class);
        return ok(service.getConfig(service.getSubjectFactory(subjectClassKey).createSubject(subjectKey),
                                    service.getConfigClass(subjectClassKey, configKey)).node()).build();
=======
     * Get specific network configuration for a subject.
     *
     * @param subjectKey subject class key
     * @param subject    subject key
     * @param configKey  configuration class key
     * @return network configuration JSON
     */
    @GET
    @Path("{subjectKey}/{subject}/{configKey}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Response download(@PathParam("subjectKey") String subjectKey,
                             @PathParam("subject") String subject,
                             @PathParam("configKey") String configKey) {
        NetworkConfigService service = get(NetworkConfigService.class);
        return ok(service.getConfig(service.getSubjectFactory(subjectKey).createSubject(subject),
                                    service.getConfigClass(subjectKey, configKey)).node()).build();
>>>>>>> 3d268c483e83ad1594aa035f9bec8a671ad42e76
    }

    @SuppressWarnings("unchecked")
    private void produceJson(NetworkConfigService service, ObjectNode node,
<<<<<<< HEAD
                             SubjectFactory subjectFactory, Class subjectClass) {
        service.getSubjects(subjectClass).forEach(s ->
            produceSubjectJson(service, newObject(node, subjectFactory.subjectKey(s)), s));
=======
                             Class subjectClass) {
        service.getSubjects(subjectClass).forEach(s ->
            produceSubjectJson(service, newObject(node, s.toString()), s));
>>>>>>> 3d268c483e83ad1594aa035f9bec8a671ad42e76
    }

    private void produceSubjectJson(NetworkConfigService service, ObjectNode node,
                                    Object subject) {
        service.getConfigs(subject).forEach(c -> node.set(c.key(), c.node()));
    }


    /**
     * Upload bulk network configuration.
     *
     * @param request network configuration JSON rooted at the top node
<<<<<<< HEAD
     * @return empty response
     * @throws IOException if unable to parse the request
=======
     * @throws IOException if unable to parse the request
     * @return empty response
>>>>>>> 3d268c483e83ad1594aa035f9bec8a671ad42e76
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Response upload(InputStream request) throws IOException {
        NetworkConfigService service = get(NetworkConfigService.class);
        ObjectNode root = (ObjectNode) mapper().readTree(request);
        root.fieldNames()
                .forEachRemaining(sk -> consumeJson(service, (ObjectNode) root.path(sk),
                                                    service.getSubjectFactory(sk)));
        return Response.ok().build();
    }

    /**
     * Upload multiple network configurations for a subject class.
     *
<<<<<<< HEAD
     * @param subjectClassKey subject class key
     * @param request         network configuration JSON rooted at the top node
=======
     * @param subjectKey subject class key
     * @param request    network configuration JSON rooted at the top node
>>>>>>> 3d268c483e83ad1594aa035f9bec8a671ad42e76
     * @return empty response
     * @throws IOException if unable to parse the request
     */
    @POST
<<<<<<< HEAD
    @Path("{subjectClassKey}")
    @Consumes(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Response upload(@PathParam("subjectClassKey") String subjectClassKey,
                           InputStream request) throws IOException {
        NetworkConfigService service = get(NetworkConfigService.class);
        ObjectNode root = (ObjectNode) mapper().readTree(request);
        consumeJson(service, root, service.getSubjectFactory(subjectClassKey));
=======
    @Path("{subjectKey}")
    @Consumes(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Response upload(@PathParam("subjectKey") String subjectKey,
                           InputStream request) throws IOException {
        NetworkConfigService service = get(NetworkConfigService.class);
        ObjectNode root = (ObjectNode) mapper().readTree(request);
        consumeJson(service, root, service.getSubjectFactory(subjectKey));
>>>>>>> 3d268c483e83ad1594aa035f9bec8a671ad42e76
        return Response.ok().build();
    }

    /**
<<<<<<< HEAD
     * Upload mutliple network configurations for a subjectKey.
     *
     * @param subjectClassKey subjectKey class key
     * @param subjectKey      subjectKey key
     * @param request         network configuration JSON rooted at the top node
=======
     * Upload mutliple network configurations for a subject.
     *
     * @param subjectKey subject class key
     * @param subject    subject key
     * @param request    network configuration JSON rooted at the top node
>>>>>>> 3d268c483e83ad1594aa035f9bec8a671ad42e76
     * @return empty response
     * @throws IOException if unable to parse the request
     */
    @POST
<<<<<<< HEAD
    @Path("{subjectClassKey}/{subjectKey}")
    @Consumes(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Response upload(@PathParam("subjectClassKey") String subjectClassKey,
                           @PathParam("subjectKey") String subjectKey,
=======
    @Path("{subjectKey}/{subject}")
    @Consumes(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Response upload(@PathParam("subjectKey") String subjectKey,
                           @PathParam("subject") String subject,
>>>>>>> 3d268c483e83ad1594aa035f9bec8a671ad42e76
                           InputStream request) throws IOException {
        NetworkConfigService service = get(NetworkConfigService.class);
        ObjectNode root = (ObjectNode) mapper().readTree(request);
        consumeSubjectJson(service, root,
<<<<<<< HEAD
                           service.getSubjectFactory(subjectClassKey).createSubject(subjectKey),
                           subjectClassKey);
=======
                           service.getSubjectFactory(subjectKey).createSubject(subject),
                           subjectKey);
>>>>>>> 3d268c483e83ad1594aa035f9bec8a671ad42e76
        return Response.ok().build();
    }

    /**
<<<<<<< HEAD
     * Upload specific network configuration for a subjectKey.
     *
     * @param subjectClassKey subjectKey class key
     * @param subjectKey      subjectKey key
     * @param configKey       configuration class key
     * @param request         network configuration JSON rooted at the top node
=======
     * Upload specific network configuration for a subject.
     *
     * @param subjectKey subject class key
     * @param subject    subject key
     * @param configKey  configuration class key
     * @param request    network configuration JSON rooted at the top node
>>>>>>> 3d268c483e83ad1594aa035f9bec8a671ad42e76
     * @return empty response
     * @throws IOException if unable to parse the request
     */
    @POST
<<<<<<< HEAD
    @Path("{subjectClassKey}/{subjectKey}/{configKey}")
    @Consumes(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Response upload(@PathParam("subjectClassKey") String subjectClassKey,
                           @PathParam("subjectKey") String subjectKey,
=======
    @Path("{subjectKey}/{subject}/{configKey}")
    @Consumes(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Response upload(@PathParam("subjectKey") String subjectKey,
                           @PathParam("subject") String subject,
>>>>>>> 3d268c483e83ad1594aa035f9bec8a671ad42e76
                           @PathParam("configKey") String configKey,
                           InputStream request) throws IOException {
        NetworkConfigService service = get(NetworkConfigService.class);
        ObjectNode root = (ObjectNode) mapper().readTree(request);
<<<<<<< HEAD
        service.applyConfig(service.getSubjectFactory(subjectClassKey).createSubject(subjectKey),
                            service.getConfigClass(subjectClassKey, configKey), root);
=======
        service.applyConfig(service.getSubjectFactory(subjectKey).createSubject(subject),
                            service.getConfigClass(subjectKey, configKey), root);
>>>>>>> 3d268c483e83ad1594aa035f9bec8a671ad42e76
        return Response.ok().build();
    }

    private void consumeJson(NetworkConfigService service, ObjectNode classNode,
                             SubjectFactory subjectFactory) {
        classNode.fieldNames().forEachRemaining(s ->
<<<<<<< HEAD
            consumeSubjectJson(service, (ObjectNode) classNode.path(s),
                               subjectFactory.createSubject(s),
                               subjectFactory.subjectClassKey()));
=======
                                                        consumeSubjectJson(service, (ObjectNode) classNode.path(s),
                                                                           subjectFactory.createSubject(s),
                                                                           subjectFactory.subjectKey()));
>>>>>>> 3d268c483e83ad1594aa035f9bec8a671ad42e76
    }

    private void consumeSubjectJson(NetworkConfigService service,
                                    ObjectNode subjectNode, Object subject,
                                    String subjectKey) {
        subjectNode.fieldNames().forEachRemaining(c ->
            service.applyConfig(subject, service.getConfigClass(subjectKey, c),
<<<<<<< HEAD
                                subjectNode.path(c)));
=======
                                (ObjectNode) subjectNode.path(c)));
>>>>>>> 3d268c483e83ad1594aa035f9bec8a671ad42e76
    }


    /**
     * Clear entire network configuration base.
     *
     * @return empty response
     */
    @DELETE
    @SuppressWarnings("unchecked")
    public Response delete() {
        NetworkConfigService service = get(NetworkConfigService.class);
        service.getSubjectClasses()
                .forEach(subjectClass -> service.getSubjects(subjectClass)
                        .forEach(subject -> service.getConfigs(subject)
<<<<<<< HEAD
                                .forEach(config -> service.removeConfig(subject, config.getClass()))));
=======
                                .forEach(config -> service
                                        .removeConfig(subject, config.getClass()))));
>>>>>>> 3d268c483e83ad1594aa035f9bec8a671ad42e76
        return Response.ok().build();
    }

    /**
     * Clear all network configurations for a subject class.
     *
<<<<<<< HEAD
     * @param subjectClassKey subject class key
     * @return empty response
     */
    @DELETE
    @Path("{subjectClassKey}")
    @SuppressWarnings("unchecked")
    public Response delete(@PathParam("subjectClassKey") String subjectClassKey) {
        NetworkConfigService service = get(NetworkConfigService.class);
        service.getSubjects(service.getSubjectFactory(subjectClassKey).getClass())
                .forEach(subject -> service.getConfigs(subject)
                        .forEach(config -> service.removeConfig(subject, config.getClass())));
=======
     * @param subjectKey subject class key
     * @return empty response
     */
    @DELETE
    @Path("{subjectKey}")
    @SuppressWarnings("unchecked")
    public Response delete(@PathParam("subjectKey") String subjectKey) {
        NetworkConfigService service = get(NetworkConfigService.class);
        service.getSubjects(service.getSubjectFactory(subjectKey).getClass())
                .forEach(subject -> service.getConfigs(subject)
                        .forEach(config -> service
                                .removeConfig(subject, config.getClass())));
>>>>>>> 3d268c483e83ad1594aa035f9bec8a671ad42e76
        return Response.ok().build();
    }

    /**
<<<<<<< HEAD
     * Clear all network configurations for a subjectKey.
     *
     * @param subjectClassKey subjectKey class key
     * @param subjectKey      subjectKey key
     * @return empty response
     */
    @DELETE
    @Path("{subjectClassKey}/{subjectKey}")
    @SuppressWarnings("unchecked")
    public Response delete(@PathParam("subjectClassKey") String subjectClassKey,
                           @PathParam("subjectKey") String subjectKey) {
        NetworkConfigService service = get(NetworkConfigService.class);
        Object s = service.getSubjectFactory(subjectClassKey).createSubject(subjectKey);
=======
     * Clear all network configurations for a subject.
     *
     * @param subjectKey subject class key
     * @param subject    subject key
     * @return empty response
     */
    @DELETE
    @Path("{subjectKey}/{subject}")
    @SuppressWarnings("unchecked")
    public Response delete(@PathParam("subjectKey") String subjectKey,
                           @PathParam("subject") String subject) {
        NetworkConfigService service = get(NetworkConfigService.class);
        Object s = service.getSubjectFactory(subjectKey).createSubject(subject);
>>>>>>> 3d268c483e83ad1594aa035f9bec8a671ad42e76
        service.getConfigs(s).forEach(c -> service.removeConfig(s, c.getClass()));
        return Response.ok().build();
    }

    /**
<<<<<<< HEAD
     * Clear specific network configuration for a subjectKey.
     *
     * @param subjectClassKey subjectKey class key
     * @param subjectKey      subjectKey key
     * @param configKey       configuration class key
     * @return empty response
     */
    @DELETE
    @Path("{subjectClassKey}/{subjectKey}/{configKey}")
    @SuppressWarnings("unchecked")
    public Response delete(@PathParam("subjectClassKey") String subjectClassKey,
                           @PathParam("subjectKey") String subjectKey,
                           @PathParam("configKey") String configKey) {
        NetworkConfigService service = get(NetworkConfigService.class);
        service.removeConfig(service.getSubjectFactory(subjectClassKey).createSubject(subjectKey),
                             service.getConfigClass(subjectClassKey, configKey));
=======
     * Clear specific network configuration for a subject.
     *
     * @param subjectKey subject class key
     * @param subject    subject key
     * @param configKey  configuration class key
     * @return empty response
     */
    @DELETE
    @Path("{subjectKey}/{subject}/{configKey}")
    @SuppressWarnings("unchecked")
    public Response delete(@PathParam("subjectKey") String subjectKey,
                           @PathParam("subject") String subject,
                           @PathParam("configKey") String configKey) {
        NetworkConfigService service = get(NetworkConfigService.class);
        service.removeConfig(service.getSubjectFactory(subjectKey).createSubject(subject),
                             service.getConfigClass(subjectKey, configKey));
>>>>>>> 3d268c483e83ad1594aa035f9bec8a671ad42e76
        return Response.ok().build();
    }

}
