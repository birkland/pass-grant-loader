/*
 * Copyright 2018 Johns Hopkins University
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
package org.dataconservancy.pass.grant.data;

import org.junit.Test;

/**
 * Test class for the COEUS connector
 *
 * @author jrm@jhu.edu
 */
public class CoeusConnectorTest {

    @Test
    public void testCoeusConnector(){

    }

    /**
     * Test that the query string produces is as expected
     */
    @Test
    public void testBuildString() {

      CoeusConnector connector = new CoeusConnector(null);
        System.out.println(connector.buildQueryString("2018-13-14 06:00:00.0"));

    }

}
