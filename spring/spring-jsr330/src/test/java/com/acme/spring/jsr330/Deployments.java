/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.acme.spring.jsr330;

import com.acme.spring.jsr330.domain.Stock;
import com.acme.spring.jsr330.repository.StockRepository;
import com.acme.spring.jsr330.repository.impl.DefaultStockRepository;
import com.acme.spring.jsr330.service.StockService;
import com.acme.spring.jsr330.service.impl.DefaultStockService;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;

import java.io.File;

/**
 * <p>A helper class for creating the tests deployments.</p>
 *
 * @author <a href="mailto:jmnarloch@gmail.com">Jakub Narloch</a>
 */
public final class Deployments {

    /**
     * <p>Represents the deployment dependencies.</p>
     */
    private static File[] deploymentsDependencies;

    /**
     * <p>Creates new instance of {@link Deployments} class.</p>
     *
     * <p>Private constructor prevents from instantiation outside of this class.</p>
     */
    private Deployments() {
        // empty constructor
    }

    /**
     * <p>Creates new test deployment.</p>
     *
     * @return new test deployment
     */
    public static WebArchive createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "spring-test.war")
                .addClasses(Stock.class, StockRepository.class, StockService.class,
                        DefaultStockRepository.class, DefaultStockService.class)
                .addAsResource("applicationContext.xml")
                .addAsLibraries(getDeploymentDependencies());
    }

    /**
     * <p>Retrieves the deployment dependencies.</p>
     *
     * @return the deployment dependencies
     */
    private static File[] getDeploymentDependencies() {

        if (deploymentsDependencies == null) {

            deploymentsDependencies = DependencyResolvers.use(MavenDependencyResolver.class)
                    .artifacts("org.springframework:spring-context:3.1.1.RELEASE")
                    .resolveAsFiles();
        }

        return deploymentsDependencies;
    }
}
