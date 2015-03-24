/*
 * License (MIT)
 *
 * Copyright (c) 2014-2015 Granite Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.granitepowered.granite;

import com.google.common.base.Optional;
import javassist.ClassPool;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.MinecraftVersion;
import org.spongepowered.api.Platform;
import org.spongepowered.api.Server;
import org.spongepowered.api.plugin.PluginManager;
import org.spongepowered.api.service.ServiceManager;
import org.spongepowered.api.service.SimpleServiceManager;
import org.spongepowered.api.service.command.CommandService;
import org.spongepowered.api.service.command.SimpleCommandService;
import org.spongepowered.api.service.event.EventManager;
import org.spongepowered.api.service.scheduler.AsynchronousScheduler;
import org.spongepowered.api.service.scheduler.Scheduler;
import org.spongepowered.api.service.scheduler.SynchronousScheduler;
import org.spongepowered.api.util.annotation.NonnullByDefault;

import java.io.File;

import javax.inject.Inject;

@NonnullByDefault
public class Granite implements Game {

    private static Granite instance;

    private PluginManager pluginManager;
    private GameRegistry gameRegistry;
    private EventManager eventManager;
    private CommandService commandService;
    private ServiceManager serviceManager;
    private Scheduler scheduler;
    // Not injected directly; initialization is done after classes are rewritten
    private Server server;
    private String implementationVersion;
    private String apiVersion;
    private MinecraftVersion minecraftVersion;
    private ServerConfig serverConfig;
    private ClassPool classPool;
    private Logger logger;
    private File classesDir;

    public Granite() {

    }

    @Inject
    public Granite(PluginManager pluginManager, GameRegistry gameRegistry, EventManager eventManager, Scheduler scheduler,
            String implementationVersion) {
        this.pluginManager = pluginManager;
        this.gameRegistry = gameRegistry;
        this.eventManager = eventManager;
        this.scheduler = scheduler;
        this.serviceManager = new SimpleServiceManager(pluginManager);
        this.commandService = new SimpleCommandService(pluginManager);
        this.implementationVersion = implementationVersion;
        instance = this;
    }

    public static Granite getInstance() {
        return instance;
    }

    public static void setInstance(Granite granite) {
        instance = granite;
    }

    public static void error(String message, Throwable t) {
        getInstance().getLogger().error(message, t);
    }

    public static void error(Throwable t) {
        error("We did a boo-boo :'(", t);
    }

    @Override
    public String getApiVersion() {
        return this.apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @Override
    public String getImplementationVersion() {
        return this.implementationVersion;
    }

    public void setImplementationVersion(String implementationVersion) {
        this.implementationVersion = implementationVersion;
    }

    @Override
    public MinecraftVersion getMinecraftVersion() {
        return this.minecraftVersion;
    }

    @Override
    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    @Override
    public Platform getPlatform() {
        return Platform.SERVER;
    }

    @Override
    public Optional<Server> getServer() {
        return Optional.of(this.server);
    }

    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public EventManager getEventManager() {
        return this.eventManager;
    }

    @Override
    public GameRegistry getRegistry() {
        return this.gameRegistry;
    }

    @Override
    public ServiceManager getServiceManager() {
        return this.serviceManager;
    }

    @Override
    public SynchronousScheduler getSyncScheduler() {
        throw new NotImplementedException("");
    }

    @Override
    public AsynchronousScheduler getAsyncScheduler() {
        throw new NotImplementedException("");
    }

    @Override
    public CommandService getCommandDispatcher() {
        return this.commandService;
    }

    public ServerConfig getServerConfig() {
        return this.serverConfig;
    }

    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public ClassPool getClassPool() {
        return classPool;
    }

    public void setClassPool(ClassPool classPool) {
        this.classPool = classPool;
    }

    public File getClassesDir() {
        return classesDir;
    }

    public void setClassesDir(File classesDir) {
        this.classesDir = classesDir;
    }
}