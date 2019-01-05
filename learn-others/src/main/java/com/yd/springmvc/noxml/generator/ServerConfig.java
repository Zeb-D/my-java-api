/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yd.springmvc.noxml.generator;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServerConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String id;
    protected String name;
    protected String description;
    protected String serverAddress;
    protected Integer port;
    protected String contextRoot;
    protected String restRoot;
    protected String userName;
    protected String password;
    protected Integer endpointType;
    protected String tenantId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServerConfig config = (ServerConfig) o;

        return id.equals(config.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ServerConfig [id=" + id + ", name=" + name + ", description=" + description + ", serverAddress="
                + serverAddress + ", port=" + port + ", contextRoot=" + contextRoot + ", restRoot=" + restRoot + ", userName="
                + userName + ", password=" + password + "]";
    }
}
